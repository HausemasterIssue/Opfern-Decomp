//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.module.modules.misc;

import me.zeroeightsix.kami.module.*;
import me.zeroeightsix.kami.event.events.*;
import me.zero.alpine.listener.*;
import java.util.function.*;

@Module.Info(name = "LiquidInteract", category = Module.Category.MISC)
public class LiquidInteract extends Module
{
    @EventHandler
    private Listener<EventCanCollideCheck> CanCollide;
    
    public LiquidInteract() {
        this.CanCollide = (Listener<EventCanCollideCheck>)new Listener(p_Event -> p_Event.cancel(), new Predicate[0]);
    }
}
