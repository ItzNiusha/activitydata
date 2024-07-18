package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvDataModel {

	public static String fileName;

	public CsvDataModel() {

	}

	public static List<ActivityData> processCSVFile(File file) {
		List<ActivityData> dataList = new ArrayList<>();

		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line;
			reader.readLine(); 
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(";");
				String date = parts[0];
				String time = parts[1];
				int elapsedTime = Integer.parseInt(parts[2]);
				double latitude = Double.parseDouble(parts[3].replace(',', '.'));
				double longitude = Double.parseDouble(parts[4].replace(',', '.'));
				double altitude = Double.parseDouble(parts[5].replace(',', '.'));
				double distance = Double.parseDouble(parts[6].replace(',', '.'));
				double heartRate = Double.parseDouble(parts[7].replace(',', '.'));
				double speed = Double.parseDouble(parts[8].replace(',', '.'));
				double cadence = Double.parseDouble(parts[9].replace(',', '.'));

				ActivityData data = new ActivityData(date, time, elapsedTime, latitude, longitude, altitude, distance,
						heartRate, speed, cadence);
				dataList.add(data);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return dataList;
	}

	public String importFileNameFromCSV() {
		return fileName;
	}

}
