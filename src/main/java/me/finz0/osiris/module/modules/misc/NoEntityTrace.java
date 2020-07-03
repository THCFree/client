package me.finz0.osiris.module.modules.misc;

import me.finz0.osiris.module.Module;
import me.finz0.osiris.settings.Setting;
import net.minecraft.item.ItemPickaxe;

public class NoEntityTrace extends Module {
    public NoEntityTrace() {
        super("NoEntityTrace", Category.MISC);
        rSetting(pickaxeOnly = new Setting("PickaxeOnly", this, false, "NoEntityTracePickaxeOnly"));
    }

    Setting pickaxeOnly;

    boolean isHoldingPickaxe = false;

    public void onUpdate(){
        isHoldingPickaxe = mc.player.getHeldItemMainhand().getItem() instanceof ItemPickaxe;
    }

    public boolean noTrace(){
        if(pickaxeOnly.getValBoolean()) return isEnabled() && isHoldingPickaxe;
        return isEnabled();
    }
}
