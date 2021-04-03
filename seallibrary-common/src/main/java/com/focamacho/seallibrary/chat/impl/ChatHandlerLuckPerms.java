package com.focamacho.seallibrary.chat.impl;

import com.focamacho.seallibrary.chat.IChatHandler;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

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

    private CompletableFuture<User> getUser(UUID uuid) {
        return api.getUserManager().loadUser(uuid);
    }

    @Override
    public CompletableFuture<String> getPrefix(UUID uuid) {
        CompletableFuture<String> future = new CompletableFuture<>();
        getUser(uuid).whenComplete((user, throwable) -> {
            if(user == null) future.complete("");
            else future.complete(user.getCachedData().getMetaData().getPrefix());
        });
        return future;
    }

    @Override
    public CompletableFuture<String> getSuffix(UUID uuid) {
        CompletableFuture<String> future = new CompletableFuture<>();
        getUser(uuid).whenComplete((user, throwable) -> {
            if(user == null) future.complete("");
            else future.complete(user.getCachedData().getMetaData().getSuffix());
        });
        return future;
    }

}
