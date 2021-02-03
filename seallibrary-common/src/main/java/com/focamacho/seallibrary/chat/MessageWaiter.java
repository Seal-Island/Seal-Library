package com.focamacho.seallibrary.chat;

import com.focamacho.seallibrary.chat.lib.Runnable;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Classe abstract para implementação
 * do sistema de aguardar uma mensagem
 * do jogador.
 * @see IChatHandler
 */
public class MessageWaiter {

    public static final List<MessageWaiter> waiters = new ArrayList<>();
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @Getter private final UUID player;
    @Getter private final Runnable.MessageRunnable onReceive;
    @Getter private final java.lang.Runnable onExpire;
    @Getter private final int secondsLimit;

    private MessageWaiter(UUID player, Runnable.MessageRunnable onReceive, int secondsLimit, java.lang.Runnable onExpire) {
        //Verificar se não há nenhum waiter já criado com o jogador especificado.
        Iterator<MessageWaiter> waiters = MessageWaiter.waiters.iterator();
        while(waiters.hasNext()) {
            MessageWaiter waiter = waiters.next();
            if(player.equals(waiter.player)) {
                waiters.remove();
                break;
            }
        }

        this.player = player;
        this.onReceive = onReceive;
        this.onExpire = onExpire;
        this.secondsLimit = secondsLimit;
        MessageWaiter.waiters.add(this);

        scheduler.schedule(() -> {
            if(MessageWaiter.waiters.contains(this)) {
                onExpire.run();
                MessageWaiter.waiters.remove(this);
            }
        }, secondsLimit, TimeUnit.SECONDS);
    }

    public static void waitForMessage(UUID player, Runnable.MessageRunnable onReceive, int secondsLimit, java.lang.Runnable onExpire) {
        new MessageWaiter(player, onReceive, secondsLimit, onExpire);
    }

}
