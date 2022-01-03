//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.gui.kami;

import me.zeroeightsix.kami.gui.rgui.*;
import me.zeroeightsix.kami.gui.rgui.render.theme.*;
import me.zeroeightsix.kami.gui.kami.theme.kami.*;
import net.minecraft.client.gui.*;
import me.zeroeightsix.kami.module.*;
import me.zeroeightsix.kami.gui.rgui.layout.*;
import me.zeroeightsix.kami.gui.rgui.component.*;
import me.zeroeightsix.kami.gui.rgui.poof.*;
import me.zeroeightsix.kami.gui.rgui.component.container.use.*;
import me.zeroeightsix.kami.gui.rgui.component.container.*;
import me.zeroeightsix.kami.gui.rgui.component.use.*;
import net.minecraft.client.*;
import me.zeroeightsix.kami.gui.rgui.render.font.*;
import me.zeroeightsix.kami.module.modules.combat.*;
import me.zeroeightsix.kami.module.modules.HUD.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import java.math.*;
import net.minecraft.entity.*;
import me.zeroeightsix.kami.command.*;
import me.zeroeightsix.kami.util.*;
import com.mojang.realmsclient.gui.*;
import me.zeroeightsix.kami.gui.rgui.component.listen.*;
import net.minecraft.entity.player.*;
import java.util.stream.*;
import net.minecraft.util.text.*;
import java.util.function.*;
import me.zeroeightsix.kami.gui.kami.component.*;
import java.text.*;
import javax.annotation.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.projectile.*;
import java.util.*;
import me.zeroeightsix.kami.gui.rgui.util.*;

public class KamiGUI extends GUI
{
    public static final RootFontRenderer fontRenderer;
    public Theme theme;
    public static ColourHolder primaryColour;
    private static final int DOCK_OFFSET = 0;
    
    public KamiGUI() {
        super(new KamiTheme());
        this.theme = this.getTheme();
    }
    
    @Override
    public void drawGUI() {
        super.drawGUI();
    }
    
