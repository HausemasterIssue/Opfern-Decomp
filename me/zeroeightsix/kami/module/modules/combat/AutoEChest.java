//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.module.modules.combat;

import me.zeroeightsix.kami.setting.*;
import net.minecraft.block.state.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import me.zeroeightsix.kami.module.*;
import com.mojang.realmsclient.gui.*;
import me.zeroeightsix.kami.command.*;
import net.minecraft.entity.item.*;
import me.zeroeightsix.kami.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.block.*;

@Module.Info(name = "AutoEChest", category = Module.Category.COMBAT)
public class AutoEChest extends Module
{
    private Setting<Mode> mode;
    private Setting<Integer> timeoutTicks;
    private Setting<Integer> blocksPerTick;
    private Setting<Integer> tickDelay;
    private Setting<Boolean> rotate;
    private Setting<Boolean> infoMessage;
    private int offsetStep;
    private int delayStep;
    private int playerHotbarSlot;
    private int lastHotbarSlot;
    private boolean isSneaking;
    private int totalTicksRunning;
    private boolean firstRun;
    private boolean missingObiDisable;
    private int firstPos;
    
    public AutoEChest() {
        this.mode = (Setting<Mode>)this.register((Setting)Settings.e("Mode", Mode.FULL));
        this.timeoutTicks = (Setting<Integer>)this.register((Setting)Settings.integerBuilder("TimeoutTicks").withMinimum(1).withValue(40).withMaximum(100).build());
        this.blocksPerTick = (Setting<Integer>)this.register((Setting)Settings.integerBuilder("BlocksPerTick").withMinimum(1).withValue(4).withMaximum(9).build());
        this.tickDelay = (Setting<Integer>)this.register((Setting)Settings.integerBuilder("TickDelay").withMinimum(0).withValue(0).withMaximum(10).build());
        this.rotate = (Setting<Boolean>)this.register((Setting)Settings.b("Rotate", true));
        this.infoMessage = (Setting<Boolean>)this.register((Setting)Settings.b("InfoMessage", false));
        this.offsetStep = 0;
        this.delayStep = 0;
        this.playerHotbarSlot = -1;
        this.lastHotbarSlot = -1;
        this.isSneaking = false;
        this.totalTicksRunning = 0;
        this.missingObiDisable = false;
    }
    
