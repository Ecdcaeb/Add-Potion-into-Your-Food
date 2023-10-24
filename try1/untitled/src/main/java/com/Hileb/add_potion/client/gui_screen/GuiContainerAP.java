package com.Hileb.add_potion.client.gui_screen;

import com.Hileb.add_potion.AddPotionMain;
import com.Hileb.add_potion.common.container.ContainerAP;
import com.Hileb.add_potion.common.event.APTooltipEvent;
import com.Hileb.add_potion.common.init.ModConfig;
import com.Hileb.add_potion.common.potion.APotion;
import com.Hileb.add_potion.common.potion.ApplyUtil;
import com.Hileb.add_potion.common.potion.PotionUtil;
import com.Hileb.add_potion.network.ButtonDownEvent;
import com.Hileb.add_potion.network.ButtonDownMessage;
import com.Hileb.add_potion.network.NetWorkHandler;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class GuiContainerAP extends AbstractContainerScreen<ContainerAP> {
    public final ContainerAP containerAP;

    public final ImageButton craft_button;
    private static final ResourceLocation TEXTURE = new ResourceLocation(AddPotionMain.MODID,"textures/gui/gui_potion.png");
    public GuiContainerAP(ContainerAP containerAPIn, Inventory inventory, Component component) {
        super(containerAPIn,inventory,component);
        this.imageWidth = 176;
        this.imageHeight= 133;

        containerAP=containerAPIn;
        craft_button=new ImageButton(this.leftPos +  36, this.topPos +8, 23, 13, 1, 134, 19, TEXTURE, (button) -> {
            NetWorkHandler.INSTANCE.sendToServer(new ButtonDownMessage());
            AddPotionMain.LOGGER.warn("send pack at"+System.nanoTime());

        }){
            @Override
            public void render(PoseStack pose, int d1,int d2,float d3) {
                RenderSystem.setShaderTexture(0,TEXTURE);
                GuiComponent.blit(pose,leftPos +  36,topPos +8,1, 134,23,13);
            }

        };

        this.addRenderableWidget(craft_button);
    }

    @Override
    protected void renderBg(PoseStack poseStack, float particularTick, int mouseX, int mouseY) {
        RenderSystem.setShaderTexture(0,TEXTURE);
        GuiComponent.blit(poseStack,this.leftPos,this.topPos,0,0,this.imageWidth,this.imageHeight);
    }

    @Override
    public void render(PoseStack poseStack,int mouseX, int mouseY, float particularTick) {
        renderBackground(poseStack);
        super.render(poseStack,mouseX,mouseY,particularTick);
        this.renderTooltip(poseStack,mouseX,mouseY);
        craft_button.setPosition(this.leftPos+36,this.topPos+8);
        if (craft_button.isMouseOver(mouseX,mouseY)){
            renderComponentTooltip(poseStack,getTooltip(),mouseX,mouseY);
        }
    }


    public  List<Component> getTooltip(){
        List<Component> components=new ArrayList<>();
        components.add(Component.translatable("com.hileb.ap.botton"));
        if (ModConfig.INSTANCE.showDesc.get() && !containerAP.foodSlot.getItem().isEmpty()){
            if (containerAP.foodSlot.getItem().getTag()!=null) {
                components.add(Component.literal( " "));
                components.add(Component.translatable("com.hileb.ap.desc_1"));
                addPotionTooltip(containerAP.foodSlot.getItem().copy(), components);
            }
        }
        if (!containerAP.potionSlot.getItem().isEmpty()){
            components.add(Component.literal( " "));
            components.add(Component.translatable("com.hileb.ap.desc_2"));
            addPotionTooltip(containerAP.potionSlot.getItem().copy(), components);

        }

        return components;

    }
    public static void addPotionTooltip(ItemStack stackIn, List<Component>  lores) {
        if (ModConfig.INSTANCE.showDesc.get()){
            List<APotion> potions=PotionUtil.getAllEffect(stackIn.copy());
            getPotionTooltip(PotionUtil.getAllEffect(stackIn),lores);
            MinecraftForge.EVENT_BUS.post(new APTooltipEvent(lores,stackIn));
        }
    }
    public static void getPotionTooltip(List<APotion> aPotions, List<Component> components){
        PotionUtils.addPotionTooltip(PotionUtil.getListOfPotionEffect(aPotions),components,1.0F);
    }
}
