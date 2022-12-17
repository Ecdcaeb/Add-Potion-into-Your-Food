package com.Hileb.add_potion.gui.potion.expOne;

import com.Hileb.add_potion.IdlFramework;
import com.Hileb.add_potion.init.ModConfig;
import com.Hileb.add_potion.network.NetworkHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.config.GuiUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.awt.*;
import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import static com.Hileb.add_potion.gui.ModGuiElementLoader.GUI_DEMO;

@SideOnly(Side.CLIENT)
public class GuiContainerDemo extends GuiContainer
{
    private static final int BUTTON = 0;
    private static final String TEXTURE_PATH = IdlFramework.MODID + ":" + "textures/gui/gui_potion.png";
    private static final ResourceLocation TEXTURE = new ResourceLocation(TEXTURE_PATH);

    protected Slot potionSlot;
    protected Slot foodSlot;

    public GuiContainerDemo(ContainerDemo inventorySlotsIn)
    {
        super(inventorySlotsIn);
        this.xSize = 176;
        this.ySize = 133;
        potionSlot=inventorySlotsIn.getPotionSlot();
        foodSlot=inventorySlotsIn.getFoodSlot();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
        this.drawBottonTooltip(mouseX,mouseY);
    }
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F);

        this.mc.getTextureManager().bindTexture(TEXTURE);
        int offsetX = (this.width - this.xSize) / 2, offsetY = (this.height - this.ySize) / 2;

        this.drawTexturedModalRect(offsetX, offsetY, 0, 0, this.xSize, this.ySize);

    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        //this.drawVerticalLine(30, 19, 36, 0xFF000000);
        //this.drawHorizontalLine(8, 167, 43, 0xFF000000);

        String title = I18n.format("tile.blackstonebrick.name");
        this.fontRenderer.drawString(title, ((this.xSize - this.fontRenderer.getStringWidth(title)) / 2)+30, 6, 0xFFFF00);
    }
    @Override
    public void initGui()
    {
        super.initGui();
        int offsetX = (this.width - this.xSize) / 2, offsetY = (this.height - this.ySize) / 2;
        this.buttonList.add(new GuiButton(BUTTON, offsetX + 36, offsetY + 8, 23, 13, "")
        {

            @Override
            public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
            {
                if (this.visible)
                {
                    GlStateManager.color(1.0F, 1.0F, 1.0F);

                    mc.getTextureManager().bindTexture(TEXTURE);
                    this.drawTexturedModalRect(this.x, this.y, 1, 134, this.width, this.height);
                }
            }
        });
    }
    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {

        switch (button.id)
        {
            case BUTTON:
                ProcessMessage message=new ProcessMessage();
                message.message=ProcessMessage.PROCESS;
                NetworkHandler.SendToServer(message);
                return;
            default:
                super.actionPerformed(button);
                return;
        }
    }
    protected void drawBottonTooltip(int mouseX,int mouseY){
        int offsetX = (this.width - this.xSize) / 2, offsetY = (this.height - this.ySize) / 2;
        //offsetX + 36, offsetY + 8, 23, 13
        offsetX=offsetX+36;
        offsetY=offsetY+8;
        if (mouseX>=offsetX && mouseY>=offsetY){
            if(mouseX<=offsetX+23){
                if (mouseY<=offsetY+13){
                    List<String> tooltip= new ArrayList<>();
                    tooltip.add(I18n.format("com.hileb.ap.botton"));
                    tooltip.add("");


                    if (ModConfig.entityElectricShakingConf.ap_showPotion_desc){
                        tooltip.add(I18n.format("com.hileb.ap.desc_1"));
                        PotionUtils.addPotionTooltip(foodSlot.getStack().copy(), tooltip, 1.0F);
                    }
                    tooltip.add(I18n.format("com.hileb.ap.desc_2"));
                    PotionUtils.addPotionTooltip(potionSlot.getStack().copy(), tooltip, 1.0F);

                    int max=0;
                    for (String s:tooltip){
                        if (s.length()>=max)max=s.length();
                    }
                    GuiUtils.drawHoveringText(tooltip,mouseX,mouseY,width,height,tooltip.size(),fontRenderer);
                }
            }
        }
    }
}
