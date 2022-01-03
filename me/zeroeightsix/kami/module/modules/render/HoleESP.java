//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.module.modules.render;

import java.util.concurrent.*;
import me.zeroeightsix.kami.setting.*;
import me.zeroeightsix.kami.setting.builder.*;
import me.zeroeightsix.kami.module.modules.combat.*;
import me.zeroeightsix.kami.module.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.block.*;
import me.zeroeightsix.kami.event.events.*;
import java.awt.*;
import me.zeroeightsix.kami.util.*;
import net.minecraft.util.math.*;

@Module.Info(name = "HoleESP", category = Module.Category.RENDER)
public class HoleESP extends Module
{
    private final BlockPos[] surroundOffset;
    private Setting<Page> page;
    private Setting<Double> renderDistance;
    private Setting<Float> boundWidth;
    private Setting<Integer> a0;
    private Setting<Integer> r1;
    private Setting<Integer> g1;
    private Setting<Integer> b1;
    private Setting<Integer> r2;
    private Setting<Integer> g2;
    private Setting<Integer> b2;
    private Setting<Integer> OboundRed;
    private Setting<Integer> OboundGreen;
    private Setting<Integer> OboundBlue;
    private Setting<Integer> OboundAlpha;
    private Setting<Integer> BboundRed;
    private Setting<Integer> BboundGreen;
    private Setting<Integer> BboundBlue;
    private Setting<Integer> BboundAlpha;
    private Setting<RenderMode> renderModeSetting;
    private Setting<RenderBlocks> renderBlocksSetting;
    private Setting<BlockMode> mode;
    private Setting<Boolean> own;
    private ConcurrentHashMap<BlockPos, Boolean> safeHoles;
    
