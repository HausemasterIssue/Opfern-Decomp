//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.gui.kami.theme.kami;

import me.zeroeightsix.kami.gui.rgui.render.*;
import me.zeroeightsix.kami.gui.kami.component.*;
import me.zeroeightsix.kami.gui.rgui.render.font.*;
import org.lwjgl.opengl.*;
import me.zeroeightsix.kami.module.modules.HUD.*;
import me.zeroeightsix.kami.module.*;
import me.zeroeightsix.kami.gui.kami.*;
import me.zeroeightsix.kami.gui.rgui.component.*;

public class KamiSettingsPanelUI extends AbstractComponentUI<SettingsPanel>
{
    @Override
    public void renderComponent(final SettingsPanel component, final FontRenderer fontRenderer) {
        super.renderComponent(component, fontRenderer);
        GL11.glLineWidth(2.0f);
        final ClickGUI colorBack = (ClickGUI)ModuleManager.getModuleByName("GUI");
        GL11.glColor4f((float)colorBack.getSBRed(), (float)colorBack.getSBGreen(), (float)colorBack.getSBBlue(), (float)colorBack.getSBAlpha());
        RenderHelper.drawFilledRectangle(-7.0f, 0.0f, (float)component.getParent().getWidth(), (float)(component.getHeight() + 2));
        GL11.glColor3f((float)colorBack.getSORed(), (float)colorBack.getSOGreen(), (float)colorBack.getSOBlue());
        GL11.glLineWidth(1.5f);
        GL11.glPushMatrix();
        GL11.glTranslated(-7.0, 0.0, 0.0);
        RenderHelper.drawRectangle(0.0f, 0.0f, (float)component.getParent().getWidth(), (float)(component.getHeight() + 2));
        GL11.glPopMatrix();
    }
}
