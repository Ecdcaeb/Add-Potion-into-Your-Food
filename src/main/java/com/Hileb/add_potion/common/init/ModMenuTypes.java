package com.Hileb.add_potion.common.init;

import com.Hileb.add_potion.common.gui.PotionTableMenu;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.Hileb.add_potion.AddPotion.MODID;

public class ModMenuTypes {
	private static final DeferredRegister<MenuType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.MENU_TYPES, MODID);

	public static final RegistryObject<MenuType<PotionTableMenu>> POTION_TABLE_MENU = REGISTER.register(
			"potion_table", () -> new MenuType<>(PotionTableMenu::new, FeatureFlags.DEFAULT_FLAGS)
	);

	public static void init(IEventBus bus) {
		REGISTER.register(bus);
	}
}
