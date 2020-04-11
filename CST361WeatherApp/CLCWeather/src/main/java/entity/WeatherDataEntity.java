package entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

@Entity(name = "entity.WeatherDataEntity")
@XmlRootElement
@Table(name = "weather", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class WeatherDataEntity implements Serializable {
	/**
     * Default value included to remove warning. Remove or modify at will.
     **/
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id", unique=true, nullable = false)
	private long id;
    
    @Column(name="times")
	private String time; //displays the hour
    
    @Column(name="temp")
	private int temp; //current temperature (F) at hour
    
    @Column(name="min_temp")
	private int minTemp; //lowest temperature over the past 12 hours
    
    @Column(name="max_temp")
	private int maxTemp; //highest temperature over the past 12 hours
    
    @Column(name="wind_speed")
	private int windSpeed; // wind speed (MPH) at hour
    
    @Column(name="wind_direct")
	private String windDirect; // wind direction at hour
    
    @Column(name="rain")
	private double rainIn; //rain collected past hour in inches
    
    @Column(name="humid")
	private int humid; //humidity percentage at hour
    
    @Column(name="pressure")
	private double pressure; //pressure at hour

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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
