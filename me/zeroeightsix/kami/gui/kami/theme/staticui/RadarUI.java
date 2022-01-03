//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.gui.kami.theme.staticui;

import me.zeroeightsix.kami.gui.rgui.render.*;
import me.zeroeightsix.kami.gui.kami.component.*;
import me.zeroeightsix.kami.gui.rgui.render.font.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import me.zeroeightsix.kami.gui.kami.*;
import net.minecraft.entity.*;
import me.zeroeightsix.kami.util.*;
import java.util.*;
import me.zeroeightsix.kami.gui.rgui.component.*;

public class RadarUI extends AbstractComponentUI<Radar>
{
    float scale;
    public static final int radius = 45;
    
    public RadarUI() {
        this.scale = 2.0f;
    }
    
    @Override
    public void handleSizeComponent(final Radar component) {
        component.setWidth(90);
        component.setHeight(90);
    }
    
    @Override
    public void renderComponent(final Radar component, final FontRenderer fontRenderer) {
        this.scale = 2.0f;
        GL11.glTranslated((double)(component.getWidth() / 2), (double)(component.getHeight() / 2), 0.0);
        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.enableBlend();
        GlStateManager.disableCull();
        GlStateManager.pushMatrix();
        GL11.glColor4f(0.11f, 0.11f, 0.11f, 0.6f);
        RenderHelper.drawCircle(0.0f, 0.0f, 45.0f);
        GL11.glRotatef(Wrapper.getPlayer().rotationYaw + 180.0f, 0.0f, 0.0f, -1.0f);
        for (final Entity e : Wrapper.getWorld().loadedEntityList) {
            if (!(e instanceof EntityLiving)) {
                continue;
            }
            float red = 1.0f;
            float green = 1.0f;
            if (EntityUtil.isPassive(e)) {
                red = 0.0f;
            }
            else {
                green = 0.0f;
            }
            final double dX = e.posX - Wrapper.getPlayer().posX;
            final double dZ = e.posZ - Wrapper.getPlayer().posZ;
            final double distance = Math.sqrt(Math.pow(dX, 2.0) + Math.pow(dZ, 2.0));
            if (distance > 45.0f * this.scale) {
                continue;
            }
            if (Math.abs(Wrapper.getPlayer().posY - e.posY) > 30.0) {
                continue;
            }
            GL11.glColor4f(red, green, 0.0f, 0.5f);
            RenderHelper.drawCircle((int)dX / this.scale, (int)dZ / this.scale, 2.5f / this.scale);
        }
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
        RenderHelper.drawCircle(0.0f, 0.0f, 3.0f / this.scale);
        GL11.glLineWidth(1.8f);
        GL11.glColor3f(0.05f, 0.59f, 0.1f);
        GL11.glEnable(2848);
        RenderHelper.drawCircleOutline(0.0f, 0.0f, 45.0f);
        GL11.glDisable(2848);
        component.getTheme().getFontRenderer().drawString(-component.getTheme().getFontRenderer().getStringWidth("+z") / 2, 45 - component.getTheme().getFontRenderer().getFontHeight(), "�7z+");
        GL11.glRotatef(90.0f, 0.0f, 0.0f, 1.0f);
        component.getTheme().getFontRenderer().drawString(-component.getTheme().getFontRenderer().getStringWidth("+x") / 2, 45 - component.getTheme().getFontRenderer().getFontHeight(), "�7x-");
        GL11.glRotatef(90.0f, 0.0f, 0.0f, 1.0f);
        component.getTheme().getFontRenderer().drawString(-component.getTheme().getFontRenderer().getStringWidth("-z") / 2, 45 - component.getTheme().getFontRenderer().getFontHeight(), "�7z-");
        GL11.glRotatef(90.0f, 0.0f, 0.0f, 1.0f);
        component.getTheme().getFontRenderer().drawString(-component.getTheme().getFontRenderer().getStringWidth("+x") / 2, 45 - component.getTheme().getFontRenderer().getFontHeight(), "�7x+");
        GlStateManager.popMatrix();
        GlStateManager.enableTexture2D();
        GL11.glTranslated((double)(-component.getWidth() / 2), (double)(-component.getHeight() / 2), 0.0);
    }
}
