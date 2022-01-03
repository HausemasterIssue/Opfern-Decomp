//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.module.modules.combat;

import me.zeroeightsix.kami.module.*;
import me.zeroeightsix.kami.event.events.*;
import me.zero.alpine.listener.*;
import java.util.function.*;
import net.minecraft.init.*;

@Module.Info(name = "FastCrystal", category = Module.Category.COMBAT, description = "Allows faster crystal placement")
public class FastCrystal extends Module
{
    @EventHandler
    private Listener<PacketEvent.Receive> receiveListener;
    
    public FastCrystal() {
        this.receiveListener = (Listener<PacketEvent.Receive>)new Listener(event -> {
            if (FastCrystal.mc.player != null && (FastCrystal.mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL || FastCrystal.mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL)) {
                FastCrystal.mc.rightClickDelayTimer = 0;
            }
        }, new Predicate[0]);
    }
}
