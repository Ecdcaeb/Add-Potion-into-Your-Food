package com.Hileb.add_potion.recipe;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.NonNullList;

import java.util.ArrayList;
import java.util.List;

public class RecipePutrid extends ShapelessRecipes {
    public static List<Item> PutridItems=new ArrayList<Item>();
    public RecipePutrid(Item item ,Item card, Item result) {
        super("suit",new ItemStack(result),getlist(card,item));

        //setRegistryName(Objects.requireNonNull(result.getRegistryName()));
    }
    public static NonNullList<Ingredient> getlist(Item card,Item result){
        NonNullList<Ingredient> list=NonNullList.create();
        list.add(Ingredient.fromItem(card));
        list.add(Ingredient.fromItem(result));
        return list;
    }
}
