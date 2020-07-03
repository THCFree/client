package me.finz0.osiris.module.modules.gui;

import me.finz0.osiris.settings.Setting;
import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.module.Module;

public class PvpInfo extends Module {
    public PvpInfo() {
        super("PvpInfo", Category.GUI);
        setDrawn(false);
    }

    public Setting offRainbow;
    public Setting offR;
    public Setting offG;
    public Setting offB;
    public Setting onRainbow;
    public Setting onR;
    public Setting onG;
    public Setting onB;
    public Setting customFont;

    public void setup(){
        OsirisMod.getInstance().settingsManager.rSetting(offRainbow = new Setting("OffRainbow", this, false, "PvpInfoOffRainbow"));
        OsirisMod.getInstance().settingsManager.rSetting(offR = new Setting("OffR", this, 255, 0, 255, true, "PvpInfoOffRed"));
        OsirisMod.getInstance().settingsManager.rSetting(offG = new Setting("OffG", this, 0, 0, 255, true, "PvpInfoOffGreen"));
        OsirisMod.getInstance().settingsManager.rSetting(offB = new Setting("OffB", this, 0, 0, 255, true, "PvpInfoOffBlue"));
        OsirisMod.getInstance().settingsManager.rSetting(onRainbow = new Setting("OnRainbow", this, false, "PvpInfoOnRainbow"));
        OsirisMod.getInstance().settingsManager.rSetting(onR = new Setting("OnR", this, 0, 0, 255, true, "PvpInfoOnRed"));
        OsirisMod.getInstance().settingsManager.rSetting(onG = new Setting("OnG", this, 255, 0, 255, true, "PvpInfoOnGreen"));
        OsirisMod.getInstance().settingsManager.rSetting(onB = new Setting("OnB", this, 0, 0, 255, true, "PvpInfoOnBlue"));
        OsirisMod.getInstance().settingsManager.rSetting(customFont = new Setting("CFont", this, false, "PvpInfoCustomFont"));
    }

    public void onEnable(){
        disable();
    }
}
