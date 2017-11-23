package me.rorykelly.shogi.game.pieces;

import java.util.ArrayList;

import me.rorykelly.shogi.exceptions.InvalidRankFileException;
import me.rorykelly.shogi.game.Player;
import me.rorykelly.shogi.i18n.GameDisplay;

@SuppressWarnings("serial")
public final class King extends Piece {

	public King(Player controller, RankFile pos) {
		super(false, controller, pos);

	}

	@Override
	public ArrayList<RankFile> unpromoted() {
		ArrayList<RankFile> moves = CommonMoves.diagonal(board(), this, true);
		moves.addAll(CommonMoves.orthogonal(board(), this, true));
		return moves;
	}

	@Override
	public ArrayList<RankFile> promoted() {
		return null;
	}

	@Override
	boolean droppable(RankFile pos) {
		return false;
	}

	@Override
	public Piece copy() {
		try {
			return new King(getController(),
					(getPos() == null ? null : new RankFile(getPos().getRank(), getPos().getFile())));
		} catch (InvalidRankFileException e) {
			return null;
		}
	}

	@Override
	String getPieceDisplayString() {
		return getController() != null ? (getController().isJewel() ? GameDisplay.getString("king_jewel")
				: GameDisplay.getString("king_champ")) : GameDisplay.getString("king_champ");
	}
}
