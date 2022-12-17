package com.Hileb.add_potion.gui.potion.expOne;

import com.Hileb.add_potion.IdlFramework;
import com.Hileb.add_potion.init.ModConfig;
import com.Hileb.add_potion.network.NetworkHandler;
import com.Hileb.add_potion.util.NBTStrDef.IDLNBTUtil;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.PotionTypes;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.config.GuiUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.awt.*;
import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

                    if (ModConfig.entityElectricShakingConf.ap_showPotion_desc && !foodSlot.getStack().isEmpty()){
                        if (foodSlot.getStack().getTagCompound()!=null)
                            tooltip.add(I18n.format("com.hileb.ap.desc_1"));
                        addPotionTooltip(foodSlot.getStack().copy(), tooltip, 1.0F);
                        tooltip.add("");
                    }
                    if (!potionSlot.getStack().isEmpty()){
                        tooltip.add(I18n.format("com.hileb.ap.desc_2"));
                        addPotionTooltip(potionSlot.getStack().copy(), tooltip, 1.0F);

                    }

                    GuiUtils.drawHoveringText(tooltip,mouseX,mouseY,width,height,-1,fontRenderer);
                }
            }
        }
    }
    @SideOnly(Side.CLIENT)
    public static void addPotionTooltip(ItemStack stackIn, List<String> lores, float durationFactor) {
        List<String> s=new ArrayList<>();
        for(int i = 0; i< IDLNBTUtil.GetInt(stackIn,"PotionCountAP",0); i++){
            if (IDLNBTUtil.GetString(
                    stackIn,String.format(
                            "PotionAPS_%d_I",i),
                    null)!=null){
                s.add(IDLNBTUtil.GetString(
                        stackIn,String.format(
                                "PotionAPS_%d_I",i),
                        null));
            }
        }
        for(String p:s){
            {
                if (p!=null){
                    PotionType type=PotionType.getPotionTypeForName(p);
                    if (type!=null && type!=PotionTypes.EMPTY){
                        addPotionTooltip(type,lores,1.0f);
                    }
                }
            }
        }
        if (stackIn.hasTagCompound()){
            if (PotionUtils.getPotionTypeFromNBT(stackIn.getTagCompound())!= null){
                addPotionTooltip(PotionUtils.getPotionTypeFromNBT(stackIn.getTagCompound()),lores,1.0f);
            }
        }
    }
    @SideOnly(Side.CLIENT)
    public static void addPotionTooltip(PotionType type, List<String> lores, float durationFactor)
    {
        List<PotionEffect> list = type.getEffects();
        List<Tuple<String, AttributeModifier>> list1 = Lists.<Tuple<String, AttributeModifier>>newArrayList();

        if (list.isEmpty())
        {
            String s = net.minecraft.util.text.translation.I18n.translateToLocal("effect.none").trim();
            lores.add(TextFormatting.GRAY + s);
        }
        else
        {
            for (PotionEffect potioneffect : list)
            {
                String s1 = net.minecraft.util.text.translation.I18n.translateToLocal(potioneffect.getEffectName()).trim();
                Potion potion = potioneffect.getPotion();
                Map<IAttribute, AttributeModifier> map = potion.getAttributeModifierMap();

                if (!map.isEmpty())
                {
                    for (Map.Entry<IAttribute, AttributeModifier> entry : map.entrySet())
                    {
                        AttributeModifier attributemodifier = entry.getValue();
                        AttributeModifier attributemodifier1 = new AttributeModifier(attributemodifier.getName(), potion.getAttributeModifierAmount(potioneffect.getAmplifier(), attributemodifier), attributemodifier.getOperation());
                        list1.add(new Tuple(((IAttribute)entry.getKey()).getName(), attributemodifier1));
                    }
                }

                if (potioneffect.getAmplifier() > 0)
                {
                    s1 = s1 + " " + net.minecraft.util.text.translation.I18n.translateToLocal("potion.potency." + potioneffect.getAmplifier()).trim();
                }

                if (potioneffect.getDuration() > 20)
                {
                    s1 = s1 + " (" + Potion.getPotionDurationString(potioneffect, durationFactor) + ")";
                }

                if (potion.isBadEffect())
                {
                    lores.add(TextFormatting.RED + s1);
                }
                else
                {
                    lores.add(TextFormatting.BLUE + s1);
                }
            }
        }

        if (!list1.isEmpty())
        {
            lores.add("");
            lores.add(TextFormatting.DARK_PURPLE + net.minecraft.util.text.translation.I18n.translateToLocal("potion.whenDrank"));

            for (Tuple<String, AttributeModifier> tuple : list1)
            {
                AttributeModifier attributemodifier2 = tuple.getSecond();
                double d0 = attributemodifier2.getAmount();
                double d1;

                if (attributemodifier2.getOperation() != 1 && attributemodifier2.getOperation() != 2)
                {
                    d1 = attributemodifier2.getAmount();
                }
                else
                {
                    d1 = attributemodifier2.getAmount() * 100.0D;
                }

                if (d0 > 0.0D)
                {
                    lores.add(TextFormatting.BLUE + net.minecraft.util.text.translation.I18n.translateToLocalFormatted("attribute.modifier.plus." + attributemodifier2.getOperation(), ItemStack.DECIMALFORMAT.format(d1), net.minecraft.util.text.translation.I18n.translateToLocal("attribute.name." + (String)tuple.getFirst())));
                }
                else if (d0 < 0.0D)
                {
                    d1 = d1 * -1.0D;
                    lores.add(TextFormatting.RED + net.minecraft.util.text.translation.I18n.translateToLocalFormatted("attribute.modifier.take." + attributemodifier2.getOperation(), ItemStack.DECIMALFORMAT.format(d1), net.minecraft.util.text.translation.I18n.translateToLocal("attribute.name." + (String)tuple.getFirst())));
                }
            }
        }
    }
}
