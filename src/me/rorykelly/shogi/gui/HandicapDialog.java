package me.rorykelly.shogi.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import me.rorykelly.shogi.exceptions.InvalidRankFileException;
import me.rorykelly.shogi.game.Board;
import me.rorykelly.shogi.game.Handicap;
import me.rorykelly.shogi.game.Player;
import me.rorykelly.shogi.game.pieces.Bishop;
import me.rorykelly.shogi.game.pieces.Gold;
import me.rorykelly.shogi.game.pieces.King;
import me.rorykelly.shogi.game.pieces.Knight;
import me.rorykelly.shogi.game.pieces.Lance;
import me.rorykelly.shogi.game.pieces.Pawn;
import me.rorykelly.shogi.game.pieces.Piece;
import me.rorykelly.shogi.game.pieces.RankFile;
import me.rorykelly.shogi.game.pieces.Rook;
import me.rorykelly.shogi.game.pieces.Silver;

@SuppressWarnings("serial")
public class HandicapDialog extends JDialog {

	private static final Dimension DIALOG_SIZE = new Dimension(520, 480);

	private BoardDisplay boardDisplay;
	private JPanel bottomPanel;
	private JLabel handicapLabel;
	private JComboBox<String> handicapBox;
	private JButton saveButton;
	private JButton closeButton;
	private JButton flipButton;

	private JPanel contentPanel;

	// TODO change access modifier as necessary
	public HandicapDialog() {
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
		GUIHelper.initDialogDetails(this, "Handicap...", DIALOG_SIZE, null, false, DISPOSE_ON_CLOSE);
		initBoard();
		initBottom();
		initContentPanel();
	}

	private void initBoard() {
		boardDisplay = new BoardDisplay(new Board(false));
		boardDisplay.setIgnoreRepaint(true);
		refreshBoard(new ArrayList<RankFile>());

	}

	private void refreshBoard(List<RankFile> list) {
		try {
			Player champ = new Player(false);
			Player jewel = new Player(true);
			for (int i = 9; i > 0; i--) {
				if (!list.contains(new RankFile('C', i)))
					boardDisplay.getBoard().addPieces(new Pawn(champ, new RankFile('C', i)));
				boardDisplay.getBoard().addPieces(new Pawn(jewel, new RankFile('G', i)));
				switch (i) {
				case 9:
				case 1:
					if (!list.contains(new RankFile('A', i)))
						boardDisplay.getBoard().addPieces(new Lance(champ, new RankFile('A', i)));
					boardDisplay.getBoard().addPieces(new Lance(jewel, new RankFile('I', i)));
					break;
				case 8:
					if (!list.contains(new RankFile('B', i)))
						boardDisplay.getBoard().addPieces(new Rook(champ, new RankFile('B', i)));
					boardDisplay.getBoard().addPieces(new Bishop(jewel, new RankFile('H', i)));
					if (!list.contains(new RankFile('A', i)))
						boardDisplay.getBoard().addPieces(new Knight(champ, new RankFile('A', i)));
					boardDisplay.getBoard().addPieces(new Knight(jewel, new RankFile('I', i)));
					break;
				case 2:
					if (!list.contains(new RankFile('B', i)))
						boardDisplay.getBoard().addPieces(new Bishop(champ, new RankFile('B', i)));
					boardDisplay.getBoard().addPieces(new Rook(jewel, new RankFile('H', i)));
					if (!list.contains(new RankFile('A', i)))
						boardDisplay.getBoard().addPieces(new Knight(champ, new RankFile('A', i)));
					boardDisplay.getBoard().addPieces(new Knight(jewel, new RankFile('I', i)));
					break;
				case 7:
				case 3:
					if (!list.contains(new RankFile('A', i)))
						boardDisplay.getBoard().addPieces(new Silver(champ, new RankFile('A', i)));
					boardDisplay.getBoard().addPieces(new Silver(jewel, new RankFile('I', i)));
					break;
				case 6:
				case 4:
					if (!list.contains(new RankFile('A', i)))
						boardDisplay.getBoard().addPieces(new Gold(champ, new RankFile('A', i)));
					boardDisplay.getBoard().addPieces(new Gold(jewel, new RankFile('I', i)));
					break;
				case 5:
					if (!list.contains(new RankFile('A', i)))
						boardDisplay.getBoard().addPieces(new King(champ, new RankFile('A', i)));
					boardDisplay.getBoard().addPieces(new King(jewel, new RankFile('I', i)));
					break;
				}
			}
		} catch (InvalidRankFileException e) {
			e.printStackTrace();
		}
		for (Piece piece : boardDisplay.getBoard().getPieces()) {
			if (piece.getController().isJewel())
				piece.promote(true);
		}
		boardDisplay.repaint();
	}

	private void initBottom() {
		bottomPanel = new JPanel();
		handicapLabel = new JLabel("Handicap");
		handicapBox = new JComboBox<String>(new DefaultComboBoxModel<String>(Handicap.getHandicapNames()));
		saveButton = new JButton("Save");
		closeButton = new JButton("Close");
		flipButton = new JButton("Flip");

		handicapBox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					boardDisplay.setBoard(new Board(boardDisplay.getBoard().isUpsideDown()));
					refreshBoard(Handicap.getHandicap(handicapBox.getSelectedItem().toString()));
				}

			}

		});

		flipButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				boardDisplay.getBoard().flipBoard();
				repaint();

			}
		});

		bottomPanel.add(handicapLabel);
		bottomPanel.add(handicapBox);
		bottomPanel.add(saveButton);
		bottomPanel.add(closeButton);
		bottomPanel.add(flipButton);

	}

	private void initContentPanel() {
		contentPanel = new JPanel();

		contentPanel.add(boardDisplay);
		contentPanel.add(bottomPanel);

		setContentPane(contentPanel);

	}
}
