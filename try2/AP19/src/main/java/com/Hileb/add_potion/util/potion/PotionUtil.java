package com.Hileb.add_potion.util.potion;

import com.Hileb.add_potion.event.APCraftEvent;
import com.Hileb.add_potion.util.NBTStrDef.IDLNBTUtil;
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
        if (stack.hasTag() && IDLNBTUtil.StackHasKey(stack,"Potion") ){
            Potion type= ForgeRegistries.POTIONS.getValue(new ResourceLocation(IDLNBTUtil.GetString(stack,"Potion",null)));
            for(MobEffectInstance effect:type.getEffects()){
                list.add(new APotion(effect));
            }
        }
        return list;
    }
    public static List<APotion> getFormOldAPStack(ItemStack stack){
        List<APotion> list=new ArrayList<>();
        if (stack.hasTag() && IDLNBTUtil.StackHasKey(stack,"PotionCountAP")){
            for (int i = 0; i < IDLNBTUtil.GetInt(stack, "PotionCountAP", 0); i++) {
                if (IDLNBTUtil.GetString(stack, String.format("PotionAPS_%d_I", i), null) != null) {
                    Potion type= ForgeRegistries.POTIONS.getValue(new ResourceLocation(IDLNBTUtil.GetString(stack, String.format("PotionAPS_%d_I", i), null)));
                    for(MobEffectInstance effect:type.getEffects()){
                        list.add(new APotion(effect));
                    }
                }
            }
        }
        return list;
    }
    public static  List<APotion> getAPotionFromThis(ItemStack stack){
        List<APotion> list=new ArrayList<>();
        if (stack.hasTag() && IDLNBTUtil.StackHasKey(stack,NBT_COUNT)){
            for(int i=0;i<IDLNBTUtil.GetInt(stack,NBT_COUNT,0);i++){
                list.add(APotion.fromNBT(stack.getOrCreateTag().getCompound(getNBTPOTION(i))));
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
        stack.getOrCreateTag();
        for(int i=0;i<list.size();i++){
            stack.getOrCreateTag().put(getNBTPOTION(i),list.get(i).toNBT());
        }
        stack.getOrCreateTag().putInt(NBT_COUNT,list.size());
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
        //这里伪装一个type
        //用于minecraft写死的效果云
        //但是无法保存
    }
}
