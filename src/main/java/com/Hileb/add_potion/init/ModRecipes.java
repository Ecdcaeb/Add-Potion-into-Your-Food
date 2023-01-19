package com.Hileb.add_potion.init;

import com.Hileb.add_potion.meta.MetaUtil;
import com.Hileb.add_potion.util.Reference;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class ModRecipes {
	
	
	public static void Init() {
		registerRecipe();
		registerSmelting();
	}
	private static void registerRecipe(){

	}
	private static void  registerSmelting(){
	}
	
	@SubscribeEvent
	public static void registerRecipes(RegistryEvent.Register<IRecipe> evt) {
		IForgeRegistry<IRecipe> r = evt.getRegistry();
		MetaUtil.loadmodload();
		MetaUtil.modLoadInit();
	}
}
