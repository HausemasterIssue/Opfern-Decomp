//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.module.modules.misc;

import me.zeroeightsix.kami.module.*;
import me.zeroeightsix.kami.setting.*;

@Module.Info(name = "Announcer", category = Module.Category.MISC)
public class Announcer extends Module
{
    private long blockTime;
    private Setting<Integer> delay;
    private Setting<Boolean> noZero;
    private Setting<Boolean> multi;
    private double X;
    private double Z;
    private double Y;
    private double dist;
    private int jumps;
    
    public Announcer() {
        this.blockTime = -1L;
        this.delay = (Setting<Integer>)this.register((Setting)Settings.i("Delay (seconds)", 5));
        this.noZero = (Setting<Boolean>)this.register((Setting)Settings.b("NoAnnounceZero", true));
        this.multi = (Setting<Boolean>)this.register((Setting)Settings.b("Yes", false));
        this.jumps = 0;
    }
    
    protected void onEnable() {
        if (Announcer.mc.world == null) {
            return;
        }
        this.blockTime = System.nanoTime() / 1000000L;
        this.X = Announcer.mc.player.posX;
        this.Z = Announcer.mc.player.posX;
    }
    
    public void onUpdate() {
        this.X = Announcer.mc.player.posX - Announcer.mc.player.prevPosX;
        this.Z = Announcer.mc.player.posZ - Announcer.mc.player.prevPosZ;
        this.dist += Math.sqrt(this.X * this.X + this.Z * this.Z) / 2.0;
        if (Announcer.mc.player.posY > Announcer.mc.player.prevPosY) {
            this.jumps += (int)(Math.sqrt(this.Y * this.Y) / 2.0);
        }
        if (System.nanoTime() / 1000000L - this.blockTime >= this.delay.getValue() * 1000) {
            this.dist = Math.round(this.dist * 100.0) / 100.0;
            if (this.noZero.getValue() && this.dist < 1.0) {
                return;
            }
            if (this.multi.getValue()) {
                if (this.jumps < 10) {
                    Announcer.mc.player.sendChatMessage("Opfern just launched me " + this.dist + " blocks");
                }
                else {
                    Announcer.mc.player.sendChatMessage("I just BunnyHopped " + this.dist + " blocks like a speed demon thanks to opfern");
                }
            }
            else {
                Announcer.mc.player.sendChatMessage("Opfern just launched me " + this.dist + " blocks");
            }
            this.dist = 0.0;
            this.jumps = 0;
            this.blockTime = System.nanoTime() / 1000000L;
        }
    }
}
