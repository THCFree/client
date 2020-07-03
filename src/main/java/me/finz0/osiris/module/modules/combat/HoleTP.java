package me.finz0.osiris.module.modules.combat;

import me.finz0.osiris.module.Module;

public class HoleTP extends Module {
    public HoleTP() {
        super("HoleTP", Category.COMBAT, "Gets you into holes faster");
    }
	
    public void onUpdate() {
        if (mc.player.onGround)
            --mc.player.motionY;
    }
}
//should work, I hope so... I made this somewhat myself actually.