package com.Hileb.add_potion.common.init;

import net.minecraftforge.common.ForgeConfigSpec;

public class ModCommonConfig {
	private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	private static final ForgeConfigSpec SPEC;

	static {
		BUILDER.push("add-potion-common-config");

		BUILDER.pop();

		SPEC = BUILDER.build();
	}

	public static ForgeConfigSpec getConfig() {
		return SPEC;
	}
}
