//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.module.modules.movement;

import me.zeroeightsix.kami.module.*;
import net.minecraftforge.client.event.*;
import me.zero.alpine.listener.*;
import me.zeroeightsix.kami.event.events.*;
import me.zeroeightsix.kami.setting.*;
import java.util.*;
import java.util.function.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.*;
import net.minecraft.client.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.math.*;
import net.minecraft.client.gui.*;
import net.minecraft.entity.*;
import me.zeroeightsix.kami.util.*;

@Module.Info(name = "PacketFly", category = Module.Category.MOVEMENT)
public class PacketFly extends Module
{
    public final Setting<Float> speed;
    public final Setting<Boolean> noKick;
    private int teleportId;
    private List<CPacketPlayer> packets;
    @EventHandler
    public Listener<InputUpdateEvent> listener;
    @EventHandler
    public Listener<PacketEvent.Send> sendListener;
    @EventHandler
    public Listener<PacketEvent.Receive> receiveListener;
    
    public PacketFly() {
        this.speed = (Setting<Float>)this.register((Setting)Settings.floatBuilder("Speed").withValue(0.1f).withMaximum(5.0f).withMinimum(0.0f).build());
        this.noKick = (Setting<Boolean>)this.register((Setting)Settings.b("NoKick", true));
        this.packets = new ArrayList<CPacketPlayer>();
        final CPacketPlayer[] bounds = { null };
        final double[] ySpeed = { 0.0 };
        final double[] ySpeed2 = { 0.0 };
        final double[] n = { 0.0 };
        final double[][] directionalSpeed = new double[1][1];
        final int[] i = { 0 };
        final int[] j = { 0 };
        final int[] k = { 0 };
        this.listener = (Listener<InputUpdateEvent>)new Listener(event -> {
            if (this.teleportId <= 0) {
                bounds[0] = (CPacketPlayer)new CPacketPlayer.Position(Minecraft.getMinecraft().player.posX, 0.0, Minecraft.getMinecraft().player.posZ, Minecraft.getMinecraft().player.onGround);
                this.packets.add(bounds[0]);
                Minecraft.getMinecraft().player.connection.sendPacket((Packet)bounds[0]);
                return;
            }
            PacketFly.mc.player.setVelocity(0.0, 0.0, 0.0);
            if (PacketFly.mc.world.getCollisionBoxes((Entity)PacketFly.mc.player, PacketFly.mc.player.getEntityBoundingBox().expand(-0.0625, 0.0, -0.0625)).isEmpty()) {
                ySpeed[0] = 0.0;
                if (PacketFly.mc.gameSettings.keyBindJump.isKeyDown()) {
                    if (this.noKick.getValue()) {
                        ySpeed2[0] = ((PacketFly.mc.player.ticksExisted % 20 == 0) ? -0.03999999910593033 : 0.06199999898672104);
                    }
                    else {
                        ySpeed2[0] = 0.06199999898672104;
                    }
                }
                else if (PacketFly.mc.gameSettings.keyBindSneak.isKeyDown()) {
                    ySpeed2[0] = -0.062;
                }
                else {
                    if (PacketFly.mc.world.getCollisionBoxes((Entity)PacketFly.mc.player, PacketFly.mc.player.getEntityBoundingBox().expand(-0.0625, -0.0625, -0.0625)).isEmpty()) {
                        if (PacketFly.mc.player.ticksExisted % 4 == 0) {
                            n[0] = (this.noKick.getValue() ? -0.04f : 0.0f);
                        }
                        else {
                            n[0] = 0.0;
                        }
                    }
                    else {
                        n[0] = 0.0;
                    }
                    ySpeed2[0] = n[0];
                }
                directionalSpeed[0] = BlockInteractionHelper.directionSpeed(this.speed.getValue());
                if (PacketFly.mc.gameSettings.keyBindJump.isKeyDown() || PacketFly.mc.gameSettings.keyBindSneak.isKeyDown() || PacketFly.mc.gameSettings.keyBindForward.isKeyDown() || PacketFly.mc.gameSettings.keyBindBack.isKeyDown() || PacketFly.mc.gameSettings.keyBindRight.isKeyDown() || PacketFly.mc.gameSettings.keyBindLeft.isKeyDown()) {
                    if (directionalSpeed[0][0] != 0.0 || ySpeed2[0] != 0.0 || directionalSpeed[0][1] != 0.0) {
                        if (PacketFly.mc.player.movementInput.jump && (PacketFly.mc.player.moveStrafing != 0.0f || PacketFly.mc.player.moveForward != 0.0f)) {
                            PacketFly.mc.player.setVelocity(0.0, 0.0, 0.0);
                            this.move(0.0, 0.0, 0.0);
                            i[0] = 0;
                            while (i[0] <= 3) {
                                PacketFly.mc.player.setVelocity(0.0, ySpeed2[0] * i[0], 0.0);
                                this.move(0.0, ySpeed2[0] * i[0], 0.0);
                                final int n2 = 0;
                                ++i[n2];
                            }
                        }
                        else if (PacketFly.mc.player.movementInput.jump) {
                            PacketFly.mc.player.setVelocity(0.0, 0.0, 0.0);
                            this.move(0.0, 0.0, 0.0);
                            j[0] = 0;
                            while (j[0] <= 3) {
                                PacketFly.mc.player.setVelocity(0.0, ySpeed2[0] * j[0], 0.0);
                                this.move(0.0, ySpeed2[0] * j[0], 0.0);
                                final int n3 = 0;
                                ++j[n3];
                            }
                        }
                        else {
                            k[0] = 0;
                            while (k[0] <= 2) {
                                PacketFly.mc.player.setVelocity(directionalSpeed[0][0] * k[0], ySpeed2[0] * k[0], directionalSpeed[0][1] * k[0]);
                                this.move(directionalSpeed[0][0] * k[0], ySpeed2[0] * k[0], directionalSpeed[0][1] * k[0]);
                                final int n4 = 0;
                                ++k[n4];
                            }
                        }
                    }
                }
                else if (this.noKick.getValue() && PacketFly.mc.world.getCollisionBoxes((Entity)PacketFly.mc.player, PacketFly.mc.player.getEntityBoundingBox().expand(-0.0625, -0.0625, -0.0625)).isEmpty()) {
                    PacketFly.mc.player.setVelocity(0.0, (PacketFly.mc.player.ticksExisted % 2 == 0) ? 0.03999999910593033 : -0.03999999910593033, 0.0);
                    this.move(0.0, (PacketFly.mc.player.ticksExisted % 2 == 0) ? 0.03999999910593033 : -0.03999999910593033, 0.0);
                }
            }
        }, (Predicate[])new Predicate[0]);
        final CPacketPlayer[] packet = { null };
        this.sendListener = (Listener<PacketEvent.Send>)new Listener(event -> {
            if (event.getPacket() instanceof CPacketPlayer && !(event.getPacket() instanceof CPacketPlayer.Position)) {
                event.cancel();
            }
            if (event.getPacket() instanceof CPacketPlayer) {
                packet[0] = (CPacketPlayer)event.getPacket();
                if (this.packets.contains(packet[0])) {
                    this.packets.remove(packet[0]);
                }
                else {
                    event.cancel();
                }
            }
        }, (Predicate[])new Predicate[0]);
        final SPacketPlayerPosLook[] packet2 = { null };
        this.receiveListener = (Listener<PacketEvent.Receive>)new Listener(event -> {
            if (event.getPacket() instanceof SPacketPlayerPosLook) {
                packet2[0] = (SPacketPlayerPosLook)event.getPacket();
                if (Minecraft.getMinecraft().player.isEntityAlive() && Minecraft.getMinecraft().world.isBlockLoaded(new BlockPos(Minecraft.getMinecraft().player.posX, Minecraft.getMinecraft().player.posY, Minecraft.getMinecraft().player.posZ)) && !(Minecraft.getMinecraft().currentScreen instanceof GuiDownloadTerrain)) {
                    if (this.teleportId <= 0) {
                        this.teleportId = packet2[0].getTeleportId();
                    }
                    else {
                        event.cancel();
                    }
                }
            }
        }, (Predicate[])new Predicate[0]);
    }
    
