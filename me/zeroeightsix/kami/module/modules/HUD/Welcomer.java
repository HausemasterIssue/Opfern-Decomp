//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.module.modules.HUD;

import me.zeroeightsix.kami.module.*;
import java.util.*;
import java.time.*;
import me.zeroeightsix.kami.setting.*;
import com.mojang.realmsclient.gui.*;
import me.zeroeightsix.kami.util.*;
import java.awt.*;

@Module.Info(name = "Welcomer", category = Module.Category.HUD)
public class Welcomer extends Module
{
    private Setting<Float> x;
    private Setting<Float> y;
    Random r;
    ZonedDateTime time;
    private int counter;
    
    public Welcomer() {
        this.x = (Setting<Float>)this.register((Setting)Settings.f("X", 400.0f));
        this.y = (Setting<Float>)this.register((Setting)Settings.f("Y", 0.0f));
        this.r = new Random();
        this.time = ZonedDateTime.now();
        this.counter = 0;
    }
    
    public void onRender() {
        final float xPos = this.x.getValue();
        final String timer = (this.time.getHour() <= 11) ? "Good Morning " : ((this.time.getHour() <= 18 && this.time.getHour() > 11) ? "Good Afternoon " : ((this.time.getHour() <= 23 && this.time.getHour() > 18) ? "Good Evening " : ""));
        Welcomer.mc.fontRenderer.drawStringWithShadow(timer + ChatFormatting.WHITE + Wrapper.getPlayer().getDisplayNameString() + ChatFormatting.RESET + ", Welcome To " + ChatFormatting.RED + "Opfern", xPos, (float)this.y.getValue(), 16777215);
    }
    
    public static int rainbow(final int delay) {
        double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 20.0);
        rainbowState %= 360.0;
        return Color.getHSBColor((float)(rainbowState / 360.0), 0.8f, 0.7f).getRGB();
    }
}
