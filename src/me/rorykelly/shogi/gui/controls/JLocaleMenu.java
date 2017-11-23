package me.rorykelly.shogi.gui.controls;

import javax.swing.JMenu;

import me.rorykelly.shogi.i18n.*;

@SuppressWarnings("serial")
public final class JLocaleMenu extends JMenu implements LanguageSensitive {

	private String key;

	public JLocaleMenu(String key) {
		if (key == null)
			throw new IllegalArgumentException("You must assign a key to " + this.toString() + "!");
		else {
			setText(Messages.getString(key));
			setFont(Messages.getLocale().equals(Languages.JAPANESE.toLocale()) ? Messages.DEFAULT_ASIAN_FONT
					: Messages.DEFAULT_LATIN_FONT);
			this.key = key;
		}
	}

	public JLocaleMenu(String key, Object... replace) {
		if (key == null)
			throw new IllegalArgumentException("You must assign a key to " + this.toString() + "!");
		else {
			setText(String.format(Messages.getString(key), replace));
			setFont(Messages.getLocale().equals(Languages.JAPANESE.toLocale()) ? Messages.DEFAULT_ASIAN_FONT
					: Messages.DEFAULT_LATIN_FONT);
			this.key = key;
		}
	}

	@Override
	public void changeLanguage(Languages locale) {
		setText(Messages.getString(this.key, locale));
		setFont((locale.toLocale().equals(Languages.JAPANESE.toLocale()) ? Messages.DEFAULT_ASIAN_FONT
				: Messages.DEFAULT_LATIN_FONT));
	}

	@Override
	public void changeLanguage(Languages locale, Object... replace) {
		setText(String.format(Messages.getString(this.key, locale), (Object[]) replace));
		setFont((locale.toLocale().equals(Languages.JAPANESE.toLocale()) ? Messages.DEFAULT_ASIAN_FONT
				: Messages.DEFAULT_LATIN_FONT));

	}

	@Override
	public void changeKey(String key) {
		setText(Messages.getString(key));
		this.key = key;

	}

	@Override
	public void changeKey(String key, Object... replace) {
		setText(String.format(Messages.getString(key), replace));
		this.key = key;

	}

}
