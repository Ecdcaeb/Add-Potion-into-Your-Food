package com.Hileb.add_potion.common.potion;



import com.Hileb.add_potion.common.event.APCraftEvent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class PotionUtil {
    public static final String NBT_COUNT="com.hileb.ap.nbt.count";
    public static final String NBT_POTION="com.hileb.ap.nbt.nbt.level.potion_%d_I";
    public static String getNBTPOTION(int index){
        return String.format(NBT_POTION,index);
    }
    public static List<APotion> getFormBasicStack(ItemStack stack){
        List<APotion> list=new ArrayList<>();
        if (stack.hasTag() && stack.getTag().contains("Potion")){
            Potion type= ForgeRegistries.POTIONS.getValue(new ResourceLocation(stack.getTag().getString("Potion")));
            for(MobEffectInstance effect:type.getEffects()){
                list.add(new APotion(effect));
            }
        }
        return list;
    }
    public static List<APotion> getFormOldAPStack(ItemStack stack){
        List<APotion> list=new ArrayList<>();
        if (stack.hasTag() && stack.getTag().contains("PotionCountAP")){
            CompoundTag tag=stack.getTag();
            for (int i = 0; i < IDFBasicNBTUtil.getInt(tag, "PotionCountAP", 0); i++){
                if (IDFBasicNBTUtil.getString(tag, String.format("PotionAPS_%d_I", i), null) != null) {
                    Potion type= ForgeRegistries.POTIONS.getValue(new ResourceLocation(IDFBasicNBTUtil.getString(tag, String.format("PotionAPS_%d_I", i), null)));
                    for(MobEffectInstance effect:type.getEffects()){
                        list.add(new APotion(effect));
                    }
                }
            }
        }
        return list;
    }
//    public static List<APotion> getFromRusticStack(ItemStack stack){
//        List<APotion> list=new ArrayList<>();
//        if (LoadMods.rustic){
//            if (rustic.common.util.ElixirUtils.getEffects(stack).size()<=0)return list;
//            for(PotionEffect effect:rustic.common.util.ElixirUtils.getEffects(stack)){
//                list.add(new APotion(effect));
//            }
//        }
//        return list;
//    }
    public static  List<APotion> getAPotionFromThis(ItemStack stack){
        List<APotion> list=new ArrayList<>();
        if (stack.hasTag() && stack.getTag().contains(NBT_COUNT)){
            CompoundTag tag=stack.getTag();
            for(int i=0;i<IDFBasicNBTUtil.getInt(tag,NBT_COUNT,0);i++){
                list.add(APotion.fromNBT(stack.getTag().getCompound(getNBTPOTION(i))));
            }
        }
        return list;
    }
    public static List<APotion> getAPotionFromList(List<MobEffectInstance> effects){
        List<APotion> list=new ArrayList<>();
        for(MobEffectInstance effect:effects){
            list.add(new APotion(effect));
        }
        return list;
    }
//    public static List<APotion> getFromBotanla(ItemStack stack){
//        List<APotion> list=new ArrayList<>();
//        if (LoadMods.botania){
//            if (stack.hasTagCompound() && IDLNBTUtil.GetString(stack,"brewKey",null)!=null){
//                Brew brew= BotaniaAPI.getBrewFromKey(IDLNBTUtil.GetString(stack,"brewKey",null));
//                List<APotion> list1=getAPotionFromList(brew.getPotionEffects(stack));
//                if (list1!=null && list1.size()>0){
//                    list.addAll(list1);
//                }
//            }
//        }
//        return list;
//    }
//    public static List<APotion> getFromPotionCore(ItemStack stack){
//        if (LoadMods.potionCore){
//            return getListOfAPotion(PotionCoreHelper.getEffectsFromStack(stack));
//        }
//        return new ArrayList<>();
//    }
    public static List<APotion> getAllEffectIShouldDo(ItemStack stack){
        List<APotion> list=new ArrayList<>();
        if(getAPotionFromThis(stack).size()>0)list.addAll(getAPotionFromThis(stack));
        if(getFormOldAPStack(stack).size()>0)list.addAll(getFormOldAPStack(stack));
        return list;
    }

    public static List<APotion> getAllEffect(ItemStack stack){
        List<APotion> list=new ArrayList<>();
        if(getFormBasicStack(stack).size()>0)list.addAll(getFormBasicStack(stack));
        if(getFormOldAPStack(stack).size()>0)list.addAll(getFormOldAPStack(stack));
        if(getAPotionFromThis(stack).size()>0)list.addAll(getAPotionFromThis(stack));
//        if(LoadMods.rustic && getFromRusticStack(stack).size()>0)list.addAll(getFromRusticStack(stack));
//        if (LoadMods.botania && getFromBotanla(stack).size()>0)list.addAll(getFromBotanla(stack));
//        if (LoadMods.potionCore && getFromPotionCore(stack).size()>0)list.addAll(getFromPotionCore(stack));
        MinecraftForge.EVENT_BUS.post(new APCraftEvent.GetAPotion(stack,list));
        return list;
    }
    public static void writeAPotionToStack(ItemStack stack,List<APotion> list){
        if (list==null && list.size()<=0)return;
        if (!stack.hasTag())stack.setTag(new CompoundTag());
        CompoundTag tag=stack.getTag();
        for(int i=0;i<list.size();i++){
            tag.put(getNBTPOTION(i),list.get(i).toNBT());
        }
        IDFBasicNBTUtil.setInt(tag,NBT_COUNT,list.size());
    }
    public static void addAPotionToStack(ItemStack stack,List<APotion> list){
        if (list==null || list.size()<=0)return;
        List<APotion> oldList=getAPotionFromThis(stack);
        if (oldList!=null && oldList.size()>0)list.addAll(oldList);
        writeAPotionToStack(stack,list);
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
    public static Potion makeType(List<APotion> aPotions){
        MobEffectInstance[] listPP=new MobEffectInstance[aPotions.size()];
        getListOfPotionEffect(aPotions).toArray(listPP);
        return new Potion("random"+aPotions.hashCode(),listPP);
    }
}
