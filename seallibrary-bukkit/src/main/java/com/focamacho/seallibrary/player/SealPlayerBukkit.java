package com.focamacho.seallibrary.player;

import com.focamacho.seallibrary.SealLibraryBukkit;
import com.focamacho.seallibrary.item.ISealStack;
import com.focamacho.seallibrary.item.SealStack;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("unused")
public class SealPlayerBukkit implements ISealPlayer {

    private final Player player;

    public SealPlayerBukkit(Player player) {
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
        player.sendMessage(message);
    }

    @Override
    public void kick(String message) {
        player.kickPlayer(message);
    }

    @Override
    public void openInventory(Object inventory) {
        Bukkit.getScheduler().callSyncMethod(SealLibraryBukkit.instance, () -> player.openInventory((Inventory) inventory));
    }

    @Override
    public void closeInventory() {
        Bukkit.getScheduler().callSyncMethod(SealLibraryBukkit.instance, () -> {
            player.closeInventory();
            return true;
        });
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
    public void removeItems(ISealStack item, int amount) {
        int toRemove = amount;
        for(ItemStack slt : player.getInventory().getContents()) {
            if(toRemove <= 0) break;
            ISealStack stack = SealStack.get(slt);
            if(stack.equalsIgnoreAmount(item)) {
                if(stack.getAmount() >= toRemove) {
                    if(stack.getAmount() - amount == 0) player.getInventory().remove(slt);
                    else stack.setAmount(stack.getAmount() - toRemove);
                    toRemove = 0;
                } else {
                    toRemove -= stack.getAmount();
                    player.getInventory().removeItem(slt);
                }
            }
        }
    }

    @Override
    public List<ISealStack> giveItems(ISealStack... items) {
        List<ISealStack> rejected = new ArrayList<>();
        for (ISealStack item : items) {
            player.getInventory().addItem((ItemStack) item.toOriginal()).forEach((index, stack) -> rejected.add(SealStack.get(stack)));
        }
        return rejected;
    }

    @Override
    public void giveOrDropItems(ISealStack... items) {
        giveItems(items).forEach(rejected -> player.getWorld().dropItem(player.getLocation(), (ItemStack) rejected.toOriginal()));
    }

    @Override
    public List<ISealStack> getInventory() {
        List<ISealStack> allItems = new ArrayList<>();
        for (ItemStack content : player.getInventory().getContents()) {
            allItems.add(SealStack.get(content));
        }
        return allItems;
    }

    @Override
    public ISealStack getMainHand() {
        return SealStack.get(this.player.getInventory().getItemInMainHand());
    }

    @Override
    public ISealStack getOffHand() {
        return SealStack.get(this.player.getInventory().getItemInOffHand());
    }

    @Override
    public void runCommand(String command) {
        Bukkit.getServer().dispatchCommand(this.player, command);
    }

}
