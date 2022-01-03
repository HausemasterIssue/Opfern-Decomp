//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.module.modules.HUD;

import me.zeroeightsix.kami.module.*;
import me.zeroeightsix.kami.setting.*;
import me.zeroeightsix.kami.util.*;
import net.minecraft.potion.*;
import java.util.*;

@Module.Info(name = "Effects", category = Module.Category.HUD)
public class Effects extends Module
{
    private Setting<Float> x;
    private Setting<Float> y;
    private float posY;
    
    public Effects() {
        this.x = (Setting<Float>)this.register((Setting)Settings.f("X", 300.0f));
        this.y = (Setting<Float>)this.register((Setting)Settings.f("Y", 400.0f));
    }
    
    public void onRender() {
        this.posY = this.y.getValue();
        for (final PotionEffect p : Wrapper.getPlayer().getActivePotionEffects()) {
            final String text = p.getPotion().getName() + " - " + p.getDuration();
            Effects.mc.fontRenderer.drawStringWithShadow(text, (float)this.x.getValue(), this.posY, 16777215);
            this.posY += Effects.mc.fontRenderer.FONT_HEIGHT;
        }
    }
}
