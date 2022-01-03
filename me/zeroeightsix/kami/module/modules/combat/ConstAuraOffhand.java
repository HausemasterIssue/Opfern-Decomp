//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.module.modules.combat;

import net.minecraft.entity.player.*;
import me.zero.alpine.listener.*;
import me.zeroeightsix.kami.setting.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.enchantment.*;
import me.zeroeightsix.kami.event.events.*;
import java.awt.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import java.util.function.*;
import me.zeroeightsix.kami.module.*;
import net.minecraft.entity.item.*;
import java.util.stream.*;
import net.minecraft.network.*;
import net.minecraft.client.entity.*;
import me.zeroeightsix.kami.util.*;
import net.minecraft.util.math.*;
import java.util.*;
import me.zeroeightsix.kami.module.modules.misc.*;
import com.mojang.realmsclient.gui.*;
import me.zeroeightsix.kami.command.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.play.server.*;
import net.minecraft.util.*;
import net.minecraft.init.*;

public class ConstAuraOffhand extends Module
{
    private static boolean togglePitch;
    private static boolean isSpoofingAngles;
    private static double yaw;
    private static double pitch;
    private String renderDmg;
    private int waitCounter;
    private Item item;
    private int crystals;
    private boolean moving;
    private boolean returnI;
    private Setting<Page> p;
    private Setting<Boolean> place;
    private Setting<Boolean> autoSwitch;
    private Setting<Double> placeRange;
    private Setting<Double> minDamage;
    private Setting<Double> facePlace;
    private Setting<Boolean> noTrace;
    private Setting<Integer> targetRange;
    private Setting<Boolean> explode;
    private Setting<Double> hitRange;
    private Setting<Double> wallRange;
    private Setting<Integer> waitTick;
    private Setting<Integer> maxSelfDmg;
    private Setting<Boolean> spoofRotations;
    private Setting<Boolean> rayTrace;
    private Setting<Boolean> rotate;
    private Setting<RenderMode> renderMode;
    private Setting<Boolean> announceUsage;
    private Setting<Boolean> nodesync;
    private Setting<Boolean> soft;
    private Setting<Boolean> totemOnDisable;
    private Setting<Double> health;
    private Setting<Boolean> boxRainbow;
    private Setting<Boolean> boundRainbow;
    private Setting<Integer> boxRed;
    private Setting<Integer> boxGreen;
    private Setting<Integer> boxBlue;
    private Setting<Integer> boxAlpha;
    private Setting<Integer> boundRed;
    private Setting<Integer> boundGreen;
    private Setting<Integer> boundBlue;
    private Setting<Integer> boundAlpha;
    private BlockPos renderBlock;
    private EntityPlayer target;
    private boolean turnTraceOff;
    private boolean switchCooldown;
    private int oldSlot;
    private static boolean isActive;
    @EventHandler
    private Listener<PacketEvent.Receive> packetReceiveListener;
    @EventHandler
    private Listener<PacketEvent.Send> packetListener;
    
