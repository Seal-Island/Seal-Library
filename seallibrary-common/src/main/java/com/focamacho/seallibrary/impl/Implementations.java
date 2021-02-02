package com.focamacho.seallibrary.impl;

import com.focamacho.seallibrary.item.SealStack;
import com.focamacho.seallibrary.logger.ILogger;
import com.focamacho.seallibrary.menu.Menu;
import com.focamacho.seallibrary.permission.IPermissionHandler;
import com.focamacho.seallibrary.permission.impl.LuckPermsHandler;
import com.focamacho.seallibrary.player.SealPlayer;

/**
 * Classe contendo todas as
 * implementações efetuadas.
 */
public class Implementations {

    /**
     * ILogger utilizado para mensagens de
     * Logging.
     * @see ILogger
     */
    public static ILogger logger;

    /**
     * IStackBuilder utilizado para a obtenção
     * de SealStacks.
     * @see SealStack
     */
    public static ImpInterfaces.IStackBuilder stackBuilder;

    /**
     * MenuBuilder utilizado para a obtenção
     * de Menus.
     * @see Menu
     */
    public static ImpInterfaces.MenuBuilder builder;

    /**
     * ISealPlayerGetter utilizado para a obtenção
     * de SealPlayers.
     * @see SealPlayer
     */
    public static ImpInterfaces.ISealPlayerGetter getter;

    /**
     * IPermissionHandler utilizado para a manipualção
     * de permissões.
     */
    public static IPermissionHandler permissionHandler;

    /**
     *  Define qual é o plugin de permissão ativo
     *  no momento para consulta de valores.
     *  Esse valor é definido automaticamente pelo
     *  Seal Library.
     *
     *  Plugins compatíveis: luckperms
     *
     * @param plugin nome do plugin de permissões
     */
    public static void setPermissionHandler(String plugin) {
        if(plugin.equalsIgnoreCase("luckperms")) Implementations.permissionHandler = new LuckPermsHandler();
    }

}
