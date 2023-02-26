package com.Hileb.add_potion.blocks.tileEntity;

import com.Hileb.add_potion.IdlFramework;
import com.Hileb.add_potion.blocks.BlockBase;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class BlockAutoAPCrafting extends BlockBase {
    public BlockAutoAPCrafting(String name){
        super("auto_ap_crafting", Material.ROCK);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileEntityAutoAPCrafting te=(TileEntityAutoAPCrafting)worldIn.getTileEntity(pos);

        if(!worldIn.isRemote){
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
        if (!worldIn.isRemote){
            TileEntityAutoAPCrafting te=(TileEntityAutoAPCrafting)worldIn.getTileEntity(pos);
            if (te!=null){
                if (!te.slotFood.getStackInSlot(0).isEmpty()){
                    Block.spawnAsEntity(worldIn,pos,te.slotFood.getStackInSlot(0));
                }
                if (!te.slotPotion.getStackInSlot(0).isEmpty()){
                    Block.spawnAsEntity(worldIn,pos,te.slotPotion.getStackInSlot(0));
                }
                for(int i=0;i<te.slotOut.getSlots();i++){
                    if (!te.slotOut.getStackInSlot(i).isEmpty()){
                        Block.spawnAsEntity(worldIn,pos,te.slotOut.getStackInSlot(i));
                    }
                }
            }
        }
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityAutoAPCrafting();
    }

}
