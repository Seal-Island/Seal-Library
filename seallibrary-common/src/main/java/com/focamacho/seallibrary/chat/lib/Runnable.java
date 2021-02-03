package com.focamacho.seallibrary.chat.lib;

public interface Runnable {

    /**
     * Runnable contendo informações
     * da mensagem enviada no chat.
     */
    interface MessageRunnable {
        void run(IMessage message);
    }

}