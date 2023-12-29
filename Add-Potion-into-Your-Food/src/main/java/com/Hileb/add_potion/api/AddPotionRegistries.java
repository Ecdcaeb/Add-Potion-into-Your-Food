package com.Hileb.add_potion.api;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * @Project Add-Potion-into-Your-Food
 * @Author Hileb
 * @Date 2023/10/22 11:15
 **/
@SuppressWarnings("all")
public class AddPotionRegistries {

    @Deprecated(since = "use method")
    public static final LinkedList<Adjuster> FOODS=new LinkedList<>();
    @Deprecated(since = "use method")
    public static final LinkedList<Adjuster> POTION=new LinkedList<>();
    @Deprecated(since = "use method")
    public static final HashMap<ResourceLocation,IPotionGetter> POTION_GETTERS=new HashMap<>();

    /**
     * register the {@link IPotionGetter} into add_potion
     * **/
    public static void addGetter(ResourceLocation location,IPotionGetter iPotionGetter){
        POTION_GETTERS.put(location,iPotionGetter);
    }


    /**
     * register the {@link Adjuster} into add_potion to interference the judgment of food
     * **/
    public static void addFood(Adjuster stackPredicate){
        FOODS.add(stackPredicate);
    }
    /**
     * register the {@link Adjuster} into add_potion to interference the judgment of potion
     * **/
    public static void addPotion(Adjuster stackPredicate){
        POTION.add(stackPredicate);
    }
    /**
     * use both {@link AddPotionRegistries#addPotion(Adjuster)}{@link AddPotionRegistries#addFood(Adjuster)}
     * **/
    public static void addBoth(Adjuster stackPredicate){
        POTION.add(stackPredicate);
        FOODS.add(stackPredicate);
    }
    /**
     * the result of {@link Adjuster}
     * **/
    public enum Result{
        PASS,
        APPLY,
        REJECT;
    }
    @FunctionalInterface
    public interface Adjuster{
        Result test(@Nullable Player player, @Nonnull ItemStack stack);
    }
    public static void callForClassLoad(){}
}
