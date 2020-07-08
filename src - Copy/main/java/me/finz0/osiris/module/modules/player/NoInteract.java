package me.finz0.osiris.module.modules.player;

import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.event.events.PacketEvent;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.block.BlockContainer;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.math.BlockPos;

public class NoInteract extends Module {
    public NoInteract() {
        super("NoInteract", Category.PLAYER, "Prevents opening containers");
        rSetting(containerOnly = new Setting("ContainerOnly", this, false, "NoIteractContainerOnly"));
    }

    private Setting containerOnly;
    private boolean shouldStop = false;

    @EventHandler
    private Listener<PacketEvent.Send> sendListener = new Listener<>(event -> {
        if(event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock && !mc.player.isSneaking()) {

            CPacketPlayerTryUseItemOnBlock packet = ((CPacketPlayerTryUseItemOnBlock) event.getPacket());
            BlockPos pos = packet.getPos();

            if(containerOnly.getValBoolean() && !(mc.world.getBlockState(pos).getBlock() instanceof BlockContainer)) return;

            mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SNEAKING));
            shouldStop = true;
        }
    });

    @EventHandler
    private Listener<PacketEvent.PostSend> postSendListener = new Listener<>(event -> {
        if(event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock && shouldStop) {

            CPacketPlayerTryUseItemOnBlock packet = ((CPacketPlayerTryUseItemOnBlock) event.getPacket());
            BlockPos pos = packet.getPos();

            if(containerOnly.getValBoolean() && !(mc.world.getBlockState(pos).getBlock() instanceof BlockContainer)) return;

            mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            shouldStop = false;
        }
    });

    public void onEnable(){
        OsirisMod.EVENT_BUS.subscribe(this);
    }

    public void onDisable(){
        OsirisMod.EVENT_BUS.unsubscribe(this);
    }

}
