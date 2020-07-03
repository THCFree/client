package me.finz0.osiris.module.modules.misc;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.event.events.EntityUseTotemEvent;
import me.finz0.osiris.command.Command;
import me.finz0.osiris.event.events.PacketEvent;
import me.finz0.osiris.module.Module;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.concurrent.ConcurrentHashMap;
import java.util.HashMap;

public class TotemPopCounter extends Module {
    public TotemPopCounter() {
        super("TotemPopCounter", Category.MISC, "Broken");
    }

    private HashMap<String, Integer> playerList;
    private boolean isDead;
    @EventHandler
    public Listener<EntityUseTotemEvent> listListener;
    @EventHandler
    public Listener<PacketEvent.Receive> popListener;

    {

        this.playerList = new HashMap<String, Integer>();
        this.isDead = false;
        final int[] popCounter = {0};
        this.listListener = new Listener<EntityUseTotemEvent>(event -> {
            if (this.playerList == null) {
                this.playerList = new HashMap<String, Integer>();
            }
            if (this.playerList.get(event.getEntity().getName()) == null) {
                this.playerList.put(event.getEntity().getName(), 1);
                this.sendMessage(this.formatName2(event.getEntity().getName()) + " popped " + this.formatNumber(1) + " totem" + this.ending());
            } else if (this.playerList.get(event.getEntity().getName()) != null) {
                popCounter[0] = this.playerList.get(event.getEntity().getName());
                ++popCounter[0];
                this.playerList.put(event.getEntity().getName(), popCounter[0]);
                this.sendMessage(this.formatName2(event.getEntity().getName()) + " popped " + this.formatNumber(popCounter[0]) + " totems" + this.ending());
            }
            return;
        });
        final SPacketEntityStatus[] packet = new SPacketEntityStatus[1];
        final Entity[] entity = new Entity[1];
        this.popListener = new Listener<PacketEvent.Receive>(event -> {
            if (mc.player != null) {
                if (event.getPacket() instanceof SPacketEntityStatus) {
                    packet[0] = (SPacketEntityStatus) event.getPacket();
                    if (packet[0].getOpCode() == 35) {
                        entity[0] = packet[0].getEntity((World) mc.world);
                        if (this.selfCheck(entity[0].getName())) {
                            OsirisMod.EVENT_BUS.post(new EntityUseTotemEvent(entity[0]));
                        }
                    }
                }
            }
        });
    }


    @Override
    public void onUpdate() {
        if (!this.isDead && 0.0f >= mc.player.getHealth()) {
            this.sendMessage(this.formatName(mc.player.getName()) + " died and " + this.grammar(mc.player.getName()) + " pop list was reset");
            this.isDead = true;
            this.playerList.clear();
            return;
        }
        if (this.isDead && 0.0f < mc.player.getHealth()) {
            this.isDead = false;
        }
        for (final EntityPlayer player : mc.world.playerEntities) {
            if (0.0f >= player.getHealth() && this.selfCheck(player.getName()) && this.playerList.containsKey(player.getName())) {
                this.sendMessage(this.formatName(player.getName()) + " died after popping " + this.formatNumber(this.playerList.get(player.getName())) + " totems" + this.ending());
                this.playerList.remove(player.getName(), this.playerList.get(player.getName()));
            }
        }
    }

    private boolean selfCheck(final String name) {
        return !this.isDead && ((name.equalsIgnoreCase(mc.player.getName())) || !name.equalsIgnoreCase(mc.player.getName()));
    }

    private boolean isSelf(final String name) {
        return name.equalsIgnoreCase(mc.player.getName());
    }

    private String formatName(String name) {
        String extraText = "";
        if (this.isSelf(name)) {
            extraText = "";
            name = "I";
        }

        return extraText + ChatFormatting.RED + name + TextFormatting.RESET;
    }

    private String formatName2(String name) {
        String extraText = "";
        if (this.isSelf(name)) {
            extraText = "";
            name = "I";
        }

        return extraText + ChatFormatting.GREEN + name + TextFormatting.RESET;
    }

    private String grammar(final String name) {
        if (this.isSelf(name)) {
            return "my";
        }
        return "their";
    }

    private String ending() {

        return "";
    }



    private String formatNumber(final int message) {
        {
        }
        return ChatFormatting.RED + "" + message + TextFormatting.RESET;
    }

    private void sendMessage(final String message) {
        {
        }
        {
            Command.sendClientMessage(message);
        }
    }

    public void onEnable() {
        OsirisMod.EVENT_BUS.subscribe(this);
    }

    public void onDisable() {
        OsirisMod.EVENT_BUS.unsubscribe(this);
    }
}