package com.Hileb.add_potion.meta;

import com.Hileb.add_potion.item.ModItems;
import com.Hileb.add_potion.recipe.RecipePutrid;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Loader;

public class MetaUtil {

    public static void loadmodload(){
    }
    public static void modLoadInit(){
    }

    public static int GetModCount()
    {
        return Loader.instance().getActiveModList().size();
    }
}
