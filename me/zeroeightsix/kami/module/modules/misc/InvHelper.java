//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.module.modules.misc;

import me.zeroeightsix.kami.module.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;

@Module.Info(name = "InvHelper", category = Module.Category.MISC, description = "Prevents some inv desync")
public class InvHelper extends Module
{
    public void onUpdate() {
        if (!(InvHelper.mc.currentScreen instanceof GuiContainer) && !InvHelper.mc.player.inventory.itemStack.isEmpty()) {
            final int slot = InvHelper.mc.player.inventory.getFirstEmptyStack();
            if (slot != -1) {
                InvHelper.mc.playerController.windowClick(0, (slot < 9) ? (slot + 36) : slot, 0, ClickType.PICKUP, (EntityPlayer)InvHelper.mc.player);
            }
        }
    }
}
