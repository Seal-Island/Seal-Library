package com.focamacho.seallibrary.impl;

import com.focamacho.seallibrary.economy.IEconomyHandler;
import com.focamacho.seallibrary.item.SealStack;
import com.focamacho.seallibrary.logger.ILogger;
import com.focamacho.seallibrary.menu.Menu;
import com.focamacho.seallibrary.permission.IPermissionHandler;
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
    public static ImpInterfaces.MenuBuilder menuBuilder;

    /**
     * ISealPlayerGetter utilizado para a obtenção
     * de SealPlayers.
     * @see SealPlayer
     */
    public static ImpInterfaces.ISealPlayerGetter playerGetter;

    /**
     * IEconomyHandler utilizado para a manipulação
     * de economia.
     */
    public static IEconomyHandler economyHandler;

    /**
     * IPermissionHandler utilizado para a manipualção
     * de permissões.
     */
    public static IPermissionHandler permissionHandler;

}
