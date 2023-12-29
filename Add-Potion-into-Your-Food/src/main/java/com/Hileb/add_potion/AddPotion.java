package com.Hileb.add_potion;

import com.Hileb.add_potion.api.AddPotionRegistries;
import com.Hileb.add_potion.client.gui_screen.GuiContainerAP;
import com.Hileb.add_potion.common.APRegisterHandler;
import com.Hileb.add_potion.common.init.OnEat;
import com.Hileb.add_potion.common.potion.PotionUtil;
import com.Hileb.add_potion.network.NetWorkHandler;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.Hileb.add_potion.common.APRegisterHandler.CONTAINER_AP;

@Mod(AddPotion.MODID)
public class AddPotion {
    public static final String MODID = "add_potion";
    public static final Logger LOGGER= LoggerFactory.getLogger(MODID);

    static {
        AddPotionRegistries.callForClassLoad();
    }
    public AddPotion() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);
        APRegisterHandler.addToBus(modEventBus);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, APConfig.SPEC);
        NetWorkHandler.init();
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        buildInRegister();
        MinecraftForge.EVENT_BUS.register(OnEat.class);
    }
    @OnlyIn(Dist.CLIENT)
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            event.enqueueWork(()-> MenuScreens.register(CONTAINER_AP.get(), GuiContainerAP::new));
        }
    }
    public static void buildInRegister(){
        //vanilla
        AddPotionRegistries.addBoth(PotionUtil.BuildInUtils.RegisterObject.Vanilla.BOTH);
        AddPotionRegistries.addFood(PotionUtil.BuildInUtils.RegisterObject.Vanilla.FOOD_ONLY);
        //
        AddPotionRegistries.addGetter(new ResourceLocation(MODID,"internal_getter"),PotionUtil.BuildInUtils.RegisterObject.Vanilla.AP_EFFECT);
        AddPotionRegistries.addGetter(new ResourceLocation(MODID,"vanilla_getter"),PotionUtil.BuildInUtils.RegisterObject.Vanilla.VANILLA);
    }
}
