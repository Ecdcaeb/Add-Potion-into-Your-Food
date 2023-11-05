package com.Hileb.add_potion;

import com.Hileb.add_potion.common.init.ModMenuTypes;
import com.Hileb.add_potion.common.util.compat.LoadMods;
import com.Hileb.add_potion.common.init.ModBlockEntities;
import com.Hileb.add_potion.common.init.ModBlocks;
import com.Hileb.add_potion.common.init.ModItems;
import com.Hileb.add_potion.common.proxy.ClientProxy;
import com.Hileb.add_potion.common.proxy.ProxyBase;
import com.Hileb.add_potion.common.proxy.ServerProxy;
import com.Hileb.add_potion.common.util.ModLogger;
import com.Hileb.add_potion.common.world.Villages;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;
import org.apache.logging.log4j.LogManager;

import java.util.function.Supplier;


@Mod(AddPotion.MODID)
public class AddPotion {
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

	public AddPotion() {
		ModLogger.logger = LogManager.getLogger(MODID);

		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		MinecraftForge.EVENT_BUS.addListener(this::setup);

		ModBlocks.init(bus);
		ModItems.init(bus);
		ModBlockEntities.init(bus);
		ModMenuTypes.init(bus);
		Villages.Registers.init(bus);
	}

	public void setup(FMLCommonSetupEvent event) {
		LoadMods.init();

		ModLogger.LogInfo("%s has finished its initializations", MODID);
	}
}