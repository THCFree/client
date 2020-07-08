package me.finz0.osiris.module.modules.render;

import me.finz0.osiris.settings.Setting;
import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.event.events.RenderEvent;
import me.finz0.osiris.friends.Friends;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.util.GeometryMasks;
import me.finz0.osiris.util.OsirisTessellator;
import me.finz0.osiris.util.Rainbow;
import net.minecraft.entity.item.*;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class BoxESP extends Module {
    public BoxESP() {
        super("BoxESP", Category.RENDER, "Draws a box around entities");
        OsirisMod.getInstance().settingsManager.rSetting(players = new Setting("Players", this, false, "BoxEspPlayers"));
        OsirisMod.getInstance().settingsManager.rSetting(passive = new Setting("Passive", this, false, "BoxEspPassive"));
        OsirisMod.getInstance().settingsManager.rSetting(mobs = new Setting("Mobs", this, false, "BoxEspMobs"));
        OsirisMod.getInstance().settingsManager.rSetting(exp = new Setting("XpBottles", this, false, "BoxEspXpBottles"));
        OsirisMod.getInstance().settingsManager.rSetting(epearls = new Setting("Epearls", this, false, "BoxEspEpearls"));
        OsirisMod.getInstance().settingsManager.rSetting(crystals = new Setting("Crystals", this, false, "BoxEspCrystals"));
        OsirisMod.getInstance().settingsManager.rSetting(items = new Setting("Items", this, false, "BoxEspItems"));
        rSetting(orbs = new Setting("XpOrbs", this, false, "BoxEspXpOrbs"));
        OsirisMod.getInstance().settingsManager.rSetting(rainbow = new Setting("Rainbow", this, false, "BoxEspRainbow"));
        OsirisMod.getInstance().settingsManager.rSetting(r = new Setting("Red", this, 255, 1, 255, true, "BoxEspRed"));
        OsirisMod.getInstance().settingsManager.rSetting(g = new Setting("Green", this, 255, 1, 255, true, "BoxEspGreen"));
        OsirisMod.getInstance().settingsManager.rSetting(b = new Setting("Blue", this, 255, 1, 255, true, "BoxEspBlue"));
        OsirisMod.getInstance().settingsManager.rSetting(a = new Setting("Alpha", this, 50, 1, 255, true, "BoxEspAlpha"));
    }

    Setting players;
    Setting passive;
    Setting mobs;
    Setting exp;
    Setting epearls;
    Setting crystals;
    Setting items;
    Setting orbs;

    Setting rainbow;
    Setting r;
    Setting g;
    Setting b;
    Setting a;

    public void onWorldRender(RenderEvent event){
        Color c = new Color(r.getValInt(), g.getValInt(), b.getValInt(), a.getValInt());
        if(rainbow.getValBoolean()) c = new Color(Rainbow.getColor().getRed(), Rainbow.getColor().getGreen(), Rainbow.getColor().getBlue(), a.getValInt());
        Color enemy = new Color(255, 0, 0, a.getValInt());
        Color friend = new Color(0, 255, 255, a.getValInt());
        Color finalC = c;
        mc.world.loadedEntityList.stream()
                .filter(entity -> entity != mc.player)
                .forEach(e -> {
                    OsirisTessellator.prepare(GL11.GL_QUADS);
                    if(players.getValBoolean() && e instanceof EntityPlayer) {
                        if (Friends.isFriend(e.getName())) OsirisTessellator.drawBox(e.getRenderBoundingBox(), friend.getRGB(), GeometryMasks.Quad.ALL);
                        else OsirisTessellator.drawBox(e.getRenderBoundingBox(), enemy.getRGB(), GeometryMasks.Quad.ALL);
                    }
                    if(mobs.getValBoolean() && GlowESP.isMonster(e)){
                        OsirisTessellator.drawBox(e.getRenderBoundingBox(), finalC.getRGB(), GeometryMasks.Quad.ALL);
                    }
                    if(passive.getValBoolean() && GlowESP.isPassive(e)){
                        OsirisTessellator.drawBox(e.getRenderBoundingBox(), finalC.getRGB(), GeometryMasks.Quad.ALL);
                    }
                    if(exp.getValBoolean() && e instanceof EntityExpBottle){
                        OsirisTessellator.drawBox(e.getRenderBoundingBox(), finalC.getRGB(), GeometryMasks.Quad.ALL);
                    }
                    if(epearls.getValBoolean() && e instanceof EntityEnderPearl){
                        OsirisTessellator.drawBox(e.getRenderBoundingBox(), finalC.getRGB(), GeometryMasks.Quad.ALL);
                    }
                    if(crystals.getValBoolean() && e instanceof EntityEnderCrystal){
                        OsirisTessellator.drawBox(e.getRenderBoundingBox(), finalC.getRGB(), GeometryMasks.Quad.ALL);
                    }
                    if(items.getValBoolean() && e instanceof EntityItem){
                        OsirisTessellator.drawBox(e.getRenderBoundingBox(), finalC.getRGB(), GeometryMasks.Quad.ALL);
                    }
                    if(orbs.getValBoolean() && e instanceof EntityXPOrb){
                        OsirisTessellator.drawBox(e.getRenderBoundingBox(), finalC.getRGB(), GeometryMasks.Quad.ALL);
                    }
                    OsirisTessellator.release();
                });
    }
}
