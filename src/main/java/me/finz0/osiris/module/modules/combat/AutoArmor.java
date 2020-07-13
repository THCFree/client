package me.finz0.osiris.module.modules.combat;

import me.finz0.osiris.module.Module;
import me.finz0.osiris.settings.Setting;
import me.finz0.osiris.util.Timer;
import me.finz0.osiris.OsirisMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class AutoArmor extends Module {
    public AutoArmor() {
        super("AutoArmor", Category.COMBAT, "Automatically Equip Armor");
        OsirisMod.getInstance().settingsManager.rSetting(delay = new Setting("Delay", this, 50.0, 0.0, 1000.0, true, "delay"));
        OsirisMod.getInstance().settingsManager.rSetting(curse = new Setting("NoCurse", this, false, "curse"));
	}
	Setting delay;
	Setting curse;
	private Timer timer = new Timer();
    
    public void onUpdate() {
        final Minecraft mc = Minecraft.getMinecraft();
        if (mc.currentScreen instanceof GuiInventory) {
            return;
        }

        final ItemStack helm = mc.player.inventoryContainer.getSlot(5).getStack();

        if (helm.getItem() == Items.AIR) {
            final int slot = this.findArmorSlot(EntityEquipmentSlot.HEAD);

            if (slot != -1) {
                    this.clickSlot(slot, 0, ClickType.QUICK_MOVE);
            }
        }

        final ItemStack chest = mc.player.inventoryContainer.getSlot(6).getStack();

        if (chest.getItem() == Items.AIR) {
            final int slot = this.findArmorSlot(EntityEquipmentSlot.CHEST);

            if (slot != -1) {
                    this.clickSlot(slot, 0, ClickType.QUICK_MOVE);
            }
        }

        final ItemStack legging = mc.player.inventoryContainer.getSlot(7).getStack();

        if (legging.getItem() == Items.AIR) {
            final int slot = this.findArmorSlot(EntityEquipmentSlot.LEGS);

            if (slot != -1) {
                this.clickSlot(slot, 0, ClickType.QUICK_MOVE);
            }
        }

        final ItemStack feet = mc.player.inventoryContainer.getSlot(8).getStack();

        if (feet.getItem() == Items.AIR) {
            final int slot = this.findArmorSlot(EntityEquipmentSlot.FEET);

            if (slot != -1) {
                this.clickSlot(slot, 0, ClickType.QUICK_MOVE);
            }
        }
    }
    

    private void clickSlot(int slot, int mouse, ClickType type) {
        if (this.timer.passed(this.delay.getValDouble())) {
            Minecraft.getMinecraft().playerController.windowClick(Minecraft.getMinecraft().player.inventoryContainer.windowId, slot, mouse, type, Minecraft.getMinecraft().player);
            this.timer.reset();
        }
    }

    private int findArmorSlot(EntityEquipmentSlot type) {
        int slot = -1;
        float damage = 0;

        for (int i = 9; i < 45; i++) {
            final ItemStack s = Minecraft.getMinecraft().player.inventoryContainer.getSlot(i).getStack();
            if (s != null && s.getItem() != Items.AIR) {

                if (s.getItem() instanceof ItemArmor) {
                    final ItemArmor armor = (ItemArmor) s.getItem();
                    if (armor.armorType == type) {
                        final float currentDamage = (armor.damageReduceAmount + EnchantmentHelper.getEnchantmentLevel(Enchantments.PROTECTION, s));

                        final boolean cursed = this.curse.getValBoolean() ? (EnchantmentHelper.hasBindingCurse(s)) : false;

                        if (currentDamage > damage && !cursed) {
                            damage = currentDamage;
                            slot = i;
                        }
                    }
                }
            }
        }

        return slot;
    }

}
