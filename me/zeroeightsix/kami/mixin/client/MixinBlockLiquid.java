//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.mixin.client;

import org.spongepowered.asm.mixin.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import me.zeroeightsix.kami.module.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ BlockLiquid.class })
public class MixinBlockLiquid
{
    @Inject(method = { "modifyAcceleration" }, at = { @At("HEAD") }, cancellable = true)
    public void modifyAcceleration(final World worldIn, final BlockPos pos, final Entity entityIn, final Vec3d motion, final CallbackInfoReturnable returnable) {
        if (ModuleManager.isModuleEnabled("Velocity")) {
            returnable.setReturnValue((Object)motion);
            returnable.cancel();
        }
    }
}
