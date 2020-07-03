package me.finz0.osiris.module.modules.gui;

import me.finz0.osiris.settings.Setting;
import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.module.Module;

public class Time extends Module {
    public Time() {
        super("Time", Category.GUI);
        setDrawn(false);
    }

    public Setting red;
    public Setting green;
    public Setting blue;
    public Setting rainbow;
    public Setting customFont;

    public void setup(){
        red = new Setting("Red", this, 255, 0, 255, true, "TimeRed");
        green = new Setting("Green", this, 255, 0, 255, true, "TimeGreen");
        blue = new Setting("Blue", this, 255, 0, 255, true, "TimeBlue");
        OsirisMod.getInstance().settingsManager.rSetting(red);
        OsirisMod.getInstance().settingsManager.rSetting(green);
        OsirisMod.getInstance().settingsManager.rSetting(blue);
        OsirisMod.getInstance().settingsManager.rSetting(rainbow = new Setting("Rainbow", this, false, "TimeRainbow"));
        OsirisMod.getInstance().settingsManager.rSetting(customFont = new Setting("CFont", this, false, "TimeCustomFont"));
    }

    public void onEnable(){
        disable();
    }
}
