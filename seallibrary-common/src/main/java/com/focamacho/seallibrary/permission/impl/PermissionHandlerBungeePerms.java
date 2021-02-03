package com.focamacho.seallibrary.permission.impl;

import com.focamacho.seallibrary.permission.IPermissionHandler;
import net.alpenblock.bungeeperms.BungeePermsAPI;

import java.util.UUID;

public class PermissionHandlerBungeePerms implements IPermissionHandler {

    @Override
    public boolean hasPermission(UUID uuid, String permission) {
        return BungeePermsAPI.userHasPermission(uuid.toString(), permission, "", null);
    }

    @Override
    public boolean addPermission(UUID uuid, String permission) {
        return BungeePermsAPI.userAdd(uuid.toString(), permission, "", null);
    }

    @Override
    public boolean removePermission(UUID uuid, String permission) {
        return BungeePermsAPI.userRemove(uuid.toString(), permission, "", null);
    }

    @Override
    public boolean hasGroup(UUID uuid, String group) {
        return BungeePermsAPI.userInGroup(uuid.toString(), group);
    }

    @Override
    public boolean addGroup(UUID uuid, String group) {
        return BungeePermsAPI.userAddGroup(uuid.toString(), group);
    }

    @Override
    public boolean removeGroup(UUID uuid, String group) {
        return BungeePermsAPI.userRemoveGroup(uuid.toString(), group);
    }

}
