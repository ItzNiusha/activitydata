package view;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import javax.swing.JPanel;

import model.ActivityData;
import model.DataCalculator;

public class ChartView extends JPanel {

	private List<ActivityData> activityData;

	public ChartView(List<ActivityData> activityData) {
		this.activityData = activityData;
	}

	public static void drawChart(Graphics g, List<ActivityData> data) {
		int width = 800;
		int height = 600;
		int margin = 50;
		int dataSize = data.size();

		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);

		g.setColor(Color.BLACK);
		g.drawLine(margin, margin, margin, height - margin); // Y-axis (Heart Rate)
		g.drawLine(margin, height - margin, width - margin, height - margin); // X-axis (Time)

		double xScale = (double) (width - 2 * margin) / dataSize;

		// Scales for heart rate, altitude, and speed
		double yScaleHeartRate = (double) (height - 2 * margin)
				/ (DataCalculator.getMaxHeartRate(data) - DataCalculator.getMinHeartRate(data));
		double yScaleAltitude = (double) (height - 2 * margin)
				/ (DataCalculator.getMaxAltitude(data) - DataCalculator.getMinAltitude(data));
		double yScaleSpeed = (double) (height - 2 * margin)
				/ (DataCalculator.getMaxSpeed(data) - DataCalculator.getMinSpeed(data));

		// Draw heart rate graph in blue
		g.setColor(Color.BLUE);
		for (int i = 1; i < dataSize; i++) {
			int x1 = (int) ((i - 1) * xScale + margin);
			int x2 = (int) (i * xScale + margin);
			int y1 = (int) ((DataCalculator.getMaxHeartRate(data) - data.get(i - 1).getHeartRate()) * yScaleHeartRate
					+ margin);
			int y2 = (int) ((DataCalculator.getMaxHeartRate(data) - data.get(i).getHeartRate()) * yScaleHeartRate
					+ margin);
			g.drawLine(x1, y1, x2, y2);
		}

		// Draw altitude graph in red
		g.setColor(Color.RED);
		for (int i = 1; i < dataSize; i++) {
			int x1 = (int) ((i - 1) * xScale + margin);
			int x2 = (int) (i * xScale + margin);
			int y1 = (int) ((DataCalculator.getMaxAltitude(data) - data.get(i - 1).getAltitude()) * yScaleAltitude
					+ margin);
			int y2 = (int) ((DataCalculator.getMaxAltitude(data) - data.get(i).getAltitude()) * yScaleAltitude
					+ margin);
			g.drawLine(x1, y1, x2, y2);
		}

		// Draw speed graph in green
		g.setColor(Color.GREEN);
		for (int i = 1; i < dataSize; i++) {
			int x1 = (int) ((i - 1) * xScale + margin);
			int x2 = (int) (i * xScale + margin);
			int y1 = (int) ((DataCalculator.getMaxSpeed(data) - data.get(i - 1).getSpeed()) * yScaleSpeed + margin);
			int y2 = (int) ((DataCalculator.getMaxSpeed(data) - data.get(i).getSpeed()) * yScaleSpeed + margin);
			g.drawLine(x1, y1, x2, y2);
		}

		// Labels to distinguish different attributes
		g.setColor(Color.BLUE);
		g.drawString("Heart Rate", width - margin - 50, margin + 10);
		g.setColor(Color.RED);
		g.drawString("Altitude", width - margin - 50, margin + 30);
		g.setColor(Color.GREEN);
		g.drawString("Speed", width - margin - 50, margin + 50);
	}

	public static void drawMap(Graphics g, List<ActivityData> mapData) {
		int width = 800;
		int height = 600;
		int margin = 50;
		int dataSize = mapData.size();

		// Calculate bounds for the map
		double minLat = DataCalculator.getMinLatitude(mapData);
		double maxLat = DataCalculator.getMaxLatitude(mapData);
		double minLong = DataCalculator.getMinLongitude(mapData);
		double maxLong = DataCalculator.getMaxLongitude(mapData);

		// Clear the canvas
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);


		// beräkna skalfaktorer för latitude and longitude
		double xScale = (double) (width - 2 * margin) / (maxLong - minLong);
		double yScale = (double) (height - 2 * margin) / (maxLat - minLat);

		// Rita kartans väg
		g.setColor(Color.BLUE);
		for (int i = 1; i < dataSize; i++) {
			double lat1 = mapData.get(i - 1).getLatitude();
			double long1 = mapData.get(i - 1).getLongitude();
			double lat2 = mapData.get(i).getLatitude();
			double long2 = mapData.get(i).getLongitude();

			int x1 = (int) ((long1 - minLong) * xScale + margin);
			int y1 = (int) (height - ((lat1 - minLat) * yScale + margin));
			int x2 = (int) ((long2 - minLong) * xScale + margin);
			int y2 = (int) (height - ((lat2 - minLat) * yScale + margin));

			g.drawLine(x1, y1, x2, y2);
		}

		// Label for the map
		g.setColor(Color.BLUE);
		g.drawString("Map View", width - margin - 50, margin + 10);
	}

}
