package com.focamacho.seallibrary.impl;

import com.focamacho.seallibrary.SealLibraryBungee;
import com.focamacho.seallibrary.logger.LoggerBungee;
import com.focamacho.seallibrary.player.SealPlayerBungee;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.PluginManager;

/**
 * Implementações dos sistemas da Seal Library
 * por meio do BungeeCord.
 */
public class ImplementationsBungee {

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
        Implementations.builder = null;

        /*
         * Implementação do sistema de SealPlayers.
         */
        Implementations.getter = player -> new SealPlayerBungee((ProxiedPlayer) player);

        /*
         * Implementação do sistema de manipulação de Permissões.
         */
        PluginManager pluginManager = ProxyServer.getInstance().getPluginManager();
        if(pluginManager.getPlugin("LuckPerms") != null) Implementations.setPermissionHandler("luckperms");
    }

}
