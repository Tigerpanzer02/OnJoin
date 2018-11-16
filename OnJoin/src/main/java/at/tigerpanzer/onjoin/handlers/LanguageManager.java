

package at.tigerpanzer.onjoin.handlers;

import at.tigerpanzer.onjoin.Main;
import pl.plajerlair.core.utils.ConfigUtils;

import java.io.File;
import java.util.List;


public class LanguageManager {

    private static Main plugin;

    public static void init(Main pl) {
        plugin = pl;
        if(!new File(plugin.getDataFolder() + File.separator + "language.yml").exists()) {
            plugin.saveResource("language.yml", false);
        }
    }
    public static List<String> getLanguageList(String path) {
        return ConfigUtils.getConfig(plugin, "language").getStringList(path);
    }

    public static String getLanguageMessage(String message) {
        return ConfigUtils.getConfig(plugin, "language").getString(message, "ERR_MESSAGE_NOT_FOUND");
    }
}
