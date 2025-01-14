package it.hdev.hDEVLib.api.updater;

import lombok.Data;
import org.bukkit.plugin.java.JavaPlugin;

public abstract @Data class AbstractUpdater implements Updater {
    private JavaPlugin javaPlugin;
    private String id;

    public AbstractUpdater(JavaPlugin javaPlugin, String id) {
        this.id = id;
        this.javaPlugin = javaPlugin;
    }

    public abstract String fetchLatestVersion();
}
