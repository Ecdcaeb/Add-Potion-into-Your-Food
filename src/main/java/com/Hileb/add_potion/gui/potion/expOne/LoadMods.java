package com.Hileb.add_potion.gui.potion.expOne;

public class LoadMods {
	public static boolean rustic;
	public static boolean botania;
	public static void init() {
		rustic = Loader.isModLoaded("rustic");
		botania = Loader.isModLoaded("botania");
	}
}
