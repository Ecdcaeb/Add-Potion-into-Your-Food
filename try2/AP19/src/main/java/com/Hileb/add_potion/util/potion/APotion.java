package com.Hileb.add_potion.util.potion;


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
    public static final String NBT_SHOW="com.hileb.ap.nbt.so";
    public static final String NBT_VI="com.hileb.ap.nbt.vi";
    public final MobEffect potion;
    public final int tick;
    public final int level;
    public final boolean ambient;
    public final boolean visible;
    public final  boolean showIcon;
    public APotion(MobEffectInstance effect){
        potion=effect.getEffect();
        tick=effect.getDuration();
        level=effect.getAmplifier()+1;
        ambient=effect.isAmbient();
        visible=effect.isVisible();
        showIcon=effect.showIcon();
    }
    public APotion(MobEffect potionIn, int tickIn,int levelIn,boolean ambientIn,boolean visibleIn,boolean showIconIn){
        potion=potionIn;
        tick=tickIn;
        level=levelIn;
        ambient=ambientIn;
        visible=visibleIn;
        showIcon=showIconIn;
    }

    public MobEffectInstance getEffect(){
        return new MobEffectInstance(potion,tick,level-1,ambient,visible,showIcon);
    }
    public CompoundTag toNBT(){
        CompoundTag nbt=new CompoundTag();
        nbt.putString(NBT_POTION, ForgeRegistries.MOB_EFFECTS.getResourceKey(potion).get().registry().toString());
        nbt.putInt(NBT_TICK,tick);
        nbt.putInt(NBT_LEVEL,level);
        nbt.putBoolean(NBT_AB,ambient);
        nbt.putBoolean(NBT_VI,visible);
        nbt.putBoolean(NBT_SHOW,showIcon);

        return nbt;
    }
    public static APotion fromNBT(CompoundTag nbt){
        ResourceLocation resourceLocation=new ResourceLocation(nbt.getString(NBT_POTION));
        MobEffect potion= ForgeRegistries.MOB_EFFECTS.getValue(resourceLocation);
        return new APotion(
                potion,
                nbt.getInt(NBT_TICK),
                nbt.getInt(NBT_LEVEL),
                nbt.getBoolean(NBT_AB),
                nbt.getBoolean(NBT_VI),
                nbt.getBoolean(NBT_SHOW)
        );
    }
    public boolean isEmpty(){
        return potion==null;
    }
}
