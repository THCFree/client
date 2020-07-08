package me.finz0.osiris.module.modules.chat;

import me.finz0.osiris.settings.Setting;
import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.module.Module;

import java.text.DecimalFormat;
import java.util.concurrent.ThreadLocalRandom;

public class DotGodSpammer extends Module {
    public DotGodSpammer() {
        super("DotGodSpammer", Category.CHAT, "Spams helpful messages in chat");
    }
    int waitCounter;
    Setting delay;

    public void setup(){
        OsirisMod.getInstance().settingsManager.rSetting(delay = new Setting("Delay", this, 2, 1, 100, true, "DotgodSpammerDelay"));
    }

    public void onUpdate(){
            if (waitCounter < delay.getValDouble() * 100) {
                waitCounter++;
                return;
            } else {
                waitCounter = 0;
            }
            double randomNum = ThreadLocalRandom.current().nextDouble(1.0, 200.0);

            mc.player.sendChatMessage("I just flew " + new DecimalFormat("0.##").format(randomNum) + " meters like a butterfly thanks to DotGod.CC!");
    }
}
