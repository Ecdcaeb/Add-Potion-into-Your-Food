package com.Hileb.add_potion.common;

import com.Hileb.add_potion.AddPotion;
import com.Hileb.add_potion.common.block.BlockAPCrafting;
import com.Hileb.add_potion.common.container.ContainerAP;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
@Project Add-Potion-into-Your-Food
@Author Hileb
@Date 2023/10/22 10:16
**/
public class APRegisterHandler {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, AddPotion.MODID);
    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITY_TYPES=DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, AddPotion.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, AddPotion.MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB_DEFERRED_REGISTER=DeferredRegister.create(Registries.CREATIVE_MODE_TAB, AddPotion.MODID);
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, AddPotion.MODID);
    public static final RegistryObject<Block> BLOCK_AP_CRAFTING_TABLE = BLOCKS.register("block_ap_crafting_table", BlockAPCrafting::new);
    public static final RegistryObject<Item> ITEM_BLOCK_AP_CRAFTING_TABLE  = ITEMS.register("item_block_ap_crafting_table", () -> new BlockItem(BLOCK_AP_CRAFTING_TABLE.get(), new Item.Properties()));

    //public static final RegistryObject<BlockEntityType<TileEntityBlockCrafting>> TILE_ENTITY_BLOCK_AP_CRAFTING_TABLE = TILE_ENTITY_TYPES.register("tile_entity_block_ap_crafting_table", () -> BlockEntityType.Builder.of(TileEntityBlockCrafting::new, BLOCK_AP_CRAFTING_TABLE.get()).build(null));
    public static final RegistryObject<MenuType<ContainerAP>> CONTAINER_AP = MENUS.register("container_ap", () ->new MenuType<>(ContainerAP::new, FeatureFlags.DEFAULT_FLAGS));


    public static final RegistryObject<CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TAB_DEFERRED_REGISTER.register("ap_tab", () -> CreativeModeTab.builder()
            .icon(() ->ITEM_BLOCK_AP_CRAFTING_TABLE.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(ITEM_BLOCK_AP_CRAFTING_TABLE.get()); // Add the example item to the tab. For your own tabs, this method is preferred over the event
            }).build());
    public static void addToBus(IEventBus bus){
        BLOCKS.register(bus);
        TILE_ENTITY_TYPES.register(bus);
        ITEMS.register(bus);
        MENUS.register(bus);
        CREATIVE_MODE_TAB_DEFERRED_REGISTER.register(bus);
    }

}
