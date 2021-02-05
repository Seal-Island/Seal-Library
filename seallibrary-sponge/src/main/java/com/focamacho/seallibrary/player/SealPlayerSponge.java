package com.focamacho.seallibrary.player;


import com.focamacho.seallibrary.SealLibrarySponge;
import com.focamacho.seallibrary.util.InventoryUtilsSponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.text.Text;

import java.util.UUID;

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
        InventoryUtilsSponge.openInventory(player, (Inventory) inventory, SealLibrarySponge.instance);
    }

    @Override
    public void closeInventory() {
        InventoryUtilsSponge.closeInventory(player, SealLibrarySponge.instance);
    }

}
