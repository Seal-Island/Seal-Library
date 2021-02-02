package com.focamacho.seallibrary.menu.lib;

import com.focamacho.seallibrary.player.ISealPlayer;

/**
 * Representação de um click
 * em um inventário.
 */
public interface IClick {

    /**
     * Retorna o jogador que efetuou
     * o click.
     * @return o jogador.
     */
    ISealPlayer getPlayer();

}