    public void onEnable() {
        if (PacketFly.mc.world != null) {
            this.teleportId = 0;
            this.packets.clear();
            final CPacketPlayer bounds = (CPacketPlayer)new CPacketPlayer.Position(PacketFly.mc.player.posX, 0.0, PacketFly.mc.player.posZ, PacketFly.mc.player.onGround);
            this.packets.add(bounds);
            PacketFly.mc.player.connection.sendPacket((Packet)bounds);
        }
    }
    
    private void move(final double x, final double y, final double z) {
        final Minecraft mc = Minecraft.getMinecraft();
        final CPacketPlayer pos = (CPacketPlayer)new CPacketPlayer.Position(mc.player.posX + x, mc.player.posY + y, mc.player.posZ + z, mc.player.onGround);
        this.packets.add(pos);
        mc.player.connection.sendPacket((Packet)pos);
        final CPacketPlayer bounds = (CPacketPlayer)new CPacketPlayer.Position(mc.player.posX + x, 0.0, mc.player.posZ + z, mc.player.onGround);
        this.packets.add(bounds);
        mc.player.connection.sendPacket((Packet)bounds);
        ++this.teleportId;
        mc.player.connection.sendPacket((Packet)new CPacketConfirmTeleport(this.teleportId - 1));
        mc.player.connection.sendPacket((Packet)new CPacketConfirmTeleport(this.teleportId));
        mc.player.connection.sendPacket((Packet)new CPacketConfirmTeleport(this.teleportId + 1));
    }
}
