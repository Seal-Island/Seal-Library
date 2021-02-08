package com.focamacho.seallibrary.menu.lib;

import com.focamacho.seallibrary.player.ISealPlayer;
import lombok.Getter;
import lombok.Setter;

public abstract class AbstractInteract {

    @Getter @Setter boolean cancelled = false;

    /**
     * Retorna o jogador que efetuou
     * a ação.
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
