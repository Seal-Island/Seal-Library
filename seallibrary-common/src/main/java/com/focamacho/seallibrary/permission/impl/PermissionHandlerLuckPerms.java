package com.focamacho.seallibrary.permission.impl;

import com.focamacho.seallibrary.permission.IPermissionHandler;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.data.DataType;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.NodeType;
import net.luckperms.api.node.types.InheritanceNode;
import net.luckperms.api.node.types.MetaNode;
import net.luckperms.api.node.types.PermissionNode;
import net.luckperms.api.query.QueryOptions;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * Implementação do sistema de permissões
 * por meio do LuckPerms.
 * @see IPermissionHandler
 */
public class PermissionHandlerLuckPerms implements IPermissionHandler {

    private final LuckPerms api;

    public PermissionHandlerLuckPerms() {
        api = LuckPermsProvider.get();
    }

    private CompletableFuture<User> getUser(UUID uuid) {
        return api.getUserManager().loadUser(uuid);
    }

    private CompletableFuture<Group> getGroup(String group) {
        CompletableFuture<Group> future = new CompletableFuture<>();

        if(!api.getGroupManager().isLoaded(group))
            api.getGroupManager().loadGroup(group).whenComplete((opt, throwable) -> future.complete(opt.orElseGet(null)));
        else future.complete(api.getGroupManager().getGroup(group));

        return future;
    }

    @Override
    public CompletableFuture<Boolean> hasPermission(UUID uuid, String permission) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        getUser(uuid).whenComplete((user, throwable) -> future.complete(user != null && user.getCachedData().getPermissionData().checkPermission(permission).asBoolean()));
        return future;
    }

    @Override
    public CompletableFuture<Boolean> addPermission(UUID uuid, String permission) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        getUser(uuid).whenComplete((user, throwable) -> {
            future.complete(user != null && user.data().add(PermissionNode.builder().permission(permission).value(true).build()).wasSuccessful());
            if(user != null) api.getUserManager().saveUser(user);
        });
        return future;
    }

    @Override
    public CompletableFuture<Boolean> removePermission(UUID uuid, String permission) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        getUser(uuid).whenComplete((user, throwable) -> {
            future.complete(user != null && user.data().remove(PermissionNode.builder().permission(permission).value(true).build()).wasSuccessful());
            if(user != null) api.getUserManager().saveUser(user);
        });
        return future;
    }

    private boolean hasGroup(User user, Group group) {
        Set<String> groups = user.getNodes(NodeType.INHERITANCE).stream().map(InheritanceNode::getGroupName).collect(Collectors.toSet());
        return groups.contains(group.getName());
    }

    @Override
    public CompletableFuture<Boolean> hasGroup(UUID uuid, String group) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();

        getUser(uuid).whenComplete((user, throwable) -> {
            if(user != null) {
                getGroup(group).whenComplete((gp, tr) -> {
                    if(group != null) {
                        future.complete(hasGroup(user, gp));
                    } else future.complete(false);
                });
            } else future.complete(false);
        });

        return future;
    }

    @Override
    public CompletableFuture<Boolean> addGroup(UUID uuid, String group) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();

        getUser(uuid).whenComplete((user, throwable) -> {
            if(user != null) {
                getGroup(group).whenComplete((gp, tr) -> {
                    if(group != null) {
                        future.complete(user.data().add(InheritanceNode.builder().group(gp).value(true).build()).wasSuccessful());
                        api.getUserManager().saveUser(user);
                    } else future.complete(false);
                });
            } else future.complete(false);
        });

        return future;
    }

    @Override
    public CompletableFuture<Boolean> removeGroup(UUID uuid, String group) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();

        getUser(uuid).whenComplete((user, throwable) -> {
            if(user != null) {
                getGroup(group).whenComplete((gp, tr) -> {
                    if(group != null) {
                        future.complete(user.data().add(InheritanceNode.builder().group(gp).value(false).build()).wasSuccessful());
                        api.getUserManager().saveUser(user);
                    } else future.complete(false);
                });
            } else future.complete(false);
        });

        return future;
    }

    @Override
    public CompletableFuture<String> getOption(UUID uuid, String option) {
        CompletableFuture<String> future = new CompletableFuture<>();

        getUser(uuid).whenComplete((user, throwable) -> {
            if(user != null) {
                String opt = user.getCachedData().getMetaData(QueryOptions.nonContextual()).getMetaValue("");
                future.complete(opt != null ? opt : "");
            } else future.complete("");
        });

        return future;
    }

    @Override
    public CompletableFuture<Boolean> setOption(UUID uuid, String option, String value) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();

        getUser(uuid).whenComplete((user, throwable) -> {
            if(user != null) {
                future.complete(user.data().add(MetaNode.builder().key(option).value(value).build()).wasSuccessful());
                api.getUserManager().saveUser(user);
            } else future.complete(false);
        });

        return future;
    }

}
