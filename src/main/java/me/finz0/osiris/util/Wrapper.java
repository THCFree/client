package me.finz0.osiris.util;


 import me.finz0.osiris.OsirisMod;
 import me.finz0.osiris.util.font.CFontRenderer;
 import net.minecraft.client.Minecraft;
 import net.minecraft.client.entity.EntityPlayerSP;
 import net.minecraft.world.World;
 import org.lwjgl.input.Keyboard;






 public class Wrapper
 {
	     final static Minecraft mc = Minecraft.getMinecraft();
    
    public static Minecraft GetMC()
    {
        return mc;
    }

    public static EntityPlayerSP GetPlayer()
    {
        return mc.player;
    }
   private static CFontRenderer fontRenderer;

   public static void init() { fontRenderer = OsirisMod.fontRenderer; }


   public static Minecraft getMinecraft() { return Minecraft.getMinecraft(); }


   public static EntityPlayerSP getPlayer() { return (getMinecraft()).player; }


   public static World getWorld() { return (getMinecraft()).world; }


   public static int getKey(String keyname) { return Keyboard.getKeyIndex(keyname.toUpperCase()); }



   public static CFontRenderer getFontRenderer() { return fontRenderer; }}
