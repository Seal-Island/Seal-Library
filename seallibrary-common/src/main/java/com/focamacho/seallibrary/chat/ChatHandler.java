package com.focamacho.seallibrary.chat;

import com.focamacho.seallibrary.chat.lib.Runnable;
import com.focamacho.seallibrary.impl.Implementations;

import java.util.UUID;

/**
 * Classe static para acesso dos métodos
 * de controle de permissões.
 *
 * @see IChatHandler para mais informações
 * sobre os métodos.
 */
public class ChatHandler {

    public static String getPrefix(UUID uuid) {
        return Implementations.chatHandler.getPrefix(uuid);
    }

    public static String getSuffix(UUID uuid) {
        return Implementations.chatHandler.getSuffix(uuid);
    }

    public static void waitForMessage(UUID player, Runnable.MessageRunnable onReceive, int secondsLimit, java.lang.Runnable onExpire) {
        Implementations.chatHandler.waitForMessage(player, onReceive, secondsLimit, onExpire);
    }

    public static void waitForMessage(UUID player, Runnable.MessageRunnable onReceive, int secondsLimit) {
        Implementations.chatHandler.waitForMessage(player, onReceive, secondsLimit, () -> {});
    }

    public static void waitForMessage(UUID player, Runnable.MessageRunnable onReceive) {
        Implementations.chatHandler.waitForMessage(player, onReceive, Integer.MAX_VALUE);
    }

}
