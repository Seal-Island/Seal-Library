package com.focamacho.seallibrary.player;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

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
    public void sendMessage(String message) {
        player.sendMessage(message);
    }

    @Override
    public void kick(String message) {
        player.kickPlayer(message);
    }

    @Override
    public void openInventory(Object inventory) {
        player.openInventory((Inventory) inventory);
    }

    @Override
    public void closeInventory() {
        player.closeInventory();
    }

}
