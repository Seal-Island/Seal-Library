package com.focamacho.seallibrary.permission.impl;

import com.focamacho.seallibrary.permission.IPermissionHandler;
import net.alpenblock.bungeeperms.BungeePermsAPI;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class PermissionHandlerBungeePerms implements IPermissionHandler {

    @Override
    public CompletableFuture<Boolean> hasPermission(UUID uuid, String permission) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        future.complete(BungeePermsAPI.userHasPermission(uuid.toString(), permission, "", null));
        return future;
    }

    @Override
    public CompletableFuture<Boolean> addPermission(UUID uuid, String permission) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        future.complete(BungeePermsAPI.userAdd(uuid.toString(), permission, "", null));
        return future;
    }

    @Override
    public CompletableFuture<Boolean> removePermission(UUID uuid, String permission) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        future.complete(BungeePermsAPI.userRemove(uuid.toString(), permission, "", null));
        return future;
    }

    @Override
    public CompletableFuture<Boolean> hasGroup(UUID uuid, String group) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        future.complete(BungeePermsAPI.userInGroup(uuid.toString(), group));
        return future;
    }

    @Override
    public CompletableFuture<Boolean> addGroup(UUID uuid, String group) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        future.complete(BungeePermsAPI.userAddGroup(uuid.toString(), group));
        return future;
    }

    @Override
    public CompletableFuture<Boolean> removeGroup(UUID uuid, String group) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        future.complete(BungeePermsAPI.userRemoveGroup(uuid.toString(), group));
        return future;
    }

    @Override
    public CompletableFuture<String> getOption(UUID uuid, String option) {
        CompletableFuture<String> future = new CompletableFuture<>();
        future.complete("");
        return future;
    }

    @Override
    public CompletableFuture<Boolean> setOption(UUID uuid, String option, String value) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        future.complete(false);
        return future;
    }

}
