//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.module.modules.combat;

import me.zeroeightsix.kami.module.*;
import me.zeroeightsix.kami.setting.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import me.zeroeightsix.kami.util.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.block.*;

@Module.Info(name = "HotbarReplenish", description = "Replenishes on items if you're low", category = Module.Category.COMBAT)
public class HotbarReplenish extends Module
{
    private Setting<Integer> threshold;
    private Setting<Integer> tickDelay;
    private int delayStep;
    
    public HotbarReplenish() {
        this.threshold = (Setting<Integer>)this.register((Setting)Settings.integerBuilder("Threshold").withMinimum(1).withValue(32).withMaximum(63).build());
        this.tickDelay = (Setting<Integer>)this.register((Setting)Settings.integerBuilder("TickDelay").withMinimum(1).withValue(2).withMaximum(10).build());
        this.delayStep = 0;
    }
    
    private static Map<Integer, ItemStack> getInventory() {
        return getInventorySlots(9, 35);
    }
    
    private static Map<Integer, ItemStack> getHotbar() {
        return getInventorySlots(36, 44);
    }
    
    private static Map<Integer, ItemStack> getInventorySlots(int current, final int last) {
        final HashMap<Integer, ItemStack> fullInventorySlots = new HashMap<Integer, ItemStack>();
        while (current <= last) {
            fullInventorySlots.put(current, (ItemStack)HotbarReplenish.mc.player.inventoryContainer.getInventory().get(current));
            ++current;
        }
        return fullInventorySlots;
    }
    
    public void onUpdate() {
        if (HotbarReplenish.mc.player == null) {
            return;
        }
        if (HotbarReplenish.mc.currentScreen instanceof GuiContainer) {
            return;
        }
        if (this.delayStep < this.tickDelay.getValue()) {
            ++this.delayStep;
            return;
        }
        this.delayStep = 0;
        final Pair<Integer, Integer> slots = this.findReplenishableHotbarSlot();
        if (slots == null) {
            return;
        }
        final int inventorySlot = slots.getKey();
        final int hotbarSlot = slots.getValue();
        HotbarReplenish.mc.playerController.windowClick(0, inventorySlot, 0, ClickType.PICKUP, (EntityPlayer)HotbarReplenish.mc.player);
        HotbarReplenish.mc.playerController.windowClick(0, hotbarSlot, 0, ClickType.PICKUP, (EntityPlayer)HotbarReplenish.mc.player);
        HotbarReplenish.mc.playerController.windowClick(0, inventorySlot, 0, ClickType.PICKUP, (EntityPlayer)HotbarReplenish.mc.player);
    }
    
    private Pair<Integer, Integer> findReplenishableHotbarSlot() {
        Pair<Integer, Integer> returnPair = null;
        for (final Map.Entry<Integer, ItemStack> hotbarSlot : getHotbar().entrySet()) {
            final ItemStack stack = hotbarSlot.getValue();
            if (!stack.isEmpty && stack.getItem() != Items.AIR && stack.isStackable() && stack.stackSize < stack.getMaxStackSize() && stack.stackSize <= this.threshold.getValue()) {
                final int inventorySlot;
                if ((inventorySlot = this.findCompatibleInventorySlot(stack)) == -1) {
                    continue;
                }
                returnPair = new Pair<Integer, Integer>(inventorySlot, hotbarSlot.getKey());
            }
        }
        return returnPair;
    }
    
    private int findCompatibleInventorySlot(final ItemStack hotbarStack) {
        int inventorySlot = -1;
        int smallestStackSize = 999;
        for (final Map.Entry<Integer, ItemStack> entry : getInventory().entrySet()) {
            final ItemStack inventoryStack = entry.getValue();
            if (!inventoryStack.isEmpty && inventoryStack.getItem() != Items.AIR && this.isCompatibleStacks(hotbarStack, inventoryStack)) {
                final int currentStackSize;
                if (smallestStackSize <= (currentStackSize = ((ItemStack)HotbarReplenish.mc.player.inventoryContainer.getInventory().get((int)entry.getKey())).stackSize)) {
                    continue;
                }
                smallestStackSize = currentStackSize;
                inventorySlot = entry.getKey();
            }
        }
        return inventorySlot;
    }
    
    private boolean isCompatibleStacks(final ItemStack stack1, final ItemStack stack2) {
        if (!stack1.getItem().equals(stack2.getItem())) {
            return false;
        }
        if (stack1.getItem() instanceof ItemBlock && stack2.getItem() instanceof ItemBlock) {
            final Block block1 = ((ItemBlock)stack1.getItem()).getBlock();
            final Block block2 = ((ItemBlock)stack2.getItem()).getBlock();
            if (!block1.material.equals(block2.material)) {
                return false;
            }
        }
        return stack1.getDisplayName().equals(stack2.getDisplayName()) && stack1.getItemDamage() == stack2.getItemDamage();
    }
}
