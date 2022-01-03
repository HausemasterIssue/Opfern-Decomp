//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.mixin.client;

import net.minecraft.client.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ RenderGlobal.class })
public class MixinRenderGlobal
{
    @Shadow
    Minecraft mc;
    @Shadow
    public ChunkRenderContainer renderContainer;
    
    @Inject(method = { "renderBlockLayer(Lnet/minecraft/util/BlockRenderLayer;)V" }, at = { @At("HEAD") }, cancellable = true)
    public void renderBlockLayer(final BlockRenderLayer blockLayerIn, final CallbackInfo callbackInfo) {
        callbackInfo.cancel();
    }
}
