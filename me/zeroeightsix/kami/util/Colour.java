//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.util;

import java.awt.*;

public class Colour
{
    public static Color rainbow(final float speed, final float off) {
        double time = System.currentTimeMillis() / (double)speed;
        time += off;
        time %= 255.0;
        return Color.getHSBColor((float)(time / 255.0), 1.0f, 1.0f);
    }
}
