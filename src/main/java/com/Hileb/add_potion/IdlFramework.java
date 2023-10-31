package com.Hileb.add_potion;

import com.Hileb.add_potion.gui.ModGuiElementLoader;
import com.Hileb.add_potion.gui.potion.expOne.LoadMods;
import com.Hileb.add_potion.init.ModBlockEntities;
import com.Hileb.add_potion.init.ModBlocks;
import com.Hileb.add_potion.init.ModItems;
import com.Hileb.add_potion.network.NetworkHandler;
import com.Hileb.add_potion.proxy.ClientProxy;
import com.Hileb.add_potion.proxy.ProxyBase;
import com.Hileb.add_potion.proxy.ServerProxy;
import com.Hileb.add_potion.util.ModLogger;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;
import org.apache.logging.log4j.LogManager;

import java.util.function.Supplier;


@Mod(IdlFramework.MODID)
public class IdlFramework {
	public static final String MODID = "add_potion";
	public static final String NAME = "Add Potion into Your food";
	public static final String VERSION = "1.2.0.5";



	public static <T> Supplier<T> bootstrapErrorToXCPInDev(Supplier<T> in) {
		if(FMLLoader.isProduction()) {
			return in;
		}
		return () -> {
			try {
				return in.get();
			} catch(BootstrapMethodError e) {
				throw new RuntimeException(e);
			}
		};
	}

	public static final ProxyBase proxy = DistExecutor.safeRunForDist(
			bootstrapErrorToXCPInDev(() -> ClientProxy::new),
			bootstrapErrorToXCPInDev(() -> ServerProxy::new)
	);

	public IdlFramework() {
		ModLogger.logger = LogManager.getLogger(MODID);

		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		MinecraftForge.EVENT_BUS.addListener(this::setup);

		ModBlocks.init(bus);
		ModItems.init(bus);
		ModBlockEntities.init(bus);
	}

	public void setup(FMLCommonSetupEvent event) {
		new ModGuiElementLoader();
		LoadMods.init();

		NetworkHandler.init();

		ModLogger.LogWarning("%s has finished its initializations", MODID);
	}
}