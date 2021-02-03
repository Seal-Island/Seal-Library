package com.focamacho.seallibrary.player;

import com.focamacho.seallibrary.impl.Implementations;

public class SealPlayer {

    /**
     * Obtêm um SealPlayer a partir do
     * jogador original.
     * @param player o jogador original.
     * @return o SealPlayer obtido.
     */
    public static ISealPlayer get(Object player) {
        return Implementations.playerGetter.get(player);
    }

}
