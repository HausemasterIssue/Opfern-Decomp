//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.gui.rgui.poof;

import me.zeroeightsix.kami.gui.rgui.component.*;

public interface IPoof<T extends Component, S extends PoofInfo>
{
    void execute(final T p0, final S p1);
    
    Class getComponentClass();
    
    Class getInfoClass();
}
