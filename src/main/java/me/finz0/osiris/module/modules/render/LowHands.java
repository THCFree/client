package me.finz0.osiris.module.modules.render;

import me.finz0.osiris.settings.Setting;
import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.module.Module;
import net.minecraft.client.renderer.ItemRenderer;

public class LowHands extends Module {
    public LowHands() {
        super("LowOffhand", Category.RENDER, "Makes the item in your offhand not take up half of your screen");
    }
    Setting off;
    ItemRenderer itemRenderer = mc.entityRenderer.itemRenderer;

    public void setup(){
        off = new Setting("Height", this, 0.5, 0, 1, false, "LowOffhandHeight");
        OsirisMod.getInstance().settingsManager.rSetting(off);
    }

    public void onUpdate(){
        itemRenderer.equippedProgressOffHand = (float)off.getValDouble();
    }
}
