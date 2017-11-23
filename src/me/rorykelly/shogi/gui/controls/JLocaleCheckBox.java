package me.rorykelly.shogi.gui.controls;

import javax.swing.Icon;
import javax.swing.JCheckBox;

import me.rorykelly.shogi.i18n.*;

@SuppressWarnings("serial")
public final class JLocaleCheckBox extends JCheckBox implements LanguageSensitive {

	private String key;

	public JLocaleCheckBox(String key) {
		this(key, null, false);
	}

	public JLocaleCheckBox(String key, Object... replace) {
		this(key, null, false, replace);
	}

	public JLocaleCheckBox(Icon icon, String key) {
		this(key, icon, false);

	}

	public JLocaleCheckBox(Icon icon, String key, Object... replace) {
		this(key, icon, false, replace);

	}

	public JLocaleCheckBox(String key, boolean selected) {
		this(key, null, selected);

	}

	public JLocaleCheckBox(String key, boolean selected, Object... replace) {
		this(key, null, selected, replace);

	}

	public JLocaleCheckBox(String key, Icon icon, boolean selected) {
		super(key, icon, selected);
		if (key == null)
			throw new IllegalArgumentException("You must assign a key to " + this.toString() + "!");
		else {
			setText(Messages.getString(key));
			setFont((Messages.getLocale().equals(Languages.JAPANESE.toLocale()) ? Messages.DEFAULT_ASIAN_FONT
					: Messages.DEFAULT_LATIN_FONT));
			this.key = key;
		}
	}

	public JLocaleCheckBox(String key, Icon icon, boolean selected, Object... replace) {
		super(key, icon, selected);
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
