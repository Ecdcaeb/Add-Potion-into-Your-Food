package com.Hileb.add_potion.util;

import net.minecraft.item.ItemStack;

public class MessageDef {
    //GENERAL:
    public static final String OUT_OF_RANGE = "add_potion.msg.out_of_range";
    public static final String IN_COOLDOWN = "add_potion.skill.msg.cool_down";
    public static final String NOT_CASTABLE_MAINHAND = "add_potion.skill.msg.not_castable_mainhand";
    public static final String NOT_CASTABLE_OFFHAND = "add_potion.skill.msg.not_castable_offhand";

    public static String getSkillCastKey(ItemStack stack, int index)
    {
        //remove"item."
        return String.format("msg.%s.cast.%d", stack.getUnlocalizedName().substring(5), index);
    }
}
