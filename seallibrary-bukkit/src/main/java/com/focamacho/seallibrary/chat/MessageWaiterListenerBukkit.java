package com.focamacho.seallibrary.chat;

import com.focamacho.seallibrary.chat.lib.IMessage;
import com.focamacho.seallibrary.player.ISealPlayer;
import com.focamacho.seallibrary.player.SealPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Iterator;

public class MessageWaiterListenerBukkit implements Listener {

    @EventHandler
    public void onChatMessage(AsyncPlayerChatEvent event) {
        Iterator<MessageWaiter> waiters = MessageWaiter.waiters.iterator();
        while(waiters.hasNext()) {
            MessageWaiter waiter = waiters.next();
            if(waiter.getPlayer().equals(event.getPlayer().getUniqueId())) {
                waiters.remove();

                waiter.getOnReceive().run(new IMessage() {
                    @Override
                    public ISealPlayer getPlayer() {
                        return SealPlayer.get(event.getPlayer());
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
    public void onPlayerLogout(PlayerQuitEvent event) {
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
