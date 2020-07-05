package me.finz0.osiris.module.modules.chat;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.finz0.osiris.settings.Setting;
import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.friends.Friends;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;

public class BetterChat extends Module {
    public BetterChat() {
        super("BetterChat", Category.CHAT, "Improves chat");
    }

    public Setting clearBkg;
    Setting nameHighlight;
    Setting friendHighlight;

    public void setup(){
        clearBkg = new Setting("Clear", this, true, "BetterChatClear");
        OsirisMod.getInstance().settingsManager.rSetting(clearBkg);
        OsirisMod.getInstance().settingsManager.rSetting(nameHighlight = new Setting("NameHighlight", this, false, "BetterChatNameHighlight"));
        OsirisMod.getInstance().settingsManager.rSetting(friendHighlight = new Setting("FriendHighlight", this, false, "BetterChatFriendHighlight"));
    }

    @EventHandler
    private Listener<ClientChatReceivedEvent> chatReceivedEventListener = new Listener<>(event -> {
        if(mc.player == null) return;
        String name = mc.player.getName().toLowerCase();
        if(friendHighlight.getValBoolean()){
            if(!event.getMessage().getUnformattedText().startsWith("<"+mc.player.getName()+">")){
                Friends.getFriends().forEach(f -> {
                    if(event.getMessage().getUnformattedText().contains(f.getName())){
                        event.getMessage().setStyle(event.getMessage().getStyle().setColor(TextFormatting.AQUA));
                    }
                });
            }
        }
        if(nameHighlight.getValBoolean()){
            String s = ChatFormatting.GOLD + "" + ChatFormatting.BOLD + mc.player.getName() + ChatFormatting.RESET;
            Style style = event.getMessage().getStyle();
            if(!event.getMessage().getUnformattedText().startsWith("<"+mc.player.getName()+">") && event.getMessage().getUnformattedText().toLowerCase().contains(name)) {
                event.getMessage().getStyle().setParentStyle(style.setBold(true).setColor(TextFormatting.GOLD));
            }
        }
    });

    public void onEnable(){
        OsirisMod.EVENT_BUS.subscribe(this);
    }

    public void onDisable(){
        OsirisMod.EVENT_BUS.unsubscribe(this);
    }
}
