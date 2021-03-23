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
                    "Algumas coisas não funcionarão corretamente até que um plugin compatível seja instalado.");
            economyService = null;
        }
    }

    @Override
    public double getMoney(UUID player) {
        Optional<UniqueAccount> account = economyService.getOrCreateAccount(player);
        return account.map(uniqueAccount -> uniqueAccount.getBalance(economyService.getDefaultCurrency()).doubleValue()).orElse(0.0);
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
        return account.map(uniqueAccount -> uniqueAccount.getBalance(cur).doubleValue()).orElse(0.0);
    }

    @Override
    public void addMoney(UUID player, double value, String currency) {
        Optional<UniqueAccount> account = economyService.getOrCreateAccount(player);
        Currency cur = getCurrency(currency);
        account.ifPresent(uniqueAccount -> uniqueAccount.deposit(cur, BigDecimal.valueOf(value), emptyCause));
    }

    @Override
    public void removeMoney(UUID player, double value, String currency) {
        Optional<UniqueAccount> account = economyService.getOrCreateAccount(player);
        Currency cur = getCurrency(currency);
        account.ifPresent(uniqueAccount -> uniqueAccount.withdraw(cur, BigDecimal.valueOf(value), emptyCause));
    }

    @Override
    public String getCurrencySymbol() {
        return economyService.getDefaultCurrency().getSymbol().toPlain();
    }

    @Override
    public String getCurrencySingular() {
        return economyService.getDefaultCurrency().getDisplayName().toPlain();
    }

    @Override
    public String getCurrencyPlural() {
        return economyService.getDefaultCurrency().getPluralDisplayName().toPlain();
    }

    @Override
    public String getCurrencySymbol(String currency) {
        return getCurrency(currency).getSymbol().toPlain();
    }

    @Override
    public String getCurrencySingular(String currency) {
        return getCurrency(currency).getDisplayName().toPlain();
    }

    @Override
    public String getCurrencyPlural(String currency) {
        return getCurrency(currency).getPluralDisplayName().toPlain();
    }

    @Override
    public boolean isValidCurrency(String currency) {
        return currency.equalsIgnoreCase("") || economyService.getCurrencies().stream().anyMatch(cur -> cur.getId().equalsIgnoreCase(currency));
    }

    private Currency getCurrency(String currency) {
        for (Currency cur : economyService.getCurrencies()) {
            if(cur.getId().equalsIgnoreCase(currency)) return cur;
        }
        return economyService.getDefaultCurrency();
    }

}
