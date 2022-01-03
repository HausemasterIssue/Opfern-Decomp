//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.module.modules.render;

import me.zeroeightsix.kami.module.*;
import me.zeroeightsix.kami.setting.*;

@Module.Info(name = "ShulkerPreview", category = Module.Category.RENDER)
public class ShulkerPreview extends Module
{
    private Setting<Boolean> rect;
    
    public ShulkerPreview() {
        this.rect = (Setting<Boolean>)this.register((Setting)Settings.b("FancyMode", true));
    }
    
    public boolean getRect() {
        return this.rect.getValue();
    }
}
