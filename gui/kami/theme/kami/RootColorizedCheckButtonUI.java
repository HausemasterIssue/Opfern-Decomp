//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.gui.kami.theme.kami;

import me.zeroeightsix.kami.gui.kami.component.*;
import java.awt.*;
import me.zeroeightsix.kami.gui.rgui.component.use.*;
import me.zeroeightsix.kami.gui.rgui.render.font.*;
import org.lwjgl.opengl.*;
import me.zeroeightsix.kami.gui.kami.*;
import me.zeroeightsix.kami.gui.rgui.component.*;

public class RootColorizedCheckButtonUI extends RootCheckButtonUI<ColorizedCheckButton>
{
    RootSmallFontRenderer ff;
    
    public RootColorizedCheckButtonUI() {
        this.ff = new RootSmallFontRenderer();
        this.backgroundColour = new Color(200, this.backgroundColour.getGreen(), this.backgroundColour.getBlue());
        this.backgroundColourHover = new Color(255, this.backgroundColourHover.getGreen(), this.backgroundColourHover.getBlue());
        this.downColourNormal = new Color(190, 190, 190);
    }
    
    public void renderComponent(final CheckButton component, final FontRenderer aa) {
        GL11.glColor4f(this.backgroundColour.getRed() / 255.0f, this.backgroundColour.getGreen() / 255.0f, this.backgroundColour.getBlue() / 255.0f, component.getOpacity());
        if (component.isHovered() || component.isPressed()) {
            GL11.glColor4f(this.backgroundColourHover.getRed() / 255.0f, this.backgroundColourHover.getGreen() / 255.0f, this.backgroundColourHover.getBlue() / 255.0f, component.getOpacity());
        }
        if (component.isToggled()) {
            GL11.glColor3f(this.backgroundColour.getRed() / 255.0f, this.backgroundColour.getGreen() / 255.0f, this.backgroundColour.getBlue() / 255.0f);
        }
        GL11.glLineWidth(2.5f);
        GL11.glBegin(1);
        GL11.glVertex2d(0.0, (double)component.getHeight());
        GL11.glVertex2d((double)component.getWidth(), (double)component.getHeight());
        GL11.glEnd();
        final Color idleColour = component.isToggled() ? this.idleColourToggle : this.idleColourNormal;
        final Color downColour = component.isToggled() ? this.downColourToggle : this.downColourNormal;
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
        GL11.glEnable(3553);
        this.ff.drawString(component.getWidth() / 2 - KamiGUI.fontRenderer.getStringWidth(component.getName()) / 2, 0, component.isPressed() ? downColour : idleColour, component.getName());
        GL11.glDisable(3553);
    }
}
