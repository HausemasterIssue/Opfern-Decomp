//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.module.modules.combat;

import net.minecraft.entity.player.*;
import me.zero.alpine.listener.*;
import me.zeroeightsix.kami.setting.*;
import java.util.function.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.enchantment.*;
import me.zeroeightsix.kami.event.events.*;
import java.awt.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.item.*;
import java.util.stream.*;
import me.zeroeightsix.kami.module.*;
import net.minecraft.network.*;
import net.minecraft.client.*;
import me.zeroeightsix.kami.util.*;
import net.minecraft.util.math.*;
import java.util.*;
import me.zeroeightsix.kami.module.modules.misc.*;
import com.mojang.realmsclient.gui.*;
import me.zeroeightsix.kami.command.*;
import net.minecraft.network.play.server.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.network.play.client.*;

@Module.Info(name = "AutoCrystal", category = Module.Category.COMBAT)
public class CrystalAura extends Module
{
    private static double yaw;
    private static double pitch;
    private long placeSystemTime;
    private long multiPlaceSystemTime;
    private long hitSystemTime;
    private String renderDmg;
    private Setting<Page> p;
    private Setting<Boolean> place;
    private Setting<Boolean> multiplace;
    private Setting<Boolean> rayTrace;
    private Setting<Boolean> autoSwitch;
    private Setting<Double> placeRange;
    private Setting<Boolean> rotatePlace;
    private Setting<Integer> multiPlaceSpeed;
    private Setting<Integer> placeDelay;
    private Setting<Double> minDamage;
    private Setting<Integer> maxSelfDmg;
    private Setting<Double> facePlace;
    private Setting<Boolean> noTrace;
    private Setting<Integer> targetRange;
    private Setting<Boolean> explode;
    private Setting<AttackMode> attackMode;
    private Setting<Double> hitRange;
    private Setting<Double> wallRange;
    private Setting<Boolean> rotateHit;
    private Setting<Integer> attackDelay;
    private Setting<Integer> waitTick;
    private Setting<Double> minDamagee;
    private Setting<RenderMode> renderMode;
    private Setting<Boolean> announceUsage;
    private Setting<Boolean> nodesync;
    private Setting<SpeedMode> speedMode;
    private Setting<Boolean> dmgRender;
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
    private BlockPos renderBlockE;
    private EntityPlayer target;
    private boolean turnTraceOff;
    private boolean switchCooldown;
    private int oldSlot;
    private int waitCounter;
    private static boolean isActive;
    @EventHandler
    private Listener<PacketEvent.Send> packetListener;
    @EventHandler
    private Listener<PacketEvent.Receive> packetReceiveListener;
    
