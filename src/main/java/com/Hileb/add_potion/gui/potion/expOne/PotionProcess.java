package com.Hileb.add_potion.gui.potion.expOne;

import com.Hileb.add_potion.util.NBTStrDef.IDLNBTUtil;
import com.google.common.collect.Lists;
import net.minecraft.init.Items;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;

import java.util.List;

public class PotionProcess {
    public static void process(Slot potion, Slot food){
        if (potion.getStack().hasTagCompound()){
            if (potion.getStack().getTagCompound().hasKey("Potion")){
                String potion_name= IDLNBTUtil.GetString(potion.getStack(),"Potion",null);
                if (potion_name!=null){

                    IDLNBTUtil.SetInt(food.getStack(),"PotionCountAP",IDLNBTUtil.GetInt(food.getStack(),"PotionCountAP",0)+1);
                    IDLNBTUtil.SetString(food.getStack(),String.format("PotionAPS_%d_I",IDLNBTUtil.GetInt(food.getStack(),"PotionCountAP",1)-1),
                            PotionType.REGISTRY.getNameForObject(PotionUtils.getPotionFromItem(potion.getStack())).toString());



                    potion.putStack(ItemStack.EMPTY);
                }
            }
        }
    }
}
