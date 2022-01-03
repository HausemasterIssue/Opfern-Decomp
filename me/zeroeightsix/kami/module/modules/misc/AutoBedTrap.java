//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.module.modules.misc;

import me.zeroeightsix.kami.module.*;
import me.zeroeightsix.kami.setting.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import java.util.stream.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.block.state.*;
import net.minecraft.network.play.client.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.util.math.*;

@Module.Info(name = "AutoBedTrap", category = Module.Category.MISC)
public class AutoBedTrap extends Module
{
    private Setting<Integer> range;
    private Setting<Boolean> rotate;
    List<BlockPos> beds;
    int blocksPlaced;
    
    public AutoBedTrap() {
        this.range = (Setting<Integer>)this.register((Setting)Settings.i("Range", 4));
        this.rotate = (Setting<Boolean>)this.register((Setting)Settings.b("Rotate", true));
    }
    
    protected void onEnable() {
        this.beds = new ArrayList<BlockPos>();
    }
    
    public void onUpdate() {
        int y;
        for (int reach = y = this.range.getValue(); y >= -reach; --y) {
            for (int x = -reach; x <= reach; ++x) {
                for (int z = -reach; z <= reach; ++z) {
                    final BlockPos pos = new BlockPos(AutoBedTrap.mc.player.posX + x, AutoBedTrap.mc.player.posY + y, AutoBedTrap.mc.player.posZ + z);
                    if (this.getFacingDirection(pos) != null && this.blockChecks(AutoBedTrap.mc.world.getBlockState(pos).getBlock()) && AutoBedTrap.mc.player.getDistance(AutoBedTrap.mc.player.posX + x, AutoBedTrap.mc.player.posY + y, AutoBedTrap.mc.player.posZ + z) < AutoBedTrap.mc.playerController.getBlockReachDistance() - 0.2 && !this.beds.contains(pos)) {
                        this.beds.add(pos);
                    }
                }
            }
        }
        if (!this.beds.isEmpty()) {
            for (int m = 0; m < this.beds.size(); ++m) {
                final BlockPos bed = this.beds.get(m);
                final BlockPos x2 = bed.add(1, 0, 0);
                final BlockPos negx = bed.add(-1, 0, 0);
                final BlockPos y2 = bed.add(0, 1, 0);
                final BlockPos z2 = bed.add(0, 0, 1);
                final BlockPos negz = bed.add(0, 0, -1);
                int newSlot = -1;
                for (int i = 0; i < 9; ++i) {
                    final ItemStack stack = AutoBedTrap.mc.player.inventory.getStackInSlot(i);
                    if (stack != ItemStack.EMPTY) {
                        if (stack.getItem() instanceof ItemBlock) {
                            final Block block = ((ItemBlock)stack.getItem()).getBlock();
                            if (block instanceof BlockObsidian) {
                                newSlot = i;
                                break;
                            }
                        }
                    }
                }
                if (newSlot == -1) {
                    return;
                }
                final int oldSlot = AutoBedTrap.mc.player.inventory.currentItem;
                AutoBedTrap.mc.player.inventory.currentItem = newSlot;
                if (this.shouldPlace(x2)) {
                    placeBlockScaffold(x2, this.rotate.getValue());
                }
                if (this.shouldPlace(negx)) {
                    placeBlockScaffold(negx, this.rotate.getValue());
                }
                if (this.shouldPlace(y2)) {
                    placeBlockScaffold(y2, this.rotate.getValue());
                }
                if (this.shouldPlace(z2)) {
                    placeBlockScaffold(z2, this.rotate.getValue());
                }
                if (this.shouldPlace(negz)) {
                    placeBlockScaffold(negz, this.rotate.getValue());
                }
                AutoBedTrap.mc.player.inventory.currentItem = oldSlot;
            }
        }
    }
    
