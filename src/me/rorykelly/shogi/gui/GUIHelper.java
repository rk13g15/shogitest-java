package me.rorykelly.shogi.gui;

import java.awt.*;

import javax.swing.*;

public final class GUIHelper {

	protected static final void initFrameDetails(JFrame frame, String title, Dimension size,
			Component locationRelativeTo, boolean resizable, int operation) {
		frame.setTitle(title);
		frame.setSize(size);
		frame.setLocationRelativeTo(locationRelativeTo);
		frame.setResizable(resizable);
		frame.setDefaultCloseOperation(operation);
	}

	protected static final void initDialogDetails(JDialog dialog, String title, Dimension size,
			Component locationRelativeTo, boolean resizable, int operation) {
		dialog.setTitle(title);
		dialog.setSize(size);
		dialog.setLocationRelativeTo(locationRelativeTo);
		dialog.setResizable(resizable);
		dialog.setDefaultCloseOperation(operation);
	}
}
