package com.Hileb.add_potion.util.potion;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

public class APotion {
	public static final String NBT_POTION="com.hileb.ap.nbt.potion";
	public static final String NBT_TICK="com.hileb.ap.nbt.tick";
	public static final String NBT_LEVEL="com.hileb.ap.nbt.level";
	public static final String NBT_AB="com.hileb.ap.nbt.am";
	public static final String NBT_SHOW="com.hileb.ap.nbt.so";
	public final Potion potion;
	public final int tick;
	public final int level;
	public final boolean ambient;
	public final boolean showParticles;
	public APotion(PotionEffect effect) {
		potion=effect.getPotion();
		tick=effect.getDuration();
		level=effect.getAmplifier()+1;
		ambient=effect.getIsAmbient();
		showParticles=effect.doesShowParticles();
	}
	public APotion(Potion potionIn, int tickIn,int levelIn,boolean ambientIn,boolean showParticlesIn) {
		potion=potionIn;
		tick=tickIn;
		level=levelIn;
		ambient=ambientIn;
		showParticles=showParticlesIn;
	}

	public PotionEffect getEffect() {
		return new PotionEffect(potion,tick,level-1,ambient,showParticles);
	}
	public NBTTagCompound toNBT() {
		NBTTagCompound nbt=new NBTTagCompound();
		nbt.setString(NBT_POTION,potion.getRegistryName().toString());
		nbt.setInteger(NBT_TICK,tick);
		nbt.setInteger(NBT_LEVEL,level);
		nbt.setBoolean(NBT_AB,ambient);
		nbt.setBoolean(NBT_SHOW,showParticles);
		return nbt;
	}
	public static APotion fromNBT(NBTTagCompound nbt) {
		return new APotion(
				Potion.getPotionFromResourceLocation(nbt.getString(NBT_POTION)),
				nbt.getInteger(NBT_TICK),
				nbt.getInteger(NBT_LEVEL),
				nbt.getBoolean(NBT_AB),
				nbt.getBoolean(NBT_SHOW)
		);
	}
	public boolean isEmpty() {
		return potion==null || potion.getRegistryName() ==null;
	}
}
