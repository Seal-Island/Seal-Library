package com.focamacho.seallibrary;

import com.focamacho.seallibrary.impl.ImplementationsSponge;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.game.state.GameStoppedEvent;
import org.spongepowered.api.plugin.Dependency;
import org.spongepowered.api.plugin.Plugin;

@Plugin(
        id = "seallibrary",
        name = "Seal Library",
        description = "Library para facilitar a criação de plugins.",
        authors = {
                "Focamacho"
        },
        dependencies = {
                @Dependency(id = "luckperms")
        }
)
public class SealLibrarySponge {

    public static SealLibrarySponge instance;

    @Inject
    public Logger logger;

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
        instance = this;
        ImplementationsSponge.init();
        SealLibrary.init(instance);
    }

    @Listener
    public void onServerStop(GameStoppedEvent event) {
        SealLibrary.stop();
    }

    @Listener
    public void onServerReload(GameReloadEvent event) {
        SealLibrary.reload();
    }

}
