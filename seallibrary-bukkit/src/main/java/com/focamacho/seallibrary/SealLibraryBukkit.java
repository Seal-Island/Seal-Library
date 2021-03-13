package com.focamacho.seallibrary;

import com.focamacho.seallibrary.impl.ImplementationsBukkit;
import com.focamacho.seallibrary.menu.MenuListener;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class SealLibraryBukkit extends JavaPlugin implements Listener {

    public static SealLibraryBukkit instance;
    private boolean implemented = false;

    @Override
    public void onEnable() {
        instance = this;
        ImplementationsBukkit.init();
        SealLibrary.init(instance);

        Bukkit.getPluginManager().registerEvents(new MenuListener(), this);
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        SealLibrary.stop();
    }

    @Override
    public void reloadConfig() {
        SealLibrary.reload();
    }

    @EventHandler
    public void onLoadWorld(WorldLoadEvent event) {
        if(!implemented) {
            ImplementationsBukkit.postInit();
            implemented = true;
        }
    }

}
