package com.focamacho.seallibrary.server;

import com.focamacho.seallibrary.player.ISealPlayer;
import com.focamacho.seallibrary.player.SealPlayer;
import net.md_5.bungee.api.ProxyServer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SealServerBungee implements ISealServer {

    private final ProxyServer server;

    public SealServerBungee(ProxyServer server) {
        this.server = server;
    }

    @Override
    public Object toOriginal() {
        return this.server;
    }

    @Override
    public void runCommand(String command) {
        this.server.getPluginManager().dispatchCommand(this.server.getConsole(), command);
    }

    @Override
    public ISealPlayer getPlayer(UUID uuid) {
        return SealPlayer.get(this.server.getPlayer(uuid));
    }

    @Override
    public List<ISealPlayer> getPlayers() {
        List<ISealPlayer> players = new ArrayList<>();
        this.server.getPlayers().forEach(player -> players.add(SealPlayer.get(player)));
        return players;
    }

    @Override
    public void shutdown() {
        this.server.stop();
    }

}
