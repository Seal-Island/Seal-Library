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

    public static String getOption(UUID uuid, String option) {
        return Implementations.permissionHandler.getOption(uuid, option);
    }

    public static boolean setOption(UUID uuid, String option, String value) {
        return Implementations.permissionHandler.setOption(uuid, option, value);
    }

}