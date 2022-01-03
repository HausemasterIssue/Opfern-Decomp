//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.command.commands;

import me.zeroeightsix.kami.command.*;
import net.minecraft.tileentity.*;
import me.zeroeightsix.kami.command.syntax.*;
import me.zeroeightsix.kami.util.*;
import net.minecraft.item.*;

public class PeekCommand extends Command
{
    public static TileEntityShulkerBox sb;
    
    public PeekCommand() {
        super("peek", SyntaxChunk.EMPTY);
    }
    
    public void call(final String[] args) {
        final ItemStack is = Wrapper.getPlayer().inventory.getCurrentItem();
        if (is.getItem() instanceof ItemShulkerBox) {
            final TileEntityShulkerBox entityBox = new TileEntityShulkerBox();
            entityBox.blockType = ((ItemShulkerBox)is.getItem()).getBlock();
            entityBox.setWorld(Wrapper.getWorld());
            entityBox.readFromNBT(is.getTagCompound().getCompoundTag("BlockEntityTag"));
            PeekCommand.sb = entityBox;
        }
        else {
            Command.sendChatMessage("You aren't carrying a shulker box.");
        }
    }
}
