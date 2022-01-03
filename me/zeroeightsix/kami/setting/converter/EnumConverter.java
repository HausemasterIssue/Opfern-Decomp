//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.setting.converter;

import com.google.common.base.*;
import com.google.gson.*;

public class EnumConverter extends Converter<Enum, JsonElement>
{
    Class<? extends Enum> clazz;
    
    public EnumConverter(final Class<? extends Enum> clazz) {
        this.clazz = clazz;
    }
    
    protected JsonElement doForward(final Enum anEnum) {
        return (JsonElement)new JsonPrimitive(anEnum.toString());
    }
    
    protected Enum doBackward(final JsonElement jsonElement) {
        return (Enum)Enum.valueOf(this.clazz, jsonElement.getAsString());
    }
}
