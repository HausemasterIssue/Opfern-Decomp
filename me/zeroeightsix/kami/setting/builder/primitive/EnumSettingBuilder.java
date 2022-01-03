//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.setting.builder.primitive;

import me.zeroeightsix.kami.setting.builder.*;
import me.zeroeightsix.kami.setting.*;
import me.zeroeightsix.kami.setting.impl.*;

public class EnumSettingBuilder<T extends Enum> extends SettingBuilder<T>
{
    Class<? extends Enum> clazz;
    
    public EnumSettingBuilder(final Class<? extends Enum> clazz) {
        this.clazz = clazz;
    }
    
    @Override
    public Setting<T> build() {
        return new EnumSetting<T>(this.initialValue, this.predicate(), this.consumer(), this.name, this.visibilityPredicate(), this.clazz);
    }
}
