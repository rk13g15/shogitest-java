package me.rorykelly.shogi.game.pieces;

import java.util.regex.Pattern;

import me.rorykelly.shogi.exceptions.InvalidRankFileException;

public final class RankFile {

	private static final Pattern RANK_REGEX = Pattern.compile("[A-I]");
	private static final Pattern FILE_REGEX = Pattern.compile("[1-9]");
	private char rank;
	private int file;

	public RankFile(char rank, int file) throws InvalidRankFileException {
		if (validate(rank, file)) {
			this.rank = rank;
			this.file = file;

		} else
			throw new InvalidRankFileException("");

	}

	public RankFile(RankFile pos) throws InvalidRankFileException {
		if (validate(pos.rank, pos.file)) {
			this.rank = pos.rank;
			this.file = pos.file;

		} else
			throw new InvalidRankFileException("");

	}

	protected char getRank() {
		return this.rank;

	}

	protected int getFile() {
		return this.file;

	}

	protected void setRank(char rank) throws InvalidRankFileException {
		if (validate(rank)) {
			this.rank = rank;

		} else
			throw new InvalidRankFileException("");

	}

	protected void setFile(int file) throws InvalidRankFileException {
		if (validate(file)) {
			this.file = file;

		} else
			throw new InvalidRankFileException("");
	}

	protected void setRankAndFile(char rank, int file) throws InvalidRankFileException {
		if (validate(rank, file)) {
			this.rank = rank;
			this.file = file;

		} else
			throw new InvalidRankFileException("");

	}

	private boolean validate(char rank) {
		return validate(rank, this.file);
	}

	private boolean validate(int file) {
		return validate(this.rank, file);

	}

	private boolean validate(char rank, int file) {
		return RANK_REGEX.matcher(String.valueOf(rank)).matches() && FILE_REGEX.matcher(String.valueOf(file)).matches();

	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof RankFile))
			return false;
		else
			return (this.getRank() == ((RankFile) obj).getRank()) && (this.getFile() == ((RankFile) obj).getFile());

	}

	public boolean equals(char rank, int file) {
		try {
			return equals(new RankFile(rank, file));
		} catch (InvalidRankFileException e) {
			return false;
		}

	}

	@Override
	public String toString() {
		return String.valueOf(file) + String.valueOf(rank);

	}
}