    private boolean shouldPlace(final BlockPos pos) {
        final List<Entity> entities = (List<Entity>)AutoBedTrap.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(pos)).stream().filter(e -> !(e instanceof EntityItem)).filter(e -> !(e instanceof EntityXPOrb)).collect(Collectors.toList());
        final boolean a = entities.isEmpty();
        final boolean b = AutoBedTrap.mc.world.getBlockState(pos).getMaterial().isReplaceable();
        return a && b;
    }
    
    public static boolean placeBlockScaffold(final BlockPos pos, final boolean rotate) {
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbor = pos.offset(side);
            final EnumFacing side2 = side.getOpposite();
            if (canBeClicked(neighbor)) {
                final Vec3d hitVec = new Vec3d((Vec3i)neighbor).add(0.5, 0.5, 0.5).add(new Vec3d(side2.getDirectionVec()).scale(0.5));
                if (rotate) {
                    faceVectorPacketInstant(hitVec);
                }
                AutoBedTrap.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)AutoBedTrap.mc.player, CPacketEntityAction.Action.START_SNEAKING));
                processRightClickBlock(neighbor, side2, hitVec);
                AutoBedTrap.mc.player.swingArm(EnumHand.MAIN_HAND);
                AutoBedTrap.mc.rightClickDelayTimer = 0;
                AutoBedTrap.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)AutoBedTrap.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                return true;
            }
        }
        return false;
    }
    
    private static PlayerControllerMP getPlayerController() {
        return AutoBedTrap.mc.playerController;
    }
    
    public static void processRightClickBlock(final BlockPos pos, final EnumFacing side, final Vec3d hitVec) {
        getPlayerController().processRightClickBlock(AutoBedTrap.mc.player, AutoBedTrap.mc.world, pos, side, hitVec, EnumHand.MAIN_HAND);
    }
    
    public static IBlockState getState(final BlockPos pos) {
        return AutoBedTrap.mc.world.getBlockState(pos);
    }
    
    public static Block getBlock(final BlockPos pos) {
        return getState(pos).getBlock();
    }
    
    public static boolean canBeClicked(final BlockPos pos) {
        return getBlock(pos).canCollideCheck(getState(pos), false);
    }
    
    public static void faceVectorPacketInstant(final Vec3d vec) {
        final float[] rotations = getNeededRotations2(vec);
        AutoBedTrap.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(rotations[0], rotations[1], AutoBedTrap.mc.player.onGround));
    }
    
    private static float[] getNeededRotations2(final Vec3d vec) {
        final Vec3d eyesPos = getEyesPos();
        final double diffX = vec.x - eyesPos.x;
        final double diffY = vec.y - eyesPos.y;
        final double diffZ = vec.z - eyesPos.z;
        final double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        final float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        final float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        return new float[] { AutoBedTrap.mc.player.rotationYaw + MathHelper.wrapDegrees(yaw - AutoBedTrap.mc.player.rotationYaw), AutoBedTrap.mc.player.rotationPitch + MathHelper.wrapDegrees(pitch - AutoBedTrap.mc.player.rotationPitch) };
    }
    
    public static Vec3d getEyesPos() {
        return new Vec3d(AutoBedTrap.mc.player.posX, AutoBedTrap.mc.player.posY + AutoBedTrap.mc.player.getEyeHeight(), AutoBedTrap.mc.player.posZ);
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
    
    private boolean blockChecks(final Block block) {
        return block == Blocks.BED;
    }
    
    private EnumFacing getFacingDirection(final BlockPos pos) {
        EnumFacing direction = null;
        if (!AutoBedTrap.mc.world.getBlockState(pos.add(0, 1, 0)).getBlock().fullBlock && !(AutoBedTrap.mc.world.getBlockState(pos.add(0, 1, 0)).getBlock() instanceof BlockBed)) {
            direction = EnumFacing.UP;
        }
        else if (!AutoBedTrap.mc.world.getBlockState(pos.add(0, -1, 0)).getBlock().fullBlock && !(AutoBedTrap.mc.world.getBlockState(pos.add(0, -1, 0)).getBlock() instanceof BlockBed)) {
            direction = EnumFacing.DOWN;
        }
        else if (!AutoBedTrap.mc.world.getBlockState(pos.add(1, 0, 0)).getBlock().fullBlock && !(AutoBedTrap.mc.world.getBlockState(pos.add(1, 0, 0)).getBlock() instanceof BlockBed)) {
            direction = EnumFacing.EAST;
        }
        else if (!AutoBedTrap.mc.world.getBlockState(pos.add(-1, 0, 0)).getBlock().fullBlock && !(AutoBedTrap.mc.world.getBlockState(pos.add(-1, 0, 0)).getBlock() instanceof BlockBed)) {
            direction = EnumFacing.WEST;
        }
        else if (!AutoBedTrap.mc.world.getBlockState(pos.add(0, 0, 1)).getBlock().fullBlock && !(AutoBedTrap.mc.world.getBlockState(pos.add(0, 0, 1)).getBlock() instanceof BlockBed)) {
            direction = EnumFacing.SOUTH;
        }
        else if (!AutoBedTrap.mc.world.getBlockState(pos.add(0, 0, 1)).getBlock().fullBlock && !(AutoBedTrap.mc.world.getBlockState(pos.add(0, 0, -1)).getBlock() instanceof BlockBed)) {
            direction = EnumFacing.NORTH;
        }
        final RayTraceResult rayResult = AutoBedTrap.mc.world.rayTraceBlocks(new Vec3d(AutoBedTrap.mc.player.posX, AutoBedTrap.mc.player.posY + AutoBedTrap.mc.player.getEyeHeight(), AutoBedTrap.mc.player.posZ), new Vec3d(pos.getX() + 0.5, (double)pos.getY(), pos.getZ() + 0.5));
        if (rayResult != null && rayResult.getBlockPos() == pos) {
            return rayResult.sideHit;
        }
        return direction;
    }
}
