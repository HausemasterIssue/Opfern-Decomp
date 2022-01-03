//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.module.modules.render;

import me.zeroeightsix.kami.module.*;
import me.zeroeightsix.kami.setting.*;
import me.zeroeightsix.kami.event.events.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import java.awt.*;
import me.zeroeightsix.kami.util.*;
import java.util.*;
import net.minecraft.util.math.*;

@Module.Info(name = "PearlESP", category = Module.Category.RENDER)
public class PearlESP extends Module
{
    private Setting<Integer> r;
    private Setting<Integer> g;
    private Setting<Integer> b;
    private Setting<Integer> a;
    
    public PearlESP() {
        this.r = (Setting<Integer>)this.register((Setting)Settings.integerBuilder("Red").withMinimum(0).withMaximum(255).withValue(0).build());
        this.g = (Setting<Integer>)this.register((Setting)Settings.integerBuilder("Green").withMinimum(0).withMaximum(255).withValue(255).build());
        this.b = (Setting<Integer>)this.register((Setting)Settings.integerBuilder("Blue").withMinimum(0).withMaximum(255).withValue(30).build());
        this.a = (Setting<Integer>)this.register((Setting)Settings.integerBuilder("Alpha").withMinimum(0).withMaximum(255).withValue(255).build());
    }
    
    public void onWorldRender(final RenderEvent event) {
        for (final Entity e : PearlESP.mc.world.loadedEntityList) {
            if (e instanceof EntityEnderPearl) {
                final Vec3d vec = MathUtil.interpolateEntity(e, event.getPartialTicks());
                final double posX = vec.x - PearlESP.mc.player.renderOffsetX;
                final double posY = vec.y - PearlESP.mc.player.renderOffsetY;
                final double posZ = vec.z - PearlESP.mc.player.renderOffsetZ;
                final Color color = new Color(this.r.getValue(), this.g.getValue(), this.b.getValue(), this.a.getValue());
                KamiTessellator.prepare(7);
                KamiTessellator.drawBox((float)(int)posX, (float)(int)posY, (float)(int)posZ, color.getRGB(), 63);
                KamiTessellator.release();
            }
        }
    }
}
