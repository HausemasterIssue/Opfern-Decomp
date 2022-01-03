//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.module.modules.combat;

import me.zeroeightsix.kami.module.*;
import java.util.concurrent.*;
import me.zeroeightsix.kami.event.events.*;
import me.zero.alpine.listener.*;
import net.minecraftforge.event.entity.living.*;
import me.zeroeightsix.kami.setting.*;
import java.util.function.*;
import me.zeroeightsix.kami.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.network.play.client.*;
import net.minecraft.world.*;

@Module.Info(name = "AutoGG", category = Module.Category.COMBAT, description = "Announce killed Players")
public class AutoGG extends Module
{
    public static ConcurrentHashMap<String, Integer> targetedPlayers;
    private Setting<Boolean> toxicMode;
    private Setting<Boolean> clientName;
    private Setting<Boolean> rand;
    private Setting<Integer> timeoutTicks;
    List<String> msg;
    @EventHandler
    public Listener<PacketEvent.Send> sendListener;
    @EventHandler
    public Listener<LivingDeathEvent> livingDeathEventListener;
    
    public AutoGG() {
        this.toxicMode = (Setting<Boolean>)this.register((Setting)Settings.b("ToxicMode", false));
        this.clientName = (Setting<Boolean>)this.register((Setting)Settings.b("ClientName", true));
        this.rand = (Setting<Boolean>)this.register((Setting)Settings.b("Random", true));
        this.timeoutTicks = (Setting<Integer>)this.register((Setting)Settings.i("TimeoutTicks", 20));
        this.msg = new ArrayList<String>();
        this.sendListener = (Listener<PacketEvent.Send>)new Listener(event -> {
            if (AutoGG.mc.player == null) {
                return;
            }
            if (AutoGG.targetedPlayers == null) {
                AutoGG.targetedPlayers = new ConcurrentHashMap<String, Integer>();
            }
            if (!(event.getPacket() instanceof CPacketUseEntity)) {
                return;
            }
            final CPacketUseEntity cPacketUseEntity = (CPacketUseEntity)event.getPacket();
            if (!cPacketUseEntity.getAction().equals((Object)CPacketUseEntity.Action.ATTACK)) {
                return;
            }
            final Entity targetEntity = cPacketUseEntity.getEntityFromWorld((World)AutoGG.mc.world);
            if (!EntityUtil.isPlayer(targetEntity)) {
                return;
            }
            this.addTargetedPlayer(targetEntity.getName());
        }, new Predicate[0]);
        this.livingDeathEventListener = (Listener<LivingDeathEvent>)new Listener(event -> {
            if (AutoGG.mc.player == null) {
                return;
            }
            if (AutoGG.targetedPlayers == null) {
                AutoGG.targetedPlayers = new ConcurrentHashMap<String, Integer>();
            }
            final EntityLivingBase entity = event.getEntityLiving();
            if (entity == null) {
                return;
            }
            if (!EntityUtil.isPlayer((Entity)entity)) {
                return;
            }
            final EntityPlayer player = (EntityPlayer)entity;
            if (player.getHealth() > 0.0f) {
                return;
            }
            final String name = player.getName();
            if (this.shouldAnnounce(name)) {
                this.doAnnounce(name);
            }
        }, new Predicate[0]);
    }
    
    private String getMsg(final String name) {
        (this.msg = new ArrayList<String>()).add(name + " Taki daun");
        this.msg.add("gg " + name + " Opfern Over Everything");
        this.msg.add(name + " Tango Down");
        this.msg.add(name + " wurde den G\u00f6ttern geopfert");
        final Random r = new Random();
        return this.msg.get(r.nextInt(this.msg.size()));
    }
    
    public void onEnable() {
        AutoGG.targetedPlayers = new ConcurrentHashMap<String, Integer>();
    }
    
    public void onDisable() {
        AutoGG.targetedPlayers = null;
    }
    
    public void onUpdate() {
        if (this.isDisabled() || AutoGG.mc.player == null) {
            return;
        }
        if (AutoGG.targetedPlayers == null) {
            AutoGG.targetedPlayers = new ConcurrentHashMap<String, Integer>();
        }
        for (final Entity entity : AutoGG.mc.world.getLoadedEntityList()) {
            if (!EntityUtil.isPlayer(entity)) {
                continue;
            }
            final EntityPlayer player = (EntityPlayer)entity;
            if (player.getHealth() > 0.0f) {
                continue;
            }
            final String name2 = player.getName();
            if (this.shouldAnnounce(name2)) {
                this.doAnnounce(name2);
                break;
            }
        }
        AutoGG.targetedPlayers.forEach((name, timeout) -> {
            if (timeout <= 0) {
                AutoGG.targetedPlayers.remove(name);
            }
            else {
                AutoGG.targetedPlayers.put(name, timeout - 1);
            }
        });
    }
    
    private boolean shouldAnnounce(final String name) {
        return AutoGG.targetedPlayers.containsKey(name);
    }
    
    private void doAnnounce(final String name) {
        AutoGG.targetedPlayers.remove(name);
        final StringBuilder message = new StringBuilder();
        if (!this.rand.getValue()) {
            if (this.toxicMode.getValue()) {
                message.append("EZ'd ");
            }
            else {
                message.append("gg ");
            }
            message.append(name);
            message.append("!");
            if (this.clientName.getValue()) {
                message.append(" ");
                message.append("Opfern");
                message.append(" On Top");
            }
        }
        else {
            message.append(this.getMsg(name));
        }
        String messageSanitized = message.toString().replaceAll("§", "");
        if (messageSanitized.length() > 255) {
            messageSanitized = messageSanitized.substring(0, 255);
        }
        AutoGG.mc.player.connection.sendPacket((Packet)new CPacketChatMessage(messageSanitized));
    }
    
    public void addTargetedPlayer(final String name) {
        if (Objects.equals(name, AutoGG.mc.player.getName())) {
            return;
        }
        if (AutoGG.targetedPlayers == null) {
            AutoGG.targetedPlayers = new ConcurrentHashMap<String, Integer>();
        }
        AutoGG.targetedPlayers.put(name, this.timeoutTicks.getValue());
    }
    
    static {
        AutoGG.targetedPlayers = null;
    }
}
