package me.finz0.osiris.mixin.mixins;

import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.event.events.GuiScreenDisplayedEvent;
import me.finz0.osiris.module.ModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.gui.*;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//this mixin randomly causes crash at startup sometimes idk why somebody help
@Mixin(value = Minecraft.class, priority = 9999)
public class MixinMinecraft {

    @Inject(method = "displayGuiScreen", at = @At("HEAD"))
    private void displayGuiScreen(GuiScreen guiScreenIn, CallbackInfo info) {
            GuiScreenDisplayedEvent screenEvent = new GuiScreenDisplayedEvent(guiScreenIn);
            OsirisMod.EVENT_BUS.post(screenEvent);
    }

    //credit cookiedragon234 this is very epic
    @Redirect(method = "sendClickBlockToController", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;isHandActive()Z"))
    private boolean isHandActive(EntityPlayerSP player){
        if(ModuleManager.isModuleEnabled("BreakTweaks")) return false;
        return Minecraft.getMinecraft().player.isHandActive();
    }

    @Redirect(method = "rightClickMouse", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/PlayerControllerMP;getIsHittingBlock()Z"))
    private boolean isHittingBlock(PlayerControllerMP playerControllerMP){
        if(ModuleManager.isModuleEnabled("BreakTweaks")) return false;
        return Minecraft.getMinecraft().playerController.getIsHittingBlock();
    }
}
