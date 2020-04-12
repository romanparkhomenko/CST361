package com.nilfactor.activity3.model;

import java.io.IOException;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.ArrayList;
import java.util.Arrays;

@ManagedBean
@SessionScoped
public class WeatherData {

	private String time; //displays the hour
	private int temp; //current temperature (F) at hour
	private int minTemp; //lowest temperature over the past 12 hours
	private int maxTemp; //highest temperature over the past 12 hours
	private int windSpeed; // wind speed (MPH) at hour
	private String windDirect; // wind direction at hour
	private double rainIn; //rain collected past hour in inches
	private int humid; //humidity percentage at hour
	private double pressure; //pressure at hour
	
	public ArrayList<WeatherData> dataCol = new ArrayList<WeatherData>();

	public ArrayList<WeatherData> getData(){

		if (dataCol.size() == 0) {
			this.dataCol.add(new WeatherData("09:00 PM",  49, 45, 50,  8,"SSE",  0.00,  79,  30.05));
			this.dataCol.add(new WeatherData("10:00 PM",  48, 45, 50,  8,"SSE",  0.00,  79,  30.05));
			this.dataCol.add(new WeatherData("11:00 PM",  48, 45, 50,  7,"SSE",  0.00,  80,  30.04));
			this.dataCol.add(new WeatherData("12:00 AM",  48, 45, 50,  7,"SSE",  0.01,  83,  30.04));
			this.dataCol.add(new WeatherData("01:00 AM",  47, 45, 50,  6,"SSE",  0.01,  84,  30.05));
			this.dataCol.add(new WeatherData("02:00 AM",  46, 45, 50,  6,"SSE",  0.02,  87,  30.06));
			this.dataCol.add(new WeatherData("03:00 AM",  45, 45, 50,  5,"-S-",  0.04,  87,  30.06));
			this.dataCol.add(new WeatherData("04:00 AM",  44, 44, 50,  6,"SSW",  0.06,  85,  30.07));
			this.dataCol.add(new WeatherData("05:00 AM",  45, 45, 50,  7,"SSw",  0.08,  84,  30.07));
			this.dataCol.add(new WeatherData("06:00 AM",  46, 45, 50,  7,"-W-",  0.04,  86,  30.06));
			this.dataCol.add(new WeatherData("07:00 AM",  46, 45, 50,  7,"-W-",  0.02,  87,  30.05));
			this.dataCol.add(new WeatherData("08:00 AM",  47, 45, 50,  8,"-NW",  0.01,  87,  30.04));
			this.dataCol.add(new WeatherData("09:00 AM",  48, 45, 50,  9,"NNW",  0.01,  84,  30.05));
			this.dataCol.add(new WeatherData("10:00 AM",  51, 45, 51,  9,"NNW",  0.00,  80,  30.05));
		}

		return this.dataCol;
	}
	
	public WeatherData() {}
	
	public WeatherData(String time, int temp, int minTemp, int maxTemp, int windSpeed, String windDirect, double rainIn, int humid, double pressure){
		this.time = time;
		this.temp = temp;
		this.minTemp = minTemp;
		this.maxTemp = maxTemp;
		this.windSpeed = windSpeed;
		this.windDirect = windDirect;
		this.rainIn = rainIn;
		this.humid = humid;
		this.pressure = pressure;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getTemp() {
		return temp;
	}

	public void setTemp(int temp) {
		this.temp = temp;
	}

	public int getMinTemp() {
		return minTemp;
	}

	public void setMinTemp(int minTemp) {
		this.minTemp = minTemp;
	}

	public int getMaxTemp() {
		return maxTemp;
	}

	public void setMaxTemp(int maxTemp) {
		this.maxTemp = maxTemp;
	}

	public int getWindSpeed() {
		return windSpeed;
	}

	public void setWindSpeed(int windSpeed) {
		this.windSpeed = windSpeed;
	}

	public String getWindDirect() {
		return windDirect;
	}

	public void setWindDirect(String windDirect) {
		this.windDirect = windDirect;
	}

	public double getRainIn() {
		return rainIn;
	}

	public void setRainIn(double rainIn) {
		this.rainIn = rainIn;
	}

	public int getHumid() {
		return humid;
	}

	public void setHumid(int humid) {
		this.humid = humid;
	}

	public double getPressure() {
		return pressure;
	}

	public void setPressure(double pressure) {
		this.pressure = pressure;
	}
	
	
	
}
