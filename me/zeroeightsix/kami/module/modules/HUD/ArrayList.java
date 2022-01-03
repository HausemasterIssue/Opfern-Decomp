//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.module.modules.HUD;

import me.zeroeightsix.kami.module.*;
import me.zeroeightsix.kami.setting.*;

@Module.Info(name = "ArrayList", category = Module.Category.HUD)
public class ArrayList extends Module
{
    private Setting<Float> Red;
    private Setting<Float> Green;
    private Setting<Float> Blue;
    
    public ArrayList() {
        this.Red = (Setting<Float>)this.register((Setting)Settings.floatBuilder("Red").withMinimum(0.0f).withValue(1.0f).withMaximum(1.0f).build());
        this.Green = (Setting<Float>)this.register((Setting)Settings.floatBuilder("Green").withMinimum(0.0f).withValue(1.0f).withMaximum(1.0f).build());
        this.Blue = (Setting<Float>)this.register((Setting)Settings.floatBuilder("Blue").withMinimum(0.0f).withValue(1.0f).withMaximum(1.0f).build());
    }
    
    public Float getRed() {
        return this.Red.getValue();
    }
    
    public Float getGreen() {
        return this.Green.getValue();
    }
    
    public Float getBlue() {
        return this.Blue.getValue();
    }
    
    protected void onEnable() {
        this.disable();
    }
}
