package com.focamacho.seallibrary.impl;

import com.focamacho.seallibrary.SealLibraryBukkit;
import com.focamacho.seallibrary.chat.ChatHandlerVault;
import com.focamacho.seallibrary.chat.MessageWaiterListenerBukkit;
import com.focamacho.seallibrary.chat.impl.ChatHandlerLuckPerms;
import com.focamacho.seallibrary.economy.EconomyHandlerVault;
import com.focamacho.seallibrary.item.ISealStack;
import com.focamacho.seallibrary.item.SealStackBukkit;
import com.focamacho.seallibrary.logger.LoggerBukkit;
import com.focamacho.seallibrary.logger.SealLogger;
import com.focamacho.seallibrary.menu.MenuBukkit;
import com.focamacho.seallibrary.permission.PermissionHandlerVault;
import com.focamacho.seallibrary.permission.impl.PermissionHandlerLuckPerms;
import com.focamacho.seallibrary.player.SealPlayerBukkit;
import com.focamacho.seallibrary.util.ItemStackUtilsBukkit;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;

/**
 * Implementações dos sistemas da Seal Library
 * por meio do Bukkit.
 */
public class ImplementationsBukkit {

    public static final PluginManager pluginManager = Bukkit.getPluginManager();

    public static void init() {
        /*
         * Implementação do sistema de Logging.
         */
        Implementations.logger = new LoggerBukkit(SealLibraryBukkit.instance.getLogger());

        /*
         * Implementação do sistema de SealStacks.
         */
        Implementations.stackBuilder = new ImpInterfaces.IStackBuilder() {
            @Override
            public ISealStack get(Object item) {
                return new SealStackBukkit((ItemStack) item);
            }

            @Override
            public ISealStack get(String item) {
                return get(ItemStackUtilsBukkit.getStackFromID(item));
            }
        };

        /*
         * Implementação do sistema de Menus.
         */
        Implementations.menuBuilder = MenuBukkit::new;

        /*
         * Implementação do sistema de SealPlayers.
         */
        Implementations.playerGetter = player -> new SealPlayerBukkit((Player) player);

        /*
         * Implementação do sistema de manipulação de Economia.
         */
        if(pluginManager.isPluginEnabled("vault")) Implementations.economyHandler = new EconomyHandlerVault();
        else {
            SealLogger.error("Nenhum plugin de economia compatível foi carregado.",
                    "Por favor, instale um dos seguintes plugins:",
                    "Vault",
                    "O servidor será desligado para evitar problemas.");
            Bukkit.getServer().shutdown();
        }

        /*
         * Implementação do sistema de manipulação de Permissões.
         */
        if(pluginManager.isPluginEnabled("luckperms")) Implementations.permissionHandler = new PermissionHandlerLuckPerms();
        else if(pluginManager.isPluginEnabled("vault")) Implementations.permissionHandler = new PermissionHandlerVault();
        else {
            SealLogger.error("Nenhum plugin de permissões compatível foi carregado.",
                    "Por favor, instale um dos seguintes plugins:",
                    "LuckPerms; Vault",
                    "O servidor será desligado para evitar problemas.");
            Bukkit.getServer().shutdown();
        }

        /*
         * Implementação do sistema de manipulação de Chat.
         */
        if(pluginManager.isPluginEnabled("luckperms")) Implementations.chatHandler = new ChatHandlerLuckPerms();
        else if(pluginManager.isPluginEnabled("vault")) Implementations.chatHandler = new ChatHandlerVault();
        else {
            SealLogger.error("Nenhum plugin de chat compatível foi carregado.",
                    "Por favor, instale um dos seguintes plugins:",
                    "LuckPerms; Vault",
                    "O servidor será desligado para evitar problemas.");
            Bukkit.getServer().shutdown();
        }
        pluginManager.registerEvents(new MessageWaiterListenerBukkit(), SealLibraryBukkit.instance);
    }

}
