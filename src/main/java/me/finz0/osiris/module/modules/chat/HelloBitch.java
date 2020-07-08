package me.finz0.osiris.module.modules.chat;

import me.finz0.osiris.settings.Setting;
import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.module.Module;

import java.text.DecimalFormat;
import java.util.concurrent.ThreadLocalRandom;

public class HelloBitch extends Module {
    public HelloBitch() {
        super("HelloBitch", Category.CHAT, "Spams helpful messages in chat");
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

            mc.player.sendChatMessage("Hello bitch, nice TITS ahahahahah milky milky milky baby thirsty mommy baby want milk suck suck suck suck hahahaha stupid cunt give me those big udders you slut hahahaha tits tit titty me your caveman me use big titty for big bitty hahaha honk honk honk slut cunt mommy honk honk milky baby want more now honk honk honk pitter patter on those big mommy milkies hee hee hee haha haaaa haaaa can't stop the milk truck coming through honk honk all aboard the titty train hee hee wooo wooooooo honk honk honk");
    }
}
