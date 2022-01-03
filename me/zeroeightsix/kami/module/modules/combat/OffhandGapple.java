//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.module.modules.combat;

import me.zeroeightsix.kami.setting.*;
import me.zeroeightsix.kami.module.*;
import net.minecraft.init.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import java.util.function.*;

@Module.Info(name = "OffhandGapple", category = Module.Category.COMBAT, description = "Auto Offhand Gapple")
public class OffhandGapple extends Module
{
    private int gapples;
    private boolean moving;
    private boolean returnI;
    private int oldSlot;
    Item item;
    private Setting<Boolean> soft;
    private Setting<Boolean> totemOnDisable;
    private Setting<Double> health;
    
    public OffhandGapple() {
        this.moving = false;
        this.returnI = false;
        this.oldSlot = -1;
        this.soft = (Setting<Boolean>)this.register((Setting)Settings.b("Soft", false));
        this.totemOnDisable = (Setting<Boolean>)this.register((Setting)Settings.b("TotemOnDisable", true));
        this.health = (Setting<Double>)this.register((Setting)Settings.doubleBuilder("TotemHealth").withMinimum(0.0).withValue(6.0).withMaximum(36.0).build());
    }
    
    public void onEnable() {
        this.oldSlot = -1;
        if (ModuleManager.getModuleByName("AutoTotem").isEnabled()) {
            ModuleManager.getModuleByName("AutoTotem").disable();
        }
    }
    
    public void onDisable() {
        if (!this.totemOnDisable.getValue()) {
            return;
        }
        final AutoTotem autoTotem = (AutoTotem)ModuleManager.getModuleByName("AutoTotem");
        autoTotem.disableSoft();
        if (autoTotem.isDisabled()) {
            autoTotem.enable();
        }
    }
    
    public void onUpdate() {
        if (this.shouldTotem()) {
            this.item = Items.TOTEM_OF_UNDYING;
        }
        else {
            this.item = Items.GOLDEN_APPLE;
        }
        if (OffhandGapple.mc.currentScreen instanceof GuiContainer) {
            return;
        }
        if (this.returnI) {
            int t = -1;
            if (this.oldSlot == -1) {
                for (int i = 0; i < 45; ++i) {
                    if (OffhandGapple.mc.player.inventory.getStackInSlot(i).isEmpty) {
                        t = i;
                        break;
                    }
                }
                if (t == -1) {
                    return;
                }
            }
            else {
                t = this.oldSlot;
            }
            OffhandGapple.mc.playerController.windowClick(0, (t < 9) ? (t + 36) : t, 0, ClickType.PICKUP, (EntityPlayer)OffhandGapple.mc.player);
            this.returnI = false;
        }
        this.gapples = OffhandGapple.mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == this.item).mapToInt(ItemStack::getCount).sum();
        if (OffhandGapple.mc.player.getHeldItemOffhand().getItem() == this.item) {
            ++this.gapples;
        }
        else {
            if (this.soft.getValue() && !OffhandGapple.mc.player.getHeldItemOffhand().isEmpty) {
                return;
            }
            if (this.moving) {
                OffhandGapple.mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, (EntityPlayer)OffhandGapple.mc.player);
                this.moving = false;
                if (!OffhandGapple.mc.player.inventory.itemStack.isEmpty()) {
                    this.returnI = true;
                }
                return;
            }
            if (OffhandGapple.mc.player.inventory.itemStack.isEmpty()) {
                if (this.gapples == 0) {
                    return;
                }
                int t = -1;
                for (int i = 9; i < 45; ++i) {
                    if (OffhandGapple.mc.player.inventory.getStackInSlot(i).getItem() == this.item) {
                        t = i;
                        break;
                    }
                }
                if (t == -1) {
                    for (int i = 0; i < 10; ++i) {
                        if (OffhandGapple.mc.player.inventory.getStackInSlot(i).getItem() == this.item) {
                            t = i;
                            break;
                        }
                    }
                    if (t == -1) {
                        return;
                    }
                }
                this.oldSlot = t;
                OffhandGapple.mc.playerController.windowClick(0, (t < 9) ? (t + 36) : t, 0, ClickType.PICKUP, (EntityPlayer)OffhandGapple.mc.player);
                this.moving = true;
            }
            else if (!this.soft.getValue()) {
                int t = -1;
                for (int i = 0; i < 45; ++i) {
                    if (OffhandGapple.mc.player.inventory.getStackInSlot(i).isEmpty) {
                        t = i;
                        break;
                    }
                }
                if (t == -1) {
                    return;
                }
                OffhandGapple.mc.playerController.windowClick(0, (t < 9) ? (t + 36) : t, 0, ClickType.PICKUP, (EntityPlayer)OffhandGapple.mc.player);
            }
        }
    }
    
    public String getHudInfo() {
        return String.valueOf(this.gapples);
    }
    
    private boolean shouldTotem() {
        final boolean hp = OffhandGapple.mc.player.getHealth() + OffhandGapple.mc.player.getAbsorptionAmount() <= this.health.getValue();
        return hp;
    }
}
