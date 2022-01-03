//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.mixin.client;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.multiplayer.*;
import me.zeroeightsix.kami.module.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.client.entity.*;
import net.minecraft.potion.*;
import me.zeroeightsix.kami.module.modules.render.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import com.google.common.base.*;
import me.zeroeightsix.kami.module.modules.misc.*;
import java.util.*;

@Mixin({ EntityRenderer.class })
public class MixinEntityRenderer
{
    private boolean nightVision;
    
    public MixinEntityRenderer() {
        this.nightVision = false;
    }
    
    @Redirect(method = { "orientCamera" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/WorldClient;rayTraceBlocks(Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Vec3d;)Lnet/minecraft/util/math/RayTraceResult;"))
    public RayTraceResult rayTraceBlocks(final WorldClient world, final Vec3d start, final Vec3d end) {
        if (ModuleManager.isModuleEnabled("CameraClip")) {
            return null;
        }
        return world.rayTraceBlocks(start, end);
    }
    
    @Inject(method = { "hurtCameraEffect" }, at = { @At("HEAD") }, cancellable = true)
    public void hurtCameraEffect(final float ticks, final CallbackInfo info) {
        if (NoHurtCam.shouldDisable()) {
            info.cancel();
        }
    }
    
    @Redirect(method = { "updateLightmap" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;isPotionActive(Lnet/minecraft/potion/Potion;)Z"))
    public boolean isPotionActive(final EntityPlayerSP player, final Potion potion) {
        final boolean shouldBeActive = Brightness.shouldBeActive();
        this.nightVision = shouldBeActive;
        return shouldBeActive || player.isPotionActive(potion);
    }
    
    @Redirect(method = { "updateLightmap" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/EntityRenderer;getNightVisionBrightness(Lnet/minecraft/entity/EntityLivingBase;F)F"))
    public float getNightVisionBrightnessMixin(final EntityRenderer renderer, final EntityLivingBase entity, final float partialTicks) {
        if (this.nightVision) {
            return Brightness.getCurrentBrightness();
        }
        return renderer.getNightVisionBrightness(entity, partialTicks);
    }
    
    @Redirect(method = { "getMouseOver" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/WorldClient;getEntitiesInAABBexcluding(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/AxisAlignedBB;Lcom/google/common/base/Predicate;)Ljava/util/List;"))
    public List<Entity> getEntitiesInAABBexcluding(final WorldClient worldClient, final Entity entityIn, final AxisAlignedBB boundingBox, final Predicate predicate) {
        if (NoEntityTrace.shouldBlock()) {
            return new ArrayList<Entity>();
        }
        return (List<Entity>)worldClient.getEntitiesInAABBexcluding(entityIn, boundingBox, predicate);
    }
}
