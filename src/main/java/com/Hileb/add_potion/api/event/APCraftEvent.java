package com.Hileb.add_potion.api.event;

import com.Hileb.add_potion.common.util.potion.APotion;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.Event;

import java.util.List;

public abstract class APCraftEvent extends Event {
	public ItemStack foodStack;
	public ItemStack potionStack;
	public EntityPlayer player;
	public APCraftEvent(EntityPlayer playerIn,ItemStack foodStackIn, ItemStack potionStackIn) {
		player=playerIn;
		foodStack=foodStackIn;
		potionStack=potionStackIn;
	}


	@Override
	public abstract boolean isCancelable();

	public static class Pre extends APCraftEvent{
		public Pre(EntityPlayer playerIn,ItemStack foodStackIn, ItemStack potionStackIn) {
			super(playerIn,foodStackIn,potionStackIn);
		}

		@Override
		public boolean isCancelable() {
			return true;
		}
	}
	public static class Post extends APCraftEvent{
		public Post(EntityPlayer playerIn,ItemStack foodStackIn, ItemStack potionStackIn) {
			super(playerIn,foodStackIn,potionStackIn);
		}

		@Override
		public boolean isCancelable() {
			return false;
		}
	}
	public static class GetAPotion extends APCraftEvent{
		public List<APotion> aPotions;
		public ItemStack stack;
		public GetAPotion(ItemStack stackIn,List<APotion> aPotionsIn) {
			super(null,null,null);
			/**
			 * if you use it
			 * you can't use itemStack that is not "stack".For example:food,potion,player
			 * Because NPE(null);
			 */
			stack=stackIn;
			aPotions=aPotionsIn;
		}
		@Override
		public boolean isCancelable() {
			return false;
		}
		public ItemStack getStack() {
			return stack;
		}
	}

}
