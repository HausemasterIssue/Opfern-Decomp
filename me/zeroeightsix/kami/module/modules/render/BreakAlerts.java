//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.module.modules.render;

import me.zeroeightsix.kami.module.*;
import me.zeroeightsix.kami.event.events.*;
import me.zero.alpine.listener.*;
import me.zeroeightsix.kami.setting.*;
import java.util.function.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import net.minecraft.network.play.server.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.block.*;

@Module.Info(name = "BreakAlerts", category = Module.Category.RENDER)
public class BreakAlerts extends Module
{
    private Setting<Double> minRange;
    private Setting<Boolean> obsidianOnly;
    private Setting<Boolean> pickaxeOnly;
    private Setting<Integer> x;
    private Setting<Integer> y;
    private Boolean warn;
    private String playerName;
    private int delay;
    @EventHandler
    private Listener<PacketEvent.Receive> receiveListener;
    
    public BreakAlerts() {
        this.minRange = (Setting<Double>)this.register((Setting)Settings.doubleBuilder("Min Range").withMinimum(0.0).withValue(1.5).withMaximum(10.0).build());
        this.obsidianOnly = (Setting<Boolean>)this.register((Setting)Settings.b("Obsidian Only", true));
        this.pickaxeOnly = (Setting<Boolean>)this.register((Setting)Settings.b("Pickaxe Only", true));
        this.x = (Setting<Integer>)this.register((Setting)Settings.i("X", 400));
        this.y = (Setting<Integer>)this.register((Setting)Settings.i("Y", 200));
        this.warn = false;
        this.receiveListener = (Listener<PacketEvent.Receive>)new Listener(event -> {
            if (event.getPacket() instanceof SPacketBlockBreakAnim) {
                final SPacketBlockBreakAnim packet = (SPacketBlockBreakAnim)event.getPacket();
                final int progress = packet.getProgress();
                final int breakerId = packet.getBreakerId();
                final BlockPos pos = packet.getPosition();
                final Block block = BreakAlerts.mc.world.getBlockState(pos).getBlock();
                final EntityPlayer breaker = (EntityPlayer)BreakAlerts.mc.world.getEntityByID(breakerId);
                if (breaker == null) {
                    return;
                }
                if (this.obsidianOnly.getValue() && !block.equals(Blocks.OBSIDIAN)) {
                    return;
                }
                if (this.pickaxeOnly.getValue() && (breaker.itemStackMainHand.isEmpty() || !(breaker.itemStackMainHand.getItem() instanceof ItemPickaxe))) {
                    return;
                }
                if (this.pastDistance((EntityPlayer)BreakAlerts.mc.player, pos, this.minRange.getValue())) {
                    this.playerName = breaker.getName();
                    this.warn = true;
                    this.delay = 0;
                    if (progress == 255) {
                        this.warn = false;
                    }
                }
            }
        }, new Predicate[0]);
    }
    
    public void onRender() {
        if (!this.warn) {
            return;
        }
        if (this.delay++ > 100) {
            this.warn = false;
        }
        final String text = "Warning: " + this.playerName + " is mining near you!";
        int divider = BreakAlerts.mc.gameSettings.guiScale;
        if (divider == 0) {
            divider = 3;
        }
        BreakAlerts.mc.fontRenderer.drawStringWithShadow(text, (float)this.x.getValue(), (float)this.y.getValue(), 16777215);
    }
    
    private boolean pastDistance(final EntityPlayer player, final BlockPos pos, final double dist) {
        return player.getDistanceSqToCenter(pos) <= Math.pow(dist, 2.0);
    }
}
