/*
* IoT Hub Raspberry Pi NodeJS - Microsoft Sample Code - Copyright (c) 2017 - Licensed MIT
*/
const wpi = require('wiring-pi');
const Client = require('azure-iot-device').Client;
const Message = require('azure-iot-device').Message;
const Protocol = require('azure-iot-device-mqtt').Mqtt;
const BME280 = require('bme280-sensor');

const BME280_OPTION = {
  i2cBusNo: 1, // defaults to 1
  i2cAddress: BME280.BME280_DEFAULT_I2C_ADDRESS() // defaults to 0x77
};

const connectionString = 'HostName=CST361CLCMilestone.azure-devices.net;DeviceId=CLCTestingSimulator;SharedAccessKey=51RuBWL2sv2HSGJ+WFmoyRPjDo1pI+hkP8ps1Ha5wys=';
const LEDPin = 4;

var sendingMessage = false;
var messageId = 0;
var client, sensor;
var blinkLEDTimeout = null;

function getMessage(cb) {
  messageId++;
  sensor.readSensorData()
    .then(function (data) {
      cb(JSON.stringify({
        messageId: messageId,
        deviceId: 'Raspberry Pi Web Client',
        temperature: data.temperature_C,
        humidity: data.humidity
      }), data.temperature_C > 30);
      postToSchool(data);
    })
    .catch(function (err) {
      console.error('Failed to read out sensor data: ' + err);
    });
}

function postToSchool(data) {
  const randInt = Math.round(Math.random() * 5);
  const time = Date(Date.now());
  const temperature = Math.floor(data.temperature_C);
  const minTemp = Math.floor(data.temperature_C) - randInt;
  const maxTemp = Math.floor(data.temperature_C) + randInt;
  const windSpeed = randInt;
  const windDirections = ['S', 'N', 'E', 'W', 'SW'];
  const rainIn = randInt + 0.02;
  const humidity = Math.round(data.humidity);
  const pressure = Math.round(data.pressure_hPa).toFixed(2);

  const weatherData = {
    time: time.substring(0, 240),
    temp: temperature,
    minTemp: minTemp,
    maxTemp: maxTemp,
    windSpeed: windSpeed,
    windDirect: windDirections[randInt],
    rainIn: rainIn,
    humid: humidity,
    pressure: parseFloat(pressure),
  };

  fetch('http://localhost:8080/rest/weather', {
    method: 'POST',
    mode: 'cors',
    cache: 'no-cache',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': 'Basic cm9tYW46cGFzc3dvcmQ=',
    },
    body: JSON.stringify(weatherData)
  })
  .then(res => res.json())
  .then(schoolResponse => {
    console.info(schoolResponse);
  })
}

function sendMessage() {
  if (!sendingMessage) { return; }

  getMessage(function (content, temperatureAlert) {
    var message = new Message(content);
    message.properties.add('temperatureAlert', temperatureAlert.toString());
    console.log('Sending message: ' + content);
    client.sendEvent(message, function (err) {
      if (err) {
        console.error('Failed to send message to Azure IoT Hub');
      } else {
        blinkLED();
        console.log('Message sent to Azure IoT Hub');
      }
    });
  });
}

function onStart(request, response) {
  console.log('Try to invoke method start(' + request.payload + ')');
  sendingMessage = true;

  response.send(200, 'Successully start sending message to cloud', function (err) {
    if (err) {
      console.error('[IoT hub Client] Failed sending a method response:\n' + err.message);
    }
  });
}

function onStop(request, response) {
  console.log('Try to invoke method stop(' + request.payload + ')');
  sendingMessage = false;

  response.send(200, 'Successully stop sending message to cloud', function (err) {
    if (err) {
      console.error('[IoT hub Client] Failed sending a method response:\n' + err.message);
    }
  });
}

function receiveMessageCallback(msg) {
  blinkLED();
  var message = msg.getData().toString('utf-8');
  client.complete(msg, function () {
    console.log('Receive message: ' + message);
  });
}

function blinkLED() {
  // Light up LED for 500 ms
  if(blinkLEDTimeout) {
       clearTimeout(blinkLEDTimeout);
   }
  wpi.digitalWrite(LEDPin, 1);
  blinkLEDTimeout = setTimeout(function () {
    wpi.digitalWrite(LEDPin, 0);
  }, 500);
}

// set up wiring
wpi.setup('wpi');
wpi.pinMode(LEDPin, wpi.OUTPUT);
sensor = new BME280(BME280_OPTION);
sensor.init()
  .then(function () {
    sendingMessage = true;
  })
  .catch(function (err) {
    console.error(err.message || err);
  });

// create a client
client = Client.fromConnectionString(connectionString, Protocol);

client.open(function (err) {
  if (err) {
    console.error('[IoT hub Client] Connect error: ' + err.message);
    return;
  }

  // set C2D and device method callback
  client.onDeviceMethod('start', onStart);
  client.onDeviceMethod('stop', onStop);
  client.on('message', receiveMessageCallback);
  setInterval(sendMessage, 2000);
});
