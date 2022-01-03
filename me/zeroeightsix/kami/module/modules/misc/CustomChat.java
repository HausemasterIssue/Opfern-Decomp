//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.module.modules.misc;

import me.zeroeightsix.kami.module.*;
import me.zeroeightsix.kami.event.events.*;
import me.zero.alpine.listener.*;
import me.zeroeightsix.kami.setting.*;
import java.util.function.*;
import net.minecraft.network.play.client.*;

@Module.Info(name = "CustomChat", category = Module.Category.MISC, description = "Modifies your chat messages")
public class CustomChat extends Module
{
    private Setting<Boolean> commands;
    private final String KAMI_SUFFIX = " \u23d0 \u1d0f\u1d18\ua730\u1d07\u0280\u0274";
    @EventHandler
    public Listener<PacketEvent.Send> listener;
    
    public CustomChat() {
        this.commands = (Setting<Boolean>)this.register((Setting)Settings.b("Commands", false));
        this.listener = (Listener<PacketEvent.Send>)new Listener(event -> {
            if (event.getPacket() instanceof CPacketChatMessage) {
                String s = ((CPacketChatMessage)event.getPacket()).getMessage();
                if (s.startsWith("/") && !this.commands.getValue()) {
                    return;
                }
                s += " \u23d0 \u1d0f\u1d18\ua730\u1d07\u0280\u0274";
                if (s.length() >= 256) {
                    s = s.substring(0, 256);
                }
                ((CPacketChatMessage)event.getPacket()).message = s;
            }
        }, new Predicate[0]);
    }
}
