package com.focamacho.seallibrary.chat;

import com.focamacho.seallibrary.chat.lib.Runnable;

import java.util.UUID;

@SuppressWarnings("unused")
public interface IChatHandler {

    /**
     *  Consulta o prefix de um jogador.
     *
     * @param uuid o uuid do jogador
     * @return o prefix ou null caso o jogador
     * não possua um.
     */
    String getPrefix(UUID uuid);

    /**
     *  Consulta o suffix de um jogador.
     *
     * @param uuid o uuid do jogador
     * @return o suffix ou null caso o jogador
     * não possua um.
     */
    String getSuffix(UUID uuid);

    /**
     * Aguarda uma mensagem do jogador
     * para executar uma ação.
     *
     * @param player o jogador para aguardar
     *               a mensagem.
     * @param onReceive a ação efetuada ao receber
     *                  a mensagem.
     * @param secondsLimit o limite de segundos antes desse
     *                     waiter se auto-destruir.
     * @param onExpire a ação efetuada ao tempo limiter ser
     *                 esgotado.
     */
    default void waitForMessage(UUID player, Runnable.MessageRunnable onReceive, int secondsLimit, java.lang.Runnable onExpire) {
        MessageWaiter.waitForMessage(player, onReceive, secondsLimit, onExpire);
    }

    /**
     * Aguarda uma mensagem do jogador
     * para executar uma ação.
     *
     * @param player o jogador para aguardar
     *               a mensagem.
     * @param onReceive a ação efetuada ao receber
     *                  a mensagem.
     * @param secondsLimit o limite de segundos antes desse
     *                     waiter se auto-destruir.
     */
    default void waitForMessage(UUID player, Runnable.MessageRunnable onReceive, int secondsLimit) {
        waitForMessage(player, onReceive, secondsLimit, () -> {});
    }

    /**
     * Aguarda uma mensagem do jogador
     * para executar uma ação.
     *
     * @param player o jogador para aguardar
     *               a mensagem.
     * @param onReceive a ação efetuada ao receber
     *                  a mensagem.
     */
    default void waitForMessage(UUID player, Runnable.MessageRunnable onReceive) {
        waitForMessage(player, onReceive, Integer.MAX_VALUE);
    }

}
