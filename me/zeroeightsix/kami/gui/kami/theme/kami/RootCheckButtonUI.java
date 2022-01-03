//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.gui.kami.theme.kami;

import me.zeroeightsix.kami.gui.rgui.component.use.*;
import me.zeroeightsix.kami.gui.rgui.render.*;
import java.awt.*;
import me.zeroeightsix.kami.gui.rgui.render.font.*;
import org.lwjgl.opengl.*;
import me.zeroeightsix.kami.module.modules.HUD.*;
import me.zeroeightsix.kami.module.*;
import me.zeroeightsix.kami.gui.kami.*;
import me.zeroeightsix.kami.gui.rgui.component.container.*;
import me.zeroeightsix.kami.gui.rgui.component.*;

public class RootCheckButtonUI<T extends CheckButton> extends AbstractComponentUI<CheckButton>
{
    protected Color backgroundColour;
    protected Color backgroundColourHover;
    protected Color idleColourNormal;
    protected Color downColourNormal;
    protected Color idleColourToggle;
    protected Color downColourToggle;
    
    public RootCheckButtonUI() {
        this.backgroundColour = new Color(200, 56, 56);
        this.backgroundColourHover = new Color(255, 66, 66);
        this.idleColourNormal = new Color(200, 200, 200);
        this.downColourNormal = new Color(190, 190, 190);
        this.idleColourToggle = new Color(250, 120, 120);
        this.downColourToggle = this.idleColourToggle.brighter();
    }
    
    @Override
    public void renderComponent(final CheckButton component, final FontRenderer ff) {
        GL11.glColor4f(this.backgroundColour.getRed() / 255.0f, this.backgroundColour.getGreen() / 255.0f, this.backgroundColour.getBlue() / 255.0f, component.getOpacity());
        final ClickGUI colorBack = (ClickGUI)ModuleManager.getModuleByName("GUI");
        if (colorBack.getBTMode() == ClickGUI.ButtonMode.HIGHLIGHT) {
            if (component.isToggled()) {
                GL11.glColor4f((float)colorBack.getBTRed(), (float)colorBack.getBTGreen(), (float)colorBack.getBTBlue(), 0.9f);
                RenderHelper.drawFilledRectangle(0.0f, -1.0f, (float)component.getWidth(), (float)(component.getHeight() + 2));
                GL11.glColor3f(0.0f, 0.0f, 0.0f);
            }
            if (component.isHovered()) {
                GL11.glColor4f(0.59f, 0.59f, 0.59f, 0.9f);
                RenderHelper.drawFilledRectangle(0.0f, -1.0f, (float)component.getWidth(), (float)(component.getHeight() + 2));
                GL11.glColor3f(0.0f, 0.0f, 0.0f);
            }
            if (component.isPressed()) {
                GL11.glColor4f(1.02f, 1.01f, 1.01f, 0.9f);
                RenderHelper.drawFilledRectangle(0.0f, -1.0f, (float)component.getWidth(), (float)(component.getHeight() + 2));
                GL11.glColor3f(0.0f, 0.0f, 0.0f);
            }
        }
        final String text = component.getName();
        int c = component.isToggled() ? 16777215 : 13750737;
        if (colorBack.getBTMode() == ClickGUI.ButtonMode.FONT) {
            c = (component.isToggled() ? 1769216 : 16777215);
            if (component.isHovered()) {
                c = (c & 0x7F7F7F) << 1;
            }
        }
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
        GL11.glEnable(3553);
        KamiGUI.fontRenderer.drawString(2, KamiGUI.fontRenderer.getFontHeight() / 2 - 2, c, text);
        GL11.glDisable(3553);
        GL11.glDisable(3042);
    }
    
    @Override
    public void handleAddComponent(final CheckButton component, final Container container) {
        component.setWidth(KamiGUI.fontRenderer.getStringWidth(component.getName()) + 28);
        component.setHeight(KamiGUI.fontRenderer.getFontHeight() + 2);
    }
}
