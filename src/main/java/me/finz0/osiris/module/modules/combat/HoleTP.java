package me.finz0.osiris.module.modules.combat;

import me.finz0.osiris.module.Module;
import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.settings.Setting;

public class HoleTP extends Module {
    public HoleTP() {
        super("HoleTP", Category.COMBAT, "Gets you into holes faster");
    }
	
    public void onUpdate() {
        if (mc.player.onGround) //TODO: make a slider so user can customise distance.
            --mc.player.motionY;
    }
}
//should work, I hope so... I made this somewhat myself actually.
