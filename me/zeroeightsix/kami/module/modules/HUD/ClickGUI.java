//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.module.modules.HUD;

import me.zeroeightsix.kami.module.*;
import me.zeroeightsix.kami.setting.*;

@Module.Info(name = "GUI", category = Module.Category.HUD)
public class ClickGUI extends Module
{
    private Setting<Float> bRed;
    private Setting<Float> bGreen;
    private Setting<Float> bBlue;
    private Setting<Float> bAlpha;
    private Setting<Float> oRed;
    private Setting<Float> oGreen;
    private Setting<Float> oBlue;
    private Setting<Float> sbRed;
    private Setting<Float> sbGreen;
    private Setting<Float> sbBlue;
    private Setting<Float> sbAlpha;
    private Setting<Float> soRed;
    private Setting<Float> soGreen;
    private Setting<Float> soBlue;
    private Setting<Float> btRed;
    private Setting<Float> btGreen;
    private Setting<Float> btBlue;
    private Setting<ButtonMode> buttonMode;
    private Setting<Boolean> blur;
    private Setting<Boolean> particles;
    
    public ClickGUI() {
        this.bRed = (Setting<Float>)this.register((Setting)Settings.floatBuilder("BGRed").withMinimum(0.0f).withValue(0.15f).withMaximum(1.0f).build());
        this.bGreen = (Setting<Float>)this.register((Setting)Settings.floatBuilder("BGGreen").withMinimum(0.0f).withValue(0.15f).withMaximum(1.0f).build());
        this.bBlue = (Setting<Float>)this.register((Setting)Settings.floatBuilder("BGBlue").withMinimum(0.0f).withValue(0.15f).withMaximum(1.0f).build());
        this.bAlpha = (Setting<Float>)this.register((Setting)Settings.floatBuilder("BGAlpha").withMinimum(0.0f).withValue(0.95f).withMaximum(1.0f).build());
        this.oRed = (Setting<Float>)this.register((Setting)Settings.floatBuilder("OutlineRed").withMinimum(0.0f).withValue(0.0f).withMaximum(1.0f).build());
        this.oGreen = (Setting<Float>)this.register((Setting)Settings.floatBuilder("OutlineGreen").withMinimum(0.0f).withValue(0.0f).withMaximum(1.0f).build());
        this.oBlue = (Setting<Float>)this.register((Setting)Settings.floatBuilder("OutlineBlue").withMinimum(0.0f).withValue(0.0f).withMaximum(1.0f).build());
        this.sbRed = (Setting<Float>)this.register((Setting)Settings.floatBuilder("SetBGRed").withMinimum(0.0f).withValue(0.15f).withMaximum(1.0f).build());
        this.sbGreen = (Setting<Float>)this.register((Setting)Settings.floatBuilder("SetBGGreen").withMinimum(0.0f).withValue(0.15f).withMaximum(1.0f).build());
        this.sbBlue = (Setting<Float>)this.register((Setting)Settings.floatBuilder("SetBGBlue").withMinimum(0.0f).withValue(0.15f).withMaximum(1.0f).build());
        this.sbAlpha = (Setting<Float>)this.register((Setting)Settings.floatBuilder("SetBGAlpha").withMinimum(0.0f).withValue(0.95f).withMaximum(1.0f).build());
        this.soRed = (Setting<Float>)this.register((Setting)Settings.floatBuilder("SetOutlineRed").withMinimum(0.0f).withValue(0.0f).withMaximum(1.0f).build());
        this.soGreen = (Setting<Float>)this.register((Setting)Settings.floatBuilder("SetOutlineGreen").withMinimum(0.0f).withValue(0.0f).withMaximum(1.0f).build());
        this.soBlue = (Setting<Float>)this.register((Setting)Settings.floatBuilder("SetOutlineBlue").withMinimum(0.0f).withValue(0.0f).withMaximum(1.0f).build());
        this.btRed = (Setting<Float>)this.register((Setting)Settings.floatBuilder("ButtonRed").withMinimum(0.0f).withValue(0.64f).withMaximum(1.0f).build());
        this.btGreen = (Setting<Float>)this.register((Setting)Settings.floatBuilder("ButtonGreen").withMinimum(0.0f).withValue(0.0f).withMaximum(1.0f).build());
        this.btBlue = (Setting<Float>)this.register((Setting)Settings.floatBuilder("ButtonBlue").withMinimum(0.0f).withValue(0.0f).withMaximum(1.0f).build());
        this.buttonMode = (Setting<ButtonMode>)this.register((Setting)Settings.e("ButtonMode", ButtonMode.HIGHLIGHT));
        this.blur = (Setting<Boolean>)this.register((Setting)Settings.b("Blur", false));
        this.particles = (Setting<Boolean>)this.register((Setting)Settings.b("Particles", false));
    }
    
    protected void onEnable() {
        this.disable();
    }
    
    public boolean getBlur() {
        return this.blur.getValue();
    }
    
    public boolean getParticles() {
        return this.particles.getValue();
    }
    
    public Float getBRed() {
        return this.bRed.getValue();
    }
    
    public Float getBGreen() {
        return this.bGreen.getValue();
    }
    
    public Float getBBlue() {
        return this.bBlue.getValue();
    }
    
    public Float getBAlpha() {
        return this.bAlpha.getValue();
    }
    
    public Float getORed() {
        return this.oRed.getValue();
    }
    
    public Float getOGreen() {
        return this.oGreen.getValue();
    }
    
    public Float getOBlue() {
        return this.oBlue.getValue();
    }
    
    public Float getSBRed() {
        return this.sbRed.getValue();
    }
    
    public Float getSBGreen() {
        return this.sbGreen.getValue();
    }
    
    public Float getSBBlue() {
        return this.sbBlue.getValue();
    }
    
    public Float getSBAlpha() {
        return this.sbAlpha.getValue();
    }
    
    public Float getSORed() {
        return this.soRed.getValue();
    }
    
    public Float getSOGreen() {
        return this.soGreen.getValue();
    }
    
    public Float getSOBlue() {
        return this.soBlue.getValue();
    }
    
    public Float getBTRed() {
        return this.btRed.getValue();
    }
    
    public Float getBTGreen() {
        return this.btGreen.getValue();
    }
    
    public Float getBTBlue() {
        return this.btBlue.getValue();
    }
    
    public Enum getBTMode() {
        return this.buttonMode.getValue();
    }
    
    public enum ButtonMode
    {
        FONT, 
        HIGHLIGHT;
    }
}
