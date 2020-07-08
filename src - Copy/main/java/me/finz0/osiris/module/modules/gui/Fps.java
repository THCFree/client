package me.finz0.osiris.module.modules.gui;

import me.finz0.osiris.settings.Setting;
import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.module.Module;

import java.awt.*;

public class Fps extends Module {
    public Fps() {
        super("FPS", Category.GUI, "It's Minecraft, there's no way you're dropping frames");
        setDrawn(false);
    }
    public Setting rainbow;
    public Setting customFont;
    public Setting color;

    public void setup(){
        OsirisMod.getInstance().settingsManager.rSetting(rainbow = new Setting("Rainbow", this, false, "FpsRainbow"));
        OsirisMod.getInstance().settingsManager.rSetting(customFont = new Setting("CFont", this, false, "FpsCustomFont"));
        rSetting(color = new Setting("Color", this, Color.WHITE, "FpsColor"));
    }

    public void onEnable(){
        disable();
    }
}
