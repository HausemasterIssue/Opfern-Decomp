//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.module.modules.player;

import me.zeroeightsix.kami.setting.*;
import me.zeroeightsix.kami.setting.builder.*;
import me.zeroeightsix.kami.module.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import me.zeroeightsix.kami.util.*;
import net.minecraft.block.*;
import net.minecraft.util.math.*;

@Module.Info(name = "Scaffold", category = Module.Category.PLAYER)
public class Scaffold extends Module
{
    private Setting<Integer> future;
    
    public Scaffold() {
        this.future = (Setting<Integer>)this.register((SettingBuilder)Settings.integerBuilder("Ticks").withMinimum(0).withMaximum(60).withValue(2));
    }
    
    public void onUpdate() {
        if (this.isDisabled() || Scaffold.mc.player == null || ModuleManager.isModuleEnabled("Freecam")) {
            return;
        }
        final Vec3d vec3d = EntityUtil.getInterpolatedPos((Entity)Scaffold.mc.player, this.future.getValue());
        final BlockPos blockPos = new BlockPos(vec3d).down();
        final BlockPos belowBlockPos = blockPos.down();
        if (!Wrapper.getWorld().getBlockState(blockPos).getMaterial().isReplaceable()) {
            return;
        }
        int newSlot = -1;
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = Wrapper.getPlayer().inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY) {
                if (stack.getItem() instanceof ItemBlock) {
                    final Block block = ((ItemBlock)stack.getItem()).getBlock();
                    if (!BlockInteractionHelper.blackList.contains(block)) {
                        if (!(block instanceof BlockContainer)) {
                            if (Block.getBlockFromItem(stack.getItem()).getDefaultState().isFullBlock()) {
                                if (!(((ItemBlock)stack.getItem()).getBlock() instanceof BlockFalling) || !Wrapper.getWorld().getBlockState(belowBlockPos).getMaterial().isReplaceable()) {
                                    newSlot = i;
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        if (newSlot == -1) {
            return;
        }
        final int oldSlot = Wrapper.getPlayer().inventory.currentItem;
        Wrapper.getPlayer().inventory.currentItem = newSlot;
        if (!BlockInteractionHelper.checkForNeighbours(blockPos)) {
            return;
        }
        BlockInteractionHelper.placeBlockScaffold(blockPos);
        Wrapper.getPlayer().inventory.currentItem = oldSlot;
    }
    
    public static String omg() {
        return "5bzPn";
    }
}
