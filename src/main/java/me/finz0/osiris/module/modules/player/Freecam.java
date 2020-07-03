package me.finz0.osiris.module.modules.player;

import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.event.events.PacketEvent;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.network.play.client.CPacketPlayer;

public class Freecam extends Module {
    public Freecam() {
        super("Freecam", Category.PLAYER);
        rSetting(speed = new Setting("Speed", this, 1, 0.1, 10, false, "FreecamSpeed"));
    }

    Setting speed;

    EntityOtherPlayerMP entity;
    boolean oldOnGround;
    boolean oldNoClip;
    double x;
    double y;
    double z;


    public void onEnable(){
        disable();
        OsirisMod.EVENT_BUS.subscribe(this);
        entity = new EntityOtherPlayerMP(mc.world, mc.getSession().getProfile());
        entity.copyLocationAndAnglesFrom(mc.player);
        entity.rotationYaw = mc.player.rotationYaw;
        entity.rotationYawHead = mc.player.rotationYawHead;
        mc.world.addEntityToWorld(696984837, entity);

        oldOnGround = mc.player.onGround;
        oldNoClip = mc.player.noClip;
        x = mc.player.posX;
        y = mc.player.posY;
        z = mc.player.posZ;
    }

    public void onDisable(){
        /*
        OsirisMod.EVENT_BUS.unsubscribe(this);
        if (this.entity != null) {
            mc.world.removeEntity(entity);
        }

        mc.player.onGround = oldOnGround;
        mc.player.noClip = oldNoClip;
        mc.player.posX = x;
        mc.player.posY = y;
        mc.player.posZ = z;
        */
    }

    public void onUpdate(){
        //mc.player.onGround = true;
        mc.player.noClip = true;
        if(mc.player.movementInput.jump){
            mc.player.motionY = speed.getValDouble() / 10;
        } else if(mc.player.movementInput.sneak){
            mc.player.motionY = -(speed.getValDouble() / 10);
        } else {
            mc.player.motionY = 0;
        }

        if (mc.player.movementInput.moveStrafe != 0 || mc.player.movementInput.moveForward != 0) {
            mc.player.motionX = speed.getValDouble();
            mc.player.motionZ = speed.getValDouble();
        } else {
            mc.player.motionX = 0;
            mc.player.motionZ = 0;
        }
    }

    @EventHandler
    private Listener<PacketEvent.Send> packetSendListener = new Listener<>(event -> {
        if(event.getPacket() instanceof CPacketPlayer){
            event.cancel();
        }
    });



}