    public ConstAuraOffhand() {
        this.item = Items.END_CRYSTAL;
        this.moving = false;
        this.returnI = false;
        this.p = (Setting<Page>)this.register((Setting)Settings.enumBuilder(Page.class).withName("Page").withValue(Page.PLACE).build());
        this.place = (Setting<Boolean>)this.register((Setting)Settings.booleanBuilder("Place").withValue(true).withVisibility(v -> this.p.getValue().equals(Page.PLACE)).build());
        this.autoSwitch = (Setting<Boolean>)this.register((Setting)Settings.booleanBuilder("Auto Switch").withValue(true).withVisibility(v -> this.p.getValue().equals(Page.PLACE)).build());
        this.placeRange = (Setting<Double>)this.register((Setting)Settings.doubleBuilder("Place Range").withMinimum(0.0).withValue(3.5).withMaximum(10.0).withVisibility(v -> this.p.getValue().equals(Page.PLACE)).build());
        this.minDamage = (Setting<Double>)this.register((Setting)Settings.doubleBuilder("Min Damage").withMinimum(0.0).withValue(2.0).withMaximum(36.0).withVisibility(v -> this.p.getValue().equals(Page.PLACE)).build());
        this.facePlace = (Setting<Double>)this.register((Setting)Settings.doubleBuilder("FacePlaceHealth").withMinimum(0.0).withValue(6.0).withMaximum(36.0).withVisibility(v -> this.p.getValue().equals(Page.PLACE)).build());
        this.noTrace = (Setting<Boolean>)this.register((Setting)Settings.booleanBuilder("EnableNoTrace").withValue(true).withVisibility(v -> this.p.getValue().equals(Page.PLACE)).build());
        this.targetRange = (Setting<Integer>)this.register((Setting)Settings.integerBuilder("MaxCDist").withMinimum(0).withValue(10).withMaximum(13).withVisibility(v -> this.p.getValue().equals(Page.PLACE)).build());
        this.explode = (Setting<Boolean>)this.register((Setting)Settings.booleanBuilder("Explode").withValue(true).withVisibility(v -> this.p.getValue().equals(Page.HIT)).build());
        this.hitRange = (Setting<Double>)this.register((Setting)Settings.doubleBuilder("Hit Range").withMinimum(0.0).withValue(5.0).withMaximum(10.0).withVisibility(v -> this.p.getValue().equals(Page.HIT)).build());
        this.wallRange = (Setting<Double>)this.register((Setting)Settings.doubleBuilder("Wall Range").withMinimum(0.0).withValue(3.0).withMaximum(10.0).withVisibility(v -> this.p.getValue().equals(Page.HIT)).build());
        this.waitTick = (Setting<Integer>)this.register((Setting)Settings.integerBuilder("Tick Delay").withMinimum(0).withValue(5).withMaximum(20).withVisibility(v -> this.p.getValue().equals(Page.HIT)).build());
        this.maxSelfDmg = (Setting<Integer>)this.register((Setting)Settings.integerBuilder("Max SelfDmg").withMinimum(0).withValue(10).withMaximum(36).withVisibility(v -> this.p.getValue().equals(Page.MISC)).build());
        this.spoofRotations = (Setting<Boolean>)this.register((Setting)Settings.booleanBuilder("Spoof Rotations").withValue(false).withVisibility(v -> this.p.getValue().equals(Page.MISC)).build());
        this.rayTrace = (Setting<Boolean>)this.register((Setting)Settings.booleanBuilder("RayTracePlace").withValue(false).withVisibility(v -> this.p.getValue().equals(Page.MISC)).build());
        this.rotate = (Setting<Boolean>)this.register((Setting)Settings.booleanBuilder("Rotate").withValue(false).withVisibility(v -> this.p.getValue().equals(Page.MISC)).build());
        this.renderMode = (Setting<RenderMode>)this.register((Setting)Settings.enumBuilder(RenderMode.class).withName("RenderMode").withValue(RenderMode.BLOCK).withVisibility(v -> this.p.getValue().equals(Page.MISC)).build());
        this.announceUsage = (Setting<Boolean>)this.register((Setting)Settings.booleanBuilder("Announce Usage").withValue(true).withVisibility(v -> this.p.getValue().equals(Page.MISC)).build());
        this.nodesync = (Setting<Boolean>)this.register((Setting)Settings.booleanBuilder("No Desync").withValue(true).withVisibility(v -> this.p.getValue().equals(Page.MISC)).build());
        this.soft = (Setting<Boolean>)this.register((Setting)Settings.booleanBuilder("Soft").withValue(true).withVisibility(v -> this.p.getValue().equals(Page.OFFHAND)).build());
        this.totemOnDisable = (Setting<Boolean>)this.register((Setting)Settings.booleanBuilder("TotemOnDisable").withValue(true).withVisibility(v -> this.p.getValue().equals(Page.OFFHAND)).build());
        this.health = (Setting<Double>)this.register((Setting)Settings.doubleBuilder("TotemHealth").withMinimum(0.0).withValue(6.0).withMaximum(36.0).withVisibility(v -> this.p.getValue().equals(Page.OFFHAND)).build());
        this.boxRainbow = (Setting<Boolean>)this.register((Setting)Settings.booleanBuilder("Rainbow").withValue(false).withVisibility(v -> this.p.getValue().equals(Page.COLOR)).build());
        this.boundRainbow = (Setting<Boolean>)this.register((Setting)Settings.booleanBuilder("BoundingRainbow").withValue(false).withVisibility(v -> this.p.getValue().equals(Page.COLOR)).build());
        this.boxRed = (Setting<Integer>)this.register((Setting)Settings.integerBuilder("Red").withMinimum(0).withMaximum(255).withValue(255).withVisibility(v -> this.p.getValue().equals(Page.COLOR) && !this.boxRainbow.getValue()).build());
        this.boxGreen = (Setting<Integer>)this.register((Setting)Settings.integerBuilder("Green").withMinimum(0).withMaximum(255).withValue(255).withVisibility(v -> this.p.getValue().equals(Page.COLOR) && !this.boxRainbow.getValue()).build());
        this.boxBlue = (Setting<Integer>)this.register((Setting)Settings.integerBuilder("Blue").withMinimum(0).withMaximum(255).withValue(255).withVisibility(v -> this.p.getValue().equals(Page.COLOR) && !this.boxRainbow.getValue()).build());
        this.boxAlpha = (Setting<Integer>)this.register((Setting)Settings.integerBuilder("Alpha").withMinimum(0).withMaximum(100).withValue(60).withVisibility(v -> this.p.getValue().equals(Page.COLOR)).build());
        this.boundRed = (Setting<Integer>)this.register((Setting)Settings.integerBuilder("BoundingRed").withMinimum(0).withMaximum(255).withValue(255).withVisibility(v -> this.p.getValue().equals(Page.COLOR) && !this.boundRainbow.getValue()).build());
        this.boundGreen = (Setting<Integer>)this.register((Setting)Settings.integerBuilder("BoundingGreen").withMinimum(0).withMaximum(255).withValue(255).withVisibility(v -> this.p.getValue().equals(Page.COLOR) && !this.boundRainbow.getValue()).build());
        this.boundBlue = (Setting<Integer>)this.register((Setting)Settings.integerBuilder("BoundingBlue").withMinimum(0).withMaximum(255).withValue(255).withVisibility(v -> this.p.getValue().equals(Page.COLOR) && !this.boundRainbow.getValue()).build());
        this.boundAlpha = (Setting<Integer>)this.register((Setting)Settings.integerBuilder("BoundingAlpha").withMinimum(0).withMaximum(100).withValue(60).withVisibility(v -> this.p.getValue().equals(Page.COLOR)).build());
        this.switchCooldown = false;
        this.oldSlot = -1;
        this.packetReceiveListener = (Listener<PacketEvent.Receive>)new Listener(event -> {
            if (event.getPacket() instanceof SPacketSoundEffect && this.nodesync.getValue()) {
                final SPacketSoundEffect packet = (SPacketSoundEffect)event.getPacket();
                if (packet.getCategory() == SoundCategory.BLOCKS && packet.getSound() == SoundEvents.ENTITY_GENERIC_EXPLODE) {
                    for (final Entity e : ConstAuraOffhand.mc.world.loadedEntityList) {
                        if (e instanceof EntityEnderCrystal && e.getDistance(packet.getX(), packet.getY(), packet.getZ()) <= 6.0) {
                            e.setDead();
                        }
                    }
                }
            }
        }, new Predicate[0]);
        this.packetListener = (Listener<PacketEvent.Send>)new Listener(event -> {
            if (!this.spoofRotations.getValue()) {
                return;
            }
            final Packet packet = event.getPacket();
            if (packet instanceof CPacketPlayer && ConstAuraOffhand.isSpoofingAngles) {
                ((CPacketPlayer)packet).yaw = (float)ConstAuraOffhand.yaw;
                ((CPacketPlayer)packet).pitch = (float)ConstAuraOffhand.pitch;
            }
        }, new Predicate[0]);
    }
    
