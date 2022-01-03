//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.setting.impl;

import me.zeroeightsix.kami.setting.*;
import me.zeroeightsix.kami.setting.converter.*;
import java.util.function.*;
import com.google.common.base.*;

public class StringSetting extends Setting<String>
{
    private static final StringConverter converter;
    
    public StringSetting(final String value, final Predicate<String> restriction, final BiConsumer<String, String> consumer, final String name, final Predicate<String> visibilityPredicate) {
        super(value, restriction, consumer, name, visibilityPredicate);
    }
    
    public StringConverter converter() {
        return StringSetting.converter;
    }
    
    static {
        converter = new StringConverter();
    }
}
