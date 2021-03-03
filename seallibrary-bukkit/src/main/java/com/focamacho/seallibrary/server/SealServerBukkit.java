package com.focamacho.seallibrary.server;

import com.focamacho.seallibrary.command.ISealCommand;
import com.focamacho.seallibrary.command.lib.ISealCommandSender;
import com.focamacho.seallibrary.player.ISealPlayer;
import com.focamacho.seallibrary.player.SealPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SealServerBukkit implements ISealServer {

    private final Server server;

    public SealServerBukkit(Server server) {
        this.server = server;
    }

    @Override
    public Object toOriginal() {
        return this.server;
    }

    @Override
    public void runCommand(String command) {
        this.server.dispatchCommand(this.server.getConsoleSender(), command);
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

    @Override
    public void registerCommand(Object plugin, ISealCommand command) {
        Command cmd = new Command(command.getName()) {
            private final List<String> aliases = new ArrayList<>(java.util.Arrays.asList(command.getAliases()));

            @Override
            public List<String> getAliases() {
                return this.aliases;
            }

            @Override
            public String getPermission() {
                return command.getPermission();
            }

            @Override
            public String getDescription() {
                return command.getDescription();
            }

            @Override
            public boolean execute(CommandSender commandSender, String s, String[] args) {
                ISealCommandSender sender = new ISealCommandSender() {
                    @Override
                    public boolean isConsole() {
                        return commandSender instanceof ConsoleCommandSender;
                    }

                    @Override
                    public boolean isPlayer() {
                        return commandSender instanceof Player;
                    }

                    @Override
                    public Object get() {
                        return commandSender;
                    }

                    @Override
                    public void sendMessage(String message) {
                        commandSender.sendMessage(message);
                    }
                };

                command.run(sender, args);
                return true;
            }
        };

        registerCmd(cmd);
    }

    @Override
    public String getConfigFolder() {
        return "./plugins/";
    }

    private void registerCmd(Command command) {
        try {
            Field commandMapField = Bukkit.getPluginManager().getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);

            Object commandMapObject = commandMapField.get(Bukkit.getPluginManager());
            if (commandMapObject instanceof CommandMap) {
                CommandMap commandMap = (CommandMap) commandMapObject;
                commandMap.register(command.getName(), command);
            }
        } catch(IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

}
