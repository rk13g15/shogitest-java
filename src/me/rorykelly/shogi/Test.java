package me.rorykelly.shogi;

import me.rorykelly.shogi.gui.ConnectDialog;
import me.rorykelly.shogi.gui.HandicapDialog;
import me.rorykelly.shogi.i18n.GameDisplay;
import me.rorykelly.shogi.i18n.Languages;
import me.rorykelly.shogi.i18n.Messages;

public class Test {

	public static void main(String[] args) {
		GameDisplay.setLocale(Languages.JAPANESE);
		new HandicapDialog().setVisible(true);

	}
}
