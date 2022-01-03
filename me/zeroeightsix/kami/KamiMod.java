//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami;

import net.minecraftforge.fml.common.*;
import me.zeroeightsix.kami.gui.kami.*;
import com.google.common.base.*;
import org.lwjgl.opengl.*;
import net.minecraftforge.fml.common.event.*;
import java.util.function.*;
import net.minecraftforge.common.*;
import me.zeroeightsix.kami.event.*;
import me.zeroeightsix.kami.util.*;
import me.zeroeightsix.kami.command.*;
import me.zeroeightsix.kami.setting.*;
import me.zeroeightsix.kami.module.*;
import java.nio.file.*;
import me.zeroeightsix.kami.setting.config.*;
import me.zeroeightsix.kami.gui.rgui.component.container.use.*;
import me.zeroeightsix.kami.gui.rgui.util.*;
import me.zeroeightsix.kami.gui.rgui.component.container.*;
import me.zeroeightsix.kami.gui.rgui.component.*;
import com.google.gson.*;
import java.util.*;
import java.nio.file.attribute.*;
import java.io.*;
import org.apache.logging.log4j.*;
import me.zero.alpine.*;

@Mod(modid = "opfern", name = "Opfern", version = "1.3.0Beta")
public class KamiMod
{
    public static final String MODID = "opfern";
    public static final String MODNAME = "Opfern";
    public static final String MODVER = "1.3.0Beta";
    public static final String KAMI_HIRAGANA = "\u534d";
    public static final String KAMI_KATAKANA = "\u534d";
    public static final String KAMI_KANJI = "\u1d0f\u1d18\ua730\u1d07\u0280\u0274";
    private static final String KAMI_CONFIG_NAME_DEFAULT = "opfern/OPFERNConfig.json";
    public static final Logger log;
    public static final EventBus EVENT_BUS;
    @Mod.Instance
    private static KamiMod INSTANCE;
    public KamiGUI guiManager;
    public CommandManager commandManager;
    private Setting<JsonObject> guiStateSetting;
    
    public KamiMod() {
        this.guiStateSetting = Settings.custom("gui", new JsonObject(), new Converter<JsonObject, JsonObject>() {
            protected JsonObject doForward(final JsonObject jsonObject) {
                return jsonObject;
            }
            
            protected JsonObject doBackward(final JsonObject jsonObject) {
                return jsonObject;
            }
        }).buildAndRegister("");
    }
    
    @Mod.EventHandler
    public void postInit(final FMLPostInitializationEvent event) {
        Display.setTitle("Opfern 1.3.0Beta");
    }
    
    @Mod.EventHandler
    public void preInit(final FMLPreInitializationEvent event) {
    }
    
    @Mod.EventHandler
    public void init(final FMLInitializationEvent event) throws Exception {
        KamiMod.log.info("\n\nInitializing Opfern 1.3.0Beta");
        ModuleManager.initialize();
        ModuleManager.getModules().stream().filter(module -> module.alwaysListening).forEach(KamiMod.EVENT_BUS::subscribe);
        MinecraftForge.EVENT_BUS.register((Object)new ForgeEventProcessor());
        MinecraftForge.EVENT_BUS.register((Object)new Rainbow());
        MinecraftForge.EVENT_BUS.register((Object)new Rainbow2());
        LagCompensator.INSTANCE = new LagCompensator();
        Wrapper.init();
        (this.guiManager = new KamiGUI()).initializeGUI();
        this.commandManager = new CommandManager();
        Friends.initFriends();
        SettingsRegister.register("commandPrefix", Command.commandPrefix);
        loadConfiguration();
        KamiMod.log.info("Settings loaded");
        ModuleManager.updateLookup();
        ModuleManager.getModules().stream().filter(Module::isEnabled).forEach(Module::enable);
        KamiMod.log.info("Opfern Mod initialized!\n");
    }
    
