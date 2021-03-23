package com.focamacho.seallibrary.permission;

import com.focamacho.seallibrary.logger.SealLogger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.service.permission.PermissionService;
import org.spongepowered.api.service.permission.Subject;
import org.spongepowered.api.service.permission.SubjectData;
import org.spongepowered.api.service.permission.SubjectReference;
import org.spongepowered.api.util.Tristate;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class PermissionHandlerSponge implements IPermissionHandler {

    private final PermissionService permissionService;

    {
        Optional<PermissionService> service = Sponge.getServiceManager().provide(PermissionService.class);
        if(service.isPresent()) {
            permissionService = service.get();
        } else {
            SealLogger.error("Nenhum plugin de permissões compatível foi carregado.",
                    "Por favor, instale um plugin compatível com o Sponge.",
                    "Algumas coisas não funcionarão corretamente até que um plugin compatível seja instalado.");
            permissionService = null;
        }
    }

    private CompletableFuture<Subject> getUser(UUID uuid) {
        return permissionService.getUserSubjects().loadSubject(uuid.toString());
    }

    private CompletableFuture<Subject> getGroup(String group) {
        return permissionService.getGroupSubjects().loadSubject(group);
    }

    @Override
    public CompletableFuture<Boolean> hasPermission(UUID uuid, String permission) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();

        getUser(uuid).whenComplete((user, throwable) -> {
            if(user != null) {
                future.complete(user.getPermissionValue(SubjectData.GLOBAL_CONTEXT, permission).asBoolean());
            } else future.complete(false);
        });

        return future;
    }

    @Override
    public CompletableFuture<Boolean> addPermission(UUID uuid, String permission) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();

        getUser(uuid).whenComplete((user, throwable) -> {
            if(user != null) {
                user.getSubjectData().setPermission(SubjectData.GLOBAL_CONTEXT, permission, Tristate.TRUE).whenComplete((success, thr) -> future.complete(success));
            } else future.complete(false);
        });

        return future;
    }

    @Override
    public CompletableFuture<Boolean> removePermission(UUID uuid, String permission) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();

        getUser(uuid).whenComplete((user, throwable) -> {
            if(user != null) {
                user.getSubjectData().setPermission(SubjectData.GLOBAL_CONTEXT, permission, Tristate.UNDEFINED).whenComplete((success, thr) -> future.complete(success));
            } else future.complete(false);
        });

        return future;
    }

    private boolean hasGroup(Subject user, Subject group) {
        Set<String> groups = user.getSubjectData().getParents(SubjectData.GLOBAL_CONTEXT).stream().map(SubjectReference::getSubjectIdentifier).collect(Collectors.toSet());
        return groups.contains(group.getIdentifier());
    }

    @Override
    public CompletableFuture<Boolean> hasGroup(UUID uuid, String group) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();

        getUser(uuid).whenComplete((user, throwable) -> {
            if(user != null) {
                getGroup(group).whenComplete((gp, thr) -> {
                    if(gp != null) {
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
                getGroup(group).whenComplete((gp, thr) -> {
                    if(gp != null) {
                        user.getSubjectData().addParent(SubjectData.GLOBAL_CONTEXT, gp.asSubjectReference()).whenComplete((success, tr) -> future.complete(success));
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
                getGroup(group).whenComplete((gp, thr) -> {
                    if(gp != null) {
                        user.getSubjectData().removeParent(SubjectData.GLOBAL_CONTEXT, gp.asSubjectReference()).whenComplete((success, tr) -> future.complete(success));
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
                future.complete(user.getOption(option).orElse(""));
            } else future.complete("");
        });

        return future;
    }

    @Override
    public CompletableFuture<Boolean> setOption(UUID uuid, String option, String value) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();

        getUser(uuid).whenComplete((user, throwable) -> {
            if(user != null) {
                user.getSubjectData().setOption(SubjectData.GLOBAL_CONTEXT, option, value).whenComplete((success, thr) -> future.complete(success));
            } else future.complete(false);
        });

        return future;
    }

}
