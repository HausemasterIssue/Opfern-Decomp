//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.module.modules.movement;

import me.zeroeightsix.kami.module.*;
import me.zeroeightsix.kami.setting.*;
import net.minecraft.init.*;

@Module.Info(name = "IceSpeed", description = "Ice Speed", category = Module.Category.MOVEMENT)
public class IceSpeed extends Module
{
    private Setting<Float> slipperiness;
    
    public IceSpeed() {
        this.slipperiness = (Setting<Float>)this.register((Setting)Settings.floatBuilder("Slipperiness").withMinimum(0.2f).withValue(0.4f).withMaximum(1.0f).build());
    }
    
    public void onUpdate() {
        Blocks.ICE.slipperiness = this.slipperiness.getValue();
        Blocks.PACKED_ICE.slipperiness = this.slipperiness.getValue();
        Blocks.FROSTED_ICE.slipperiness = this.slipperiness.getValue();
    }
    
    public void onDisable() {
        Blocks.ICE.slipperiness = 0.98f;
        Blocks.PACKED_ICE.slipperiness = 0.98f;
        Blocks.FROSTED_ICE.slipperiness = 0.98f;
    }
}
