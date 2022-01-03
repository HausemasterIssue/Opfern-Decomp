//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.module.modules.render;

import me.zeroeightsix.kami.module.*;
import java.awt.*;
import me.zeroeightsix.kami.setting.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import me.zeroeightsix.kami.gui.rgui.util.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.util.math.*;
import net.minecraft.init.*;

@Module.Info(name = "HoleInfo", category = Module.Category.RENDER)
public class HoleInfo extends Module
{
    private Setting<Double> xpos;
    private Setting<Double> ypos;
    Color c;
    boolean font;
    Color color;
    boolean bedrock;
    boolean obby;
    boolean safe;
    
    public HoleInfo() {
        this.xpos = (Setting<Double>)this.register((Setting)Settings.d("X", 198.0));
        this.ypos = (Setting<Double>)this.register((Setting)Settings.d("Y", 469.0));
    }
    
    private static void preitemrender() {
        GL11.glPushMatrix();
        GL11.glDepthMask(true);
        GlStateManager.clear(256);
        GlStateManager.disableDepth();
        GlStateManager.enableDepth();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.scale(1.01f, 1.01f, 0.01f);
    }
    
    private static void postitemrender() {
        GlStateManager.scale(1.0f, 1.0f, 1.0f);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.disableLighting();
        GlStateManager.scale(0.5, 0.5, 0.5);
        GlStateManager.disableDepth();
        GlStateManager.enableDepth();
        GlStateManager.scale(2.0f, 2.0f, 2.0f);
        GL11.glPopMatrix();
    }
    
    public void onRender() {
        this.doStuff();
        this.renderHole(this.xpos.getValue(), this.ypos.getValue());
    }
    
    private void doStuff() {
        this.bedrock = (this.northBrock() && this.eastBrock() && this.southBrock() && this.westBrock());
        this.obby = (!this.bedrock && (this.northObby() || this.northBrock()) && (this.eastObby() || this.eastBrock()) && (this.southObby() || this.southBrock()) && (this.westObby() || this.westBrock()));
        this.safe = (this.obby || this.bedrock);
    }
    
    private void renderHole(final double x, final double y) {
        final double leftX = x;
        final double leftY = y + 16.0;
        final double upX = x + 16.0;
        final double upY = y;
        final double rightX = x + 32.0;
        final double rightY = y + 16.0;
        final double bottomX = x + 16.0;
        final double bottomY = y + 32.0;
        final Vec3d vec3d = BlockUtils.getInterpolatedPos((Entity)HoleInfo.mc.player, 0.0f);
        final BlockPos playerPos = new BlockPos(vec3d);
        switch (HoleInfo.mc.getRenderViewEntity().getHorizontalFacing()) {
            case NORTH: {
                if (this.northObby() || this.northBrock()) {
                    this.renderItem(upX, upY, new ItemStack(HoleInfo.mc.world.getBlockState(playerPos.north()).getBlock()));
                }
                if (this.westObby() || this.westBrock()) {
                    this.renderItem(leftX, leftY, new ItemStack(HoleInfo.mc.world.getBlockState(playerPos.west()).getBlock()));
                }
                if (this.eastObby() || this.eastBrock()) {
                    this.renderItem(rightX, rightY, new ItemStack(HoleInfo.mc.world.getBlockState(playerPos.east()).getBlock()));
                }
                if (this.southObby() || this.southBrock()) {
                    this.renderItem(bottomX, bottomY, new ItemStack(HoleInfo.mc.world.getBlockState(playerPos.south()).getBlock()));
                    break;
                }
                break;
            }
            case SOUTH: {
                if (this.southObby() || this.southBrock()) {
                    this.renderItem(upX, upY, new ItemStack(HoleInfo.mc.world.getBlockState(playerPos.south()).getBlock()));
                }
                if (this.eastObby() || this.eastBrock()) {
                    this.renderItem(leftX, leftY, new ItemStack(HoleInfo.mc.world.getBlockState(playerPos.east()).getBlock()));
                }
                if (this.westObby() || this.westBrock()) {
                    this.renderItem(rightX, rightY, new ItemStack(HoleInfo.mc.world.getBlockState(playerPos.west()).getBlock()));
                }
                if (this.northObby() || this.northBrock()) {
                    this.renderItem(bottomX, bottomY, new ItemStack(HoleInfo.mc.world.getBlockState(playerPos.north()).getBlock()));
                    break;
                }
                break;
            }
            case WEST: {
                if (this.westObby() || this.westBrock()) {
                    this.renderItem(upX, upY, new ItemStack(HoleInfo.mc.world.getBlockState(playerPos.west()).getBlock()));
                }
                if (this.southObby() || this.southBrock()) {
                    this.renderItem(leftX, leftY, new ItemStack(HoleInfo.mc.world.getBlockState(playerPos.south()).getBlock()));
                }
                if (this.northObby() || this.northBrock()) {
                    this.renderItem(rightX, rightY, new ItemStack(HoleInfo.mc.world.getBlockState(playerPos.north()).getBlock()));
                }
                if (this.eastObby() || this.eastBrock()) {
                    this.renderItem(bottomX, bottomY, new ItemStack(HoleInfo.mc.world.getBlockState(playerPos.east()).getBlock()));
                    break;
                }
                break;
            }
            case EAST: {
                if (this.eastObby() || this.eastBrock()) {
                    this.renderItem(upX, upY, new ItemStack(HoleInfo.mc.world.getBlockState(playerPos.east()).getBlock()));
                }
                if (this.northObby() || this.northBrock()) {
                    this.renderItem(leftX, leftY, new ItemStack(HoleInfo.mc.world.getBlockState(playerPos.north()).getBlock()));
                }
                if (this.southObby() || this.southBrock()) {
                    this.renderItem(rightX, rightY, new ItemStack(HoleInfo.mc.world.getBlockState(playerPos.south()).getBlock()));
                }
                if (this.westObby() || this.westBrock()) {
                    this.renderItem(bottomX, bottomY, new ItemStack(HoleInfo.mc.world.getBlockState(playerPos.west()).getBlock()));
                    break;
                }
                break;
            }
        }
    }
    
