package me.finz0.osiris.module.modules.combat;

import me.finz0.osiris.module.Module;
import me.finz0.osiris.settings.Setting;
import me.finz0.osiris.util.BlockUtils;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.tileentity.TileEntityBed;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;

import java.util.Comparator;

public class BedAura extends Module {
    public BedAura() {
        super("BedAura", Category.COMBAT, "Right clicks beds");
        rSetting(range = new Setting("Range", this, 4.5, 0, 10, false, "BedAuraRange"));
        rSetting(rotate = new Setting("Rotate", this, true, "BedAuraRotate"));
        rSetting(dimensionCheck = new Setting("DimensionCheck", this, true, "BedAuraDimensionCheck"));
        rSetting(refill = new Setting("RefillHotbar", this, false, "BedAuraRefillHotbar"));
    }

    private Setting range;
    private Setting dimensionCheck;
    private Setting rotate;
    Setting refill;

    boolean moving = false;

    public void onUpdate() {
        if(refill.getValBoolean()) {
            //search for empty hotbar slots
            int slot = -1;
            for (int i = 0; i < 9; i++) {
                if (mc.player.inventory.getStackInSlot(i) == ItemStack.EMPTY) {
                    slot = i;
                    break;
                }
            }

            if (moving && slot != -1) {
                mc.playerController.windowClick(0, slot + 36, 0, ClickType.PICKUP, mc.player);
                moving = false;
                slot = -1;
            }

            if (slot != -1 && !(mc.currentScreen instanceof GuiContainer) && mc.player.inventory.getItemStack().isEmpty()) {
                //search for beds in inventory
                int t = -1;
                for (int i = 0; i < 45; i++) {
                    if (mc.player.inventory.getStackInSlot(i).getItem() == Items.BED && i >= 9) {
                        t = i;
                        break;
                    }
                }
                //click bed item
                if (t != -1) {
                    mc.playerController.windowClick(0, t, 0, ClickType.PICKUP, mc.player);
                    moving = true;
                }
            }
        }

        mc.world.loadedTileEntityList.stream()
                .filter(e -> e instanceof TileEntityBed)
                .filter(e -> mc.player.getDistance(e.getPos().getX(), e.getPos().getY(), e.getPos().getZ()) <= range.getValDouble())
                .sorted(Comparator.comparing(e -> mc.player.getDistance(e.getPos().getX(), e.getPos().getY(), e.getPos().getZ())))
                .forEach(bed -> {

                    if(dimensionCheck.getValBoolean() && mc.player.dimension == 0) return;

                    if(rotate.getValBoolean()) BlockUtils.faceVectorPacketInstant(new Vec3d(bed.getPos().add(0.5, 0.5, 0.5)));
                    mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(bed.getPos(), EnumFacing.UP, EnumHand.MAIN_HAND, 0, 0, 0));

                });
    }
}
