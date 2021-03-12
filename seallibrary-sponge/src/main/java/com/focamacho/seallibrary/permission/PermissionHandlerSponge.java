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

    private Subject getUser(UUID uuid) {
        try { permissionService.getUserSubjects().loadSubject(uuid.toString()).wait(); } catch (Exception ignored) {}
        return permissionService.getUserSubjects().getSubject(uuid.toString()).orElse(null);
    }

    private Subject getGroup(String group) {
        try { permissionService.getGroupSubjects().loadSubject(group).wait(); } catch (Exception ignored) {}
        return permissionService.getGroupSubjects().getSubject(group).orElse(null);
    }

    @Override
    public boolean hasPermission(UUID uuid, String permission) {
        Subject user = getUser(uuid);
        return user != null && user.getPermissionValue(SubjectData.GLOBAL_CONTEXT, permission).asBoolean();
    }

    @Override
    public boolean addPermission(UUID uuid, String permission) {
        Subject user = getUser(uuid);
        if(user == null) return false;

        try { return user.getSubjectData().setPermission(SubjectData.GLOBAL_CONTEXT, permission, Tristate.TRUE).get(); }
        catch(Exception ignored) { return false; }
    }

    @Override
    public boolean removePermission(UUID uuid, String permission) {
        Subject user = getUser(uuid);
        if(user == null) return false;

        try { return user.getSubjectData().setPermission(SubjectData.GLOBAL_CONTEXT, permission, Tristate.UNDEFINED).get(); }
        catch(Exception ignored) { return false; }
    }

    private boolean hasGroup(Subject user, Subject group) {
        Set<String> groups = user.getSubjectData().getParents(SubjectData.GLOBAL_CONTEXT).stream().map(SubjectReference::getSubjectIdentifier).collect(Collectors.toSet());
        return groups.contains(group.getIdentifier());
    }

    @Override
    public boolean hasGroup(UUID uuid, String group) {
        Subject user = getUser(uuid);
        if(user == null) return false;

        Subject gp = getGroup(group);
        if(gp == null) return false;

        return hasGroup(user, gp);
    }

    @Override
    public boolean addGroup(UUID uuid, String group) {
        Subject user = getUser(uuid);
        if(user == null) return false;

        Subject gp = getGroup(group);
        if(group == null) return false;

        if(hasGroup(user, gp)) return false;

        try { return user.getSubjectData().addParent(SubjectData.GLOBAL_CONTEXT, gp.asSubjectReference()).get(); }
        catch(Exception ignored) { return false; }
    }

    @Override
    public boolean removeGroup(UUID uuid, String group) {
        Subject user = getUser(uuid);
        if(user == null) return false;

        Subject gp = getGroup(group);
        if(group == null) return false;

        if(hasGroup(user, gp)) return false;

        try { return user.getSubjectData().removeParent(SubjectData.GLOBAL_CONTEXT, gp.asSubjectReference()).get(); }
        catch(Exception ignored) { return false; }
    }

    @Override
    public String getOption(UUID uuid, String option) {
        Subject user = getUser(uuid);
        if(user == null) return "";

        return user.getOption(option).orElse("");
    }

    @Override
    public boolean setOption(UUID uuid, String option, String value) {
        Subject user = getUser(uuid);
        if(user == null) return false;

        try { return user.getSubjectData().setOption(SubjectData.GLOBAL_CONTEXT, option, value).get(); }
        catch(Exception ignored) { return false; }
    }

}
