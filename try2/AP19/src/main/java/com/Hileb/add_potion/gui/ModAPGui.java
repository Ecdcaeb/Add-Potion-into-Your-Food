package com.Hileb.add_potion.gui;


import com.Hileb.add_potion.AP19Main;
import com.Hileb.add_potion.gui.potion.expOne.ContainerDemo;
import com.Hileb.add_potion.gui.potion.expOne.GuiContainerDemo;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

public class ModAPGui {
    public static final RegistryObject<MenuType<ContainerDemo>> GUI_SERVER_POTION= AP19Main.GUI_SERVER.register("potion_gui",()-> new MenuType<>(ContainerDemo::new, FeatureFlags.DEFAULT_FLAGS));

}
