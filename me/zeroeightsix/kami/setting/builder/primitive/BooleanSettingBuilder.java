//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.setting.builder.primitive;

import me.zeroeightsix.kami.setting.builder.*;
import me.zeroeightsix.kami.setting.impl.*;
import me.zeroeightsix.kami.setting.*;

public class BooleanSettingBuilder extends SettingBuilder<Boolean>
{
    @Override
    public BooleanSetting build() {
        return new BooleanSetting((Boolean)this.initialValue, this.predicate(), this.consumer(), this.name, this.visibilityPredicate());
    }
    
    @Override
    public BooleanSettingBuilder withName(final String name) {
        return (BooleanSettingBuilder)super.withName(name);
    }
}
