package com.Hileb.add_potion;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Forge's config APIs
@Mod.EventBusSubscriber(modid = AddPotion.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class APConfig
{
    private static final ForgeConfigSpec.Builder BUILDER=new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec.BooleanValue AP_MAX_IS_1=BUILDER
            .comment("is slot max item count once is 1")
            .define("ap_maxIs1_desc",false);
    public static final ForgeConfigSpec.IntValue AP_ADDITION_LIMIT= BUILDER
            .comment("the max count of potion of one item")
            .defineInRange("ap_addLimit_desc",1024,0,Integer.MAX_VALUE);

    public static final ForgeConfigSpec.BooleanValue AP_SHOWTOOLTIP=BUILDER
            .comment("should show tooltip of item for client")
            .define("ap_showPotion_desc",false);
    public static final ForgeConfigSpec SPEC = BUILDER.build();


    public static boolean ap_showPotion_desc=false;

    public static boolean ap_maxIs1_desc=false;

    public static int ap_addLimit_desc=1024;

    @SubscribeEvent
    public static void onLoad(final ModConfigEvent event)
    {
        ap_maxIs1_desc=AP_MAX_IS_1.get();
        ap_showPotion_desc=AP_SHOWTOOLTIP.get();
        ap_addLimit_desc=AP_ADDITION_LIMIT.get();
    }
}
