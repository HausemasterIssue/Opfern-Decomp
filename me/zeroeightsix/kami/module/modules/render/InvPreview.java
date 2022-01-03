//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.module.modules.render;

import me.zeroeightsix.kami.module.*;
import me.zeroeightsix.kami.setting.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import net.minecraft.item.*;

@Module.Info(name = "InvPreview", category = Module.Category.RENDER, description = "View Inventory")
public class InvPreview extends Module
{
    private Setting<Integer> optionX;
    private Setting<Integer> optionY;
    private Setting<Boolean> renderz;
    private static final ResourceLocation box;
    
    public InvPreview() {
        this.optionX = (Setting<Integer>)this.register((Setting)Settings.i("X", 198));
        this.optionY = (Setting<Integer>)this.register((Setting)Settings.i("Y", 469));
        this.renderz = (Setting<Boolean>)this.register((Setting)Settings.b("RenderRect", true));
    }
    
    private static void preboxrender() {
        GL11.glPushMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.disableAlpha();
        GlStateManager.clear(256);
        GlStateManager.enableBlend();
    }
    
    private static void postboxrender() {
        GlStateManager.disableBlend();
        GlStateManager.disableDepth();
        GlStateManager.disableLighting();
        GlStateManager.enableDepth();
        GlStateManager.enableAlpha();
        GlStateManager.popMatrix();
        GL11.glPopMatrix();
    }
    
    private static void preitemrender() {
        GL11.glPushMatrix();
        GL11.glDepthMask(true);
        GlStateManager.clear(256);
        GlStateManager.disableDepth();
        GlStateManager.enableDepth();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.scale(1.0f, 1.0f, 0.01f);
    }
    
    private static void postitemrender() {
        GlStateManager.scale(1.0f, 1.0f, 1.0f);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.disableLighting();
        GlStateManager.scale(0.5, 0.5, 0.5);
        GlStateManager.disableDepth();
        GlStateManager.enableDepth();
        GlStateManager.scale(2.0f, 2.0f, 2.0f);
        GL11.glPopMatrix();
    }
    
    public void onRender() {
        final NonNullList<ItemStack> items = (NonNullList<ItemStack>)InvPreview.mc.player.inventory.mainInventory;
        if (this.renderz.getValue()) {
            this.boxrender(this.optionX.getValue(), this.optionY.getValue());
        }
        this.itemrender(items, this.optionX.getValue(), this.optionY.getValue());
    }
    
    private void boxrender(final int x, final int y) {
        preboxrender();
        InvPreview.mc.renderEngine.bindTexture(InvPreview.box);
        InvPreview.mc.ingameGUI.drawTexturedModalRect(x, y, 7, 17, 162, 54);
        postboxrender();
    }
    
    private void itemrender(final NonNullList<ItemStack> items, final int x, final int y) {
        for (int size = items.size(), item = 9; item < size; ++item) {
            final int slotx = x + 1 + item % 9 * 18;
            final int sloty = y + 1 + (item / 9 - 1) * 18;
            preitemrender();
            InvPreview.mc.getRenderItem().renderItemAndEffectIntoGUI((ItemStack)items.get(item), slotx, sloty);
            InvPreview.mc.getRenderItem().renderItemOverlays(InvPreview.mc.fontRenderer, (ItemStack)items.get(item), slotx, sloty);
            postitemrender();
        }
    }
    
    static {
        box = new ResourceLocation("textures/gui/container/generic_54.png");
    }
}
