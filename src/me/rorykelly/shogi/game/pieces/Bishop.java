package me.rorykelly.shogi.game.pieces;

import java.util.ArrayList;

import me.rorykelly.shogi.exceptions.InvalidRankFileException;
import me.rorykelly.shogi.game.Player;
import me.rorykelly.shogi.i18n.GameDisplay;

@SuppressWarnings("serial")
public final class Bishop extends Piece {

	public Bishop(Player controller, RankFile pos) {
		super(true, controller, pos);
	}

	@Override
	public ArrayList<RankFile> unpromoted() {
		return CommonMoves.diagonal(board(), this, false);

	}

	@Override
	public ArrayList<RankFile> promoted() {
		ArrayList<RankFile> moves = unpromoted();
		moves.addAll(CommonMoves.orthogonal(board(), this, true));
		return moves;

	}

	@Override
	boolean droppable(RankFile pos) {
		Piece piece = board().getPieceAtPos(pos);
		return piece == null;
	}

	@Override
	public Piece copy() {
		Bishop bishop = null;
		try {
			bishop = new Bishop(getController(),
					(getPos() == null ? null : new RankFile(getPos().getRank(), getPos().getFile())));
		} catch (InvalidRankFileException e) {
			return bishop;
		}
		if (isPromoted())
			bishop.promote(true);

		return bishop;
	}

	@Override
	String getPieceDisplayString() {
		return isPromoted() ? GameDisplay.getString("bishop_promoted") : GameDisplay.getString("bishop");
	}

}
