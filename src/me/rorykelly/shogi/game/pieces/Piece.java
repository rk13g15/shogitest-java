package me.rorykelly.shogi.game.pieces;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.JComponent;

import me.rorykelly.shogi.exceptions.InvalidRankFileException;
import me.rorykelly.shogi.game.Board;
import me.rorykelly.shogi.game.Player;
import me.rorykelly.shogi.graphics.BoardColours;
import me.rorykelly.shogi.i18n.GameDisplay;
import me.rorykelly.shogi.i18n.Languages;

/**
 * 
 * @author Rory
 *
 */
public abstract class Piece extends JComponent {

	/**
	 * Contains generic move generator functions. These functions are used in
	 * combination with each other to create the complete moving range for a
	 * piece.
	 * 
	 * @author Rory
	 *
	 */
	protected final static class CommonMoves {

		/**
		 * Shows the legal moves in all 4 orthogonal directions (north, south,
		 * east, west). Can be configured to generate one or many moves in the
		 * aforementioned directions.
		 * 
		 * @param board
		 *            the {@link Board} in which to check the moves. Required to
		 *            check when move paths should be stopped.
		 * @param piece
		 *            the {@link Piece} to generate the moves for.
		 * @param single
		 *            if {@code true}, only one move in the respective
		 *            directions will be generated. if {@code false}, all moves
		 *            in the respective directions will be generated.
		 * @return an ArrayList of moves (as {@link RankFile} objects) generated
		 *         for the passed {@link Piece}.
		 */
		public static ArrayList<RankFile> orthogonal(Board board, Piece piece, boolean single) {
			// initalise variables for function
			ArrayList<RankFile> moves = new ArrayList<RankFile>();
			RankFile pos;

			// loop through for each orthogonal direction
			for (int d = 0b0001; d < 0b10000; d <<= 1) {
				try {
					// attempt to create a new rank file representing the
					// current position of the passed piece
					pos = new RankFile(piece.getPos());
				} catch (InvalidRankFileException e) {
					// expected to be thrown when the current position is not a
					// valid position on the board. returns the moves ArrayList,
					// which at this point should be empty
					return moves;
				}

				// generate at least one move, and then repeat depending on the
				// value of the "single" boolean
				do {

					// try moving one square in the appropriate direction,
					// represented by "d"
					try {
						switch (d) {
						case 0b0001:
							pos.setRank((char) (pos.getRank() - 1));
							break;
						case 0b0010:
							pos.setFile((char) (pos.getFile() - 1));
							break;
						case 0b0100:
							pos.setRank((char) (pos.getRank() + 1));
							break;
						case 0b1000:
							pos.setFile((char) (pos.getFile() + 1));
							break;

						default:
							break;

						}
					} catch (InvalidRankFileException e) {
						// expected to be thrown when the piece moves off of the
						// board. break out of the do loop to move on to the
						// next orthogonal direction
						break;
					}

					// check whether the square the piece moved to is a legal
					// move
					if (reachable(board, piece, pos) > 0) {
						// if it is, add this move to the list of moves
						try {
							moves.add(new RankFile(pos));
						} catch (InvalidRankFileException e) {
							e.printStackTrace();
						}
					}
					// check whether the square the piece moved to is not
					// capturing a piece
					if (reachable(board, piece, pos) < 2) {
						// if it is, stop generating moves in the current
						// direction, and move onto the next one
						break;
					}
				} while (!single);

			}

			// once all moves are generated, return the list of these moves
			return moves;
		}

