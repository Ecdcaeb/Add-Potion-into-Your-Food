package com.Hileb.add_potion.common.init;

import com.google.common.collect.Maps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;

import static com.Hileb.add_potion.AddPotion.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ModSounds {
	static final Map<ResourceLocation, SoundEvent> registeredEvents = Maps.newHashMap();
	public static final SoundEvent VILLAGER_WORK_APOTHECARY = registerSound("entity.villager.work_apothecary");

	@SuppressWarnings("SameParameterValue")
	private static SoundEvent registerSound(String name) {
		ResourceLocation location = new ResourceLocation(MODID, name);
		SoundEvent event = new SoundEvent(location);
		registeredEvents.put(location, event);
		return event;
	}

	@SubscribeEvent
	public static void init(RegistryEvent.Register<SoundEvent> event) {
		registeredEvents.forEach((id, sound) -> {
			sound.setRegistryName(id);
			event.getRegistry().register(sound);
		});
	}
}
