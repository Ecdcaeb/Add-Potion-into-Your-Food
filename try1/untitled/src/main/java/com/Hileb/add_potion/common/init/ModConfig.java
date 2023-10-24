package com.Hileb.add_potion.common.init;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import org.apache.commons.lang3.tuple.Pair;


public class ModConfig {

    public final ForgeConfigSpec.ConfigValue<Boolean> showDesc;
    public final ForgeConfigSpec.ConfigValue<Boolean> maxCountIs1;
    public final ForgeConfigSpec.ConfigValue<Boolean> openArmor;
    public final ForgeConfigSpec.ConfigValue<Integer> addLimit;

    public ModConfig(ForgeConfigSpec.Builder builder){
        builder.push("general");

        showDesc = builder.comment("ap.showPotion.desc").translation("ap.showPotion.desc").define("ap.showPotion.desc", false);
        maxCountIs1= builder.comment("ap.config.maxCountIs1.desc").translation("ap.config.maxCountIs1.desc").define("ap.config.maxCountIs1.desc", true);
        addLimit= builder.comment("ap.config.addLimit.desc").translation("ap.config.addLimit.desc").define("ap.config.addLimit.desc", 1024);
        openArmor=builder.comment("ap.config.openArmor.desc").translation("ap.config.openArmor.desc").define("ap.config.openArmor.desc", true);

        builder.pop();
    }

    public static ModConfig INSTANCE;
    public static ModConfig init() {
        Pair<ModConfig, ForgeConfigSpec> pair = new ForgeConfigSpec.Builder()
                .configure(ModConfig::new);
        INSTANCE=pair.getLeft();
        ModLoadingContext.get().registerConfig(net.minecraftforge.fml.config.ModConfig.Type.COMMON, pair.getRight());
        return INSTANCE;
    }
}
