package com.Hileb.add_potion.common.potion;


import com.Hileb.add_potion.api.AddPotionRegistries;
import com.Hileb.add_potion.api.IPotionGetter;
import com.Hileb.add_potion.api.events.APCraftEvent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class PotionUtil {
    public static final class BuildInUtils{
        public static List<APotion> getInternalAPEffect(ItemStack stack){
            List<APotion> list=new ArrayList<>();
            if (stack.hasTag() && stack.getTag().contains(NBT_COUNT)){
                CompoundTag tag=stack.getTag();
                for(int i=0;i<IDFBasicNBTUtil.getInt(tag,NBT_COUNT,0);i++){
                    list.add(APotion.fromNBT(stack.getTag().getCompound(getNBTPotionById(i))));
                }
            }
            return list;
        }
        public static List<APotion> getFormVanilla(ItemStack stack){
            List<APotion> list=new ArrayList<>();
            list.addAll(PotionUtil.getListOfAPotion(PotionUtils.getMobEffects(stack)));
            return list;
        }
        public static final class RegisterObject{
            public static final class Vanilla{
                public static final IPotionGetter AP_EFFECT=BuildInUtils::getInternalAPEffect;
                public static final IPotionGetter VANILLA=BuildInUtils::getFormVanilla;
                public static final AddPotionRegistries.Adjuster BOTH=((player, stack) -> (stack.getItem()== Items.POTION || stack.getItem()==Items.LINGERING_POTION || stack.getItem()==Items.SPLASH_POTION)?AddPotionRegistries.Result.APPLY: AddPotionRegistries.Result.PASS);
                public static final AddPotionRegistries.Adjuster FOOD_ONLY=(player, stack) ->  stack.getItem().isEdible()?AddPotionRegistries.Result.APPLY: AddPotionRegistries.Result.PASS;
            }
        }
    }
    public static final String NBT_COUNT="add_potion.count";
    public static final String NBT_POTION="add_potion.potion_%d_I";
    public static String getNBTPotionById(int index){
        return String.format(NBT_POTION,index);
    }
    public static List<APotion> getAPotionFromList(List<MobEffectInstance> effects){
        List<APotion> list=new ArrayList<>();
        for(MobEffectInstance effect:effects){
            list.add(new APotion(effect));
        }
        return list;
    }
    @SuppressWarnings("all")
    public static List<APotion> getAllEffect(ItemStack stack){
        List<APotion> list=new ArrayList<>();
        Map.Entry<ResourceLocation,IPotionGetter> entry=null;
        Iterator<Map.Entry<ResourceLocation,IPotionGetter>> iterator=AddPotionRegistries.POTION_GETTERS.entrySet().iterator();
        try{
            while (iterator.hasNext()){
                entry=iterator.next();
                list.addAll(entry.getValue().get(stack));
            }
        }catch (Exception e){
            throw new RuntimeException("a Exception happened at getting effects from stack:"+stack.toString()+" .reason is "+entry.getKey().toString(),e);
        }
        MinecraftForge.EVENT_BUS.post(new APCraftEvent.GetAPotion(stack,list));
        return list;
    }
    @SuppressWarnings("all")
    public static void writeAPotionToStack(ItemStack stack,List<APotion> list){
        if (list==null || list.size() == 0)return;
        if (!stack.hasTag())stack.setTag(new CompoundTag());
        CompoundTag tag=stack.getTag();
        for(int i=0;i<list.size();i++){
            tag.put(getNBTPotionById(i),list.get(i).toNBT());
        }
        IDFBasicNBTUtil.setInt(tag,NBT_COUNT,list.size());
    }
    @SuppressWarnings("all")
    public static void addAPotionToStack(ItemStack stack,List<APotion> list){
        if (list==null || list.isEmpty())return;
        int oldSize=getAPotionCount(stack);
        if (!stack.hasTag())stack.setTag(new CompoundTag());
        CompoundTag tag=stack.getTag();
        int size=list.size();
        for(int i=0;i<size;i++){
            tag.put(getNBTPotionById(i+oldSize),list.get(i).toNBT());
        }
    }
    public static List<MobEffectInstance> getListOfPotionEffect(List<APotion> aPotions){
        List<MobEffectInstance> effects=new ArrayList<>();
        for(APotion aPotion:aPotions){
            effects.add(aPotion.getEffect());
        }
        return effects;
    }
    public static List<APotion> getListOfAPotion(List<MobEffectInstance> aPotions){
        List<APotion> effects=new ArrayList<>();
        for(MobEffectInstance aPotion:aPotions){
            effects.add(new APotion(aPotion));
        }
        return effects;
    }
    public static int getAPotionCount(ItemStack stack){
        if (stack.hasTag() && stack.getTag().contains(NBT_COUNT,99)){
            return stack.getTag().getInt(NBT_COUNT);
        }else return 0;
    }
}
