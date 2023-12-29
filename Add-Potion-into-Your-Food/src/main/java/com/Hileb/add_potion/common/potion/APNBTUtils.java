package com.Hileb.add_potion.common.potion;


import net.minecraft.nbt.CompoundTag;

import java.util.UUID;

public class APNBTUtils {
    public static int getInt(CompoundTag tagCompound, String key, int defaultVal)
    {
        if (tagCompound!=null)
        {
            if (tagCompound.contains(key,99)){
                return tagCompound.getInt(key);
            }
        }
        return defaultVal;
    }
    public static void setInt(CompoundTag tagCompound, String key, int var)
    {
        if (tagCompound!=null)
        {
            tagCompound.putInt(key,var);
        }
    }
}
