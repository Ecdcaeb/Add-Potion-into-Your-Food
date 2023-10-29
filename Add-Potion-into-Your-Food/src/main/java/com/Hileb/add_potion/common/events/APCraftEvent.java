package com.Hileb.add_potion.common.events;


import com.Hileb.add_potion.common.potion.APotion;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.Event;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class APCraftEvent extends Event {
    /**
     * player may be null ,this means auto craft.
     */
    @Nullable
    public Player player;
    public APCraftEvent(Player playerIn){
        player=playerIn;
    }


    @Override
    public abstract boolean isCancelable();

    public static class Pre extends APCraftEvent{
        public ItemStack foodStack;
        public ItemStack potionStack;
        public Pre(Player playerIn,ItemStack foodStackIn, ItemStack potionStackIn){
            super(playerIn);
            foodStack=foodStackIn;
            potionStack=potionStackIn;
        }

        @Override
        public boolean isCancelable() {
            return true;
        }
    }
    public static class Post extends APCraftEvent{
        public ItemStack result;
        public Post(Player playerIn,ItemStack resultIn){
            super(playerIn);
            result=resultIn;
        }

        @Override
        public boolean isCancelable() {
            return false;
        }
    }
    public static class GetAPotion extends APCraftEvent{
        public List<APotion> aPotions;
        public ItemStack stack;
        public GetAPotion(ItemStack stackIn, List<APotion> aPotionsIn){
            super(null);
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
