package com.focamacho.seallibrary.economy;

import com.focamacho.seallibrary.logger.SealLogger;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.UUID;

public class EconomyHandlerVault implements IEconomyHandler {

    private final Economy economyService;

    {
        RegisteredServiceProvider<Economy> service = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        if(service != null) {
            economyService = service.getProvider();
        } else {
            SealLogger.error("Nenhum plugin de economia compatível foi carregado.",
                    "Por favor, instale um plugin compatível com o Vault.",
                    "Algumas coisas não funcionarão corretamente até que um plugin compatível seja instalado.");
            economyService = null;
        }
    }

    @Override
    public double getMoney(UUID player) {
        return economyService.getBalance(Bukkit.getOfflinePlayer(player));
    }

    @Override
    public void addMoney(UUID player, double value) {
        economyService.depositPlayer(Bukkit.getOfflinePlayer(player), value);
    }

    @Override
    public void removeMoney(UUID player, double value) {
        economyService.withdrawPlayer(Bukkit.getOfflinePlayer(player), value);
    }

    @Override
    public double getMoney(UUID player, String currency) {
        return economyService.getBalance(Bukkit.getOfflinePlayer(player));
    }

    @Override
    public void addMoney(UUID player, double value, String currency) {
        economyService.depositPlayer(Bukkit.getOfflinePlayer(player), value);
    }

    @Override
    public void removeMoney(UUID player, double value, String currency) {
        economyService.withdrawPlayer(Bukkit.getOfflinePlayer(player), value);
    }

    @Override
    public String getCurrencySymbol() {
        return "$";
    }

    @Override
    public String getCurrencySingular() {
        return economyService.currencyNameSingular();
    }

    @Override
    public String getCurrencyPlural() {
        return economyService.currencyNamePlural();
    }

    @Override
    public String getCurrencySymbol(String currency) {
        return "$";
    }

    @Override
    public String getCurrencySingular(String currency) {
        String name = economyService.currencyNameSingular();
        if(name == null) name = "";
        return name;
    }

    @Override
    public String getCurrencyPlural(String currency) {
        String name = economyService.currencyNamePlural();
        if(name == null) name = "";
        return name;
    }

}