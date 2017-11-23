package me.rorykelly.shogi.game;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.JPanel;

import me.rorykelly.shogi.game.pieces.King;
import me.rorykelly.shogi.game.pieces.Piece;
import me.rorykelly.shogi.game.pieces.RankFile;
import me.rorykelly.shogi.graphics.BoardColours;

public class Board extends JPanel {

	private static final long serialVersionUID = 2816752000411457277L;
	public static final int BOARD_SIZE = 360;
	private static final int PROMO_OVAL_SIZE = BOARD_SIZE / 72;
	private boolean upsideDown;

	public Board(boolean upsideDown) {
		this.upsideDown = upsideDown;
		setJPanelProps();

	}

	public Board(Board board) {
		this.upsideDown = board.upsideDown;
		setJPanelProps();
		for (Piece piece : board.getPieces()) {
			addPieces(piece.copy());
		}

	}

	private void setJPanelProps() {
		setDoubleBuffered(true);
		setLayout(null);
		setSize(new Dimension(BOARD_SIZE + 1, BOARD_SIZE + 1));
		setPreferredSize(new Dimension(BOARD_SIZE + 1, BOARD_SIZE + 1));
		setMinimumSize(new Dimension(BOARD_SIZE + 1, BOARD_SIZE + 1));
		setMaximumSize(new Dimension(BOARD_SIZE + 1, BOARD_SIZE + 1));

	}

	public boolean isUpsideDown() {
		return upsideDown;
	}

	public void setUpsideDown(boolean upsideDown) {
		this.upsideDown = upsideDown;
	}

	public void flipBoard() {
		this.upsideDown = !this.upsideDown;
	}

	public void addPieces(Piece... pieces) {
		for (Piece piece : pieces) {
			add(piece);
		}
	}

	public ArrayList<Piece> getPieces() {
		ArrayList<Piece> pieces = new ArrayList<Piece>();
		for (Component piece : getComponents())
			pieces.add((Piece) piece);

		return pieces;
	}

	public Piece getPieceAtPos(RankFile pos) {
		for (Piece piece : getPieces()) {
			if (pos.equals(piece.getPos()))
				return piece;
		}
		return null;
	}

	protected boolean check() {
		return check(this);
	}

	protected boolean check(Board board) {
		ArrayList<RankFile> moves = getAllMoves(board);

		for (Piece piece : board.getPieces()) {
			if (piece instanceof King && moves.contains(((King) piece).getPos())) {
				return true;
			}

		}
		return false;

	}

	public boolean checkmate(Player owner) {
		for (Piece piece : getPieces()) {
			if (piece.getController() == owner) {
				ArrayList<RankFile> moves = piece.isPromoted() ? piece.promoted() : piece.unpromoted();
				for (RankFile move : moves) {
					Board copy = new Board(this);
					Piece p = copy.getPieceAtPos(piece.getPos());
					p.move(move);
					if (!check(copy)) {
						return false;
					}
				}
			}
		}
		return true;

	}

	private ArrayList<RankFile> getAllMoves() {
		return getAllMoves(this);
	}

	private ArrayList<RankFile> getAllMoves(Board board) {
		ArrayList<RankFile> moves = new ArrayList<RankFile>();
		for (Component piece : board.getComponents()) {
			moves.addAll(((Piece) piece).isPromoted() || !((Piece) piece).isPromotable() ? ((Piece) piece).unpromoted()
					: ((Piece) piece).promoted());
		}
		return moves;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(BoardColours.boardColor);
		g2.fillRect(0, 0, BOARD_SIZE, BOARD_SIZE);
		g2.setColor(Color.BLACK);
		for (int f = 0; f < 9; f++) {
			for (int r = 0; r < 9; r++) {
				g.drawRect(Piece.PIECE_SIZE * f, Piece.PIECE_SIZE * r, Piece.PIECE_SIZE, Piece.PIECE_SIZE);
			}
		}
		for (int b = 0b00; b < 0b100; b++) {
			g.fillOval((Piece.PIECE_SIZE * (b < 0b10 ? 3 : 6)) - (PROMO_OVAL_SIZE / 2),
					(Piece.PIECE_SIZE * (3 * ((b % 2) + 1))) - (PROMO_OVAL_SIZE / 2), PROMO_OVAL_SIZE, PROMO_OVAL_SIZE);
		}
		paintChildren(g);

	}
}
