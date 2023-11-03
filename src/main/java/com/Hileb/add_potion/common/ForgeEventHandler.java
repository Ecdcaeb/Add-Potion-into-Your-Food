package com.Hileb.add_potion.common;

import com.Hileb.add_potion.api.event.APPotionAffectEvent;
import com.Hileb.add_potion.common.util.APUtils;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

import static com.Hileb.add_potion.AddPotion.MODID;

@Mod.EventBusSubscriber(modid = MODID)
public class ForgeEventHandler {
	@SubscribeEvent
	public static void onFoodEaten(LivingEntityUseItemEvent.Finish event) {
		LivingEntity entity = event.getEntityLiving();
		Level level = entity.level;
		if (!level.isClientSide) {
			List<MobEffectInstance> effects = APUtils.getEffectsFromFood(event.getItem());

			if(!MinecraftForge.EVENT_BUS.post(new APPotionAffectEvent(entity, event.getItem()))) {
				effects.forEach(instance -> entity.addEffect(instance, entity));
			}
		}
	}
}
