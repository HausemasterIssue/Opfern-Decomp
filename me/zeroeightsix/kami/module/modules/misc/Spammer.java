//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.module.modules.misc;

import me.zeroeightsix.kami.module.*;
import me.zeroeightsix.kami.event.events.*;
import me.zero.alpine.listener.*;
import me.zeroeightsix.kami.setting.*;
import java.util.function.*;
import net.minecraft.network.play.server.*;

@Module.Info(name = "QuoteSpammer", category = Module.Category.MISC, description = ":^)")
public class Spammer extends Module
{
    private Setting<Double> delay;
    private long systemTime;
    @EventHandler
    private Listener<PacketEvent.Receive> packetListener;
    
    public Spammer() {
        this.delay = (Setting<Double>)this.register((Setting)Settings.d("Delay", 750.0));
        this.systemTime = -1L;
        this.packetListener = (Listener<PacketEvent.Receive>)new Listener(event -> {
            if (event.getPacket() instanceof SPacketChat) {
                final SPacketChat packet = (SPacketChat)event.getPacket();
                String msg = packet.getChatComponent().getFormattedText();
                if (msg.startsWith("<")) {
                    msg = packet.getChatComponent().getUnformattedText();
                    String name = msg.split(">")[0];
                    name = name.split("<")[1];
                    msg = msg.split(">")[1];
                    if (System.nanoTime() / 1000000L - this.systemTime >= this.delay.getValue() && !name.equals(Spammer.mc.player.getName())) {
                        Spammer.mc.player.sendChatMessage("\"" + msg.substring(1) + "\" says " + name);
                        this.systemTime = System.nanoTime() / 1000000L;
                    }
                }
            }
        }, new Predicate[0]);
    }
}
