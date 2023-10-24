package com.Hileb.add_potion.util.NBTStrDef;


import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;


public class IDLNBTUtil {

	
	@Nullable
	public static boolean StackHasKey(ItemStack stack, String key) {
		if (stack==null)return false;
		else if (stack.isEmpty())return false;
		else if (!stack.hasTag())return false;
		else return stack.getOrCreateTag().contains(key);
	}


	public static int GetInt(ItemStack stack, String key, int defaultVal)
	{
		if (StackHasKey(stack, key))
		{
			CompoundTag nbt = stack.getOrCreateTag();
			return nbt.getInt(key);
		}		
		else
		{
			return defaultVal;
		}
	}

	//String
	public static String GetString(ItemStack stack, String key, String defaultVal)
	{
		if (StackHasKey(stack, key))
		{
			CompoundTag nbt = stack.getOrCreateTag();
			return nbt.getString(key);
		}		
		else
		{
			return defaultVal;
		}
	}

}
