//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.command.syntax.parsers;

import me.zeroeightsix.kami.command.syntax.*;
import me.zeroeightsix.kami.module.*;

public class ModuleParser extends AbstractParser
{
    public String getChunk(final SyntaxChunk[] chunks, final SyntaxChunk thisChunk, final String[] values, final String chunkValue) {
        if (chunkValue == null) {
            return this.getDefaultChunk(thisChunk);
        }
        final Module chosen = ModuleManager.getModules().stream().filter(module -> module.getName().toLowerCase().startsWith(chunkValue.toLowerCase())).findFirst().orElse(null);
        if (chosen == null) {
            return null;
        }
        return chosen.getName().substring(chunkValue.length());
    }
}
