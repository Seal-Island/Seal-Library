package com.focamacho.seallibrary.chat;

import com.focamacho.seallibrary.chat.lib.IMessage;
import com.focamacho.seallibrary.player.ISealPlayer;
import com.focamacho.seallibrary.player.SealPlayer;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.message.MessageChannelEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;

import java.util.Iterator;

public class MessageWaiterListenerSponge {

    @Listener
    public void onChatMessage(MessageChannelEvent.Chat event) {
        if(!(event.getSource() instanceof Player)) return;

        Iterator<MessageWaiter> waiters = MessageWaiter.waiters.iterator();
        while(waiters.hasNext()) {
            MessageWaiter waiter = waiters.next();
            if(waiter.getPlayer().equals(((Player)event.getSource()).getUniqueId())) {
                event.setCancelled(true);
                waiters.remove();

                waiter.getOnReceive().run(new IMessage() {
                    @Override
                    public ISealPlayer getPlayer() {
                        return SealPlayer.get(event.getSource());
                    }

                    @Override
                    public String getMessage() {
                        return event.getMessage().toPlainSingle();
                    }
                });

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
            if(waiter.getPlayer().equals(((Player)event.getSource()).getUniqueId())) {
                waiters.remove();

                waiter.getOnExpire().run();

                break;
            }
        }
    }

}
