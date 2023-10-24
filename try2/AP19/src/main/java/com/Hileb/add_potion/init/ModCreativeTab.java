package com.Hileb.add_potion.init;


import com.Hileb.add_potion.AP19Main;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import org.checkerframework.checker.units.qual.C;

import java.util.Collection;

public class ModCreativeTab {
	public static final CreativeModeTab IDL_MISC =CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0).title(Component.translatable("itemGroup.apMiscTab")).icon(() -> {
        return new ItemStack(Blocks.BRICKS);
    }).displayItems((p_270425_, p_260158_) -> {
        p_260158_.accept(AP19Main.EXAMPLE_BLOCK_ITEM.get());
    }).build();
}
