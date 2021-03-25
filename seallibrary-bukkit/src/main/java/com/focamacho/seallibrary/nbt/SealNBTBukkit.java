package com.focamacho.seallibrary.nbt;

import com.focamacho.seallibrary.wrapper.nms.NMSWrapper;

import java.util.Set;

public class SealNBTBukkit implements ISealNBT {

    private final Object nbt;

    public SealNBTBukkit(Object nbt) {
        this.nbt = nbt;
    }

    @Override
    public boolean hasKey(String key) {
        return NMSWrapper.nmsWrapper.hasKey(nbt, key);
    }

    @Override
    public void set(String key, ISealNBT value) {
        NMSWrapper.nmsWrapper.set(nbt, key, value);
    }

    @Override
    public void setByte(String key, byte value) {
        NMSWrapper.nmsWrapper.setByte(nbt, key, value);
    }

    @Override
    public void setShort(String key, short value) {
        NMSWrapper.nmsWrapper.setShort(nbt, key, value);
    }

    @Override
    public void setInt(String key, int value) {
        NMSWrapper.nmsWrapper.setInt(nbt, key, value);
    }

    @Override
    public void setLong(String key, long value) {
        NMSWrapper.nmsWrapper.setLong(nbt, key, value);
    }

    @Override
    public void setFloat(String key, float value) {
        NMSWrapper.nmsWrapper.setFloat(nbt, key, value);
    }

    @Override
    public void setDouble(String key, double value) {
        NMSWrapper.nmsWrapper.setDouble(nbt, key, value);
    }

    @Override
    public void setString(String key, String value) {
        NMSWrapper.nmsWrapper.setString(nbt, key, value);
    }

    @Override
    public void setByteArray(String key, byte[] value) {
        NMSWrapper.nmsWrapper.setByteArray(nbt, key, value);
    }

    @Override
    public void setIntArray(String key, int[] value) {
        NMSWrapper.nmsWrapper.setIntArray(nbt, key, value);
    }

    @Override
    public void setBoolean(String key, boolean value) {
        NMSWrapper.nmsWrapper.setBoolean(nbt, key, value);
    }

    @Override
    public ISealNBT get(String key) {
        return NMSWrapper.nmsWrapper.get(nbt, key);
    }

    @Override
    public byte getByte(String key) {
        return NMSWrapper.nmsWrapper.getByte(nbt, key);
    }

    @Override
    public short getShort(String key) {
        return NMSWrapper.nmsWrapper.getShort(nbt, key);
    }

    @Override
    public int getInt(String key) {
        return NMSWrapper.nmsWrapper.getInt(nbt, key);
    }

    @Override
    public long getLong(String key) {
        return NMSWrapper.nmsWrapper.getLong(nbt, key);
    }

    @Override
    public float getFloat(String key) {
        return NMSWrapper.nmsWrapper.getFloat(nbt, key);
    }

    @Override
    public double getDouble(String key) {
        return NMSWrapper.nmsWrapper.getDouble(nbt, key);
    }

    @Override
    public String getString(String key) {
        return NMSWrapper.nmsWrapper.getString(nbt, key);
    }

    @Override
    public byte[] getByteArray(String key) {
        return NMSWrapper.nmsWrapper.getByteArray(nbt, key);
    }

    @Override
    public int[] getIntArray(String key) {
        return NMSWrapper.nmsWrapper.getIntArray(nbt, key);
    }

    @Override
    public boolean getBoolean(String key) {
        return NMSWrapper.nmsWrapper.getBoolean(nbt, key);
    }

    @Override
    public Set<String> getKeys() {
        return NMSWrapper.nmsWrapper.getKeys(nbt);
    }

    @Override
    public void remove(String key) {
        NMSWrapper.nmsWrapper.remove(nbt, key);
    }

    @Override
    public Object toOriginal() {
        return nbt;
    }

}
