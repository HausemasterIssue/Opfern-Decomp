//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.module.modules.combat;

import me.zeroeightsix.kami.module.*;
import me.zeroeightsix.kami.event.events.*;
import me.zero.alpine.listener.*;
import java.util.function.*;
import net.minecraft.init.*;

@Module.Info(name = "FastExp", category = Module.Category.COMBAT)
public class FastExp extends Module
{
    @EventHandler
    private Listener<PacketEvent.Receive> receiveListener;
    
    public FastExp() {
        this.receiveListener = (Listener<PacketEvent.Receive>)new Listener(event -> {
            if (FastExp.mc.player != null && (FastExp.mc.player.getHeldItemMainhand().getItem() == Items.EXPERIENCE_BOTTLE || FastExp.mc.player.getHeldItemOffhand().getItem() == Items.EXPERIENCE_BOTTLE)) {
                FastExp.mc.rightClickDelayTimer = 0;
            }
        }, new Predicate[0]);
    }
}
