package com.Hileb.add_potion.common.world;

import com.Hileb.add_potion.api.event.AddVillagerTradePotionEvent;
import com.Hileb.add_potion.common.init.ModBlocks;
import com.Hileb.add_potion.common.init.ModSounds;
import com.Hileb.add_potion.common.util.compat.LoadMods;
import com.Hileb.add_potion.mixin.HeroGiftsTaskAccess;
import com.google.common.collect.ImmutableSet;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Collection;
import java.util.List;

import static com.Hileb.add_potion.AddPotion.MODID;

public final class Villages {
	public static final ResourceLocation APOTHECARY = new ResourceLocation(MODID, "apothecary");

	public static void init() {
		HeroGiftsTaskAccess.getGifts().put(Registers.PROF_APOTHECARY.get(), new ResourceLocation(MODID, "gameplay/hero_of_the_village/apothecary_gift"));
	}

	public static void addAllStructuresToPool(RegistryAccess registryAccess) {
	}

	@SuppressWarnings("SameParameterValue")
	public static class Registers {
		public static final DeferredRegister<PoiType> POINTS_OF_INTEREST = DeferredRegister.create(ForgeRegistries.POI_TYPES, MODID);
		public static final DeferredRegister<VillagerProfession> PROFESSIONS = DeferredRegister.create(ForgeRegistries.PROFESSIONS, MODID);

		public static final RegistryObject<PoiType> POI_POTION_TABLE = POINTS_OF_INTEREST.register(
				"potion_table", () -> createPOI(Villages.APOTHECARY, assembleStates(ModBlocks.POTION_TABLE.get()))
		);

		public static final RegistryObject<VillagerProfession> PROF_APOTHECARY = PROFESSIONS.register(
				Villages.APOTHECARY.getPath(), () -> createProf(Villages.APOTHECARY, POI_POTION_TABLE.get(), ModSounds.VILLAGER_WORK_APOTHECARY)
		);

		private static Collection<BlockState> assembleStates(Block block) {
			return block.getStateDefinition().getPossibleStates();
		}

		private static PoiType createPOI(ResourceLocation name, Collection<BlockState> block) {
			return new PoiType(name.toString(), ImmutableSet.copyOf(block), 1, 1);
		}

		private static VillagerProfession createProf(ResourceLocation name, PoiType poi, SoundEvent sound) {
			return new VillagerProfession(name.toString(), poi, ImmutableSet.<Item>builder().build(), ImmutableSet.<Block>builder().build(), sound);
		}

		public static void init(IEventBus bus) {
			POINTS_OF_INTEREST.register(bus);
			PROFESSIONS.register(bus);
		}
	}

	@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
	public static class Events {
		@SubscribeEvent
		public static void registerTrades(VillagerTradesEvent event) {
			ModTrades.init();
			Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

			ResourceLocation currentVillagerProfession = event.getType().getRegistryName();

			if(APOTHECARY.equals(currentVillagerProfession)) {
				trades.get(1).add(new ModTrades.FoodWithRandomEffectsForEmerald(Items.BREAD, 2, 1, 1, ModTrades.DEFAULT_SUPPLY, ModTrades.XP_LEVEL_1_SELL, ModTrades.LOW_TIER_PRICE_MULTIPLIER));
				trades.get(1).add(new ModTrades.FoodWithRandomEffectsForEmerald(Items.APPLE, 2, 1, 1, ModTrades.DEFAULT_SUPPLY, ModTrades.XP_LEVEL_1_SELL, ModTrades.LOW_TIER_PRICE_MULTIPLIER));
			}
		}

		@SubscribeEvent
		public static void onAddVillagerTradePotions(AddVillagerTradePotionEvent event) {
			LoadMods.addEmeraldCraftPotions(event.getBeneficial(), event.getHarmful());
		}
	}
}
