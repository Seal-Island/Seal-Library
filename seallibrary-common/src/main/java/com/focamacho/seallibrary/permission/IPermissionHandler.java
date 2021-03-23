package com.focamacho.seallibrary.permission;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unused")
public interface IPermissionHandler {

    /**
     *  Consulta se o jogador possui uma
     *  permissão.
     *
     * @param uuid o uuid do jogador
     * @param permission a permissão para consulta
     */
    CompletableFuture<Boolean> hasPermission(UUID uuid, String permission);

    /**
     *  Adiciona uma permissão para
     *  o jogador.
     *
     * @param uuid o uuid do jogador
     * @param permission a permissão para adição
     */
    CompletableFuture<Boolean> addPermission(UUID uuid, String permission);

    /**
     *  Remove uma permissão
     *  do jogador.
     *
     * @param uuid o uuid do jogador
     * @param permission a permissão para remoção
     */
    CompletableFuture<Boolean> removePermission(UUID uuid, String permission);

    /**
     *  Consulta se o jogador está em um
     *  grupo.
     *
     * @param uuid o uuid do jogador
     * @param group o grupo para consulta
     */
    CompletableFuture<Boolean> hasGroup(UUID uuid, String group);

    /**
     * Adiciona o jogador no grupo
     * especificado.
     *
     * @param uuid o uuid do jogador
     * @param group o grupo desejado
     * @return true se a ação foi efetuada com sucesso
     * e false caso algum erro tenha ocorrido.
     */
    CompletableFuture<Boolean> addGroup(UUID uuid, String group);

    /**
     * Remove o jogador do grupo
     * especificado.
     *
     * @param uuid o uuid do jogador
     * @param group o grupo desejado
     * @return true se a ação foi efetuada com sucesso
     * e false caso algum erro tenha ocorrido.
     */
    CompletableFuture<Boolean> removeGroup(UUID uuid, String group);

    /**
     * Consulta uma option do jogador.
     *
     * @param uuid o uuid do jogador
     * @param option a option para consulta
     * @return uma String vazia se ela não existe, ou então
     * a option.
     */
    CompletableFuture<String> getOption(UUID uuid, String option);

    /**
     * Define uma option do jogador.
     *
     * @param uuid o uuid do jogador
     * @param option a option para definir
     * @param value o valor para definir
     * @return uma String vazia se ela não existe, ou então
     * a option.
     */
    CompletableFuture<Boolean> setOption(UUID uuid, String option, String value);

}
