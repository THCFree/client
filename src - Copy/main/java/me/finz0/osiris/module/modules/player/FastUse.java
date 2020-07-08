package me.finz0.osiris.module.modules.player;

import me.finz0.osiris.settings.Setting;
import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.module.Module;

import net.minecraft.init.Items;

public class FastUse extends Module {
    public FastUse() {
        super("FastUse", Category.PLAYER, "Sets right click / block break delay to 0");
    }

    Setting xp;
    Setting crystals;
    Setting all;
    Setting breakS;

    public void setup(){
        xp = new Setting( "EXP", this, true, "FastUseEXP");
        OsirisMod.getInstance().settingsManager.rSetting(xp);
        crystals = new Setting("Crystals", this, true, "FastUseCrystals");
        OsirisMod.getInstance().settingsManager.rSetting(crystals);
        all = new Setting("Everything", this, false, "FastUseEverything");
        OsirisMod.getInstance().settingsManager.rSetting(all);
        breakS = new Setting("FastBreak", this, true, "FastUseFastBreak");
        OsirisMod.getInstance().settingsManager.rSetting(breakS);
    }

    public void onUpdate() {
        if(xp.getValBoolean()) {
            if (mc.player != null && (mc.player.getHeldItemMainhand().getItem() == Items.EXPERIENCE_BOTTLE || mc.player.getHeldItemOffhand().getItem() == Items.EXPERIENCE_BOTTLE)) {
                mc.rightClickDelayTimer = 0;
            }
        }

        if(crystals.getValBoolean()) {
            if (mc.player != null && (mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL || mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL)) {
                mc.rightClickDelayTimer = 0;
            }
        }

        if(all.getValBoolean()) {
            mc.rightClickDelayTimer = 0;
        }

        if(breakS.getValBoolean()){
            mc.playerController.blockHitDelay = 0;
        }
    }
}
