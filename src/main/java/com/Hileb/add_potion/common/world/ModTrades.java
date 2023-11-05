package com.Hileb.add_potion.common.world;

import com.Hileb.add_potion.api.event.AddVillagerTradePotionEvent;
import com.Hileb.add_potion.common.util.APUtils;
import com.google.common.collect.Lists;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.MinecraftForge;

import java.util.List;
import java.util.Random;

import static com.Hileb.add_potion.common.util.APUtils.TAG_DISABLE;
import static com.Hileb.add_potion.common.util.APUtils.TAG_EFFECTS;
import static net.minecraft.world.item.alchemy.Potions.*;

@SuppressWarnings("unused")
public final class ModTrades {
	public static final int DEFAULT_SUPPLY = 12;
	public static final int COMMON_ITEMS_SUPPLY = 16;
	public static final int UNCOMMON_ITEMS_SUPPLY = 3;
	public static final int XP_LEVEL_1_SELL = 1;
	public static final int XP_LEVEL_1_BUY = 2;
	public static final int XP_LEVEL_2_SELL = 5;
	public static final int XP_LEVEL_2_BUY = 10;
	public static final int XP_LEVEL_3_SELL = 10;
	public static final int XP_LEVEL_3_BUY = 20;
	public static final int XP_LEVEL_4_SELL = 15;
	public static final int XP_LEVEL_4_BUY = 30;
	public static final int XP_LEVEL_5_TRADE = 30;
	public static final float LOW_TIER_PRICE_MULTIPLIER = 0.05F;
	public static final float HIGH_TIER_PRICE_MULTIPLIER = 0.2F;

	private static final List<Potion> ALL_BENEFICIAL_POTIONS = Lists.newArrayList(
			NIGHT_VISION,
			INVISIBILITY,
			LEAPING,
			FIRE_RESISTANCE,
			SWIFTNESS,
			WATER_BREATHING,
			HEALING,
			REGENERATION,
			STRENGTH,
			SLOW_FALLING
	);

	private static final List<Potion> ALL_HARMFUL_POTIONS = Lists.newArrayList(
			SLOWNESS,
			HARMING,
			POISON,
			WEAKNESS
	);

	public record FoodWithRandomEffectsForEmerald(ItemLike item, int itemCount, int chooses, int emeraldCost, int maxUses, int villagerXp, float priceMultiplier) implements VillagerTrades.ItemListing {
		@Override
		public MerchantOffer getOffer(Entity trader, Random random) {
			ItemStack result = new ItemStack(this.item, this.itemCount);
			CompoundTag nbt = result.getOrCreateTag();
			ListTag listTag = new ListTag();
			List<Potion> pool = random.nextBoolean() ? ALL_BENEFICIAL_POTIONS : ALL_HARMFUL_POTIONS;
			assert this.chooses < pool.size() : "Chooses Must be lesser than the size of pool!";
			boolean[] flags = new boolean[pool.size()];
			for (int i = 0; i < this.chooses; ++i) {
				int choice;
				do {
					choice = random.nextInt(pool.size());
				} while(flags[choice]);
				flags[choice] = true;
				for (MobEffectInstance instance: pool.get(choice).getEffects()) {
					APUtils.applyEffectTo(listTag, instance, APUtils.PotionType.DEFAULT);
				}
			}
			nbt.put(TAG_EFFECTS, listTag);
			if(random.nextInt(4) == 0) {
				nbt.putBoolean(TAG_DISABLE, true);
			}
			result.setTag(nbt);

			return new MerchantOffer(new ItemStack(Items.EMERALD, this.emeraldCost), result, this.maxUses, this.villagerXp, this.priceMultiplier);
		}
	}

	public record EmeraldForItems(ItemLike item, int itemCount, int emeraldCost, int maxUses, int villagerXp, float priceMultiplier) implements VillagerTrades.ItemListing {
		public MerchantOffer getOffer(Entity trader, Random random) {
			ItemStack itemstack = new ItemStack(this.item, this.itemCount);
			return new MerchantOffer(itemstack, new ItemStack(Items.EMERALD, this.emeraldCost), this.maxUses, this.villagerXp, this.priceMultiplier);
		}
	}

	public static void init() {
		MinecraftForge.EVENT_BUS.post(new AddVillagerTradePotionEvent(ALL_BENEFICIAL_POTIONS, ALL_HARMFUL_POTIONS));
	}
}
