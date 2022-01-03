//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.module.modules.misc;

import me.zeroeightsix.kami.module.*;
import me.zeroeightsix.kami.setting.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import me.zeroeightsix.kami.util.*;
import com.mojang.realmsclient.gui.*;
import net.minecraft.init.*;
import java.util.*;
import me.zeroeightsix.kami.command.*;

@Module.Info(name = "VisualRange", description = "Reports Players in VisualRange", category = Module.Category.MISC)
public class VisualRange extends Module
{
    private Setting<Boolean> sound;
    private List<String> knownPlayers;
    
    public VisualRange() {
        this.sound = (Setting<Boolean>)this.register((Setting)Settings.b("Sound", false));
    }
    
    public void onUpdate() {
        if (VisualRange.mc.player == null) {
            return;
        }
        final List<String> tickPlayerList = new ArrayList<String>();
        for (final Entity entity : VisualRange.mc.world.getLoadedEntityList()) {
            if (entity instanceof EntityPlayer) {
                tickPlayerList.add(entity.getName());
            }
        }
        if (tickPlayerList.size() > 0) {
            for (final String playerName : tickPlayerList) {
                if (playerName.equals(VisualRange.mc.player.getName())) {
                    continue;
                }
                if (!this.knownPlayers.contains(playerName)) {
                    this.knownPlayers.add(playerName);
                    if (Friends.isFriend(playerName)) {
                        this.sendNotification(ChatFormatting.GREEN.toString() + playerName + ChatFormatting.RESET.toString() + " Has Entered Your IO!");
                    }
                    else {
                        this.sendNotification(ChatFormatting.RED.toString() + playerName + ChatFormatting.RESET.toString() + " Has Entered Your IO!");
                    }
                    if (this.sound.getValue()) {
                        VisualRange.mc.player.playSound(SoundEvents.BLOCK_NOTE_CHIME, 0.5f, 1.0f);
                    }
                    return;
                }
            }
        }
        if (this.knownPlayers.size() > 0) {
            for (final String playerName : this.knownPlayers) {
                if (!tickPlayerList.contains(playerName)) {
                    this.knownPlayers.remove(playerName);
                }
            }
        }
    }
    
    private void sendNotification(final String s) {
        Command.sendRawChatMessage(s);
    }
    
    public void onEnable() {
        this.knownPlayers = new ArrayList<String>();
    }
}
