//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.module.modules.player;

import me.zeroeightsix.kami.module.*;
import me.zeroeightsix.kami.event.events.*;
import me.zero.alpine.listener.*;
import java.util.function.*;
import net.minecraft.network.play.server.*;
import net.minecraft.init.*;

@Module.Info(name = "AutoFish", category = Module.Category.MISC, description = "Automatically catch fish")
public class AutoFish extends Module
{
    @EventHandler
    private Listener<PacketEvent.Receive> receiveListener;
    
    public AutoFish() {
        this.receiveListener = (Listener<PacketEvent.Receive>)new Listener(event -> {
            if (AutoFish.mc.player != null && (AutoFish.mc.player.getHeldItemMainhand().getItem() == Items.FISHING_ROD || AutoFish.mc.player.getHeldItemOffhand().getItem() == Items.FISHING_ROD) && event.getPacket() instanceof SPacketSoundEffect && SoundEvents.ENTITY_BOBBER_SPLASH.equals(((SPacketSoundEffect)event.getPacket()).getSound())) {
                new Thread(() -> {
                    try {
                        Thread.sleep(200L);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    AutoFish.mc.rightClickMouse();
                    try {
                        Thread.sleep(200L);
                    }
                    catch (InterruptedException e2) {
                        e2.printStackTrace();
                    }
                    AutoFish.mc.rightClickMouse();
                }).start();
            }
        }, new Predicate[0]);
    }
}
