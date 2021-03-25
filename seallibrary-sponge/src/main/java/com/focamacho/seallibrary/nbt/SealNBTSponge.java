package com.focamacho.seallibrary.nbt;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Set;

public class SealNBTSponge implements ISealNBT {

    private final NBTTagCompound nbt;

    public SealNBTSponge(Object nbt) {
        this.nbt = (NBTTagCompound) nbt;
    }

    @Override
    public boolean hasKey(String key) {
        return nbt.hasKey(key);
    }

    @Override
    public void set(String key, ISealNBT value) {
        nbt.setTag(key, (NBTBase) value.toOriginal());
    }

    @Override
    public void setByte(String key, byte value) {
        nbt.setByte(key, value);
    }

    @Override
    public void setShort(String key, short value) {
        nbt.setShort(key, value);
    }

    @Override
    public void setInt(String key, int value) {
        nbt.setInteger(key, value);
    }

    @Override
    public void setLong(String key, long value) {
        nbt.setLong(key, value);
    }

    @Override
    public void setFloat(String key, float value) {
        nbt.setFloat(key, value);
    }

    @Override
    public void setDouble(String key, double value) {
        nbt.setDouble(key, value);
    }

    @Override
    public void setString(String key, String value) {
        nbt.setString(key, value);
    }

    @Override
    public void setByteArray(String key, byte[] value) {
        nbt.setByteArray(key, value);
    }

    @Override
    public void setIntArray(String key, int[] value) {
        nbt.setIntArray(key, value);
    }

    @Override
    public void setBoolean(String key, boolean value) {
        nbt.setBoolean(key, value);
    }

    @Override
    public ISealNBT get(String key) {
        return SealNBT.get(nbt.getCompoundTag(key));
    }

    @Override
    public byte getByte(String key) {
        return nbt.getByte(key);
    }

    @Override
    public short getShort(String key) {
        return nbt.getShort(key);
    }

    @Override
    public int getInt(String key) {
        return nbt.getInteger(key);
    }

    @Override
    public long getLong(String key) {
        return nbt.getLong(key);
    }

    @Override
    public float getFloat(String key) {
        return nbt.getFloat(key);
    }

    @Override
    public double getDouble(String key) {
        return nbt.getDouble(key);
    }

    @Override
    public String getString(String key) {
        return nbt.getString(key);
    }

    @Override
    public byte[] getByteArray(String key) {
        return nbt.getByteArray(key);
    }

    @Override
    public int[] getIntArray(String key) {
        return nbt.getIntArray(key);
    }

    @Override
    public boolean getBoolean(String key) {
        return nbt.getBoolean(key);
    }

    @Override
    public Set<String> getKeys() {
        return nbt.getKeySet();
    }

    @Override
    public void remove(String key) {
        nbt.removeTag(key);
    }

    @Override
    public Object toOriginal() {
        return nbt;
    }
}
