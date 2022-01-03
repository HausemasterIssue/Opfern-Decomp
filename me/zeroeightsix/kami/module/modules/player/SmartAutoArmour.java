//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.module.modules.player;

import me.zeroeightsix.kami.module.*;
import me.zeroeightsix.kami.setting.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.renderer.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.inventory.*;
import java.util.*;

@Module.Info(name = "SmartAutoArmour", category = Module.Category.PLAYER)
public class SmartAutoArmour extends Module
{
    private Setting<Double> playerRange;
    private Setting<Double> crystalRange;
    
    public SmartAutoArmour() {
        this.playerRange = (Setting<Double>)this.register((Setting)Settings.doubleBuilder("Enenmy Range").withMinimum(0.0).withValue(8.0).build());
        this.crystalRange = (Setting<Double>)this.register((Setting)Settings.doubleBuilder("Crystal Range").withMinimum(0.0).withValue(8.0).build());
    }
    
    public void onUpdate() {
        for (final EntityPlayer e : SmartAutoArmour.mc.world.playerEntities) {
            if (SmartAutoArmour.mc.player.getDistance((Entity)e) > this.playerRange.getValue()) {
                return;
            }
        }
        for (final Entity e2 : SmartAutoArmour.mc.world.loadedEntityList) {
            if (e2 instanceof EntityEnderCrystal && SmartAutoArmour.mc.player.getDistance(e2) > this.crystalRange.getValue()) {
                return;
            }
        }
        if (SmartAutoArmour.mc.player.ticksExisted % 2 == 0) {
            return;
        }
        if (SmartAutoArmour.mc.currentScreen instanceof GuiContainer && !(SmartAutoArmour.mc.currentScreen instanceof InventoryEffectRenderer)) {
            return;
        }
        final int[] bestArmorSlots = new int[4];
        final int[] bestArmorValues = new int[4];
        for (int armorType = 0; armorType < 4; ++armorType) {
            final ItemStack oldArmor = SmartAutoArmour.mc.player.inventory.armorItemInSlot(armorType);
            if (oldArmor != null && oldArmor.getItem() instanceof ItemArmor) {
                bestArmorValues[armorType] = ((ItemArmor)oldArmor.getItem()).damageReduceAmount;
            }
            bestArmorSlots[armorType] = -1;
        }
        for (int slot = 0; slot < 36; ++slot) {
            final ItemStack stack = SmartAutoArmour.mc.player.inventory.getStackInSlot(slot);
            if (stack.getCount() <= 1) {
                if (stack != null) {
                    if (stack.getItem() instanceof ItemArmor) {
                        final ItemArmor armor = (ItemArmor)stack.getItem();
                        final int armorType2 = armor.armorType.ordinal() - 2;
                        if (armorType2 != 2 || !SmartAutoArmour.mc.player.inventory.armorItemInSlot(armorType2).getItem().equals(Items.ELYTRA)) {
                            final int armorValue = armor.damageReduceAmount;
                            if (armorValue > bestArmorValues[armorType2]) {
                                bestArmorSlots[armorType2] = slot;
                                bestArmorValues[armorType2] = armorValue;
                            }
                        }
                    }
                }
            }
        }
        for (int armorType = 0; armorType < 4; ++armorType) {
            int slot2 = bestArmorSlots[armorType];
            if (slot2 != -1) {
                final ItemStack oldArmor2 = SmartAutoArmour.mc.player.inventory.armorItemInSlot(armorType);
                if (oldArmor2 == null || oldArmor2 != ItemStack.EMPTY || SmartAutoArmour.mc.player.inventory.getFirstEmptyStack() != -1) {
                    if (slot2 < 9) {
                        slot2 += 36;
                    }
                    SmartAutoArmour.mc.playerController.windowClick(0, 8 - armorType, 0, ClickType.QUICK_MOVE, (EntityPlayer)SmartAutoArmour.mc.player);
                    SmartAutoArmour.mc.playerController.windowClick(0, slot2, 0, ClickType.QUICK_MOVE, (EntityPlayer)SmartAutoArmour.mc.player);
                    break;
                }
            }
        }
    }
}
