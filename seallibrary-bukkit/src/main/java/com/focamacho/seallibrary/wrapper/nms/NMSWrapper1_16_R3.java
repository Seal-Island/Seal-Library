package com.focamacho.seallibrary.wrapper.nms;

import com.focamacho.seallibrary.item.ISealStack;
import com.focamacho.seallibrary.nbt.ISealNBT;
import com.focamacho.seallibrary.nbt.SealNBT;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.v1_16_R3.MojangsonParser;
import net.minecraft.server.v1_16_R3.NBTBase;
import net.minecraft.server.v1_16_R3.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.json.JSONObject;

import java.util.Set;
import java.util.regex.Pattern;

public class NMSWrapper1_16_R3 extends NMSWrapper {

    @Override
    public ItemStack getStackFromString(String id) {
        String data = "";

        if(id.contains(".withTag(")) {
            String[] item = id.split(Pattern.quote(".withTag("));

            if(item.length > 1) {
                StringBuilder dataB = new StringBuilder();
                for(int i = 1; i < item.length; i++) {
                    dataB.append(item[i]);
                }
                data = dataB.toString();
                data = data.substring(0, data.length() - 1);
            }

            id = item[0];
        }

        String[] split = id.split(":");
        if(split.length == 3) {
            Material material = Registry.MATERIAL.get(new NamespacedKey(split[0], split[1]));
            if(material == null) return new ItemStack(Material.AIR);

            short value;

            try {
                value = Short.parseShort(split[2]);
            } catch(NumberFormatException e) {
                return new ItemStack(Material.AIR);
            }

            ItemStack stack = new ItemStack(material, 1);

            if(!data.isEmpty()) {
                stack = setDataToStack(stack, data);
            }

            return stack;
        } else if(split.length == 2) {
            Material material = Registry.MATERIAL.get(new NamespacedKey(split[0], split[1]));
            if(material == null) return new ItemStack(Material.AIR);
            ItemStack stack = new ItemStack(material, 1);

            if(!data.isEmpty()) {
                stack = setDataToStack(stack, data);
            }

            return stack;
        } else return new ItemStack(Material.AIR);
    }

    @Override
    public String getDataFromStack(ItemStack stack) {
        net.minecraft.server.v1_16_R3.ItemStack craftStack = CraftItemStack.asNMSCopy(stack);
        return craftStack.hasTag() ? craftStack.getTag().toString() : "";
    }

    @Override
    public ItemStack setDataToStack(ItemStack stack, String data) {
        net.minecraft.server.v1_16_R3.ItemStack craftStack = CraftItemStack.asNMSCopy(stack);
        try {
            craftStack.setTag(MojangsonParser.parse(data));
        } catch(CommandSyntaxException ignored) {}
        return CraftItemStack.asBukkitCopy(craftStack);
    }

    @Override
    public ISealNBT getNBTFromStack(ItemStack stack) {
        net.minecraft.server.v1_16_R3.ItemStack craftStack = org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack.asNMSCopy(stack);
        return craftStack.hasTag() ? SealNBT.get(craftStack.getTag()) : SealNBT.get(new net.minecraft.server.v1_16_R3.NBTTagCompound());
    }

    @Override
    public ISealNBT createNBT() {
        return SealNBT.get(new NBTTagCompound());
    }

    @Override
    public void setNBTToStack(ItemStack stack, ISealNBT nbt) {
        net.minecraft.server.v1_16_R3.ItemStack craftStack = org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack.asNMSCopy(stack);
        try {
            craftStack.setTag((net.minecraft.server.v1_16_R3.NBTTagCompound) nbt.toOriginal());
            stack.setItemMeta(org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack.getItemMeta(craftStack));
        } catch (Exception ignored) {}
    }

    @Override
    public String stackToJson(ISealStack stack) {
        JSONObject json = new JSONObject();
        json.put("item", ((ItemStack)stack.toOriginal()).getType().getKey().toString());
        json.put("data", stack.getData());
        json.put("amount", stack.getAmount());
        return json.toString();
    }

    @Override
    public String getItemRegistryName(ItemStack stack) {
        return stack.getType().getKey().toString();
    }

    @Override
    public int getItemMetaData(ItemStack stack) {
        return 0;
    }

    @Override
    public boolean hasKey(Object nbt, String key) {
        return ((NBTTagCompound)nbt).hasKey(key);
    }

    @Override
    public void set(Object nbt, String key, ISealNBT value) {
        ((NBTTagCompound)nbt).set(key, (NBTBase) value.toOriginal());
    }

    @Override
    public void setByte(Object nbt, String key, byte value) {
        ((NBTTagCompound)nbt).setByte(key, value);;
    }

    @Override
    public void setShort(Object nbt, String key, short value) {
        ((NBTTagCompound)nbt).setShort(key, value);
    }

    @Override
    public void setInt(Object nbt, String key, int value) {
        ((NBTTagCompound)nbt).setInt(key, value);
    }

    @Override
    public void setLong(Object nbt, String key, long value) {
        ((NBTTagCompound)nbt).setLong(key, value);
    }

    @Override
    public void setFloat(Object nbt, String key, float value) {
        ((NBTTagCompound)nbt).setFloat(key, value);
    }

    @Override
    public void setDouble(Object nbt, String key, double value) {
        ((NBTTagCompound)nbt).setDouble(key, value);
    }

    @Override
    public void setString(Object nbt, String key, String value) {
        ((NBTTagCompound)nbt).setString(key, value);
    }

    @Override
    public void setByteArray(Object nbt, String key, byte[] value) {
        ((NBTTagCompound)nbt).setByteArray(key, value);
    }

    @Override
    public void setIntArray(Object nbt, String key, int[] value) {
        ((NBTTagCompound)nbt).setIntArray(key, value);
    }

    @Override
    public void setBoolean(Object nbt, String key, boolean value) {
        ((NBTTagCompound)nbt).setBoolean(key, value);
    }

    @Override
    public ISealNBT get(Object nbt, String key) {
        return SealNBT.get(((NBTTagCompound)nbt).getCompound(key));
    }

    @Override
    public byte getByte(Object nbt, String key) {
        return ((NBTTagCompound)nbt).getByte(key);
    }

    @Override
    public short getShort(Object nbt, String key) {
        return ((NBTTagCompound)nbt).getShort(key);
    }

    @Override
    public int getInt(Object nbt, String key) {
        return ((NBTTagCompound)nbt).getInt(key);
    }

    @Override
    public long getLong(Object nbt, String key) {
        return ((NBTTagCompound)nbt).getLong(key);
    }

    @Override
    public float getFloat(Object nbt, String key) {
        return ((NBTTagCompound)nbt).getFloat(key);
    }

    @Override
    public double getDouble(Object nbt, String key) {
        return ((NBTTagCompound)nbt).getDouble(key);
    }

    @Override
    public String getString(Object nbt, String key) {
        return ((NBTTagCompound)nbt).getString(key);
    }

    @Override
    public byte[] getByteArray(Object nbt, String key) {
        return ((NBTTagCompound)nbt).getByteArray(key);
    }

    @Override
    public int[] getIntArray(Object nbt, String key) {
        return ((NBTTagCompound)nbt).getIntArray(key);
    }

    @Override
    public boolean getBoolean(Object nbt, String key) {
        return ((NBTTagCompound)nbt).getBoolean(key);
    }

    @Override
    public Set<String> getKeys(Object nbt) {
        return ((NBTTagCompound)nbt).getKeys();
    }

    @Override
    public void remove(Object nbt, String key) {
        ((NBTTagCompound)nbt).remove(key);
    }
}
