package com.Hileb.add_potion;

import com.Hileb.add_potion.common.init.*;
import com.Hileb.add_potion.common.proxy.ClientProxy;
import com.Hileb.add_potion.common.proxy.ProxyBase;
import com.Hileb.add_potion.common.proxy.ServerProxy;
import com.Hileb.add_potion.common.util.ModLogger;
import com.Hileb.add_potion.common.util.compat.LoadMods;
import com.Hileb.add_potion.common.world.ModTrades;
import com.Hileb.add_potion.common.world.Villages;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.registries.RegisterEvent;
import org.apache.logging.log4j.LogManager;

import java.util.function.Supplier;

@SuppressWarnings("unused")
@Mod(AddPotion.MODID)
public class AddPotion {
	public static final String MODID = "add_potion";
	public static final String NAME = "Add Potion into Your food";
	public static final String VERSION = ModList.get().getModFileById(MODID).versionString();

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
		bus.addListener(this::setup);
		bus.addListener(this::onRegister);
		bus.addListener(ModCreativeTab::creativeTabEvent);

		MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGHEST, this::onServerAboutToStart);

		ModBlocks.init(bus);
		ModItems.init(bus);
		ModBlockEntities.init(bus);
		ModMenuTypes.init(bus);
		Villages.Registers.init(bus);

		MinecraftForge.EVENT_BUS.register(this);
	}

	public void onRegister(RegisterEvent event) {
		ModSounds.init(event);
	}

	public void setup(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			LoadMods.init();
			Villages.init();

			ModLogger.LogInfo("%s has finished its initializations", MODID);
		});
	}

	public void onServerAboutToStart(ServerAboutToStartEvent event) {
		ModTrades.init();
	}
}