    public HoleESP() {
        this.surroundOffset = new BlockPos[] { new BlockPos(0, -1, 0), new BlockPos(0, 0, -1), new BlockPos(1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(-1, 0, 0) };
        this.page = (Setting<Page>)this.register((Setting)Settings.e("Page", Page.RENDER));
        this.renderDistance = (Setting<Double>)this.register((SettingBuilder)Settings.doubleBuilder("Render Distance").withValue(8.0).withMinimum(0.0).withVisibility(v -> this.page.getValue() == Page.RENDER));
        this.boundWidth = (Setting<Float>)this.register((Setting)Settings.floatBuilder("BoundingWidth").withMinimum(0.1f).withValue(1.5f).withMaximum(10.0f).withVisibility(v -> this.bedrockSettings() && this.page.getValue() == Page.RENDER).build());
        this.a0 = (Setting<Integer>)this.register((Setting)Settings.integerBuilder("Transparency").withMinimum(0).withValue(32).withMaximum(255).withVisibility(v -> this.page.getValue() == Page.BEDROCK || this.page.getValue() == Page.OBBY).build());
        this.r1 = (Setting<Integer>)this.register((Setting)Settings.integerBuilder("Red (Obby)").withMinimum(0).withValue(208).withMaximum(255).withVisibility(v -> this.obbySettings() && this.page.getValue() == Page.OBBY).build());
        this.g1 = (Setting<Integer>)this.register((Setting)Settings.integerBuilder("Green (Obby)").withMinimum(0).withValue(144).withMaximum(255).withVisibility(v -> this.obbySettings() && this.page.getValue() == Page.OBBY).build());
        this.b1 = (Setting<Integer>)this.register((Setting)Settings.integerBuilder("Blue (Obby)").withMinimum(0).withValue(255).withMaximum(255).withVisibility(v -> this.obbySettings() && this.page.getValue() == Page.OBBY).build());
        this.r2 = (Setting<Integer>)this.register((Setting)Settings.integerBuilder("Red (Bedrock)").withMinimum(0).withValue(144).withMaximum(255).withVisibility(v -> this.bedrockSettings() && this.page.getValue() == Page.BEDROCK).build());
        this.g2 = (Setting<Integer>)this.register((Setting)Settings.integerBuilder("Green (Bedrock)").withMinimum(0).withValue(144).withMaximum(255).withVisibility(v -> this.bedrockSettings() && this.page.getValue() == Page.BEDROCK).build());
        this.b2 = (Setting<Integer>)this.register((Setting)Settings.integerBuilder("Blue (Bedrock)").withMinimum(0).withValue(255).withMaximum(255).withVisibility(v -> this.bedrockSettings() && this.page.getValue() == Page.BEDROCK).build());
        this.OboundRed = (Setting<Integer>)this.register((Setting)Settings.integerBuilder("BoundingRed (Obby)").withMinimum(0).withMaximum(255).withValue(255).withVisibility(v -> this.obbySettings() && this.page.getValue() == Page.OBBY).build());
        this.OboundGreen = (Setting<Integer>)this.register((Setting)Settings.integerBuilder("BoundingGreen (Obby)").withMinimum(0).withMaximum(255).withValue(255).withVisibility(v -> this.obbySettings() && this.page.getValue() == Page.OBBY).build());
        this.OboundBlue = (Setting<Integer>)this.register((Setting)Settings.integerBuilder("BoundingBlue (Obby)").withMaximum(255).withValue(255).withMinimum(0).withVisibility(v -> this.obbySettings() && this.page.getValue() == Page.OBBY).build());
        this.OboundAlpha = (Setting<Integer>)this.register((Setting)Settings.integerBuilder("BoundingAlpha (Obby)").withMaximum(100).withMinimum(0).withVisibility(v -> this.obbySettings() && this.page.getValue() == Page.OBBY).withValue(60).build());
        this.BboundRed = (Setting<Integer>)this.register((Setting)Settings.integerBuilder("BoundingRed (Bedrock)").withMinimum(0).withMaximum(255).withValue(255).withVisibility(v -> this.bedrockSettings() && this.page.getValue() == Page.BEDROCK).build());
        this.BboundGreen = (Setting<Integer>)this.register((Setting)Settings.integerBuilder("BoundingGreen (Bedrock)").withMinimum(0).withValue(255).withMaximum(255).withVisibility(v -> this.bedrockSettings() && this.page.getValue() == Page.BEDROCK).build());
        this.BboundBlue = (Setting<Integer>)this.register((Setting)Settings.integerBuilder("BoundingBlue (Bedrock)").withMinimum(0).withValue(255).withMaximum(255).withVisibility(v -> this.bedrockSettings() && this.page.getValue() == Page.BEDROCK).build());
        this.BboundAlpha = (Setting<Integer>)this.register((Setting)Settings.integerBuilder("BoundingAlpha (Bedrock)").withMinimum(0).withValue(60).withMaximum(100).withVisibility(v -> this.bedrockSettings() && this.page.getValue() == Page.BEDROCK).build());
        this.renderModeSetting = (Setting<RenderMode>)this.register((Setting)Settings.enumBuilder(RenderMode.class).withName("Render Mode").withValue(RenderMode.BOUNDDOWN).build());
        this.renderBlocksSetting = (Setting<RenderBlocks>)this.register((Setting)Settings.enumBuilder(RenderBlocks.class).withName("RenderBlocks").withValue(RenderBlocks.BOTH).build());
        this.mode = (Setting<BlockMode>)this.register((Setting)Settings.enumBuilder(BlockMode.class).withName("BlockMode").withValue(BlockMode.BOTH).build());
        this.own = (Setting<Boolean>)this.register((Setting)Settings.booleanBuilder("Show Own").withValue(true).withVisibility(v -> this.page.getValue() == Page.RENDER).build());
    }
    
    private boolean obbySettings() {
        return this.renderBlocksSetting.getValue().equals(RenderBlocks.OBBY) || this.renderBlocksSetting.getValue().equals(RenderBlocks.BOTH);
    }
    
    private boolean bedrockSettings() {
        return this.renderBlocksSetting.getValue().equals(RenderBlocks.BEDROCK) || this.renderBlocksSetting.getValue().equals(RenderBlocks.BOTH);
    }
    
    public void onUpdate() {
        if (this.safeHoles == null) {
            this.safeHoles = new ConcurrentHashMap<BlockPos, Boolean>();
        }
        else {
            this.safeHoles.clear();
        }
        final int range = (int)Math.ceil(this.renderDistance.getValue());
        final CrystalAura crystalAura = (CrystalAura)ModuleManager.getModuleByName("AutoCrystal");
        final List<BlockPos> blockPosList = (List<BlockPos>)crystalAura.getSphere(CrystalAura.getPlayerPos(), (float)range, range, false, true, 0);
        for (final BlockPos pos : blockPosList) {
            if (!HoleESP.mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR)) {
                continue;
            }
            if (!HoleESP.mc.world.getBlockState(pos.add(0, 1, 0)).getBlock().equals(Blocks.AIR)) {
                continue;
            }
            if (!HoleESP.mc.world.getBlockState(pos.add(0, 2, 0)).getBlock().equals(Blocks.AIR)) {
                continue;
            }
            boolean isSafe = true;
            boolean isBedrock = true;
            for (final BlockPos offset : this.surroundOffset) {
                final Block block = HoleESP.mc.world.getBlockState(pos.add((Vec3i)offset)).getBlock();
                if (block != Blocks.BEDROCK) {
                    isBedrock = false;
                }
                if (block != Blocks.BEDROCK && block != Blocks.OBSIDIAN && block != Blocks.ENDER_CHEST && block != Blocks.ANVIL) {
                    isSafe = false;
                    break;
                }
            }
            if (!isSafe) {
                continue;
            }
            this.safeHoles.put(pos, isBedrock);
        }
    }
    
