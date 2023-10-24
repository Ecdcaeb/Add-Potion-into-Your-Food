package com.Hileb.add_potion.gui.potion.expOne;

import net.minecraftforge.fml.common.Loader;

public class LoadMods {
    public static boolean rustic;
    public static boolean botania;
    public static boolean potionCore;
    public static boolean liquidenchanting;
    public static void init(){
        rustic= Loader.isModLoaded("rustic");
        botania=Loader.isModLoaded("botania");
        potionCore=Loader.isModLoaded("potioncore");
        liquidenchanting=Loader.isModLoaded("liquidenchanting");
    }
}