    private void renderItem(final double x, final double y, final ItemStack is) {
        preitemrender();
        RenderHelper.enableGUIStandardItemLighting();
        HoleInfo.mc.getRenderItem().renderItemAndEffectIntoGUI(is, (int)x, (int)y);
        RenderHelper.disableStandardItemLighting();
        postitemrender();
    }
    
    private boolean northObby() {
        final Vec3d vec3d = BlockUtils.getInterpolatedPos((Entity)HoleInfo.mc.player, 0.0f);
        final BlockPos playerPos = new BlockPos(vec3d);
        return HoleInfo.mc.world.getBlockState(playerPos.north()).getBlock() == Blocks.OBSIDIAN;
    }
    
    private boolean eastObby() {
        final Vec3d vec3d = BlockUtils.getInterpolatedPos((Entity)HoleInfo.mc.player, 0.0f);
        final BlockPos playerPos = new BlockPos(vec3d);
        return HoleInfo.mc.world.getBlockState(playerPos.east()).getBlock() == Blocks.OBSIDIAN;
    }
    
    private boolean southObby() {
        final Vec3d vec3d = BlockUtils.getInterpolatedPos((Entity)HoleInfo.mc.player, 0.0f);
        final BlockPos playerPos = new BlockPos(vec3d);
        return HoleInfo.mc.world.getBlockState(playerPos.south()).getBlock() == Blocks.OBSIDIAN;
    }
    
    private boolean westObby() {
        final Vec3d vec3d = BlockUtils.getInterpolatedPos((Entity)HoleInfo.mc.player, 0.0f);
        final BlockPos playerPos = new BlockPos(vec3d);
        return HoleInfo.mc.world.getBlockState(playerPos.west()).getBlock() == Blocks.OBSIDIAN;
    }
    
    private boolean northBrock() {
        final Vec3d vec3d = BlockUtils.getInterpolatedPos((Entity)HoleInfo.mc.player, 0.0f);
        final BlockPos playerPos = new BlockPos(vec3d);
        return HoleInfo.mc.world.getBlockState(playerPos.north()).getBlock() == Blocks.BEDROCK;
    }
    
    private boolean eastBrock() {
        final Vec3d vec3d = BlockUtils.getInterpolatedPos((Entity)HoleInfo.mc.player, 0.0f);
        final BlockPos playerPos = new BlockPos(vec3d);
        return HoleInfo.mc.world.getBlockState(playerPos.east()).getBlock() == Blocks.BEDROCK;
    }
    
    private boolean southBrock() {
        final Vec3d vec3d = BlockUtils.getInterpolatedPos((Entity)HoleInfo.mc.player, 0.0f);
        final BlockPos playerPos = new BlockPos(vec3d);
        return HoleInfo.mc.world.getBlockState(playerPos.south()).getBlock() == Blocks.BEDROCK;
    }
    
    private boolean westBrock() {
        final Vec3d vec3d = BlockUtils.getInterpolatedPos((Entity)HoleInfo.mc.player, 0.0f);
        final BlockPos playerPos = new BlockPos(vec3d);
        return HoleInfo.mc.world.getBlockState(playerPos.west()).getBlock() == Blocks.BEDROCK;
    }
}
