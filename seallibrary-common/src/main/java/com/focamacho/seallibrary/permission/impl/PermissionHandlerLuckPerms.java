package com.focamacho.seallibrary.permission.impl;

import com.focamacho.seallibrary.permission.IPermissionHandler;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.data.DataMutateResult;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.NodeType;
import net.luckperms.api.node.types.InheritanceNode;
import net.luckperms.api.node.types.PermissionNode;

import java.util.Set;
import java.util.UUID;
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

    private User getUser(UUID uuid) {
        if(!api.getUserManager().isLoaded(uuid)) api.getUserManager().loadUser(uuid);
        return api.getUserManager().getUser(uuid);
    }

    private Group getGroup(String group) {
        if(!api.getGroupManager().isLoaded(group)) api.getGroupManager().loadGroup(group);
        return api.getGroupManager().getGroup(group);
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

    @Override
    public boolean hasPermission(UUID uuid, String permission) {
        User user = getUser(uuid);
        return user != null && user.getCachedData().getPermissionData().checkPermission(permission).asBoolean();
    }

    @Override
    public boolean addPermission(UUID uuid, String permission) {
        User user = getUser(uuid);
        if(user == null) return false;

        DataMutateResult result = user.data().add(PermissionNode.builder().permission(permission).value(true).build());
        api.getUserManager().saveUser(user);

        return result.wasSuccessful();
    }

    @Override
    public boolean removePermission(UUID uuid, String permission) {
        User user = getUser(uuid);
        if(user == null) return false;

        DataMutateResult result = user.data().remove(PermissionNode.builder().permission(permission).value(true).build());
        api.getUserManager().saveUser(user);

        return result.wasSuccessful();
    }

    private boolean hasGroup(User user, Group group) {
        Set<String> groups = user.getNodes(NodeType.INHERITANCE).stream().map(InheritanceNode::getGroupName).collect(Collectors.toSet());
        return groups.contains(group.getName());
    }

    @Override
    public boolean hasGroup(UUID uuid, String group) {
        User user = getUser(uuid);
        if(user == null) return false;

        Group gp = getGroup(group);
        if(gp == null) return false;

        return hasGroup(user, gp);
    }

    @Override
    public boolean addGroup(UUID uuid, String group) {
        User user = getUser(uuid);
        if(user == null) return false;

        Group gp = getGroup(group);
        if(group == null) return false;

        if(hasGroup(user, gp)) return false;

        DataMutateResult result = user.data().add(InheritanceNode.builder().group(gp).value(true).build());
        api.getUserManager().saveUser(user);

        return result.wasSuccessful();
    }

    @Override
    public boolean removeGroup(UUID uuid, String group) {
        User user = getUser(uuid);
        if(user == null) return false;

        Group gp = getGroup(group);
        if(group == null) return false;

        if(hasGroup(user, gp)) return false;

        DataMutateResult result = user.data().add(InheritanceNode.builder().group(gp).value(false).build());
        api.getUserManager().saveUser(user);

        return result.wasSuccessful();
    }

}
