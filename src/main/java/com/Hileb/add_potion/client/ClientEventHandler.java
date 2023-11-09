package com.Hileb.add_potion.client;

import com.Hileb.add_potion.common.util.APUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Locale;

import static com.Hileb.add_potion.AddPotion.MODID;

@Mod.EventBusSubscriber(modid = MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientEventHandler {
	@SubscribeEvent
	public static void onToolTipShow(ItemTooltipEvent event) {
		ItemStack itemStack = event.getItemStack();
		if(APUtils.canPlaceToFoodSlot(itemStack)) {
			if(!APUtils.isEffectsHiding(itemStack)) {
				APUtils.getEffectsFromFood(itemStack).forEach(((instance, potionType) -> {
					MutableComponent component = Component.translatable(instance.getDescriptionId());
					MobEffect mobeffect = instance.getEffect();

					if (instance.getAmplifier() > 0) {
						component = Component.translatable("potion.withAmplifier", component, Component.translatable("potion.potency." + instance.getAmplifier()));
					}

					if (instance.getDuration() > 20) {
						component = Component.translatable("potion.withDuration", component, MobEffectUtil.formatDuration(instance, 1.0F));
					}

					event.getToolTip().add(Component.translatable("add_potion.potion_type." + potionType.name().toLowerCase(Locale.ROOT), component.withStyle(mobeffect.getCategory().getTooltipFormatting())));
				}));
			}
		}
	}
}
