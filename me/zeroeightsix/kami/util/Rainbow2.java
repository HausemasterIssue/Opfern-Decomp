//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.util;

import java.awt.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class Rainbow2
{
    public static Rainbow2 INSTANCE;
    float hue;
    Color c;
    int rgb;
    int speed;
    
    public Rainbow2() {
        this.hue = 0.0f;
        this.speed = 1;
        Rainbow2.INSTANCE = this;
    }
    
    public int getRgb() {
        return this.rgb;
    }
    
    public Color getC() {
        return this.c;
    }
    
    public void setRainbowSpeed(final int s) {
        this.speed = s;
    }
    
    public int getRainbowSpeed() {
        return this.speed;
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        this.c = Color.getHSBColor(this.hue, 1.0f, 1.0f);
        this.rgb = Color.HSBtoRGB(this.hue, 1.0f, 1.0f);
        this.hue += this.speed / 2000.0f;
    }
}
