package com.Hileb.add_potion.common.potion;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.registries.ForgeRegistries;

public class APotion {
    public static final String NBT_POTION="com.hileb.ap.nbt.potion";
    public static final String NBT_TICK="com.hileb.ap.nbt.tick";
    public static final String NBT_LEVEL="com.hileb.ap.nbt.level";
    public static final String NBT_AB="com.hileb.ap.nbt.am";
    public static final String NBT_SHOWICON="com.hileb.ap.nbt.soIcon";
    public static final String NBT_SHOW_PAETICLES="com.hileb.ap.nbt.so";
    public final MobEffect potion;
    public final int tick;
    public final int level;
    public final boolean ambient;
    public final boolean showIcon;

    public final boolean showParticles;
    public APotion(MobEffectInstance effect){
        potion=effect.getEffect();
        tick=effect.getDuration();
        level=effect.getAmplifier()+1;
        ambient=effect.isAmbient();
        showParticles=effect.isVisible();
        showIcon=effect.showIcon();

    }
    public APotion(MobEffect potionIn, int tickIn,int levelIn,boolean ambientIn,boolean showParticlesIn,boolean showIconIn){
        potion=potionIn;
        tick=tickIn;
        level=levelIn;
        ambient=ambientIn;
        showParticles=showParticlesIn;
        showIcon=showIconIn;
    }

    public MobEffectInstance getEffect(){
        return new MobEffectInstance(potion,tick,level-1,ambient,showParticles,showIcon);
    }
    public CompoundTag toNBT(){
        CompoundTag nbt=new CompoundTag();


        ResourceLocation resourceLocation=ForgeRegistries.MOB_EFFECTS.getKey(potion);
        IDFBasicNBTUtil.setString(nbt,NBT_POTION,resourceLocation.toString());
        IDFBasicNBTUtil.setInt(nbt,NBT_TICK,tick);
        IDFBasicNBTUtil.setInt(nbt,NBT_LEVEL,level);
        IDFBasicNBTUtil.setBoolean(nbt,NBT_AB,ambient);
        IDFBasicNBTUtil.setBoolean(nbt,NBT_SHOW_PAETICLES,showParticles);
        IDFBasicNBTUtil.setBoolean(nbt,NBT_SHOWICON,showIcon);
        return nbt;
    }
    public static APotion fromNBT(CompoundTag nbt){
        MobEffect effect=ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation(IDFBasicNBTUtil.getString(nbt,NBT_POTION,null)));
        int tick=IDFBasicNBTUtil.getInt(nbt,NBT_TICK,0);
        int level=IDFBasicNBTUtil.getInt(nbt,NBT_LEVEL,1);
        boolean ambient=IDFBasicNBTUtil.getBoolean(nbt,NBT_AB,false);
        boolean show_particles =IDFBasicNBTUtil.getBoolean(nbt,NBT_SHOW_PAETICLES,true);
        boolean showIcon=IDFBasicNBTUtil.getBoolean(nbt,NBT_SHOWICON,true);



        return new APotion(effect, tick, level, ambient,show_particles,showIcon);
    }
    public boolean isEmpty(){
        return potion==null || ForgeRegistries.MOB_EFFECTS.getKey(potion) ==null;
    }
}