    public void onWorldRender(final RenderEvent event) {
        if (HoleESP.mc.player == null || this.safeHoles == null) {
            return;
        }
        this.safeHoles.forEach((blockPos, isBedrock) -> {
            if (!this.own.getValue() && blockPos.x == 1 && blockPos.z == 1) {
                return;
            }
            else {
                return;
            }
        });
        if (this.safeHoles.isEmpty()) {
            return;
        }
        if (this.renderModeSetting.getValue() == RenderMode.BLOCK || this.renderModeSetting.getValue() == RenderMode.DOWN || this.renderModeSetting.getValue() == RenderMode.BOUNDBLOCK || this.renderModeSetting.getValue() == RenderMode.BOUNDDOWNFULL) {
            KamiTessellator.prepare(7);
            this.safeHoles.forEach((blockPos, isBedrock) -> {
                switch (this.renderBlocksSetting.getValue()) {
                    case BOTH: {
                        if (isBedrock) {
                            this.drawBox(blockPos, this.r2.getValue(), this.g2.getValue(), this.b2.getValue());
                            break;
                        }
                        else {
                            this.drawBox(blockPos, this.r1.getValue(), this.g1.getValue(), this.b1.getValue());
                            break;
                        }
                        break;
                    }
                    case OBBY: {
                        if (!isBedrock) {
                            this.drawBox(blockPos, this.r1.getValue(), this.g1.getValue(), this.b1.getValue());
                            break;
                        }
                        else {
                            break;
                        }
                        break;
                    }
                    case BEDROCK: {
                        if (isBedrock) {
                            this.drawBox(blockPos, this.r2.getValue(), this.g2.getValue(), this.b2.getValue());
                            break;
                        }
                        else {
                            break;
                        }
                        break;
                    }
                }
                return;
            });
            KamiTessellator.release();
        }
        if (this.renderModeSetting.getValue() == RenderMode.BOUND || this.renderModeSetting.getValue() == RenderMode.BOUNDBLOCK || this.renderModeSetting.getValue() == RenderMode.BOUNDDOWNFULL || this.renderModeSetting.getValue() == RenderMode.BOUNDDOWN) {
            this.safeHoles.forEach((blockPos, isBedrock) -> {
                switch (this.renderBlocksSetting.getValue()) {
                    case BOTH: {
                        if (isBedrock) {
                            this.drawOutline(blockPos, this.BboundRed.getValue(), this.BboundGreen.getValue(), this.BboundBlue.getValue(), this.BboundAlpha.getValue());
                            break;
                        }
                        else {
                            this.drawOutline(blockPos, this.OboundRed.getValue(), this.OboundGreen.getValue(), this.OboundBlue.getValue(), this.OboundAlpha.getValue());
                            break;
                        }
                        break;
                    }
                    case OBBY: {
                        if (!isBedrock) {
                            this.drawOutline(blockPos, this.OboundRed.getValue(), this.OboundGreen.getValue(), this.OboundBlue.getValue(), this.OboundAlpha.getValue());
                            break;
                        }
                        else {
                            break;
                        }
                        break;
                    }
                    case BEDROCK: {
                        if (isBedrock) {
                            this.drawOutline(blockPos, this.BboundRed.getValue(), this.BboundGreen.getValue(), this.BboundBlue.getValue(), this.BboundAlpha.getValue());
                            break;
                        }
                        else {
                            break;
                        }
                        break;
                    }
                }
            });
        }
    }
    
