package me.rorykelly.shogi.game.pieces;

import java.util.ArrayList;

import me.rorykelly.shogi.exceptions.InvalidRankFileException;
import me.rorykelly.shogi.game.Board;
import me.rorykelly.shogi.game.Player;
import me.rorykelly.shogi.i18n.GameDisplay;

@SuppressWarnings("serial")
public final class Silver extends Piece {

	public Silver(Player controller, RankFile pos) {
		super(true, controller, pos);
	}

	@Override
	public ArrayList<RankFile> unpromoted() {
		ArrayList<RankFile> moves = CommonMoves.diagonal((Board) getParent(), this, true);
		moves.addAll(CommonMoves.forward((Board) getParent(), this, true));
		return moves;

	}

	@Override
	public ArrayList<RankFile> promoted() {
		return CommonMoves.gold((Board) getParent(), this);
	}

	@Override
	boolean droppable(RankFile pos) {
		Piece piece = board().getPieceAtPos(pos);
		return piece == null;
	}

	@Override
	public Piece copy() {
		Silver silver = null;
		try {
			silver = new Silver(getController(),
					(getPos() == null ? null : new RankFile(getPos().getRank(), getPos().getFile())));
		} catch (InvalidRankFileException e) {
			return silver;
		}
		if (isPromoted())
			silver.promote(true);

		return silver;
	}

	@Override
	String getPieceDisplayString() {
		return isPromoted() ? GameDisplay.getString("silver_promoted") : GameDisplay.getString("silver");
	}

}
