package com.focamacho.seallibrary.server;

import com.focamacho.seallibrary.command.ISealCommand;
import com.focamacho.seallibrary.command.lib.ISealCommandSender;
import com.focamacho.seallibrary.player.ISealPlayer;
import com.focamacho.seallibrary.player.SealPlayer;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;

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

    @Override
    public void registerCommand(Object plugin, ISealCommand command) {
        Command cmd = new Command(command.getName()) {
            @Override
            public String[] getAliases() {
                return command.getAliases();
            }

            @Override
            public String getPermission() {
                return command.getPermission();
            }

            @Override
            public void execute(CommandSender commandSender, String[] args) {
                ISealCommandSender sender = new ISealCommandSender() {
                    @Override
                    public boolean isConsole() {
                        return commandSender.equals(ProxyServer.getInstance().getConsole());
                    }

                    @Override
                    public boolean isPlayer() {
                        return commandSender instanceof ProxiedPlayer;
                    }

                    @Override
                    public Object get() {
                        return commandSender;
                    }

                    @Override
                    public void sendMessage(String message) {
                        commandSender.sendMessage(new TextComponent(message));
                    }
                };

                command.run(sender, args);
            }
        };

        ProxyServer.getInstance().getPluginManager().registerCommand((Plugin) plugin, cmd);
    }

    @Override
    public String getConfigFolder() {
        return "./plugins/";
    }

}