    public CrystalAura() {
        this.placeSystemTime = -1L;
        this.multiPlaceSystemTime = -1L;
        this.hitSystemTime = -1L;
        this.p = (Setting<Page>)this.register((Setting)Settings.enumBuilder(Page.class).withName("Page").withValue(Page.PLACE).build());
        this.place = (Setting<Boolean>)this.register((Setting)Settings.booleanBuilder("Place").withValue(true).withVisibility(v -> this.p.getValue().equals(Page.PLACE)).build());
        this.multiplace = (Setting<Boolean>)this.register((Setting)Settings.booleanBuilder("MultiPlace").withValue(true).withVisibility(v -> this.p.getValue().equals(Page.PLACE)).build());
        this.rayTrace = (Setting<Boolean>)this.register((Setting)Settings.booleanBuilder("RayTracePlace").withValue(false).withVisibility(v -> this.p.getValue().equals(Page.PLACE)).build());
        this.autoSwitch = (Setting<Boolean>)this.register((Setting)Settings.booleanBuilder("Auto Switch").withValue(true).withVisibility(v -> this.p.getValue().equals(Page.PLACE)).build());
        this.placeRange = (Setting<Double>)this.register((Setting)Settings.doubleBuilder("Place Range").withMinimum(0.0).withValue(3.5).withMaximum(10.0).withVisibility(v -> this.p.getValue().equals(Page.PLACE)).build());
        this.rotatePlace = (Setting<Boolean>)this.register((Setting)Settings.booleanBuilder("RotatePlace").withValue(false).withVisibility(v -> this.p.getValue().equals(Page.MISC)).build());
        this.multiPlaceSpeed = (Setting<Integer>)this.register((Setting)Settings.integerBuilder("MultiPlaceSpeed").withMinimum(1).withValue(2).withMaximum(10).withVisibility(v -> this.p.getValue().equals(Page.PLACE)).build());
        this.placeDelay = (Setting<Integer>)this.register((Setting)Settings.integerBuilder("PlaceDelay").withMinimum(0).withValue(10).withMaximum(100).withVisibility(v -> this.p.getValue().equals(Page.PLACE)).build());
        this.minDamage = (Setting<Double>)this.register((Setting)Settings.doubleBuilder("Min Damage").withMinimum(0.0).withValue(6.0).withMaximum(36.0).withVisibility(v -> this.p.getValue().equals(Page.PLACE)).build());
        this.maxSelfDmg = (Setting<Integer>)this.register((Setting)Settings.integerBuilder("Max SelfDmg").withMinimum(0).withValue(10).withMaximum(36).withVisibility(v -> this.p.getValue().equals(Page.PLACE)).build());
        this.facePlace = (Setting<Double>)this.register((Setting)Settings.doubleBuilder("FacePlaceHealth").withMinimum(0.0).withValue(6.0).withMaximum(36.0).withVisibility(v -> this.p.getValue().equals(Page.PLACE)).build());
        this.noTrace = (Setting<Boolean>)this.register((Setting)Settings.booleanBuilder("EnableNoTrace").withValue(true).withVisibility(v -> this.p.getValue().equals(Page.PLACE)).build());
        this.targetRange = (Setting<Integer>)this.register((Setting)Settings.integerBuilder("MaxCDist").withMinimum(0).withValue(10).withMaximum(13).withVisibility(v -> this.p.getValue().equals(Page.PLACE)).build());
        this.explode = (Setting<Boolean>)this.register((Setting)Settings.booleanBuilder("Explode").withValue(true).withVisibility(v -> this.p.getValue().equals(Page.HIT)).build());
        this.attackMode = (Setting<AttackMode>)this.register((Setting)Settings.enumBuilder(AttackMode.class).withName("AttackMode").withValue(AttackMode.MS).withVisibility(v -> this.p.getValue().equals(Page.HIT)).build());
        this.hitRange = (Setting<Double>)this.register((Setting)Settings.doubleBuilder("Hit Range").withMinimum(0.0).withValue(5.0).withMaximum(10.0).withVisibility(v -> this.p.getValue().equals(Page.HIT)).build());
        this.wallRange = (Setting<Double>)this.register((Setting)Settings.doubleBuilder("Walls Range").withMinimum(0.0).withValue(3.0).withMaximum(10.0).withVisibility(v -> this.p.getValue().equals(Page.HIT)).build());
        this.rotateHit = (Setting<Boolean>)this.register((Setting)Settings.booleanBuilder("RotateHit").withValue(true).withVisibility(v -> this.p.getValue().equals(Page.MISC)).build());
        this.attackDelay = (Setting<Integer>)this.register((Setting)Settings.integerBuilder("Attack Delay").withMinimum(0).withValue(17).withMaximum(100).withVisibility(v -> this.p.getValue().equals(Page.HIT) && this.attackMode.getValue() == AttackMode.MS).build());
        this.waitTick = (Setting<Integer>)this.register((Setting)Settings.integerBuilder("Tick Delay").withMinimum(0).withValue(5).withMaximum(20).withVisibility(v -> this.p.getValue().equals(Page.HIT) && this.attackMode.getValue() == AttackMode.TICK).build());
        this.minDamagee = (Setting<Double>)this.register((Setting)Settings.doubleBuilder("Min Damage Hit").withMinimum(0.0).withValue(2.0).withMaximum(36.0).withVisibility(v -> this.p.getValue().equals(Page.HIT)).build());
        this.renderMode = (Setting<RenderMode>)this.register((Setting)Settings.enumBuilder(RenderMode.class).withName("RenderMode").withValue(RenderMode.BLOCK).withVisibility(v -> this.p.getValue().equals(Page.MISC)).build());
        this.announceUsage = (Setting<Boolean>)this.register((Setting)Settings.booleanBuilder("Announce Usage").withValue(true).withVisibility(v -> this.p.getValue().equals(Page.MISC)).build());
        this.nodesync = (Setting<Boolean>)this.register((Setting)Settings.booleanBuilder("No Desync").withValue(true).withVisibility(v -> this.p.getValue().equals(Page.MISC)).build());
        this.speedMode = (Setting<SpeedMode>)this.register((Setting)Settings.enumBuilder(SpeedMode.class).withName("SpeedMode").withValue(SpeedMode.FAST).withVisibility(v -> this.p.getValue().equals(Page.MISC)).build());
        this.dmgRender = (Setting<Boolean>)this.register((Setting)Settings.booleanBuilder("Render Damage").withValue(true).withVisibility(v -> this.p.getValue().equals(Page.MISC)).build());
        this.boxRainbow = (Setting<Boolean>)this.register((Setting)Settings.booleanBuilder("Rainbow").withValue(false).withVisibility(v -> this.p.getValue().equals(Page.COLOR)).build());
        this.boundRainbow = (Setting<Boolean>)this.register((Setting)Settings.booleanBuilder("BoundingRainbow").withValue(false).withVisibility(v -> this.p.getValue().equals(Page.COLOR)).build());
        this.boxRed = (Setting<Integer>)this.register((Setting)Settings.integerBuilder("Red").withMinimum(0).withMaximum(255).withValue(255).withVisibility(v -> this.p.getValue().equals(Page.COLOR) && !this.boxRainbow.getValue()).build());
        this.boxGreen = (Setting<Integer>)this.register((Setting)Settings.integerBuilder("Green").withMinimum(0).withMaximum(255).withValue(255).withVisibility(v -> this.p.getValue().equals(Page.COLOR) && !this.boxRainbow.getValue()).build());
        this.boxBlue = (Setting<Integer>)this.register((Setting)Settings.integerBuilder("Blue").withMinimum(0).withMaximum(255).withValue(255).withVisibility(v -> this.p.getValue().equals(Page.COLOR) && !this.boxRainbow.getValue()).build());
        this.boxAlpha = (Setting<Integer>)this.register((Setting)Settings.integerBuilder("Alpha").withMinimum(0).withMaximum(255).withValue(60).withVisibility(v -> this.p.getValue().equals(Page.COLOR)).build());
        this.boundRed = (Setting<Integer>)this.register((Setting)Settings.integerBuilder("BoundingRed").withMinimum(0).withMaximum(255).withValue(255).withVisibility(v -> this.p.getValue().equals(Page.COLOR) && !this.boundRainbow.getValue()).build());
        this.boundGreen = (Setting<Integer>)this.register((Setting)Settings.integerBuilder("BoundingGreen").withMinimum(0).withMaximum(255).withValue(255).withVisibility(v -> this.p.getValue().equals(Page.COLOR) && !this.boundRainbow.getValue()).build());
        this.boundBlue = (Setting<Integer>)this.register((Setting)Settings.integerBuilder("BoundingBlue").withMinimum(0).withMaximum(255).withValue(255).withVisibility(v -> this.p.getValue().equals(Page.COLOR) && !this.boundRainbow.getValue()).build());
        this.boundAlpha = (Setting<Integer>)this.register((Setting)Settings.integerBuilder("BoundingAlpha").withMinimum(0).withMaximum(255).withValue(60).withVisibility(v -> this.p.getValue().equals(Page.COLOR)).build());
        this.switchCooldown = false;
        this.oldSlot = -1;
        this.packetListener = (Listener<PacketEvent.Send>)new Listener(event -> {
            if (!this.rotateHit.getValue()) {
                return;
            }
            final Packet packet = event.getPacket();
            if (packet instanceof CPacketPlayer) {
                ((CPacketPlayer)packet).yaw = (float)CrystalAura.yaw;
                ((CPacketPlayer)packet).pitch = (float)CrystalAura.pitch;
            }
        }, new Predicate[0]);
        this.packetReceiveListener = (Listener<PacketEvent.Receive>)new Listener(event -> {
            if (event.getPacket() instanceof SPacketSoundEffect && this.nodesync.getValue()) {
                final SPacketSoundEffect packet = (SPacketSoundEffect)event.getPacket();
                if (packet.getCategory() == SoundCategory.BLOCKS && packet.getSound() == SoundEvents.ENTITY_GENERIC_EXPLODE) {
                    for (final Entity e : CrystalAura.mc.world.loadedEntityList) {
                        if (e instanceof EntityEnderCrystal && e.getDistance(packet.getX(), packet.getY(), packet.getZ()) <= 6.0) {
                            e.setDead();
                        }
                    }
                }
            }
        }, new Predicate[0]);
    }
    
