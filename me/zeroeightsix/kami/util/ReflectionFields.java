//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.util;

import java.lang.reflect.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.*;
import net.minecraft.client.settings.*;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.play.server.*;
import net.minecraft.util.math.*;
import net.minecraftforge.fml.common.*;
import net.minecraft.client.renderer.block.model.*;
import java.util.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.inventory.*;
import net.minecraft.util.*;
import net.minecraft.network.datasync.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.client.gui.*;
import net.minecraft.util.text.*;

public class ReflectionFields
{
    public static Field renderPosX;
    public static Field renderPosY;
    public static Field renderPosZ;
    public static Field playerViewX;
    public static Field playerViewY;
    public static Field timer;
    public static Field modelManager;
    public static Field pressed;
    public static Field cpacketPlayerYaw;
    public static Field cpacketPlayerPitch;
    public static Field spacketPlayerPosLookYaw;
    public static Field spacketPlayerPosLookPitch;
    public static Field mapTextureObjects;
    public static Field cpacketPlayerOnGround;
    public static Field rightClickDelayTimer;
    public static Field horseJumpPower;
    private static Field modifiersField;
    public static Method rightClickMouse;
    public static Field curBlockDamageMP;
    public static Field blockHitDelay;
    public static Field debugFps;
    public static Field lowerChestInventory;
    public static Field shulkerInventory;
    public static Field spacketExplosionMotionX;
    public static Field spacketExplosionMotionY;
    public static Field spacketExplosionMotionZ;
    public static Field cpacketPlayerY;
    public static Field cpacketVehicleMoveY;
    public static Field session;
    public static Field PLAYER_MODEL_FLAG;
    public static Field speedInAir;
    public static Field guiButtonHovered;
    public static Field ridingEntity;
    public static Field foodExhaustionLevel;
    public static Field cPacketUpdateSignLines;
    public static Field hopperInventory;
    public static Field cPacketChatMessage;
    public static Field guiSceenServerListServerData;
    public static Field guiDisconnectedParentScreen;
    public static Field sPacketChatChatComponent;
    public static Field boundingBox;
    public static Field y_vec3d;
    public static Field sleeping;
    public static Field sleepTimer;
    
