//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.mixin.client;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import me.zeroeightsix.kami.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import me.zeroeightsix.kami.event.events.*;

@Mixin({ PlayerControllerMP.class })
public class MixinPlayerControllerMP
{
    @Inject(method = { "onPlayerDamageBlock" }, at = { @At("HEAD") }, cancellable = true)
    public void onPlayerDamageBlock(final BlockPos posBlock, final EnumFacing directionFacing, final CallbackInfoReturnable<Boolean> p_Info) {
        final DamageBlockEvent l_Event = new DamageBlockEvent(posBlock, directionFacing);
        KamiMod.EVENT_BUS.post((Object)l_Event);
        if (l_Event.isCancelled()) {
            p_Info.setReturnValue((Object)false);
            p_Info.cancel();
        }
    }
    
    @Inject(method = { "clickBlock" }, at = { @At("HEAD") }, cancellable = true)
    public void clickBlock(final BlockPos loc, final EnumFacing face, final CallbackInfoReturnable<Boolean> callback) {
        final EventPlayerClickBlock l_Event = new EventPlayerClickBlock(loc, face);
        KamiMod.EVENT_BUS.post((Object)l_Event);
        if (l_Event.isCancelled()) {
            callback.setReturnValue((Object)false);
            callback.cancel();
        }
    }
    
    @Inject(method = { "resetBlockRemoving" }, at = { @At("HEAD") }, cancellable = true)
    public void resetBlockRemoving(final CallbackInfo p_Info) {
        final EventPlayerResetBlockRemoving l_Event = new EventPlayerResetBlockRemoving();
        KamiMod.EVENT_BUS.post((Object)l_Event);
        if (l_Event.isCancelled()) {
            p_Info.cancel();
        }
    }
}
