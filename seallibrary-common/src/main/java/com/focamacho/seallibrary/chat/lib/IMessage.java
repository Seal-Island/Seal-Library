package com.focamacho.seallibrary.chat.lib;

import com.focamacho.seallibrary.player.ISealPlayer;

/**
 * Representação de uma mensagem
 * no chat.
 */
public interface IMessage {

    /**
     * Retorna o jogador que enviou
     * a mensagem.
     * @return o jogador.
     */
    ISealPlayer getPlayer();

    /**
     * Retorna a mensagem enviada
     * @return a mensagem.
     */
    String getMessage();

}
