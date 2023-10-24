package com.Hileb.add_potion;

import com.Hileb.add_potion.common.block.BlockAPCrafting;
import com.Hileb.add_potion.common.block.TileEntityBlockCrafting;
import com.Hileb.add_potion.common.container.ContainerAP;
import com.Hileb.add_potion.client.gui_screen.GuiContainerAP;
import com.Hileb.add_potion.common.init.ModConfig;
import com.Hileb.add_potion.network.NetWorkHandler;
import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(AddPotionMain.MODID)
public class AddPotionMain {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "add_potion";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    // Create a Deferred Register to hold Blocks which will all be registered under the "untitled" namespace
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITY_TYPES=DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MODID);
    // Create a Deferred Register to hold Items which will all be registered under the "untitled" namespace
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, MODID);


    // Creates a new Block with the id "untitled:example_block", combining the namespace and path
    public static final RegistryObject<Block> BLOCK_AP_CRAFTING_TABLE = BLOCKS.register("block_ap_crafting_table", BlockAPCrafting::new);
    // Creates a new BlockItem with the id "untitled:example_block", combining the namespace and path
    public static final RegistryObject<Item> ITEM_BLOCK_AP_CRAFTING_TABLE  = ITEMS.register("item_block_ap_crafting_table", () -> new BlockItem(BLOCK_AP_CRAFTING_TABLE.get(), new Item.Properties()));

    public static final RegistryObject<BlockEntityType<TileEntityBlockCrafting>> TILE_ENTITY_BLOCK_AP_CRAFTING_TABLE = TILE_ENTITY_TYPES.register("tile_entity_block_ap_crafting_table", () -> BlockEntityType.Builder.of(TileEntityBlockCrafting::new, BLOCK_AP_CRAFTING_TABLE.get()).build(null));
    public static final RegistryObject<MenuType<ContainerAP>> CONTAINER_AP = MENUS.register("container_ap", () ->new MenuType<>(ContainerAP::new, FeatureFlags.DEFAULT_FLAGS));

    public static ModConfig CONFIG;
    public AddPotionMain() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading

        // Register the Deferred Register to the mod event bus so blocks get registered
        BLOCKS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so items get registered
        ITEMS.register(modEventBus);
        TILE_ENTITY_TYPES.register(modEventBus);
        MENUS.register(modEventBus);


        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        modEventBus.addListener(AddPotionMain::onCreativeTab);


        CONFIG= ModConfig.init();
        NetWorkHandler.init();
    }

    public static void onCreativeTab(CreativeModeTabEvent.BuildContents event){
        if (event.getTab()== CreativeModeTabs.FUNCTIONAL_BLOCKS){
            event.accept(new ItemStack(ITEM_BLOCK_AP_CRAFTING_TABLE.get()));
        }
    }





    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {

            event.enqueueWork(()-> MenuScreens.register(CONTAINER_AP.get(),GuiContainerAP::new));
        }
    }
}
