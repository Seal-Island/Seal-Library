package com.focamacho.seallibrary.util;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;

import java.util.Optional;

@SuppressWarnings("unused")
public class ItemStackUtilsSponge {

    public static ItemStack getStackFromID(String id) {
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

            return ItemStack.builder().fromContainer(type.get().getTemplate().toContainer().set(DataQuery.of("UnsafeDamage"), value)).build();
        } else if(split.length == 2) {
            Optional<ItemType> type = Sponge.getRegistry().getType(ItemType.class, split[0] + ":" + split[1]);
            return type.map(itemType -> ItemStack.builder().itemType(itemType).build()).orElseGet(ItemStackSnapshot.NONE::createStack);
        } else return ItemStackSnapshot.NONE.createStack();
    }

}
