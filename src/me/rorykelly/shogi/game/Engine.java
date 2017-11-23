package me.rorykelly.shogi.game;

import java.io.Serializable;
import java.util.Random;

import me.rorykelly.shogi.exceptions.InvalidRankFileException;
import me.rorykelly.shogi.game.pieces.*;

public final class Engine implements Serializable {

	private static final long serialVersionUID = 3805850810305066449L;

	private Board board;
	private Player champ;
	private Player jewel;
	private boolean ongoing = true;

	public Engine(Player champ, Player jewel) {
		this.champ = champ;
		this.jewel = jewel;

	}

	private void game() {
		generatePieces();
		furigoma(champ, jewel);
		run(champ.hasSente() ? champ : jewel);

	}

	private void generatePieces() {
		try {
			for (int i = 9; i > 0; i--) {
				board.addPieces(new Pawn(champ, new RankFile('C', i)), new Pawn(jewel, new RankFile('G', i)));
				switch (i) {
				case 9:
				case 1:
					board.addPieces(new Lance(champ, new RankFile('A', i)), new Lance(jewel, new RankFile('I', i)));
					break;
				case 8:
					board.addPieces(new Rook(champ, new RankFile('B', i)), new Bishop(jewel, new RankFile('H', i)));
					board.addPieces(new Knight(champ, new RankFile('A', i)), new Knight(jewel, new RankFile('I', i)));
					break;
				case 2:
					board.addPieces(new Bishop(champ, new RankFile('B', i)), new Rook(jewel, new RankFile('H', i)));
					board.addPieces(new Knight(champ, new RankFile('A', i)), new Knight(jewel, new RankFile('I', i)));
					break;
				case 7:
				case 3:
					board.addPieces(new Silver(champ, new RankFile('A', i)), new Silver(jewel, new RankFile('I', i)));
					break;
				case 6:
				case 4:
					board.addPieces(new Gold(champ, new RankFile('A', i)), new Gold(jewel, new RankFile('I', i)));
					break;
				case 5:
					board.addPieces(new King(champ, new RankFile('A', i)), new King(jewel, new RankFile('I', i)));
					break;
				}
			}
		} catch (InvalidRankFileException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	private void furigoma(Player throwing, Player observing) {
		Random rng = new Random(System.currentTimeMillis() / 1000L);
		int diff = 0;
		for (int i = 0; (i < 5) || ((i == 3 && Math.abs(diff) < 3) || (i == 4 && Math.abs(diff) < 2)); i++) {
			diff += (rng.nextInt(1) == 1 ? 1 : -1);
		}
		throwing.setSente(diff < 1);
		observing.setSente(diff > 1);

	}

	private void run(Player sente) {
		while (ongoing) {

		}
	}

}
