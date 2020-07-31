package me.finz0.osiris.module.modules.render;





import java.awt.Color;

import java.util.ArrayList;

import java.util.Collection;

import java.util.Iterator;

import java.util.List;

import java.util.stream.Collectors;


import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.event.events.RenderEvent;
import me.finz0.osiris.friends.Friends;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.module.ModuleManager;
import me.finz0.osiris.module.modules.combat.AutoCrystalPlus;
import me.finz0.osiris.settings.Setting;
import me.finz0.osiris.util.OsirisTessellator;
import net.minecraft.entity.player.EntityPlayer;

import net.minecraft.init.Blocks;

import net.minecraft.util.math.BlockPos;
import org.lwjgl.opengl.GL11;


public class CityESP extends Module {

    public CityESP() {
        super("CityESP", Category.RENDER, "CityESP");
    }
    Setting red;
    Setting green;
    Setting blue;
    Setting alpha;
    public void setup(){
        red = new Setting("EspRed", this, 200, 0, 255, true, "EspRed");
        OsirisMod.getInstance().settingsManager.rSetting(red);
        green = new Setting("EspGreen", this, 50, 0, 255, true, "EspGreen");
        OsirisMod.getInstance().settingsManager.rSetting(green);
        blue = new Setting("EspBlue", this, 200, 0, 255, true, "EspBlue");
        OsirisMod.getInstance().settingsManager.rSetting(blue);
        alpha = new Setting("EspAlpha", this, 50, 0, 255, true, "EspAlpha");
        OsirisMod.getInstance().settingsManager.rSetting(alpha);
    }

    private final BlockPos[] surroundOffset = new BlockPos[]{new BlockPos(0, 0, -1), new BlockPos(1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(-1, 0, 0)};



    public void onWorldRender(RenderEvent event) {

        if (mc.world != null) {

            HoleESP h = (HoleESP) ModuleManager.getModuleByName("HoleESP");

            AutoCrystalPlus a = (AutoCrystalPlus) ModuleManager.getModuleByName("AutoCrystalPlus");

            Color c = new Color((Integer)this.red.getValInt(), (Integer)this.green.getValInt(), (Integer)this.blue.getValInt(), (Integer)this.alpha.getValInt());

            List entities = new ArrayList();

            entities.addAll((Collection)mc.world.playerEntities.stream().filter((entityPlayer) -> {

                return !Friends.isFriend(entityPlayer.getName());

            }).collect(Collectors.toList()));

            Iterator var6 = entities.iterator();



            while(var6.hasNext()) {

                EntityPlayer e = (EntityPlayer)var6.next();

                int i = 0;

                BlockPos[] var9 = this.surroundOffset;

                int var10 = var9.length;



                for(int var11 = 0; var11 < var10; ++var11) {

                    BlockPos add = var9[var11];

                    ++i;

                    BlockPos o = (new BlockPos(e.getPositionVector().x, e.getPositionVector().y, e.getPositionVector().z)).add(add.getX(), add.getY(), add.getZ());

                    if (mc.world.getBlockState(o).getBlock() == Blocks.OBSIDIAN) {

                        OsirisTessellator.prepare(7);

                        if (i == 1 && a.canPlaceCrystal(o.north(1).down())) {

                            OsirisTessellator.drawBox((float) o.getX(), (float) o.getY(), (float) o.getZ(), c.getRGB(), 4);

                        }



                        if (i == 2 && a.canPlaceCrystal(o.east(1).down())) {

                            OsirisTessellator.drawBox((float) o.getX(), (float) o.getY(), (float) o.getZ(), c.getRGB(), 32);

                        }



                        if (i == 3 && a.canPlaceCrystal(o.south(1).down())) {

                            OsirisTessellator.drawBox((float) o.getX(), (float) o.getY(), (float) o.getZ(), c.getRGB(), 8);

                        }



                        if (i == 4 && a.canPlaceCrystal(o.west(1).down())) {

                            OsirisTessellator.drawBox((float) o.getX(), (float) o.getY(), (float) o.getZ(), c.getRGB(), 16);

                        }



                        OsirisTessellator.release();

                    }

                }

            }



        }

    }

}

