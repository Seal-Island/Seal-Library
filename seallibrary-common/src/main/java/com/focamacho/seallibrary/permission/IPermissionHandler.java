package com.focamacho.seallibrary.permission;

import java.util.UUID;

public interface IPermissionHandler {

    /**
     *  Consulta o prefix de um jogador.
     *
     * @param uuid o uuid do jogador
     * @return o prefix ou null caso o jogador
     * não possua um.
     */
    String getPrefix(UUID uuid);

    /**
     *  Consulta o suffix de um jogador.
     *
     * @param uuid o uuid do jogador
     * @return o suffix ou null caso o jogador
     * não possua um.
     */
    String getSuffix(UUID uuid);

    /**
     *  Consulta se o jogador possui uma
     *  permissão.
     *
     * @param uuid o uuid do jogador
     * @param permission a permissão para consulta
     */
    boolean hasPermission(UUID uuid, String permission);

    /**
     *  Consulta se o jogador está em um
     *  grupo.
     *
     * @param uuid o uuid do jogador
     * @param group o grupo para consulta
     */
    boolean hasGroup(UUID uuid, String group);

    /**
     * Adiciona o jogador no grupo
     * especificado.
     *
     * @param uuid o uuid do jogador
     * @param group o grupo desejado
     * @return true se a ação foi efetuada com sucesso
     * e false caso algum erro tenha ocorrido.
     */
    boolean addGroup(UUID uuid, String group);

    /**
     * Remove o jogador do grupo
     * especificado.
     *
     * @param uuid o uuid do jogador
     * @param group o grupo desejado
     * @return true se a ação foi efetuada com sucesso
     * e false caso algum erro tenha ocorrido.
     */
    boolean removeGroup(UUID uuid, String group);

}
