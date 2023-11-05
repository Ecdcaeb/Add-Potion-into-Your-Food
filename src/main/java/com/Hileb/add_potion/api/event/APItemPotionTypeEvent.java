package com.Hileb.add_potion.api.event;

import com.Hileb.add_potion.common.util.APUtils;
import net.minecraft.world.item.PotionItem;
import net.minecraftforge.eventbus.api.Event;

/**
 * This Event is fired when get potion type of a potion item and apply it to an effect.
 * @see com.Hileb.add_potion.common.util.APUtils#getPotionTypeOfPotionItem
 */
public class APItemPotionTypeEvent extends Event {
	private final PotionItem potionItem;

	private APUtils.PotionType result = APUtils.PotionType.DEFAULT;

	public APItemPotionTypeEvent(PotionItem potionItem) {
		this.potionItem = potionItem;
	}

	public PotionItem getPotionItem() {
		return this.potionItem;
	}

	public void setPotionType(APUtils.PotionType result) {
		this.result = result;
	}

	public APUtils.PotionType getPotionType() {
		return this.result;
	}
}
