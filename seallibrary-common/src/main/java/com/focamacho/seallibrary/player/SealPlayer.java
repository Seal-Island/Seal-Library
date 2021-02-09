package com.focamacho.seallibrary.player;

import com.focamacho.seallibrary.impl.Implementations;

import java.util.Optional;
import java.util.UUID;

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

    /**
     * Obtêm um SealPlayer a partir de
     * um UUID. O jogador precisa estar
     * online para ser obtido.
     * @param uuid o uuid do jogador.
     * @return o SealPlayer obtido.
     */
    public static Optional<ISealPlayer> get(UUID uuid) {
        return Implementations.playerGetter.get(uuid);
    }

}
