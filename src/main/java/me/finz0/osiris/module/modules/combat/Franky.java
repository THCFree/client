package me.finz0.osiris.module.modules.gui;

import me.finz0.osiris.settings.Setting;
import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.module.Module;

import java.awt.*;

public class Franky extends Module {
    public Franky() {
        super("Franky", Category.GUI, "Does exactly what you think it does :)");
        setDrawn(false);
    }

    public void setup(){
        
    }

    public void onEnable(){
        disable();
    }
}
