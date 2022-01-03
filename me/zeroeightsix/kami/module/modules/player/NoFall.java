//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.module.modules.player;

import me.zeroeightsix.kami.module.*;
import me.zeroeightsix.kami.event.events.*;
import me.zero.alpine.listener.*;
import me.zeroeightsix.kami.setting.*;
import java.util.function.*;
import me.zeroeightsix.kami.util.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.network.play.client.*;

@Module.Info(category = Module.Category.PLAYER, description = "Prevents fall damage", name = "NoFall")
public class NoFall extends Module
{
    private Setting<Boolean> packet;
    private Setting<Boolean> bucket;
    private Setting<Integer> distance;
    private long last;
    @EventHandler
    public Listener<PacketEvent.Send> sendListener;
    
    public NoFall() {
        this.packet = (Setting<Boolean>)this.register((Setting)Settings.b("Packet", false));
        this.bucket = (Setting<Boolean>)this.register((Setting)Settings.b("Bucket", true));
        this.distance = (Setting<Integer>)this.register((Setting)Settings.i("Distance", 15));
        this.last = 0L;
        this.sendListener = (Listener<PacketEvent.Send>)new Listener(event -> {
            if (event.getPacket() instanceof CPacketPlayer && this.packet.getValue()) {
                ((CPacketPlayer)event.getPacket()).onGround = true;
            }
        }, new Predicate[0]);
    }
    
    public void onUpdate() {
        if (this.bucket.getValue() && NoFall.mc.player.fallDistance >= this.distance.getValue() && !EntityUtil.isAboveWater((Entity)NoFall.mc.player) && System.currentTimeMillis() - this.last > 100L) {
            final Vec3d posVec = NoFall.mc.player.getPositionVector();
            final RayTraceResult result = NoFall.mc.world.rayTraceBlocks(posVec, posVec.add(0.0, -5.329999923706055, 0.0), true, true, false);
            if (result != null && result.typeOfHit == RayTraceResult.Type.BLOCK) {
                EnumHand hand = EnumHand.MAIN_HAND;
                if (NoFall.mc.player.getHeldItemOffhand().getItem() == Items.WATER_BUCKET) {
                    hand = EnumHand.OFF_HAND;
                }
                else if (NoFall.mc.player.getHeldItemMainhand().getItem() != Items.WATER_BUCKET) {
                    for (int i = 0; i < 9; ++i) {
                        if (NoFall.mc.player.inventory.getStackInSlot(i).getItem() == Items.WATER_BUCKET) {
                            NoFall.mc.player.inventory.currentItem = i;
                            NoFall.mc.player.rotationPitch = 90.0f;
                            this.last = System.currentTimeMillis();
                            return;
                        }
                    }
                    return;
                }
                NoFall.mc.player.rotationPitch = 90.0f;
                NoFall.mc.playerController.processRightClick((EntityPlayer)NoFall.mc.player, (World)NoFall.mc.world, hand);
                this.last = System.currentTimeMillis();
            }
        }
    }
}
