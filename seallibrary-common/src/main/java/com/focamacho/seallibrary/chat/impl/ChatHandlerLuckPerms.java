package com.focamacho.seallibrary.chat.impl;

import com.focamacho.seallibrary.chat.IChatHandler;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;

import java.util.UUID;

/**
 * Implementação do sistema de chat
 * por meio do LuckPerms.
 * @see IChatHandler
 */
public class ChatHandlerLuckPerms implements IChatHandler {

    private final LuckPerms api;

    public ChatHandlerLuckPerms() {
        api = LuckPermsProvider.get();
    }

    private User getUser(UUID uuid) {
        if(!api.getUserManager().isLoaded(uuid)) try { api.getUserManager().loadUser(uuid).wait(); } catch (Exception ignored) {}
        return api.getUserManager().getUser(uuid);
    }

    @Override
    public String getPrefix(UUID uuid) {
        User user = getUser(uuid);
        if(user == null) return null;
        return user.getCachedData().getMetaData().getPrefix();
    }

    @Override
    public String getSuffix(UUID uuid) {
        User user = getUser(uuid);
        if(user == null) return null;
        return user.getCachedData().getMetaData().getSuffix();
    }

}
