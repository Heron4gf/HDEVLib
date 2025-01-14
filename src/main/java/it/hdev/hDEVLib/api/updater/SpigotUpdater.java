package it.hdev.hDEVLib.api.updater;

import it.hdev.hDEVLib.api.webutils.WebUtils;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.JSONObject;

import java.util.Date;

public abstract class SpigotUpdater extends AbstractUpdater {

    public static final String SPIGOT_LATEST_VERSION_CHECK_LINK = "https://api.spiget.org/v2/resources/{id}/versions/latest";
    public static final String SPIGOT_RESOURCE_INFO_LINK = "https://api.spiget.org/v2/resources/{id}";
    public static final String SPIGOT_LATEST_UPDATE_CHECK_LINK = "https://api.spiget.org/v2/resources/{id}/updates/latest";


    public SpigotUpdater(JavaPlugin javaPlugin, String id) {
        super(javaPlugin, id);
    }

    @Override
    public String fetchLatestVersion() {
        JSONObject jsonObject = WebUtils.getJSONResponse(WebUtils.safeURL(SPIGOT_LATEST_VERSION_CHECK_LINK.replace("{id}", getId()+"")));
        String version_name = jsonObject.getString("name");
        Date date = new Date(jsonObject.getLong("releaseDate"));
        int id = jsonObject.getInt("id");

        return version_name;
    }

}
