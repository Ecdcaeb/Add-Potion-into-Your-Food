package com.Hileb.add_potion.gui.potion.expOne;

import com.Hileb.add_potion.IdlFramework;
import com.Hileb.add_potion.util.NBTStrDef.IDLNBTUtil;
import com.Hileb.add_potion.util.Reference;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class OnEat {
    @SubscribeEvent
    public static void onFoodEaten(LivingEntityUseItemEvent.Finish event){
        World world=event.getEntityLiving().world;
        if (!world.isRemote){
            if (event.getItem().getItem() instanceof ItemFood || event.getItem().getItem()== Items.POTIONITEM){
                //PotionType.getPotionTypeForName
                List<String> s=new ArrayList<>();
                for(int i=0;i< IDLNBTUtil.GetInt(event.getItem(),"PotionCountAP",0);i++){
                    if (IDLNBTUtil.GetString(
                            event.getItem(),String.format(
                                    "PotionAPS_%d_I",i),
                            null)!=null){
                        s.add(IDLNBTUtil.GetString(
                                event.getItem(),String.format(
                                        "PotionAPS_%d_I",i),
                                null));
                    }
                }
                for(String p:s){
                {
                    if (p!=null){
                        PotionType type=PotionType.getPotionTypeForName(p);
                        if (type!=null){
                            for (PotionEffect potioneffect : type.getEffects())
                            {
                                if (potioneffect.getPotion().isInstant())
                                {
                                    potioneffect.getPotion().affectEntity(event.getEntityLiving(), event.getEntityLiving(), event.getEntityLiving(), potioneffect.getAmplifier(), 1.0D);
                                }
                                else
                                {
                                    event.getEntityLiving().addPotionEffect(new PotionEffect(potioneffect));
                                    //IdlFramework.Log("add Potion:%s",potioneffect.toString());
                                }
                            }
                        }
                    }
                }
                }
            }
        }
    }
//    @SubscribeEvent
//    public static void onMilkEaten(LivingEntityUseItemEvent.Finish event){
//        if (!event.getEntityLiving().world.isRemote){
//            if (event.getItem().getItem()==Items.MILK_BUCKET){
//                event.setCanceled(true);
//                event.getItem().shrink(1);
//            }
//        }
//    }
}
