package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import controller.Controller;
import model.ActivityData;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.FileListRenderer;

public class AppView extends JFrame {

	private Controller controller;

	private JTextField usernameField;
	private JPasswordField passwordField;

	private JTabbedPane tabbedPane;

	JPanel activityPanel;

	private DefaultListModel<File> listModel;
	private JList<File> activityList;

	private Map<String, String> fileDataMap = new HashMap<>();
	private List<String> importedFiles = new ArrayList<>();

	private JButton importButton;
	private JButton confirmButton;
	private JButton removeButton;

	private File selectedFile;

	private JFileChooser fileChooser;

	private static final String SAVED_FILES_PATH = "saved_files.txt";

	public AppView(Controller controller) {

		this.controller = controller;

		listModel = new DefaultListModel<>();
		activityList = new JList<>(listModel);
		activityList.setCellRenderer(new FileListRenderer());

		loadSavedFiles();

		fileChooser = new JFileChooser();

		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 300);

		JPanel loginPanel = new JPanel();
		loginPanel.setLayout(new GridLayout(3, 2));

		JLabel usernameLabel = new JLabel("Username:");
		usernameField = new JTextField(20);

		JLabel passwordLabel = new JLabel("Password:");
		passwordField = new JPasswordField(20);

		JButton loginButton = new JButton("Login");

		loginPanel.add(usernameLabel);
		loginPanel.add(usernameField);
		loginPanel.add(passwordLabel);
		loginPanel.add(passwordField);
		loginPanel.add(loginButton);

		tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Login", loginPanel);

		JPanel buttonPanel = new JPanel(new FlowLayout());

		confirmButton = new JButton("Confirm");
//		confirmButton.setEnabled(false);
		importButton = new JButton("Import Activity");
		removeButton = new JButton("Remove");

		buttonPanel.add(importButton);
		buttonPanel.add(confirmButton); // Add the Confirm button initially to the button panel
		buttonPanel.add(removeButton);
//		editButton = new JButton("Edit");

		removeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedIndex = activityList.getSelectedIndex();
				if (selectedIndex != -1) {
					File selectedFile = listModel.getElementAt(selectedIndex);
					removeCSVFile(selectedFile);
				}
			}

			// Delete file if user wants to
			private void removeCSVFile(File selectedFile) {
				importedFiles.remove(selectedFile);
				fileDataMap.remove(selectedFile);
				listModel.removeElement(selectedFile);

				// Save the updated list of imported files
				saveSavedFiles();
			}
		});

		activityList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int selectedIndex = activityList.getSelectedIndex();
					if (selectedIndex >= 0) {
						selectedFile = listModel.getElementAt(selectedIndex);
						displayGraphData(selectedFile);

					}
				}
			}
		});

		importButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				int returnVal = fileChooser.showOpenDialog(frame);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					selectedFile = fileChooser.getSelectedFile();
					if (selectedFile != null && selectedFile.exists()) {
						renameFile();
					}
				}
			}
		});

		confirmButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (selectedFile != null) {
					addFileName(selectedFile);
					saveSavedFiles();
				} else {
					JOptionPane.showMessageDialog(frame, "No file selected to add to the list.", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String username = usernameField.getText();
				char[] passwordChars = passwordField.getPassword();
				String password = new String(passwordChars);

				boolean loginSucceeded = controller.handleLogin(username, password);
				if (loginSucceeded) {
					activityPanel = new JPanel();
					activityPanel.setLayout(new BorderLayout());

					// Create a JPanel for the buttons and arrange them horizontally
					JPanel buttonPanel = new JPanel(new FlowLayout());
					buttonPanel.add(importButton);
					buttonPanel.add(confirmButton);
					buttonPanel.add(removeButton);
//					buttonPanel.add(editButton);

					// Add the buttonPanel to the top (north) of activityPanel
					activityPanel.add(buttonPanel, BorderLayout.NORTH);

					// Add a JScrollPane for the JList beneath the buttons
					activityPanel.add(new JScrollPane(activityList), BorderLayout.CENTER);

					// Add the activityPanel to the new tab
					tabbedPane.addTab("Activity", activityPanel);

					displayLoginSuccess();
				} else {
					displayLoginFailure();
				}
			}

		});

		frame.add(tabbedPane);
		frame.setVisible(true);

	}

	private void renameFile() {
		String newName = JOptionPane.showInputDialog(null, "Enter a name for the file:");
		if (newName != null && !newName.isEmpty()) {
			File renamedFile = new File(selectedFile.getParent(), newName);
			try {
				Files.move(selectedFile.toPath(), renamedFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
				selectedFile = renamedFile;
			} catch (IOException ex) {
				JOptionPane.showMessageDialog(null, "Error renaming the file: " + ex.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void displayGraphData(File fileName) {
		List<ActivityData> activityData = controller.processCSVFile(fileName);

		// Create a JFrame for the heart rate plot
		JFrame rateFrame = new JFrame("Graph Data");
		rateFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// Create a panel to display the heart rate chart
		JPanel chartPanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				ChartView.drawChart(g, activityData);
			}
		};

		// Create a button to show the text data in a table
		JButton showTextButton = new JButton("Show Data Table");
		// Create a button to show the map plot
		JButton showMapPlotButton = new JButton("Show Map Plot");

		JPanel buttonPanel = new JPanel(new FlowLayout());
		buttonPanel.add(showTextButton);
		buttonPanel.add(showMapPlotButton);

		// Create a table to display the data
		DefaultTableModel tableModel = new DefaultTableModel();
		tableModel.addColumn("Attribute");
		tableModel.addColumn("Value");
		JTable dataTable = new JTable(tableModel);
		JScrollPane tableScrollPane = new JScrollPane(dataTable);

		// Add an action listener to the "Show Data Table" button
		showTextButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Clear the existing data in the table
				tableModel.setRowCount(0);

				// Add data to the table
				String date = controller.getDate(activityData);
				tableModel.addRow(new Object[] { "Date", date });
				double totalDistance = controller.calculateTotalDistance(activityData);
				tableModel.addRow(new Object[] { "Total Distance", totalDistance });
				String startTime = controller.getStartTime(activityData);
				tableModel.addRow(new Object[] { "Start Time", startTime });
				String endTime = controller.getEndTime(activityData);
				tableModel.addRow(new Object[] { "End Time", endTime });
				double maxSpeed = controller.getMaxSpeed(activityData);
				tableModel.addRow(new Object[] { "Max Speed", maxSpeed });
				double minSpeed = controller.getMinSpeed(activityData);
				tableModel.addRow(new Object[] { "Min Speed", minSpeed });
				double averageSpeed = controller.getAverageSpeed(activityData);
				tableModel.addRow(new Object[] { "Average Speed", averageSpeed });
				double minHr = controller.getMinHeartRate(activityData);
				tableModel.addRow(new Object[] { "Min HeartRate", minHr });
				double maxHr = controller.getMaxHeartRate(activityData);
				tableModel.addRow(new Object[] { "Max HeartRate", maxHr });
				double averageHr = controller.getAverageHeartRate(activityData);
				tableModel.addRow(new Object[] { "Average HeartRate", averageHr });
				double maxCa = controller.getMaxCadence(activityData);
				tableModel.addRow(new Object[] { "Max Cadence", maxCa });
				double minCa = controller.getMinCadence(activityData);
				tableModel.addRow(new Object[] { "Min Cadence", minCa });
				double averageCa = controller.getAverageCadence(activityData);
				tableModel.addRow(new Object[] { "Average Cadence", averageCa });

			}
		});

		showMapPlotButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedIndex = activityList.getSelectedIndex();
				if (selectedIndex >= 0) {
					selectedFile = listModel.getElementAt(selectedIndex);
					showMapPlot(selectedFile);
				}
			}
		});

		// Create a panel for the table
		JPanel tablePanel = new JPanel(new BorderLayout());
		tablePanel.add(tableScrollPane, BorderLayout.CENTER);
		tablePanel.setBorder(BorderFactory.createTitledBorder("Data Table"));

		// Set a preferred size for the table panel
		tablePanel.setPreferredSize(new Dimension(400, 150)); // Adjust the dimensions as needed

		// Create a panel for the north layout
		JPanel northPanel = new JPanel(new BorderLayout());
		northPanel.add(tablePanel, BorderLayout.CENTER);

		// Set an EmptyBorder on the north panel to create a margin
		northPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Adjust the margins as needed

		// Add the components to the rateFrame
		rateFrame.add(chartPanel, BorderLayout.CENTER);
		rateFrame.add(buttonPanel, BorderLayout.SOUTH); // Add the buttonPanel with both buttons
		rateFrame.add(northPanel, BorderLayout.NORTH);

		rateFrame.setPreferredSize(new Dimension(800, 830));
		rateFrame.pack();
		rateFrame.setLocationRelativeTo(this);
		rateFrame.setVisible(true);
	}

	private void showMapPlot(File fileName) {
		List<ActivityData> activityData = controller.processCSVFile(fileName);

		// Create a JFrame for the map plot
		JFrame mapFrame = new JFrame("Map Plot");
		mapFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// Create a panel for the map plot
		JPanel mapPanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
//				ChartView chartView = new ChartView(activityData);
				ChartView.drawMap(g, activityData);
			}
		};

		// Add the map panel to the map frame
		mapFrame.add(mapPanel);

		mapFrame.setPreferredSize(new Dimension(800, 600)); // Adjust the dimensions as needed
		mapFrame.pack();
		mapFrame.setLocationRelativeTo(this);
		mapFrame.setVisible(true);
	}

	public void addFileName(File fileName) {
		listModel.addElement(fileName);
	}

	public void displayLoginSuccess() {
		JOptionPane.showMessageDialog(null, "Login successful!");
	}

	public void displayLoginFailure() {
		JOptionPane.showMessageDialog(null, "Incorrect username or password. Please try again.");
	}

	private void loadSavedFiles() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(SAVED_FILES_PATH));
			String line;
			while ((line = reader.readLine()) != null) {
				importedFiles.add(line);
				fileDataMap.put(line, line);
				listModel.addElement(new File(line)); // Create a File object from the string
			}
			reader.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	private void saveSavedFiles() {
		try {
			FileWriter writer = new FileWriter(SAVED_FILES_PATH);
			for (int i = 0; i < listModel.size(); i++) {
				File path = listModel.getElementAt(i);
				writer.write(path + "\n");
			}
			writer.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}
