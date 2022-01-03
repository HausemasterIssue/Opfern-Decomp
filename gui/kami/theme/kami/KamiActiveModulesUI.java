//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.gui.kami.theme.kami;

import me.zeroeightsix.kami.gui.rgui.render.*;
import me.zeroeightsix.kami.gui.kami.component.*;
import me.zeroeightsix.kami.gui.rgui.render.font.*;
import org.lwjgl.opengl.*;
import me.zeroeightsix.kami.util.*;
import me.zeroeightsix.kami.module.*;
import java.util.*;
import java.util.stream.*;
import me.zeroeightsix.kami.module.modules.HUD.*;
import java.awt.*;
import me.zeroeightsix.kami.command.*;
import java.util.function.*;
import me.zeroeightsix.kami.gui.rgui.component.*;

public class KamiActiveModulesUI extends AbstractComponentUI<ActiveModules>
{
    @Override
    public void renderComponent(final ActiveModules component, final FontRenderer f) {
        GL11.glDisable(2884);
        GL11.glEnable(3042);
        GL11.glEnable(3553);
        final FontRenderer renderer = Wrapper.getFontRenderer();
        String string;
        final FontRenderer fontRenderer;
        final StringBuilder sb;
        final List<Module> mods = ModuleManager.getModules().stream().filter(Module::isEnabled).filter(Module::getDrawn).sorted(Comparator.comparing(module -> {
            new StringBuilder().append(module.getName());
            if (module.getHudInfo() == null || module.getHudInfo() == "") {
                string = "";
            }
            else {
                string = module.getHudInfo() + "   ";
            }
            return Integer.valueOf(fontRenderer.getStringWidth(sb.append(string).toString()) * (component.sort_up ? -1 : 1));
        })).collect((Collector<? super Object, ?, List<Module>>)Collectors.toList());
        final int[] y = { 2 };
        if (component.getParent().getY() < 26 && Wrapper.getPlayer().getActivePotionEffects().size() > 0 && component.getParent().getOpacity() == 0.0f) {
            y[0] = Math.max(component.getParent().getY(), 26 - component.getParent().getY());
        }
        final boolean lAlign = component.getAlignment() == AlignedComponent.Alignment.LEFT;
        switch (component.getAlignment()) {
            case RIGHT: {
                final Function<Integer, Integer> xFunc = (Function<Integer, Integer>)(i -> component.getWidth() - i);
                break;
            }
            case CENTER: {
                final Function<Integer, Integer> xFunc = (Function<Integer, Integer>)(i -> component.getWidth() / 2 - i / 2);
                break;
            }
            default: {
                final Function<Integer, Integer> xFunc = (Function<Integer, Integer>)(i -> 0);
                break;
            }
        }
        final ArrayList colorBack;
        final Color rgb;
        final String s;
        String text;
        final FontRenderer fontRenderer2;
        final int textwidth;
        final int textheight;
        final Function<Integer, Integer> function;
        final Object o;
        final int n;
        mods.stream().forEach(module -> {
            colorBack = (ArrayList)ModuleManager.getModuleByName("ArrayList");
            rgb = new Color(colorBack.getRed(), colorBack.getGreen(), colorBack.getBlue());
            s = module.getHudInfo();
            if (s == null || s == "") {
                text = module.getName();
            }
            else {
                text = module.getName() + Command.SECTIONSIGN() + "7 [" + Command.SECTIONSIGN() + "f" + s + Command.SECTIONSIGN() + "7]";
            }
            textwidth = fontRenderer2.getStringWidth(text);
            textheight = fontRenderer2.getFontHeight();
            fontRenderer2.drawStringWithShadow((int)function.apply(textwidth), o[0], rgb.getRed(), rgb.getGreen(), rgb.getBlue(), text);
            o[n] = (int)((double)o[n] + (textheight + 0.2));
            return;
        });
        component.setHeight(y[0]);
        GL11.glEnable(2884);
        GL11.glDisable(3042);
    }
    
    @Override
    public void handleSizeComponent(final ActiveModules component) {
        component.setWidth(100);
        component.setHeight(100);
    }
}
