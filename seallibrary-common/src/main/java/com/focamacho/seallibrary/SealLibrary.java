package com.focamacho.seallibrary;

import com.focamacho.seallibrary.command.commands.SealLibraryCommand;
import com.focamacho.seallibrary.server.SealServer;

public class SealLibrary {

    private static Object instance;

    public static void init(Object instance) {
        SealLibrary.instance = instance;
        SealServer.get().registerCommand(instance, new SealLibraryCommand());
    }

    public static void stop() {}

    public static void reload() {}

}
