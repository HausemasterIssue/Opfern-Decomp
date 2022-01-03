//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.module.modules.render;

import me.zeroeightsix.kami.module.*;
import me.zeroeightsix.kami.setting.*;
import me.zeroeightsix.kami.util.*;
import net.minecraft.util.math.*;

@Module.Info(name = "Compass", category = Module.Category.RENDER)
public class Compass extends Module
{
    private Setting<Integer> scale;
    private Setting<Integer> optionX;
    private Setting<Integer> optionY;
    private static final double HALF_PI = 1.5707963267948966;
    
    public Compass() {
        this.scale = (Setting<Integer>)this.register((Setting)Settings.integerBuilder("Scale").withMinimum(0).withValue(3).withMaximum(3).build());
        this.optionX = (Setting<Integer>)this.register((Setting)Settings.i("X", 198));
        this.optionY = (Setting<Integer>)this.register((Setting)Settings.i("Y", 469));
    }
    
    public void onRender() {
        for (final Direction dir : Direction.values()) {
            final double rad = getPosOnCompass(dir);
            Compass.mc.fontRenderer.drawStringWithShadow(dir.name(), (float)(this.optionX.getValue() + this.getX(rad)), (float)(this.optionY.getValue() + this.getY(rad)), (dir == Direction.N) ? ColourUtils.Colors.RED : ColourUtils.Colors.WHITE);
        }
    }
    
    private double getX(final double rad) {
        return Math.sin(rad) * (this.scale.getValue() * 10);
    }
    
    private double getY(final double rad) {
        final double epicPitch = MathHelper.clamp(Compass.mc.player.rotationPitch + 30.0f, -90.0f, 90.0f);
        final double pitchRadians = Math.toRadians(epicPitch);
        return Math.cos(rad) * Math.sin(pitchRadians) * (this.scale.getValue() * 10);
    }
    
    private static double getPosOnCompass(final Direction dir) {
        final double yaw = Math.toRadians(MathHelper.wrapDegrees(Compass.mc.player.rotationYaw));
        final int index = dir.ordinal();
        return yaw + index * 1.5707963267948966;
    }
    
    private enum Direction
    {
        N, 
        W, 
        S, 
        E;
    }
}
