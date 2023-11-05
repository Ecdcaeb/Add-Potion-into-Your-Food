package com.Hileb.add_potion.common;

import com.Hileb.add_potion.api.event.APPotionAffectEvent;
import com.Hileb.add_potion.api.event.ApplyEffectsToFoodEvent;
import com.Hileb.add_potion.api.event.IngredientCheckEvent;
import com.Hileb.add_potion.common.util.APUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.Hileb.add_potion.AddPotion.MODID;

@Mod.EventBusSubscriber(modid = MODID)
public class ForgeEventHandler {
	@SubscribeEvent
	public static void onFoodEaten(LivingEntityUseItemEvent.Finish event) {
		LivingEntity entity = event.getEntityLiving();
		Level level = entity.level;
		if (level instanceof ServerLevel serverLevel) {
			ItemStack food = event.getItem();
			LivingEntity owner = APUtils.getOwner(serverLevel, food);

			if(!MinecraftForge.EVENT_BUS.post(new APPotionAffectEvent(entity, event.getItem()))) {
				APUtils.getEffectsFromFood(food).forEach((instance, potionType) -> potionType.afterEat.accept(entity, instance, owner));
			}
		}
	}

	@SubscribeEvent
	public static void onPlaceToPotionSlot(IngredientCheckEvent.Potion event) {
		if(event.getStack().is(Items.GOLD_INGOT)) {
			event.setIngredient(true);
		}
	}

	@SubscribeEvent
	public static void onApplyPotionToFood(ApplyEffectsToFoodEvent event) {
		if(event.getPotion().is(Items.GOLD_INGOT)) {
			APUtils.setEffectsHiding(event.getFood());
		}
	}
}
