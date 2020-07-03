package me.finz0.osiris.module.modules.render;

import me.finz0.osiris.settings.Setting;
import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.module.Module;

public class FovModule extends Module {
    public FovModule() {
        super("FOV", Category.RENDER, "Changes your fov");
        OsirisMod.getInstance().settingsManager.rSetting(fov = new Setting("Value", this, 90, 0, 180, true, "FovModValue"));
        setDrawn(false);
    }

    Setting fov;

    public void onUpdate(){
        mc.gameSettings.fovSetting = (float)fov.getValInt();
    }
}
