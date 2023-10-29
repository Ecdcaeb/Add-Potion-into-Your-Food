package com.Hileb.add_potion.client.gui_screen;

import com.Hileb.add_potion.APConfig;
import com.Hileb.add_potion.AddPotion;
import com.Hileb.add_potion.common.container.ContainerAP;
import com.Hileb.add_potion.common.events.APTooltipEvent;
import com.Hileb.add_potion.common.potion.PotionUtil;
import com.Hileb.add_potion.network.APMessage;
import com.Hileb.add_potion.network.NetWorkHandler;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.network.PacketDistributor;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class GuiContainerAP extends AbstractContainerScreen<ContainerAP> {
    public final ContainerAP containerAP;
    public final ImageButton craft_button;
    private static final ResourceLocation BUTTON_TEXTURE = new ResourceLocation(AddPotion.MODID,"textures/gui/button.png");
    public static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(AddPotion.MODID,"textures/gui/gui_potion.png");
    public GuiContainerAP(ContainerAP containerAPIn, Inventory inventory, Component component) {
        super(containerAPIn,inventory,component);
        this.imageWidth = 176;
        this.imageHeight= 133;

        containerAP=containerAPIn;
        craft_button=new ImageButton(this.leftPos+36,this.topPos+8, 24,14,
                new WidgetSprites(new ResourceLocation("social_interactions/report_button"), new ResourceLocation("social_interactions/report_button_disabled"), new ResourceLocation("social_interactions/report_button_highlighted"))
                ,pButton -> NetWorkHandler.INSTANCE.send(new APMessage(), PacketDistributor.SERVER.noArg()));
        this.addRenderableWidget(craft_button);
    }
    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        int i = this.leftPos;
        int j = (this.height - this.imageHeight) / 2;
        pGuiGraphics.blit(BACKGROUND_TEXTURE, i, j, 0, 0, this.imageWidth, this.imageHeight);
        craft_button.setPosition(this.leftPos+36,this.topPos+8);
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(pGuiGraphics, pMouseX, pMouseY);
        if (craft_button.isMouseOver(pMouseX,pMouseY)){
            pGuiGraphics.renderComponentTooltip(this.font,getTooltip(containerAP.potionSlot.getItem(),containerAP.foodSlot.getItem()),pMouseX,pMouseY);
        }
    }
    public static List<Component> getTooltip(ItemStack p,ItemStack f){
        List<Component> components=new ArrayList<>();
        components.add(Component.translatable("com.hileb.ap.botton"));
        if (APConfig.ap_showPotion_desc){
            if (!f.isEmpty()){
                if (f.getTag()!=null) {
                    components.add(Component.literal(" "));
                    components.add(Component.translatable("com.hileb.ap.desc_1"));

                    List<Component> fc=new ArrayList<>();
                    PotionUtils.addPotionTooltip(PotionUtil.getListOfPotionEffect(PotionUtil.getAllEffect(f)),fc,1.0F);
                    if(!MinecraftForge.EVENT_BUS.post(new APTooltipEvent(fc,f)))components.addAll(fc);
                }
            }
            if (!p.isEmpty()){
                if (p.getTag()!=null) {
                    components.add(Component.literal(" "));
                    components.add(Component.translatable("com.hileb.ap.desc_2"));

                    List<Component> pc=new ArrayList<>();
                    PotionUtils.addPotionTooltip(PotionUtil.getListOfPotionEffect(PotionUtil.getAllEffect(p)), components, 1.0F);
                    if (!MinecraftForge.EVENT_BUS.post(new APTooltipEvent(pc,p)))components.addAll(pc);
                }
            }
        }
        return components;
    }
}
