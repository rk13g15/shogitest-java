package me.rorykelly.shogi.game.pieces;

import java.util.ArrayList;

import me.rorykelly.shogi.exceptions.InvalidRankFileException;
import me.rorykelly.shogi.game.Player;
import me.rorykelly.shogi.i18n.GameDisplay;

@SuppressWarnings("serial")
public final class Lance extends Piece {

	public Lance(Player controller, RankFile pos) {
		super(true, controller, pos);
	}

	@Override
	public ArrayList<RankFile> unpromoted() {
		return CommonMoves.forward(board(), this, false);
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
		return piece == null;
	}

	@Override
	public Piece copy() {
		Lance lance = null;
		try {
			lance = new Lance(getController(),
					(getPos() == null ? null : new RankFile(getPos().getRank(), getPos().getFile())));
		} catch (InvalidRankFileException e) {
			return lance;
		}
		if (isPromoted())
			lance.promote(true);

		return lance;
	}

	@Override
	String getPieceDisplayString() {
		return isPromoted() ? GameDisplay.getString("lance_promoted") : GameDisplay.getString("lance");
	}

}
