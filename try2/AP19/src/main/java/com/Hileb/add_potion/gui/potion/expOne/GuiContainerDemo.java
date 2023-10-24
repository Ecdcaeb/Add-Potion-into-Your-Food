package com.Hileb.add_potion.gui.potion.expOne;

import com.Hileb.add_potion.AP19Main;
import com.Hileb.add_potion.event.APTooltipEvent;
import com.Hileb.add_potion.init.ModConfig;
import com.Hileb.add_potion.network.NetworkHandler;
import com.Hileb.add_potion.util.potion.APotion;
import com.Hileb.add_potion.util.potion.PotionUtil;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import com.sun.xml.internal.bind.v2.TODO;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.BeaconScreen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.*;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.network.protocol.game.ServerboundSetBeaconPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Tuple;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.BeaconMenu;
import net.minecraft.world.inventory.ContainerListener;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.ScreenUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.IOException;
import java.util.ArrayList;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class GuiContainerDemo extends AbstractContainerScreen<ContainerDemo>
{
    private static final Component NO_EFFECT = Component.translatable("effect.none").withStyle(ChatFormatting.GRAY);
    public static final Component TITLE=Component.translatable("tile.blackstonebrick.name");
    public Player entityPlayer;
    private int coolDown=20;
    private static final int BUTTON = 0;

    private static final ResourceLocation TEXTURE_PATH=new ResourceLocation(AP19Main.MODID,"textures/gui/gui_potion.png");

    protected Slot potionSlot;
    protected Slot foodSlot;


    private final List<AbstractButton> buttons = Lists.newArrayList();
    @SubscribeEvent
    public void update(TickEvent.LevelTickEvent event){
        if (event.level.isClientSide)coolDown++;
    }


    public GuiContainerDemo(final ContainerDemo containerDemo, Inventory playerInv, Component titleComponent) {
        super(containerDemo,playerInv,titleComponent);
        this.imageWidth = 176;
        this.imageHeight = 133;



        potionSlot=containerDemo.getPotionSlot();
        foodSlot=containerDemo.getFoodSlot();
        entityPlayer=playerInv.player;


//        containerDemo.addSlotListener(new ContainerListener() {
//            public void slotChanged(AbstractContainerMenu p_97973_, int p_97974_, ItemStack p_97975_) {
//            }
//
//            public void dataChanged(AbstractContainerMenu p_169628_, int p_169629_, int p_169630_) {
//                BeaconScreen.this.primary = p_97912_.getPrimaryEffect();
//                BeaconScreen.this.secondary = p_97912_.getSecondaryEffect();
//            }
//        });
    }


    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(poseStack);
        super.render(poseStack,mouseX,mouseY,partialTicks);
        this.renderTooltip(poseStack,mouseX,mouseY);
        this.drawBottonTooltip(mouseX,mouseY);
    }

    @Override
    protected void renderBg(PoseStack poseStack,float partialTicks, int mouseX, int mouseY) {

        RenderSystem.setShaderTexture(0,TEXTURE_PATH);
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        blit(poseStack, i, j, 0, 0, this.imageWidth, this.imageHeight);
    }
    @Override
    protected void renderLabels(PoseStack p_97935_, int p_97936_, int p_97937_) {
        drawCenteredString(p_97935_, this.font,TITLE, ((this.getXSize() - this.font.width(title)) / 2)+30, 6, 0xFFFF00);
    }


    @Override
    public void init()
    {
        super.init();
        int offsetX = (this.width - this.getXSize()) / 2, offsetY = (this.height - this.getYSize()) / 2;
        this.buttons.add(new BeaconConfirmButton(null, offsetX + 36, offsetY + 8));
    }
    protected void drawBottonTooltip(PoseStack poseStack,int mouseX,int mouseY){
        int offsetX = (this.width - this.imageWidth) / 2, offsetY = (this.height - this.imageHeight) / 2;
        //offsetX + 36, offsetY + 8, 23, 13
        offsetX=offsetX+36;
        offsetY=offsetY+8;
        if (mouseX>=offsetX && mouseY>=offsetY){
            if(mouseX<=offsetX+23){
                if (mouseY<=offsetY+13){
                    List<Component> lores=new ArrayList<>();
                    lores.add(Component.translatable("com.hileb.ap.botton"));

                    lores.add(Component.translatable(""));

                    if (ModConfig.entityElectricShakingConf.ap_showPotion_desc && !foodSlot.getItem().isEmpty()){
                        if (foodSlot.getItem().hasTag())
                            lores.add(Component.translatable("com.hileb.ap.desc_1"));
                        addPotionTooltip(foodSlot.getItem().copy(), lores);
                        lores.add(Component.translatable(""));
                    }
                    if (!potionSlot.getItem().isEmpty()){
                        lores.add(Component.translatable("com.hileb.ap.desc_2"));
                        addPotionTooltip(potionSlot.getItem().copy(), lores);

                    }
                    renderTooltip(poseStack,lores, Optional.empty(),mouseX,mouseY,font);
                }
            }
        }
    }

    public static void addPotionTooltip(ItemStack stackIn,  List<Component> lores) {
        if (ModConfig.entityElectricShakingConf.ap_showPotion_desc){
            List<APotion> potions=PotionUtil.getAllEffect(stackIn.copy());
            addPotionTooltip(PotionUtil.getListOfPotionEffect(potions),lores,1.0F);
            MinecraftForge.EVENT_BUS.post(new APTooltipEvent(lores,stackIn));
        }
    }
    public static void addPotionTooltip(List<MobEffectInstance> p_259687_, List<Component> p_259660_, float p_259949_) {
        List<Pair<Attribute, AttributeModifier>> list = Lists.newArrayList();
        if (p_259687_.isEmpty()) {
            p_259660_.add(NO_EFFECT);
        } else {
            for(MobEffectInstance mobeffectinstance : p_259687_) {
                MutableComponent mutablecomponent = Component.translatable(mobeffectinstance.getDescriptionId());
                MobEffect mobeffect = mobeffectinstance.getEffect();
                Map<Attribute, AttributeModifier> map = mobeffect.getAttributeModifiers();
                if (!map.isEmpty()) {
                    for(Map.Entry<Attribute, AttributeModifier> entry : map.entrySet()) {
                        AttributeModifier attributemodifier = entry.getValue();
                        AttributeModifier attributemodifier1 = new AttributeModifier(attributemodifier.getName(), mobeffect.getAttributeModifierValue(mobeffectinstance.getAmplifier(), attributemodifier), attributemodifier.getOperation());
                        list.add(new Pair<>(entry.getKey(), attributemodifier1));
                    }
                }

                if (mobeffectinstance.getAmplifier() > 0) {
                    mutablecomponent = Component.translatable("potion.withAmplifier", mutablecomponent, Component.translatable("potion.potency." + mobeffectinstance.getAmplifier()));
                }

                if (!mobeffectinstance.endsWithin(20)) {
                    mutablecomponent = Component.translatable("potion.withDuration", mutablecomponent, MobEffectUtil.formatDuration(mobeffectinstance, p_259949_));
                }

                p_259660_.add(mutablecomponent.withStyle(mobeffect.getCategory().getTooltipFormatting()));
            }
        }

        if (!list.isEmpty()) {
            p_259660_.add(CommonComponents.EMPTY);
            p_259660_.add(Component.translatable("potion.whenDrank").withStyle(ChatFormatting.DARK_PURPLE));

            for(Pair<Attribute, AttributeModifier> pair : list) {
                AttributeModifier attributemodifier2 = pair.getSecond();
                double d0 = attributemodifier2.getAmount();
                double d1;
                if (attributemodifier2.getOperation() != AttributeModifier.Operation.MULTIPLY_BASE && attributemodifier2.getOperation() != AttributeModifier.Operation.MULTIPLY_TOTAL) {
                    d1 = attributemodifier2.getAmount();
                } else {
                    d1 = attributemodifier2.getAmount() * 100.0D;
                }

                if (d0 > 0.0D) {
                    p_259660_.add(Component.translatable("attribute.modifier.plus." + attributemodifier2.getOperation().toValue(), ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(d1), Component.translatable(pair.getFirst().getDescriptionId())).withStyle(ChatFormatting.BLUE));
                } else if (d0 < 0.0D) {
                    d1 *= -1.0D;
                    p_259660_.add(Component.translatable("attribute.modifier.take." + attributemodifier2.getOperation().toValue(), ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(d1), Component.translatable(pair.getFirst().getDescriptionId())).withStyle(ChatFormatting.RED));
                }
            }
        }

    }
    @OnlyIn(Dist.CLIENT)
    class BeaconConfirmButton extends ImageButton {
        public static Button.OnPress NULL_PRESS=new Button.OnPress(){@Override public void onPress(Button p_93751_) {}};
        public BeaconConfirmButton(Component component, int offsetX, int offsetY) {
            super(offsetX,offsetY,23,13,1,134,0,TEXTURE_PATH,23,13,NULL_PRESS,component);
        }

        public void onPress() {
            if (coolDown>=20){
                ProcessMessage message=new ProcessMessage();
                message.message=ProcessMessage.PROCESS;
                message.uuid=entityPlayer.getUniqueID().toString();
                NetworkHandler.SendToServer(message);
            }
        }
    }
}
