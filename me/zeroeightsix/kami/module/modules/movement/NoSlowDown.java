//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.module.modules.movement;

import me.zeroeightsix.kami.module.*;
import net.minecraftforge.client.event.*;
import me.zero.alpine.listener.*;
import java.util.function.*;
import net.minecraft.util.*;

@Module.Info(name = "NoSlowDown", category = Module.Category.MOVEMENT)
public class NoSlowDown extends Module
{
    @EventHandler
    private Listener<InputUpdateEvent> eventListener;
    
    public NoSlowDown() {
        this.eventListener = (Listener<InputUpdateEvent>)new Listener(event -> {
            if (NoSlowDown.mc.player.isHandActive() && !NoSlowDown.mc.player.isRiding()) {
                final MovementInput movementInput = event.getMovementInput();
                movementInput.moveStrafe *= 5.0f;
                final MovementInput movementInput2 = event.getMovementInput();
                movementInput2.moveForward *= 5.0f;
            }
        }, new Predicate[0]);
    }
}
