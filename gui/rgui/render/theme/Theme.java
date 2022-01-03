//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.gui.rgui.render.theme;

import me.zeroeightsix.kami.gui.rgui.component.*;
import me.zeroeightsix.kami.gui.rgui.render.*;
import me.zeroeightsix.kami.gui.rgui.render.font.*;

public interface Theme
{
    ComponentUI getUIForComponent(final Component p0);
    
    FontRenderer getFontRenderer();
}