    public static BlockPos getPlayerPos() {
        return new BlockPos(Math.floor(ConstAuraOffhand.mc.player.posX), Math.floor(ConstAuraOffhand.mc.player.posY), Math.floor(ConstAuraOffhand.mc.player.posZ));
    }
    
    static float calculateDamage(final double posX, final double posY, final double posZ, final Entity entity) {
        final float doubleExplosionSize = 12.0f;
        final double distancedsize = entity.getDistance(posX, posY, posZ) / doubleExplosionSize;
        final Vec3d vec3d = new Vec3d(posX, posY, posZ);
        final double blockDensity = entity.world.getBlockDensity(vec3d, entity.getEntityBoundingBox());
        final double v = (1.0 - distancedsize) * blockDensity;
        final float damage = (float)(int)((v * v + v) / 2.0 * 7.0 * doubleExplosionSize + 1.0);
        double finald = 1.0;
        if (entity instanceof EntityLivingBase) {
            finald = getBlastReduction((EntityLivingBase)entity, getDamageMultiplied(damage), new Explosion((World)ConstAuraOffhand.mc.world, (Entity)null, posX, posY, posZ, 6.0f, false, true));
        }
        return (float)finald;
    }
    
    private static float getBlastReduction(final EntityLivingBase entity, float damage, final Explosion explosion) {
        if (entity instanceof EntityPlayer) {
            final EntityPlayer ep = (EntityPlayer)entity;
            final DamageSource ds = DamageSource.causeExplosionDamage(explosion);
            damage = CombatRules.getDamageAfterAbsorb(damage, (float)ep.getTotalArmorValue(), (float)ep.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
            final int k = EnchantmentHelper.getEnchantmentModifierDamage(ep.getArmorInventoryList(), ds);
            final float f = MathHelper.clamp((float)k, 0.0f, 20.0f);
            damage *= 1.0f - f / 25.0f;
            if (entity.isPotionActive(MobEffects.RESISTANCE)) {
                damage -= damage / 4.0f;
            }
            return damage;
        }
        damage = CombatRules.getDamageAfterAbsorb(damage, (float)entity.getTotalArmorValue(), (float)entity.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
        return damage;
    }
    
    private static float getDamageMultiplied(final float damage) {
        final int diff = ConstAuraOffhand.mc.world.getDifficulty().getId();
        return damage * ((diff == 0) ? 0.0f : ((diff == 2) ? 1.0f : ((diff == 1) ? 0.5f : 1.5f)));
    }
    
    private static void setYawAndPitch(final float yaw1, final float pitch1) {
        ConstAuraOffhand.yaw = yaw1;
        ConstAuraOffhand.pitch = pitch1;
        ConstAuraOffhand.isSpoofingAngles = true;
    }
    
    private static void resetRotation() {
        if (ConstAuraOffhand.isSpoofingAngles) {
            ConstAuraOffhand.yaw = ConstAuraOffhand.mc.player.rotationYaw;
            ConstAuraOffhand.pitch = ConstAuraOffhand.mc.player.rotationPitch;
            ConstAuraOffhand.isSpoofingAngles = false;
        }
    }
    
    public void onWorldRender(final RenderEvent event) {
        if (this.renderBlock != null && !this.renderMode.getValue().equals(RenderMode.NONE) && this.renderDmg != null) {
            if (this.boxRainbow.getValue()) {
                this.drawBlock(this.renderBlock, Rainbow.INSTANCE.getC().getRed(), Rainbow.INSTANCE.getC().getGreen(), Rainbow.INSTANCE.getC().getBlue(), this.renderDmg);
            }
            else {
                this.drawBlock(this.renderBlock, this.boxRed.getValue(), this.boxGreen.getValue(), this.boxBlue.getValue(), this.renderDmg);
            }
        }
    }
    
    private void drawBlock(final BlockPos blockPos, final int r, final int g, final int b, final String str) {
        final Color color = new Color(r, g, b, this.boxAlpha.getValue());
        Color colorBound;
        if (this.boundRainbow.getValue()) {
            colorBound = new Color(Rainbow.INSTANCE.getC().getRed(), Rainbow.INSTANCE.getC().getGreen(), Rainbow.INSTANCE.getC().getBlue(), this.boundAlpha.getValue());
        }
        else {
            colorBound = new Color(this.boundRed.getValue(), this.boundGreen.getValue(), this.boundBlue.getValue(), this.boundAlpha.getValue());
        }
        KamiTessellator.prepare(7);
        if (this.renderMode.getValue().equals(RenderMode.UP)) {
            KamiTessellator.drawBox(blockPos, color.getRGB(), 2);
        }
        else if (this.renderMode.getValue().equals(RenderMode.BLOCK)) {
            KamiTessellator.drawBox(blockPos, color.getRGB(), 63);
        }
        KamiTessellator.release();
        if (this.renderMode.getValue().equals(RenderMode.BLOCK)) {
            KamiTessellator.prepareGL();
            KamiTessellator.drawBoundingBox(blockPos, 5.0f, colorBound.getRGB());
            KamiTessellator.releaseGL();
        }
        this.drawString(blockPos, str);
    }
    
    public static Vec3d getEyesPos() {
        return new Vec3d(ConstAuraOffhand.mc.player.posX, ConstAuraOffhand.mc.player.posY + ConstAuraOffhand.mc.player.getEyeHeight(), ConstAuraOffhand.mc.player.posZ);
    }
    
    public static Vec3d getInterpolatedAmount(final BlockPos blockPos, final double x, final double y, final double z) {
        return new Vec3d((blockPos.x - blockPos.x) * x, (blockPos.y - blockPos.y) * y, (blockPos.z - blockPos.z) * z);
    }
    
    public static Vec3d getInterpolatedAmount(final BlockPos blockPos) {
        return getInterpolatedAmount(blockPos, blockPos.x, blockPos.y, blockPos.z);
    }
    
    public static Vec3d getInterpolatedPos(final BlockPos blockPos) {
        return new Vec3d((double)blockPos.x, (double)blockPos.y, (double)blockPos.z).add(getInterpolatedAmount(blockPos));
    }
    
    public static Vec3d getInterpolatedRenderPos(final BlockPos blockPos) {
        return getInterpolatedPos(blockPos).subtract(Wrapper.getMinecraft().getRenderManager().renderPosX, Wrapper.getMinecraft().getRenderManager().renderPosY, Wrapper.getMinecraft().getRenderManager().renderPosZ);
    }
    
    public static void glBillboard(final float x, final float y, final float z) {
        final float scale = 0.02666667f;
        GlStateManager.translate(x - ConstAuraOffhand.mc.getRenderManager().renderPosX, y - ConstAuraOffhand.mc.getRenderManager().renderPosY, z - ConstAuraOffhand.mc.getRenderManager().renderPosZ);
        GlStateManager.glNormal3f(0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(-ConstAuraOffhand.mc.player.rotationYaw, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(ConstAuraOffhand.mc.player.rotationPitch, (ConstAuraOffhand.mc.gameSettings.thirdPersonView == 2) ? -1.0f : 1.0f, 0.0f, 0.0f);
        GlStateManager.scale(-scale, -scale, scale);
    }
    
    public static void glBillboardDistanceScaled(final float x, final float y, final float z, final EntityPlayer player, final float scale) {
        glBillboard(x, y, z);
        final int distance = (int)player.getDistance((double)x, (double)y, (double)z);
        float scaleDistance = distance / 2.0f / (2.0f + (2.0f - scale));
        if (scaleDistance < 1.0f) {
            scaleDistance = 1.0f;
        }
        GlStateManager.scale(scaleDistance, scaleDistance, scaleDistance);
    }
    
    private void drawString(final BlockPos blockPos, final String str) {
        GlStateManager.pushMatrix();
        glBillboardDistanceScaled(blockPos.x + 0.5f, blockPos.y + 0.5f, blockPos.z + 0.5f, (EntityPlayer)ConstAuraOffhand.mc.player, 1.5f);
        GlStateManager.disableDepth();
        GlStateManager.translate(-(ConstAuraOffhand.mc.fontRenderer.getStringWidth(str) / 2.0), 0.0, 0.0);
        ConstAuraOffhand.mc.fontRenderer.drawStringWithShadow(str, 0.0f, 0.0f, -10027024);
        GlStateManager.popMatrix();
    }
    
    private boolean shouldTotem() {
        final boolean hp = ConstAuraOffhand.mc.player.getHealth() + ConstAuraOffhand.mc.player.getAbsorptionAmount() <= this.health.getValue();
        return hp;
    }
    
    public void onUpdate() {
        if (ConstAuraOffhand.mc.player == null) {
            return;
        }
        if (this.shouldTotem()) {
            this.item = Items.TOTEM_OF_UNDYING;
        }
        else {
            this.item = Items.END_CRYSTAL;
        }
        if (ConstAuraOffhand.mc.currentScreen instanceof GuiContainer) {
            return;
        }
        if (this.returnI) {
            int t = -1;
            for (int i = 0; i < 45; ++i) {
                if (ConstAuraOffhand.mc.player.inventory.getStackInSlot(i).isEmpty) {
                    t = i;
                    break;
                }
            }
            if (t == -1) {
                return;
            }
            ConstAuraOffhand.mc.playerController.windowClick(0, (t < 9) ? (t + 36) : t, 0, ClickType.PICKUP, (EntityPlayer)ConstAuraOffhand.mc.player);
            this.returnI = false;
        }
        this.crystals = ConstAuraOffhand.mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == this.item).mapToInt(ItemStack::getCount).sum();
        if (ConstAuraOffhand.mc.player.getHeldItemOffhand().getItem() == this.item) {
            ++this.crystals;
        }
        else {
            if (ModuleManager.isModuleEnabled("OffhandGapple")) {
                return;
            }
            if (this.soft.getValue() && !ConstAuraOffhand.mc.player.getHeldItemOffhand().isEmpty) {
                return;
            }
            if (this.moving) {
                ConstAuraOffhand.mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, (EntityPlayer)ConstAuraOffhand.mc.player);
                this.moving = false;
                if (!ConstAuraOffhand.mc.player.inventory.itemStack.isEmpty()) {
                    this.returnI = true;
                }
                return;
            }
            if (ConstAuraOffhand.mc.player.inventory.itemStack.isEmpty()) {
                if (this.crystals == 0) {
                    return;
                }
                int t = -1;
                for (int i = 0; i < 45; ++i) {
                    if (ConstAuraOffhand.mc.player.inventory.getStackInSlot(i).getItem() == this.item) {
                        t = i;
                        break;
                    }
                }
                if (t == -1) {
                    return;
                }
                ConstAuraOffhand.mc.playerController.windowClick(0, (t < 9) ? (t + 36) : t, 0, ClickType.PICKUP, (EntityPlayer)ConstAuraOffhand.mc.player);
                this.moving = true;
            }
            else if (!this.soft.getValue()) {
                int t = -1;
                for (int i = 0; i < 45; ++i) {
                    if (ConstAuraOffhand.mc.player.inventory.getStackInSlot(i).isEmpty) {
                        t = i;
                        break;
                    }
                }
                if (t == -1) {
                    return;
                }
                ConstAuraOffhand.mc.playerController.windowClick(0, (t < 9) ? (t + 36) : t, 0, ClickType.PICKUP, (EntityPlayer)ConstAuraOffhand.mc.player);
            }
        }
        ConstAuraOffhand.isActive = false;
        final EntityEnderCrystal crystal = (EntityEnderCrystal)ConstAuraOffhand.mc.world.loadedEntityList.stream().filter(entity -> entity instanceof EntityEnderCrystal).filter(e -> ConstAuraOffhand.mc.player.getDistance(e) <= this.hitRange.getValue()).map(entity -> entity).min(Comparator.comparing(c -> ConstAuraOffhand.mc.player.getDistance(c))).orElse(null);
        if (this.explode.getValue() && crystal != null) {
            if (ConstAuraOffhand.mc.player.canEntityBeSeen((Entity)crystal) || ConstAuraOffhand.mc.player.getDistance((Entity)crystal) <= this.wallRange.getValue()) {
                if (this.waitTick.getValue() > 0) {
                    if (this.waitCounter < this.waitTick.getValue()) {
                        ++this.waitCounter;
                        return;
                    }
                    this.waitCounter = 0;
                }
                final double selfDamage = calculateDamage(crystal.posX, crystal.posY, crystal.posZ, (Entity)ConstAuraOffhand.mc.player);
                if (selfDamage < this.maxSelfDmg.getValue()) {
                    if (this.rotate.getValue()) {
                        this.lookAtPacket(crystal.posX, crystal.posY, crystal.posZ, (EntityPlayer)ConstAuraOffhand.mc.player);
                    }
                    ConstAuraOffhand.isActive = true;
                    ConstAuraOffhand.mc.player.swingArm(EnumHand.MAIN_HAND);
                    ConstAuraOffhand.mc.playerController.attackEntity((EntityPlayer)ConstAuraOffhand.mc.player, (Entity)crystal);
                    ConstAuraOffhand.isActive = false;
                }
            }
        }
        else {
            resetRotation();
            if (this.oldSlot != -1) {
                ConstAuraOffhand.mc.player.inventory.currentItem = this.oldSlot;
                this.oldSlot = -1;
            }
            ConstAuraOffhand.isActive = false;
        }
        int crystalSlot = (ConstAuraOffhand.mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL) ? ConstAuraOffhand.mc.player.inventory.currentItem : -1;
        if (crystalSlot == -1) {
            for (int l = 0; l < 9; ++l) {
                if (ConstAuraOffhand.mc.player.inventory.getStackInSlot(l).getItem() == Items.END_CRYSTAL) {
                    crystalSlot = l;
                    break;
                }
            }
        }
        boolean offhand = false;
        if (ConstAuraOffhand.mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) {
            offhand = true;
        }
        else if (crystalSlot == -1) {
            return;
        }
        if (this.place.getValue()) {
            final List<Entity> entities = (List<Entity>)ConstAuraOffhand.mc.world.playerEntities.stream().filter(entityPlayer -> !Friends.isFriend(entityPlayer.getName())).sorted((entity1, entity2) -> Float.compare(ConstAuraOffhand.mc.player.getDistance(entity1), ConstAuraOffhand.mc.player.getDistance(entity2))).collect(Collectors.toList());
            final List<BlockPos> blocks = this.findCrystalBlocks();
            BlockPos targetBlock = null;
            double lastDamage = 0.0;
            this.target = null;
            for (final Entity entity3 : entities) {
                if (entity3 == ConstAuraOffhand.mc.player) {
                    continue;
                }
                if (!(entity3 instanceof EntityPlayer)) {
                    continue;
                }
                final EntityPlayer testTarget = (EntityPlayer)entity3;
                if (testTarget.isDead) {
                    continue;
                }
                if (testTarget.getHealth() <= 0.0f) {
                    continue;
                }
                for (final BlockPos blockPos : blocks) {
                    if (testTarget.getDistanceSq(blockPos) >= this.targetRange.getValue() * this.targetRange.getValue()) {
                        continue;
                    }
                    final double targetDamage = calculateDamage(blockPos.x + 0.5, blockPos.y + 1, blockPos.z + 0.5, (Entity)testTarget);
                    final double selfDamage2 = calculateDamage(blockPos.x + 0.5, blockPos.y + 1, blockPos.z + 0.5, (Entity)ConstAuraOffhand.mc.player);
                    final float healthTarget = testTarget.getHealth() + testTarget.getAbsorptionAmount();
                    final float healthSelf = ConstAuraOffhand.mc.player.getHealth() + ConstAuraOffhand.mc.player.getAbsorptionAmount();
                    if (selfDamage2 >= healthSelf - 0.5) {
                        continue;
                    }
                    if (selfDamage2 > this.maxSelfDmg.getValue()) {
                        continue;
                    }
                    if (selfDamage2 > targetDamage && targetDamage < healthTarget) {
                        continue;
                    }
                    if (targetDamage <= 2.0) {
                        continue;
                    }
                    if (targetDamage < this.minDamage.getValue() && healthTarget > this.facePlace.getValue()) {
                        continue;
                    }
                    if (targetDamage <= lastDamage) {
                        continue;
                    }
                    targetBlock = blockPos;
                    lastDamage = targetDamage;
                    this.target = testTarget;
                    this.renderDmg = String.valueOf((int)targetDamage);
                }
                if (this.target != null) {
                    break;
                }
            }
            if (this.target == null) {
                this.renderBlock = null;
                resetRotation();
                return;
            }
            this.renderBlock = targetBlock;
            if (ModuleManager.getModuleByName("AutoGG").isEnabled()) {
                final AutoGG autoGG = (AutoGG)ModuleManager.getModuleByName("AutoGG");
                autoGG.addTargetedPlayer(this.target.getName());
            }
            if (ModuleManager.getModuleByName("TotemPopCounter").isEnabled()) {
                final TotemPopCounter tpc = (TotemPopCounter)ModuleManager.getModuleByName("TotemPopCounter");
                tpc.addTargetedPlayer(this.target.getName());
            }
            if (!offhand && ConstAuraOffhand.mc.player.inventory.currentItem != crystalSlot) {
                if (this.autoSwitch.getValue()) {
                    ConstAuraOffhand.mc.player.inventory.currentItem = crystalSlot;
                    resetRotation();
                    this.switchCooldown = true;
                }
                return;
            }
            if (this.rotate.getValue()) {
                this.lookAtPacket(targetBlock.x + 0.5, targetBlock.y - 0.5, targetBlock.z + 0.5, (EntityPlayer)ConstAuraOffhand.mc.player);
            }
            EnumFacing f;
            if (this.rayTrace.getValue()) {
                final RayTraceResult result = ConstAuraOffhand.mc.world.rayTraceBlocks(new Vec3d(ConstAuraOffhand.mc.player.posX, ConstAuraOffhand.mc.player.posY + ConstAuraOffhand.mc.player.getEyeHeight(), ConstAuraOffhand.mc.player.posZ), new Vec3d(targetBlock.x + 0.5, targetBlock.y - 0.5, targetBlock.z + 0.5));
                if (result == null || result.sideHit == null) {
                    f = EnumFacing.UP;
                }
                else {
                    f = result.sideHit;
                }
            }
            else {
                f = EnumFacing.UP;
            }
            if (this.switchCooldown) {
                this.switchCooldown = false;
                return;
            }
            ConstAuraOffhand.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(targetBlock, f, offhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
        }
        if (this.spoofRotations.getValue() && ConstAuraOffhand.isSpoofingAngles) {
            if (ConstAuraOffhand.togglePitch) {
                final EntityPlayerSP player = ConstAuraOffhand.mc.player;
                player.rotationPitch += (float)4.0E-4;
                ConstAuraOffhand.togglePitch = false;
            }
            else {
                final EntityPlayerSP player2 = ConstAuraOffhand.mc.player;
                player2.rotationPitch -= (float)4.0E-4;
                ConstAuraOffhand.togglePitch = true;
            }
        }
    }
    
    private void lookAtPacket(final double px, final double py, final double pz, final EntityPlayer me) {
        final double[] v = EntityUtil.calculateLookAt(px, py, pz, me);
        setYawAndPitch((float)v[0], (float)v[1]);
    }
    
    private boolean canPlaceCrystal(final BlockPos blockPos) {
        final BlockPos boost = blockPos.add(0, 1, 0);
        final BlockPos boost2 = blockPos.add(0, 2, 0);
        return (ConstAuraOffhand.mc.world.getBlockState(blockPos).getBlock() == Blocks.BEDROCK || ConstAuraOffhand.mc.world.getBlockState(blockPos).getBlock() == Blocks.OBSIDIAN) && ConstAuraOffhand.mc.world.getBlockState(boost).getBlock() == Blocks.AIR && ConstAuraOffhand.mc.world.getBlockState(boost2).getBlock() == Blocks.AIR && ConstAuraOffhand.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(boost)).isEmpty() && ConstAuraOffhand.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(boost2)).isEmpty();
    }
    
    private List<BlockPos> findCrystalBlocks() {
        final NonNullList<BlockPos> positions = (NonNullList<BlockPos>)NonNullList.create();
        positions.addAll((Collection)this.getSphere(getPlayerPos(), this.placeRange.getValue().floatValue(), this.placeRange.getValue().intValue(), false, true, 0).stream().filter((Predicate<? super Object>)this::canPlaceCrystal).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()));
        return (List<BlockPos>)positions;
    }
    
