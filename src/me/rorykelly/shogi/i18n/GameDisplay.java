package me.rorykelly.shogi.i18n;

import java.awt.Font;
import java.util.ResourceBundle;

import javax.swing.plaf.FontUIResource;

public final class GameDisplay {

	private static Languages locale;
	private static ResourceBundle bundle;
	private static final String BASE_NAME = "game/game";
	public static final FontUIResource DEFAULT_LATIN_FONT = new FontUIResource("Segoe UI", Font.PLAIN, 28);
	public static final FontUIResource DEFAULT_ASIAN_FONT = new FontUIResource("Yu Gothic UI", Font.PLAIN, 28);

	public static String getString(String key) {
		return bundle.getString(key);
	}

	public static String getString(String key, Languages locale) {
		return ResourceBundle.getBundle(BASE_NAME, locale.toLocale()).getString(key);
	}

	public static void setLocale(String locale) {
		setLocale(Languages.valueOf(locale));
	}

	public static void setLocale(Languages locale) {
		GameDisplay.locale = locale;
		bundle = ResourceBundle.getBundle(BASE_NAME, GameDisplay.locale.toLocale());
	}

	public static Languages getLocale() {
		return locale;
	}

	public static boolean matches(String locale) {
		return GameDisplay.getLocale().toString().equals(locale);
	}

	public static boolean matches(Languages locale) {
		return GameDisplay.getLocale().equals(locale);
	}
}
