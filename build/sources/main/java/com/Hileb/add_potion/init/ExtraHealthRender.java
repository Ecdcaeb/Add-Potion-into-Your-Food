package com.Hileb.add_potion.init;

import com.Hileb.add_potion.IdlFramework;
import com.Hileb.add_potion.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

//@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
//public class ExtraHealthRender {
//    @SideOnly(Side.CLIENT)
//    @SubscribeEvent
//    public static void onRender(RenderGameOverlayEvent.Post event){
//        Minecraft mc=Minecraft.getMinecraft();
//        ScaledResolution res = new ScaledResolution(mc);
//        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
//        renderFood(res.getScaledWidth(),res.getScaledHeight(),6);
//    }
//    private static void renderFood(int width, int height,int amount)
//    {
//        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(IdlFramework.MODID,"textures/icons.png"));
//        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
//        GlStateManager.disableDepth();
//        GlStateManager.enableTexture2D();
//        GlStateManager.enableBlend();
//        GlStateManager.enableLighting();
//        GlStateManager.disableAlpha();
//
//        int level = amount;
//
//        for (int i = 0; i < 10; ++i)
//        {
//            if(level>0){
//                drawTexturedModalRect(i*40, 0, 0, 0, 40, 40);
//            }
//            level--;
//        }
//        GlStateManager.disableLighting();
//        GlStateManager.enableAlpha();
//    }
//    private static void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height)
//    {
//        float f = 0.00390625F;
//        //float f1 = 0.00390625F;
//        Tessellator tessellator = Tessellator.getInstance();
//        BufferBuilder bufferbuilder = tessellator.getBuffer();
//        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
//        bufferbuilder.pos((double)(x ), (double)(y + height), (double)-90F).tex((double)((float)(textureX ) * f), (double)((float)(textureY + height) * f)).endVertex();
//        bufferbuilder.pos((double)(x + width), (double)(y + height), (double)-90F).tex((double)((float)(textureX + width) * f), (double)((float)(textureY + height) *f)).endVertex();
//        bufferbuilder.pos((double)(x + width), (double)(y ), (double)-90F).tex((double)((float)(textureX + width) * f), (double)((float)(textureY ) * f)).endVertex();
//        bufferbuilder.pos((double)(x ), (double)(y ), (double)-90F).tex((double)((float)(textureX ) * f), (double)((float)(textureY ) * f)).endVertex();
//        tessellator.draw();
//    }
//}
