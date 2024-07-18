package model;

import java.util.List;

public class DataCalculator {

	public static String getDate(List<ActivityData> data) {
		if (!data.isEmpty()) {

			return data.get(0).getDate();
		}
		return "N/A";
	}

	public static double getMaxCadence(List<ActivityData> data) {
		double max = Double.MIN_VALUE;
		for (ActivityData entry : data) {
			if (entry.getCadence() > max) {
				max = entry.getCadence();
			}
		}
		return max;
	}

	public static double getMinCadence(List<ActivityData> data) {
		double min = Double.MAX_VALUE;
		for (ActivityData entry : data) {
			if (entry.getCadence() < min) {
				min = entry.getCadence();
			}
		}
		return min;
	}

	public static double getMinLongitude(List<ActivityData> data) {
		double minLongitude = Double.MAX_VALUE;
		for (ActivityData entry : data) {
			if (entry.getLongitude() < minLongitude) {
				minLongitude = entry.getLongitude();
			}
		}
		return minLongitude;
	}

	public static double getMinLatitude(List<ActivityData> data) {
		double minLatitude = Double.MAX_VALUE;
		for (ActivityData entry : data) {
			if (entry.getLatitude() < minLatitude) {
				minLatitude = entry.getLatitude();
			}
		}
		return minLatitude;
	}

	public static double getMaxLongitude(List<ActivityData> data) {
		double maxLongitude = Double.MIN_VALUE;
		for (ActivityData entry : data) {
			if (entry.getLongitude() > maxLongitude) {
				maxLongitude = entry.getLongitude();
			}
		}
		return maxLongitude;
	}

	public static double getMaxLatitude(List<ActivityData> data) {
		double maxLatitude = Double.MIN_VALUE;
		for (ActivityData entry : data) {
			if (entry.getLatitude() > maxLatitude) {
				maxLatitude = entry.getLatitude();
			}
		}
		return maxLatitude;
	}

	public static double getMaxSpeed(List<ActivityData> data) {
		double max = Double.MIN_VALUE;
		for (ActivityData entry : data) {
			if (entry.getSpeed() > max) {
				max = entry.getSpeed();
			}
		}
		return max;
	}

	public static double getMinSpeed(List<ActivityData> data) {
		double min = Double.MAX_VALUE;
		for (ActivityData entry : data) {
			if (entry.getSpeed() < min) {
				min = entry.getSpeed();
			}
		}
		return min;
	}

	public static double getMaxHeartRate(List<ActivityData> data) {
		double max = Double.MIN_VALUE;
		for (ActivityData entry : data) {
			if (entry.getHeartRate() > max) {
				max = entry.getHeartRate();
			}
		}
		return max;
	}

	public static double getMinHeartRate(List<ActivityData> data) {
		double min = Double.MAX_VALUE;
		for (ActivityData entry : data) {
			if (entry.getHeartRate() < min) {
				min = entry.getHeartRate();
			}
		}
		return min;
	}

	public static double getMaxAltitude(List<ActivityData> data) {
		double max = Double.MIN_VALUE;
		for (ActivityData entry : data) {
			if (entry.getAltitude() > max) {
				max = entry.getAltitude();
			}
		}
		return max;
	}

	public static double getMinAltitude(List<ActivityData> data) {
		double min = Double.MAX_VALUE;
		for (ActivityData entry : data) {
			if (entry.getAltitude() < min) {
				min = entry.getAltitude();
			}
		}
		return min;
	}

	public static double calculateTotalDistance(List<ActivityData> data) {
		double totalDistance = 0.0;
		for (int i = 1; i < data.size(); i++) {
			totalDistance += data.get(i).getDistance() - data.get(i - 1).getDistance();
		}
		return totalDistance;
	}

	public static String getStartTime(List<ActivityData> data) {
		if (!data.isEmpty()) {
			return data.get(0).getTime();
		}
		return "N/A";
	}

	public static String getEndTime(List<ActivityData> data) {
		if (!data.isEmpty()) {
			return data.get(data.size() - 1).getTime();
		}
		return "N/A";
	}

	public static double getAverageSpeed(List<ActivityData> data) {
		double sum = 0.0;
		int count = data.size();
		for (ActivityData entry : data) {
			sum += entry.getSpeed();
		}
		return count > 0 ? sum / count : 0;
	}

	public static double getAverageCadence(List<ActivityData> data) {
		double sum = 0.0;
		int count = data.size();
		for (ActivityData entry : data) {
			sum += entry.getCadence();
		}
		return count > 0 ? sum / count : 0;
	}

	public static double getAverageHeartRate(List<ActivityData> data) {
		double sum = 0.0;
		int count = data.size();
		for (ActivityData entry : data) {
			sum += entry.getHeartRate();
		}
		return count > 0 ? sum / count : 0;
	}

}
