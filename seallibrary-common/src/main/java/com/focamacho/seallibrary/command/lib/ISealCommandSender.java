package com.focamacho.seallibrary.command.lib;

import com.focamacho.seallibrary.player.ISealPlayer;
import com.focamacho.seallibrary.player.SealPlayer;

/**
 * Representa alguém que executou um
 * comando. Podendo ser um jogador ou
 * o console.
 */
@SuppressWarnings("unused")
public interface ISealCommandSender {

    /**
     * Retorna se quem enviou o
     * comando foi o console.
     * @return true se foi o console,
     * false se não.
     */
    boolean isConsole();

    /**
     * Retorna se quem enviou
     * o comando foi um jogador.
     * @return true se foi um jogador,
     * false se não.
     */
    boolean isPlayer();

    /**
     * Retorna quem enviou o comando.
     * @return o objeto original de quem
     * enviou o comando.
     */
    Object get();

    /**
     * Retorna o ISealPlayer que enviou
     * o comando.
     * @return o ISealPlayer ou null caso
     * não tenha sido um jogador quem enviou
     * o comando.
     */
    default ISealPlayer getPlayer() {
        if(isPlayer()) return SealPlayer.get(this.get());
        return null;
    }

    /**
     * Envia uma mensagem para quem
     * enviou o comando.
     */
    void sendMessage(String message);

}
