package com.Hileb.add_potion.util.NBTStrDef;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nullable;


public class IDLNBTUtil {
	public static NBTTagCompound getNBT(ItemStack stack)
	{
		NBTTagCompound nbt = stack.getTagCompound();
		if (nbt == null)
		{
			nbt = new NBTTagCompound();
			stack.setTagCompound(nbt);
		}
		return nbt;
	}
	
	public static NBTTagCompound getNBT(Entity entity) {
		NBTTagCompound nbt = entity.getEntityData();
		return nbt;
	}
	//writeEntityToNBT
	//readEntityFromNBT
	
	@Nullable
	public static boolean StackHasKey(ItemStack stack, String key) {
		return !(stack.isEmpty() || !getNBT(stack).hasKey(key));
	}


	public static int GetInt(ItemStack stack, String key, int defaultVal)
	{
		if (StackHasKey(stack, key))
		{
			NBTTagCompound nbt = getNBT(stack);
			return nbt.getInteger(key);
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
			NBTTagCompound nbt = getNBT(stack);
			return nbt.getString(key);
		}		
		else
		{
			return defaultVal;
		}
	}

}
