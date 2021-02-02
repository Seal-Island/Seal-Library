package com.focamacho.seallibrary.chat.lib;

import org.spongepowered.api.event.message.MessageChannelEvent;

public interface MessageRunnable {

    void run(MessageChannelEvent.Chat event);

}
