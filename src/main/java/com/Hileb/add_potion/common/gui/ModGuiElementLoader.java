package com.Hileb.add_potion.common.gui;

import com.Hileb.add_potion.IdlFramework;
import com.Hileb.add_potion.common.gui.potion.expOne.ContainerDemo;
import com.Hileb.add_potion.common.gui.potion.expOne.GuiContainerDemo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import javax.annotation.Nullable;

public class ModGuiElementLoader implements IGuiHandler {

	public static final int GUI_DEMO = 1;

	public ModGuiElementLoader()
	{
		NetworkRegistry.INSTANCE.registerGuiHandler(IdlFramework.instance, this);
	}

	@Nullable
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch (ID)
		{
			case GUI_DEMO:
				return new ContainerDemo(player);
				default:
					return null;
		}
	}

	@Nullable
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch (ID)
		{
			case GUI_DEMO:
				return new GuiContainerDemo(player,new ContainerDemo(player));
			default:
				return null;
		}
	}
}
