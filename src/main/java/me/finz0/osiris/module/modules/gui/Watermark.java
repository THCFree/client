package me.finz0.osiris.module.modules.gui;

import me.finz0.osiris.settings.Setting;
import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.module.Module;

public class Watermark extends Module {
    public static Watermark INSTANCE;
    public Watermark() {
        super("Watermark", Category.GUI);
        setDrawn(false);
        INSTANCE = this;
    }

    public Setting red;
    public Setting green;
    public Setting blue;
    public Setting rainbow;
    public Setting customFont;
    public Setting version;

    public void setup(){
        red = new Setting("Red", this, 255, 0, 255, true, "GuiWatermarkRed");
        green = new Setting("Green", this, 255, 0, 255, true, "GuiWatermarkGreen");
        blue = new Setting("Blue", this, 255, 0, 255, true, "GuiWatermarkBlue");
        OsirisMod.getInstance().settingsManager.rSetting(red);
        OsirisMod.getInstance().settingsManager.rSetting(green);
        OsirisMod.getInstance().settingsManager.rSetting(blue);
        rainbow = new Setting("Rainbow", this, true, "GuiWatermarkRainbow");
        OsirisMod.getInstance().settingsManager.rSetting(rainbow);
        OsirisMod.getInstance().settingsManager.rSetting(customFont = new Setting("CustomFont", this, true, "GuiWatermarkCustomFont"));
        rSetting(version = new Setting("Version", this, true, "GuiWatermarkVersionBoolean"));
    }

    public void onEnable(){
        disable();
    }
}
