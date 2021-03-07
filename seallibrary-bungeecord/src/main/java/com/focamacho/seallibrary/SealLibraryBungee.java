package com.focamacho.seallibrary;

import com.focamacho.seallibrary.impl.ImplementationsBungee;
import net.md_5.bungee.api.plugin.Plugin;

public class SealLibraryBungee extends Plugin {

    public static SealLibraryBungee instance;

    @Override
    public void onEnable() {
        instance = this;
        ImplementationsBungee.init();
        SealLibrary.init(instance);
    }

    @Override
    public void onDisable() {
        SealLibrary.stop();
    }

}
