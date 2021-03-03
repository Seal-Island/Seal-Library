package com.focamacho.seallibrary.command;

import com.focamacho.seallibrary.command.lib.ISealCommandSender;

/**
 * Representa um comando
 * no servidor.
 */
public interface ISealCommand {

    /**
     * Retorna o nome do comando.
     * @return o nome.
     */
    String getName();

    /**
     * Retorna a descrição do comando.
     * @return a descrição.
     */
    String getDescription();


    /**
     * Retorna os aliases do comando.
     * @return os aliases.
     */
    String[] getAliases();

    /**
     * Retorna a permissão necessária para
     * usar o comando.
     * @return a permissão.
     */
    String getPermission();

    /**
     * Executa esse comando.
     * @param sender quem enviou o comando.
     * @param args os argumentos utilizados.
     */
    void run(ISealCommandSender sender, String[] args);

}
