package me.rorykelly.shogi.game.pieces;

import java.util.ArrayList;

import me.rorykelly.shogi.exceptions.InvalidRankFileException;
import me.rorykelly.shogi.game.Board;
import me.rorykelly.shogi.game.Player;
import me.rorykelly.shogi.i18n.GameDisplay;

@SuppressWarnings("serial")
public final class Pawn extends Piece {

	public Pawn(Player controller, RankFile pos) {
		super(true, controller, pos);
	}

	@Override
	public ArrayList<RankFile> unpromoted() {
		return CommonMoves.forward(board(), this, true);
	}

	@Override
	public ArrayList<RankFile> promoted() {
		return CommonMoves.gold(board(), this);
	}

	@Override
	boolean droppable(RankFile pos) {
		if (pos.getRank() == (getController().isJewel() ? 'A' : 'I'))
			return false;

		Piece piece = board().getPieceAtPos(pos);
		if (piece != null)
			return false;

		return !nifu(pos.getFile()) && !uchifuzume(pos);
	}

	private boolean nifu(int file) {
		for (char c = 'A'; c != 'J'; c = (char) (c + 1)) {
			Piece piece = null;
			try {
				piece = board().getPieceAtPos(new RankFile(c, file));
			} catch (InvalidRankFileException e) {
				continue;
			}
			if (piece instanceof Pawn && getController() == piece.getController() && !piece.isPromoted())
				return true;
		}
		return false;
	}

	private boolean uchifuzume(RankFile pos) {
		King k;
		try {
			k = (King) board().getPieceAtPos(
					new RankFile((char) (pos.getRank() + (getController().isJewel() ? -1 : 1)), pos.getFile()));
		} catch (InvalidRankFileException e) {
			return false;
		}

		Board fwd = new Board(board());
		for (Piece piece : fwd.getPieces()) {
			if (piece instanceof Pawn && piece.getController() == getController() && piece.getPos() == null) {
				piece.drop(pos);
				return fwd.checkmate(k.getController());

			}
		}
		return false;

	}

	@Override
	public Piece copy() {
		try {
			return new Pawn(getController(),
					(getPos() == null ? null : new RankFile(getPos().getRank(), getPos().getFile())));
		} catch (InvalidRankFileException e) {
			return null;
		}
	}

	@Override
	String getPieceDisplayString() {
		return isPromoted() ? GameDisplay.getString("pawn_promoted") : GameDisplay.getString("pawn");
	}

}
