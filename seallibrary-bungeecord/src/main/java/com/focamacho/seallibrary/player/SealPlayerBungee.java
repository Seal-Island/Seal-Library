package com.focamacho.seallibrary.player;

import com.focamacho.seallibrary.item.ISealStack;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.List;
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
    public String getName() {
        return player.getName();
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
    public void runCommand(String command) {
        ProxyServer.getInstance().getPluginManager().dispatchCommand(this.player, command);
    }

    //Métodos não disponíveis para BungeeCord
    @Override
    public void openInventory(Object inventory) {}

    @Override
    public void closeInventory() {}

    @Override
    public boolean hasItems(ISealStack item, int amount) {
        return false;
    }

    @Override
    public void removeItems(ISealStack item, int amount) {}

    @Override
    public List<ISealStack> giveItems(ISealStack... items) {
        return null;
    }

    @Override
    public void giveOrDropItems(ISealStack... items) {}

    @Override
    public List<ISealStack> getInventory() {
        return null;
    }

    @Override
    public ISealStack getMainHand() {
        return null;
    }

    @Override
    public ISealStack getOffHand() {
        return null;
    }

}
