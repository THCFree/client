package me.finz0.osiris.module.modules.gui;

import me.finz0.osiris.settings.Setting;
import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.module.Module;

import java.util.ArrayList;

public class Coords extends Module {
    public Coords() {
        super("Coordinates", Category.GUI, "Leaks your coords to your screen");
        setDrawn(false);
    }

    public Setting red;
    public Setting green;
    public Setting blue;
    public Setting rainbow;
    public Setting customFont;
    public Setting decimal;

    public void setup(){
        ArrayList<String> modes = new ArrayList<>();
        modes.add("0");
        modes.add("0.0");
        modes.add("0.00");
        modes.add("0.0#");
        red = new Setting("Red", this, 255, 0, 255, true, "CoordinatesRed");
        green = new Setting("Green", this, 255, 0, 255, true, "CoordinatesGreen");
        blue = new Setting("Blue", this, 255, 0, 255, true, "CoordinatesBlue");
        OsirisMod.getInstance().settingsManager.rSetting(red);
        OsirisMod.getInstance().settingsManager.rSetting(green);
        OsirisMod.getInstance().settingsManager.rSetting(blue);
        OsirisMod.getInstance().settingsManager.rSetting(rainbow = new Setting("Rainbow", this, false, "CoordinatesRainbow"));
        OsirisMod.getInstance().settingsManager.rSetting(customFont = new Setting("CFont", this, false, "CoordinatesCustomFont"));
        OsirisMod.getInstance().settingsManager.rSetting(decimal = new Setting("DecimalFormat", this, "0", modes, "CoordinatesDecimalFormat"));
    }

    public void onEnable(){
        disable();
    }
}
