package me.rorykelly.shogi.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import me.rorykelly.shogi.game.Board;
import me.rorykelly.shogi.game.pieces.Piece;
import me.rorykelly.shogi.i18n.GameDisplay;
import me.rorykelly.shogi.i18n.Languages;

@SuppressWarnings("serial")
public class BoardDisplay extends JPanel {

	public static final int BOARD_DISPLAY_SIZE = (10 * Board.BOARD_SIZE) / 9;

	private Board board;

	protected BoardDisplay(Board board) {
		this.board = board;
		setLayout(null);
		setSize(BOARD_DISPLAY_SIZE + 1, BOARD_DISPLAY_SIZE + 1);
		setPreferredSize(new Dimension(BOARD_DISPLAY_SIZE + 1, BOARD_DISPLAY_SIZE + 1));
		setMaximumSize(new Dimension(BOARD_DISPLAY_SIZE + 1, BOARD_DISPLAY_SIZE + 1));
		setMinimumSize(new Dimension(BOARD_DISPLAY_SIZE + 1, BOARD_DISPLAY_SIZE + 1));
		add(board);
		board.setLocation(0, 40);

	}

	protected Board getBoard() {
		return this.board;
	}

	protected void setBoard(Board board) {
		removeAll();
		this.board = board;
		add(board);
		board.setLocation(0, 40);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.BLACK);
		g2.setFont(GameDisplay.getLocale().equals(Languages.JAPANESE) ? GameDisplay.DEFAULT_ASIAN_FONT
				: GameDisplay.DEFAULT_LATIN_FONT);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		FontMetrics m = g2.getFontMetrics(g2.getFont());
		for (int x = 1; x < 10; x++) {
			int f = getBoard().isUpsideDown() ? x : 10 - x;
			int sX = (Piece.PIECE_SIZE - m.stringWidth(getFileDisplayString(f))) / 2;
			int sY = ((Piece.PIECE_SIZE - m.getHeight()) / 2) + m.getAscent();
			g2.drawString(getFileDisplayString(f), sX + ((x - 1) * 40), sY);

		}
		for (int y = 1; y < 10; y++) {
			char r = getBoard().isUpsideDown() ? (char) (74 - y) : (char) (64 + y);
			int sX = (40 - m.stringWidth(getRankDisplayString(r))) / 2;
			int sY = ((40 - m.getHeight()) / 2) + m.getAscent();
			g2.drawString(getRankDisplayString(r), sX + 360, sY + (y * 40));

		}

		paintChildren(g);

	}

	private String getRankDisplayString(char rank) {
		return GameDisplay.getString("rank_" + Character.toLowerCase(rank));
	}

	private String getRankDisplayString(char rank, Languages locale) {
		return GameDisplay.getString("rank_" + Character.toLowerCase(rank), locale);
	}

	private String getFileDisplayString(int file) {
		return GameDisplay.getString("file_" + file);
	}

	private String getFileDisplayString(int file, Languages locale) {
		return GameDisplay.getString("file_" + file, locale);
	}

}
