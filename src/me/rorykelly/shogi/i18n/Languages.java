package me.rorykelly.shogi.i18n;

import java.util.Locale;

public enum Languages {

	BRITISH("en_GB"), JAPANESE("ja_JP");

	private String locale;

	private Languages(String locale) {
		this.locale = locale;
	}

	public String getLocale() {
		return locale;
	}

	public String getLanguage() {
		return getLocale().split("_")[0].toLowerCase();
	}

	public String getCountry() {
		return getLocale().split("_")[1].toUpperCase();

	}

	public Locale toLocale() {
		return new Locale(getLanguage(), getCountry());
	}
}
