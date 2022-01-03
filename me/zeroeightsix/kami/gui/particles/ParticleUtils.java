//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.gui.particles;

public final class ParticleUtils
{
    private static ParticleGenerator particleGenerator;
    
    public static void drawParticles(final int mouseX, final int mouseY) {
        ParticleUtils.particleGenerator.draw(mouseX, mouseY);
    }
    
    public static void setAmount(final int amount) {
        ParticleUtils.particleGenerator.particles = null;
        ParticleUtils.particleGenerator.amount = amount;
        ParticleUtils.particleGenerator.create();
    }
    
    static {
        ParticleUtils.particleGenerator = new ParticleGenerator(100);
    }
}
