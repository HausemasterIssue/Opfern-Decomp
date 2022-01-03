//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.module.modules.combat;

import me.zeroeightsix.kami.module.*;
import net.minecraft.block.*;
import me.zeroeightsix.kami.setting.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import net.minecraft.item.*;
import java.util.function.*;
import java.util.*;
import com.mojang.realmsclient.gui.*;
import me.zeroeightsix.kami.command.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;

@Module.Info(name = "HoleFill", category = Module.Category.COMBAT)
public class HoleFill extends Module
{
    private Setting<Boolean> ec;
    private Setting<Boolean> chat;
    private Setting<Boolean> rotate;
    private Setting<Integer> timeoutTicks;
    private Setting<Integer> range;
    private Setting<Integer> yRange;
    private Setting<Integer> waitTick;
    private int totalTicksRunning;
    private ArrayList<BlockPos> holes;
    private List<Block> whiteList;
    BlockPos pos;
    private int waitCounter;
    
    public HoleFill() {
        this.ec = (Setting<Boolean>)this.register((Setting)Settings.b("Ender Chests", true));
        this.chat = (Setting<Boolean>)this.register((Setting)Settings.b("Announce", true));
        this.rotate = (Setting<Boolean>)this.register((Setting)Settings.b("Rotate", true));
        this.timeoutTicks = (Setting<Integer>)this.register((Setting)Settings.integerBuilder("TimeoutTicks").withMinimum(1).withValue(40).withMaximum(100).build());
        this.range = (Setting<Integer>)this.register((Setting)Settings.i("Range", 3));
        this.yRange = (Setting<Integer>)this.register((Setting)Settings.i("YRange", 2));
        this.waitTick = (Setting<Integer>)this.register((Setting)Settings.i("WaitTick", 2));
        this.totalTicksRunning = 0;
        this.holes = new ArrayList<BlockPos>();
        this.whiteList = Arrays.asList(Blocks.OBSIDIAN);
    }
    
    public void onUpdate() {
        if (this.totalTicksRunning >= this.timeoutTicks.getValue()) {
            this.totalTicksRunning = 0;
            this.disable();
            return;
        }
        ++this.totalTicksRunning;
        this.holes = new ArrayList<BlockPos>();
        if (this.ec.getValue()) {
            if (!this.whiteList.contains(Blocks.ENDER_CHEST)) {
                this.whiteList.add(Blocks.ENDER_CHEST);
            }
        }
        else if (this.whiteList.contains(Blocks.ENDER_CHEST)) {
            this.whiteList.remove(Blocks.ENDER_CHEST);
        }
        final Iterable<BlockPos> blocks = (Iterable<BlockPos>)BlockPos.getAllInBox(HoleFill.mc.player.getPosition().add(-this.range.getValue(), -this.yRange.getValue(), -this.range.getValue()), HoleFill.mc.player.getPosition().add((int)this.range.getValue(), (int)this.yRange.getValue(), (int)this.range.getValue()));
        for (final BlockPos pos : blocks) {
            if (!HoleFill.mc.world.getBlockState(pos).getMaterial().blocksMovement() && !HoleFill.mc.world.getBlockState(pos.add(0, 1, 0)).getMaterial().blocksMovement()) {
                final boolean solidNeighbours = (HoleFill.mc.world.getBlockState(pos.add(1, 0, 0)).getBlock() == Blocks.BEDROCK | HoleFill.mc.world.getBlockState(pos.add(1, 0, 0)).getBlock() == Blocks.OBSIDIAN) && (HoleFill.mc.world.getBlockState(pos.add(0, 0, 1)).getBlock() == Blocks.BEDROCK | HoleFill.mc.world.getBlockState(pos.add(0, 0, 1)).getBlock() == Blocks.OBSIDIAN) && (HoleFill.mc.world.getBlockState(pos.add(-1, 0, 0)).getBlock() == Blocks.BEDROCK | HoleFill.mc.world.getBlockState(pos.add(-1, 0, 0)).getBlock() == Blocks.OBSIDIAN) && (HoleFill.mc.world.getBlockState(pos.add(0, 0, -1)).getBlock() == Blocks.BEDROCK | HoleFill.mc.world.getBlockState(pos.add(0, 0, -1)).getBlock() == Blocks.OBSIDIAN) && HoleFill.mc.world.getBlockState(pos.add(0, 0, 0)).getMaterial() == Material.AIR && HoleFill.mc.world.getBlockState(pos.add(0, 1, 0)).getMaterial() == Material.AIR && HoleFill.mc.world.getBlockState(pos.add(0, 2, 0)).getMaterial() == Material.AIR;
                if (!solidNeighbours) {
                    continue;
                }
                this.holes.add(pos);
            }
        }
        int newSlot = -1;
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = HoleFill.mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY) {
                if (stack.getItem() instanceof ItemBlock) {
                    final Block block = ((ItemBlock)stack.getItem()).getBlock();
                    if (this.whiteList.contains(block)) {
                        newSlot = i;
                        break;
                    }
                }
            }
        }
        if (newSlot == -1) {
            return;
        }
        final int oldSlot = HoleFill.mc.player.inventory.currentItem;
        if (this.waitTick.getValue() > 0) {
            if (this.waitCounter < this.waitTick.getValue()) {
                HoleFill.mc.player.inventory.currentItem = newSlot;
                this.holes.forEach(this::place);
                HoleFill.mc.player.inventory.currentItem = oldSlot;
                return;
            }
            this.waitCounter = 0;
        }
    }
    
    public void onEnable() {
        if (HoleFill.mc.player != null && this.chat.getValue()) {
            Command.sendChatMessage("HoleFill " + ChatFormatting.GREEN + "Enabled!");
        }
    }
    
    public void onDisable() {
        if (HoleFill.mc.player != null && this.chat.getValue()) {
            Command.sendChatMessage("HoleFill " + ChatFormatting.RED + "Disabled!");
        }
    }
    
    private void place(final BlockPos blockPos) {
        for (final Entity entity : HoleFill.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(blockPos))) {
            if (entity instanceof EntityLivingBase) {
                return;
            }
        }
        AutoFeetPlace.placeBlockScaffold(blockPos, (boolean)this.rotate.getValue());
        ++this.waitCounter;
    }
}
