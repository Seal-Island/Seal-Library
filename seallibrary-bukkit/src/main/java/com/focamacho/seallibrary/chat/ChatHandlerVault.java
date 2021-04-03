package com.focamacho.seallibrary.chat;

import com.focamacho.seallibrary.logger.SealLogger;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class ChatHandlerVault implements IChatHandler {

    private final Chat chatService;

    {
        RegisteredServiceProvider<Chat> chatService = Bukkit.getServer().getServicesManager().getRegistration(Chat.class);
        if(chatService != null) {
            this.chatService = chatService.getProvider();
        } else {
            SealLogger.error("Nenhum plugin de chat compatível foi carregado.",
                    "Por favor, instale um plugin compatível com o Vault.",
                    "O servidor será desligado para evitar problemas.");
            this.chatService = null;
            Bukkit.getServer().shutdown();
        }
    }

    @Override
    public CompletableFuture<String> getPrefix(UUID uuid) {
        CompletableFuture<String> future = new CompletableFuture<>();
        future.complete(chatService.getPlayerPrefix(null, Bukkit.getOfflinePlayer(uuid)));
        return future;
    }

    @Override
    public CompletableFuture<String> getSuffix(UUID uuid) {
        CompletableFuture<String> future = new CompletableFuture<>();
        future.complete(chatService.getPlayerSuffix(null, Bukkit.getOfflinePlayer(uuid)));
        return future;
    }

}
