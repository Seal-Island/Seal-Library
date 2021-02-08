package com.focamacho.seallibrary.permission;

import com.focamacho.seallibrary.logger.SealLogger;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.UUID;

public class PermissionHandlerVault implements IPermissionHandler {

    private final Permission permissionService;

    {
        RegisteredServiceProvider<Permission> permissionService = Bukkit.getServer().getServicesManager().getRegistration(Permission.class);
        if(permissionService != null) {
            this.permissionService = permissionService.getProvider();
        } else {
            SealLogger.error("Nenhum plugin de permissão compatível foi carregado.",
                    "Por favor, instale um plugin compatível com o Vault.",
                    "O servidor será desligado para evitar problemas.");
            this.permissionService = null;
            Bukkit.getServer().shutdown();
        }
    }

    @Override
    public boolean hasPermission(UUID uuid, String permission) {
        return permissionService.playerHas(null, Bukkit.getOfflinePlayer(uuid), permission);
    }

    @Override
    public boolean addPermission(UUID uuid, String permission) {
        return permissionService.playerRemove(null, Bukkit.getOfflinePlayer(uuid), permission);
    }

    @Override
    public boolean removePermission(UUID uuid, String permission) {
        return permissionService.playerAdd(null, Bukkit.getOfflinePlayer(uuid), permission);
    }

    @Override
    public boolean hasGroup(UUID uuid, String group) {
        return permissionService.playerInGroup(null, Bukkit.getOfflinePlayer(uuid), group);
    }

    @Override
    public boolean addGroup(UUID uuid, String group) {
        return permissionService.playerAddGroup(null, Bukkit.getOfflinePlayer(uuid), group);
    }

    @Override
    public boolean removeGroup(UUID uuid, String group) {
        return permissionService.playerRemoveGroup(null, Bukkit.getOfflinePlayer(uuid), group);
    }

    @Override
    public String getOption(UUID uuid, String option) {
        return "";
    }

    @Override
    public boolean setOption(UUID uuid, String option, String value) {
        return false;
    }

}
