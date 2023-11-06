package com.Hileb.add_potion.mixin.animals;

import com.Hileb.add_potion.api.AddPotionApi;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.animal.Parrot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Parrot.class)
public class ParrotMixin {
	@Inject(method = "mobInteract", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/animal/Parrot;addEffect(Lnet/minecraft/world/effect/MobEffectInstance;)Z", shift = At.Shift.BEFORE))
	public void applyAPEffectsTo(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
		Parrot current = (Parrot)(Object)this;
		ItemStack itemStack = player.getItemInHand(hand);
		if(current.level instanceof ServerLevel serverLevel) {
			AddPotionApi.onFoodEaten(current, serverLevel, itemStack);
		}
	}
}
