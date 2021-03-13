package com.focamacho.seallibrary.player;


import com.focamacho.seallibrary.SealLibrarySponge;
import com.focamacho.seallibrary.item.ISealStack;
import com.focamacho.seallibrary.item.SealStack;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.Item;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.item.inventory.Slot;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@SuppressWarnings("unused")
public class SealPlayerSponge implements ISealPlayer {

    private final Player player;

    public SealPlayerSponge(Player player) {
        this.player = player;
    }

    @Override
    public Object toOriginal() {
        return player;
    }

    @Override
    public UUID getUUID() {
        return player.getUniqueId();
    }

    @Override
    public String getName() {
        return player.getName();
    }

    @Override
    public void sendMessage(String message) {
        player.sendMessage(Text.of(message));
    }

    @Override
    public void kick(String message) {
        player.kick(Text.of(message));
    }

    @Override
    public void openInventory(Object inventory) {
        Task.builder().execute(() -> player.openInventory((Inventory) inventory)).submit(SealLibrarySponge.instance);
    }

    @Override
    public void closeInventory() {
        Task.builder().execute(player::closeInventory).submit(SealLibrarySponge.instance);
    }

    @Override
    public boolean hasItems(ISealStack item, int amount) {
        int itemsInInventory = 0;
        for(ISealStack stack : this.getInventory()) {
            if(stack.equalsIgnoreAmount(item)) itemsInInventory += stack.getAmount();
        }
        return itemsInInventory >= amount;
    }

    @Override
    public boolean hasAndRemoveItems(ISealStack item, int amount) {
        try {
            return Sponge.getScheduler().createSyncExecutor(SealLibrarySponge.instance).submit(() -> {
                if(hasItems(item, amount)) {
                    int toRemove = amount;
                    for(Object slt : player.getInventory().slots()) {
                        if(toRemove <= 0) break;
                        Slot slot = (Slot) slt;
                        if(slot.peek().isPresent()) {
                            ISealStack stack = SealStack.get(slot.peek().get());
                            if(stack.equalsIgnoreAmount(item)) {
                                if(stack.getAmount() >= toRemove) {
                                    if(stack.getAmount() - amount == 0) slot.clear();
                                    else slot.poll(toRemove);
                                    toRemove = 0;
                                } else {
                                    toRemove -= stack.getAmount();
                                    slot.clear();
                                }
                            }
                        }
                    }
                }

                return false;
            }).get();
        } catch (InterruptedException | ExecutionException ignored) { return false; }
    }

    @Override
    public void removeItems(ISealStack item, int amount) {
        Task.builder().execute(() -> {
            int toRemove = amount;
            for(Object slt : player.getInventory().slots()) {
                if(toRemove <= 0) break;
                Slot slot = (Slot) slt;
                if(slot.peek().isPresent()) {
                    ISealStack stack = SealStack.get(slot.peek().get());
                    if(stack.equalsIgnoreAmount(item)) {
                        if(stack.getAmount() >= toRemove) {
                            if(stack.getAmount() - amount == 0) slot.clear();
                            else slot.poll(toRemove);
                            toRemove = 0;
                        } else {
                            toRemove -= stack.getAmount();
                            slot.clear();
                        }
                    }
                }
            }
        }).submit(SealLibrarySponge.instance);
    }

    @Override
    public List<ISealStack> giveItems(ISealStack... items) {
        try {
            return Sponge.getScheduler().createSyncExecutor(SealLibrarySponge.instance).submit(() -> {
                List<ISealStack> rejected = new ArrayList<>();
                for (ISealStack item : items) {
                    player.getInventory().offer((ItemStack) item.toOriginal()).getRejectedItems().forEach(stack -> rejected.add(SealStack.get(stack.createStack())));
                }
                return rejected;
            }).get();
        } catch (InterruptedException | ExecutionException ignored) { return Collections.emptyList(); }
    }

    @Override
    public void giveOrDropItems(ISealStack... items) {
        giveItems(items).forEach(rejected -> {
            Item itemEntity = (Item) player.getWorld().createEntity(EntityTypes.ITEM, player.getPosition());
            itemEntity.offer(Keys.REPRESENTED_ITEM, ((ItemStack) rejected.toOriginal()).createSnapshot());
            player.getWorld().spawnEntity(itemEntity);
        });
    }

    @Override
    public List<ISealStack> getInventory() {
        List<ISealStack> allItems = new ArrayList<>();
        for(Object slt : player.getInventory().slots()) {
            Slot slot = (Slot) slt;
            if(slot.peek().isPresent()) {
                allItems.add(SealStack.get(slot.peek().get()));

            }
        }
        return allItems;
    }

    @Override
    public ISealStack getMainHand() {
        return SealStack.get(this.player.getItemInHand(HandTypes.MAIN_HAND).orElseGet(ItemStackSnapshot.NONE::createStack));
    }

    @Override
    public ISealStack getOffHand() {
        return SealStack.get(this.player.getItemInHand(HandTypes.OFF_HAND).orElseGet(ItemStackSnapshot.NONE::createStack));
    }

    @Override
    public void runCommand(String command) {
        Sponge.getCommandManager().process(this.player, command);
    }

}