		/**
		 * Shows the legal moves in all 4 diagonal directions (northwest,
		 * northeast, southwest, southeast). Can be configured to generate one
		 * or many moves in the aforementioned directions.
		 * 
		 * @param board
		 *            the {@link Board} in which to check the moves. Required to
		 *            check when move paths should be stopped.
		 * @param piece
		 *            the {@link Piece} to generate the moves for.
		 * @param single
		 *            if {@code true}, only one move in the respective
		 *            directions will be generated. if {@code false}, all moves
		 *            in the respective directions will be generated.
		 * @return an ArrayList of moves (as {@link RankFile} objects) generated
		 *         for the passed {@link Piece}.
		 */
		public static ArrayList<RankFile> diagonal(Board board, Piece piece, boolean single) {
			// initalise variables for function
			ArrayList<RankFile> moves = new ArrayList<RankFile>();
			RankFile pos;

			// loop through for each diagonal direction
			for (int b = 0b100; b < 0b1000; b++) {
				// grab the binary values required to calculate the direction
				// from the loop variable "b"
				int rankB = Character.getNumericValue(Integer.toBinaryString(b).toCharArray()[1]);
				int fileB = Character.getNumericValue(Integer.toBinaryString(b).toCharArray()[2]);

				try {
					// attempt to create a new rank file representing the
					// current position of the passed piece
					pos = new RankFile(piece.getPos());
				} catch (InvalidRankFileException e) {
					// expected to be thrown when the current position is not a
					// valid position on the board. returns the moves ArrayList,
					// which at this point should be empty
					return moves;
				}

				// generate at least one move, and then repeat depending on the
				// value of the "single" boolean
				do {
					// move the piece diagonally in the appropriate direction,
					// based on the aforementioned binary values
					try {
						pos.setRankAndFile((char) (pos.getRank() + (rankB == 0 ? -1 : 1)),
								pos.getFile() + (fileB == 0 ? -1 : 1));
					} catch (InvalidRankFileException e) {
						// expected to be thrown when the piece moves off of the
						// board. break out of the do loop to move on to the
						// next orthogonal direction
						break;
					}

					// check whether the square the piece moved to is a legal
					// move
					if (reachable(board, piece, pos) > 0) {
						// if it is, add this move to the list of moves
						try {
							moves.add(new RankFile(pos));
						} catch (InvalidRankFileException e) {
							e.printStackTrace();
						}
					}
					// check whether the square the piece moved to is not
					// capturing a piece
					if (reachable(board, piece, pos) < 2) {
						// if it is, stop generating moves in the current
						// direction, and move onto the next one
						break;
					}
				} while (!single);

			}

			// once all moves are generated, return the list of these moves
			return moves;

		}

		/**
		 * Shows the legal moves in the diagonal directions in front of a piece
		 * (northwest, northeast, southwest, southeast). This only calculates
		 * for the immediately adjacent directions, so a max of 2 moves should
		 * be generated here.
		 * 
		 * @param board
		 *            the {@link Board} in which to check the moves. Required to
		 *            check when move paths should be stopped.
		 * @param piece
		 *            the {@link Piece} to generate the moves for.
		 * @param offset
		 *            the number of squares forward orthogonally to offset the
		 *            diagonal moves. theoretically can be any value, but in
		 *            practice only offsets of 0 and 1 are used.
		 * @return an ArrayList of moves (as {@link RankFile} objects) generated
		 *         for the passed {@link Piece}.
		 */
		public static ArrayList<RankFile> diagonalForward(Board board, Piece piece, int offset) {
			// initalise variables for function
			ArrayList<RankFile> moves = new ArrayList<RankFile>();
			RankFile pos;

			// loop for the two forward diagonal directions
			for (int b = -0b1; b < 0b10; b += 2) {
				try {
					// attempt to create a new rank file representing the
					// current position of the passed piece
					pos = new RankFile(piece.getPos());
				} catch (InvalidRankFileException e) {
					// expected to be thrown when the current position is not a
					// valid position on the board. returns the moves ArrayList,
					// which at this point should be empty
					return moves;
				}

				//
				try {
					// move the piece diagonally forwards one, and then
					// orthogonally forwards "offset" number of spaces
					pos.setRankAndFile(((char) (pos.getRank() + (piece.getController().isJewel() ? -offset : offset))),
							pos.getFile() + b);

					// check whether the square the piece moved to is a legal
					// move
					if (reachable(board, piece, pos) > 0)
						// if it is, add this move to the list of moves
						moves.add(new RankFile(pos));
				} catch (InvalidRankFileException e) {
					// expected to throw then the piece would go off of the
					// board. continue onto the next direction (or out of the
					// loop)
					continue;
				}
			}

			// once all moves are generated, return the list of these moves
			return moves;
		}

