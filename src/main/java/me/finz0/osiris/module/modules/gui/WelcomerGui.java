package me.finz0.osiris.module.modules.gui;

import me.finz0.osiris.settings.Setting;
import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.module.Module;

import java.util.ArrayList;


public class WelcomerGui extends Module {
    public WelcomerGui() {
        super("Welcome", Category.GUI);
        setDrawn(false);
    }

    public Setting red;
    public Setting green;
    public Setting blue;
    public Setting rainbow;
    public Setting message;
    public Setting customFont;
    ArrayList<String> messages;

    public void setup(){
        messages = new ArrayList<>();
        messages.add("Welcome1");
        messages.add("Welcome2");
        messages.add("Hello1");
        messages.add("Hello2");
        red = new Setting("Red", this, 255, 0, 255, true, "GuiWelcomeRed");
        green = new Setting("Green", this, 255, 0, 255, true, "GuiWelcomeGreen");
        blue = new Setting("Blue", this, 255, 0, 255, true, "GuiWelcomeBlue");
        OsirisMod.getInstance().settingsManager.rSetting(red);
        OsirisMod.getInstance().settingsManager.rSetting(green);
        OsirisMod.getInstance().settingsManager.rSetting(blue);
        rainbow = new Setting("Rainbow", this, true, "GuiWelcomeRainbow");
        OsirisMod.getInstance().settingsManager.rSetting(rainbow);
        OsirisMod.getInstance().settingsManager.rSetting(message = new Setting("Message", this, "Welcome1", messages, "GuiWelcomeMessageMode"));
        OsirisMod.getInstance().settingsManager.rSetting(customFont = new Setting("CFont", this, false, "GuiWelcomeCustomFont"));
    }

    public void onEnable(){
        disable();
    }
}
