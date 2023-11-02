package com.Hileb.add_potion.common.init;

import com.Hileb.add_potion.common.util.Reference;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


@Config(modid = Reference.MOD_ID, category = "")
public class ModConfig {
	@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
	private static class EventHandler {

		private EventHandler() {
		}

		@SubscribeEvent
		public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
			if (event.getModID().equals(Reference.MOD_ID)) {
				ConfigManager.sync(Reference.MOD_ID, Config.Type.INSTANCE);
			}
		}
	}
	@Config.LangKey("ap.configMisc.desc")
	@Config.Comment("ap.configMisc.desc")
	public static final EntityElectricShakingConf entityElectricShakingConf = new EntityElectricShakingConf();

	public static class EntityElectricShakingConf{
		//ConfigLoader.canEntityElectricShaking=true;
		@Config.LangKey("ap.showPotion.desc")
		@Config.Comment("ap.showPotion.desc")
		@Config.RequiresMcRestart
		public boolean ap_showPotion_desc=false;

		@Config.LangKey("ap.config.maxCountIs1.desc")
		@Config.Comment("ap.config.maxCountIs1.desc")
		@Config.RequiresMcRestart
		public boolean ap_maxIs1_desc=false;

		@Config.LangKey("ap.config.addLimit.desc")
		@Config.Comment("ap.config.addLimit.desc")
		@Config.RequiresMcRestart
		public int ap_addLimit_desc=1024;
	}
}