		/**
		 * Shows the legal moves that are in the forward direction of a piece.
		 * 
		 * @param board
		 *            the {@link Board} in which to check the moves. Required to
		 *            check when move paths should be stopped.
		 * @param piece
		 *            the {@link Piece} to generate the moves for.
		 * @param single
		 *            if {@code true}, only one move in the respective
		 *            directions will be generated. if {@code false}, all moves
		 *            in the respective directions will be generated.
		 * @return an ArrayList of moves (as {@link RankFile} objects) generated
		 *         for the passed {@link Piece}.
		 */
		public static ArrayList<RankFile> forward(Board board, Piece piece, boolean single) {
			// initalise variables for function
			ArrayList<RankFile> moves = new ArrayList<RankFile>();
			RankFile pos = null;

			try {
				// attempt to create a new rank file representing the
				// current position of the passed piece
				pos = new RankFile(piece.getPos());
			} catch (InvalidRankFileException e) {
				// expected to be thrown when the current position is not a
				// valid position on the board. returns the moves ArrayList,
				// which at this point should be empty
				return moves;
			}

			do {
				try {
					pos.setRank(piece.getController().isJewel() ? ((char) (pos.getRank() - 1))
							: ((char) (pos.getRank() + 1)));
				} catch (InvalidRankFileException e) {
					break;
				}

				if (reachable(board, piece, pos) > 0) {
					try {
						moves.add(new RankFile(pos));
					} catch (InvalidRankFileException e) {
						e.printStackTrace();
					}
				}
				if (reachable(board, piece, pos) < 2) {
					break;
				}
			} while (!single);

			return moves;
		}

		/**
		 * Shows the legal moves that a Gold General (&#x91D1;&#x5C06) would
		 * have. This is a common moveset as it is used by the majority of
		 * promoted pieces.
		 * 
		 * @param board
		 *            the {@link Board} in which to check the moves. Required to
		 *            check when move paths should be stopped.
		 * @param piece
		 *            the {@link Piece} to generate the moves for.
		 * @return an ArrayList of moves (as {@link RankFile} objects) generated
		 *         for the passed {@link Piece}.
		 * @see Gold
		 */
		public static ArrayList<RankFile> gold(Board board, Piece piece) {
			// return the legal moves for a Gold general (all othogonal
			// directions, plus the forward diagonal directions)
			ArrayList<RankFile> moves = orthogonal(board, piece, true);
			moves.addAll(diagonalForward(board, piece, 1));
			return moves;

		}

	}

	/**
	 * The UID for this class. Used in serialisation.
	 */
	private static final long serialVersionUID = 2564878512454484137L;
	/**
	 * The size of this piece, in pixels. Equal to the board size divided by the
	 * number of ranks/files; in shogi this is 9.
	 */
	public static final int PIECE_SIZE = (Board.BOARD_SIZE / 9);

	/**
	 * If {@code true}, the piece type can be promoted. Otherwise, it cannot.
	 * Used to make sure promotion of unpromotable pieces is impossible.
	 */
	private final boolean promotable;
	/**
	 * If {@code true}, the current piece is promoted. Otherwise, it is
	 * unpromoted.
	 */
	private boolean promoted = false;
	/**
	 * The current owner of this piece. Changes when the piece is captured.
	 */
	private Player controller;
	/**
	 * The current position of this piece, represented by a {@link RankFile}. If
	 * {@code null}, then this piece is in the current controller's hand.
	 */
	private RankFile pos = null;

