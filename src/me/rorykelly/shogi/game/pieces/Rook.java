package me.rorykelly.shogi.game.pieces;

import java.util.ArrayList;

import me.rorykelly.shogi.exceptions.InvalidRankFileException;
import me.rorykelly.shogi.game.Player;
import me.rorykelly.shogi.i18n.GameDisplay;

@SuppressWarnings("serial")
public class Rook extends Piece {

	public Rook(Player controller, RankFile pos) {
		super(true, controller, pos);
	}

	@Override
	public ArrayList<RankFile> unpromoted() {
		return CommonMoves.orthogonal(board(), this, false);

	}

	@Override
	public ArrayList<RankFile> promoted() {
		ArrayList<RankFile> moves = unpromoted();
		moves.addAll(CommonMoves.diagonal(board(), this, true));
		return moves;

	}

	@Override
	boolean droppable(RankFile pos) {
		Piece piece = board().getPieceAtPos(pos);
		return piece == null;
	}

	@Override
	public Piece copy() {
		Rook rook = null;
		try {
			rook = new Rook(getController(),
					(getPos() == null ? null : new RankFile(getPos().getRank(), getPos().getFile())));
		} catch (InvalidRankFileException e) {
			return rook;
		}
		if (isPromoted())
			rook.promote(true);

		return rook;
	}

	@Override
	String getPieceDisplayString() {
		return isPromoted() ? GameDisplay.getString("rook_promoted") : GameDisplay.getString("rook");
	}

}
