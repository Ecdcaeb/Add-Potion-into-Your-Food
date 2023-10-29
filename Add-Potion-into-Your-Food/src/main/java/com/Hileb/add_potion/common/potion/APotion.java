package com.Hileb.add_potion.common.potion;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.registries.ForgeRegistries;

public class APotion {
    public final CompoundTag nbt;
    public APotion(MobEffectInstance effect){
        nbt=effect.save(new CompoundTag());
    }
    public APotion(CompoundTag effect){
        nbt=effect.copy();
    }
    public MobEffectInstance getEffect(){
        return MobEffectInstance.load(nbt);
    }
    public CompoundTag toNBT(){
        return nbt;
    }
    public static APotion fromNBT(CompoundTag nbt){
        return new APotion(nbt);
    }
}
