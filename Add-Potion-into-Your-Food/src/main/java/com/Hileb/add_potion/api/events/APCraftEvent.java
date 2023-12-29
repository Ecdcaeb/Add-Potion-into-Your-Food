package com.Hileb.add_potion.api.events;


import com.Hileb.add_potion.common.potion.APotion;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.Event;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Dirt BOTH<p></p>
 * Side Server<p></p>
 * Bus {@link net.minecraftforge.common.MinecraftForge#EVENT_BUS}<p></p>
 * fire before {@link Pre} or after {@link Post} or when get APotion {@link GetAPotion}<p></p>
 * @author Hileb
 **/
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

    /**
     * Cancelable true
     * **/
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
    /**
     * Cancelable false
     * **/
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
    /**
     * Cancelable false
     * **/
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
