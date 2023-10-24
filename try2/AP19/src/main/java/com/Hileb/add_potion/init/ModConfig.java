package com.Hileb.add_potion.init;

import com.Hileb.add_potion.util.Reference;
import net.minecraftforge.fml.common.Mod;


@Conf
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

        @Config.LangKey("ap.config.openArmor.desc")
        @Config.Comment("ap.config.openArmor.desc")
        @Config.RequiresMcRestart
        public boolean openArmor=false;
    }
}
