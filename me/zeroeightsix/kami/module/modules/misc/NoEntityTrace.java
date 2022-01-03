//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.module.modules.misc;

import me.zeroeightsix.kami.module.*;
import me.zeroeightsix.kami.setting.*;

@Module.Info(name = "NoEntityTrace", category = Module.Category.MISC, description = "Blocks entities from stopping you from mining")
public class NoEntityTrace extends Module
{
    public Setting<TraceMode> mode;
    public static NoEntityTrace INSTANCE;
    
    public NoEntityTrace() {
        this.mode = (Setting<TraceMode>)this.register((Setting)Settings.e("Mode", TraceMode.DYNAMIC));
        NoEntityTrace.INSTANCE = this;
    }
    
    public static boolean shouldBlock() {
        return NoEntityTrace.mc.world != null && NoEntityTrace.INSTANCE.isEnabled() && (NoEntityTrace.INSTANCE.mode.getValue() == TraceMode.STATIC || NoEntityTrace.mc.playerController.isHittingBlock);
    }
    
    public enum TraceMode
    {
        STATIC, 
        DYNAMIC;
    }
}
