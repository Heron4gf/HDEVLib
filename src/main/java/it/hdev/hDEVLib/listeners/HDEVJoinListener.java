package it.hdev.hDEVLib.listeners;

import it.hdev.hDEVLib.api.HDEVPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Set;

public class HDEVJoinListener implements Listener {

    private final Set<HDEVPlugin> plugins;
    private final MiniMessage miniMessage = MiniMessage.miniMessage();

    public HDEVJoinListener(Set<HDEVPlugin> plugins) {
        this.plugins = plugins;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // Only inform admins about new plugins
        if (!player.hasPermission("hdev.admin")) {
            return;
        }

        for (HDEVPlugin hdevPlugin : plugins) {
            if (hdevPlugin.isJustnew() && !hdevPlugin.isInstalled()) {
                // Example MiniMessage string with click event to open a URL
                String mmText = """
                        <white>A new HDEV plugin is available! <dark_purple>%pluginName% <dark_purple>%pluginDesc% <dark_purple><under><click:open_url:'%downloadURL%'>download</click>
                        """
                        .replace("%pluginName%", hdevPlugin.getName())
                        .replace("%pluginDesc%", hdevPlugin.getDescription())
                        .replace("%downloadURL%", hdevPlugin.getDownloadURL());

                Component component = miniMessage.deserialize(mmText);
                player.sendMessage(component);
            }
        }
    }
}
