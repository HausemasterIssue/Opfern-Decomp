//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.module.modules.combat;

import net.minecraft.entity.player.*;
import me.zeroeightsix.kami.setting.*;
import me.zeroeightsix.kami.module.*;
import me.zeroeightsix.kami.command.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import me.zeroeightsix.kami.util.*;
import java.util.*;

@Module.Info(name = "WebAura", category = Module.Category.COMBAT)
public class WebAuraMin extends Module
{
    private Setting<Double> range;
    private Setting<Double> blockPerTick;
    private Setting<Boolean> spoofRotations;
    private Setting<Boolean> spoofHotbar;
    private Setting<Boolean> debugMessages;
    private final Vec3d[] offsetList;
    private boolean slowModeSwitch;
    private int playerHotbarSlot;
    private EntityPlayer closestTarget;
    private int lastHotbarSlot;
    private int offsetStep;
    
    public WebAuraMin() {
        this.range = (Setting<Double>)this.register((Setting)Settings.d("Range", 5.5));
        this.blockPerTick = (Setting<Double>)this.register((Setting)Settings.d("Blocks per Tick", 8.0));
        this.spoofRotations = (Setting<Boolean>)this.register((Setting)Settings.b("Spoof Rotations", false));
        this.spoofHotbar = (Setting<Boolean>)this.register((Setting)Settings.b("Spoof Hotbar", false));
        this.debugMessages = (Setting<Boolean>)this.register((Setting)Settings.b("Debug Messages", false));
        this.offsetList = new Vec3d[] { new Vec3d(0.0, 2.0, 0.0), new Vec3d(0.0, 1.0, 0.0), new Vec3d(0.0, 0.0, 0.0) };
        this.slowModeSwitch = false;
        this.playerHotbarSlot = -1;
        this.lastHotbarSlot = -1;
        this.offsetStep = 0;
    }
    
    public void onUpdate() {
        if (this.isDisabled() || WebAuraMin.mc.player == null || ModuleManager.isModuleEnabled("Freecam")) {
            return;
        }
        if (this.closestTarget == null) {
            return;
        }
        if (this.slowModeSwitch) {
            this.slowModeSwitch = false;
            return;
        }
        for (int i = 0; i < (int)Math.floor(this.blockPerTick.getValue()); ++i) {
            if (this.debugMessages.getValue()) {
                Command.sendChatMessage("[WebAuraMin] Loop iteration: " + this.offsetStep);
            }
            if (this.offsetStep >= this.offsetList.length) {
                this.endLoop();
                return;
            }
            final Vec3d offset = this.offsetList[this.offsetStep];
            this.placeBlock(new BlockPos(this.closestTarget.getPositionVector()).down().add(offset.x, offset.y, offset.z));
            ++this.offsetStep;
        }
        this.slowModeSwitch = true;
    }
    
    private void placeBlock(final BlockPos blockPos) {
        if (!Wrapper.getWorld().getBlockState(blockPos).getMaterial().isReplaceable()) {
            if (this.debugMessages.getValue()) {
                Command.sendChatMessage("[WebAuraMin] Block is already placed, skipping");
            }
            return;
        }
        if (!BlockInteractionHelper.checkForNeighbours(blockPos)) {
            return;
        }
        this.placeBlockExecute(blockPos);
    }
    
