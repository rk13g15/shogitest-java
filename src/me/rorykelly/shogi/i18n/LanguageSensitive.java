package me.rorykelly.shogi.i18n;

public interface LanguageSensitive {

	public abstract void changeLanguage(Languages locale);

	public abstract void changeLanguage(Languages locale, Object... replace);

	public abstract void changeKey(String key);

	public abstract void changeKey(String key, Object... replace);

}
