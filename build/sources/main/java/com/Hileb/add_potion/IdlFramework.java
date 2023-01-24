package com.Hileb.add_potion;

import com.Hileb.add_potion.gui.ModGuiElementLoader;
import com.Hileb.add_potion.gui.potion.expOne.LoadMods;
import com.Hileb.add_potion.init.RegistryHandler;
import com.Hileb.add_potion.network.NetworkHandler;
import com.Hileb.add_potion.proxy.ProxyBase;
import com.Hileb.add_potion.util.Reference;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.Logger;



@Mod(modid = IdlFramework.MODID, name = IdlFramework.NAME, version = IdlFramework.VERSION,dependencies = "after:botania;after:rustic")//dependencies = "required-after:Forge@[14.23.5.2705,)"
public class IdlFramework {
    public static final String MODID = "add_potion";
    public static final String NAME = "Add Potion into Your food";
    public static final String VERSION = "1.0.1.4";

    public static Logger logger;

    public static final boolean SHOW_WARN = true;

    @Mod.Instance
    public static IdlFramework instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static ProxyBase proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();

        NetworkHandler.init();

    }

    @EventHandler
    public static void Init(FMLInitializationEvent event) {
        new ModGuiElementLoader();
        LoadMods.init();

		LogWarning("%s has finished its initializations", MODID);

	}



    public static void LogWarning(String str, Object... args) {
        if (SHOW_WARN) {
            logger.warn(String.format(str, args));
        }
    }
}