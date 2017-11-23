package me.rorykelly.shogi.game;

import java.io.Serializable;

public class Player implements Serializable {

	private static final long serialVersionUID = -5749674890691252047L;

	private boolean jewel;
	private boolean sente;

	public Player(boolean jewel) {
		this.jewel = jewel;
	}

	public boolean isJewel() {
		return jewel;
	}

	protected void setSente(boolean sente) {
		this.sente = sente;

	}

	protected boolean hasSente() {
		return this.sente;
	}

}
