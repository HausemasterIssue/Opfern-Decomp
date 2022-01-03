//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.module.modules.HUD;

import me.zeroeightsix.kami.module.*;
import me.zeroeightsix.kami.setting.*;

@Module.Info(name = "PvPInfo", category = Module.Category.HUD)
public class PvPInfo extends Module
{
    private Setting<Enum> mode;
    
    public PvPInfo() {
        this.mode = (Setting<Enum>)this.register((Setting)Settings.e("Mode", sex.Longhand));
    }
    
    public Enum getSex() {
        return this.mode.getValue();
    }
    
    protected void onEnable() {
        this.disable();
    }
    
    public enum sex
    {
        Longhand, 
        Shorthand;
    }
}
