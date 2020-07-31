package me.finz0.osiris.module.modules.misc;

import com.mojang.authlib.GameProfile;
import me.finz0.osiris.module.Module;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import java.util.UUID;

public class fakeplayer extends Module {
    public fakeplayer() {
        super("FakePlayer", Category.MISC, "Fake Player");
    }

    public void onEnable() {

        if (mc.world == null)

            return;

        EntityOtherPlayerMP fakePlayer = new EntityOtherPlayerMP((World)mc.world, new GameProfile(UUID.fromString("f6ceda4b-00d9-46f9-be46-ae994c613055"), "THCFRee"));

        fakePlayer.copyLocationAndAnglesFrom((Entity)mc.player);

        fakePlayer.rotationYawHead = mc.player.rotationYawHead;

        mc.world.addEntityToWorld(-100, (Entity)fakePlayer);

    }



    public void onDisable() {

        mc.world.removeEntityFromWorld(-100);

    }

}

