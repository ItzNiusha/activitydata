package model;

public class ActivityData {

	private String date;
	private String time;
	private int elapsedTime;
	private double latitude;
	private double longitude;
	private double altitude;
	private double distance;
	private double heartRate;
	private double speed;
	private double cadence;

	public ActivityData(String date, String time, int elapsedTime, double latitude, double longitude, double altitude,
			double distance, double heartRate, double speed, double cadence) {
		this.date = date;
		this.time = time;
		this.elapsedTime = elapsedTime;
		this.latitude = latitude;
		this.longitude = longitude;
		this.altitude = altitude;
		this.distance = distance;
		this.heartRate = heartRate;
		this.speed = speed;
		this.cadence = cadence;
	}

	// Getter meetoder
	public String getDate() {
		return date;
	}

	public String getTime() {
		return time;
	}

	public int getElapsedTime() {
		return elapsedTime;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public double getAltitude() {
		return altitude;
	}

	public double getDistance() {
		return distance;
	}

	public double getHeartRate() {
		return heartRate;
	}

	public double getSpeed() {
		return speed;
	}

	public double getCadence() {
		return cadence;
	}

	public String toString() {
		// Returnerar representation för ActivityData objekt
		return "Date: " + date + ", Time: " + time + ", Distance: " + distance;
	}
}
