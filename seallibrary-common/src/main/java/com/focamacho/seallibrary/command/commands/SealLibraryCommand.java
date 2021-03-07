package com.focamacho.seallibrary.command.commands;

import com.focamacho.seallibrary.command.ISealCommand;
import com.focamacho.seallibrary.command.lib.ISealCommandSender;
import com.focamacho.seallibrary.item.ISealStack;
import com.focamacho.seallibrary.player.ISealPlayer;
import com.focamacho.seallibrary.server.SealServer;

public class SealLibraryCommand implements ISealCommand {

    String[] aliases = {"seallibrary"};

    @Override
    public String getName() {
        return "Seal Library";
    }

    @Override
    public String getDescription() {
        return "Comandos da Seal Library.";
    }

    @Override
    public String[] getAliases() {
        return aliases;
    }

    @Override
    public String getPermission() {
        return "seallibrary.command";
    }

    @Override
    public void run(ISealCommandSender sender, String[] args) {
        if(args.length == 1) {
            if(args[0].equalsIgnoreCase("itemtonbt") && sender.isPlayer()) {
                ISealPlayer player = sender.getPlayer();
                if(!player.getMainHand().isEmpty()) {
                    String json = player.getMainHand().toJson();
                    SealServer.get().runCommand("tellraw " + player.getName() + " {\"text\":\"" + json + "\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"" + json + "\"},\"hoverEvent\":{\"action\":\"show_text\", \"value\":\"" + json + "\"}}");
                }
            }
        }
    }

}
