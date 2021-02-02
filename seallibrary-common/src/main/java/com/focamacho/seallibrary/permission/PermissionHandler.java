package com.focamacho.seallibrary.permission;

import com.focamacho.seallibrary.impl.Implementations;

import java.util.UUID;

/**
 * Classe static para acesso dos métodos
 * de controle de permissões.
 *
 * @see IPermissionHandler para mais informações
 * sobre os métodos.
 */
public class PermissionHandler {

    public static String getPrefix(UUID uuid) {
        return Implementations.permissionHandler.getPrefix(uuid);
    }

    public static String getSuffix(UUID uuid) {
        return Implementations.permissionHandler.getSuffix(uuid);
    }

    public static boolean hasPermission(UUID uuid, String permission) {
        return Implementations.permissionHandler.hasPermission(uuid, permission);
    }

    public static boolean hasGroup(UUID uuid, String group) {
        return Implementations.permissionHandler.hasGroup(uuid, group);
    }

    public static boolean addGroup(UUID uuid, String group) {
        return Implementations.permissionHandler.addGroup(uuid, group);
    }

    public static boolean removeGroup(UUID uuid, String group) {
        return Implementations.permissionHandler.removeGroup(uuid, group);
    }

}
