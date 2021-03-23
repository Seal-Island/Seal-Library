package com.focamacho.seallibrary.economy;

import com.focamacho.seallibrary.impl.Implementations;

import java.util.UUID;

/**
 * Classe static para acesso dos métodos
 * de controle de economia.
 *
 * @see IEconomyHandler para mais informações
 * sobre os métodos.
 */
@SuppressWarnings("unused")
public class EconomyHandler {

    public static double getMoney(UUID player) {
        return Implementations.economyHandler.getMoney(player);
    }

    public static boolean hasMoney(UUID player, double value) {
        return Implementations.economyHandler.hasMoney(player, value);
    }

    public static void addMoney(UUID player, double value) {
        Implementations.economyHandler.addMoney(player, value);
    }

    public static void removeMoney(UUID player, double value) {
        Implementations.economyHandler.removeMoney(player, value);
    }

    public static double getMoney(UUID player, String currency) {
        return Implementations.economyHandler.getMoney(player, currency);
    }

    public static boolean hasMoney(UUID player, double value, String currency) {
        return Implementations.economyHandler.hasMoney(player, value, currency);
    }

    public static void addMoney(UUID player, double value, String currency) {
        Implementations.economyHandler.addMoney(player, value, currency);
    }

    public static void removeMoney(UUID player, double value, String currency) {
        Implementations.economyHandler.removeMoney(player, value, currency);
    }

    public static String getCurrencySymbol() {
        return Implementations.economyHandler.getCurrencySymbol();
    }

    public static String getCurrencySingular() {
        return Implementations.economyHandler.getCurrencySingular();
    }

    public static String getCurrencyPlural() {
        return Implementations.economyHandler.getCurrencyPlural();
    }

    public static String getCurrencySymbol(String currency) {
        return Implementations.economyHandler.getCurrencySymbol(currency);
    }

    public static String getCurrencySingular(String currency) {
        return Implementations.economyHandler.getCurrencySingular(currency);
    }

    public static String getCurrencyPlural(String currency) {
        return Implementations.economyHandler.getCurrencyPlural(currency);
    }

    public static boolean isValidCurrency(String currency) {
        return Implementations.economyHandler.isValidCurrency(currency);
    }

}
