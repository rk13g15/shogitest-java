package me.rorykelly.shogi.i18n;

import java.awt.Font;
import java.util.Enumeration;
import java.util.ResourceBundle;

import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

public final class Messages {

	private static Languages locale;
	private static ResourceBundle bundle;
	private static final String BASE_NAME = "messages/messages";
	public static final FontUIResource DEFAULT_LATIN_FONT = new FontUIResource("Segoe UI", Font.PLAIN, 12);
	public static final FontUIResource DEFAULT_ASIAN_FONT = new FontUIResource("Yu Gothic UI", Font.PLAIN, 12);

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
		Messages.locale = locale;
		bundle = ResourceBundle.getBundle(BASE_NAME, Messages.locale.toLocale());
		updateUIManager();
	}

	public static Languages getLocale() {
		return locale;
	}

	public static boolean matches(String locale) {
		return Messages.getLocale().toString().equals(locale);
	}

	public static boolean matches(Languages locale) {
		return Messages.getLocale().equals(locale);
	}

	private static void updateUIManager() {
		updateUIManager(getLocale());
	}

	private static void updateUIManager(Languages locale) {
		Enumeration<Object> keys = UIManager.getDefaults().keys();
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value != null && value instanceof FontUIResource)
				UIManager.put(key, locale.equals(Languages.JAPANESE) ? DEFAULT_ASIAN_FONT : DEFAULT_LATIN_FONT);
		}

	}
}
