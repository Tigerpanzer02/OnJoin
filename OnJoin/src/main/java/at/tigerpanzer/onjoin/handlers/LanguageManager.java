

/*
 *  OnJoin - Your Server Join Plugin
 *          With this plugin, joins are
 *          unique on your server​
 *
 *  Maintained by Tigerpanzer_02
 */

package at.tigerpanzer.onjoin.handlers;

import at.tigerpanzer.onjoin.Main;
import at.tigerpanzer.onjoin.util.MessageUtils;
import at.tigerpanzer.onjoin.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.List;


public class LanguageManager {

    private static Main plugin;

    public LanguageManager(Main main) {
        plugin = main;
    }

    public static List<String> getLanguageList(String list) {
        return getLocaleFile().getStringList(list);
    }

    public static String getLanguageMessage(String message) {
        return getLocaleFile().getString(message, "ERR_MESSAGE_NOT_FOUND");
    }

    public static boolean getLanguageBoolean(String message) {
        return getLocaleFile().getBoolean(message, false);
    }

    public static boolean getLanguageBoolean(String message, boolean trueorfalse) {
        return getLocaleFile().getBoolean(message, trueorfalse);
    }

    public static ConfigurationSection getLanguageSection(String section) {
        return getLocaleFile().getConfigurationSection(section);
    }


    private static FileConfiguration getLocaleFile() {
        if(!plugin.getConfig().get("locale").toString().equalsIgnoreCase("de") && !plugin.getConfig().get("locale").toString().equalsIgnoreCase("default") && !plugin.getConfig().get("locale").toString().equalsIgnoreCase("hu")) {
            try {
                return Utils.getConfig(plugin, "language_" + plugin.getConfig().get("locale").toString());
            } catch(Exception ex) {
                MessageUtils.errorOccurred();
                ex.printStackTrace();
                Bukkit.getConsoleSender().sendMessage(Utils.color(plugin.consolePrefix + " &7Returning with language.yml because the defined does not exist: " + "language_" + plugin.getConfig().get("locale").toString()));
                return Utils.getConfig(plugin, "language");
            }
        } else {
            switch(plugin.getConfig().get("locale").toString()) {
                case "de":
                    if(!new File(plugin.getDataFolder() + File.separator + "language_de.yml").exists()) {
                        plugin.saveResource("language_de.yml", false);
                    }
                    return Utils.getConfig(plugin, "language_de");
                case "hu":
                    if(!new File(plugin.getDataFolder() + File.separator + "language_hu.yml").exists()) {
                        plugin.saveResource("language_hu.yml", false);
                    }
                    return Utils.getConfig(plugin, "language_hu");
                default:
                    if(!new File(plugin.getDataFolder() + File.separator + "language.yml").exists()) {
                        plugin.saveResource("language.yml", false);
                    }
                    return Utils.getConfig(plugin, "language");
            }
        }
    }
}
