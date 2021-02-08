package com.focamacho.seallibrary.permission;

import java.util.UUID;

@SuppressWarnings("unused")
public interface IPermissionHandler {

    /**
     *  Consulta se o jogador possui uma
     *  permissão.
     *
     * @param uuid o uuid do jogador
     * @param permission a permissão para consulta
     */
    boolean hasPermission(UUID uuid, String permission);

    /**
     *  Adiciona uma permissão para
     *  o jogador.
     *
     * @param uuid o uuid do jogador
     * @param permission a permissão para adição
     */
    boolean addPermission(UUID uuid, String permission);

    /**
     *  Remove uma permissão
     *  do jogador.
     *
     * @param uuid o uuid do jogador
     * @param permission a permissão para remoção
     */
    boolean removePermission(UUID uuid, String permission);

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

    /**
     * Consulta uma option do jogador.
     *
     * @param uuid o uuid do jogador
     * @param option a option para consulta
     * @return uma String vazia se ela não existe, ou então
     * a option.
     */
    String getOption(UUID uuid, String option);

    /**
     * Define uma option do jogador.
     *
     * @param uuid o uuid do jogador
     * @param option a option para definir
     * @param value o valor para definir
     * @return uma String vazia se ela não existe, ou então
     * a option.
     */
    boolean setOption(UUID uuid, String option, String value);

}
