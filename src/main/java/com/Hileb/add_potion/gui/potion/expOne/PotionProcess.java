package com.Hileb.add_potion.gui.potion.expOne;

import com.Hileb.add_potion.util.NBTStrDef.IDLNBTUtil;
import com.google.common.collect.Lists;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;

import java.util.ArrayList;
import java.util.List;

public class PotionProcess {
    public static void process(Slot potion, Slot food){
        if (potion.getStack().hasTagCompound()){
            if (potion.getStack().getTagCompound().hasKey("Potion") || potion.getStack().getTagCompound().hasKey("PotionCountAP")){
                List<PotionType> types=new ArrayList<>();
                if (PotionUtils.getPotionFromItem(potion.getStack())!=PotionTypes.EMPTY)types.add(PotionUtils.getPotionFromItem(potion.getStack()));
                if (potion.getStack().getTagCompound().hasKey("PotionCountAP"))addPotionTooltip(potion.getStack(),types);
                for(PotionType type:types){
                    if (type!=null){
                        if (type!=PotionTypes.EMPTY){
                            IDLNBTUtil.SetInt(food.getStack(),"PotionCountAP",IDLNBTUtil.GetInt(food.getStack(),"PotionCountAP",0)+1);
                            IDLNBTUtil.SetString(food.getStack(),String.format("PotionAPS_%d_I",IDLNBTUtil.GetInt(food.getStack(),"PotionCountAP",1)-1),
                                    PotionType.REGISTRY.getNameForObject(type).toString());

                        }
                    }
                }
                potion.putStack(ItemStack.EMPTY);
            }
        }
    }
    public static void addPotionTooltip(ItemStack stackIn,List<PotionType> types) {
        List<String> s = new ArrayList<>();
        for (int i = 0; i < IDLNBTUtil.GetInt(stackIn, "PotionCountAP", 0); i++) {
            if (IDLNBTUtil.GetString(
                    stackIn, String.format(
                            "PotionAPS_%d_I", i),
                    null) != null) {
                s.add(IDLNBTUtil.GetString(
                        stackIn, String.format(
                                "PotionAPS_%d_I", i),
                        null));
            }
        }
        for (String p : s) {
            {
                if (p != null) {
                    PotionType type = PotionType.getPotionTypeForName(p);
                    if (type != null && type != PotionTypes.EMPTY) {
                        types.add(type);
                    }
                }
            }
        }
    }
}
