package me.rorykelly.shogi.gui.controls;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import me.rorykelly.shogi.i18n.*;

@SuppressWarnings("serial")
public final class JLocaleLabel extends JLabel implements LanguageSensitive {

	private String key;

	public JLocaleLabel(String key) {
		this(key, null, SwingConstants.LEFT);
	}

	public JLocaleLabel(String key, Object... replace) {
		this(key, null, SwingConstants.LEFT, replace);
	}

	public JLocaleLabel(Icon icon, String key) {
		this(key, icon, SwingConstants.LEFT);

	}

	public JLocaleLabel(Icon icon, String key, Object... replace) {
		this(key, icon, SwingConstants.LEFT, replace);

	}

	public JLocaleLabel(String key, int horizontalAlignment) {
		this(key, null, horizontalAlignment);

	}

	public JLocaleLabel(String key, int horizontalAlignment, Object... replace) {
		this(key, null, horizontalAlignment, replace);

	}

	public JLocaleLabel(String key, Icon icon, int horizontalAlignment) {
		super(key, icon, horizontalAlignment);
		if (key == null)
			throw new IllegalArgumentException("You must assign a key to " + this.toString() + "!");
		else {
			setText(Messages.getString(key));
			setFont(Messages.getLocale().equals(Languages.JAPANESE.toLocale()) ? Messages.DEFAULT_ASIAN_FONT
					: Messages.DEFAULT_LATIN_FONT);
			this.key = key;

		}
	}

	public JLocaleLabel(String key, Icon icon, int horizontalAlignment, Object... replace) {
		super(key, icon, horizontalAlignment);
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
		setFont((locale.getLocale().equals(Languages.JAPANESE.toLocale()) ? Messages.DEFAULT_ASIAN_FONT
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