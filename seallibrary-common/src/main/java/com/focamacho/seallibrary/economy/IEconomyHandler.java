package com.focamacho.seallibrary.economy;

import java.util.UUID;

public interface IEconomyHandler {

    /**
     * Consulta a quantia de dinheiro
     * que o jogador possui na moeda padrão.
     * @param player o jogador para consulta.
     * @return a quantia de dinheiro que o jogador possui.
     */
    double getMoney(UUID player);

    /**
     * Consulta se o jogador possui
     * uma quantia especificada de dinheiro
     * na moeda padrão.
     * @param player o jogador para consulta.
     * @param value a quantia para consulta.
     * @return se o jogador possui essa quantia ou não.
     */
    default boolean hasMoney(UUID player, double value) {
        return getMoney(player) - value >= 0;
    }

    /**
     * Adiciona uma quantidade de dinheiro
     * na conta desse jogador na moeda padrão.
     * @param player o jogador para a adição.
     * @param value a quantia para adicionar.
     */
    void addMoney(UUID player, double value);

    /**
     * Remove uma quantidade de dinheiro
     * da conta desse jogador na moeda padrão.
     * @param player o jogador para a remoção.
     * @param value a quantia para remover.
     */
    void removeMoney(UUID player, double value);

    /**
     * Consulta a quantia de dinheiro
     * que o jogador possui na moeda especificada.
     * @param player o jogador para consulta.
     * @return a quantia de dinheiro que o jogador possui.
     */
    double getMoney(UUID player, String currency);

    /**
     * Consulta se o jogador possui
     * uma quantia especificada de dinheiro
     * na moeda especificada.
     * @param player o jogador para consulta.
     * @param value a quantia para consulta.
     * @return se o jogador possui essa quantia ou não.
     */
    default boolean hasMoney(UUID player, double value, String currency) {
        return getMoney(player, currency) - value >= 0;
    }

    /**
     * Adiciona uma quantidade de dinheiro
     * na conta desse jogador na moeda especificada.
     * @param player o jogador para a adição.
     * @param value a quantia para adicionar.
     */
    void addMoney(UUID player, double value, String currency);

    /**
     * Remove uma quantidade de dinheiro
     * da conta desse jogador na moeda especificada.
     * @param player o jogador para a remoção.
     * @param value a quantia para remover.
     */
    void removeMoney(UUID player, double value, String currency);

}
