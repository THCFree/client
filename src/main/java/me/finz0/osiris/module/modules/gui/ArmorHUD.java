package me.finz0.osiris.module.modules.gui;

import me.finz0.osiris.settings.Setting;
import me.finz0.osiris.module.Module;

public class ArmorHUD extends Module {
    public ArmorHUD() {
        super("ArmorHUD", Category.GUI, "Shows equipped armor and durability");
        rSetting(vertical = new Setting("Vertical", this, false, "ArmorHudVertical"));
        rSetting(reverse = new Setting("Reverse", this, true, "ArmorHudReverse"));
    }

    public Setting vertical;
    public Setting reverse;

    public void onEnable(){
        disable();
    }
}
