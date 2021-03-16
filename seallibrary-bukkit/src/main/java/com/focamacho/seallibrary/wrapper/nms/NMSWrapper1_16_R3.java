package com.focamacho.seallibrary.wrapper.nms;

import com.focamacho.seallibrary.item.ISealStack;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.v1_16_R3.MojangsonParser;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.json.JSONObject;

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
    public String stackToJson(ISealStack stack) {
        net.minecraft.server.v1_16_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy((ItemStack) stack.toOriginal());

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
}
