//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.module;

import me.zeroeightsix.kami.util.*;
import net.minecraft.client.*;
import java.awt.*;
import me.zeroeightsix.kami.setting.*;
import com.google.common.base.*;
import java.util.*;
import me.zeroeightsix.kami.event.events.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraftforge.fml.common.eventhandler.*;
import me.zeroeightsix.kami.*;
import me.zeroeightsix.kami.setting.builder.*;
import com.google.gson.*;
import org.lwjgl.input.*;
import java.lang.annotation.*;

public class Module
{
    private final String originalName;
    private final Setting<String> name;
    private final String description;
    private final Category category;
    private Setting<Bind> bind;
    private Setting<Boolean> enabled;
    private Setting<Boolean> visible;
    public boolean alwaysListening;
    protected static final Minecraft mc;
    public static Module INSTANCE;
    float hue;
    Color c;
    int rgb;
    int speed;
    public List<Setting> settingList;
    
    public Module() {
        this.originalName = this.getAnnotation().name();
        this.name = this.register(Settings.s("Name", this.originalName));
        this.description = this.getAnnotation().description();
        this.category = this.getAnnotation().category();
        this.bind = this.register(Settings.custom("Bind", Bind.none(), new BindConverter()).build());
        this.enabled = this.register(Settings.booleanBuilder("Enabled").withVisibility(aBoolean -> false).withValue(false).build());
        this.visible = this.register(Settings.booleanBuilder("Drawn").withVisibility(aBoolean -> false).withValue(true).build());
        this.hue = 0.0f;
        this.speed = 2;
        this.settingList = new ArrayList<Setting>();
        Module.INSTANCE = this;
        this.alwaysListening = this.getAnnotation().alwaysListening();
        this.registerAll(this.bind, this.enabled);
    }
    
    private Info getAnnotation() {
        if (this.getClass().isAnnotationPresent(Info.class)) {
            return this.getClass().getAnnotation(Info.class);
        }
        throw new IllegalStateException("No Annotation on class " + this.getClass().getCanonicalName() + "!");
    }
    
    public int getRgb() {
        return this.rgb;
    }
    
    public Color getC() {
        return this.c;
    }
    
    public void onUpdate() {
    }
    
    public void onRender() {
    }
    
    public void onWorldRender(final RenderEvent event) {
    }
    
    public Bind getBind() {
        return this.bind.getValue();
    }
    
    public String getBindName() {
        return this.bind.getValue().toString();
    }
    
    public void setName(final String name) {
        this.name.setValue(name);
        ModuleManager.updateLookup();
    }
    
    public void setDrawn(final boolean drawn) {
        this.visible.setValue(drawn);
    }
    
    public boolean getDrawn() {
        return this.visible.getValue();
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        this.c = Color.getHSBColor(this.hue, 1.0f, 1.0f);
        this.rgb = Color.HSBtoRGB(this.hue, 1.0f, 1.0f);
        this.hue += this.speed / 2000.0f;
        if (Module.mc.player != null) {
            ModuleManager.onUpdate();
        }
    }
    
    public String getOriginalName() {
        return this.originalName;
    }
    
    public String getName() {
        return this.name.getValue();
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public Category getCategory() {
        return this.category;
    }
    
    public boolean isEnabled() {
        return this.enabled.getValue();
    }
    
    protected void onEnable() {
    }
    
    protected void onDisable() {
    }
    
    public void toggle() {
        this.setEnabled(!this.isEnabled());
    }
    
    public void enable() {
        this.enabled.setValue(true);
        this.onEnable();
        if (!this.alwaysListening) {
            KamiMod.EVENT_BUS.subscribe((Object)this);
        }
    }
    
    public void disable() {
        this.enabled.setValue(false);
        this.onDisable();
        if (!this.alwaysListening) {
            KamiMod.EVENT_BUS.unsubscribe((Object)this);
        }
    }
    
    public boolean isDisabled() {
        return !this.isEnabled();
    }
    
    public void setEnabled(final boolean enabled) {
        final boolean prev = this.enabled.getValue();
        if (prev != enabled) {
            if (enabled) {
                this.enable();
            }
            else {
                this.disable();
            }
        }
    }
    
    public String getHudInfo() {
        return null;
    }
    
    protected final void setAlwaysListening(final boolean alwaysListening) {
        this.alwaysListening = alwaysListening;
        if (alwaysListening) {
            KamiMod.EVENT_BUS.subscribe((Object)this);
        }
        if (!alwaysListening && this.isDisabled()) {
            KamiMod.EVENT_BUS.unsubscribe((Object)this);
        }
    }
    
    public void destroy() {
    }
    
    protected void registerAll(final Setting... settings) {
        for (final Setting setting : settings) {
            this.register((Setting<Object>)setting);
        }
    }
    
    protected <T> Setting<T> register(final Setting<T> setting) {
        if (this.settingList == null) {
            this.settingList = new ArrayList<Setting>();
        }
        this.settingList.add(setting);
        return SettingBuilder.register(setting, "modules." + this.originalName);
    }
    
    protected <T> Setting<T> register(final SettingBuilder<T> builder) {
        if (this.settingList == null) {
            this.settingList = new ArrayList<Setting>();
        }
        final Setting<T> setting = builder.buildAndRegister("modules." + this.name);
        this.settingList.add(setting);
        return setting;
    }
    
    static {
        mc = Minecraft.getMinecraft();
    }
    
    public enum Category
    {
        COMBAT("Combat", false), 
        RENDER("Render", false), 
        MISC("Misc", false), 
        PLAYER("Player", false), 
        MOVEMENT("Movement", false), 
        EXPLOIT("Exploit", false), 
        HUD("HUD", false), 
        HIDDEN("Hidden", true);
        
        boolean hidden;
        String name;
        
        private Category(final String name, final boolean hidden) {
            this.name = name;
            this.hidden = hidden;
        }
        
        public boolean isHidden() {
            return this.hidden;
        }
        
        public String getName() {
            return this.name;
        }
    }
    
    private class BindConverter extends Converter<Bind, JsonElement>
    {
        protected JsonElement doForward(final Bind bind) {
            return (JsonElement)new JsonPrimitive(bind.toString());
        }
        
        protected Bind doBackward(final JsonElement jsonElement) {
            String s = jsonElement.getAsString();
            if (s.equalsIgnoreCase("None")) {
                return Bind.none();
            }
            boolean ctrl = false;
            boolean alt = false;
            boolean shift = false;
            if (s.startsWith("Ctrl+")) {
                ctrl = true;
                s = s.substring(5);
            }
            if (s.startsWith("Alt+")) {
                alt = true;
                s = s.substring(4);
            }
            if (s.startsWith("Shift+")) {
                shift = true;
                s = s.substring(6);
            }
            int key = -1;
            try {
                key = Keyboard.getKeyIndex(s.toUpperCase());
            }
            catch (Exception ex) {}
            if (key == 0) {
                return Bind.none();
            }
            return new Bind(ctrl, alt, shift, key);
        }
    }
    
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Info {
        String name();
        
        String description() default "Descriptionless";
        
        Category category();
        
        boolean alwaysListening() default false;
    }
}
