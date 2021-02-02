package com.focamacho.seallibrary.impl;

import com.focamacho.seallibrary.SealLibrarySponge;
import com.focamacho.seallibrary.item.ISealStack;
import com.focamacho.seallibrary.item.SealStackSponge;
import com.focamacho.seallibrary.logger.LoggerSponge;
import com.focamacho.seallibrary.menu.MenuSponge;
import com.focamacho.seallibrary.player.SealPlayerSponge;
import com.focamacho.seallibrary.util.ItemStackUtils;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.plugin.PluginManager;

/**
 * Implementações dos sistemas da Seal Library
 * por meio do Sponge.
 */
public class ImplementationsSponge {

    public static void init() {
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
                return get(ItemStackUtils.getStackFromID(item));
            }
        };

        /*
         * Implementação do sistema de Menus.
         */
        Implementations.builder = MenuSponge::new;

        /*
         * Implementação do sistema de SealPlayers.
         */
        Implementations.getter = player -> new SealPlayerSponge((Player) player);

        /*
         * Implementação do sistema de manipulação de Permissões.
         */
        PluginManager pluginManager = Sponge.getPluginManager();
        if(pluginManager.isLoaded("luckperms")) Implementations.setPermissionHandler("luckperms");
    }

}