    @Override
    public void initializeGUI() {
        final ScaledResolution scaledResolution = new ScaledResolution(Wrapper.getMinecraft());
        final int width = scaledResolution.getScaledWidth();
        final int height = scaledResolution.getScaledHeight();
        final HashMap<Module.Category, Pair<Scrollpane, SettingsPanel>> categoryScrollpaneHashMap = new HashMap<Module.Category, Pair<Scrollpane, SettingsPanel>>();
        for (final Module module : ModuleManager.getModules()) {
            if (module.getCategory().isHidden()) {
                continue;
            }
            final Module.Category moduleCategory = module.getCategory();
            if (!categoryScrollpaneHashMap.containsKey(moduleCategory)) {
                final Stretcherlayout stretcherlayout = new Stretcherlayout(1);
                stretcherlayout.setComponentOffsetWidth(0);
                final Scrollpane scrollpane = new Scrollpane(this.getTheme(), stretcherlayout, 300, 260);
                scrollpane.setMaximumHeight(260);
                categoryScrollpaneHashMap.put(moduleCategory, new Pair<Scrollpane, SettingsPanel>(scrollpane, new SettingsPanel(this.getTheme(), (Module)null)));
            }
            final Pair<Scrollpane, SettingsPanel> pair = categoryScrollpaneHashMap.get(moduleCategory);
            final Scrollpane scrollpane = pair.getKey();
            final CheckButton checkButton = new CheckButton(module.getName());
            checkButton.setToggled(module.isEnabled());
            final CheckButton checkButton2;
            final Module module2;
            checkButton.addTickListener(() -> {
                checkButton2.setToggled(module2.isEnabled());
                checkButton2.setName(module2.getName());
                return;
            });
            checkButton.addMouseListener(new MouseListener() {
                @Override
                public void onMouseDown(final MouseButtonEvent event) {
                    if (event.getButton() == 1) {
                        if (pair.getValue().getModule() == module) {
                            pair.getValue().kill();
                            pair.getValue().setModule((Module)null);
                        }
                        else {
                            pair.getValue().setModule(module);
                            pair.getValue().setVisible(true);
                            pair.getValue().setX(checkButton.getX() + 4);
                            pair.getValue().setY(checkButton.getHeight() + checkButton.getY());
                        }
                    }
                }
                
                @Override
                public void onMouseRelease(final MouseButtonEvent event) {
                }
                
                @Override
                public void onMouseDrag(final MouseButtonEvent event) {
                }
                
                @Override
                public void onMouseMove(final MouseMoveEvent event) {
                }
                
                @Override
                public void onScroll(final MouseScrollEvent event) {
                }
            });
            checkButton.addPoof(new CheckButton.CheckButtonPoof<CheckButton, CheckButton.CheckButtonPoof.CheckButtonPoofInfo>() {
                @Override
                public void execute(final CheckButton component, final CheckButtonPoofInfo info) {
                    if (info.getAction().equals(CheckButtonPoofInfo.CheckButtonPoofInfoAction.TOGGLE)) {
                        module.setEnabled(checkButton.isToggled());
                    }
                }
            });
            scrollpane.addChild(checkButton);
        }
        int x = 10;
        int nexty;
        int y = nexty = 10;
        for (final Map.Entry<Module.Category, Pair<Scrollpane, SettingsPanel>> entry : categoryScrollpaneHashMap.entrySet()) {
            final Stretcherlayout stretcherlayout2 = new Stretcherlayout(1);
            stretcherlayout2.COMPONENT_OFFSET_Y = 1;
            final Frame frame = new Frame(this.getTheme(), stretcherlayout2, entry.getKey().getName());
            final Scrollpane scrollpane2 = entry.getValue().getKey();
            frame.addChild(scrollpane2);
            frame.addChild((Component)entry.getValue().getValue());
            scrollpane2.setOriginOffsetY(0);
            scrollpane2.setOriginOffsetX(0);
            frame.setCloseable(false);
            frame.setX(x);
            frame.setY(y);
            this.addChild(frame);
            nexty = Math.max(y + frame.getHeight() + 10, nexty);
            x += frame.getWidth() + 10;
            if (x > Wrapper.getMinecraft().displayWidth / 1.2f) {
                y = (nexty = nexty);
            }
        }
        this.addMouseListener(new MouseListener() {
            private boolean isBetween(final int min, final int val, final int max) {
                return val <= max && val >= min;
            }
            
            @Override
            public void onMouseDown(final MouseButtonEvent event) {
                final List<SettingsPanel> panels = ContainerHelper.getAllChildren((Class<? extends SettingsPanel>)SettingsPanel.class, (Container)KamiGUI.this);
                for (final SettingsPanel settingsPanel : panels) {
                    if (!settingsPanel.isVisible()) {
                        continue;
                    }
                    final int[] real = GUI.calculateRealPosition((Component)settingsPanel);
                    final int pX = event.getX() - real[0];
                    final int pY = event.getY() - real[1];
                    if (this.isBetween(0, pX, settingsPanel.getWidth()) && this.isBetween(0, pY, settingsPanel.getHeight())) {
                        continue;
                    }
                    settingsPanel.setVisible(false);
                }
            }
            
            @Override
            public void onMouseRelease(final MouseButtonEvent event) {
            }
            
            @Override
            public void onMouseDrag(final MouseButtonEvent event) {
            }
            
            @Override
            public void onMouseMove(final MouseMoveEvent event) {
            }
            
            @Override
            public void onScroll(final MouseScrollEvent event) {
            }
        });
        final ArrayList<Frame> frames = new ArrayList<Frame>();
        Frame frame2 = new Frame(this.getTheme(), new Stretcherlayout(1), "Active modules");
        frame2.setCloseable(false);
        frame2.addChild((Component)new ActiveModules());
        frame2.setPinneable(true);
        frames.add(frame2);
        frame2 = new Frame(this.getTheme(), new Stretcherlayout(1), "Info");
        frame2.setCloseable(false);
        frame2.setPinneable(true);
        final Label information = new Label("");
        information.setShadow(true);
        final Label label;
        final StringBuilder sb;
        information.addTickListener(() -> {
            label.setText("");
            label.addLine("§a\u1d0f\u1d18\ua730\u1d07\u0280\u0274§8 1.3.0Beta");
            label.addLine("§a" + Math.round(LagCompensator.INSTANCE.getTickRate()) + "§8 tps");
            new StringBuilder().append("§a");
            Wrapper.getMinecraft();
            label.addLine(sb.append(Minecraft.debugFPS).append("§8 fps").toString());
            return;
        });
        frame2.addChild(information);
        information.setFontRenderer(KamiGUI.fontRenderer);
        frames.add(frame2);
        frame2 = new Frame(this.getTheme(), new Stretcherlayout(1), "Safe");
        frame2.setCloseable(false);
        frame2.setPinneable(true);
        final Label safe = new Label("");
        safe.setShadow(false);
        final Label label2;
        safe.addTickListener(() -> {
            label2.setText("");
            if (AutoFeetPlace.isSafe()) {
                label2.addLine("§6 SAFE");
            }
            else {
                label2.addLine("§4 NOT SAFE");
            }
            return;
        });
        frame2.addChild(safe);
        safe.setFontRenderer(KamiGUI.fontRenderer);
        frames.add(frame2);
        frame2 = new Frame(this.getTheme(), new Stretcherlayout(1), "PvPInfo");
        frame2.setCloseable(false);
        frame2.setPinneable(true);
        final Label pinfo = new Label("");
        pinfo.setShadow(false);
        final Label label3;
        final PvPInfo pvp;
        pinfo.addTickListener(() -> {
            label3.setText("");
            pvp = (PvPInfo)ModuleManager.getModuleByName("PvPInfo");
            if (pvp.getSex() == PvPInfo.sex.Longhand) {
                if (ModuleManager.getModuleByName("AutoCrystal").isEnabled()) {
                    label3.addLine("§aAuto Crystal");
                }
                else {
                    label3.addLine("§4Auto Crystal");
                }
                if (ModuleManager.getModuleByName("OffhandAC").isEnabled()) {
                    label3.addLine("§aOffhandAC");
                }
                else {
                    label3.addLine("§4OffhandAC");
                }
                if (ModuleManager.getModuleByName("Surround").isEnabled()) {
                    label3.addLine("§aSurround");
                }
                else {
                    label3.addLine("§4Surround");
                }
                if (ModuleManager.getModuleByName("AutoTrap").isEnabled()) {
                    label3.addLine("§aTrap");
                }
                else {
                    label3.addLine("§4Trap");
                }
                if (ModuleManager.getModuleByName("KillAura").isEnabled()) {
                    label3.addLine("§aKillAura");
                }
                else {
                    label3.addLine("§4KillAura");
                }
                if (ModuleManager.getModuleByName("HoleFill").isEnabled()) {
                    label3.addLine("§aHoleFill");
                }
                else {
                    label3.addLine("§4HoleFill");
                }
            }
            if (pvp.getSex() == PvPInfo.sex.Shorthand) {
                if (ModuleManager.getModuleByName("AutoCrystal").isEnabled()) {
                    label3.addLine("§aAC");
                }
                else {
                    label3.addLine("§4AC");
                }
                if (ModuleManager.getModuleByName("OffhandAC").isEnabled()) {
                    label3.addLine("§aOAC");
                }
                else {
                    label3.addLine("§4OAC");
                }
                if (ModuleManager.getModuleByName("Surround").isEnabled()) {
                    label3.addLine("§aSU");
                }
                else {
                    label3.addLine("§4SU");
                }
                if (ModuleManager.getModuleByName("AutoTrap").isEnabled()) {
                    label3.addLine("§aAT");
                }
                else {
                    label3.addLine("§4AT");
                }
                if (ModuleManager.getModuleByName("KillAura").isEnabled()) {
                    label3.addLine("§aKA");
                }
                else {
                    label3.addLine("§4KA");
                }
                if (ModuleManager.getModuleByName("HoleFill").isEnabled()) {
                    label3.addLine("§aHF");
                }
                else {
                    label3.addLine("§4HF");
                }
            }
            return;
        });
        frame2.addChild(pinfo);
        pinfo.setFontRenderer(KamiGUI.fontRenderer);
        frames.add(frame2);
        frame2 = new Frame(this.getTheme(), new Stretcherlayout(1), "Crystals");
        frame2.setCloseable(false);
        frame2.setPinneable(true);
        final Label crystals = new Label("");
        crystals.setShadow(false);
        final Label label4;
        int total;
        int i;
        ItemStack stack;
        crystals.addTickListener(() -> {
            label4.setText("");
            total = 0;
            for (i = 0; i < 45; ++i) {
                stack = Wrapper.getPlayer().inventory.getStackInSlot(i);
                if (stack != ItemStack.EMPTY) {
                    if (stack.getItem() instanceof ItemEndCrystal) {
                        total += stack.stackSize;
                    }
                }
            }
            label4.addLine("§fCrystals: §a" + Integer.toString(total));
            return;
        });
        frame2.addChild(crystals);
        crystals.setFontRenderer(KamiGUI.fontRenderer);
        frames.add(frame2);
        frame2 = new Frame(this.getTheme(), new Stretcherlayout(1), "Totems");
        frame2.setCloseable(false);
        frame2.setPinneable(true);
        final Label totems = new Label("");
        totems.setShadow(false);
        final Label label5;
        int total2;
        int j;
        ItemStack stack2;
        totems.addTickListener(() -> {
            label5.setText("");
            total2 = 0;
            for (j = 0; j < 45; ++j) {
                stack2 = Wrapper.getPlayer().inventory.getStackInSlot(j);
                if (stack2 != ItemStack.EMPTY) {
                    if (stack2.getItem() == Items.TOTEM_OF_UNDYING) {
                        total2 += stack2.stackSize;
                    }
                }
            }
            label5.addLine("§fTotems: §a" + Integer.toString(total2));
            return;
        });
        frame2.addChild(totems);
        totems.setFontRenderer(KamiGUI.fontRenderer);
        frames.add(frame2);
        frame2 = new Frame(this.getTheme(), new Stretcherlayout(1), "Gapples");
        frame2.setCloseable(false);
        frame2.setPinneable(true);
        final Label gapples = new Label("");
        gapples.setShadow(false);
        final Label label6;
        int total3;
        int k;
        ItemStack stack3;
        gapples.addTickListener(() -> {
            label6.setText("");
            total3 = 0;
            for (k = 0; k < 45; ++k) {
                stack3 = Wrapper.getPlayer().inventory.getStackInSlot(k);
                if (stack3 != ItemStack.EMPTY) {
                    if (stack3.getItem() == Items.GOLDEN_APPLE) {
                        total3 += stack3.stackSize;
                    }
                }
            }
            label6.addLine("§fGapples: §a" + Integer.toString(total3));
            return;
        });
        frame2.addChild(gapples);
        gapples.setFontRenderer(KamiGUI.fontRenderer);
        frames.add(frame2);
        frame2 = new Frame(this.getTheme(), new Stretcherlayout(1), "Obsidian");
        frame2.setCloseable(false);
        frame2.setPinneable(true);
        final Label obsidian = new Label("");
        obsidian.setShadow(false);
        final Label label7;
        int total4;
        int l;
        ItemStack stack4;
        obsidian.addTickListener(() -> {
            label7.setText("");
            total4 = 0;
            for (l = 0; l < 45; ++l) {
                stack4 = Wrapper.getPlayer().inventory.getStackInSlot(l);
                if (stack4 != ItemStack.EMPTY) {
                    if (stack4.getItem() == Item.getItemFromBlock(Blocks.OBSIDIAN)) {
                        total4 += stack4.stackSize;
                    }
                }
            }
            label7.addLine("§fObsidian: §a" + Integer.toString(total4));
            return;
        });
        frame2.addChild(obsidian);
        obsidian.setFontRenderer(KamiGUI.fontRenderer);
        frames.add(frame2);
        frame2 = new Frame(this.getTheme(), new Stretcherlayout(1), "PlayerInfo");
        frame2.setCloseable(false);
        frame2.setPinneable(true);
        final Label plinfo = new Label("");
        plinfo.setShadow(false);
        final Label label8;
        plinfo.addTickListener(() -> {
            label8.setText("");
            label8.addLine("§fHealth: §b" + (int)Wrapper.getPlayer().getHealth());
            label8.addLine("§fHunger: §b" + Wrapper.getPlayer().getFoodStats().getFoodLevel());
            label8.addLine("§fSaturation: §b" + (int)Wrapper.getPlayer().getFoodStats().getSaturationLevel());
            return;
        });
        frame2.addChild(plinfo);
        plinfo.setFontRenderer(KamiGUI.fontRenderer);
        frames.add(frame2);
        frame2 = new Frame(this.getTheme(), new Stretcherlayout(1), "Text Radar");
        final Label list = new Label("");
        final DecimalFormat dfHealth = new DecimalFormat("#.#");
        dfHealth.setRoundingMode(RoundingMode.HALF_UP);
        final StringBuilder healthSB = new StringBuilder();
        final Label label9;
        Minecraft mc;
        List<EntityPlayer> entityList;
        Map<String, Integer> players;
        int playerStep;
        final Iterator<EntityPlayer> iterator3;
        Entity e;
        float hpRaw;
        final NumberFormat numberFormat;
        String hp;
        final StringBuilder sb2;
        int responseTime;
        boolean friend;
        int ping;
        Map<String, Integer> players2;
        final Iterator<Map.Entry<String, Integer>> iterator4;
        Map.Entry<String, Integer> player;
        list.addTickListener(() -> {
            if (!label9.isVisible()) {
                return;
            }
            else {
                label9.setText("");
                mc = Wrapper.getMinecraft();
                if (mc.player == null) {
                    return;
                }
                else {
                    entityList = (List<EntityPlayer>)mc.world.playerEntities;
                    players = new HashMap<String, Integer>();
                    playerStep = 0;
                    entityList.iterator();
                    while (iterator3.hasNext()) {
                        e = (Entity)iterator3.next();
                        if (playerStep >= 7) {
                            break;
                        }
                        else if (e.getName().equals(mc.player.getName())) {
                            continue;
                        }
                        else {
                            hpRaw = ((EntityLivingBase)e).getHealth() + ((EntityLivingBase)e).getAbsorptionAmount();
                            hp = numberFormat.format(hpRaw);
                            sb2.append(Command.SECTIONSIGN());
                            if (hpRaw >= 20.0f) {
                                sb2.append("a");
                            }
                            else if (hpRaw >= 10.0f) {
                                sb2.append("e");
                            }
                            else if (hpRaw >= 5.0f) {
                                sb2.append("6");
                            }
                            else {
                                sb2.append("c");
                            }
                            sb2.append(hp);
                            if (e.getUniqueID() == null) {
                                responseTime = 0;
                            }
                            else if (mc.player.connection.getPlayerInfo(e.getUniqueID()) == null) {
                                responseTime = 0;
                            }
                            else {
                                responseTime = mc.player.connection.getPlayerInfo(e.getUniqueID()).getResponseTime();
                            }
                            friend = Friends.isFriend(e.getName());
                            ping = responseTime;
                            players.put((friend ? "§b" : "§f") + e.getName() + " " + sb2.toString() + ChatFormatting.WHITE + " " + ping + "ms", (int)mc.player.getDistance(e));
                            sb2.setLength(0);
                            ++playerStep;
                        }
                    }
                    if (players.isEmpty()) {
                        label9.setText("");
                        return;
                    }
                    else {
                        players2 = sortByValue(players);
                        players2.entrySet().iterator();
                        while (iterator4.hasNext()) {
                            player = iterator4.next();
                            label9.addLine(Command.SECTIONSIGN() + "7" + player.getKey() + " " + Command.SECTIONSIGN() + "4" + player.getValue());
                        }
                        return;
                    }
                }
            }
        });
        frame2.setCloseable(false);
        frame2.setPinneable(true);
        frame2.setMinimumWidth(75);
        list.setShadow(true);
        frame2.addChild(list);
        list.setFontRenderer(KamiGUI.fontRenderer);
        frames.add(frame2);
        frame2 = new Frame(this.getTheme(), new Stretcherlayout(1), "Entities");
        final Label entityLabel = new Label("");
        frame2.setCloseable(false);
        entityLabel.addTickListener(new TickListener() {
            Minecraft mc = Wrapper.getMinecraft();
            
            @Override
            public void onTick() {
                if (this.mc.player == null || !entityLabel.isVisible()) {
                    return;
                }
                final List<Entity> entityList = new ArrayList<Entity>(this.mc.world.loadedEntityList);
                if (entityList.size() <= 1) {
                    entityLabel.setText("");
                    return;
                }
                final Map<String, Integer> entityCounts = entityList.stream().filter(Objects::nonNull).filter(e -> !(e instanceof EntityPlayer)).collect(Collectors.groupingBy(x$0 -> getEntityName(x$0), (Collector<? super Object, ?, Integer>)Collectors.reducing((D)0, ent -> {
                    if (ent instanceof EntityItem) {
                        return Integer.valueOf(ent.getItem().getCount());
                    }
                    else {
                        return Integer.valueOf(1);
                    }
                }, Integer::sum)));
                entityLabel.setText("");
                entityCounts.entrySet().stream().sorted((Comparator<? super Object>)Map.Entry.comparingByValue()).map(entry -> TextFormatting.GRAY + entry.getKey() + " " + TextFormatting.DARK_GRAY + "x" + entry.getValue()).forEach((Consumer<? super Object>)entityLabel::addLine);
            }
        });
        frame2.addChild(entityLabel);
        frame2.setPinneable(true);
        entityLabel.setShadow(true);
        entityLabel.setFontRenderer(KamiGUI.fontRenderer);
        frames.add(frame2);
        frame2 = new Frame(this.getTheme(), new Stretcherlayout(1), "Coordinates");
        frame2.setCloseable(false);
        frame2.setPinneable(true);
        final Label coordsLabel = new Label("");
        coordsLabel.addTickListener(new TickListener() {
            Minecraft mc = Minecraft.getMinecraft();
            
            @Override
            public void onTick() {
                final boolean inHell = this.mc.world.getBiome(this.mc.player.getPosition()).getBiomeName().equals("Hell");
                final int posX = (int)this.mc.player.posX;
                final int posY = (int)this.mc.player.posY;
                final int posZ = (int)this.mc.player.posZ;
                final float f = inHell ? 8.0f : 0.125f;
                final int hposX = (int)(this.mc.player.posX * f);
                final int hposZ = (int)(this.mc.player.posZ * f);
                coordsLabel.setText(String.format(" %sf%,d%s7, %sf%,d%s7, %sf%,d %s7(%sf%,d%s7, %sf%,d%s7, %sf%,d%s7)", Command.SECTIONSIGN(), posX, Command.SECTIONSIGN(), Command.SECTIONSIGN(), posY, Command.SECTIONSIGN(), Command.SECTIONSIGN(), posZ, Command.SECTIONSIGN(), Command.SECTIONSIGN(), hposX, Command.SECTIONSIGN(), Command.SECTIONSIGN(), posY, Command.SECTIONSIGN(), Command.SECTIONSIGN(), hposZ, Command.SECTIONSIGN()));
            }
        });
        frame2.addChild(coordsLabel);
        coordsLabel.setFontRenderer(KamiGUI.fontRenderer);
        coordsLabel.setShadow(true);
        frame2.setHeight(20);
        frames.add(frame2);
        frame2 = new Frame(this.getTheme(), new Stretcherlayout(1), "Radar");
        frame2.setCloseable(false);
        frame2.setMinimizeable(true);
        frame2.setPinneable(true);
        frame2.addChild((Component)new Radar());
        frame2.setWidth(100);
        frame2.setHeight(100);
        frames.add(frame2);
        for (final Frame frame3 : frames) {
            frame3.setX(x);
            frame3.setY(y);
            nexty = Math.max(y + frame3.getHeight() + 10, nexty);
            x += frame3.getWidth() + 10;
            if (x * DisplayGuiScreen.getScale() > Wrapper.getMinecraft().displayWidth / 1.2f) {
                y = (nexty = nexty);
                x = 10;
            }
            this.addChild(frame3);
        }
    }
    
