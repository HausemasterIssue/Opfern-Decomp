//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.command.commands;

import me.zeroeightsix.kami.command.*;
import me.zeroeightsix.kami.command.syntax.*;
import me.zeroeightsix.kami.*;
import java.util.*;

public class CommandsCommand extends Command
{
    public CommandsCommand() {
        super("commands", SyntaxChunk.EMPTY);
    }
    
    public void call(final String[] args) {
        KamiMod.getInstance().getCommandManager().getCommands().stream().sorted(Comparator.comparing(command -> command.getLabel())).forEach(command -> Command.sendChatMessage("&7" + Command.getCommandPrefix() + command.getLabel() + "&r ~ &8" + command.getDescription()));
    }
}
