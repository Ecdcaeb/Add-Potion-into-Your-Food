package com.Hileb.add_potion.common;

import com.Hileb.add_potion.api.AddPotionApi;
import com.Hileb.add_potion.api.event.ApplyEffectsToFoodEvent;
import com.Hileb.add_potion.api.event.IngredientCheckEvent;
import com.Hileb.add_potion.common.util.APUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.entity.monster.Strider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
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
			AddPotionApi.onFoodEaten(entity, serverLevel, food);
		}
	}

	@SubscribeEvent
	public static void onPlaceToFoodSlot(IngredientCheckEvent.Food event) {
		ItemStack food = event.getStack();
		if(food.is(Items.CRIMSON_FUNGUS) || food.is(ItemTags.AXOLOTL_TEMPT_ITEMS) ||
				AbstractHorse.FOOD_ITEMS.test(food) || Chicken.FOOD_ITEMS.test(food) ||
				Llama.FOOD_ITEMS.test(food) || Pig.FOOD_ITEMS.test(food) ||
				Turtle.FOOD_ITEMS.test(food) || Strider.FOOD_ITEMS.test(food)) {
			event.setIngredient(true);
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
		} else if(event.getPotion().is(Items.POTION)) {
			event.setPotionRemaining(new ItemStack(Items.GLASS_BOTTLE, event.getPotion().getCount()));
		}
	}
}
