package com.focamacho.seallibrary.impl;

import com.focamacho.seallibrary.SealLibraryBukkit;
import com.focamacho.seallibrary.item.ISealStack;
import com.focamacho.seallibrary.item.SealStackBukkit;
import com.focamacho.seallibrary.logger.LoggerBukkit;
import com.focamacho.seallibrary.menu.MenuBukkit;
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
        Implementations.builder = MenuBukkit::new;

        /*
         * Implementação do sistema de SealPlayers.
         */
        Implementations.getter = player -> new SealPlayerBukkit((Player) player);

        /*
         * Implementação do sistema de manipulação de Permissões.
         */
        PluginManager pluginManager = Bukkit.getPluginManager();
        if(pluginManager.isPluginEnabled("luckperms")) Implementations.setPermissionHandler("luckperms");
    }

}
