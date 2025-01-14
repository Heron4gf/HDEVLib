package it.hdev.hDEVLib.api.simpledatabase;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.Serializable;

public interface SimpleDatabase {
    void saveData(JavaPlugin javaPlugin, String table, String key, Serializable serializable) throws DatabaseException;

    Object getData(JavaPlugin javaPlugin, String table, String key) throws DatabaseException;

    boolean connect(JavaPlugin javaPlugin) throws DatabaseException;

    boolean disconnect(JavaPlugin javaPlugin) throws DatabaseException;
}
