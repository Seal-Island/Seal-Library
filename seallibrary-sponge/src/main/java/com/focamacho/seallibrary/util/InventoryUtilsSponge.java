package com.focamacho.seallibrary.util;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.Item;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.Slot;
import org.spongepowered.api.scheduler.Task;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class InventoryUtilsSponge {

    public static void closeInventory(Player player, Object plugin) {
        Task.builder().execute(player::closeInventory).submit(plugin);
    }

    public static void openInventory(Player player, Inventory inventory, Object plugin) {
        Task.builder().execute(() -> player.openInventory(inventory)).submit(plugin);
    }

    public static void giveOrDropItems(Player player, ItemStack item, int count) {
        List<ItemStack> toGive = new ArrayList<>();
        int toGiveCount = count;

        while(toGiveCount > 0) {
            ItemStack stack = item.copy();
            if(toGiveCount > stack.getMaxStackQuantity()) {
                stack.setQuantity(stack.getMaxStackQuantity());
                toGiveCount -= stack.getMaxStackQuantity();
            } else {
                stack.setQuantity(toGiveCount);
                toGiveCount = 0;
            }
            toGive.add(stack);
        }

        toGive.forEach(itemGive ->
                player.getInventory().offer(itemGive).getRejectedItems().forEach(itemSnapshot -> {
                    Item itemEntity = (Item) player.getWorld().createEntity(EntityTypes.ITEM, player.getPosition());
                    itemEntity.offer(Keys.REPRESENTED_ITEM, itemSnapshot);
                    player.getWorld().spawnEntity(itemEntity);
                }));
    }

    public static boolean hasItems(Player player, ItemStack item, int amount) {
        int itemsInInventory = 0;

        for(Object slt : player.getInventory().slots()) {
            Slot slot = (Slot) slt;
            if(slot.peek().isPresent()) {
                ItemStack stack = slot.peek().get();
                ItemStack toCompare = stack.copy();
                toCompare.setQuantity(1);
                if(toCompare.equalTo(item)) itemsInInventory += stack.getQuantity();
            }
        }

        return itemsInInventory >= amount;
    }

    public static void removeItemsInInventory(Player player, ItemStack item, int amount) {
        int toRemove = amount;

        for(Object slt : player.getInventory().slots()) {
            if(toRemove <= 0) break;
            Slot slot = (Slot) slt;
            if(slot.peek().isPresent()) {
                ItemStack stack = slot.peek().get();
                ItemStack toCompare = stack.copy();
                toCompare.setQuantity(1);
                if(toCompare.equalTo(item)) {
                    if(stack.getQuantity() >= toRemove) {
                        if(stack.getQuantity() - amount == 0) slot.clear();
                        else slot.poll(toRemove);
                        toRemove = 0;
                    } else {
                        toRemove -= stack.getQuantity();
                        slot.clear();
                    }
                }
            }
        }
    }
}
