package model;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class FileListRenderer extends DefaultListCellRenderer {
	@Override
	public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
			boolean cellHasFocus) {
		if (value instanceof File) {
			File file = (File) value;
			value = file.getName(); // Display just the file name, not the full path
		}
		return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
	}
}
