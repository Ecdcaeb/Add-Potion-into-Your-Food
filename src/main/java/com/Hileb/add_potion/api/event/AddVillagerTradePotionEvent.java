package com.Hileb.add_potion.api.event;

import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.eventbus.api.Event;

import java.util.List;

/**
 * This Event is fired to register beneficial and harmful potions added by other mods.
 * @see com.Hileb.add_potion.common.world.ModTrades
 */
public class AddVillagerTradePotionEvent extends Event {
	private final List<Potion> beneficial;
	private final List<Potion> harmful;

	public AddVillagerTradePotionEvent(List<Potion> beneficial, List<Potion> harmful) {
		this.beneficial = beneficial;
		this.harmful = harmful;
	}

	public List<Potion> getBeneficial() {
		return this.beneficial;
	}

	public List<Potion> getHarmful() {
		return this.harmful;
	}
}
