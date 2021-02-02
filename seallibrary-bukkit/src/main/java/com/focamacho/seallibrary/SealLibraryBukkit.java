package com.focamacho.seallibrary;

import com.focamacho.seallibrary.impl.ImplementationsBukkit;
import com.focamacho.seallibrary.menu.MenuListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class SealLibraryBukkit extends JavaPlugin {

    public static SealLibraryBukkit instance;

    @Override
    public void onEnable() {
        instance = this;
        ImplementationsBukkit.init();
        SealLibrary.init();

        Bukkit.getPluginManager().registerEvents(new MenuListener(), this);
    }

    @Override
    public void onDisable() {
        SealLibrary.stop();
    }

    @Override
    public void reloadConfig() {
        SealLibrary.reload();
    }

}
