package de.Hero.clickgui.util;

import java.awt.Color;

import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.util.Rainbow;

/**
 *  Made by HeroCode
 *  it's free to use
 *  but you have to credit me
 *
 *  @author HeroCode
 */
public class ColorUtil {
	
	public static Color getClickGUIColor(){
		if(OsirisMod.getInstance().settingsManager.getSettingByID("ClickGuiRainbow").getValBoolean())
			return Rainbow.getColor();
		else
			return new Color((int) OsirisMod.getInstance().settingsManager.getSettingByID("ClickGuiRed").getValDouble(), (int) OsirisMod.getInstance().settingsManager.getSettingByID("ClickGuiGreen").getValDouble(), (int)OsirisMod.getInstance().settingsManager.getSettingByID("ClickGuiBlue").getValDouble());
	}
}
