package me.rorykelly.shogi.game.pieces;

import java.util.ArrayList;

import me.rorykelly.shogi.exceptions.InvalidRankFileException;
import me.rorykelly.shogi.game.Player;
import me.rorykelly.shogi.i18n.GameDisplay;

@SuppressWarnings("serial")
public final class Knight extends Piece {

	public Knight(Player controller, RankFile pos) {
		super(true, controller, pos);
	}

	@Override
	public ArrayList<RankFile> unpromoted() {
		return CommonMoves.diagonalForward(board(), this, 2);
	}

	@Override
	public ArrayList<RankFile> promoted() {
		return CommonMoves.gold(board(), this);
	}

	@Override
	boolean droppable(RankFile pos) {
		if (pos.getRank() == (getController().isJewel() ? 'A' : 'H')
				|| pos.getRank() == (getController().isJewel() ? 'B' : 'I'))
			return false;

		Piece piece = board().getPieceAtPos(pos);
		return piece == null;
	}

	@Override
	public Piece copy() {
		Knight knight = null;
		try {
			knight = new Knight(getController(),
					(getPos() == null ? null : new RankFile(getPos().getRank(), getPos().getFile())));
		} catch (InvalidRankFileException e) {
			return knight;
		}
		if (isPromoted())
			knight.promote(true);

		return knight;
	}

	@Override
	String getPieceDisplayString() {
		return isPromoted() ? GameDisplay.getString("knight_promoted") : GameDisplay.getString("knight");
	}

}
