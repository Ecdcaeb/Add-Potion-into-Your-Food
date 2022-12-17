package com.Hileb.add_potion;

import com.Hileb.add_potion.gui.ModGuiElementLoader;
import com.Hileb.add_potion.init.ModRecipes;
import com.Hileb.add_potion.init.RegistryHandler;
import com.Hileb.add_potion.network.NetworkHandler;
import com.Hileb.add_potion.proxy.ProxyBase;
import com.Hileb.add_potion.util.Reference;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.Logger;

import static com.Hileb.add_potion.init.RegistryHandler.initRegistries;


@Mod(modid = IdlFramework.MODID, name = IdlFramework.NAME, version = IdlFramework.VERSION)//dependencies = "required-after:Forge@[14.23.5.2705,)"
public class IdlFramework {
    public static final String MODID = "add_potion";
    public static final String NAME = "Add Potion into Your food";
    public static final String VERSION = "1.0.0";

    public static Logger logger;

    public static final boolean SHOW_WARN = true;

    @Mod.Instance
    public static IdlFramework instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static ProxyBase proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();

        RegistryHandler.preInitRegistries(event);
        NetworkHandler.init();
        //cConfigLoader.init(event);

    }

    @EventHandler
    public static void Init(FMLInitializationEvent event) {
        ModRecipes.Init();
        RegisterTileEntity();
        initRegistries(event);
        new ModGuiElementLoader();

		LogWarning("%s has finished its initializations", MODID);

	}

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        TrashTalking();
        RegistryHandler.postInitReg();
    }

    @EventHandler
    public static void serverInit(FMLServerStartingEvent event) {
        RegistryHandler.serverRegistries(event);
    }


    private void TrashTalking() {
    }

    private static void RegisterTileEntity() {
    }

    public static void LogWarning(String str, Object... args) {
        if (SHOW_WARN) {
            logger.warn(String.format(str, args));
        }
    }

    public static void LogWarning(String str) {
        if (SHOW_WARN) {
            logger.warn(str);
        }
    }

    public static void Log(String str) {
//        if (ModConfig.GeneralConf.LOG_ON)
//        {
        logger.info(str);
//        }
    }

    public static void Log(String str, Object... args) {
//        if (ModConfig.GeneralConf.LOG_ON)
//        {
        logger.info(String.format(str, args));
//        }
    }
    public static String after_mod(String modid){
        return String.format(";after:%s", modid);
    }
}