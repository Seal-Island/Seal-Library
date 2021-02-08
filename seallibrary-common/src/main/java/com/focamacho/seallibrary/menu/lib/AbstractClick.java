package com.focamacho.seallibrary.menu.lib;

import com.focamacho.seallibrary.player.ISealPlayer;
import lombok.Getter;
import lombok.Setter;

/**
 * Representação de um click
 * em um inventário.
 */
public abstract class AbstractClick {

    @Getter @Setter boolean cancelled = true;

    /**
     * Retorna o jogador que efetuou
     * o click.
     * @return o jogador.
     */
    public abstract ISealPlayer getPlayer();

    /**
     * Retorna o inventário em que a ação
     * foi efetuada.
     * @return o inventário.
     */
    public abstract Object getInventory();

}