    private static EnumFacing getPlaceableSide(final BlockPos pos) {
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbour = pos.offset(side);
            if (AutoEChest.mc.world.getBlockState(neighbour).getBlock().canCollideCheck(AutoEChest.mc.world.getBlockState(neighbour), false)) {
                final IBlockState blockState = AutoEChest.mc.world.getBlockState(neighbour);
                if (!blockState.getMaterial().isReplaceable()) {
                    return side;
                }
            }
        }
        return null;
    }
    
    protected void onEnable() {
        this.firstPos = (int)AutoEChest.mc.player.posY;
        if (AutoEChest.mc.player == null) {
            this.disable();
            return;
        }
        this.firstRun = true;
        this.playerHotbarSlot = AutoEChest.mc.player.inventory.currentItem;
        this.lastHotbarSlot = -1;
    }
    
    protected void onDisable() {
        if (AutoEChest.mc.player == null) {
            return;
        }
        if (this.lastHotbarSlot != this.playerHotbarSlot && this.playerHotbarSlot != -1) {
            AutoEChest.mc.player.inventory.currentItem = this.playerHotbarSlot;
        }
        if (this.isSneaking) {
            AutoEChest.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)AutoEChest.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            this.isSneaking = false;
        }
        this.playerHotbarSlot = -1;
        this.lastHotbarSlot = -1;
        this.missingObiDisable = false;
    }
    
    public void onUpdate() {
        if (AutoEChest.mc.player == null || ModuleManager.isModuleEnabled("Freecam")) {
            return;
        }
        if (this.totalTicksRunning >= this.timeoutTicks.getValue()) {
            this.totalTicksRunning = 0;
            this.disable();
            return;
        }
        if (!this.firstRun) {
            if (this.delayStep < this.tickDelay.getValue()) {
                ++this.delayStep;
                return;
            }
            this.delayStep = 0;
        }
        if (this.firstRun) {
            this.firstRun = false;
            if (this.findObiInHotbar() == -1) {
                this.missingObiDisable = true;
            }
        }
        if ((int)AutoEChest.mc.player.posY > this.firstPos) {
            this.disable();
        }
        else {
            Vec3d[] offsetPattern = new Vec3d[0];
            int maxSteps = 0;
            if (this.mode.getValue().equals(Mode.FULL)) {
                offsetPattern = Offsets.FULL;
                maxSteps = Offsets.FULL.length;
            }
            int blocksPlaced = 0;
            while (blocksPlaced < this.blocksPerTick.getValue()) {
                if (this.offsetStep >= maxSteps) {
                    this.offsetStep = 0;
                    break;
                }
                final BlockPos offsetPos = new BlockPos(offsetPattern[this.offsetStep]);
                final BlockPos targetPos = new BlockPos(AutoEChest.mc.player.getPositionVector()).add(offsetPos.x, offsetPos.y, offsetPos.z);
                if (this.placeBlock(targetPos)) {
                    ++blocksPlaced;
                }
                ++this.offsetStep;
            }
            if (blocksPlaced > 0) {
                if (this.lastHotbarSlot != this.playerHotbarSlot && this.playerHotbarSlot != -1) {
                    AutoEChest.mc.player.inventory.currentItem = this.playerHotbarSlot;
                    this.lastHotbarSlot = this.playerHotbarSlot;
                }
                if (this.isSneaking) {
                    AutoEChest.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)AutoEChest.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                    this.isSneaking = false;
                }
            }
            ++this.totalTicksRunning;
            if (this.missingObiDisable) {
                this.missingObiDisable = false;
                if (this.infoMessage.getValue()) {
                    Command.sendChatMessage("[AutoEChest] " + ChatFormatting.RED + "Disabled" + ChatFormatting.RESET + ", Chests missing!");
                }
                this.disable();
            }
        }
    }
    
    private boolean placeBlock(final BlockPos pos) {
        final Block block = AutoEChest.mc.world.getBlockState(pos).getBlock();
        if (!(block instanceof BlockAir) && !(block instanceof BlockLiquid)) {
            return false;
        }
        for (final Entity entity : AutoEChest.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(pos))) {
            if (!(entity instanceof EntityItem) && !(entity instanceof EntityXPOrb)) {
                return false;
            }
        }
        final EnumFacing side = getPlaceableSide(pos);
        if (side == null) {
            return false;
        }
        final BlockPos neighbour = pos.offset(side);
        final EnumFacing opposite = side.getOpposite();
        if (!BlockInteractionHelper.canBeClicked(neighbour)) {
            return false;
        }
        final Vec3d hitVec = new Vec3d((Vec3i)neighbour).add(0.5, 0.5, 0.5).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));
        final Block neighbourBlock = AutoEChest.mc.world.getBlockState(neighbour).getBlock();
        final int obiSlot = this.findObiInHotbar();
        if (obiSlot == -1) {
            this.missingObiDisable = true;
            return false;
        }
        if (this.lastHotbarSlot != obiSlot) {
            AutoEChest.mc.player.inventory.currentItem = obiSlot;
            this.lastHotbarSlot = obiSlot;
        }
        if ((!this.isSneaking && BlockInteractionHelper.blackList.contains(neighbourBlock)) || BlockInteractionHelper.shulkerList.contains(neighbourBlock)) {
            AutoEChest.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)AutoEChest.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            this.isSneaking = true;
        }
        if (this.rotate.getValue()) {
            BlockInteractionHelper.faceVectorPacketInstant(hitVec);
        }
        AutoEChest.mc.playerController.processRightClickBlock(AutoEChest.mc.player, AutoEChest.mc.world, neighbour, opposite, hitVec, EnumHand.MAIN_HAND);
        AutoEChest.mc.player.swingArm(EnumHand.MAIN_HAND);
        AutoEChest.mc.rightClickDelayTimer = 4;
        return true;
    }
    
    private int findObiInHotbar() {
        int slot = -1;
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = AutoEChest.mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY) {
                if (stack.getItem() instanceof ItemBlock) {
                    final Block block = ((ItemBlock)stack.getItem()).getBlock();
                    if (block instanceof BlockEnderChest) {
                        slot = i;
                        break;
                    }
                }
            }
        }
        return slot;
    }
    
    private enum Mode
    {
        SURROUND, 
        FULL;
    }
    
    private static class Offsets
    {
        private static final Vec3d[] FULL;
        
        static {
            FULL = new Vec3d[] { new Vec3d(1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, 1.0), new Vec3d(-1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, -1.0), new Vec3d(1.0, 0.0, 1.0), new Vec3d(-1.0, 0.0, -1.0), new Vec3d(-1.0, 0.0, 1.0), new Vec3d(1.0, 0.0, -1.0) };
        }
    }
}
