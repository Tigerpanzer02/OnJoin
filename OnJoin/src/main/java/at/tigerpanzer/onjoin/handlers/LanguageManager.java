

package at.tigerpanzer.onjoin.handlers;

import at.tigerpanzer.onjoin.Main;
import at.tigerpanzer.onjoin.util.MessageUtils;
import at.tigerpanzer.onjoin.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import pl.plajerlair.core.services.ServiceRegistry;
import pl.plajerlair.core.services.locale.Locale;
import pl.plajerlair.core.services.locale.LocaleRegistry;
import pl.plajerlair.core.services.locale.LocaleService;
import pl.plajerlair.core.utils.ConfigUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

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
