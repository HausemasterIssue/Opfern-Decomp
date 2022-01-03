//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.module.modules.player;

import me.zeroeightsix.kami.module.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.item.*;

@Module.Info(name = "FastEat", category = Module.Category.PLAYER)
public class FastEat extends Module
{
    public void onUpdate() {
        if (FastEat.mc.player.isHandActive()) {
            final Item item = FastEat.mc.player.itemStackMainHand.getItem();
            if (item instanceof ItemFood) {
                for (int i = 0; i < 20; ++i) {
                    FastEat.mc.player.connection.sendPacket((Packet)new CPacketPlayer());
                }
                FastEat.mc.player.stopActiveHand();
            }
        }
    }
}
