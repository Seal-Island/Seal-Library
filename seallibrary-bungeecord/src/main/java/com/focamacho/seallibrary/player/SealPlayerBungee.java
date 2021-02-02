package com.focamacho.seallibrary.player;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.UUID;

@SuppressWarnings("unused")
public class SealPlayerBungee implements ISealPlayer {

    private final ProxiedPlayer player;

    public SealPlayerBungee(ProxiedPlayer player) {
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
        player.sendMessage(new TextComponent(message));
    }

    @Override
    public void kick(String message) {
        player.disconnect(new TextComponent(message));
    }

    @Override
    public void openInventory(Object inventory) {}

    @Override
    public void closeInventory() {}

}
