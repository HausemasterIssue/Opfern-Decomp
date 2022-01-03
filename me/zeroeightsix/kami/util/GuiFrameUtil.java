//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.util;

import me.zeroeightsix.kami.gui.rgui.component.container.use.*;
import me.zeroeightsix.kami.*;
import me.zeroeightsix.kami.gui.rgui.util.*;
import me.zeroeightsix.kami.gui.rgui.component.container.*;
import java.util.*;
import net.minecraft.client.*;
import me.zeroeightsix.kami.gui.kami.*;
import org.lwjgl.opengl.*;

public class GuiFrameUtil
{
    public static Frame getFrameByName(final String name) {
        final KamiGUI kamiGUI = KamiMod.getInstance().getGuiManager();
        if (kamiGUI == null) {
            return null;
        }
        final List<Frame> frames = (List<Frame>)ContainerHelper.getAllChildren((Class)Frame.class, (Container)kamiGUI);
        for (final Frame frame : frames) {
            if (frame.getTitle().equalsIgnoreCase(name)) {
                return frame;
            }
        }
        return null;
    }
    
    public static Frame getFrameByName(final KamiGUI kamiGUI, final String name) {
        if (kamiGUI == null) {
            return null;
        }
        final List<Frame> frames = (List<Frame>)ContainerHelper.getAllChildren((Class)Frame.class, (Container)kamiGUI);
        for (final Frame frame : frames) {
            if (frame.getTitle().equalsIgnoreCase(name)) {
                return frame;
            }
        }
        return null;
    }
    
    public static void fixFrames(final Minecraft mc) {
        final KamiGUI kamiGUI = KamiMod.getInstance().getGuiManager();
        if (kamiGUI == null || mc.player == null) {
            return;
        }
        final List<Frame> frames = (List<Frame>)ContainerHelper.getAllChildren((Class)Frame.class, (Container)kamiGUI);
        for (final Frame frame : frames) {
            final int divider = DisplayGuiScreen.getScale();
            if (frame.getX() > Display.getWidth() / divider) {
                frame.setX(Display.getWidth() / divider - frame.getWidth());
            }
            if (frame.getY() > Display.getHeight() / divider) {
                frame.setY(Display.getHeight() / divider - frame.getHeight());
            }
            if (frame.getX() < 0) {
                frame.setX(0);
            }
            if (frame.getY() < 0) {
                frame.setY(0);
            }
        }
    }
}
