//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.mixin.client;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import me.zeroeightsix.kami.module.*;
import net.minecraft.item.*;
import net.minecraft.inventory.*;
import me.zeroeightsix.kami.module.modules.render.*;
import net.minecraft.client.gui.*;
import me.zeroeightsix.kami.util.*;
import net.minecraft.util.*;
import net.minecraft.nbt.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;

@Mixin({ GuiScreen.class })
public class MixinGuiScreen
{
    RenderItem itemRender;
    FontRenderer fontRenderer;
    
    public MixinGuiScreen() {
        this.itemRender = Minecraft.getMinecraft().getRenderItem();
        this.fontRenderer = Minecraft.getMinecraft().fontRenderer;
    }
    
    @Inject(method = { "renderToolTip" }, at = { @At("HEAD") }, cancellable = true)
    public void renderToolTip(final ItemStack stack, final int x, final int y, final CallbackInfo info) {
        if (ModuleManager.isModuleEnabled("ShulkerPreview") && stack.getItem() instanceof ItemShulkerBox) {
            final NBTTagCompound tagCompound = stack.getTagCompound();
            if (tagCompound != null && tagCompound.hasKey("BlockEntityTag", 10)) {
                final NBTTagCompound blockEntityTag = tagCompound.getCompoundTag("BlockEntityTag");
                if (blockEntityTag.hasKey("Items", 9)) {
                    info.cancel();
                    final NonNullList<ItemStack> nonnulllist = (NonNullList<ItemStack>)NonNullList.withSize(27, (Object)ItemStack.EMPTY);
                    ItemStackHelper.loadAllItems(blockEntityTag, (NonNullList)nonnulllist);
                    final ShulkerPreview p = (ShulkerPreview)ModuleManager.getModuleByName("ShulkerPreview");
                    if (!p.getRect()) {
                        GlStateManager.enableBlend();
                        GlStateManager.disableRescaleNormal();
                        RenderHelper.disableStandardItemLighting();
                        GlStateManager.disableLighting();
                        GlStateManager.disableDepth();
                        final int width = Math.max(144, this.fontRenderer.getStringWidth(stack.getDisplayName()) + 3);
                        final int x2 = x + 12;
                        final int y2 = y - 12;
                        final int height = 57;
                        this.itemRender.zLevel = 300.0f;
                        this.drawGradientRectP(x2 - 3, y2 - 4, x2 + width + 3, y2 - 3, -267386864, -267386864);
                        this.drawGradientRectP(x2 - 3, y2 + height + 3, x2 + width + 3, y2 + height + 4, -267386864, -267386864);
                        this.drawGradientRectP(x2 - 3, y2 - 3, x2 + width + 3, y2 + height + 3, -267386864, -267386864);
                        this.drawGradientRectP(x2 - 4, y2 - 3, x2 - 3, y2 + height + 3, -267386864, -267386864);
                        this.drawGradientRectP(x2 + width + 3, y2 - 3, x2 + width + 4, y2 + height + 3, -267386864, -267386864);
                        this.drawGradientRectP(x2 - 3, y2 - 3 + 1, x2 - 3 + 1, y2 + height + 3 - 1, 1347420415, 1344798847);
                        this.drawGradientRectP(x2 + width + 2, y2 - 3 + 1, x2 + width + 3, y2 + height + 3 - 1, 1347420415, 1344798847);
                        this.drawGradientRectP(x2 - 3, y2 - 3, x2 + width + 3, y2 - 3 + 1, 1347420415, 1347420415);
                        this.drawGradientRectP(x2 - 3, y2 + height + 2, x2 + width + 3, y2 + height + 3, 1344798847, 1344798847);
                        this.fontRenderer.drawString(stack.getDisplayName(), x + 12, y - 12, 16777215);
                        GlStateManager.enableBlend();
                        GlStateManager.enableAlpha();
                        GlStateManager.enableTexture2D();
                        GlStateManager.enableLighting();
                        GlStateManager.enableDepth();
                        RenderHelper.enableGUIStandardItemLighting();
                        for (int i = 0; i < nonnulllist.size(); ++i) {
                            final int iX = x + i % 9 * 16 + 11;
                            final int iY = y + i / 9 * 16 - 11 + 8;
                            final ItemStack itemStack = (ItemStack)nonnulllist.get(i);
                            this.itemRender.renderItemAndEffectIntoGUI(itemStack, iX, iY);
                            this.itemRender.renderItemOverlayIntoGUI(this.fontRenderer, itemStack, iX, iY, (String)null);
                        }
                        RenderHelper.disableStandardItemLighting();
                        this.itemRender.zLevel = 0.0f;
                        GlStateManager.enableLighting();
                        GlStateManager.enableDepth();
                        RenderHelper.enableStandardItemLighting();
                        GlStateManager.enableRescaleNormal();
                    }
                    else {
                        GlStateManager.enableBlend();
                        GlStateManager.disableRescaleNormal();
                        RenderHelper.disableStandardItemLighting();
                        GlStateManager.disableLighting();
                        GlStateManager.disableDepth();
                        this.itemRender.zLevel = 0.0f;
                        final int oldX = x;
                        final int oldY = y;
                        Gui.drawRect(oldX + 9, oldY - 14, oldX + 173, oldY + 52, -1441722095);
                        Wrapper.getMinecraft().renderEngine.bindTexture(new ResourceLocation("textures/gui/container/shulker_box.png"));
                        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                        Wrapper.getMinecraft().ingameGUI.drawTexturedModalRect(oldX + 10, oldY - 4, 7, 17, 162, 54);
                        Wrapper.getMinecraft().fontRenderer.drawString(stack.getDisplayName(), oldX + 12, oldY - 12, 16777215);
                        GlStateManager.enableBlend();
                        GlStateManager.enableTexture2D();
                        GlStateManager.enableLighting();
                        GlStateManager.enableDepth();
                        RenderHelper.enableGUIStandardItemLighting();
                        for (int j = 0; j < nonnulllist.size(); ++j) {
                            final int iX2 = oldX + j % 9 * 18 + 11;
                            final int iY2 = oldY + j / 9 * 18 - 11 + 8;
                            final ItemStack itemStack2 = (ItemStack)nonnulllist.get(j);
                            Wrapper.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI(itemStack2, iX2, iY2);
                            Wrapper.getMinecraft().getRenderItem().renderItemOverlayIntoGUI(Wrapper.getMinecraft().fontRenderer, itemStack2, iX2, iY2, (String)null);
                        }
                        RenderHelper.disableStandardItemLighting();
                        Wrapper.getMinecraft().getRenderItem().zLevel = 0.0f;
                        GlStateManager.enableLighting();
                        GlStateManager.enableDepth();
                        RenderHelper.enableStandardItemLighting();
                        GlStateManager.enableRescaleNormal();
                    }
                }
            }
        }
    }
    
    private void drawGradientRectP(final int left, final int top, final int right, final int bottom, final int startColor, final int endColor) {
        final float f = (startColor >> 24 & 0xFF) / 255.0f;
        final float f2 = (startColor >> 16 & 0xFF) / 255.0f;
        final float f3 = (startColor >> 8 & 0xFF) / 255.0f;
        final float f4 = (startColor & 0xFF) / 255.0f;
        final float f5 = (endColor >> 24 & 0xFF) / 255.0f;
        final float f6 = (endColor >> 16 & 0xFF) / 255.0f;
        final float f7 = (endColor >> 8 & 0xFF) / 255.0f;
        final float f8 = (endColor & 0xFF) / 255.0f;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7425);
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos((double)right, (double)top, 300.0).color(f2, f3, f4, f).endVertex();
        bufferbuilder.pos((double)left, (double)top, 300.0).color(f2, f3, f4, f).endVertex();
        bufferbuilder.pos((double)left, (double)bottom, 300.0).color(f6, f7, f8, f5).endVertex();
        bufferbuilder.pos((double)right, (double)bottom, 300.0).color(f6, f7, f8, f5).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }
}
