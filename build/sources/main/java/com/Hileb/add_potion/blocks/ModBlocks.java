package com.Hileb.add_potion.blocks;

import com.Hileb.add_potion.IdlFramework;
import com.Hileb.add_potion.blocks.tileEntity.BlockAutoAPCrafting;
import com.Hileb.add_potion.blocks.tileEntity.TileEntityAutoAPCrafting;
import com.Hileb.add_potion.gui.ModGuiElementLoader;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class ModBlocks {
	public static final List<Block> BLOCKS = new ArrayList<Block>();

	public static final Block BLOCK_BLACK_STONE_BRICK=new BlockBase("blackstonebrick", Material.ROCK){
		@Override
		public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
			playerIn.openGui(IdlFramework.instance, ModGuiElementLoader.GUI_DEMO,playerIn.world,(int) playerIn.posX,(int)playerIn.posY,(int)playerIn.posZ);
			return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
		}
	};
	public static final Block BLOCK_AUTO_AP_CRAFTING=new BlockAutoAPCrafting("block_auto_ap_crafting");


}