    private void drawBox(final BlockPos blockPos, final int r, final int g, final int b) {
        final Color color = new Color(r, g, b, this.a0.getValue());
        if (this.renderModeSetting.getValue().equals(RenderMode.DOWN) || this.renderModeSetting.getValue().equals(RenderMode.BOUNDDOWNFULL)) {
            KamiTessellator.drawBox(blockPos, color.getRGB(), 1);
        }
        else if (this.renderModeSetting.getValue().equals(RenderMode.BLOCK) || this.renderModeSetting.getValue().equals(RenderMode.BOUNDBLOCK)) {
            KamiTessellator.drawBox(blockPos, color.getRGB(), 63);
        }
    }
    
    private void drawOutline(final BlockPos blockPos, final int r, final int g, final int b, final int a) {
        if (this.renderModeSetting.getValue().equals(RenderMode.DOWN) || this.renderModeSetting.getValue().equals(RenderMode.BOUNDDOWNFULL) || this.renderModeSetting.getValue().equals(RenderMode.BOUNDDOWN)) {
            KamiTessellator.prepareGL();
            final AxisAlignedBB bb = Wrapper.getWorld().getBlockState(blockPos).getSelectedBoundingBox(Wrapper.getWorld(), blockPos);
            KamiTessellator.drawBoundingBoxBottom(bb, this.boundWidth.getValue(), r, g, b, a);
            KamiTessellator.releaseGL();
        }
        else if (this.renderModeSetting.getValue().equals(RenderMode.BLOCK) || this.renderModeSetting.getValue().equals(RenderMode.BOUNDBLOCK) || this.renderModeSetting.getValue().equals(RenderMode.BOUND)) {
            KamiTessellator.prepareGL();
            final AxisAlignedBB bb = Wrapper.getWorld().getBlockState(blockPos).getSelectedBoundingBox(Wrapper.getWorld(), blockPos);
            KamiTessellator.drawBoundingBox(bb, this.boundWidth.getValue(), r, g, b, a);
            KamiTessellator.releaseGL();
        }
    }
    
    private enum Page
    {
        RENDER, 
        BEDROCK, 
        OBBY;
    }
    
    private enum BlockMode
    {
        SINGLE, 
        DOUBLE, 
        BOTH;
    }
    
    private enum RenderMode
    {
        DOWN, 
        BLOCK, 
        BOUNDBLOCK, 
        BOUND, 
        BOUNDDOWN, 
        BOUNDDOWNFULL;
    }
    
    private enum RenderBlocks
    {
        OBBY, 
        BEDROCK, 
        BOTH;
    }
}
