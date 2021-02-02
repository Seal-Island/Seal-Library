package com.focamacho.seallibrary.chat;

import com.focamacho.seallibrary.chat.lib.MessageRunnable;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scheduler.Task;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("unused")
public class MessageWaiter {

    public static List<MessageWaiter> waiters = new ArrayList<>();

    public UUID player;
    public MessageRunnable onReceive;
    public Runnable onExpire;
    public int secondsLimit;

    private MessageWaiter(Object plugin, UUID player, MessageRunnable onReceive, int secondsLimit, Runnable onExpire) {
        //Verificar se não há nenhum waiter já criado com o jogador especificado
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

        Task.builder().delay(secondsLimit, TimeUnit.SECONDS).execute(() -> {
            if(MessageWaiter.waiters.contains(this)) {
                onExpire.run();
                MessageWaiter.waiters.remove(this);
            }
        }).submit(plugin);
    }

    public static void waitForMessage(Object plugin, Player player, MessageRunnable onReceive, int secondsLimit, Runnable onExpire) {
        new MessageWaiter(plugin, player.getUniqueId(), onReceive, secondsLimit, onExpire);
    }

    public static void waitForMessage(Object plugin, Player player, MessageRunnable onReceive, int secondsLimit) {
        waitForMessage(plugin, player, onReceive, secondsLimit, () -> {});
    }

    public static void waitForMessage(Object plugin, Player player, MessageRunnable onReceive) {
        waitForMessage(plugin, player, onReceive, Integer.MAX_VALUE);
    }

}
