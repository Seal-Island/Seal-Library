package com.focamacho.seallibrary.chat.impl;

import com.focamacho.seallibrary.chat.IChatHandler;
import net.alpenblock.bungeeperms.BungeePermsAPI;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Implementação do sistema de chat
 * por meio do LuckPerms.
 * @see IChatHandler
 */
public class ChatHandlerBungeePerms implements IChatHandler {

    @Override
    public CompletableFuture<String> getPrefix(UUID uuid) {
        CompletableFuture<String> future = new CompletableFuture<>();
        future.complete(BungeePermsAPI.userPrefix(uuid.toString(), "", null));
        return future;
    }

    @Override
    public CompletableFuture<String> getSuffix(UUID uuid) {
        CompletableFuture<String> future = new CompletableFuture<>();
        future.complete(BungeePermsAPI.userSuffix(uuid.toString(), "", null));
        return future;
    }

}
