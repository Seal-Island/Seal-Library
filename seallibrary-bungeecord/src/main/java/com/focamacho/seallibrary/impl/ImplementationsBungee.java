package com.focamacho.seallibrary.impl;

import com.focamacho.seallibrary.SealLibraryBungee;
import com.focamacho.seallibrary.chat.MessageWaiterListenerBungee;
import com.focamacho.seallibrary.chat.impl.ChatHandlerBungeePerms;
import com.focamacho.seallibrary.chat.impl.ChatHandlerLuckPerms;
import com.focamacho.seallibrary.logger.LoggerBungee;
import com.focamacho.seallibrary.logger.SealLogger;
import com.focamacho.seallibrary.permission.impl.PermissionHandlerBungeePerms;
import com.focamacho.seallibrary.permission.impl.PermissionHandlerLuckPerms;
import com.focamacho.seallibrary.player.ISealPlayer;
import com.focamacho.seallibrary.player.SealPlayerBungee;
import com.focamacho.seallibrary.server.SealServerBungee;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.PluginManager;

import java.util.Optional;
import java.util.UUID;

/**
 * Implementações dos sistemas da Seal Library
 * por meio do BungeeCord.
 */
public class ImplementationsBungee {

    private static final PluginManager pluginManager = ProxyServer.getInstance().getPluginManager();

    public static void init() {
        /*
         * Implementação do sistema de Logging.
         */
        Implementations.logger = new LoggerBungee(SealLibraryBungee.instance.getLogger());

        /*
         * Implementação do sistema de SealStacks.
         */
        Implementations.stackBuilder = null;

        /*
         * Implementação do sistema de Menus.
         */
        Implementations.menuBuilder = null;

        /*
         * Implementação do sistema de SealPlayers.
         */
        Implementations.playerGetter = new ImpInterfaces.ISealPlayerGetter() {
            @Override
            public ISealPlayer get(Object player) {
                return new SealPlayerBungee((ProxiedPlayer) player);
            }

            @Override
            public Optional<ISealPlayer> get(UUID uuid) {
                ProxiedPlayer player = ProxyServer.getInstance().getPlayer(uuid);
                return player != null ? Optional.of(new SealPlayerBungee(player)) : Optional.empty();
            }
        };

        /*
         * Implementação do sistema de manipulação de Economia.
         */
        Implementations.economyHandler = null;

        /*
         * Implementação do sistema de manipulação de Permissões.
         */
        if(pluginManager.getPlugin("LuckPerms") != null) Implementations.permissionHandler = new PermissionHandlerLuckPerms();
        else if(pluginManager.getPlugin("BungeePerms") != null) Implementations.permissionHandler = new PermissionHandlerBungeePerms();
        else {
            SealLogger.error("Nenhum plugin de permissões compatível foi carregado.",
                    "Por favor, instale um dos seguintes plugins:",
                    "LuckPerms, BungeePerms",
                    "O servidor será desligado para evitar problemas.");
            ProxyServer.getInstance().stop();
        }

        /*
         * Implementação do sistema de manipulação de Chat.
         */
        if(pluginManager.getPlugin("LuckPerms") != null) Implementations.chatHandler = new ChatHandlerLuckPerms();
        else if(pluginManager.getPlugin("BungeePerms") != null) Implementations.chatHandler = new ChatHandlerBungeePerms();
        else {
            SealLogger.error("Nenhum plugin de chat compatível foi carregado.",
                    "Por favor, instale um dos seguintes plugins:",
                    "LuckPerms, BungeePerms",
                    "O servidor será desligado para evitar problemas.");
            ProxyServer.getInstance().stop();
        }
        pluginManager.registerListener(SealLibraryBungee.instance, new MessageWaiterListenerBungee());

        /*
         * Implementação do sistema de manipulação de servidor.
         */
        Implementations.server = new SealServerBungee(ProxyServer.getInstance());

    }

}
