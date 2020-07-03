package me.finz0.osiris.module.modules.combat;

import me.finz0.osiris.module.Module;
import me.finz0.osiris.module.ModuleManager;
import me.finz0.osiris.settings.Setting;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.List;
import java.util.stream.Collectors;

public class OffHandGap extends Module {
    public OffHandGap() {
        super("OffHandGap", Category.COMBAT, "Puts gapples in your offhand");
    }

    private Item item;
    public int crystals;
    private int totems;
    public int gapples;
    boolean moving = false;
    boolean returnI = false;
    public Setting mode;
    Setting soft;
    Setting Gappleamount;
    private int totalTicksRunning = 0;
    private int playerHotbarSlot = -1;
    private int lastHotbarSlot = -1;
    private int offsetStep = 0;
    private int delayStep = 0;
    private boolean firstRun;
    private Vec3d playerPos;
    private boolean isSneaking = false;
    private Setting crystalCheck;
    private Setting tickDelay;
    private Setting health;
    private Setting totemdisable;
    private Setting autoCenter;
    private Setting announceUsage2;
    private Setting timeoutTicks;

    public void setup() {
        health = new Setting("Health", this, 10, 0, 36, false, "Health");
        crystalCheck = new Setting("CrystalCheck", this, true, "CrystalCheck");
		totemdisable = new Setting("TotemDisable", this, true, "TotDisable");


    }

    public void onEnable() {
        if (totemdisable.getValBoolean()) {
            ModuleManager.getModuleByName("AutoTotem").disable();
        }
    }


    public void onDisable() {
        if (totemdisable.getValBoolean()) {
            ModuleManager.getModuleByName("AutoTotem").enable();
        }
}

    @Override
    public String getHudInfo() {
        return String.valueOf("\u00A78[\u00A7r"+gapples+"\u00A78]");
    }

    @Override
    public void onUpdate() {
        this.item = Items.GOLDEN_APPLE;
        if (mc.currentScreen instanceof GuiContainer) {
            return;
        }
        if (this.returnI) {
            int t = -1;
            for (int i = 0; i < 45; ++i) {
                if (mc.player.inventory.getStackInSlot(i).isEmpty()) {
                    t = i;
                    break;
                }
            }
            if (t == -1) {
                return;
            }
            mc.playerController.windowClick(0, (t < 9) ? (t + 36) : t, 0, ClickType.PICKUP, (EntityPlayer) mc.player);
            this.returnI = false;
        }
        this.totems = mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.TOTEM_OF_UNDYING).mapToInt(ItemStack::getCount).sum();
        this.gapples = mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == this.item).mapToInt(ItemStack::getCount).sum();
        if (this.shouldTotem() && mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING) {
            ++this.totems;
        }
        else if (!this.shouldTotem() && mc.player.getHeldItemOffhand().getItem() == this.item) {
            this.gapples += mc.player.getHeldItemOffhand().getCount();
        }
        else {
            if (this.moving) {
                mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, (EntityPlayer) mc.player);
                this.moving = false;
                this.returnI = true;
                return;
            }
            if (mc.player.inventory.getItemStack().isEmpty()) {
                if (!this.shouldTotem() && mc.player.getHeldItemOffhand().getItem() == this.item) {
                    return;
                }
                if (this.shouldTotem() && mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING) {
                    return;
                }
                if (!this.shouldTotem()) {
                    if (this.gapples == 0) {
                        return;
                    }
                    int t = -1;
                    for (int i = 0; i < 45; ++i) {
                        if (mc.player.inventory.getStackInSlot(i).getItem() == this.item) {
                            t = i;
                            break;
                        }
                    }
                    if (t == -1) {
                        return;
                    }
                    mc.playerController.windowClick(0, (t < 9) ? (t + 36) : t, 0, ClickType.PICKUP, (EntityPlayer) mc.player);
                    this.moving = true;
                }
                else {
                    if (this.totems == 0) {
                        return;
                    }
                    int t = -1;
                    for (int i = 0; i < 45; ++i) {
                        if (mc.player.inventory.getStackInSlot(i).getItem() == Items.TOTEM_OF_UNDYING) {
                            t = i;
                            break;
                        }
                    }
                    if (t == -1) {
                        return;
                    }
                    mc.playerController.windowClick(0, (t < 9) ? (t + 36) : t, 0, ClickType.PICKUP, (EntityPlayer) mc.player);
                    this.moving = true;
                }
            }
            else {
                int t = -1;
                for (int i = 0; i < 45; ++i) {
                    if (mc.player.inventory.getStackInSlot(i).isEmpty()) {
                        t = i;
                        break;
                    }
                }
                if (t == -1) {
                    return;
                }
                mc.playerController.windowClick(0, (t < 9) ? (t + 36) : t, 0, ClickType.PICKUP, (EntityPlayer) mc.player);
            }
        }
    }

    private boolean shouldTotem() {
        final boolean hp = mc.player.getHealth() + mc.player.getAbsorptionAmount() <= this.health.getValDouble();
        final boolean endcrystal = !this.isCrystalsAABBEmpty();
        final boolean totemCount = this.totems > 0 || this.moving || !mc.player.inventory.getItemStack().isEmpty();
        if (crystalCheck.getValBoolean()) {
            return (hp || endcrystal) && totemCount;
        }
        return hp && totemCount;
    }

    private boolean isEmpty(final BlockPos pos) {
        final List<Entity> crystalsInAABB = (List<Entity>) mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(pos)).stream().filter(e -> e instanceof EntityEnderCrystal).collect(Collectors.toList());
        return crystalsInAABB.isEmpty();
    }

    private boolean isCrystalsAABBEmpty() {
        return this.isEmpty(mc.player.getPosition().add(1, 0, 0)) && this.isEmpty(mc.player.getPosition().add(-1, 0, 0)) && this.isEmpty(mc.player.getPosition().add(0, 0, 1)) && this.isEmpty(mc.player.getPosition().add(0, 0, -1)) && this.isEmpty(mc.player.getPosition());
    }
}
