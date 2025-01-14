package it.hdev.hDEVLib.api;

import lombok.Data;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;

public @Data class HDEVPlugin {
    private String name;
    private String description;
    private boolean installed;
    private String downloadURL;
    private boolean justnew = false;
    private List<String> addons = new ArrayList<>();

    public HDEVPlugin(String name, String description, boolean installed, String downloadURL, List<String> addons) {
        this.name = name;
        this.description = description;
        this.installed = installed;
        this.downloadURL = downloadURL;
        this.addons.addAll(addons);
    }

    public String getVersion() {
        if(!installed) return null;
        return Bukkit.getPluginManager().getPlugin(name).getDescription().getVersion();
    }
}
