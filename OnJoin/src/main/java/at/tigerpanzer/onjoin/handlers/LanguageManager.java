

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
    private static Locale pluginLocale;
    private static Properties properties = new Properties();

    public static void init(Main pl) {
        plugin = pl;
        if(!new File(plugin.getDataFolder() + File.separator + "language.yml").exists()) {
            plugin.saveResource("language.yml", false);
        }
        registerLocales();
        setupLocale();
    }

    private static void registerLocales() {
        LocaleRegistry.registerLocale(new Locale("English", "English", "en_GB", "Tigerkatze and POEditor contributors", Arrays.asList("default", "english", "en")));
        LocaleRegistry.registerLocale(new Locale("German", "Deutsch", "de_DE", "Tigerkatze and POEditor contributors", Arrays.asList("deutsch", "german", "de")));
    }

    private static void loadProperties() {
        if(isDefaultLanguageUsed()) {
            return;
        }
        LocaleService service = ServiceRegistry.getLocaleService(plugin);
        if(service.isValidVersion()) {
            LocaleService.DownloadStatus status = service.demandLocaleDownload(pluginLocale.getPrefix());
            if(status == LocaleService.DownloadStatus.FAIL) {
                pluginLocale = LocaleRegistry.getByName("English");
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[OnJoin] Locale service couldn't download latest locale for plugin! English locale will be used instead!");
                return;
            } else if(status == LocaleService.DownloadStatus.SUCCESS) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[OnJoin] Downloaded locale " + pluginLocale.getPrefix() + " properly!");
            } else if(status == LocaleService.DownloadStatus.LATEST) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[OnJoin] Locale " + pluginLocale.getPrefix() + " is latest! Awesome!");
            }
        } else {
            pluginLocale = LocaleRegistry.getByName("English");
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[OnJoin] Your plugin version is too old to use latest locale! Please update plugin to access latest updates of locale!");
            return;
        }
        try {
            properties.load(new FileReader(new File(plugin.getDataFolder() + "/locales/" + pluginLocale.getPrefix() + ".properties")));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static void setupLocale() {
        String localeName = plugin.getConfig().getString("locale", "default").toLowerCase();
        for(Locale locale : LocaleRegistry.getRegisteredLocales()) {
            for(String alias : locale.getAliases()) {
                if(alias.equals(localeName)) {
                    pluginLocale = locale;
                    break;
                }
            }
        }
        if(pluginLocale == null) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[OnJoin] Plugin locale is invalid! Using default one...");
            pluginLocale = LocaleRegistry.getByName("English");
        }
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[OnJoin] Loaded locale " + pluginLocale.getName() + " (" + pluginLocale.getOriginalName() + " ID: " +
                pluginLocale.getPrefix() + ") by " + pluginLocale.getAuthor());
        loadProperties();
    }

    public static boolean isDefaultLanguageUsed() {
        return pluginLocale.getName().equals("English");
    }

    public static List<String> getLanguageList(String path) {
        if(isDefaultLanguageUsed()) {
            return ConfigUtils.getConfig(plugin, "language").getStringList(path);
        } else {
            return Arrays.asList(Utils.colorMessage(path).split(";"));
        }
    }

    public static String getLanguageMessage(String message) {
        if(isDefaultLanguageUsed()) {
            return ConfigUtils.getConfig(plugin, "language").getString(message, "ERR_MESSAGE_NOT_FOUND");
        }
        try {
            return properties.getProperty(ChatColor.translateAlternateColorCodes('&', message));
        } catch(NullPointerException ex) {
            MessageUtils.errorOccurred();
            Bukkit.getConsoleSender().sendMessage("Game message not found!");
            Bukkit.getConsoleSender().sendMessage("Please regenerate your language.yml file! If error still occurs report it to the developer!");
            Bukkit.getConsoleSender().sendMessage("Access string: " + message);
            return "ERR_MESSAGE_NOT_FOUND";
        }
    }


    public static Locale getPluginLocale() {
        return pluginLocale;
    }
}
