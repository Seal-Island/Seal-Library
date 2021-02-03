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
                    "O servidor será desligado para evitar problemas.");
            economyService = null;
            Bukkit.getServer().shutdown();
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

}