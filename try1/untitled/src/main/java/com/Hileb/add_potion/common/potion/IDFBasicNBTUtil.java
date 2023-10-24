package com.Hileb.add_potion.common.potion;


import net.minecraft.nbt.CompoundTag;

import java.util.UUID;

public class IDFBasicNBTUtil {
    public static class NBTType{
        public static final int NBTTagEnd=0;
        public static final int NBTTagByte=1;
        public static final int NBTTagShort=2;
        public static final int NBTTagInt=3;
        public static final int NBTTagLong=4;
        public static final int NBTTagFloat=5;
        public static final int NBTTagDouble=6;
        public static final int NBTTagByteArray=7;
        public static final int NBTTagString=8;
        public static final int NBTTagList=9;

        public static final int NBTTagCompound=10;

        public static final int NBTTagIntArray=11;
        public static final int NBTTagLongArray=12;
    }
    public static boolean getBoolean(CompoundTag tagCompound, String key, boolean defaultVal)
    {
        if (tagCompound!=null)
        {
            if (tagCompound.contains(key)){
                return tagCompound.getBoolean(key);
            }
        }
        return defaultVal;
    }
    public static int getInt(CompoundTag tagCompound, String key, int defaultVal)
    {
        if (tagCompound!=null)
        {
            if (tagCompound.contains(key)){
                return tagCompound.getInt(key);
            }
        }
        return defaultVal;
    }
    public static int[] getIntArray(CompoundTag tagCompound, String key, int[] defaultVal)
    {
        if (tagCompound!=null)
        {
            if (tagCompound.contains(key)){
                return tagCompound.getIntArray(key);
            }
        }
        return defaultVal;
    }
    public static double getDouble(CompoundTag tagCompound, String key, double defaultVal)
    {
        if (tagCompound!=null)
        {
            if (tagCompound.contains(key)){
                return tagCompound.getDouble(key);
            }
        }
        return defaultVal;
    }
    public static float getFloat(CompoundTag tagCompound, String key, float defaultVal)
    {
        if (tagCompound!=null)
        {
            if (tagCompound.contains(key)){
                return tagCompound.getFloat(key);
            }
        }
        return defaultVal;
    }
    public static UUID getUUID(CompoundTag tagCompound, String key, UUID defaultVal)
    {
        if (tagCompound!=null)
        {
            if (tagCompound.contains(key)){
                return tagCompound.getUUID(key);
            }
        }
        return defaultVal;
    }
    public static String getString(CompoundTag tagCompound, String key,String defaultVal)
    {
        if (tagCompound!=null)
        {
            if (tagCompound.contains(key)){
                return tagCompound.getString(key);
            }
        }
        return defaultVal;
    }
    public static  CompoundTag getNBTTagCompound(CompoundTag tagCompound, String key, CompoundTag defaultVal){
        if (tagCompound!=null)
        {
            if (tagCompound.contains(key)){
                return tagCompound.getCompound(key);
            }
        }
        return defaultVal;
    }

    public static void setBoolean(CompoundTag tagCompound, String key, boolean var)
    {
        if (tagCompound!=null)
        {
            tagCompound.putBoolean(key,var);
        }
    }
    public static void setInt(CompoundTag tagCompound, String key, int var)
    {
        if (tagCompound!=null)
        {
            tagCompound.putInt(key,var);
        }
    }
    public static void setIntArray(CompoundTag tagCompound, String key, int[] var)
    {
        if (tagCompound!=null)
        {
            tagCompound.putIntArray(key,var);
        }
    }
    public static void setDouble(CompoundTag tagCompound, String key, double var)
    {
        if (tagCompound!=null)
        {
            tagCompound.putDouble(key,var);
        }
    }
    public static void setFloat(CompoundTag tagCompound, String key, float var)
    {
        if (tagCompound!=null)
        {
            tagCompound.putFloat(key,var);
        }
    }
    public static void setUUID(CompoundTag tagCompound, String key, UUID var)
    {
        if (tagCompound!=null)
        {
            tagCompound.putUUID(key,var);
        }
    }
    public static void setCompoundTag(CompoundTag tagCompound, String key, CompoundTag var)
    {
        if (tagCompound!=null)
        {
            tagCompound.put(key,var);
        }
    }
    public static void setString(CompoundTag tagCompound, String key, String var)
    {
        if (tagCompound!=null)
        {
            tagCompound.putString(key,var);
        }
    }

}
