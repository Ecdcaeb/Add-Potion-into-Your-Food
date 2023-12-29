package com.Hileb.add_potion.common.potion;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;

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
