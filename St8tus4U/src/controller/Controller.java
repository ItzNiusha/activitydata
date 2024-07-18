package controller;

import java.io.File;
import java.util.List;

import model.ActivityData;
import model.CsvDataModel;
import model.DataCalculator;
import model.UserModel;

public class Controller {
	private UserModel userModel;

	public Controller(UserModel userModel) {
		this.userModel = userModel;
	}

	public boolean handleLogin(String username, String password) {
		return userModel.authenticate(username, password);
	}

	public List<ActivityData> processCSVFile(File file) {
		return CsvDataModel.processCSVFile(file);
	}

	public String getStartTime(List<ActivityData> data) {
		return DataCalculator.getStartTime(data);
	}

	public String getEndTime(List<ActivityData> data) {
		return DataCalculator.getEndTime(data);
	}

	public double getMaxSpeed(List<ActivityData> data) {
		return DataCalculator.getMaxSpeed(data);
	}

	public double getMinSpeed(List<ActivityData> data) {
		return DataCalculator.getMinSpeed(data);
	}

	public double getMaxHeartRate(List<ActivityData> data) {
		return DataCalculator.getMaxHeartRate(data);
	}

	public double getMinHeartRate(List<ActivityData> data) {
		return DataCalculator.getMinHeartRate(data);
	}

	public double getMaxAltitude(List<ActivityData> data) {
		return DataCalculator.getMaxAltitude(data);
	}

	public double getMinAltitude(List<ActivityData> data) {
		return DataCalculator.getMinAltitude(data);
	}

	public double calculateTotalDistance(List<ActivityData> data) {

		return DataCalculator.calculateTotalDistance(data);
	}

	public double getAverageSpeed(List<ActivityData> data) {
		return DataCalculator.getAverageSpeed(data);
	}

	public double getAverageHeartRate(List<ActivityData> data) {
		return DataCalculator.getAverageCadence(data);
	}

	public double getMaxCadence(List<ActivityData> data) {
		return DataCalculator.getMaxCadence(data);
	}

	public double getMinCadence(List<ActivityData> data) {
		return DataCalculator.getMinCadence(data);
	}

	public double getAverageCadence(List<ActivityData> data) {
		return DataCalculator.getAverageCadence(data);
	}

	public String getDate(List<ActivityData> data) {
		// TODO Auto-generated method stub
		return DataCalculator.getDate(data);
	}
}
