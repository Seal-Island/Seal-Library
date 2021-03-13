package com.focamacho.seallibrary.util;

import net.minecraft.server.v1_12_R1.Item;
import net.minecraft.server.v1_12_R1.MinecraftKey;
import net.minecraft.server.v1_12_R1.MojangsonParseException;
import net.minecraft.server.v1_12_R1.MojangsonParser;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import java.util.regex.Pattern;

@SuppressWarnings("unused")
public class ItemStackUtilsBukkit {

    public static ItemStack getStackFromID(String id) {
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
                stack = setData(stack, data);
            }

            return stack;
        } else if(split.length == 2) {
            Item type = Item.REGISTRY.get(new MinecraftKey(split[0], split[1]));
            if(type == null) return new ItemStack(Material.AIR);
            ItemStack stack = new ItemStack(CraftItemStack.asBukkitCopy(new net.minecraft.server.v1_12_R1.ItemStack(type)));

            if(!data.isEmpty()) {
                stack = setData(stack, data);
            }

            return stack;
        } else return new ItemStack(Material.AIR);
    }

    private static ItemStack setData(ItemStack stack, String data) {
        net.minecraft.server.v1_12_R1.ItemStack craftStack = CraftItemStack.asNMSCopy(stack);
        try {
            craftStack.setTag(MojangsonParser.parse(data));
        } catch (MojangsonParseException ignored) {}
        return CraftItemStack.asBukkitCopy(craftStack);
    }

}
