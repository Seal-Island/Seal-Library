package com.focamacho.seallibrary.server;

import com.focamacho.seallibrary.command.ISealCommand;
import com.focamacho.seallibrary.command.lib.ISealCommandSender;
import com.focamacho.seallibrary.player.ISealPlayer;
import com.focamacho.seallibrary.player.SealPlayer;
import org.spongepowered.api.Server;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.command.SendCommandEvent;
import org.spongepowered.api.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SealServerSponge implements ISealServer {

    private final Server server;
    private final List<ISealCommand> commands = new ArrayList<>();

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
        Optional<Player> player = this.server.getPlayer(uuid);
        return player.map(SealPlayer::get).orElse(null);
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
        CommandSpec fakeCmd = CommandSpec.builder()
                .description(Text.of(command.getDescription()))
                .permission(command.getPermission())
                .executor((src, args) -> CommandResult.success()).build();

        Sponge.getCommandManager().register(plugin, fakeCmd, command.getAliases());
        this.commands.add(command);
    }

    @Override
    public String getConfigFolder() {
        return "./config/";
    }

    @Listener
    public void onCommand(SendCommandEvent event) {
        String cmd = event.getCommand();
        command:
        for (ISealCommand command : this.commands) {
            for (String alias : command.getAliases()) {
                if(!(event.getSource() instanceof CommandSource)) return;
                CommandSource src = (CommandSource) event.getSource();

                if(!src.hasPermission(command.getPermission())) break;

                if(cmd.equalsIgnoreCase(alias)) {
                    command.run(new ISealCommandSender() {
                        @Override
                        public boolean isConsole() {
                            return src instanceof ConsoleSource;
                        }

                        @Override
                        public boolean isPlayer() {
                            return src instanceof Player;
                        }

                        @Override
                        public Object get() {
                            return src;
                        }

                        @Override
                        public void sendMessage(String message) {
                            src.sendMessage(Text.of(message));
                        }
                    }, event.getArguments().split(" "));
                    break command;
                }
            }
        }
    }

}