    public static void init() {
        try {
            ReflectionFields.renderPosX = getField(RenderManager.class, "renderPosX", "renderPosX");
            ReflectionFields.renderPosY = getField(RenderManager.class, "renderPosY", "renderPosY");
            ReflectionFields.renderPosZ = getField(RenderManager.class, "renderPosZ", "renderPosZ");
            ReflectionFields.playerViewX = getField(RenderManager.class, "playerViewX", "playerViewX");
            ReflectionFields.playerViewY = getField(RenderManager.class, "playerViewY", "playerViewY");
            ReflectionFields.timer = getField(Minecraft.class, "timer", "timer");
            ReflectionFields.modelManager = getField(Minecraft.class, "modelManager", "modelManager");
            ReflectionFields.rightClickMouse = getMethod(Minecraft.class, new String[] { "rightClickMouse", "rightClickMouse" }, (Class<?>[])new Class[0]);
            ReflectionFields.pressed = getField(KeyBinding.class, "pressed", "pressed");
            ReflectionFields.cpacketPlayerYaw = getField(CPacketPlayer.class, "yaw", "yaw");
            ReflectionFields.cpacketPlayerPitch = getField(CPacketPlayer.class, "pitch", "pitch");
            ReflectionFields.spacketPlayerPosLookYaw = getField(SPacketPlayerPosLook.class, "yaw", "yaw");
            ReflectionFields.spacketPlayerPosLookPitch = getField(SPacketPlayerPosLook.class, "pitch", "pitch");
            ReflectionFields.mapTextureObjects = getField(TextureManager.class, "mapTextureObjects", "mapTextureObjects");
            ReflectionFields.cpacketPlayerOnGround = getField(CPacketPlayer.class, "onGround", "onGround");
            ReflectionFields.rightClickDelayTimer = getField(Minecraft.class, "rightClickDelayTimer", "rightClickDelayTimer");
            ReflectionFields.horseJumpPower = getField(EntityPlayerSP.class, "horseJumpPower", "horseJumpPower");
            ReflectionFields.curBlockDamageMP = getField(PlayerControllerMP.class, "curBlockDamageMP", "curBlockDamageMP");
            ReflectionFields.blockHitDelay = getField(PlayerControllerMP.class, "blockHitDelay", "blockHitDelay");
            ReflectionFields.debugFps = getField(Minecraft.class, "debugFPS", "debugFPS");
            ReflectionFields.lowerChestInventory = getField(GuiChest.class, "lowerChestInventory", "lowerChestInventory");
            ReflectionFields.shulkerInventory = getField(GuiShulkerBox.class, "inventory", "inventory");
            ReflectionFields.spacketExplosionMotionX = getField(SPacketExplosion.class, "motionX", "motionX");
            ReflectionFields.spacketExplosionMotionY = getField(SPacketExplosion.class, "motionY", "motionY");
            ReflectionFields.spacketExplosionMotionZ = getField(SPacketExplosion.class, "motionZ", "motionZ");
            ReflectionFields.cpacketPlayerY = getField(CPacketPlayer.class, "y", "y");
            ReflectionFields.cpacketVehicleMoveY = getField(CPacketVehicleMove.class, "y", "y");
            ReflectionFields.session = getField(Minecraft.class, "session", "session");
            ReflectionFields.PLAYER_MODEL_FLAG = getField(EntityPlayer.class, "PLAYER_MODEL_FLAG", "PLAYER_MODEL_FLAG");
            ReflectionFields.speedInAir = getField(EntityPlayer.class, "speedInAir", "speedInAir");
            ReflectionFields.guiButtonHovered = getField(GuiButton.class, "hovered", "hovered");
            ReflectionFields.ridingEntity = getField(Entity.class, "ridingEntity", "ridingEntity");
            ReflectionFields.foodExhaustionLevel = getField(FoodStats.class, "foodExhaustionLevel", "foodExhaustionLevel");
            ReflectionFields.cPacketUpdateSignLines = getField(CPacketUpdateSign.class, "lines", "lines");
            ReflectionFields.hopperInventory = getField(GuiHopper.class, "hopperInventory", "hopperInventory");
            ReflectionFields.cPacketChatMessage = getField(CPacketChatMessage.class, "message", "message");
            ReflectionFields.guiSceenServerListServerData = getField(GuiScreenServerList.class, "serverData", "serverData");
            ReflectionFields.guiDisconnectedParentScreen = getField(GuiDisconnected.class, "parentScreen", "parentScreen");
            ReflectionFields.sPacketChatChatComponent = getField(SPacketChat.class, "chatComponent", "chatComponent");
            ReflectionFields.boundingBox = getField(Entity.class, "boundingBox", "chatComponent");
            ReflectionFields.y_vec3d = getField(Vec3d.class, "y", "y", "c");
            ReflectionFields.sleeping = getField(EntityPlayer.class, "sleeping", "sleeping", "bK");
            ReflectionFields.sleepTimer = getField(EntityPlayer.class, "sleepTimer", "sleepTimer");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static Field getField(final Class c, final String... names) {
        for (final String s : names) {
            try {
                final Field f = c.getDeclaredField(s);
                f.setAccessible(true);
                ReflectionFields.modifiersField.setInt(f, f.getModifiers() & 0xFFFFFFEF);
                return f;
            }
            catch (NoSuchFieldException e) {
                FMLLog.log.info("unable to find field: " + s);
            }
            catch (IllegalAccessException e2) {
                FMLLog.log.info("unable to make field changeable!");
            }
        }
        throw new IllegalStateException("Field with names: " + names + " not found!");
    }
    
    public static Method getMethod(final Class c, final String[] names, final Class<?>... args) {
        final int length = names.length;
        int i = 0;
        while (i < length) {
            final String s = names[i];
            try {
                final Method m = c.getDeclaredMethod(s, (Class[])args);
                m.setAccessible(true);
                return m;
            }
            catch (NoSuchMethodException e) {
                FMLLog.log.info("unable to find method: " + s);
                ++i;
                continue;
            }
            break;
        }
        throw new IllegalStateException("Method with names: " + names + " not found!");
    }
    
    public static double getRenderPosX() {
        try {
            return (double)ReflectionFields.renderPosX.get(Wrapper.getMinecraft().getRenderManager());
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static double getRenderPosY() {
        try {
            return (double)ReflectionFields.renderPosY.get(Wrapper.getMinecraft().getRenderManager());
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static double getRenderPosZ() {
        try {
            return (double)ReflectionFields.renderPosZ.get(Wrapper.getMinecraft().getRenderManager());
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static float getPlayerViewY() {
        try {
            return (float)ReflectionFields.playerViewY.get(Wrapper.getMinecraft().getRenderManager());
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static float getPlayerViewX() {
        try {
            return (float)ReflectionFields.playerViewX.get(Wrapper.getMinecraft().getRenderManager());
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static Timer getTimer() {
        try {
            return (Timer)ReflectionFields.timer.get(Wrapper.getMinecraft());
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static ModelManager getModelManager() {
        try {
            return (ModelManager)ReflectionFields.modelManager.get(Wrapper.getMinecraft());
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static void rightClickMouse() {
        try {
            ReflectionFields.rightClickMouse.invoke(Wrapper.getMinecraft(), new Object[0]);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static boolean getPressed(final KeyBinding binding) {
        try {
            return (boolean)ReflectionFields.pressed.get(binding);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static void setPressed(final KeyBinding keyBinding, final boolean state) {
        try {
            ReflectionFields.pressed.set(keyBinding, state);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static void setCPacketPlayerYaw(final CPacketPlayer packet, final float value) {
        try {
            ReflectionFields.cpacketPlayerYaw.set(packet, value);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static void setCPacketPlayerPitch(final CPacketPlayer packet, final float value) {
        try {
            ReflectionFields.cpacketPlayerPitch.set(packet, value);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static void setSPacketPlayerPosLookYaw(final float value, final SPacketPlayerPosLook packet) {
        try {
            ReflectionFields.spacketPlayerPosLookYaw.set(packet, value);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static void setSPacketPlayerPosLookPitch(final float value, final SPacketPlayerPosLook packet) {
        try {
            ReflectionFields.spacketPlayerPosLookPitch.set(packet, value);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static Map<ResourceLocation, ITextureObject> getMapTextureObjects() {
        try {
            return (Map<ResourceLocation, ITextureObject>)ReflectionFields.mapTextureObjects.get(Wrapper.getMinecraft().getTextureManager());
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static void setCPacketPlayerOnGround(final CPacketPlayer packet, final boolean onGround) {
        try {
            ReflectionFields.cpacketPlayerOnGround.set(packet, onGround);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static void setRightClickDelayTimer(final int value) {
        try {
            ReflectionFields.rightClickDelayTimer.set(Wrapper.getMinecraft(), value);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static void setHorseJumpPower(final float value) {
        try {
            ReflectionFields.horseJumpPower.set(Wrapper.getMinecraft().player, value);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static float getCurBlockDamageMP() {
        try {
            return (float)ReflectionFields.curBlockDamageMP.get(Wrapper.getMinecraft().playerController);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static void setCurBlockDamageMP(final float value) {
        try {
            ReflectionFields.curBlockDamageMP.set(Wrapper.getMinecraft().playerController, value);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static int getBlockHitDelay() {
        try {
            return (int)ReflectionFields.blockHitDelay.get(Wrapper.getMinecraft().playerController);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static void setBlockHitDelay(final float value) {
        try {
            ReflectionFields.blockHitDelay.set(Wrapper.getMinecraft().playerController, value);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static int getDebugFps() {
        try {
            return (int)ReflectionFields.debugFps.get(null);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static IInventory getLowerChestInventory(final GuiChest chest) {
        try {
            return (IInventory)ReflectionFields.lowerChestInventory.get(chest);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static IInventory getShulkerInventory(final GuiShulkerBox chest) {
        try {
            return (IInventory)ReflectionFields.shulkerInventory.get(chest);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static void setSPacketExplosionMotionX(final SPacketExplosion packet, final float value) {
        try {
            ReflectionFields.spacketExplosionMotionX.set(packet, value);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static void setSPacketExplosionMotionY(final SPacketExplosion packet, final float value) {
        try {
            ReflectionFields.spacketExplosionMotionY.set(packet, value);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static void setSPacketExplosionMotionZ(final SPacketExplosion packet, final float value) {
        try {
            ReflectionFields.spacketExplosionMotionZ.set(packet, value);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static double getCPacketPlayerY(final CPacketPlayer packet) {
        try {
            return (double)ReflectionFields.cpacketPlayerY.get(packet);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static void setCPacketPlayerY(final CPacketPlayer packet, final double value) {
        try {
            ReflectionFields.cpacketPlayerY.set(packet, value);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static double getCPacketVehicleMoveY(final CPacketVehicleMove packet) {
        try {
            return (double)ReflectionFields.cpacketVehicleMoveY.get(packet);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static void setCPacketVehicleMoveY(final CPacketVehicleMove packet, final double value) {
        try {
            ReflectionFields.cpacketVehicleMoveY.set(packet, value);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static void setSession(final Session newSession) {
        try {
            ReflectionFields.session.set(Wrapper.getMinecraft(), newSession);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static DataParameter<Byte> getPLAYER_MODEL_FLAG() {
        try {
            return (DataParameter<Byte>)ReflectionFields.PLAYER_MODEL_FLAG.get(null);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static void setSpeedInAir(final EntityPlayer entityPlayer, final float newValue) {
        try {
            ReflectionFields.speedInAir.set(entityPlayer, newValue);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static float getSpeedInAir(final EntityPlayer entityPlayer) {
        try {
            return (float)ReflectionFields.speedInAir.get(entityPlayer);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static boolean getGuiButtonHovered(final GuiButton button) {
        try {
            return (boolean)ReflectionFields.guiButtonHovered.get(button);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static void setGuiButtonHovered(final GuiButton button, final boolean value) {
        try {
            ReflectionFields.guiButtonHovered.set(button, value);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static Entity getRidingEntity(final Entity toGetFrom) {
        try {
            return (Entity)ReflectionFields.ridingEntity.get(toGetFrom);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static float getFoodExhaustionLevel() {
        try {
            return (float)ReflectionFields.foodExhaustionLevel.get(Wrapper.getMinecraft().player.getFoodStats());
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static void setCPacketUpdateSignLines(final CPacketUpdateSign packet, final String[] value) {
        try {
            ReflectionFields.cPacketUpdateSignLines.set(packet, value);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static IInventory getHopperInventory(final GuiHopper chest) {
        try {
            return (IInventory)ReflectionFields.hopperInventory.get(chest);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static void setCPacketChatMessage(final CPacketChatMessage packet, final String value) {
        try {
            ReflectionFields.cPacketChatMessage.set(packet, value);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static ServerData getServerData(final GuiScreenServerList data) {
        try {
            return (ServerData)ReflectionFields.guiSceenServerListServerData.get(data);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static GuiScreen getGuiDisconnectedParentScreen(final GuiDisconnected toGetFrom) {
        try {
            return (GuiScreen)ReflectionFields.guiDisconnectedParentScreen.get(toGetFrom);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static void setSPacketChatChatComponent(final SPacketChat packet, final TextComponentString value) {
        try {
            ReflectionFields.sPacketChatChatComponent.set(packet, value);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static void setY_vec3d(final Vec3d vec, final double val) {
        try {
            ReflectionFields.y_vec3d.set(vec, val);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static boolean getSleeping(final EntityPlayer mgr) {
        try {
            return (boolean)ReflectionFields.sleeping.get(mgr);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static void setSleeping(final EntityPlayer entityPlayer, final boolean value) {
        try {
            ReflectionFields.sleeping.set(entityPlayer, value);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static void sleepTimer(final EntityPlayer entityPlayer, final int value) {
        try {
            ReflectionFields.sleeping.set(entityPlayer, value);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    static {
        try {
            (ReflectionFields.modifiersField = Field.class.getDeclaredField("modifiers")).setAccessible(true);
        }
        catch (Exception ex) {}
    }
}
