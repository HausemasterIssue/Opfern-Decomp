//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.module.modules.HUD;

import me.zeroeightsix.kami.module.*;
import me.zeroeightsix.kami.gui.font.*;
import me.zeroeightsix.kami.setting.*;
import java.awt.*;

@Module.Info(name = "ClientName", category = Module.Category.HUD)
public class ClientName extends Module
{
    private Setting<Integer> x;
    private Setting<Integer> y;
    CFontRenderer cFontRenderer;
    
    public ClientName() {
        this.x = (Setting<Integer>)this.register((Setting)Settings.i("X", 2));
        this.y = (Setting<Integer>)this.register((Setting)Settings.i("Y", 2));
        this.cFontRenderer = new CFontRenderer(new Font("Calibri", 3, 23), true, false);
    }
    
    public void onRender() {
        this.cFontRenderer.drawStringWithShadow("Opfern §fV1.3.0Beta", (double)this.x.getValue(), (double)this.y.getValue(), 65301);
    }
}