    public List<BlockPos> getSphere(final BlockPos loc, final float r, final int h, final boolean hollow, final boolean sphere, final int plus_y) {
        final List<BlockPos> circleblocks = new ArrayList<BlockPos>();
        final int cx = loc.getX();
        final int cy = loc.getY();
        final int cz = loc.getZ();
        for (int x = cx - (int)r; x <= cx + r; ++x) {
            for (int z = cz - (int)r; z <= cz + r; ++z) {
                for (int y = sphere ? (cy - (int)r) : cy; y < (sphere ? (cy + r) : ((float)(cy + h))); ++y) {
                    final double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? ((cy - y) * (cy - y)) : 0);
                    if (dist < r * r && (!hollow || dist >= (r - 1.0f) * (r - 1.0f))) {
                        final BlockPos l = new BlockPos(x, y + plus_y, z);
                        circleblocks.add(l);
                    }
                }
            }
        }
        return circleblocks;
    }
    
    public void onEnable() {
        if (ConstAuraOffhand.mc.world == null) {
            return;
        }
        if (ModuleManager.getModuleByName("OffhandGapple").isEnabled()) {
            ModuleManager.getModuleByName("OffhandGapple").disable();
        }
        if (ModuleManager.getModuleByName("AutoTotem").isEnabled()) {
            ModuleManager.getModuleByName("AutoTotem").disable();
        }
        if (this.noTrace.getValue() && !ModuleManager.getModuleByName("NoEntityTrace").isEnabled()) {
            NoEntityTrace.INSTANCE.mode.setValue(NoEntityTrace.TraceMode.STATIC);
            ModuleManager.getModuleByName("NoEntityTrace").enable();
            this.turnTraceOff = true;
        }
        if (this.announceUsage.getValue()) {
            Command.sendChatMessage("[OffhandAC] " + ChatFormatting.GREEN.toString() + "Enabled!");
        }
    }
    
    public void onDisable() {
        if (!this.totemOnDisable.getValue()) {
            return;
        }
        final AutoTotem autoTotem = (AutoTotem)ModuleManager.getModuleByName("AutoTotem");
        autoTotem.disableSoft();
        if (autoTotem.isDisabled()) {
            autoTotem.enable();
        }
        if (this.turnTraceOff) {
            ModuleManager.getModuleByName("NoEntityTrace").disable();
            this.turnTraceOff = false;
        }
        this.renderBlock = null;
        this.target = null;
        resetRotation();
        if (this.announceUsage.getValue()) {
            Command.sendChatMessage("[OffhandAC] " + ChatFormatting.RED.toString() + "Disabled!");
        }
    }
    
    public String getHudInfo() {
        if (this.target == null) {
            return "";
        }
        return this.target.getName().toUpperCase();
    }
    
    public static boolean isPlacing() {
        return ConstAuraOffhand.isActive;
    }
    
    static {
        ConstAuraOffhand.togglePitch = false;
    }
    
    private enum RenderMode
    {
        UP, 
        BLOCK, 
        NONE;
    }
    
    private enum Page
    {
        PLACE, 
        HIT, 
        MISC, 
        COLOR, 
        OFFHAND;
    }
}
