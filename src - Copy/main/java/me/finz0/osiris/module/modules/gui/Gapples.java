package me.finz0.osiris.module.modules.gui;

import me.finz0.osiris.settings.Setting;
import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.module.Module;
import java.util.ArrayList;

public class Gapples extends Module {
    public Gapples() {
        super("Gapples", Category.GUI, "Shows how many gapples you have");
        setDrawn(false);
    }


    public Setting red;
    public Setting green;
    public Setting blue;
    public Setting rainbow;
    public Setting customFont;
    public Setting mode;

    public void setup(){
        ArrayList<String> modes = new ArrayList<>();
        modes.add("Short");
        modes.add("Full");
        modes.add("Item");
        red = new Setting("Red", this, 255, 0, 255, true, "GapplesRed");
        green = new Setting("Green", this, 255, 0, 255, true, "GapplesGreen");
        blue = new Setting("Blue", this, 255, 0, 255, true, "GapplesBlue");
        OsirisMod.getInstance().settingsManager.rSetting(red);
        OsirisMod.getInstance().settingsManager.rSetting(green);
        OsirisMod.getInstance().settingsManager.rSetting(blue);
        OsirisMod.getInstance().settingsManager.rSetting(rainbow = new Setting("Rainbow", this, false, "GapplesRainbow"));
        OsirisMod.getInstance().settingsManager.rSetting(customFont = new Setting("CFont", this, false, "GapplesCustomFont"));
        OsirisMod.getInstance().settingsManager.rSetting(mode = new Setting("Mode", this, "Short", modes, "GapplesMode"));
    }

    public void onEnable(){
        disable();
    }
}
