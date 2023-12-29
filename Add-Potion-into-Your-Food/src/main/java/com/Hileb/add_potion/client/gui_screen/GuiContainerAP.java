package com.Hileb.add_potion.client.gui_screen;

import com.Hileb.add_potion.APConfig;
import com.Hileb.add_potion.AddPotion;
import com.Hileb.add_potion.api.AddPotionRegistries;
import com.Hileb.add_potion.api.events.APTooltipEvent;
import com.Hileb.add_potion.common.container.ContainerAP;
import com.Hileb.add_potion.common.potion.PotionUtil;
import com.Hileb.add_potion.network.APMessage;
import com.Hileb.add_potion.network.NetWorkHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.network.PacketDistributor;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class GuiContainerAP extends AbstractContainerScreen<ContainerAP> {
    public final ImageButton craft_button;
    private static final WidgetSprites BUTTON_SPRITE=new WidgetSprites(new ResourceLocation(AddPotion.MODID,"button"),new ResourceLocation(AddPotion.MODID,"button_down"),new ResourceLocation(AddPotion.MODID,"button_highlight"));
    public static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(AddPotion.MODID,"textures/gui/gui_potion.png");
    public Inventory inventory;
    public GuiContainerAP(ContainerAP containerAPIn, Inventory inventory, Component component) {
        super(containerAPIn,inventory,component);
        this.inventory=inventory;
        this.imageWidth = 176;
        this.imageHeight= 133;
        this.addRenderableWidget(craft_button=new ImageButton(this.leftPos+36,this.topPos+8, 24,14,
                BUTTON_SPRITE,
                pButton -> {
                    NetWorkHandler.INSTANCE.send(new APMessage(), PacketDistributor.SERVER.noArg());
                    this.setFocused(false);
                }));
    }
    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        int i = this.leftPos;
        int j = (this.height - this.imageHeight) / 2;
        pGuiGraphics.blit(BACKGROUND_TEXTURE, i, j, 0, 0, this.imageWidth, this.imageHeight);
    }

    @Override
    protected void containerTick() {
        craft_button.setFocused(false);
        craft_button.setPosition(this.leftPos+36,this.topPos+8);
    }

    @Override
    protected void renderLabels(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {
        pGuiGraphics.drawString(this.font, this.title, this.titleLabelX+80, this.titleLabelY, 4210752, false);
        pGuiGraphics.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX+80, this.inventoryLabelY-30, 4210752, false);
    }
    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(pGuiGraphics, pMouseX, pMouseY);
        if (craft_button.isMouseOver(pMouseX,pMouseY)){
            pGuiGraphics.renderComponentTooltip(this.font,getTooltip(getMenu().potionSlot.getItem(),getMenu().foodSlot.getItem(),this.inventory.player.level()),pMouseX,pMouseY);
        }
    }
    public static List<Component> getTooltip(ItemStack p, ItemStack f, Level level){
        List<Component> components=new ArrayList<>();
        components.add(Component.translatable("com.hileb.ap.botton"));
        if (APConfig.ap_showPotion_desc){
            if (!f.isEmpty()){
                if (f.getTag()!=null) {
                    components.add(Component.literal(" "));
                    components.add(Component.translatable("com.hileb.ap.desc_1"));

                    List<Component> fc=new ArrayList<>();
                    PotionUtils.addPotionTooltip(PotionUtil.getListOfPotionEffect(PotionUtil.getAllEffect(f)),fc,1.0F,PotionUtil.getWorldTickRate(level));
                    if(!MinecraftForge.EVENT_BUS.post(new APTooltipEvent(fc,f)))components.addAll(fc);
                }
            }
            if (!p.isEmpty()){
                if (p.getTag()!=null) {
                    components.add(Component.literal(" "));
                    components.add(Component.translatable("com.hileb.ap.desc_2"));

                    List<Component> pc=new ArrayList<>();
                    PotionUtils.addPotionTooltip(PotionUtil.getListOfPotionEffect(PotionUtil.getAllEffect(p)), components, 1.0F,PotionUtil.getWorldTickRate(level));
                    if (!MinecraftForge.EVENT_BUS.post(new APTooltipEvent(pc,p)))components.addAll(pc);
                }
            }
        }
        return components;
    }
}
