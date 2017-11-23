package me.rorykelly.shogi.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.regex.Pattern;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import me.rorykelly.shogi.gui.controls.JLocaleButton;
import me.rorykelly.shogi.gui.controls.JLocaleCheckBox;
import me.rorykelly.shogi.gui.controls.JLocaleLabel;
import me.rorykelly.shogi.network.Connection;

@SuppressWarnings("serial")
public class ConnectDialog extends JDialog {

	private class DetailsPanel extends JPanel {

		private JPanel serverPanel;
		private JLocaleLabel hostLabel;
		private JTextField hostField;
		private JLocaleLabel portLabel;
		private JTextField portField;
		private JLocaleCheckBox defaultPort;

		private JPanel buttonPanel;
		private JLocaleButton connectButton;
		private JLocaleButton closeButton;

		private DetailsPanel() {
			init();
		}

		private void init() {
			initServerPanel();
			initButtonPanel();
			addAllToPanel();

		}

		private void initServerPanel() {
			serverPanel = new JPanel(new GridBagLayout());
			hostLabel = new JLocaleLabel("connect_details_server_host_label");
			hostField = new JTextField(20);
			portLabel = new JLocaleLabel("connect_details_server_port_label");
			portField = new JTextField(6);
			defaultPort = new JLocaleCheckBox("connect_details_server_defaultport_checkbox");

			defaultPort.addChangeListener(new ChangeListener() {

				@Override
				public void stateChanged(ChangeEvent e) {
					portField.setEnabled(defaultPort.isSelected());
					portField.setText(
							defaultPort.isSelected() ? String.valueOf(Connection.DEFAULT_PORT) : portField.getText());

				}
			});

			GridBagConstraints gbc = new GridBagConstraints();

			gbc.gridx = 0;
			gbc.gridy = 0;
			serverPanel.add(hostLabel, gbc);

			gbc.gridx = 1;
			serverPanel.add(hostField, gbc);

			gbc.gridx = 0;
			gbc.gridy = 1;
			serverPanel.add(portLabel, gbc);

			gbc.gridx = 1;
			serverPanel.add(portField, gbc);

			gbc.gridx = 2;
			serverPanel.add(defaultPort, gbc);

		}

		private void initButtonPanel() {
			buttonPanel = new JPanel();
			connectButton = new JLocaleButton("connect_details_button_connect_button");
			closeButton = new JLocaleButton("connect_details_button_close_button");

			connectButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println(IP_REGEX.matcher(hostField.getText()).matches());

				}
			});

			closeButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					dispose();

				}
			});

			buttonPanel.add(connectButton);
			buttonPanel.add(closeButton);
		}

		private void addAllToPanel() {
			add(serverPanel);
			add(buttonPanel);
		}

	}

	private class WaitPanel extends JPanel {

		private WaitPanel() {
			init();
		}

		private void init() {

		}
	}

	private class OptionsPanel extends JPanel {
		private OptionsPanel() {
			init();
		}

		private void init() {

		}
	}

	private static final Dimension DIALOG_SIZE = new Dimension(500, 350);
	private static final Pattern IP_REGEX = Pattern
			.compile("\b((2[0-5]{2}|2[0-4][0-9]|[01]?[0-9][0-9]?\\.)){3}((2[0-5]{2}|2[0-4][0-9]|[01]?[0-9][0-9]?))\b");

	private DetailsPanel detailsPanel;
	private WaitPanel waitPanel;
	private OptionsPanel optionsPanel;

	// TODO set back to protected when/if possible
	public ConnectDialog() {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {

				@Override
				public void run() {
					init();

				}
			});
		} catch (InvocationTargetException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void init() {
		GUIHelper.initDialogDetails(this, "New game (client)...", DIALOG_SIZE, null, false, DISPOSE_ON_CLOSE);
		detailsPanel = new DetailsPanel();
		// waitPanel = new WaitPanel();
		// optionsPanel = new OptionsPanel();
		setContentPane(detailsPanel);

	}

}
