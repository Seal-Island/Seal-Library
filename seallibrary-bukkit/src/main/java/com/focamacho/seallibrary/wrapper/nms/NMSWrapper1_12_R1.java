package com.focamacho.seallibrary.wrapper.nms;

import com.focamacho.seallibrary.item.ISealStack;
import net.minecraft.server.v1_12_R1.Item;
import net.minecraft.server.v1_12_R1.MinecraftKey;
import net.minecraft.server.v1_12_R1.MojangsonParseException;
import net.minecraft.server.v1_12_R1.MojangsonParser;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.json.JSONObject;

import java.util.regex.Pattern;

public class NMSWrapper1_12_R1 extends NMSWrapper {

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
            Item type = Item.REGISTRY.get(new MinecraftKey(split[0], split[1]));
            if(type == null) return new ItemStack(Material.AIR);

            short value;

            try {
                value = Short.parseShort(split[2]);
            } catch(NumberFormatException e) {
                return new ItemStack(Material.AIR);
            }

            ItemStack stack = CraftItemStack.asBukkitCopy(new net.minecraft.server.v1_12_R1.ItemStack(type, 1, value));

            if(!data.isEmpty()) {
                stack = setDataToStack(stack, data);
            }

            return stack;
        } else if(split.length == 2) {
            Item type = Item.REGISTRY.get(new MinecraftKey(split[0], split[1]));
            if(type == null) return new ItemStack(Material.AIR);
            ItemStack stack = new ItemStack(CraftItemStack.asBukkitCopy(new net.minecraft.server.v1_12_R1.ItemStack(type)));

            if(!data.isEmpty()) {
                stack = setDataToStack(stack, data);
            }

            return stack;
        } else return new ItemStack(Material.AIR);
    }

    @Override
    public String getDataFromStack(ItemStack stack) {
        net.minecraft.server.v1_12_R1.ItemStack craftStack = CraftItemStack.asNMSCopy(stack);
        return craftStack.hasTag() ? craftStack.getTag().toString() : "";
    }

    @Override
    public ItemStack setDataToStack(ItemStack stack, String data) {
        net.minecraft.server.v1_12_R1.ItemStack craftStack = CraftItemStack.asNMSCopy(stack);
        try {
            craftStack.setTag(MojangsonParser.parse(data));
        } catch (MojangsonParseException ignored) {}
        return CraftItemStack.asBukkitCopy(craftStack);
    }

    @Override
    public String stackToJson(ISealStack stack) {
        net.minecraft.server.v1_12_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy((ItemStack) stack.toOriginal());

        JSONObject json = new JSONObject();
        json.put("item", Item.REGISTRY.b(nmsStack.getItem()).toString() + (nmsStack.getData() != 0 ? ":" + nmsStack.getData() : ""));
        json.put("data", stack.getData());
        json.put("amount", stack.getAmount());

        return json.toString();
    }

    @Override
    public String getItemRegistryName(ItemStack stack) {
        net.minecraft.server.v1_12_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(stack);
        MinecraftKey registry = Item.REGISTRY.b(nmsStack.getItem());
        return registry != null ? registry.toString() : "";
    }
}
