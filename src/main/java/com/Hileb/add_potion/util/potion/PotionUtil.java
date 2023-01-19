package com.Hileb.add_potion.util.potion;

import com.Hileb.add_potion.IdlFramework;
import com.Hileb.add_potion.event.APCraftEvent;
import com.Hileb.add_potion.gui.potion.expOne.LoadMods;
import com.Hileb.add_potion.util.NBTStrDef.IDLNBTUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.brew.Brew;
import vazkii.botania.common.item.brew.ItemBrewBase;

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
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey("Potion")){
            PotionType type=PotionType.getPotionTypeForName(stack.getTagCompound().getString("Potion"));
            for(PotionEffect effect:type.getEffects()){
                list.add(new APotion(effect));
            }
        }
        return list;
    }
    public static List<APotion> getFormOldAPStack(ItemStack stack){
        List<APotion> list=new ArrayList<>();
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey("PotionCountAP")){
            for (int i = 0; i < IDLNBTUtil.GetInt(stack, "PotionCountAP", 0); i++) {
                if (IDLNBTUtil.GetString(stack, String.format("PotionAPS_%d_I", i), null) != null) {
                    PotionType type=PotionType.getPotionTypeForName(IDLNBTUtil.GetString(stack, String.format("PotionAPS_%d_I", i), null));
                    for(PotionEffect effect:type.getEffects()){
                        list.add(new APotion(effect));
                    }
                }
            }
        }
        return list;
    }
    public static List<APotion> getFromRusticStack(ItemStack stack){
        List<APotion> list=new ArrayList<>();
        if (LoadMods.rustic){
            if (rustic.common.util.ElixirUtils.getEffects(stack).size()<=0)return list;
            for(PotionEffect effect:rustic.common.util.ElixirUtils.getEffects(stack)){
                list.add(new APotion(effect));
            }
        }
        return list;
    }
    public static  List<APotion> getAPotionFromThis(ItemStack stack){
        List<APotion> list=new ArrayList<>();
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey(NBT_COUNT)){
            for(int i=0;i<IDLNBTUtil.GetInt(stack,NBT_COUNT,0);i++){
                list.add(APotion.fromNBT(stack.getTagCompound().getCompoundTag(getNBTPOTION(i))));
            }
        }
        return list;
    }
    public static List<APotion> getAPotionFromList(List<PotionEffect> effects){
        List<APotion> list=new ArrayList<>();
        for(PotionEffect effect:effects){
            list.add(new APotion(effect));
        }
        return list;
    }
    public static List<APotion> getFromBotanla(ItemStack stack){
        List<APotion> list=new ArrayList<>();
        if (LoadMods.botania){
            if (stack.hasTagCompound() && IDLNBTUtil.GetString(stack,"brewKey",null)!=null){
                Brew brew= BotaniaAPI.getBrewFromKey(IDLNBTUtil.GetString(stack,"brewKey",null));
                List<APotion> list1=getAPotionFromList(brew.getPotionEffects(stack));
                if (list1!=null && list1.size()>0){
                    list.addAll(list1);
                }
            }
        }
        return list;
    }
    public static List<APotion> getAllEffectIShouldDo(ItemStack stack){
        List<APotion> list=new ArrayList<>();
        if(getAPotionFromThis(stack).size()>0)list.addAll(getFormBasicStack(stack));
        if(getFormOldAPStack(stack).size()>0)list.addAll(getFormOldAPStack(stack));
        return list;
    }

    public static List<APotion> getAllEffect(ItemStack stack){
        List<APotion> list=new ArrayList<>();
        if(getFormBasicStack(stack).size()>0)list.addAll(getFormBasicStack(stack));
        if(getFormOldAPStack(stack).size()>0)list.addAll(getFormOldAPStack(stack));
        if(getAPotionFromThis(stack).size()>0)list.addAll(getAPotionFromThis(stack));
        if(LoadMods.rustic && getFromRusticStack(stack).size()>0)list.addAll(getFromRusticStack(stack));
        if (LoadMods.botania && getFromBotanla(stack).size()>0)list.addAll(getFromBotanla(stack));
        MinecraftForge.EVENT_BUS.post(new APCraftEvent.GetAPotion(stack,list));
        return list;
    }
    public static void writeAPotionToStack(ItemStack stack,List<APotion> list){
        if (list==null && list.size()<=0)return;
        if (!stack.hasTagCompound())stack.setTagCompound(new NBTTagCompound());
        for(int i=0;i<list.size();i++){
            stack.getTagCompound().setTag(getNBTPOTION(i),list.get(i).toNBT());
        }
        stack.getTagCompound().setInteger(NBT_COUNT,list.size());
    }
    public static void addAPotionToStack(ItemStack stack,List<APotion> list){
        if (list==null || list.size()<=0)return;
        List<APotion> oldList=getAPotionFromThis(stack);
        if (oldList!=null && oldList.size()>0)list.addAll(oldList);
        writeAPotionToStack(stack,list);
    }
}
