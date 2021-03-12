package com.focamacho.seallibrary.impl;

import com.focamacho.seallibrary.SealLibrarySponge;
import com.focamacho.seallibrary.chat.MessageWaiterListenerSponge;
import com.focamacho.seallibrary.chat.impl.ChatHandlerLuckPerms;
import com.focamacho.seallibrary.economy.EconomyHandlerSponge;
import com.focamacho.seallibrary.forge.ForgeUtilsSponge;
import com.focamacho.seallibrary.item.ISealStack;
import com.focamacho.seallibrary.item.SealStackSponge;
import com.focamacho.seallibrary.logger.LoggerSponge;
import com.focamacho.seallibrary.logger.SealLogger;
import com.focamacho.seallibrary.menu.MenuSponge;
import com.focamacho.seallibrary.permission.PermissionHandlerSponge;
import com.focamacho.seallibrary.permission.impl.PermissionHandlerLuckPerms;
import com.focamacho.seallibrary.player.ISealPlayer;
import com.focamacho.seallibrary.player.SealPlayerSponge;
import com.focamacho.seallibrary.server.SealServerSponge;
import com.focamacho.seallibrary.util.ItemStackUtilsSponge;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.plugin.PluginManager;

import java.util.Optional;
import java.util.UUID;

/**
 * Implementações dos sistemas da Seal Library
 * por meio do Sponge.
 */
public class ImplementationsSponge {

    private static final PluginManager pluginManager = Sponge.getPluginManager();

    public static void init() {
        /*
         * Implementação do sistema de manipulação de servidor.
         */
        Implementations.server = new SealServerSponge(Sponge.getServer());
        Sponge.getEventManager().registerListeners(SealLibrarySponge.instance, Implementations.server);

        /*
         * Implementação do sistema de Logging.
         */
        Implementations.logger = new LoggerSponge(SealLibrarySponge.instance.logger);

        /*
         * Implementação do sistema de SealStacks.
         */
        Implementations.stackBuilder = new ImpInterfaces.IStackBuilder() {
            @Override
            public ISealStack get(Object item) {
                return new SealStackSponge((ItemStack) item);
            }

            @Override
            public ISealStack get(String item) {
                return get(ItemStackUtilsSponge.getStackFromID(item));
            }
        };

        /*
         * Implementação do sistema de Menus.
         */
        Implementations.menuBuilder = MenuSponge::new;

        /*
         * Implementação do sistema de SealPlayers.
         */
        Implementations.playerGetter = new ImpInterfaces.ISealPlayerGetter() {
            @Override
            public ISealPlayer get(Object player) {
                return new SealPlayerSponge((Player) player);
            }

            @Override
            public Optional<ISealPlayer> get(UUID uuid) {
                Optional<Player> player = Sponge.getServer().getPlayer(uuid);
                return player.map(SealPlayerSponge::new);
            }
        };

        /*
         * Implementação do sistema de manipulação de Economia.
         */
        Implementations.economyHandler = new EconomyHandlerSponge();

        /*
         * Implementação do sistema de manipulação de Permissões.
         */
        if(pluginManager.isLoaded("luckperms")) Implementations.permissionHandler = new PermissionHandlerLuckPerms();
        else Implementations.permissionHandler = new PermissionHandlerSponge();

        /*
         * Implementação do sistema de manipulação de Chat.
         */
        if(pluginManager.isLoaded("luckperms")) Implementations.chatHandler = new ChatHandlerLuckPerms();
        else {
            SealLogger.error("Nenhum plugin de chat compatível foi carregado.",
                    "Por favor, instale um dos seguintes plugins:",
                    "LuckPerms",
                    "Algumas coisas não funcionarão corretamente até que um dos plugins acima seja instalado.");
        }
        Sponge.getEventManager().registerListeners(SealLibrarySponge.instance, new MessageWaiterListenerSponge());

        /*
         * Implementação do sistema de utilidades para compatibilidade com Forge.
         */
        if(pluginManager.isLoaded("forge")) Implementations.forgeUtils = new ForgeUtilsSponge();
    }

}
