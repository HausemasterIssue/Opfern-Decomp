//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.module.modules.render;

import me.zeroeightsix.kami.module.*;
import me.zeroeightsix.kami.setting.*;

@Module.Info(name = "FOVSlider", category = Module.Category.MISC)
public class FOVSlider extends Module
{
    private Setting<Integer> fov;
    
    public FOVSlider() {
        this.fov = (Setting<Integer>)this.register((Setting)Settings.i("FOV", 150));
    }
    
    public void onUpdate() {
        FOVSlider.mc.gameSettings.fovSetting = this.fov.getValue();
    }
    
    protected void onDisable() {
        FOVSlider.mc.gameSettings.fovSetting = this.fov.getValue();
    }
}
