package me.rorykelly.shogi.gui.controls;

import java.awt.event.KeyEvent;

import javax.swing.Icon;
import javax.swing.JMenuItem;

import me.rorykelly.shogi.i18n.*;

@SuppressWarnings("serial")
public final class JLocaleMenuItem extends JMenuItem implements LanguageSensitive {

	private String key;

	public JLocaleMenuItem(String key) {
		this(key, null, KeyEvent.VK_UNDEFINED);
	}

	public JLocaleMenuItem(String key, Object... replace) {
		this(key, null, KeyEvent.VK_UNDEFINED);
	}

	public JLocaleMenuItem(String key, Icon icon) {
		this(key, icon, KeyEvent.VK_UNDEFINED);
	}

	public JLocaleMenuItem(String key, Icon icon, Object... replace) {
		this(key, icon, KeyEvent.VK_UNDEFINED, replace);
	}

	public JLocaleMenuItem(String key, int mnemonic) {
		this(key, null, mnemonic);
	}

	public JLocaleMenuItem(String key, int mnemonic, Object... replace) {
		this(key, null, mnemonic, replace);
	}

	public JLocaleMenuItem(String key, Icon icon, int mnemonic) {
		super(icon);
		setMnemonic(mnemonic);
		if (key == null)
			throw new IllegalArgumentException("You must assign a key to " + this.toString() + "!");
		else {
			setText(Messages.getString(key));
			setFont((Messages.getLocale().equals(Languages.JAPANESE.toLocale()) ? Messages.DEFAULT_ASIAN_FONT
					: Messages.DEFAULT_LATIN_FONT));
			this.key = key;
		}
	}

	public JLocaleMenuItem(String key, Icon icon, int mnemonic, Object... replace) {
		super(icon);
		setMnemonic(mnemonic);
		if (key == null)
			throw new IllegalArgumentException("You must assign a key to " + this.toString() + "!");
		else {
			setText(String.format(Messages.getString(key), replace));
			setFont((Messages.getLocale().equals(Languages.JAPANESE.toLocale()) ? Messages.DEFAULT_ASIAN_FONT
					: Messages.DEFAULT_LATIN_FONT));
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
