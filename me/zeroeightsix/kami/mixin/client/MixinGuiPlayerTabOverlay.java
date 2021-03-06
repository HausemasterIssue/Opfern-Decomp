//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.mixin.client;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.gui.*;
import java.util.*;
import me.zeroeightsix.kami.module.modules.render.*;
import net.minecraft.client.network.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ GuiPlayerTabOverlay.class })
public class MixinGuiPlayerTabOverlay
{
    @Redirect(method = { "renderPlayerlist" }, at = @At(value = "INVOKE", target = "Ljava/util/List;subList(II)Ljava/util/List;"))
    public List subList(final List list, final int fromIndex, final int toIndex) {
        return list.subList(fromIndex, ExtraTab.INSTANCE.isEnabled() ? Math.min(ExtraTab.INSTANCE.tabSize.getValue(), list.size()) : toIndex);
    }
    
    @Inject(method = { "getPlayerName" }, at = { @At("HEAD") }, cancellable = true)
    public void getPlayerName(final NetworkPlayerInfo networkPlayerInfoIn, final CallbackInfoReturnable returnable) {
        if (ExtraTab.INSTANCE.isEnabled()) {
            returnable.cancel();
            returnable.setReturnValue((Object)ExtraTab.getPlayerName(networkPlayerInfoIn));
        }
    }
}
