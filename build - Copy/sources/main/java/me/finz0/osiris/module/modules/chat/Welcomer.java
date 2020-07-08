package me.finz0.osiris.module.modules.chat;

import me.finz0.osiris.settings.Setting;
import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.command.Command;
import me.finz0.osiris.event.events.PlayerJoinEvent;
import me.finz0.osiris.event.events.PlayerLeaveEvent;
import me.finz0.osiris.module.Module;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;

public class Welcomer extends Module {
    public Welcomer() {
        super("Welcomer", Category.CHAT, "Sends a message when someone joins the server");
        OsirisMod.getInstance().settingsManager.rSetting(publicS = new Setting("Public", this, false, "WelcomerPublicMode"));
    }
    Setting publicS;

    @EventHandler
    private Listener<PlayerJoinEvent> listener1 = new Listener<>(event -> {
        if(publicS.getValBoolean()) mc.player.sendChatMessage(event.getName() + " joined the game");
        else Command.sendClientMessage(event.getName() + " joined the game");
    });

    @EventHandler
    private Listener<PlayerLeaveEvent> listener2 = new Listener<>(event -> {
        if(publicS.getValBoolean()) mc.player.sendChatMessage(event.getName() + " left the game");
        else Command.sendClientMessage(event.getName() + " left the game");
    });

    public void onEnable(){
        OsirisMod.EVENT_BUS.subscribe(this);
    }

    public void onDisable(){
        OsirisMod.EVENT_BUS.unsubscribe(this);
    }
}
