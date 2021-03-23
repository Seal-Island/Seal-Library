package com.focamacho.seallibrary.permission;

import com.focamacho.seallibrary.logger.SealLogger;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class PermissionHandlerVault implements IPermissionHandler {

    private final Permission permissionService;

    {
        RegisteredServiceProvider<Permission> permissionService = Bukkit.getServer().getServicesManager().getRegistration(Permission.class);
        if(permissionService != null) {
            this.permissionService = permissionService.getProvider();
        } else {
            SealLogger.error("Nenhum plugin de permissão compatível foi carregado.",
                    "Por favor, instale um plugin compatível com o Vault.",
                    "Algumas coisas não funcionarão corretamente até que um plugin compatível seja instalado.");
            this.permissionService = null;
        }
    }

    @Override
    public CompletableFuture<Boolean> hasPermission(UUID uuid, String permission) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        future.complete(permissionService.playerHas(null, Bukkit.getOfflinePlayer(uuid), permission));
        return future;
    }

    @Override
    public CompletableFuture<Boolean> addPermission(UUID uuid, String permission) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        future.complete(permissionService.playerRemove(null, Bukkit.getOfflinePlayer(uuid), permission));
        return future;
    }

    @Override
    public CompletableFuture<Boolean> removePermission(UUID uuid, String permission) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        future.complete(permissionService.playerAdd(null, Bukkit.getOfflinePlayer(uuid), permission));
        return future;
    }

    @Override
    public CompletableFuture<Boolean> hasGroup(UUID uuid, String group) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        future.complete(permissionService.playerInGroup(null, Bukkit.getOfflinePlayer(uuid), group));
        return future;
    }

    @Override
    public CompletableFuture<Boolean> addGroup(UUID uuid, String group) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        future.complete(permissionService.playerAddGroup(null, Bukkit.getOfflinePlayer(uuid), group));
        return future;
    }

    @Override
    public CompletableFuture<Boolean> removeGroup(UUID uuid, String group) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        future.complete(permissionService.playerRemoveGroup(null, Bukkit.getOfflinePlayer(uuid), group));
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
