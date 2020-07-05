package me.finz0.osiris.module.modules.gui;

import me.finz0.osiris.settings.Setting;
import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.module.Module;
import java.util.ArrayList;

public class Totems extends Module {
    public Totems() {
        super("Totems", Category.GUI, "Shows totem count");
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
        red = new Setting("Red", this, 255, 0, 255, true, "TotemsRed");
        green = new Setting("Green", this, 255, 0, 255, true, "TotemsGreen");
        blue = new Setting("Blue", this, 255, 0, 255, true, "TotemsBlue");
        OsirisMod.getInstance().settingsManager.rSetting(red);
        OsirisMod.getInstance().settingsManager.rSetting(green);
        OsirisMod.getInstance().settingsManager.rSetting(blue);
        OsirisMod.getInstance().settingsManager.rSetting(rainbow = new Setting("Rainbow", this, false, "TotemsRainbow"));
        OsirisMod.getInstance().settingsManager.rSetting(customFont = new Setting("CFont", this, false, "TotemsCustomFont"));
        OsirisMod.getInstance().settingsManager.rSetting(mode = new Setting("Mode", this, "Short", modes, "TotemsMode"));
    }

    public void onEnable(){
        disable();
    }
}
