package it.hdev.hDEVLib;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public final class HDEVLib extends JavaPlugin {

    @Getter
    private static HDEVLib instance;

    @Getter
    private HDEVUpdater updater;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        updater = new HDEVUpdater(this, "HDEVLib");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
