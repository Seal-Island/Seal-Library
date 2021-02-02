package com.focamacho.seallibrary.player;

import com.focamacho.seallibrary.menu.IMenu;
import com.focamacho.seallibrary.permission.PermissionHandler;

import java.util.UUID;

/**
 *  Classe que representa um jogador
 *  no servidor.
 */
public interface ISealPlayer {

    /**
     * Retorna o jogador original
     * @return o jogador original.
     */
    Object toOriginal();

    /**
     * Retorna o UUID do jogador
     * @return o UUID do jogador.
     */
    UUID getUUID();

    /**
     * Envia uma mensagem de chat
     * para o jogador.
     * @param message a mensagem que será enviada.
     */
    void sendMessage(String message);

    /**
     * Envia mensagens de chat
     * para o jogador.
     * @param messages as mensagens que serão enviadas.
     */
    default void sendMessages(String... messages) {
        for (String message : messages) {
            sendMessage(message);
        }
    }

    /**
     * Desconecta o jogador do
     * servidor.
     * @param message a mensagem de desconexão.
     */
    void kick(String message);

    /**
     * Abre um inventário para
     * o jogador.
     * @param inventory o inventário para ser aberto.
     */
    void openInventory(Object inventory);

    /**
     * Abre um menu para
     * o jogador.
     * @param menu o menu para ser aberto.
     */
    default void openInventory(IMenu menu) {
        openInventory(menu.get());
    }

    /**
     * Fecha o inventário que o
     * jogador tem aberto no momento.
     */
    void closeInventory();

    /**
     * Retorna o prefix do jogador definido
     * no plugin de permissões.
     * @return o prefix do jogador ou null
     * caso ele não possua um.
     */
    default String getPrefix() {
        return PermissionHandler.getPrefix(getUUID());
    }

    /**
     * Retorna o suffix do jogador definido
     * no plugin de permissões.
     * @return o suffix do jogador ou null
     * caso ele não possua um.
     */
    default String getSuffix() {
        return PermissionHandler.getSuffix(getUUID());
    }

    /**
     *  Consulta se o jogador possui uma
     *  permissão.
     *
     * @param permission a permissão para consulta
     */
    default boolean hasPermission(String permission) {
        return PermissionHandler.hasPermission(getUUID(), permission);
    }

    /**
     *  Consulta se o jogador está em um
     *  grupo.
     *
     * @param group o grupo para consulta
     */
    default boolean hasGroup(String group) {
        return PermissionHandler.hasGroup(getUUID(), group);
    }

    /**
     * Adiciona o jogador no grupo
     * especificado.
     *
     * @param group o grupo desejado
     * @return true se a ação foi efetuada com sucesso
     * e false caso algum erro tenha ocorrido.
     */
    default boolean addGroup(String group) {
        return PermissionHandler.addGroup(getUUID(), group);
    }

    /**
     * Remove o jogador do grupo
     * especificado.
     *
     * @param group o grupo desejado
     * @return true se a ação foi efetuada com sucesso
     * e false caso algum erro tenha ocorrido.
     */
    default boolean removeGroup(String group) {
        return PermissionHandler.removeGroup(getUUID(), group);
    }

}
