//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.gui.kami;

import net.minecraft.client.gui.*;
import net.minecraft.client.shader.*;
import me.zeroeightsix.kami.*;
import me.zeroeightsix.kami.gui.rgui.component.*;
import me.zeroeightsix.kami.gui.rgui.component.container.use.*;
import me.zeroeightsix.kami.util.*;
import java.util.*;
import org.lwjgl.opengl.*;
import java.io.*;
import me.zeroeightsix.kami.module.modules.HUD.*;
import me.zeroeightsix.kami.module.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import org.lwjgl.input.*;
import net.minecraft.client.*;

public class DisplayGuiScreen extends GuiScreen
{
    KamiGUI gui;
    public final GuiScreen lastScreen;
    public static int mouseX;
    public static int mouseY;
    Framebuffer framebuffer;
    
    public DisplayGuiScreen(final GuiScreen lastScreen) {
        this.lastScreen = lastScreen;
        final KamiGUI gui = KamiMod.getInstance().getGuiManager();
        for (final Component c : gui.getChildren()) {
            if (c instanceof Frame) {
                final Frame child = (Frame)c;
                if (!child.isPinneable() || !child.isVisible()) {
                    continue;
                }
                child.setOpacity(0.5f);
            }
        }
        this.framebuffer = new Framebuffer(Wrapper.getMinecraft().displayWidth, Wrapper.getMinecraft().displayHeight, false);
    }
    
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    public void onGuiClosed() {
        final KamiGUI gui = KamiMod.getInstance().getGuiManager();
        gui.getChildren().stream().filter(component -> component instanceof Frame && component.isPinneable() && component.isVisible()).forEach(component -> component.setOpacity(0.0f));
        if (Wrapper.getMinecraft().entityRenderer.shaderGroup != null) {
            Wrapper.getMinecraft().entityRenderer.shaderGroup.deleteShaderGroup();
        }
    }
    
    public void initGui() {
        this.gui = KamiMod.getInstance().getGuiManager();
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.calculateMouse();
        this.gui.drawGUI();
        GL11.glEnable(3553);
        GlStateManager.color(1.0f, 1.0f, 1.0f);
    }
    
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        this.gui.handleMouseDown(DisplayGuiScreen.mouseX, DisplayGuiScreen.mouseY);
    }
    
    protected void mouseReleased(final int mouseX, final int mouseY, final int state) {
        this.gui.handleMouseRelease(DisplayGuiScreen.mouseX, DisplayGuiScreen.mouseY);
    }
    
    protected void mouseClickMove(final int mouseX, final int mouseY, final int clickedMouseButton, final long timeSinceLastClick) {
        this.gui.handleMouseDrag(DisplayGuiScreen.mouseX, DisplayGuiScreen.mouseY);
    }
    
    public void updateScreen() {
        final ClickGUI colorBack = (ClickGUI)ModuleManager.getModuleByName("GUI");
        if (colorBack.getBlur()) {
            if (OpenGlHelper.shadersSupported && Wrapper.getMinecraft().getRenderViewEntity() instanceof EntityPlayer) {
                if (Wrapper.getMinecraft().entityRenderer.shaderGroup != null) {
                    Wrapper.getMinecraft().entityRenderer.shaderGroup.deleteShaderGroup();
                }
                Wrapper.getMinecraft().entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));
            }
        }
        else if (Wrapper.getMinecraft().entityRenderer.shaderGroup != null) {
            Wrapper.getMinecraft().entityRenderer.shaderGroup.deleteShaderGroup();
        }
        if (Mouse.hasWheel()) {
            final int a = Mouse.getDWheel();
            if (a != 0) {
                this.gui.handleWheel(DisplayGuiScreen.mouseX, DisplayGuiScreen.mouseY, a);
            }
        }
    }
    
    protected void keyTyped(final char typedChar, final int keyCode) throws IOException {
        if (keyCode == 1) {
            this.mc.displayGuiScreen(this.lastScreen);
        }
        else {
            this.gui.handleKeyDown(keyCode);
            this.gui.handleKeyUp(keyCode);
        }
    }
    
    public static int getScale() {
        int scale = Wrapper.getMinecraft().gameSettings.guiScale;
        if (scale == 0) {
            scale = 1000;
        }
        int scaleFactor;
        for (scaleFactor = 0; scaleFactor < scale && Wrapper.getMinecraft().displayWidth / (scaleFactor + 1) >= 320 && Wrapper.getMinecraft().displayHeight / (scaleFactor + 1) >= 240; ++scaleFactor) {}
        if (scaleFactor == 0) {
            scaleFactor = 1;
        }
        return scaleFactor;
    }
    
    private void calculateMouse() {
        final Minecraft minecraft = Minecraft.getMinecraft();
        final int scaleFactor = getScale();
        DisplayGuiScreen.mouseX = Mouse.getX() / scaleFactor;
        DisplayGuiScreen.mouseY = minecraft.displayHeight / scaleFactor - Mouse.getY() / scaleFactor - 1;
    }
}
