package com.focamacho.seallibrary.util;

import net.minecraft.server.v1_12_R1.Item;
import net.minecraft.server.v1_12_R1.MinecraftKey;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

@SuppressWarnings("unused")
public class ItemStackUtilsBukkit {

    public static ItemStack getStackFromID(String id) {
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

            return CraftItemStack.asBukkitCopy(new net.minecraft.server.v1_12_R1.ItemStack(type, 1, value));
        } else if(split.length == 2) {
            Item type = Item.REGISTRY.get(new MinecraftKey(split[0], split[1]));
            if(type == null) return new ItemStack(Material.AIR);
            return new ItemStack(CraftItemStack.asBukkitCopy(new net.minecraft.server.v1_12_R1.ItemStack(type)));
        } else return new ItemStack(Material.AIR);
    }

}
