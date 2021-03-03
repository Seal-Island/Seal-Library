package com.focamacho.seallibrary.server;

import com.focamacho.seallibrary.player.ISealPlayer;
import com.focamacho.seallibrary.player.SealPlayer;
import org.spongepowered.api.Server;
import org.spongepowered.api.Sponge;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SealServerSponge implements ISealServer {

    private final Server server;

    public SealServerSponge(Server server) {
        this.server = server;
    }

    @Override
    public Object toOriginal() {
        return this.server;
    }

    @Override
    public void runCommand(String command) {
        Sponge.getCommandManager().process(this.server.getConsole(), command);
    }

    @Override
    public ISealPlayer getPlayer(UUID uuid) {
        return SealPlayer.get(this.server.getPlayer(uuid));
    }

    @Override
    public List<ISealPlayer> getPlayers() {
        List<ISealPlayer> players = new ArrayList<>();
        this.server.getOnlinePlayers().forEach(player -> players.add(SealPlayer.get(player)));
        return players;
    }

    @Override
    public void shutdown() {
        this.server.shutdown();
    }

}