	/**
	 * Creates a new Piece with the passed parameters.
	 * 
	 * @param promotable
	 *            if {@code true}, this piece can be promoted. Otherwise, it
	 *            cannot.
	 * @param controller
	 *            the current controller of this piece.
	 * @param pos
	 *            the current position of this piece. If {@code null}, then this
	 *            piece is in the current controller's hand.
	 */
	public Piece(boolean promotable, Player controller, RankFile pos) {
		// set instance variables
		this.promotable = promotable;
		this.controller = controller;
		this.pos = pos;

		// set graphics variables
		setDoubleBuffered(true);
		setSize(PIECE_SIZE, PIECE_SIZE);
		setPreferredSize(new Dimension(PIECE_SIZE, PIECE_SIZE));
		setMinimumSize(new Dimension(PIECE_SIZE, PIECE_SIZE));
		setMaximumSize(new Dimension(PIECE_SIZE, PIECE_SIZE));

		// if the piece is not in hand, set the graphical location on the board
		// of this piece to the appropriate rank and file location.
		if (getPos() != null) {
			setLocation(PIECE_SIZE * (9 - getPos().getFile()), PIECE_SIZE * (getPos().getRank() - 65));
		}

	}

	/**
	 * Checks to see if this piece can be promoted.
	 * 
	 * @return {@code true} if this piece can be promoted; {@code false}
	 *         otherwise.
	 */
	public boolean isPromotable() {
		return promotable;
	}

	/**
	 * Checks to see if this piece is currently promoted.
	 * 
	 * @return {@code true} if this piece is promoted; {@code false} otherwise.
	 */
	public boolean isPromoted() {
		return promoted;
	}

	/**
	 * Explictly sets whether this piece is promoted or not. Should only be used
	 * when depromoting a piece (i.e. when a piece is captured).
	 * 
	 * @param promoted
	 *            {@code true} if this piece is promoted; {@code false}
	 *            otherwise.
	 */
	protected void setPromoted(boolean promoted) {
		this.promoted = promoted;
	}

	/**
	 * Gets the current controller of this piece.
	 * 
	 * @return the {@link Player} who controls this piece.
	 */
	public Player getController() {
		return controller;
	}

	/**
	 * Sets the controller of this piece. Should be a different controller than
	 * the current one. Should only be used internally; it will be called
	 * 
	 * @param controller
	 *            the new {@link Player} who will control this piece.
	 */
	protected void setController(Player controller) {
		this.controller = controller;
	}

	/**
	 * Gets the current position of this piece.
	 * 
	 * @return if this piece is on the board, a {@link RankFile} representing
	 *         this piece's current location; {@code null} if this piece is in
	 *         the controller's hand.
	 */
	public RankFile getPos() {
		return pos;
	}

	/**
	 * Sets the position of this piece. Should only be used internally; it will
	 * be changed as appropriate.
	 * 
	 * @param pos
	 *            the new position of this piece.
	 */
	protected void setPos(RankFile pos) {
		this.pos = pos;
	}

	/**
	 * Sets the position of this piece. Creates a {@link RankFile} in this
	 * function.
	 * 
	 * @param rank
	 *            the new rank of this piece.
	 * @param file
	 *            the new file of this piece.
	 * @throws InvalidRankFileException
	 *             - if {@code rank} or {@code file} are not valid, as dictated
	 *             by the regex in {@link RankFile}.
	 */
	public void setPos(char rank, int file) throws InvalidRankFileException {
		setPos(new RankFile(rank, file));

	}

	/**
	 * Gets the board that this piece is currently associated with. This will
	 * return regardless of whether the piece is currently on the board, or in
	 * someone's hand.
	 * 
	 * @return the {@link Board} this piece is associated with.
	 */
	protected Board board() {
		return (Board) getParent();
	}

