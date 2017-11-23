package me.rorykelly.shogi.game.pieces;

import java.util.ArrayList;

import me.rorykelly.shogi.exceptions.InvalidRankFileException;
import me.rorykelly.shogi.game.Player;
import me.rorykelly.shogi.i18n.GameDisplay;

@SuppressWarnings("serial")
public final class Gold extends Piece {

	public Gold(Player controller, RankFile pos) {
		super(false, controller, pos);
	}

	@Override
	public ArrayList<RankFile> unpromoted() {
		return CommonMoves.gold(board(), this);
	}

	@Override
	public ArrayList<RankFile> promoted() {
		return null;
	}

	@Override
	boolean droppable(RankFile pos) {
		Piece piece = board().getPieceAtPos(pos);
		return piece == null;
	}

	@Override
	public Piece copy() {
		try {
			return new Gold(getController(),
					(getPos() == null ? null : new RankFile(getPos().getRank(), getPos().getFile())));
		} catch (InvalidRankFileException e) {
			return null;
		}
	}

	@Override
	String getPieceDisplayString() {
		return GameDisplay.getString("gold");
	}

}
