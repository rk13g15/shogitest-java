package me.rorykelly.shogi.gui;

import javax.swing.*;

import me.rorykelly.shogi.game.Board;

public class GameWindow extends JFrame {

	private JMenuBar frameMenu;
	private JMenu fileMenu;
	private JMenu newGame;
	private JMenuItem setupServer;
	private JMenuItem connectTo;
	private JMenuItem quitProgram;
	private JMenu toolsMenu;
	private JMenu aboutMenu;

	private JPanel gamePanel;
	private Board board;
	private JPanel commandPanel;
	private JButton dropButton;
	private JButton concedeButton;
	private JPanel holdPanel;
	private JPanel opponentHold;
	private JLabel opponentLabel;
	private JPanel selfHold;
	private JLabel selfLabel;

	protected GameWindow() {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				init();

			}
		});
	}

	private void init() {
		initFrame();
		initJMenu();
		initGamePanel();

	}

	private void initFrame() {
		setTitle("Test");
		setSize(600, 600);
		setResizable(false);
		setLocationRelativeTo(null);
	}

	private void initJMenu() {
		frameMenu = new JMenuBar();
		fileMenu = new JMenu("File");
		newGame = new JMenu("New game...");
		setupServer = new JMenuItem("Setup game");
		connectTo = new JMenuItem("Connect to game");
		quitProgram = new JMenuItem("Close");
		toolsMenu = new JMenu("Tools");
		aboutMenu = new JMenu("About");

	}

	private void initGamePanel() {
		// TODO Auto-generated method stub

	}
}
