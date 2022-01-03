//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.gui.kami.theme.kami;

import me.zeroeightsix.kami.gui.rgui.render.*;
import me.zeroeightsix.kami.gui.kami.component.*;
import me.zeroeightsix.kami.gui.rgui.render.font.*;
import org.lwjgl.opengl.*;
import me.zeroeightsix.kami.gui.kami.*;
import me.zeroeightsix.kami.gui.rgui.component.*;

public class RootSettingsPanelUI extends AbstractComponentUI<SettingsPanel>
{
    @Override
    public void renderComponent(final SettingsPanel component, final FontRenderer fontRenderer) {
        GL11.glColor4f(0.32f, 0.99f, 0.41f, 0.99f);
        RenderHelper.drawOutlinedRoundedRectangle(0, 0, component.getWidth(), component.getHeight(), 6.0f, 0.14f, 0.14f, 0.14f, component.getOpacity(), 1.0f);
    }
}
