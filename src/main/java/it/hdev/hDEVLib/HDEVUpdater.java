package it.hdev.hDEVLib;

import it.hdev.hDEVLib.api.updater.SpigotUpdater;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class HDEVUpdater extends SpigotUpdater {

    @Getter
    private boolean isUpdateAvailable = false;

    public HDEVUpdater(JavaPlugin javaPlugin, String id) {
        super(javaPlugin, id);
    }


    @Override
    public void onNewUpdateAvailable(String version, String downloadLink) {
        isUpdateAvailable = true;
    }
}
