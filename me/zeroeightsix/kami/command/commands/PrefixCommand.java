//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.command.commands;

import me.zeroeightsix.kami.command.*;
import me.zeroeightsix.kami.command.syntax.*;

public class PrefixCommand extends Command
{
    public PrefixCommand() {
        super("prefix", new ChunkBuilder().append("character").build());
    }
    
    public void call(final String[] args) {
        if (args.length == 0) {
            Command.sendChatMessage("Please specify a new prefix!");
            return;
        }
        Command.commandPrefix.setValue(args[0]);
        Command.sendChatMessage("Prefix set to &b" + Command.commandPrefix.getValue());
    }
}
