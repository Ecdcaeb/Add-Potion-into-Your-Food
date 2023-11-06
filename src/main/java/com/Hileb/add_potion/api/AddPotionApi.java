package com.Hileb.add_potion.api;

import com.Hileb.add_potion.api.event.APPotionAffectEvent;
import com.Hileb.add_potion.common.util.APUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

public class AddPotionApi {
	public static void onFoodEaten(LivingEntity entity, ServerLevel serverLevel, ItemStack food) {
		LivingEntity owner = APUtils.getOwner(serverLevel, food);

		if(!MinecraftForge.EVENT_BUS.post(new APPotionAffectEvent(entity, food))) {
			APUtils.getEffectsFromFood(food).forEach((instance, potionType) -> potionType.afterEat.accept(entity, instance, owner));
		}
	}
}