    private static String getEntityName(@Nonnull final Entity entity) {
        if (entity instanceof EntityItem) {
            return TextFormatting.DARK_AQUA + ((EntityItem)entity).getItem().getItem().getItemStackDisplayName(((EntityItem)entity).getItem());
        }
        if (entity instanceof EntityWitherSkull) {
            return TextFormatting.DARK_GRAY + "Wither skull";
        }
        if (entity instanceof EntityEnderCrystal) {
            return TextFormatting.LIGHT_PURPLE + "End crystal";
        }
        if (entity instanceof EntityEnderPearl) {
            return "Thrown ender pearl";
        }
        if (entity instanceof EntityMinecart) {
            return "Minecart";
        }
        if (entity instanceof EntityItemFrame) {
            return "Item frame";
        }
        if (entity instanceof EntityEgg) {
            return "Thrown egg";
        }
        if (entity instanceof EntitySnowball) {
            return "Thrown snowball";
        }
        return entity.getName();
    }
    
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(final Map<K, V> map) {
        final List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(map.entrySet());
        Collections.sort(list, Comparator.comparing(o -> o.getValue()));
        final Map<K, V> result = new LinkedHashMap<K, V>();
        for (final Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
    
    @Override
    public void destroyGUI() {
        this.kill();
    }
    
    public static void dock(final Frame component) {
        final Docking docking = component.getDocking();
        if (docking.isTop()) {
            component.setY(0);
        }
        if (docking.isBottom()) {
            component.setY(Wrapper.getMinecraft().displayHeight / DisplayGuiScreen.getScale() - component.getHeight() - 0);
        }
        if (docking.isLeft()) {
            component.setX(0);
        }
        if (docking.isRight()) {
            component.setX(Wrapper.getMinecraft().displayWidth / DisplayGuiScreen.getScale() - component.getWidth() - 0);
        }
        if (docking.isCenterHorizontal()) {
            component.setX(Wrapper.getMinecraft().displayWidth / (DisplayGuiScreen.getScale() * 2) - component.getWidth() / 2);
        }
        if (docking.isCenterVertical()) {
            component.setY(Wrapper.getMinecraft().displayHeight / (DisplayGuiScreen.getScale() * 2) - component.getHeight() / 2);
        }
    }
    
    static {
        fontRenderer = new RootFontRenderer(1.0f);
        KamiGUI.primaryColour = new ColourHolder(29, 29, 29);
    }
}
