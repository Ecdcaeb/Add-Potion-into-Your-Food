package com.Hileb.add_potion.recipe;

import com.Hileb.add_potion.gui.potion.expOne.OnEat;
import com.Hileb.add_potion.util.potion.ApplyUtil;
import com.Hileb.add_potion.util.potion.PotionUtil;
import com.google.common.collect.Lists;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.Iterator;
import java.util.List;

public class PotionRecipeFactoryExtend extends ShapedRecipes {
    public PotionRecipeFactoryExtend() {
        super("ap_potion_imbew_crafting",
                3,3,getList(),new ItemStack(Items.DIAMOND_CHESTPLATE)
                );
        MinecraftForge.EVENT_BUS.register(this);
    }
    public static NonNullList<Ingredient> getList(){
        NonNullList<Ingredient> list=NonNullList.create();
        list.add(Ingredient.fromItem(Items.POTIONITEM));
        list.add(Ingredient.fromItem(Items.POTIONITEM));
        list.add(Ingredient.fromItem(Items.POTIONITEM));

        list.add(Ingredient.fromItem(Items.POTIONITEM));
        list.add(Ingredient.EMPTY);
        list.add(Ingredient.fromItem(Items.POTIONITEM));

        list.add(Ingredient.fromItem(Items.POTIONITEM));
        list.add(Ingredient.fromItem(Items.POTIONITEM));
        list.add(Ingredient.fromItem(Items.POTIONITEM));
        return list;
    }

    @Override
    public boolean matches(@Nonnull InventoryCrafting inv, @Nonnull World world) {
        if (inv.getSizeInventory()==9){
            for(int i=0;i<9;i++){
                ItemStack stack=inv.getStackInSlot(i);
                if (i!=4){
                    if (!ApplyUtil.applyPotion(stack))return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public ItemStack getCraftingResult(@Nonnull InventoryCrafting inventory) {
        ItemStack outputItemStack = inventory.getStackInSlot(4).copy();
        for(int i=0;i<9;i++){
            if (i!=4){
                ItemStack stack=inventory.getStackInSlot(i);
                PotionUtil.addAPotionToStack(outputItemStack,PotionUtil.getAllEffect(stack));
            }
        }
        return outputItemStack;
    }




    @SubscribeEvent
    public void playerTick(TickEvent.PlayerTickEvent event) {
        EntityLivingBase player = event.player;
        Iterator<ItemStack> armor = player.getArmorInventoryList().iterator();
        List<ItemStack> armorStack = Lists.newArrayList(armor);
        Iterator var5 = armorStack.iterator();

        while(var5.hasNext()) {
            ItemStack itemStack=(ItemStack)var5.next();
            OnEat.doit(player,itemStack);
        }

        OnEat.doit(player,player.getHeldItemMainhand());
        OnEat.doit(player,player.getHeldItemOffhand());
    }

}
