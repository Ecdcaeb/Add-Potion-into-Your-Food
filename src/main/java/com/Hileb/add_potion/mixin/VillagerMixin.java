package com.Hileb.add_potion.mixin;

import com.Hileb.add_potion.api.AddPotionApi;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Villager.class)
public class VillagerMixin {
	@Redirect(method = "eatUntilFull", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/SimpleContainer;removeItem(II)Lnet/minecraft/world/item/ItemStack;"))
	private ItemStack consumeItemAndApplyAPEffects(SimpleContainer instance, int index, int count) {
		ItemStack itemStack = instance.removeItem(index, count);
		Villager current = (Villager)(Object)this;
		if(current.level instanceof ServerLevel serverLevel) {
			AddPotionApi.onFoodEaten(current, serverLevel, itemStack);
		}
		return itemStack;
	}
}
