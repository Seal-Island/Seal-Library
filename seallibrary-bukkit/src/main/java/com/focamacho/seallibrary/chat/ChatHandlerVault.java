package com.focamacho.seallibrary.chat;

import com.focamacho.seallibrary.logger.SealLogger;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.UUID;

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
    public String getPrefix(UUID uuid) {
        return chatService.getPlayerPrefix(null, Bukkit.getOfflinePlayer(uuid));
    }

    @Override
    public String getSuffix(UUID uuid) {
        return chatService.getPlayerSuffix(null, Bukkit.getOfflinePlayer(uuid));
    }

}
