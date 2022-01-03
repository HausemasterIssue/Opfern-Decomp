//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.module.modules.combat;

import me.zeroeightsix.kami.module.*;
import me.zeroeightsix.kami.setting.*;
import com.mojang.realmsclient.gui.*;
import me.zeroeightsix.kami.command.*;
import net.minecraft.init.*;
import net.minecraft.item.*;

@Module.Info(name = "GappleSwap", category = Module.Category.COMBAT)
public class GappleSwap extends Module
{
    private Setting<Boolean> infoMessage;
    
    public GappleSwap() {
        this.infoMessage = (Setting<Boolean>)this.register((Setting)Settings.b("Toggle Message"));
    }
    
    protected void onEnable() {
        final int Gap = this.findGappleInHotbar();
        if (Gap != -1) {
            GappleSwap.mc.player.inventory.currentItem = this.findGappleInHotbar();
        }
        if (this.infoMessage.getValue()) {
            Command.sendChatMessage(ChatFormatting.DARK_GRAY + "[" + ChatFormatting.WHITE + "AutoGapple" + ChatFormatting.DARK_GRAY + "]" + ChatFormatting.GREEN + "Enabled");
        }
        this.disable();
    }
    
    private int findGappleInHotbar() {
        int slot = -1;
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = GappleSwap.mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY) {
                final Item item = stack.getItem();
                if (item.equals(Items.GOLDEN_APPLE)) {
                    slot = i;
                    break;
                }
            }
        }
        return slot;
    }
}
