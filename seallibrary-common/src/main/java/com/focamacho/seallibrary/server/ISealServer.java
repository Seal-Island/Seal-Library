package com.focamacho.seallibrary.server;

import com.focamacho.seallibrary.command.ISealCommand;
import com.focamacho.seallibrary.player.ISealPlayer;

import java.util.List;
import java.util.UUID;

/**
 * Representa o servidor.
 */
@SuppressWarnings("unused")
public interface ISealServer {

    /**
     * Retorna o objeto original.
     * @return o objeto original do servidor.
     */
    Object toOriginal();

    /**
     * Executa um comando.
     * @param command o comando desejado.
     */
    void runCommand(String command);

    /**
     * Retorna o jogador com o UUID
     * informado.
     * @param uuid o uuid do jogador desejado.
     * @return o jogador, ou null caso ele não esteja
     * online.
     */
    ISealPlayer getPlayer(UUID uuid);

    /**
     * Retorna o jogador com o nome
     * informado.
     * @param name o nome do jogador desejado.
     * @return o jogador, ou null caso ele não esteja
     * online.
     */
    default ISealPlayer getPlayer(String name) {
        for (ISealPlayer player : this.getPlayers()) {
            if(player.getName().equalsIgnoreCase(name)) return player;
        }
        return null;
    }

    /**
     * Retorna todos os jogadores que estão
     * online.
     * @return os jogadores online.
     */
    List<ISealPlayer> getPlayers();

    /**
     * Desliga o servidor.
     */
    void shutdown();

    /**
     * Envia uma mensagem para todos
     * os jogadores online.
     */
    default void broadcast(String message) {
        this.getPlayers().forEach(player -> player.sendMessage(message));
    }

    /**
     * Registra um comando.
     * @param plugin a instância do plugin que está
     *               registrando esse comando.
     * @param command o comando para registrar.
     */
    void registerCommand(Object plugin, ISealCommand command);

    /**
     * Retorna o local onde fica a
     * pasta de configuração do
     * servidor.
     * @return o caminho para a pasta
     * de configuração.
     */
    String getConfigFolder();

}
