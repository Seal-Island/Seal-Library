package com.focamacho.seallibrary.chat;

import com.focamacho.seallibrary.chat.lib.IMessage;
import com.focamacho.seallibrary.player.ISealPlayer;
import com.focamacho.seallibrary.player.SealPlayer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.Iterator;

public class MessageWaiterListenerBungee implements Listener {

    @EventHandler
    public void onChatMessage(ChatEvent event) {
        if(!(event.getSender() instanceof ProxiedPlayer)) return;

        Iterator<MessageWaiter> waiters = MessageWaiter.waiters.iterator();
        while(waiters.hasNext()) {
            MessageWaiter waiter = waiters.next();
            if(waiter.getPlayer().equals(((ProxiedPlayer) event.getSender()).getUniqueId())) {
                waiters.remove();

                waiter.getOnReceive().run(new IMessage() {
                    @Override
                    public ISealPlayer getPlayer() {
                        return SealPlayer.get(event.getSender());
                    }

                    @Override
                    public String getMessage() {
                        return event.getMessage();
                    }
                });

                break;
            }
        }
    }

    @EventHandler
    public void onPlayerLogout(PlayerDisconnectEvent event) {
        Iterator<MessageWaiter> waiters = MessageWaiter.waiters.iterator();
        while(waiters.hasNext()) {
            MessageWaiter waiter = waiters.next();
            if(waiter.getPlayer().equals(event.getPlayer().getUniqueId())) {
                waiters.remove();

                waiter.getOnExpire().run();

                break;
            }
        }
    }

}
