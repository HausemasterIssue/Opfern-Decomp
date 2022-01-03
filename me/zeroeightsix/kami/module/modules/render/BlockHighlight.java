//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.module.modules.render;

import me.zeroeightsix.kami.module.*;
import me.zeroeightsix.kami.setting.*;
import me.zeroeightsix.kami.event.events.*;
import java.awt.*;
import net.minecraft.world.*;
import me.zeroeightsix.kami.util.*;
import net.minecraft.util.math.*;

@Module.Info(name = "Block-Highlight", category = Module.Category.RENDER)
public class BlockHighlight extends Module
{
    private Integer[] colourArray;
    private Setting<Integer> rSetting;
    private Setting<Integer> gSetting;
    private Setting<Integer> bSetting;
    private Setting<Integer> aSetting;
    RayTraceResult result;
    
    public BlockHighlight() {
        this.colourArray = new Integer[] { 255, 0, 0, 40 };
        this.rSetting = (Setting<Integer>)this.register((Setting)Settings.integerBuilder("Red").withMinimum(0).withMaximum(255).withValue(this.colourArray[0]).build());
        this.gSetting = (Setting<Integer>)this.register((Setting)Settings.integerBuilder("Green").withMinimum(0).withMaximum(255).withValue(this.colourArray[1]).build());
        this.bSetting = (Setting<Integer>)this.register((Setting)Settings.integerBuilder("Blue").withMinimum(0).withMaximum(255).withValue(this.colourArray[2]).build());
        this.aSetting = (Setting<Integer>)this.register((Setting)Settings.integerBuilder("Alpha").withMinimum(0).withMaximum(255).withValue(this.colourArray[3]).build());
    }
    
    public void onUpdate() {
        if (BlockHighlight.mc.world == null || BlockHighlight.mc.player == null) {
            return;
        }
        this.result = BlockHighlight.mc.objectMouseOver;
        if (this.result == null) {
            return;
        }
    }
    
    public void onWorldRender(final RenderEvent event) {
        if (BlockHighlight.mc.world == null || BlockHighlight.mc.player == null) {
            return;
        }
        if (this.result == null) {
            return;
        }
        if (this.result.typeOfHit == RayTraceResult.Type.BLOCK) {
            final Color color = new Color(this.rSetting.getValue(), this.gSetting.getValue(), this.bSetting.getValue(), this.aSetting.getValue());
            final BlockPos pos = this.result.getBlockPos();
            final AxisAlignedBB bb = BlockHighlight.mc.world.getBlockState(pos).getSelectedBoundingBox((World)BlockHighlight.mc.world, pos);
            KamiTessellator.drawBoundingBox(bb, 5.0f, color.getRGB());
        }
    }
}
