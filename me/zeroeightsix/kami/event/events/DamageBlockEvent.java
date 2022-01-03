//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.event.events;

import me.zeroeightsix.kami.event.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;

public class DamageBlockEvent extends KamiEvent
{
    private BlockPos BlockPos;
    private EnumFacing Direction;
    
    public DamageBlockEvent(final BlockPos posBlock, final EnumFacing directionFacing) {
        this.BlockPos = posBlock;
        this.setDirection(directionFacing);
    }
    
    public BlockPos getPos() {
        return this.BlockPos;
    }
    
    public EnumFacing getDirection() {
        return this.Direction;
    }
    
    public void setDirection(final EnumFacing direction) {
        this.Direction = direction;
    }
}
