//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.module.modules.combat;

import me.zeroeightsix.kami.module.*;
import me.zeroeightsix.kami.event.events.*;
import me.zero.alpine.listener.*;
import me.zeroeightsix.kami.setting.*;
import java.util.function.*;
import net.minecraft.entity.player.*;
import java.util.*;
import java.util.stream.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import me.zeroeightsix.kami.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.util.*;

@Module.Info(name = "KillAura", category = Module.Category.COMBAT, description = "Hits entities around you")
public class Aura extends Module
{
    private Setting<Double> range;
    private Setting<Boolean> caCheck;
    private Setting<Boolean> onlyS;
    private Setting<Boolean> rotate;
    private static boolean isAttacking;
    private double lastPosX;
    private double lastPosY;
    private double lastPosZ;
    private double angle1;
    private double angle2;
    @EventHandler
    private Listener<PacketEvent.Send> packetListener;
    
    public Aura() {
        this.range = (Setting<Double>)this.register((Setting)Settings.d("Range", 5.0));
        this.caCheck = (Setting<Boolean>)this.register((Setting)Settings.b("CACheck", true));
        this.onlyS = (Setting<Boolean>)this.register((Setting)Settings.b("SwordOnly", true));
        this.rotate = (Setting<Boolean>)this.register((Setting)Settings.b("Rotate", true));
        this.packetListener = (Listener<PacketEvent.Send>)new Listener(event -> {
            if (!this.rotate.getValue()) {
                return;
            }
            final Packet packet = event.getPacket();
            if (packet instanceof CPacketPlayer) {
                ((CPacketPlayer)packet).yaw = (float)this.angle1;
                ((CPacketPlayer)packet).pitch = (float)this.angle2;
            }
        }, new Predicate[0]);
    }
    
    public void onUpdate() {
        if (Aura.mc.player == null || Aura.mc.player.isDead) {
            return;
        }
        final List<Entity> targets = (List<Entity>)Aura.mc.world.loadedEntityList.stream().filter(entity -> entity != Aura.mc.player).filter(entity -> Aura.mc.player.getDistance(entity) <= this.range.getValue()).filter(entity -> !entity.isDead).filter(entity -> entity instanceof EntityPlayer).filter(entity -> entity.getHealth() > 0.0f).filter(entity -> !Friends.isFriend(entity.getName())).sorted(Comparator.comparing(e -> Aura.mc.player.getDistance(e))).collect(Collectors.toList());
        if (this.caCheck.getValue() && (CrystalAura.isPlacing() || OffhandCA.isPlacing())) {
            return;
        }
        if (this.onlyS.getValue() && !(Aura.mc.player.getHeldItemMainhand().getItem() instanceof ItemSword)) {
            return;
        }
        targets.forEach(target -> this.attack(target));
    }
    
    public void attack(final Entity e) {
        final float[] angle = MathUtil.calcAngle(Aura.mc.player.getPositionEyes(Aura.mc.getRenderPartialTicks()), e.getPositionVector());
        this.angle1 = angle[0];
        this.angle2 = angle[1];
        if (Aura.mc.player.getCooledAttackStrength(0.0f) >= 1.0f) {
            if (Aura.mc.player.getDistance(e) > 4.0f && Aura.mc.player.canEntityBeSeen(e)) {
                this.lastPosX = Aura.mc.player.posX;
                this.lastPosY = Aura.mc.player.posY;
                this.lastPosZ = Aura.mc.player.posZ;
                Aura.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(e.posX, e.posY, e.posZ, true));
                Aura.isAttacking = true;
                Aura.mc.playerController.attackEntity((EntityPlayer)Aura.mc.player, e);
                Aura.mc.player.swingArm(EnumHand.MAIN_HAND);
                Aura.isAttacking = false;
                Aura.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.lastPosX, this.lastPosY, this.lastPosZ, true));
            }
            else {
                Aura.isAttacking = true;
                Aura.mc.playerController.attackEntity((EntityPlayer)Aura.mc.player, e);
                Aura.mc.player.swingArm(EnumHand.MAIN_HAND);
                Aura.isAttacking = false;
            }
        }
    }
    
    public static boolean getAttacking() {
        return Aura.isAttacking;
    }
    
    static {
        Aura.isAttacking = false;
    }
}