    public void placeBlockExecute(final BlockPos pos) {
        final Vec3d eyesPos = new Vec3d(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY + Wrapper.getPlayer().getEyeHeight(), Wrapper.getPlayer().posZ);
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbor = pos.offset(side);
            final EnumFacing side2 = side.getOpposite();
            if (!BlockInteractionHelper.canBeClicked(neighbor)) {
                if (this.debugMessages.getValue()) {
                    Command.sendChatMessage("[WebAuraMin] No neighbor to click at!");
                }
            }
            else {
                final Vec3d hitVec = new Vec3d((Vec3i)neighbor).add(0.5, 0.5, 0.5).add(new Vec3d(side2.getDirectionVec()).scale(0.5));
                if (eyesPos.squareDistanceTo(hitVec) > 18.0625) {
                    if (this.debugMessages.getValue()) {
                        Command.sendChatMessage("[WebAuraMin] Distance > 4.25 blocks!");
                    }
                }
                else {
                    if (this.spoofRotations.getValue()) {
                        BlockInteractionHelper.faceVectorPacketInstant(hitVec);
                    }
                    boolean needSneak = false;
                    final Block blockBelow = WebAuraMin.mc.world.getBlockState(neighbor).getBlock();
                    if (BlockInteractionHelper.blackList.contains(blockBelow) || BlockInteractionHelper.shulkerList.contains(blockBelow)) {
                        if (this.debugMessages.getValue()) {
                            Command.sendChatMessage("[WebAuraMin] Sneak enabled!");
                        }
                        needSneak = true;
                    }
                    if (needSneak) {
                        WebAuraMin.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)WebAuraMin.mc.player, CPacketEntityAction.Action.START_SNEAKING));
                    }
                    final int obiSlot = this.findObiInHotbar();
                    if (obiSlot == -1) {
                        if (this.debugMessages.getValue()) {
                            Command.sendChatMessage("[WebAuraMin] No Obi in Hotbar, disabling!");
                        }
                        this.disable();
                        return;
                    }
                    if (this.lastHotbarSlot != obiSlot) {
                        if (this.debugMessages.getValue()) {
                            Command.sendChatMessage("[WebAuraMin Setting Slot to Obi at  = " + obiSlot);
                        }
                        if (this.spoofHotbar.getValue()) {
                            WebAuraMin.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(obiSlot));
                        }
                        else {
                            Wrapper.getPlayer().inventory.currentItem = obiSlot;
                        }
                        this.lastHotbarSlot = obiSlot;
                    }
                    WebAuraMin.mc.playerController.processRightClickBlock(Wrapper.getPlayer(), WebAuraMin.mc.world, neighbor, side2, hitVec, EnumHand.MAIN_HAND);
                    WebAuraMin.mc.player.connection.sendPacket((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
                    if (needSneak) {
                        if (this.debugMessages.getValue()) {
                            Command.sendChatMessage("[WebAurav] Sneak disabled!");
                        }
                        WebAuraMin.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)WebAuraMin.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                    }
                    return;
                }
            }
        }
    }
    
    private int findObiInHotbar() {
        int slot = -1;
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = Wrapper.getPlayer().inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY && stack.getItem() instanceof ItemBlock) {
                final Block block = ((ItemBlock)stack.getItem()).getBlock();
                if (block instanceof BlockWeb) {
                    slot = i;
                    break;
                }
            }
        }
        return slot;
    }
    
    private void findTarget() {
        final List<EntityPlayer> playerList = (List<EntityPlayer>)Wrapper.getWorld().playerEntities;
        for (final EntityPlayer target : playerList) {
            if (target == WebAuraMin.mc.player) {
                continue;
            }
            if (Friends.isFriend(target.getName())) {
                continue;
            }
            if (!EntityUtil.isLiving((Entity)target)) {
                continue;
            }
            if (target.getHealth() <= 0.0f) {
                continue;
            }
            final double currentDistance = Wrapper.getPlayer().getDistance((Entity)target);
            if (currentDistance > this.range.getValue()) {
                continue;
            }
            if (this.closestTarget == null) {
                this.closestTarget = target;
            }
            else {
                if (currentDistance >= Wrapper.getPlayer().getDistance((Entity)this.closestTarget)) {
                    continue;
                }
                this.closestTarget = target;
            }
        }
    }
    
    private void endLoop() {
        this.offsetStep = 0;
        if (this.debugMessages.getValue()) {
            Command.sendChatMessage("[WebAuraMin] Ending Loop");
        }
        if (this.lastHotbarSlot != this.playerHotbarSlot && this.playerHotbarSlot != -1) {
            if (this.debugMessages.getValue()) {
                Command.sendChatMessage("[WebAuraMin] Setting Slot back to  = " + this.playerHotbarSlot);
            }
            if (this.spoofHotbar.getValue()) {
                WebAuraMin.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.playerHotbarSlot));
            }
            else {
                Wrapper.getPlayer().inventory.currentItem = this.playerHotbarSlot;
            }
            this.lastHotbarSlot = this.playerHotbarSlot;
        }
        this.findTarget();
    }
    
    protected void onEnable() {
        if (WebAuraMin.mc.player == null) {
            this.disable();
            return;
        }
        if (this.debugMessages.getValue()) {
            Command.sendChatMessage("[WebAuraMin] Enabling");
        }
        this.playerHotbarSlot = Wrapper.getPlayer().inventory.currentItem;
        this.lastHotbarSlot = -1;
        if (this.debugMessages.getValue()) {
            Command.sendChatMessage("[WebAuraMin] Saving initial Slot  = " + this.playerHotbarSlot);
        }
        this.findTarget();
    }
    
    protected void onDisable() {
        if (WebAuraMin.mc.player == null) {
            return;
        }
        if (this.debugMessages.getValue()) {
            Command.sendChatMessage("[WebAuraMin] Disabling");
        }
        if (this.lastHotbarSlot != this.playerHotbarSlot && this.playerHotbarSlot != -1) {
            if (this.debugMessages.getValue()) {
                Command.sendChatMessage("[WebAuraMin] Setting Slot to  = " + this.playerHotbarSlot);
            }
            if (this.spoofHotbar.getValue()) {
                WebAuraMin.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.playerHotbarSlot));
            }
            else {
                Wrapper.getPlayer().inventory.currentItem = this.playerHotbarSlot;
            }
        }
        this.playerHotbarSlot = -1;
        this.lastHotbarSlot = -1;
    }
}
