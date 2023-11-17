package com.Hileb.add_potion.common.init;

import com.google.common.collect.Maps;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.RegisterEvent;

import java.util.Map;

import static com.Hileb.add_potion.AddPotion.MODID;

public final class ModSounds {
	static final Map<ResourceLocation, SoundEvent> registeredEvents = Maps.newHashMap();
	public static final SoundEvent VILLAGER_WORK_APOTHECARY = registerSound("entity.villager.work_apothecary");

	@SuppressWarnings("SameParameterValue")
	private static SoundEvent registerSound(String name) {
		ResourceLocation location = new ResourceLocation(MODID, name);
		SoundEvent event = SoundEvent.createVariableRangeEvent(location);
		registeredEvents.put(location, event);
		return event;
	}

	public static void init(RegisterEvent event) {
		event.register(Registries.SOUND_EVENT, helper -> registeredEvents.forEach(helper::register));
	}
}
