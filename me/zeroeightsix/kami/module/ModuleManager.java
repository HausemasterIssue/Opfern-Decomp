//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.module;

import me.zeroeightsix.kami.module.modules.*;
import java.lang.reflect.*;
import me.zeroeightsix.kami.*;
import java.util.function.*;
import java.util.*;
import net.minecraftforge.client.event.*;
import net.minecraft.client.*;
import net.minecraft.entity.*;
import me.zeroeightsix.kami.util.*;
import me.zeroeightsix.kami.event.events.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.math.*;

public class ModuleManager
{
    public static ArrayList<Module> modules;
    static HashMap<String, Module> lookup;
    
    public static void updateLookup() {
        ModuleManager.lookup.clear();
        for (final Module m : ModuleManager.modules) {
            ModuleManager.lookup.put(m.getName().toLowerCase(), m);
        }
    }
    
    public static void initialize() {
        final Set<Class> classList = ClassFinder.findClasses(ClickGUI.class.getPackage().getName(), Module.class);
        Module module;
        classList.forEach(aClass -> {
            try {
                module = aClass.getConstructor((Class<?>[])new Class[0]).newInstance(new Object[0]);
                ModuleManager.modules.add(module);
            }
            catch (InvocationTargetException e) {
                e.getCause().printStackTrace();
                System.err.println("Couldn't initiate module " + aClass.getSimpleName() + "! Err: " + e.getClass().getSimpleName() + ", message: " + e.getMessage());
            }
            catch (Exception e2) {
                e2.printStackTrace();
                System.err.println("Couldn't initiate module " + aClass.getSimpleName() + "! Err: " + e2.getClass().getSimpleName() + ", message: " + e2.getMessage());
            }
            return;
        });
        KamiMod.log.info("Modules initialised");
        getModules().sort(Comparator.comparing((Function<? super Module, ? extends Comparable>)Module::getName));
    }
    
    public static void onUpdate() {
        ModuleManager.modules.stream().filter(module -> module.alwaysListening || module.isEnabled()).forEach(module -> module.onUpdate());
    }
    
    public static void onRender() {
        ModuleManager.modules.stream().filter(module -> module.alwaysListening || module.isEnabled()).forEach(module -> module.onRender());
    }
    
    public static void onWorldRender(final RenderWorldLastEvent event) {
        Minecraft.getMinecraft().profiler.startSection("kami");
        Minecraft.getMinecraft().profiler.startSection("setup");
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.shadeModel(7425);
        GlStateManager.disableDepth();
        GlStateManager.glLineWidth(1.0f);
        final Vec3d renderPos = EntityUtil.getInterpolatedPos((Entity)Wrapper.getPlayer(), event.getPartialTicks());
        final RenderEvent e = new RenderEvent((Tessellator)KamiTessellator.INSTANCE, renderPos);
        e.resetTranslation();
        Minecraft.getMinecraft().profiler.endSection();
        final RenderEvent renderEvent;
        ModuleManager.modules.stream().filter(module -> module.alwaysListening || module.isEnabled()).forEach(module -> {
            Minecraft.getMinecraft().profiler.startSection(module.getName());
            module.onWorldRender(renderEvent);
            Minecraft.getMinecraft().profiler.endSection();
            return;
        });
        Minecraft.getMinecraft().profiler.startSection("release");
        GlStateManager.glLineWidth(1.0f);
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.enableDepth();
        GlStateManager.enableCull();
        KamiTessellator.releaseGL();
        Minecraft.getMinecraft().profiler.endSection();
        Minecraft.getMinecraft().profiler.endSection();
    }
    
    public static void onBind(final int eventKey) {
        if (eventKey == 0) {
            return;
        }
        ModuleManager.modules.forEach(module -> {
            if (module.getBind().isDown(eventKey)) {
                module.toggle();
            }
        });
    }
    
    public static ArrayList<Module> getModules() {
        return ModuleManager.modules;
    }
    
    public static Module getModuleByName(final String name) {
        return ModuleManager.lookup.get(name.toLowerCase());
    }
    
    public static boolean isModuleEnabled(final String moduleName) {
        final Module m = getModuleByName(moduleName);
        return m != null && m.isEnabled();
    }
    
    static {
        ModuleManager.modules = new ArrayList<Module>();
        ModuleManager.lookup = new HashMap<String, Module>();
    }
}
