//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.module.modules.movement;

import me.zeroeightsix.kami.module.*;
import me.zeroeightsix.kami.setting.*;
import net.minecraft.util.math.*;
import net.minecraft.potion.*;
import net.minecraft.client.entity.*;

@Module.Info(name = "Strafe", description = "Strafe", category = Module.Category.MOVEMENT)
public class Strafe extends Module
{
    private Setting<Boolean> jump;
    int waitCounter;
    int forward;
    int side;
    private static final AxisAlignedBB WATER_WALK_AA;
    
    public Strafe() {
        this.jump = (Setting<Boolean>)this.register((Setting)Settings.b("AutoJump", true));
        this.forward = 1;
        this.side = 1;
    }
    
    public void onUpdate() {
        final boolean boost = Math.abs(Strafe.mc.player.rotationYawHead - Strafe.mc.player.rotationYaw) < 90.0f;
        if (Strafe.mc.player.moveForward != 0.0f) {
            if (!Strafe.mc.player.isSprinting()) {
                Strafe.mc.player.setSprinting(true);
            }
            float yaw = Strafe.mc.player.rotationYaw;
            if (Strafe.mc.player.moveForward > 0.0f) {
                if (Strafe.mc.player.movementInput.moveStrafe != 0.0f) {
                    yaw += ((Strafe.mc.player.movementInput.moveStrafe > 0.0f) ? -45.0f : 45.0f);
                }
                this.forward = 1;
                Strafe.mc.player.moveForward = 1.0f;
                Strafe.mc.player.moveStrafing = 0.0f;
            }
            else if (Strafe.mc.player.moveForward < 0.0f) {
                if (Strafe.mc.player.movementInput.moveStrafe != 0.0f) {
                    yaw += ((Strafe.mc.player.movementInput.moveStrafe > 0.0f) ? 45.0f : -45.0f);
                }
                this.forward = -1;
                Strafe.mc.player.moveForward = -1.0f;
                Strafe.mc.player.moveStrafing = 0.0f;
            }
            if (Strafe.mc.player.onGround) {
                Strafe.mc.player.setJumping(false);
                if (this.waitCounter < 1) {
                    ++this.waitCounter;
                    return;
                }
                this.waitCounter = 0;
                final float f = (float)Math.toRadians(yaw);
                if (this.jump.getValue()) {
                    Strafe.mc.player.motionY = 0.405;
                    final EntityPlayerSP player = Strafe.mc.player;
                    player.motionX -= MathHelper.sin(f) * (Strafe.mc.player.isPotionActive(Potion.getPotionById(1)) ? 0.25 : 0.221) * this.forward;
                    final EntityPlayerSP player2 = Strafe.mc.player;
                    player2.motionZ += MathHelper.cos(f) * (Strafe.mc.player.isPotionActive(Potion.getPotionById(1)) ? 0.25 : 0.221) * this.forward;
                }
                else if (Strafe.mc.gameSettings.keyBindJump.isPressed()) {
                    Strafe.mc.player.motionY = 0.405;
                    final EntityPlayerSP player3 = Strafe.mc.player;
                    player3.motionX -= MathHelper.sin(f) * (Strafe.mc.player.isPotionActive(Potion.getPotionById(1)) ? 0.25 : 0.221) * this.forward;
                    final EntityPlayerSP player4 = Strafe.mc.player;
                    player4.motionZ += MathHelper.cos(f) * (Strafe.mc.player.isPotionActive(Potion.getPotionById(1)) ? 0.25 : 0.221) * this.forward;
                }
            }
            else {
                if (this.waitCounter < 1) {
                    ++this.waitCounter;
                    return;
                }
                this.waitCounter = 0;
                final double currentSpeed = Math.sqrt(Strafe.mc.player.motionX * Strafe.mc.player.motionX + Strafe.mc.player.motionZ * Strafe.mc.player.motionZ);
                double speed = boost ? 1.0064 : 1.001;
                if (Strafe.mc.player.motionY < 0.0) {
                    speed = 1.0;
                }
                final double direction = Math.toRadians(yaw);
                Strafe.mc.player.motionX = -Math.sin(direction) * speed * currentSpeed * this.forward;
                Strafe.mc.player.motionZ = Math.cos(direction) * speed * currentSpeed * this.forward;
            }
        }
        else if (Strafe.mc.player.moveStrafing != 0.0f) {
            if (!Strafe.mc.player.isSprinting()) {
                Strafe.mc.player.setSprinting(true);
            }
            final float yaw = Strafe.mc.player.rotationYaw;
            final float f = (float)Math.toRadians(yaw);
            if (Strafe.mc.player.moveStrafing > 0.0f) {
                this.side = 1;
                Strafe.mc.player.moveStrafing = 1.0f;
            }
            else if (Strafe.mc.player.moveStrafing < 0.0f) {
                this.side = -1;
                Strafe.mc.player.moveStrafing = -1.0f;
            }
            if (this.jump.getValue()) {
                Strafe.mc.player.motionY = 0.405;
                final EntityPlayerSP player5 = Strafe.mc.player;
                player5.motionX -= MathHelper.sin(f) * (Strafe.mc.player.isPotionActive(Potion.getPotionById(1)) ? 0.25 : 0.221) * this.side;
                final EntityPlayerSP player6 = Strafe.mc.player;
                player6.motionZ += MathHelper.cos(f) * (Strafe.mc.player.isPotionActive(Potion.getPotionById(1)) ? 0.25 : 0.221) * this.side;
            }
            else if (Strafe.mc.gameSettings.keyBindJump.isPressed()) {
                Strafe.mc.player.motionY = 0.405;
                final EntityPlayerSP player7 = Strafe.mc.player;
                player7.motionX -= MathHelper.sin(f) * (Strafe.mc.player.isPotionActive(Potion.getPotionById(1)) ? 0.25 : 0.221) * this.side;
                final EntityPlayerSP player8 = Strafe.mc.player;
                player8.motionZ += MathHelper.cos(f) * (Strafe.mc.player.isPotionActive(Potion.getPotionById(1)) ? 0.25 : 0.221) * this.side;
            }
        }
    }
    
    static {
        WATER_WALK_AA = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.99, 1.0);
    }
}