	/**
	 * Attempts to move this piece to a loocation. As opposed to
	 * {@link Piece#setPos(RankFile)}, which just sets the piece's location,
	 * this takes promotion and capture into account.
	 * 
	 * @param pos
	 *            the position to move this piece to.
	 * @see Piece#capture(Piece)
	 * @see Piece#promote(boolean)
	 */
	public void move(RankFile pos) {
		Piece piece = board().getPieceAtPos(pos);
		if (piece != null)
			capture(piece);
		if (getController().isJewel()) {
			switch (getPos().getRank()) {
			case 'A':
				promote(this instanceof Pawn || this instanceof Lance);
				break;
			case 'B':
				promote(this instanceof Knight);
				break;
			case 'C':
				promote(false);
				break;
			default:
				switch (pos.getRank()) {
				case 'A':
					promote(this instanceof Pawn || this instanceof Lance);
					break;
				case 'B':
					promote(this instanceof Knight);
					break;
				case 'C':
					promote(false);
				default:
					break;
				}
				break;
			}
		} else {
			switch (getPos().getRank()) {
			case 'I':
				promote(this instanceof Pawn || this instanceof Lance);
				break;
			case 'H':
				promote(this instanceof Knight);
				break;
			case 'G':
				promote(false);
				break;
			default:
				switch (pos.getRank()) {
				case 'I':
					promote(this instanceof Pawn || this instanceof Lance);
					break;
				case 'H':
					promote(this instanceof Knight);
					break;
				case 'G':
					promote(false);
				default:
					break;
				}
				break;
			}
		}
		setPos(pos);

	}

	protected void capture(Piece piece) {
		piece.setPos(null);
		piece.setController(getController());
		piece.setPromoted(false);

		// TODO add code for King capture?

	}

	protected void drop(RankFile pos) {
		setPos(pos);

	}

	// TODO revert access to protected
	public void promote(boolean force) {
		if (!force) {
			boolean result = true; // TODO implement asking for promotion
			if (!result)
				return;
		}
		setPromoted(true);

	}

	protected static int reachable(Board board, Piece og, RankFile pos) {
		Board fwd = new Board(board);
		Piece p = fwd.getPieceAtPos(og.getPos());
		p.setPos(pos);
		if (fwd.checkmate(og.getController()))
			return 0;

		else {
			Piece piece = board.getPieceAtPos(pos);
			if (piece == null)
				return 2;
			else if (piece.getController() != og.getController())
				return 1;
			else
				return 0;
		}
	}

	protected ArrayList<RankFile> reachables() {
		if (!isPromotable())
			return unpromoted();
		else
			return isPromoted() ? promoted() : unpromoted();
	}

	public abstract ArrayList<RankFile> unpromoted();

	public abstract ArrayList<RankFile> promoted();

	abstract boolean droppable(RankFile pos);

	public abstract Piece copy();

	@Override
	public String toString() {
		return getClass().getSimpleName() + " (" + (getPos() == null ? "Hand" : getPos().toString()) + ")";
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(isPromoted() && isPromotable() ? BoardColours.promoColor : BoardColours.pieceColor);
		if (getPos() != null) {
			int x = board().isUpsideDown() ? PIECE_SIZE * (getPos().getFile() - 1)
					: PIECE_SIZE * (9 - getPos().getFile());
			int y = board().isUpsideDown() ? Board.BOARD_SIZE - (PIECE_SIZE * (getPos().getRank() - 64))
					: PIECE_SIZE * (getPos().getRank() - 65);
			setLocation(x, y);
		}

		g2.setFont(GameDisplay.getLocale().equals(Languages.JAPANESE) ? GameDisplay.DEFAULT_ASIAN_FONT
				: GameDisplay.DEFAULT_LATIN_FONT);
		FontMetrics m = g2.getFontMetrics(g2.getFont());
		int sX = (PIECE_SIZE - m.stringWidth(getPieceDisplayString())) / 2;
		int sY = ((PIECE_SIZE - m.getHeight()) / 2) + m.getAscent();

		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		if (!getController().isJewel() == !board().isUpsideDown())
			g2.rotate(-Math.PI, PIECE_SIZE / 2, PIECE_SIZE / 2);
		g2.drawString(getPieceDisplayString(), sX, sY);
	}

	abstract String getPieceDisplayString();
}
