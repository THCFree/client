package me.finz0.osiris.module.modules.combat;

import me.finz0.osiris.module.Module;
import me.finz0.osiris.module.ModuleManager;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemBow;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.math.BlockPos;

public class BowSpam extends Module {
    public BowSpam() {
        super("BowSpam", Category.COMBAT);
    }

    public void onUpdate() {
    	if(mc.player.inventory.getCurrentItem().getItem() instanceof ItemBow) {
    		if(mc.player.isHandActive() && mc.player.getItemInUseMaxCount() >= 3) {
    			mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, mc.player.getHorizontalFacing()));
    			mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(mc.player.getActiveHand()));
    			mc.player.stopActiveHand();
    		}
    	}
    }
}

