package com.focamacho.seallibrary.economy;

import com.focamacho.seallibrary.logger.SealLogger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.EventContext;
import org.spongepowered.api.service.economy.Currency;
import org.spongepowered.api.service.economy.EconomyService;
import org.spongepowered.api.service.economy.account.UniqueAccount;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public class EconomyHandlerSponge implements IEconomyHandler {

    private final Cause emptyCause = Cause.builder().append("").build(EventContext.empty());
    private final EconomyService economyService;

    {
        Optional<EconomyService> service = Sponge.getServiceManager().provide(EconomyService.class);
        if(service.isPresent()) {
            economyService = service.get();
        } else {
            SealLogger.error("Nenhum plugin de economia compatível foi carregado.",
                    "Por favor, instale um plugin compatível com o Sponge.",
                    "O servidor será desligado para evitar problemas.");
            economyService = null;
            Sponge.getServer().shutdown();
        }
    }

    @Override
    public double getMoney(UUID player) {
        Optional<UniqueAccount> account = economyService.getOrCreateAccount(player);
        account.ifPresent(uniqueAccount -> uniqueAccount.getBalance(economyService.getDefaultCurrency()));
        return 0;
    }

    @Override
    public void addMoney(UUID player, double value) {
        Optional<UniqueAccount> account = economyService.getOrCreateAccount(player);
        account.ifPresent(uniqueAccount -> uniqueAccount.deposit(economyService.getDefaultCurrency(), BigDecimal.valueOf(value), emptyCause));
    }

    @Override
    public void removeMoney(UUID player, double value) {
        Optional<UniqueAccount> account = economyService.getOrCreateAccount(player);
        account.ifPresent(uniqueAccount -> uniqueAccount.withdraw(economyService.getDefaultCurrency(), BigDecimal.valueOf(value), emptyCause));
    }

    @Override
    public double getMoney(UUID player, String currency) {
        Optional<UniqueAccount> account = economyService.getOrCreateAccount(player);
        Currency cur = getCurrency(currency);
        if(cur != null) {
            account.ifPresent(uniqueAccount -> uniqueAccount.getBalance(cur));
        }
        return 0;
    }

    @Override
    public void addMoney(UUID player, double value, String currency) {
        Optional<UniqueAccount> account = economyService.getOrCreateAccount(player);
        Currency cur = getCurrency(currency);
        if(cur != null) {
            account.ifPresent(uniqueAccount -> uniqueAccount.deposit(cur, BigDecimal.valueOf(value), emptyCause));
        }
    }

    @Override
    public void removeMoney(UUID player, double value, String currency) {
        Optional<UniqueAccount> account = economyService.getOrCreateAccount(player);
        Currency cur = getCurrency(currency);
        if(cur != null) {
            account.ifPresent(uniqueAccount -> uniqueAccount.withdraw(cur, BigDecimal.valueOf(value), emptyCause));
        }
    }

    private Currency getCurrency(String currency) {
        for (Currency cur : economyService.getCurrencies()) {
            if(cur.getId().equalsIgnoreCase(currency)) return cur;
        }
        return null;
    }

}
