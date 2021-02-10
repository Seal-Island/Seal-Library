package com.focamacho.seallibrary.menu.lib;

import com.focamacho.seallibrary.item.ISealStack;
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

    /**
     * Retorna o item que está atualmente no
     * slot clicado.
     * @return o item no slot clicado, pode ser null.
     */
    public abstract ISealStack getItem();

    /**
     * Retorna o item no cursor
     * do jogador.
     * @return o item no cursor, pode ser null.
     */
    public abstract ISealStack getCursor();

    /**
     * Retorna o index do slot
     * que foi clicado.
     * @return o index do slot clicado.
     */
    public abstract int getSlot();

}
