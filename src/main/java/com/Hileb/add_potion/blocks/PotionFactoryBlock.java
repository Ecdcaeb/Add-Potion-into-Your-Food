package com.Hileb.add_potion.blocks;

import com.Hileb.add_potion.blocks.tileEntity.PotionFactoryBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class PotionFactoryBlock extends BaseEntityBlock {
	public PotionFactoryBlock(Properties props) {
		super(props);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		PotionFactoryBlockEntity te=(PotionFactoryBlockEntity)worldIn.getTileEntity(pos);

		if(!worldIn.isRemote) {
			ItemStack stack=te.slotPotion.getStackInSlot(0);
			playerIn.sendMessage(new TextComponentString(TextFormatting.RED+"itemSlot:potion"));
			if (stack.isEmpty())playerIn.sendMessage(new TextComponentString("empty"));
			else {
				playerIn.sendMessage(new TextComponentString(String.format("item:%s#%d",stack.getItem().getRegistryName().toString(),stack.getMetadata())));
				playerIn.sendMessage(new TextComponentString(String.format("count:%d",stack.getCount())));
			}

			stack=te.slotFood.getStackInSlot(0);
			playerIn.sendMessage(new TextComponentString(TextFormatting.RED+"itemSlot:food"));
			if (stack.isEmpty())playerIn.sendMessage(new TextComponentString("empty"));
			else {
				playerIn.sendMessage(new TextComponentString(String.format("item:%s#%d",stack.getItem().getRegistryName().toString(),stack.getMetadata())));
				playerIn.sendMessage(new TextComponentString(String.format("count:%d",stack.getCount())));
			}

			stack=te.slotOut.getStackInSlot(0);
			playerIn.sendMessage(new TextComponentString(TextFormatting.RED+"itemSlot:out"));
			if (stack.isEmpty())playerIn.sendMessage(new TextComponentString("empty"));
			else {
				playerIn.sendMessage(new TextComponentString(String.format("item:%s#%d",stack.getItem().getRegistryName().toString(),stack.getMetadata())));
				playerIn.sendMessage(new TextComponentString(String.format("count:%d",stack.getCount())));
			}

			playerIn.sendMessage(new TextComponentString(TextFormatting.RED+"outing stacks:"+(int)(te.slotOut.getStackInSlot(1).getCount()+te.slotOut.getStackInSlot(2).getCount())));
		}


		return true;
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		if (!worldIn.isRemote) {
			PotionFactoryBlockEntity te=(PotionFactoryBlockEntity)worldIn.getTileEntity(pos);
			if (te!=null) {
				if (!te.slotFood.getStackInSlot(0).isEmpty()) {
					Block.spawnAsEntity(worldIn,pos,te.slotFood.getStackInSlot(0));
				}
				if (!te.slotPotion.getStackInSlot(0).isEmpty()) {
					Block.spawnAsEntity(worldIn,pos,te.slotPotion.getStackInSlot(0));
				}
				for(int i=0;i<te.slotOut.getSlots();i++) {
					if (!te.slotOut.getStackInSlot(i).isEmpty()) {
						Block.spawnAsEntity(worldIn,pos,te.slotOut.getStackInSlot(i));
					}
				}
			}
		}
	}

	@Nullable
	@Override
	public PotionFactoryBlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
		return new PotionFactoryBlockEntity(blockPos, blockState);
	}

}