    public static BlockPos getPlayerPos() {
        return new BlockPos(Math.floor(CrystalAura.mc.player.posX), Math.floor(CrystalAura.mc.player.posY), Math.floor(CrystalAura.mc.player.posZ));
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
            finald = getBlastReduction((EntityLivingBase)entity, getDamageMultiplied(damage), new Explosion((World)CrystalAura.mc.world, (Entity)null, posX, posY, posZ, 6.0f, false, true));
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
        final int diff = CrystalAura.mc.world.getDifficulty().getId();
        return damage * ((diff == 0) ? 0.0f : ((diff == 2) ? 1.0f : ((diff == 1) ? 0.5f : 1.5f)));
    }
    
    private static void setYawAndPitch(final float yaw1, final float pitch1) {
        CrystalAura.yaw = yaw1;
        CrystalAura.pitch = pitch1;
    }
    
    private static void resetRotation() {
        CrystalAura.yaw = CrystalAura.mc.player.rotationYaw;
        CrystalAura.pitch = CrystalAura.mc.player.rotationPitch;
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
        if (this.dmgRender.getValue()) {
            this.drawString(blockPos, str);
        }
    }
    
    public static Vec3d getEyesPos() {
        return new Vec3d(CrystalAura.mc.player.posX, CrystalAura.mc.player.posY + CrystalAura.mc.player.getEyeHeight(), CrystalAura.mc.player.posZ);
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
        GlStateManager.translate(x - CrystalAura.mc.getRenderManager().renderPosX, y - CrystalAura.mc.getRenderManager().renderPosY, z - CrystalAura.mc.getRenderManager().renderPosZ);
        GlStateManager.glNormal3f(0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(-CrystalAura.mc.player.rotationYaw, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(CrystalAura.mc.player.rotationPitch, (CrystalAura.mc.gameSettings.thirdPersonView == 2) ? -1.0f : 1.0f, 0.0f, 0.0f);
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
        glBillboardDistanceScaled(blockPos.x + 0.5f, blockPos.y + 0.5f, blockPos.z + 0.5f, (EntityPlayer)CrystalAura.mc.player, 1.5f);
        GlStateManager.disableDepth();
        GlStateManager.translate(-(CrystalAura.mc.fontRenderer.getStringWidth(str) / 2.0), 0.0, 0.0);
        CrystalAura.mc.fontRenderer.drawStringWithShadow(str, 0.0f, 0.0f, -10027024);
        GlStateManager.popMatrix();
    }
    
    public int getAttackDelay() {
        switch (this.speedMode.getValue()) {
            case FAST: {
                return this.attackDelay.getValue() * 2;
            }
            default: {
                return this.attackDelay.getValue() * 15;
            }
        }
    }
    
    public int getPlaceDelay() {
        switch (this.speedMode.getValue()) {
            case FAST: {
                return this.placeDelay.getValue() * 2;
            }
            default: {
                return this.placeDelay.getValue() * 15;
            }
        }
    }
    
    public int getMultiPlace() {
        switch (this.speedMode.getValue()) {
            case FAST: {
                return this.multiPlaceSpeed.getValue() * 15;
            }
            default: {
                return this.multiPlaceSpeed.getValue() * 30;
            }
        }
    }
    
    public void onUpdate() {
        if (CrystalAura.mc.player == null) {
            return;
        }
        if (this.explode.getValue()) {
            if (this.attackMode.getValue() == AttackMode.TICK && this.waitTick.getValue() > 0) {
                if (this.waitCounter < this.waitTick.getValue()) {
                    ++this.waitCounter;
                    return;
                }
                this.waitCounter = 0;
            }
            CrystalAura.isActive = false;
            if (this.attackMode.getValue() == AttackMode.MS && System.nanoTime() / 1000000L - this.hitSystemTime <= this.getAttackDelay()) {
                return;
            }
            for (final Entity entity3 : CrystalAura.mc.world.loadedEntityList) {
                if (entity3 != null && entity3 instanceof EntityEnderCrystal && CrystalAura.mc.player.getDistance(entity3) <= this.hitRange.getValue()) {
                    if (!CrystalAura.mc.player.canEntityBeSeen(entity3) && CrystalAura.mc.player.getDistance(entity3) > this.wallRange.getValue()) {
                        continue;
                    }
                    this.renderBlockE = null;
                    for (final Entity ent : CrystalAura.mc.world.loadedEntityList) {
                        if (ent != null && ent != CrystalAura.mc.player && ent.getDistance(entity3) <= 14.0f && ent != entity3 && ent instanceof EntityPlayer) {
                            final EntityPlayer player = (EntityPlayer)ent;
                            if (player.isDead) {
                                continue;
                            }
                            if (player.getHealth() <= 0.0f) {
                                continue;
                            }
                            if (Friends.isFriend(player.getName())) {
                                continue;
                            }
                            final float currentDamage = calculateDamage(entity3.posX, entity3.posY, entity3.posZ, (Entity)player);
                            final float localDamage = calculateDamage(entity3.posX, entity3.posY, entity3.posZ, (Entity)CrystalAura.mc.player);
                            if (localDamage > currentDamage || (currentDamage < this.minDamagee.getValue() && player.getHealth() >= this.facePlace.getValue())) {
                                continue;
                            }
                            CrystalAura.isActive = true;
                            this.renderBlockE = new BlockPos(entity3.posX, entity3.posY - 1.0, entity3.posZ);
                            CrystalAura.mc.playerController.attackEntity((EntityPlayer)CrystalAura.mc.player, entity3);
                            CrystalAura.mc.player.swingArm(EnumHand.MAIN_HAND);
                            CrystalAura.isActive = false;
                            this.hitSystemTime = System.nanoTime() / 1000000L;
                            CrystalAura.mc.playerController.updateController();
                            if (!this.multiplace.getValue()) {
                                return;
                            }
                            if (System.nanoTime() / 1000000L - this.multiPlaceSystemTime <= this.multiPlaceSpeed.getValue() * 50 && this.multiPlaceSpeed.getValue() < 10) {
                                this.multiPlaceSystemTime = System.nanoTime() / 1000000L;
                                return;
                            }
                            continue;
                        }
                    }
                }
                else {
                    resetRotation();
                }
            }
        }
        int crystalSlot = (CrystalAura.mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL) ? CrystalAura.mc.player.inventory.currentItem : -1;
        if (crystalSlot == -1) {
            for (int l = 0; l < 9; ++l) {
                if (CrystalAura.mc.player.inventory.getStackInSlot(l).getItem() == Items.END_CRYSTAL) {
                    crystalSlot = l;
                    break;
                }
            }
        }
        boolean offhand = false;
        if (CrystalAura.mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) {
            offhand = true;
        }
        else if (crystalSlot == -1) {
            return;
        }
        if (this.place.getValue() && System.nanoTime() / 1000000L - this.placeSystemTime >= this.getPlaceDelay()) {
            final List<Entity> entities = (List<Entity>)CrystalAura.mc.world.playerEntities.stream().filter(entityPlayer -> !Friends.isFriend(entityPlayer.getName())).sorted((entity1, entity2) -> Float.compare(CrystalAura.mc.player.getDistance(entity1), CrystalAura.mc.player.getDistance(entity2))).collect(Collectors.toList());
            final List<BlockPos> blocks = this.findCrystalBlocks();
            BlockPos targetBlock = null;
            double lastDamage = 0.0;
            this.target = null;
            for (final Entity entity4 : entities) {
                if (entity4 == CrystalAura.mc.player) {
                    continue;
                }
                if (!(entity4 instanceof EntityPlayer)) {
                    continue;
                }
                final EntityPlayer testTarget = (EntityPlayer)entity4;
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
                    final double selfDamage = calculateDamage(blockPos.x + 0.5, blockPos.y + 1, blockPos.z + 0.5, (Entity)CrystalAura.mc.player);
                    final float healthTarget = testTarget.getHealth() + testTarget.getAbsorptionAmount();
                    final float healthSelf = CrystalAura.mc.player.getHealth() + CrystalAura.mc.player.getAbsorptionAmount();
                    if (selfDamage >= healthSelf - 0.5) {
                        continue;
                    }
                    if (selfDamage > this.maxSelfDmg.getValue()) {
                        continue;
                    }
                    if (selfDamage > targetDamage && targetDamage < healthTarget) {
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
            if (!offhand && CrystalAura.mc.player.inventory.currentItem != crystalSlot) {
                if (this.autoSwitch.getValue()) {
                    CrystalAura.mc.player.inventory.currentItem = crystalSlot;
                    resetRotation();
                    this.switchCooldown = true;
                }
                return;
            }
            if (this.rotatePlace.getValue()) {
                this.lookAtPacket(targetBlock.x + 0.5, targetBlock.y - 0.5, targetBlock.z + 0.5, (EntityPlayer)CrystalAura.mc.player);
            }
            EnumFacing f;
            if (this.rayTrace.getValue()) {
                final RayTraceResult result = CrystalAura.mc.world.rayTraceBlocks(new Vec3d(CrystalAura.mc.player.posX, CrystalAura.mc.player.posY + CrystalAura.mc.player.getEyeHeight(), CrystalAura.mc.player.posZ), new Vec3d(targetBlock.x + 0.5, targetBlock.y - 0.5, targetBlock.z + 0.5));
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
            CrystalAura.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(targetBlock, f, offhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
            this.placeSystemTime = System.nanoTime() / 1000000L;
        }
    }
    
    public void setPlayerRotations(final float yaw, final float pitch) {
        Minecraft.getMinecraft().player.rotationYaw = yaw;
        Minecraft.getMinecraft().player.rotationYawHead = yaw;
        Minecraft.getMinecraft().player.rotationPitch = pitch;
    }
    
    private float calculateExplosionDamage(final EntityLivingBase entity, final float size, final float x, final float y, final float z) {
        final Minecraft mc = Minecraft.getMinecraft();
        final float scale = size * 2.0f;
        final Vec3d pos = MathUtil.interpolateEntity((Entity)entity, mc.getRenderPartialTicks());
        final double dist = MathUtil.getDistance(pos, x, y, z) / scale;
        final Vec3d vec3d = new Vec3d((double)x, (double)y, (double)z);
        final double density = entity.world.getBlockDensity(vec3d, entity.getEntityBoundingBox());
        final double densityScale = (1.0 - dist) * density;
        float unscaledDamage = (float)(int)((densityScale * densityScale + densityScale) / 2.0 * 7.0 * scale + 1.0);
        unscaledDamage *= 0.5f * mc.world.getDifficulty().getId();
        return this.scaleExplosionDamage(entity, new Explosion((World)mc.world, (Entity)null, (double)x, (double)y, (double)z, size, false, true), unscaledDamage);
    }
    
    private float scaleExplosionDamage(final EntityLivingBase entity, final Explosion explosion, float damage) {
        damage = CombatRules.getDamageAfterAbsorb(damage, (float)entity.getTotalArmorValue(), (float)entity.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
        damage *= 1.0f - MathHelper.clamp((float)EnchantmentHelper.getEnchantmentModifierDamage(entity.getArmorInventoryList(), DamageSource.causeExplosionDamage(explosion)), 0.0f, 20.0f) / 25.0f;
        damage = Math.max(damage - entity.getAbsorptionAmount(), 0.0f);
        return damage;
    }
    
    private void lookAtPacket(final double px, final double py, final double pz, final EntityPlayer me) {
        final double[] v = EntityUtil.calculateLookAt(px, py, pz, me);
        setYawAndPitch((float)v[0], (float)v[1]);
    }
    
    private boolean canPlaceCrystal(final BlockPos blockPos) {
        final BlockPos boost = blockPos.add(0, 1, 0);
        final BlockPos boost2 = blockPos.add(0, 2, 0);
        return (CrystalAura.mc.world.getBlockState(blockPos).getBlock() == Blocks.BEDROCK || CrystalAura.mc.world.getBlockState(blockPos).getBlock() == Blocks.OBSIDIAN) && CrystalAura.mc.world.getBlockState(boost).getBlock() == Blocks.AIR && CrystalAura.mc.world.getBlockState(boost2).getBlock() == Blocks.AIR && CrystalAura.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(boost)).isEmpty() && CrystalAura.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(boost2)).isEmpty();
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
        if (CrystalAura.mc.world == null) {
            return;
        }
        if (this.noTrace.getValue() && !ModuleManager.getModuleByName("NoEntityTrace").isEnabled()) {
            NoEntityTrace.INSTANCE.mode.setValue(NoEntityTrace.TraceMode.STATIC);
            ModuleManager.getModuleByName("NoEntityTrace").enable();
            this.turnTraceOff = true;
        }
        if (this.announceUsage.getValue()) {
            Command.sendChatMessage("[AutoCrystal] " + ChatFormatting.GREEN.toString() + "Enabled!");
        }
    }
    
    public void onDisable() {
        if (this.turnTraceOff) {
            ModuleManager.getModuleByName("NoEntityTrace").disable();
            this.turnTraceOff = false;
        }
        this.renderBlock = null;
        this.target = null;
        resetRotation();
        if (this.announceUsage.getValue()) {
            Command.sendChatMessage("[AutoCrystal] " + ChatFormatting.RED.toString() + "Disabled!");
        }
    }
    
    public String getHudInfo() {
        if (this.target == null) {
            return "";
        }
        return this.target.getName().toUpperCase();
    }
    
    public static boolean isPlacing() {
        return CrystalAura.isActive;
    }
    
    private enum RenderMode
    {
        UP, 
        BLOCK, 
        NONE;
    }
    
    private enum SpeedMode
    {
        FAST, 
        SLOW;
    }
    
    private enum AttackMode
    {
        MS, 
        TICK;
    }
    
    private enum Page
    {
        PLACE, 
        HIT, 
        MISC, 
        COLOR;
    }
}