    public static String getConfigName() {
        final Path config = Paths.get("opfern/OPFERNLastConfig.txt", new String[0]);
        String kamiConfigName = "opfern/OPFERNConfig.json";
        try (final BufferedReader reader = Files.newBufferedReader(config)) {
            kamiConfigName = reader.readLine();
            if (!isFilenameValid(kamiConfigName)) {
                kamiConfigName = "opfern/OPFERNConfig.json";
            }
        }
        catch (NoSuchFileException e3) {
            try (final BufferedWriter writer = Files.newBufferedWriter(config, new OpenOption[0])) {
                writer.write("opfern/OPFERNConfig.json");
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        catch (IOException e2) {
            e2.printStackTrace();
        }
        return kamiConfigName;
    }
    
    public static void loadConfiguration() {
        try {
            loadConfigurationUnsafe();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void doEpic() {
        doEpic();
    }
    
    public static void loadConfigurationUnsafe() throws IOException {
        final String kamiConfigName = getConfigName();
        final Path kamiConfig = Paths.get(kamiConfigName, new String[0]);
        if (!Files.exists(kamiConfig, new LinkOption[0])) {
            return;
        }
        Configuration.loadConfiguration(kamiConfig);
        final JsonObject gui = KamiMod.INSTANCE.guiStateSetting.getValue();
        for (final Map.Entry<String, JsonElement> entry : gui.entrySet()) {
            final Optional<Component> optional = (Optional<Component>)KamiMod.INSTANCE.guiManager.getChildren().stream().filter(component -> component instanceof Frame).filter(component -> component.getTitle().equals(entry.getKey())).findFirst();
            if (optional.isPresent()) {
                final JsonObject object = entry.getValue().getAsJsonObject();
                final Frame frame = (Frame)optional.get();
                frame.setX(object.get("x").getAsInt());
                frame.setY(object.get("y").getAsInt());
                final Docking docking = Docking.values()[object.get("docking").getAsInt()];
                if (docking.isLeft()) {
                    ContainerHelper.setAlignment((Container)frame, AlignedComponent.Alignment.LEFT);
                }
                else if (docking.isRight()) {
                    ContainerHelper.setAlignment((Container)frame, AlignedComponent.Alignment.RIGHT);
                }
                else if (docking.isCenterVertical()) {
                    ContainerHelper.setAlignment((Container)frame, AlignedComponent.Alignment.CENTER);
                }
                frame.setDocking(docking);
                frame.setMinimized(object.get("minimized").getAsBoolean());
                frame.setPinned(object.get("pinned").getAsBoolean());
            }
            else {
                System.err.println("Found GUI config entry for " + entry.getKey() + ", but found no frame with that name");
            }
        }
        getInstance().getGuiManager().getChildren().stream().filter(component -> component instanceof Frame && component.isPinneable() && ((Component)component).isVisible()).forEach(component -> component.setOpacity(0.0f));
    }
    
    public static void saveConfiguration() {
        try {
            saveConfigurationUnsafe();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void saveConfigurationUnsafe() throws IOException {
        final JsonObject object = new JsonObject();
        final JsonObject frameObject;
        final JsonObject jsonObject;
        KamiMod.INSTANCE.guiManager.getChildren().stream().filter(component -> component instanceof Frame).map(component -> component).forEach(frame -> {
            frameObject = new JsonObject();
            frameObject.add("x", (JsonElement)new JsonPrimitive((Number)frame.getX()));
            frameObject.add("y", (JsonElement)new JsonPrimitive((Number)frame.getY()));
            frameObject.add("docking", (JsonElement)new JsonPrimitive((Number)Arrays.asList(Docking.values()).indexOf(frame.getDocking())));
            frameObject.add("minimized", (JsonElement)new JsonPrimitive(Boolean.valueOf(frame.isMinimized())));
            frameObject.add("pinned", (JsonElement)new JsonPrimitive(Boolean.valueOf(frame.isPinned())));
            jsonObject.add(frame.getTitle(), (JsonElement)frameObject);
            return;
        });
        KamiMod.INSTANCE.guiStateSetting.setValue(object);
        final Path outputFile = Paths.get(getConfigName(), new String[0]);
        if (!Files.exists(outputFile, new LinkOption[0])) {
            Files.createFile(outputFile, (FileAttribute<?>[])new FileAttribute[0]);
        }
        Configuration.saveConfiguration(outputFile);
        ModuleManager.getModules().forEach(Module::destroy);
    }
    
    public static boolean isFilenameValid(final String file) {
        final File f = new File(file);
        try {
            f.getCanonicalPath();
            return true;
        }
        catch (IOException e) {
            return false;
        }
    }
    
    public static KamiMod getInstance() {
        return KamiMod.INSTANCE;
    }
    
    public KamiGUI getGuiManager() {
        return this.guiManager;
    }
    
    public CommandManager getCommandManager() {
        return this.commandManager;
    }
    
    static {
        log = LogManager.getLogger("Opfern");
        EVENT_BUS = (EventBus)new EventManager();
    }
}
