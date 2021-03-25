package com.focamacho.seallibrary.wrapper.nms;

import com.focamacho.seallibrary.item.ISealStack;
import com.focamacho.seallibrary.logger.SealLogger;
import com.focamacho.seallibrary.nbt.ISealNBT;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

public abstract class NMSWrapper {

    public static NMSWrapper nmsWrapper;

    static {
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3].substring(1);

        Class<?>[] versions = {
                NMSWrapper1_8_R3.class,
                NMSWrapper1_12_R1.class,
                NMSWrapper1_16_R3.class
        };

        for(Class<?> v : versions) {
            if(v.getSimpleName().substring(10).equalsIgnoreCase(version)) {
                try {
                    nmsWrapper = (NMSWrapper) v.newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }

        if(nmsWrapper == null) {
            SealLogger.error("A sua versão de servidor não é compatível com a Seal Library.");
            SealLogger.error("Muitas coisas não irão funcionar corretamente.");
        }
    }

    public abstract ItemStack getStackFromString(String id);

    public abstract String getDataFromStack(ItemStack stack);

    public abstract ItemStack setDataToStack(ItemStack stack, String data);

    public abstract ISealNBT getNBTFromStack(ItemStack stack);

    public abstract ItemStack setNBTToStack(ItemStack stack, ISealNBT nbt);

    public abstract ISealNBT createNBT();

    public abstract String stackToJson(ISealStack stack);

    public abstract String getItemRegistryName(ItemStack stack);
    
    //NBT
    public abstract boolean hasKey(Object nbt, String key);

    public abstract void set(Object nbt, String key, ISealNBT value);

    public abstract void setByte(Object nbt, String key, byte value);

    public abstract void setShort(Object nbt, String key, short value);

    public abstract void setInt(Object nbt, String key, int value);

    public abstract void setLong(Object nbt, String key, long value);

    public abstract void setFloat(Object nbt, String key, float value);

    public abstract void setDouble(Object nbt, String key, double value);

    public abstract void setString(Object nbt, String key, String value);

    public abstract void setByteArray(Object nbt, String key, byte[] value);

    public abstract void setIntArray(Object nbt, String key, int[] value);

    public abstract void setBoolean(Object nbt, String key, boolean value);

    public abstract ISealNBT get(Object nbt, String key);

    public abstract byte getByte(Object nbt, String key);

    public abstract short getShort(Object nbt, String key);

    public abstract int getInt(Object nbt, String key);

    public abstract long getLong(Object nbt, String key);

    public abstract float getFloat(Object nbt, String key);

    public abstract double getDouble(Object nbt, String key);

    public abstract String getString(Object nbt, String key);

    public abstract byte[] getByteArray(Object nbt, String key);

    public abstract int[] getIntArray(Object nbt, String key);

    public abstract boolean getBoolean(Object nbt, String key);

    public abstract Set<String> getKeys(Object nbt);

    public abstract void remove(Object nbt, String key);

}
