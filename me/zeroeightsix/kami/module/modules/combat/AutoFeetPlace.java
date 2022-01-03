//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.module.modules.combat;

import me.zeroeightsix.kami.module.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import java.util.*;
import me.zeroeightsix.kami.setting.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.entity.item.*;
import java.util.stream.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.block.state.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.math.*;

@Module.Info(name = "Surround", category = Module.Category.COMBAT)
public class AutoFeetPlace extends Module
{
    private List<Block> whiteList;
    Double startY;
    private Setting<Boolean> rotate;
    private Setting<Integer> bpt;
    private Setting<Boolean> jump;
    
    public AutoFeetPlace() {
        this.whiteList = Arrays.asList(Blocks.OBSIDIAN);
        this.rotate = (Setting<Boolean>)this.register((Setting)Settings.b("Rotate", false));
        this.bpt = (Setting<Integer>)this.register((Setting)Settings.i("BlocksPerTick", 4));
        this.jump = (Setting<Boolean>)this.register((Setting)Settings.b("JumpDisable", true));
    }
    
    public static boolean hasNeighbour(final BlockPos blockPos) {
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbour = blockPos.offset(side);
            if (!AutoFeetPlace.mc.world.getBlockState(neighbour).getMaterial().isReplaceable()) {
                return true;
            }
        }
        return false;
    }
    
    protected void onEnable() {
        if (AutoFeetPlace.mc.world == null) {
            return;
        }
        this.startY = AutoFeetPlace.mc.player.posY;
    }
    
    public void onUpdate() {
        if (AutoFeetPlace.mc.world == null) {
            return;
        }
        if (this.jump.getValue() && AutoFeetPlace.mc.player.posY != this.startY) {
            this.disable();
        }
        if (!this.isEnabled() || AutoFeetPlace.mc.player == null) {
            return;
        }
        final Vec3d vec3d = getInterpolatedPos((Entity)AutoFeetPlace.mc.player, 0.0f);
        BlockPos northBlockPos = new BlockPos(vec3d).north();
        BlockPos southBlockPos = new BlockPos(vec3d).south();
        BlockPos eastBlockPos = new BlockPos(vec3d).east();
        BlockPos westBlockPos = new BlockPos(vec3d).west();
        int blocksPlaced = 0;
        int newSlot = -1;
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = AutoFeetPlace.mc.player.inventory.getStackInSlot(i);
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
        final int oldSlot = AutoFeetPlace.mc.player.inventory.currentItem;
        AutoFeetPlace.mc.player.inventory.currentItem = newSlot;
        Label_0323: {
            if (!hasNeighbour(northBlockPos)) {
                for (final EnumFacing side : EnumFacing.values()) {
                    final BlockPos neighbour = northBlockPos.offset(side);
                    if (hasNeighbour(neighbour)) {
                        northBlockPos = neighbour;
                        break Label_0323;
                    }
                }
                return;
            }
        }
        Label_0386: {
            if (!hasNeighbour(southBlockPos)) {
                for (final EnumFacing side : EnumFacing.values()) {
                    final BlockPos neighbour = southBlockPos.offset(side);
                    if (hasNeighbour(neighbour)) {
                        southBlockPos = neighbour;
                        break Label_0386;
                    }
                }
                return;
            }
        }
        Label_0452: {
            if (!hasNeighbour(eastBlockPos)) {
                for (final EnumFacing side : EnumFacing.values()) {
                    final BlockPos neighbour = eastBlockPos.offset(side);
                    if (hasNeighbour(neighbour)) {
                        eastBlockPos = neighbour;
                        break Label_0452;
                    }
                }
                return;
            }
        }
        Label_0518: {
            if (!hasNeighbour(westBlockPos)) {
                for (final EnumFacing side : EnumFacing.values()) {
                    final BlockPos neighbour = westBlockPos.offset(side);
                    if (hasNeighbour(neighbour)) {
                        westBlockPos = neighbour;
                        break Label_0518;
                    }
                }
                return;
            }
        }
        if (AutoFeetPlace.mc.world.getBlockState(northBlockPos).getMaterial().isReplaceable()) {
            if (this.isEntitiesEmpty(northBlockPos)) {
                placeBlockScaffold(northBlockPos, this.rotate.getValue());
                ++blocksPlaced;
            }
            else if (this.isEntitiesEmpty(northBlockPos.north()) && AutoFeetPlace.mc.world.getBlockState(northBlockPos).getMaterial().isReplaceable()) {
                placeBlockScaffold(northBlockPos.north(), this.rotate.getValue());
                ++blocksPlaced;
            }
        }
        if (blocksPlaced >= this.bpt.getValue()) {
            AutoFeetPlace.mc.player.inventory.currentItem = oldSlot;
            return;
        }
        if (AutoFeetPlace.mc.world.getBlockState(southBlockPos).getMaterial().isReplaceable()) {
            if (this.isEntitiesEmpty(southBlockPos)) {
                placeBlockScaffold(southBlockPos, this.rotate.getValue());
                ++blocksPlaced;
            }
            else if (this.isEntitiesEmpty(southBlockPos.south()) && AutoFeetPlace.mc.world.getBlockState(southBlockPos.south()).getMaterial().isReplaceable()) {
                placeBlockScaffold(southBlockPos.south(), this.rotate.getValue());
                ++blocksPlaced;
            }
        }
        if (blocksPlaced >= this.bpt.getValue()) {
            AutoFeetPlace.mc.player.inventory.currentItem = oldSlot;
            return;
        }
        if (AutoFeetPlace.mc.world.getBlockState(eastBlockPos).getMaterial().isReplaceable()) {
            if (this.isEntitiesEmpty(eastBlockPos)) {
                placeBlockScaffold(eastBlockPos, this.rotate.getValue());
                ++blocksPlaced;
            }
            else if (this.isEntitiesEmpty(eastBlockPos.east()) && AutoFeetPlace.mc.world.getBlockState(eastBlockPos.east()).getMaterial().isReplaceable()) {
                placeBlockScaffold(eastBlockPos.east(), this.rotate.getValue());
                ++blocksPlaced;
            }
        }
        if (blocksPlaced >= this.bpt.getValue()) {
            AutoFeetPlace.mc.player.inventory.currentItem = oldSlot;
            return;
        }
        if (AutoFeetPlace.mc.world.getBlockState(westBlockPos).getMaterial().isReplaceable()) {
            if (this.isEntitiesEmpty(westBlockPos)) {
                placeBlockScaffold(westBlockPos, this.rotate.getValue());
                ++blocksPlaced;
            }
            else if (this.isEntitiesEmpty(westBlockPos.west()) && AutoFeetPlace.mc.world.getBlockState(westBlockPos.west()).getMaterial().isReplaceable()) {
                placeBlockScaffold(westBlockPos.west(), this.rotate.getValue());
                ++blocksPlaced;
            }
        }
        if (blocksPlaced >= this.bpt.getValue()) {
            AutoFeetPlace.mc.player.inventory.currentItem = oldSlot;
            return;
        }
        AutoFeetPlace.mc.player.inventory.currentItem = oldSlot;
    }
    
    private boolean isEntitiesEmpty(final BlockPos pos) {
        final List<Entity> entities = (List<Entity>)AutoFeetPlace.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(pos)).stream().filter(e -> !(e instanceof EntityItem)).filter(e -> !(e instanceof EntityXPOrb)).collect(Collectors.toList());
        return entities.isEmpty();
    }
    
    public static boolean placeBlockScaffold(final BlockPos pos, final boolean rotate) {
        final Vec3d eyesPos = new Vec3d(AutoFeetPlace.mc.player.posX, AutoFeetPlace.mc.player.posY + AutoFeetPlace.mc.player.getEyeHeight(), AutoFeetPlace.mc.player.posZ);
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbor = pos.offset(side);
            final EnumFacing side2 = side.getOpposite();
            if (canBeClicked(neighbor)) {
                final Vec3d hitVec = new Vec3d((Vec3i)neighbor).add(0.5, 0.5, 0.5).add(new Vec3d(side2.getDirectionVec()).scale(0.5));
                if (rotate) {
                    faceVectorPacketInstant(hitVec);
                }
                AutoFeetPlace.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)AutoFeetPlace.mc.player, CPacketEntityAction.Action.START_SNEAKING));
                processRightClickBlock(neighbor, side2, hitVec);
                AutoFeetPlace.mc.player.swingArm(EnumHand.MAIN_HAND);
                AutoFeetPlace.mc.rightClickDelayTimer = 0;
                AutoFeetPlace.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)AutoFeetPlace.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                return true;
            }
        }
        return false;
    }
    
    private static PlayerControllerMP getPlayerController() {
        return AutoFeetPlace.mc.playerController;
    }
    
    public static void processRightClickBlock(final BlockPos pos, final EnumFacing side, final Vec3d hitVec) {
        getPlayerController().processRightClickBlock(AutoFeetPlace.mc.player, AutoFeetPlace.mc.world, pos, side, hitVec, EnumHand.MAIN_HAND);
    }
    
    public static IBlockState getState(final BlockPos pos) {
        return AutoFeetPlace.mc.world.getBlockState(pos);
    }
    
    public static Block getBlock(final BlockPos pos) {
        return getState(pos).getBlock();
    }
    
    public static boolean canBeClicked(final BlockPos pos) {
        return getBlock(pos).canCollideCheck(getState(pos), false);
    }
    
    public static void faceVectorPacketInstant(final Vec3d vec) {
        final float[] rotations = getNeededRotations2(vec);
        AutoFeetPlace.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(rotations[0], rotations[1], AutoFeetPlace.mc.player.onGround));
    }
    
    private static float[] getNeededRotations2(final Vec3d vec) {
        final Vec3d eyesPos = getEyesPos();
        final double diffX = vec.x - eyesPos.x;
        final double diffY = vec.y - eyesPos.y;
        final double diffZ = vec.z - eyesPos.z;
        final double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        final float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        final float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        return new float[] { AutoFeetPlace.mc.player.rotationYaw + MathHelper.wrapDegrees(yaw - AutoFeetPlace.mc.player.rotationYaw), AutoFeetPlace.mc.player.rotationPitch + MathHelper.wrapDegrees(pitch - AutoFeetPlace.mc.player.rotationPitch) };
    }
    
    public static Vec3d getEyesPos() {
        return new Vec3d(AutoFeetPlace.mc.player.posX, AutoFeetPlace.mc.player.posY + AutoFeetPlace.mc.player.getEyeHeight(), AutoFeetPlace.mc.player.posZ);
    }
    
    public static Vec3d getInterpolatedPos(final Entity entity, final float ticks) {
        return new Vec3d(entity.lastTickPosX, entity.lastTickPosY, entity.lastTickPosZ).add(getInterpolatedAmount(entity, ticks));
    }
    
    public static Vec3d getInterpolatedAmount(final Entity entity, final double ticks) {
        return getInterpolatedAmount(entity, ticks, ticks, ticks);
    }
    
    public static Vec3d getInterpolatedAmount(final Entity entity, final double x, final double y, final double z) {
        return new Vec3d((entity.posX - entity.lastTickPosX) * x, (entity.posY - entity.lastTickPosY) * y, (entity.posZ - entity.lastTickPosZ) * z);
    }
    
    public static boolean isSafe() {
        final BlockPos top = new BlockPos(AutoFeetPlace.mc.player.getPositionVector()).add(1, 0, 0);
        final BlockPos bottom = new BlockPos(AutoFeetPlace.mc.player.getPositionVector()).add(-1, 0, 0);
        final BlockPos left = new BlockPos(AutoFeetPlace.mc.player.getPositionVector()).add(0, 0, 1);
        final BlockPos right = new BlockPos(AutoFeetPlace.mc.player.getPositionVector()).add(0, 0, -1);
        final BlockPos middle = new BlockPos(AutoFeetPlace.mc.player.getPositionVector()).add(0, -1, 0);
        return (AutoFeetPlace.mc.world.getBlockState(top).getBlock() == Blocks.OBSIDIAN || AutoFeetPlace.mc.world.getBlockState(top).getBlock() == Blocks.BEDROCK) && (AutoFeetPlace.mc.world.getBlockState(bottom).getBlock() == Blocks.OBSIDIAN || AutoFeetPlace.mc.world.getBlockState(bottom).getBlock() == Blocks.BEDROCK) && (AutoFeetPlace.mc.world.getBlockState(left).getBlock() == Blocks.OBSIDIAN || AutoFeetPlace.mc.world.getBlockState(left).getBlock() == Blocks.BEDROCK) && (AutoFeetPlace.mc.world.getBlockState(right).getBlock() == Blocks.OBSIDIAN || AutoFeetPlace.mc.world.getBlockState(right).getBlock() == Blocks.BEDROCK) && (AutoFeetPlace.mc.world.getBlockState(middle).getBlock() == Blocks.OBSIDIAN || AutoFeetPlace.mc.world.getBlockState(middle).getBlock() == Blocks.BEDROCK);
    }
}
