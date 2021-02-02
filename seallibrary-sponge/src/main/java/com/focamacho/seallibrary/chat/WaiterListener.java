package com.focamacho.seallibrary.chat;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.message.MessageChannelEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;

import java.util.Iterator;

public class WaiterListener {

    @Listener
    public void onChatMessage(MessageChannelEvent.Chat event) {
        if(!(event.getSource() instanceof Player)) return;

        Iterator<MessageWaiter> waiters = MessageWaiter.waiters.iterator();
        while(waiters.hasNext()) {
            MessageWaiter waiter = waiters.next();
            if(waiter.player.equals(((Player)event.getSource()).getUniqueId())) {
                waiters.remove();

                waiter.onReceive.run(event);

                break;
            }
        }
    }

    @Listener
    public void onPlayerLogout(ClientConnectionEvent.Disconnect event) {
        if(!(event.getSource() instanceof Player)) return;

        Iterator<MessageWaiter> waiters = MessageWaiter.waiters.iterator();
        while(waiters.hasNext()) {
            MessageWaiter waiter = waiters.next();
            if(waiter.player.equals(((Player)event.getSource()).getUniqueId())) {
                waiters.remove();

                waiter.onExpire.run();

                break;
            }
        }
    }

}
