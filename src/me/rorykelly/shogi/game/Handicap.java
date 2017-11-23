package me.rorykelly.shogi.game;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import me.rorykelly.shogi.exceptions.InvalidRankFileException;
import me.rorykelly.shogi.game.pieces.RankFile;

public class Handicap {

	private static final LinkedHashMap<String, RankFile[]> HANDICAPS;
	static {
		HANDICAPS = new LinkedHashMap<String, RankFile[]>();
		try {
			HANDICAPS.put("None", new RankFile[] {});
			HANDICAPS.put("Black", new RankFile[] {});
			HANDICAPS.put("Lance", new RankFile[] { new RankFile('A', 1) });
			HANDICAPS.put("Bishop", new RankFile[] { new RankFile('B', 2) });
			HANDICAPS.put("Rook", new RankFile[] { new RankFile('B', 8) });
			HANDICAPS.put("Rook-Lance", new RankFile[] { new RankFile('A', 1), new RankFile('B', 8) });
			HANDICAPS.put("2-Piece", new RankFile[] { new RankFile('B', 8), new RankFile('B', 2) });
			HANDICAPS.put("3-Piece",
					new RankFile[] { new RankFile('B', 8), new RankFile('B', 2), new RankFile('A', 9) });
			HANDICAPS.put("4-Piece", new RankFile[] { new RankFile('B', 8), new RankFile('B', 2), new RankFile('A', 9),
					new RankFile('A', 1) });
			HANDICAPS.put("5-Piece", new RankFile[] { new RankFile('B', 8), new RankFile('B', 2), new RankFile('A', 9),
					new RankFile('A', 1), new RankFile('A', 8) });
			HANDICAPS.put("6-Piece", new RankFile[] { new RankFile('B', 8), new RankFile('B', 2), new RankFile('A', 9),
					new RankFile('A', 1), new RankFile('A', 8), new RankFile('A', 2) });
			HANDICAPS.put("8-Piece",
					new RankFile[] { new RankFile('B', 8), new RankFile('B', 2), new RankFile('A', 9),
							new RankFile('A', 1), new RankFile('A', 8), new RankFile('A', 2), new RankFile('A', 7),
							new RankFile('A', 3) });
			HANDICAPS.put("10-Piece",
					new RankFile[] { new RankFile('B', 8), new RankFile('B', 2), new RankFile('A', 9),
							new RankFile('A', 1), new RankFile('A', 8), new RankFile('A', 2), new RankFile('A', 7),
							new RankFile('A', 3), new RankFile('A', 6), new RankFile('A', 4) });
			HANDICAPS.put("Three Pawns",
					new RankFile[] { new RankFile('B', 8), new RankFile('B', 2), new RankFile('A', 9),
							new RankFile('A', 1), new RankFile('A', 8), new RankFile('A', 2), new RankFile('A', 7),
							new RankFile('A', 3), new RankFile('A', 6), new RankFile('A', 4), new RankFile('C', 9),
							new RankFile('C', 8), new RankFile('C', 7), new RankFile('C', 6), new RankFile('C', 5),
							new RankFile('C', 4), new RankFile('C', 3), new RankFile('C', 2), new RankFile('C', 1) });
			HANDICAPS.put("Naked King",
					new RankFile[] { new RankFile('B', 8), new RankFile('B', 2), new RankFile('A', 9),
							new RankFile('A', 1), new RankFile('A', 8), new RankFile('A', 2), new RankFile('A', 7),
							new RankFile('A', 3), new RankFile('A', 6), new RankFile('A', 4), new RankFile('C', 9),
							new RankFile('C', 8), new RankFile('C', 7), new RankFile('C', 6), new RankFile('C', 5),
							new RankFile('C', 4), new RankFile('C', 3), new RankFile('C', 2), new RankFile('C', 1) });
		} catch (InvalidRankFileException e) {
			e.printStackTrace();
		}

	}

	public static final String[] getHandicapNames() {
		String[] list = new String[0];
		return HANDICAPS.keySet().toArray(list);
	}

	public static final List<RankFile> getHandicap(String key) {
		return Arrays.asList(HANDICAPS.get(key));
	}
}
