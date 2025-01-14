package it.hdev.hDEVLib.listeners;

import it.hdev.hDEVLib.HDEVLib;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class UpdaterJoinListener implements Listener {
    @EventHandler
    void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if(player.hasPermission("hdev.admin")) {
            if(HDEVLib.getInstance().getUpdater().isUpdateAvailable()) {
                // notify the player with a message formatted with minimessage
            }
        }

    }
}
