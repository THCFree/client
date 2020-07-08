package me.finz0.osiris.module.modules.gui;

import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.settings.Setting;

public class CsClickGuiModule extends Module {
    public CsClickGuiModule() {
        super("CsClickGUI", Category.GUI, "Arr Gee Bee");
        rSetting(new Setting("Rainbow", this, false, "CsGuiRainbow"));
        rSetting(new Setting("Red", this, 200, 0, 255, true, "CsGuiRed"));
        rSetting(new Setting("Green", this, 50, 0, 255, true, "CsGuiGreen"));
        rSetting(new Setting("Blue", this, 200, 0, 255, true, "CsGuiBlue"));
        rSetting(cfont = new Setting("CustomFont", this, false, "CsGuiCustomFont"));
    }

    public Setting cfont;

    public void onEnable(){
        mc.displayGuiScreen(OsirisMod.getInstance().csGui);
        disable();
    }
}
