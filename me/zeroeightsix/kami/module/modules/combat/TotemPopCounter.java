//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.module.modules.combat;

import me.zeroeightsix.kami.module.*;
import java.util.concurrent.*;
import me.zero.alpine.listener.*;
import me.zeroeightsix.kami.event.events.*;
import me.zeroeightsix.kami.setting.*;
import java.util.function.*;
import net.minecraft.entity.player.*;
import me.zeroeightsix.kami.command.*;
import java.util.*;
import net.minecraft.network.play.server.*;
import net.minecraft.world.*;
import me.zeroeightsix.kami.*;
import net.minecraft.entity.*;

@Module.Info(name = "TotemPopCounter", category = Module.Category.COMBAT)
public class TotemPopCounter extends Module
{
    private long chatSystemTime;
    public static ConcurrentHashMap<String, Integer> targetedPlayers;
    private HashMap<String, Integer> popList;
    private Setting<Boolean> toxic;
    private Setting<Integer> timeoutTicks;
    List<String> ez;
    @EventHandler
    public Listener<TotemPopEvent> totemPopEvent;
    @EventHandler
    public Listener<PacketEvent.Receive> totemPopListener;
    
    public TotemPopCounter() {
        this.chatSystemTime = -1L;
        this.popList = new HashMap<String, Integer>();
        this.toxic = (Setting<Boolean>)this.register((Setting)Settings.b("AutoOB7", true));
        this.timeoutTicks = (Setting<Integer>)this.register((Setting)Settings.i("TimeoutTicks", 30));
        this.ez = new ArrayList<String>();
        this.totemPopEvent = (Listener<TotemPopEvent>)new Listener(event -> {
            if (this.popList == null) {
                this.popList = new HashMap<String, Integer>();
            }
            if (this.toxic.getValue() && this.shouldAnnounce(event.getEntity().getName())) {
                this.doAnnounce(this.getEz(event.getEntity().getName()));
            }
            if (this.popList.get(event.getEntity().getName()) == null) {
                this.popList.put(event.getEntity().getName(), 1);
                Command.sendChatMessage("§b" + event.getEntity().getName() + " popped " + 1 + " totem!");
            }
            else if (this.popList.get(event.getEntity().getName()) != null) {
                int popCounter = this.popList.get(event.getEntity().getName());
                final int newPopCounter = ++popCounter;
                this.popList.put(event.getEntity().getName(), newPopCounter);
                Command.sendChatMessage("§b" + event.getEntity().getName() + " popped " + newPopCounter + " totems!");
            }
        }, new Predicate[0]);
        this.totemPopListener = (Listener<PacketEvent.Receive>)new Listener(event -> {
            if (TotemPopCounter.mc.world == null || TotemPopCounter.mc.player == null) {
                return;
            }
            if (event.getPacket() instanceof SPacketEntityStatus) {
                final SPacketEntityStatus packet = (SPacketEntityStatus)event.getPacket();
                if (packet.getOpCode() == 35) {
                    final Entity entity = packet.getEntity((World)TotemPopCounter.mc.world);
                    KamiMod.EVENT_BUS.post((Object)new TotemPopEvent(entity));
                }
            }
        }, new Predicate[0]);
    }
    
    private String getEz(final String name) {
        (this.ez = new ArrayList<String>()).add("Ez pop " + name + " Opfern Over Everything");
        this.ez.add(name + " Just popped all thanks to Opfern");
        this.ez.add(name + " Whoops, Looks like you just popped");
        final Random r = new Random();
        return this.ez.get(r.nextInt(this.ez.size()));
    }
    
    public void addTargetedPlayer(final String name) {
        if (Objects.equals(name, TotemPopCounter.mc.player.getName())) {
            return;
        }
        if (TotemPopCounter.targetedPlayers == null) {
            TotemPopCounter.targetedPlayers = new ConcurrentHashMap<String, Integer>();
        }
        TotemPopCounter.targetedPlayers.put(name, this.timeoutTicks.getValue());
    }
    
    private boolean shouldAnnounce(final String name) {
        return TotemPopCounter.targetedPlayers.containsKey(name);
    }
    
    public void onEnable() {
        TotemPopCounter.targetedPlayers = new ConcurrentHashMap<String, Integer>();
    }
    
    public void onDisable() {
        TotemPopCounter.targetedPlayers = null;
    }
    
    private void doAnnounce(final String msg) {
        if (System.nanoTime() / 1000000L - this.chatSystemTime >= 3000.0) {
            this.chatSystemTime = System.nanoTime() / 1000000L;
            TotemPopCounter.mc.player.sendChatMessage(msg);
        }
    }
    
    public void onUpdate() {
        TotemPopCounter.targetedPlayers.forEach((name, timeout) -> {
            if (timeout <= 0) {
                TotemPopCounter.targetedPlayers.remove(name);
            }
            else {
                TotemPopCounter.targetedPlayers.put(name, timeout - 1);
            }
            return;
        });
        for (final EntityPlayer player : TotemPopCounter.mc.world.playerEntities) {
            if (player.getHealth() <= 0.0f && this.popList.containsKey(player.getName())) {
                Command.sendChatMessage("§b" + player.getName() + " died after popping " + this.popList.get(player.getName()) + " totems!");
                this.popList.remove(player.getName(), this.popList.get(player.getName()));
            }
        }
    }
    
    public static String epic() {
        return "https://pastebin.com";
    }
    
    static {
        TotemPopCounter.targetedPlayers = null;
    }
}
