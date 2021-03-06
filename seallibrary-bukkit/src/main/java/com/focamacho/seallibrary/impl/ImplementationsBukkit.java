package com.focamacho.seallibrary.impl;

import com.focamacho.seallibrary.SealLibraryBukkit;
import com.focamacho.seallibrary.chat.ChatHandlerVault;
import com.focamacho.seallibrary.chat.MessageWaiterListenerBukkit;
import com.focamacho.seallibrary.chat.impl.ChatHandlerBungeePerms;
import com.focamacho.seallibrary.chat.impl.ChatHandlerLuckPerms;
import com.focamacho.seallibrary.economy.EconomyHandlerVault;
import com.focamacho.seallibrary.forge.ForgeUtilsBukkit;
import com.focamacho.seallibrary.item.ISealStack;
import com.focamacho.seallibrary.item.SealStackBukkit;
import com.focamacho.seallibrary.logger.LoggerBukkit;
import com.focamacho.seallibrary.logger.SealLogger;
import com.focamacho.seallibrary.menu.MenuBukkit;
import com.focamacho.seallibrary.nbt.ISealNBT;
import com.focamacho.seallibrary.nbt.SealNBTBukkit;
import com.focamacho.seallibrary.permission.PermissionHandlerVault;
import com.focamacho.seallibrary.permission.impl.PermissionHandlerBungeePerms;
import com.focamacho.seallibrary.permission.impl.PermissionHandlerLuckPerms;
import com.focamacho.seallibrary.player.ISealPlayer;
import com.focamacho.seallibrary.player.SealPlayerBukkit;
import com.focamacho.seallibrary.server.SealServerBukkit;
import com.focamacho.seallibrary.wrapper.nms.NMSWrapper;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.util.Optional;
import java.util.UUID;

/**
 * Implementações dos sistemas da Seal Library
 * por meio do Bukkit.
 */
public class ImplementationsBukkit {

    public static void init() {
        PluginManager pluginManager = Bukkit.getPluginManager();

        /*
         * Implementação do sistema de manipulação de servidor.
         */
        Implementations.server = new SealServerBukkit(Bukkit.getServer());

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
                return get(NMSWrapper.nmsWrapper.getStackFromString(item));
            }
        };

        /*
         * Implementação do sistema de Menus.
         */
        Implementations.menuBuilder = MenuBukkit::new;

        /*
         * Implementação do sistema de NBTs.
         */
        Implementations.nbtGetter = new ImpInterfaces.ISealNBTGetter() {
            @Override
            public ISealNBT create() {
                return NMSWrapper.nmsWrapper.createNBT();
            }

            @Override
            public ISealNBT get(Object nbt) {
                return new SealNBTBukkit(nbt);
            }
        };

        /*
         * Implementação do sistema de SealPlayers.
         */
        Implementations.playerGetter = new ImpInterfaces.ISealPlayerGetter() {
            @Override
            public ISealPlayer get(Object player) {
                return new SealPlayerBukkit((Player) player);
            }

            @Override
            public Optional<ISealPlayer> get(UUID uuid) {
                Player player = Bukkit.getServer().getPlayer(uuid);
                return player != null ? Optional.of(new SealPlayerBukkit(player)) : Optional.empty();
            }
        };

        /*
         * Implementação do sistema de utilidades para compatibilidade com Forge.
         */
        try {
            Class.forName("net.minecraftforge.common.MinecraftForge");
            Implementations.forgeUtils = new ForgeUtilsBukkit();
        } catch (ClassNotFoundException ignored) {}
    }

    /**
     * Algumas implementações precisam ser feitas
     * após o carregamento de outros plugins,
     * como permissões, chat e economia.
     */
    public static void postInit() {
        /*
         * Implementação do sistema de manipulação de Economia.
         */
        if(isPluginEnabled("vault")) Implementations.economyHandler = new EconomyHandlerVault();
        else {
            SealLogger.error("Nenhum plugin de economia compatível foi carregado.",
                    "Por favor, instale um dos seguintes plugins:",
                    "Vault",
                    "Algumas coisas não funcionarão corretamente até que um dos plugins acima seja instalado.");
        }

        /*
         * Implementação do sistema de manipulação de Permissões.
         */
        if(isPluginEnabled("luckperms")) Implementations.permissionHandler = new PermissionHandlerLuckPerms();
        else if(isPluginEnabled("bungeeperms")) Implementations.permissionHandler = new PermissionHandlerBungeePerms();
        else if(isPluginEnabled("vault")) Implementations.permissionHandler = new PermissionHandlerVault();
        else {
            SealLogger.error("Nenhum plugin de permissões compatível foi carregado.",
                    "Por favor, instale um dos seguintes plugins:",
                    "LuckPerms; BungeePerms; Vault",
                    "Algumas coisas não funcionarão corretamente até que um dos plugins acima seja instalado.");
        }

        /*
         * Implementação do sistema de manipulação de Chat.
         */
        if(isPluginEnabled("luckperms")) Implementations.chatHandler = new ChatHandlerLuckPerms();
        else if(isPluginEnabled("bungeeperms")) Implementations.chatHandler = new ChatHandlerBungeePerms();
        else if(isPluginEnabled("vault")) Implementations.chatHandler = new ChatHandlerVault();
        else {
            SealLogger.error("Nenhum plugin de chat compatível foi carregado.",
                    "Por favor, instale um dos seguintes plugins:",
                    "LuckPerms; BungeePerms; Vault",
                    "Algumas coisas não funcionarão corretamente até que um dos plugins acima seja instalado.");
        }
        Bukkit.getPluginManager().registerEvents(new MessageWaiterListenerBukkit(), SealLibraryBukkit.instance);
    }

    private static boolean isPluginEnabled(String plugin) {
        for (Plugin pl : Bukkit.getPluginManager().getPlugins()) {
            if(pl.getName().equalsIgnoreCase(plugin)) return true;
        }
        return false;
    }

}
