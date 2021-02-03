package com.focamacho.seallibrary.chat.impl;

import com.focamacho.seallibrary.chat.IChatHandler;
import net.alpenblock.bungeeperms.BungeePermsAPI;

import java.util.UUID;

/**
 * Implementação do sistema de chat
 * por meio do LuckPerms.
 * @see IChatHandler
 */
public class ChatHandlerBungeePerms implements IChatHandler {

    @Override
    public String getPrefix(UUID uuid) {
        return BungeePermsAPI.userPrefix(uuid.toString(), "", null);
    }

    @Override
    public String getSuffix(UUID uuid) {
        return BungeePermsAPI.userSuffix(uuid.toString(), "", null);
    }

}
