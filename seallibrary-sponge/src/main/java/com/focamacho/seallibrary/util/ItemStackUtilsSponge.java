package com.focamacho.seallibrary.util;

import com.focamacho.seallibrary.forge.ForgeUtils;
import net.minecraft.nbt.JsonToNBT;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;

import java.util.Optional;
import java.util.regex.Pattern;

@SuppressWarnings("unused")
public class ItemStackUtilsSponge {

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
            Optional<ItemType> type = Sponge.getRegistry().getType(ItemType.class, split[0] + ":" + split[1]);
            if(!type.isPresent()) return ItemStackSnapshot.NONE.createStack();

            int value;

            try {
                value = Integer.parseInt(split[2]);
            } catch(NumberFormatException e) {
                return ItemStackSnapshot.NONE.createStack();
            }

            ItemStack stack = ItemStack.builder().fromContainer(type.get().getTemplate().toContainer().set(DataQuery.of("UnsafeDamage"), value)).build();

            if(!data.isEmpty()) {
                stack = setData(stack, data);
            }

            return stack;
        } else if(split.length == 2) {
            Optional<ItemType> type = Sponge.getRegistry().getType(ItemType.class, split[0] + ":" + split[1]);
            ItemStack stack = type.map(itemType -> ItemStack.builder().itemType(itemType).build()).orElseGet(ItemStackSnapshot.NONE::createStack);
            if(!data.isEmpty()) {
                stack = setData(stack, data);
            }
            return stack;
        } else return ItemStackSnapshot.NONE.createStack();
    }

    private static ItemStack setData(ItemStack stack, String data) {
        net.minecraft.item.ItemStack item = (net.minecraft.item.ItemStack) ForgeUtils.getForgeStack(stack);
        try {
            item.setTagCompound(JsonToNBT.getTagFromJson(data));
        } catch(Exception ignored) {}
        return (ItemStack) ForgeUtils.getServerStack(item).toOriginal();
    }

}
