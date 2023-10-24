package com.Hileb.add_potion.event;

import com.Hileb.add_potion.util.potion.APotion;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.Event;

import java.util.List;

public abstract class APCraftEvent extends Event {
    public ItemStack foodStack;
    public ItemStack potionStack;
    public Player player;
    public APCraftEvent(Player playerIn,ItemStack foodStackIn, ItemStack potionStackIn){
        player=playerIn;
        foodStack=foodStackIn;
        potionStack=potionStackIn;
    }


    @Override
    public abstract boolean isCancelable();

    public static class Pre extends APCraftEvent{
        public Pre(Player playerIn,ItemStack foodStackIn, ItemStack potionStackIn){
            super(playerIn,foodStackIn,potionStackIn);
        }

        @Override
        public boolean isCancelable() {
            return true;
        }
    }
    public static class Post extends APCraftEvent{
        public Post(Player playerIn,ItemStack foodStackIn, ItemStack potionStackIn){
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
        public GetAPotion(ItemStack stackIn,List<APotion> aPotionsIn){
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
        public ItemStack getStack(){
            return stack;
        }
    }

}
