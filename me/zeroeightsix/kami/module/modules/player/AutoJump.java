//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.module.modules.player;

import me.zeroeightsix.kami.module.*;

@Module.Info(name = "AutoJump", category = Module.Category.PLAYER, description = "Automatically jumps if possible")
public class AutoJump extends Module
{
    public void onUpdate() {
        if (AutoJump.mc.player.isInWater() || AutoJump.mc.player.isInLava()) {
            AutoJump.mc.player.motionY = 0.1;
        }
        else if (AutoJump.mc.player.onGround) {
            AutoJump.mc.player.jump();
        }
    }
}
