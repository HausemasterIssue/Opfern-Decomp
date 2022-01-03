//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.module.modules.player;

import me.zeroeightsix.kami.module.*;
import me.zeroeightsix.kami.setting.*;
import net.minecraft.entity.*;
import net.minecraft.block.material.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.block.*;
import net.minecraft.util.math.*;
import net.minecraft.init.*;

@Module.Info(name = "FastFall", description = "fall fast mhm", category = Module.Category.PLAYER)
public class FastFall extends Module
{
    private Setting<Boolean> twoBlock;
    private Setting<Boolean> onlyIntoHoles;
    boolean jumping;
    boolean falling;
    
    public FastFall() {
        this.twoBlock = (Setting<Boolean>)this.register((Setting)Settings.b("TwoBlock", false));
        this.onlyIntoHoles = (Setting<Boolean>)this.register((Setting)Settings.b("OnlyIntoHoles", false));
        this.jumping = false;
        this.falling = false;
    }
    
    public void onUpdate() {
        if (FastFall.mc.world == null) {
            return;
        }
        if (FastFall.mc.gameSettings.keyBindJump.isKeyDown()) {
            this.jumping = true;
        }
        if (this.jumping && FastFall.mc.player.onGround) {
            this.jumping = false;
        }
        if (this.jumping) {
            return;
        }
        boolean hullCollidesWithBlock = this.hullCollidesWithBlock((Entity)FastFall.mc.player, FastFall.mc.player.getPositionVector().add(0.0, -1.0, 0.0));
        final boolean hullCollidesWithBlockHalf = this.hullCollidesWithBlock((Entity)FastFall.mc.player, FastFall.mc.player.getPositionVector().add(0.0, -0.5, 0.0));
        if (hullCollidesWithBlockHalf) {
            return;
        }
        if (this.twoBlock.getValue() && !hullCollidesWithBlock) {
            hullCollidesWithBlock = this.hullCollidesWithBlock((Entity)FastFall.mc.player, FastFall.mc.player.getPositionVector().add(0.0, -2.0, 0.0));
        }
        if (!hullCollidesWithBlock && !FastFall.mc.player.onGround) {
            this.falling = true;
        }
        if (this.falling && FastFall.mc.player.onGround) {
            this.falling = false;
        }
        if (this.falling) {
            return;
        }
        final AxisAlignedBB bb = FastFall.mc.player.getEntityBoundingBox();
        for (int x = MathHelper.floor(bb.minX); x < MathHelper.floor(bb.maxX + 1.0); ++x) {
            for (int z = MathHelper.floor(bb.minZ); z < MathHelper.floor(bb.maxZ + 1.0); ++z) {
                final Block block = FastFall.mc.world.getBlockState(new BlockPos((double)x, bb.maxY - 2.0, (double)z)).getBlock();
                if (!(block instanceof BlockAir)) {
                    return;
                }
            }
        }
        if (!hullCollidesWithBlock) {
            return;
        }
        if (FastFall.mc.player.onGround || FastFall.mc.player.isInWeb || FastFall.mc.player.isOnLadder() || FastFall.mc.player.isElytraFlying() || FastFall.mc.player.capabilities.isFlying || !FastFall.mc.player.isInsideOfMaterial(Material.AIR)) {
            return;
        }
        FastFall.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(FastFall.mc.player.posX, FastFall.mc.player.posY - 0.92, FastFall.mc.player.posZ, true));
        FastFall.mc.player.setPosition(FastFall.mc.player.posX, FastFall.mc.player.posY - 0.92, FastFall.mc.player.posZ);
    }
    
    private boolean hullCollidesWithBlock(final Entity entity, final Vec3d nextPosition) {
        boolean atleastOne = false;
        final AxisAlignedBB boundingBox = entity.getEntityBoundingBox();
        final Vec3d[] boundingBoxCorners = { new Vec3d(boundingBox.minX, boundingBox.minY, boundingBox.minZ), new Vec3d(boundingBox.minX, boundingBox.minY, boundingBox.maxZ), new Vec3d(boundingBox.maxX, boundingBox.minY, boundingBox.minZ), new Vec3d(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ) };
        final Vec3d entityPosition = entity.getPositionVector();
        for (final Vec3d entityBoxCorner : boundingBoxCorners) {
            final Vec3d nextBoxCorner = entityBoxCorner.subtract(entityPosition).add(nextPosition);
            final RayTraceResult rayTraceResult = entity.world.rayTraceBlocks(entityBoxCorner, nextBoxCorner, true, false, true);
            if (rayTraceResult != null) {
                if (!this.isAHole(new BlockPos(nextBoxCorner).add(0, 1, 0)) && this.onlyIntoHoles.getValue()) {
                    return false;
                }
                if (rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK) {
                    atleastOne = true;
                }
            }
        }
        return atleastOne;
    }
    
    private boolean isAHole(final BlockPos pos) {
        final BlockPos bottom = pos.add(0, -1, 0);
        final BlockPos side1 = pos.add(1, 0, 0);
        final BlockPos side2 = pos.add(-1, 0, 0);
        final BlockPos side3 = pos.add(0, 0, 1);
        final BlockPos side4 = pos.add(0, 0, -1);
        return FastFall.mc.world.getBlockState(pos).getBlock() == Blocks.AIR && (FastFall.mc.world.getBlockState(bottom).getBlock() == Blocks.BEDROCK || FastFall.mc.world.getBlockState(side1).getBlock() == Blocks.OBSIDIAN) && (FastFall.mc.world.getBlockState(side1).getBlock() == Blocks.BEDROCK || FastFall.mc.world.getBlockState(side1).getBlock() == Blocks.OBSIDIAN) && (FastFall.mc.world.getBlockState(side2).getBlock() == Blocks.BEDROCK || FastFall.mc.world.getBlockState(side2).getBlock() == Blocks.OBSIDIAN) && (FastFall.mc.world.getBlockState(side3).getBlock() == Blocks.BEDROCK || FastFall.mc.world.getBlockState(side3).getBlock() == Blocks.OBSIDIAN) && (FastFall.mc.world.getBlockState(side4).getBlock() == Blocks.BEDROCK || FastFall.mc.world.getBlockState(side4).getBlock() == Blocks.OBSIDIAN);
    }
}
