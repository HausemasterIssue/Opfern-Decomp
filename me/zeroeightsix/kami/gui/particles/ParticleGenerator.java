//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.gui.particles;

import net.minecraft.client.*;
import me.zeroeightsix.kami.util.*;
import java.util.*;

public class ParticleGenerator
{
    public List<Particle> particles;
    public int amount;
    private int prevWidth;
    private int prevHeight;
    
    public ParticleGenerator(final int amount) {
        this.particles = new ArrayList<Particle>();
        this.amount = amount;
    }
    
    public void draw(final int mouseX, final int mouseY) {
        if (this.particles.isEmpty() || this.prevWidth != Minecraft.getMinecraft().displayWidth || this.prevHeight != Minecraft.getMinecraft().displayHeight) {
            this.particles.clear();
            this.create();
        }
        this.prevWidth = Minecraft.getMinecraft().displayWidth;
        this.prevHeight = Minecraft.getMinecraft().displayHeight;
        for (final Particle particle : this.particles) {
            particle.fall();
            particle.interpolation();
            final int range = 50;
            final boolean mouseOver = mouseX >= particle.x - range && mouseY >= particle.y - range && mouseX <= particle.x + range && mouseY <= particle.y + range;
            if (mouseOver) {
                final Particle particle2;
                final int n;
                this.particles.stream().filter(part -> part.getX() > particle2.getX() && part.getX() - particle2.getX() < n && particle2.getX() - part.getX() < n && ((part.getY() > particle2.getY() && part.getY() - particle2.getY() < n) || (particle2.getY() > part.getY() && particle2.getY() - part.getY() < n))).forEach(connectable -> particle.connect(connectable.getX(), connectable.getY()));
            }
            RenderUtils.drawCircle(particle.getX(), particle.getY(), particle.size, -1);
        }
    }
    
    public void create() {
        final Random random = new Random();
        for (int i = 0; i < this.amount; ++i) {
            this.particles.add(new Particle(random.nextInt(Minecraft.getMinecraft().displayWidth), random.nextInt(Minecraft.getMinecraft().displayHeight)));
        }
    }
}
