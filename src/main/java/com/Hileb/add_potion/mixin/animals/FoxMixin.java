package com.Hileb.add_potion.mixin.animals;

import com.Hileb.add_potion.api.AddPotionApi;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Fox.class)
public class FoxMixin {
	@Inject(method = "usePlayerItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/animal/Fox;playSound(Lnet/minecraft/sounds/SoundEvent;FF)V", shift = At.Shift.BEFORE))
	protected void applyAPEffectsTo(Player player, InteractionHand hand, ItemStack itemStack, CallbackInfo ci) {
		Fox current = (Fox)(Object)this;
		if(current.level instanceof ServerLevel serverLevel) {
			AddPotionApi.onFoodEaten(current, serverLevel, itemStack);
		}
	}
}
