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
     * Faz com que a execução de outros
     * clicks no menu seja cancelada.
     * Esse método é usado caso o usuário deseje
     * definir algo no Menu#setOnClick, e previnir
     * que os outros tipos de clicks sejam
     * acionados.
     */
    @Setter @Getter boolean breakNow = false;

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

    /**
     * Retorna o tipo de click
     * que foi efetuado.
     * @return o ClickType representando o
     * tipo de click efetuado.
     */
    public abstract ClickType getType();

    /**
     * Retorna o número que foi
     * clicado.
     * Caso o click não tenha sido
     * numérico, retorna sempre 0.
     * @return o número clicado.
     */
    public abstract int getNumber();

}
