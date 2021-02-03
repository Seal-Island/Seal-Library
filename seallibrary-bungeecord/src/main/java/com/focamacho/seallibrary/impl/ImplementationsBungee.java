package com.focamacho.seallibrary.impl;

import com.focamacho.seallibrary.SealLibraryBungee;
import com.focamacho.seallibrary.logger.LoggerBungee;
import com.focamacho.seallibrary.logger.SealLogger;
import com.focamacho.seallibrary.permission.impl.PermissionHandlerLuckPerms;
import com.focamacho.seallibrary.player.SealPlayerBungee;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.PluginManager;
import org.bukkit.Bukkit;

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
        Implementations.playerGetter = player -> new SealPlayerBungee((ProxiedPlayer) player);

        /*
         * Implementação do sistema de manipulação de Economia.
         */
        Implementations.economyHandler = null;

        /*
         * Implementação do sistema de manipulação de Permissões.
         */
        if(pluginManager.getPlugin("LuckPerms") != null) Implementations.permissionHandler = new PermissionHandlerLuckPerms();
        else {
            SealLogger.error("Nenhum plugin de permissões compatível foi carregado.",
                    "Por favor, instale um dos seguintes plugins:",
                    "LuckPerms",
                    "O servidor será desligado para evitar problemas.");
            ProxyServer.getInstance().stop();
        }
    }

}
