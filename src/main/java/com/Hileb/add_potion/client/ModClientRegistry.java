package com.Hileb.add_potion.client;

import com.Hileb.add_potion.client.screens.PotionTableScreen;
import com.Hileb.add_potion.common.init.ModMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static com.Hileb.add_potion.AddPotion.MODID;

@Mod.EventBusSubscriber(modid = MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModClientRegistry {
	@SubscribeEvent
	public static void setup(final FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			registerRenderLayers();
			registerContainersAndScreens();
		});
	}

	private static void registerRenderLayers() {
	}

	private static void registerContainersAndScreens() {
		MenuScreens.register(ModMenuTypes.POTION_TABLE_MENU.get(), PotionTableScreen::new);
	}
}
