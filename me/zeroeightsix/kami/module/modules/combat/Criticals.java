//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.module.modules.combat;

import me.zeroeightsix.kami.module.*;
import me.zeroeightsix.kami.event.events.*;
import me.zero.alpine.listener.*;
import me.zeroeightsix.kami.setting.*;
import java.util.function.*;
import net.minecraft.item.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

@Module.Info(name = "Criticals", category = Module.Category.COMBAT)
public class Criticals extends Module
{
    private Setting<Boolean> onlyS;
    private Setting<Boolean> cAura;
    @EventHandler
    private Listener<PacketEvent.Send> listener;
    
    public Criticals() {
        this.onlyS = (Setting<Boolean>)this.register((Setting)Settings.b("SwordOnly", true));
        this.cAura = (Setting<Boolean>)this.register((Setting)Settings.b("StopOnCrystal", true));
        this.listener = (Listener<PacketEvent.Send>)new Listener(event -> {
            if (event.getPacket() instanceof CPacketUseEntity) {
                if (this.onlyS.getValue() && !(Criticals.mc.player.getHeldItemMainhand().getItem() instanceof ItemSword)) {
                    return;
                }
                if (this.cAura.getValue() && (CrystalAura.isPlacing() || OffhandCA.isPlacing())) {
                    return;
                }
                if (((CPacketUseEntity)event.getPacket()).getAction() == CPacketUseEntity.Action.ATTACK && Criticals.mc.player.onGround) {
                    Criticals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Criticals.mc.player.posX, Criticals.mc.player.posY + 0.10000000149011612, Criticals.mc.player.posZ, false));
                    Criticals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Criticals.mc.player.posX, Criticals.mc.player.posY, Criticals.mc.player.posZ, false));
                }
            }
        }, new Predicate[0]);
    }
}
