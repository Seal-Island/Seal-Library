package com.focamacho.seallibrary.util;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.EventContext;
import org.spongepowered.api.service.economy.Currency;
import org.spongepowered.api.service.economy.EconomyService;

import java.math.BigDecimal;
import java.util.UUID;

@SuppressWarnings("unused")
public class MoneyUtils {

    public static final EconomyService econ = Sponge.getServiceManager().provide(EconomyService.class).isPresent() ? Sponge.getServiceManager().provide(EconomyService.class).get() : null;
    public static final Currency defaultCurrency = econ != null ? econ.getDefaultCurrency() : null;

    public static Currency getCurrencyById(String id) {
        for (Currency currency : econ.getCurrencies()) {
            if(currency.getId().equalsIgnoreCase(id)) return currency;
        }
        return null;
    }

    public static Currency getCurrencyByIdOrDefault(String id) {
        for (Currency currency : econ.getCurrencies()) {
            if(currency.getId().equalsIgnoreCase(id)) return currency;
        }
        return defaultCurrency;
    }

    public static boolean hasMoney(UUID player, BigDecimal value) {
        return getMoney(player).compareTo(value) >= 0;
    }

    public static boolean hasMoney(Player player, BigDecimal value) {
        return getMoney(player).compareTo(value) >= 0;
    }

    public static BigDecimal getMoney(UUID player) {
        return econ.getOrCreateAccount(player).isPresent() ? econ.getOrCreateAccount(player).get().getBalance(defaultCurrency) : BigDecimal.ZERO;
    }

    public static BigDecimal getMoney(Player player) {
        return getMoney(player.getUniqueId());
    }

    public static void addMoney(Player player, BigDecimal value) {
        if(econ.getOrCreateAccount(player.getUniqueId()).isPresent()) {
            econ.getOrCreateAccount(player.getUniqueId()).get().deposit(defaultCurrency, value, Cause.builder().append("Seal Shops").build(EventContext.builder().build()));
        }
    }

    public static void removeMoney(UUID player, BigDecimal value) {
        if(econ.getOrCreateAccount(player).isPresent()) {
            econ.getOrCreateAccount(player).get().withdraw(defaultCurrency, value, Cause.builder().append("Seal Shops").build(EventContext.builder().build()));
        }
    }

    public static void removeMoney(Player player, BigDecimal value) {
        removeMoney(player.getUniqueId(), value);
    }

    /*
     * Moeda diferente da padrão
     */

    public static boolean hasMoney(UUID player, BigDecimal value, Currency currency) {
        return getMoney(player, currency).compareTo(value) >= 0;
    }

    public static boolean hasMoney(Player player, BigDecimal value, Currency currency) {
        return getMoney(player, currency).compareTo(value) >= 0;
    }

    public static BigDecimal getMoney(UUID player, Currency currency) {
        return econ.getOrCreateAccount(player).isPresent() ? econ.getOrCreateAccount(player).get().getBalance(currency) : BigDecimal.ZERO;
    }

    public static BigDecimal getMoney(Player player, Currency currency) {
        return getMoney(player.getUniqueId(), currency);
    }

    public static void addMoney(Player player, BigDecimal value, Currency currency) {
        if(econ.getOrCreateAccount(player.getUniqueId()).isPresent()) {
            econ.getOrCreateAccount(player.getUniqueId()).get().deposit(currency, value, Cause.builder().append("Seal Library").build(EventContext.builder().build()));
        }
    }

    public static void removeMoney(UUID player, BigDecimal value, Currency currency) {
        if(econ.getOrCreateAccount(player).isPresent()) {
            econ.getOrCreateAccount(player).get().withdraw(currency, value, Cause.builder().append("Seal Library").build(EventContext.builder().build()));
        }
    }

    public static void removeMoney(Player player, BigDecimal value, Currency currency) {
        removeMoney(player.getUniqueId(), value, currency);
    }

}