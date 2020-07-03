package me.finz0.osiris.gui.hud.components;

import de.Hero.clickgui.ClickGUI;
import de.Hero.clickgui.Panel;
import de.Hero.clickgui.util.ColorUtil;
import de.Hero.clickgui.util.FontUtil;
import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.module.ModuleManager;
import me.finz0.osiris.module.modules.gui.PvpInfo;
import me.finz0.osiris.util.Rainbow;
import net.minecraft.client.gui.Gui;

import java.awt.*;

public class PvpinfoComponent extends Panel {
    public PvpinfoComponent(double ix, double iy, ClickGUI parent) {
        super("PvpInfo", ix, iy, 10, 10, false, parent);
        this.isHudComponent = true;
    }

    PvpInfo mod = ((PvpInfo) ModuleManager.getModuleByName("PvpInfo"));

    Color c;
    boolean font;
    Color off;
    Color on;


    public void drawHud(){
        doStuff();
        draw();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks){
        doStuff();
        double w = mc.fontRenderer.getStringWidth(title) + 2;
        c = new Color(50, 50, 50, 100);
        if(isHudComponentPinned) c = new Color(ColorUtil.getClickGUIColor().darker().getRed(), ColorUtil.getClickGUIColor().darker().getGreen(), ColorUtil.getClickGUIColor().darker().getBlue(), 100);
        if (this.dragging) {
            x = x2 + mouseX;
            y = y2 + mouseY;
        }
        this.width = w;
        this.height = FontUtil.getFontHeight() + 2;
        Gui.drawRect((int)x, (int)y, (int)x + (int)width, (int)y + (int)height, c.getRGB());
        FontUtil.drawStringWithShadow(title, x, y + height / 2 - FontUtil.getFontHeight()/2f, 0xffffffff);

        if(extended) {
            double startY = y + height;
            Gui.drawRect((int) x, (int) startY, (int) x + (int) width, (int) startY + ((int) height + 42), c.getRGB());
            if(ModuleManager.isModuleEnabled("AutoCrystal")){
                drawText("CrystalAura ON", (int)x, (int)startY, on.getRGB());
            } else{
                drawText("CrystalAura OFF", (int)x, (int)startY, off.getRGB());
            }
            if(ModuleManager.isModuleEnabled("KillAura")){
                drawText("KillAura ON", (int)x, (int)startY + 10, on.getRGB());
            } else{
                drawText("KillAura OFF", (int)x, (int)startY + 10, off.getRGB());
            }
            if(ModuleManager.isModuleEnabled("AutoFeetPlace")){
                drawText("AutoFeetPlace ON", (int)x, (int)startY + 20, on.getRGB());
            } else{
                drawText("AutoFeetPlace OFF", (int)x, (int)startY + 20, off.getRGB());
            }
        }
    }

    private void doStuff() {
        font = mod.customFont.getValBoolean();
        off = new Color(mod.offR.getValInt(), mod.offG.getValInt(), mod.offB.getValInt());
        on = new Color(mod.onR.getValInt(), mod.onG.getValInt(), mod.onB.getValInt());
        if(mod.offRainbow.getValBoolean()) off = Rainbow.getColor();
        if(mod.onRainbow.getValBoolean()) on = Rainbow.getColor();
    }

    private void drawText(String s, int x, int y, int c){
        if(font) OsirisMod.fontRenderer.drawStringWithShadow(s, x ,y, c);
        else mc.fontRenderer.drawStringWithShadow(s, x, y, c);
    }

    private void draw(){
        if(ModuleManager.isModuleEnabled("AutoCrystal")){
            drawText("CrystalAura ON", (int)x, (int)y, on.getRGB());
        } else{
            drawText("CrystalAura OFF", (int)x, (int)y, off.getRGB());
        }
        if(ModuleManager.isModuleEnabled("KillAura")){
            drawText("KillAura ON", (int)x, (int)y + 10, on.getRGB());
        } else{
            drawText("KillAura OFF", (int)x, (int)y + 10, off.getRGB());
        }
        if(ModuleManager.isModuleEnabled("AutoFeetPlace")){
            drawText("AutoFeetPlace ON", (int)x, (int)y + 20, on.getRGB());
        } else{
            drawText("AutoFeetPlace OFF", (int)x, (int)y + 20, off.getRGB());
        }
    }
}