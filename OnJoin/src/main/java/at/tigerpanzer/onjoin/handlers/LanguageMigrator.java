

package at.tigerpanzer.onjoin.handlers;

import at.tigerpanzer.onjoin.Main;
import at.tigerpanzer.onjoin.util.MessageUtils;
import at.tigerpanzer.onjoin.util.Utils;
import com.mysql.jdbc.Util;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/*
  NOTE FOR CONTRIBUTORS - Please do not touch this class if you don't know how it works! You can break migrator modyfing these values!
 */
public class LanguageMigrator {


    private static final int LANGUAGE_FILE_VERSION = 1;
    private static final int CONFIG_FILE_VERSION = 3;
    private static Main plugin = JavaPlugin.getPlugin(Main.class);
    private static List<String> migratable = Arrays.asList("config", "language");

    public static void configUpdate() {
        if(plugin.getConfig().getInt("Version") == CONFIG_FILE_VERSION) {
            return;
        }
        Bukkit.getConsoleSender().sendMessage(Utils.color("§7[§eOnJoin§7] =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-="));
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[OnJoin] System notify >> Your config file is outdated! Updating...");
        File file = new File(plugin.getDataFolder() + "/config.yml");

        int version = plugin.getConfig().getInt("Version", 0);
        updateConfigVersionControl(version);

        for(int i = version; i < CONFIG_FILE_VERSION; i++) {
            switch(version) {
                case 1:
                    Utils.insertAfterLine(file, "MySQL:", "    #Should the MySQL connection after 45 minutes reconnected?\n" +
                            "    AutoReconnect: false");
                    break;
                case 2:
                    Utils.removeLineFromFile(file, "  #Should the MySQL connection after 45 minutes reconnected?");
                    Utils.insertAfterLine(file, "MySQL:\n" +
                            "  Enabled:", "  #Should the MySQL connection reconnect after amount of time?");
                    Utils.insertAfterLine(file, "AutoReconnect:", "    #Time in Minutes\n" +
                            "    ReconnectCoolDown: 45");
                    Utils.insertAfterLine(file, "locale:", "# Should the plugin send debug messages into console? | This mode can be good to find bugs!\n" +
                            "debug: false");
                    break;
            }

            version++;
        }
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[OnJoin] [System notify] Config updated, no comments were removed :)");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[OnJoin] [System notify] You're using latest config file version! Nice!");
        Bukkit.getConsoleSender().sendMessage(Utils.color("§7[§eOnJoin§7] =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-="));
    }

    public static void languageFileUpdate() {
        if(Utils.getConfig(plugin, "language").getString("File-Version-Do-Not-Edit", "").equals(String.valueOf(LANGUAGE_FILE_VERSION))) {
            return;
        }
        Bukkit.getConsoleSender().sendMessage(Utils.color("§7[§eOnJoin§7] =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-="));
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[OnJoin] [System notify] Your language file is outdated! Updating...");

        int version = 0;
        if(NumberUtils.isNumber(Utils.getConfig(plugin, "language").getString("File-Version-Do-Not-Edit"))) {
            version = Integer.valueOf(Utils.getConfig(plugin, "language").getString("File-Version-Do-Not-Edit"));
        }
        updateLanguageVersionControl(version);

        File file = new File(plugin.getDataFolder() + "/language.yml");

        for(int i = version; i < LANGUAGE_FILE_VERSION; i++) {
            switch(version) {
                case 1:
                    break;
            }
            version++;
        }
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[OnJoin] [System notify] Language file updated! Nice!");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[OnJoin] [System notify] You're using latest language file version! Nice!");
        Bukkit.getConsoleSender().sendMessage(Utils.color("§7[§eOnJoin§7] =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-="));
    }

    public static void migrateToNewFormat() {
        Bukkit.getConsoleSender().sendMessage(Utils.color("§7[§eOnJoin§7] =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-="));
        MessageUtils.gonnaMigrate();
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "OnJoin is migrating all files to the new file format...");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Don't worry! Old files will be renamed not overridden!");
        for(String file : migratable) {
            if(Utils.getFile(plugin, file).exists()) {
                Utils.getFile(plugin, file).renameTo(new File(plugin.getDataFolder(), "ONJOIN_" + file + ".yml"));
                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Renamed file " + file + ".yml");
            }
        }
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Done! Enabling OnJoin...");
        Bukkit.getConsoleSender().sendMessage(Utils.color("§7[§eOnJoin§7] =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-="));
    }

    private static void updateLanguageVersionControl(int oldVersion) {
        File file = new File(plugin.getDataFolder() + "/language.yml");
        Utils.removeLineFromFile(file, "# Don't edit it. But who's stopping you? It's your server!");
        Utils.removeLineFromFile(file, "# Really, don't edit ;p");
        Utils.removeLineFromFile(file, "File-Version-Do-Not-Edit: " + oldVersion);
        Utils.addNewLines(file, "# Don't edit it. But who's stopping you? It's your server!\r\n# Really, don't edit ;p\r\nFile-Version-Do-Not-Edit: " + LANGUAGE_FILE_VERSION + "\r\n");
    }

    private static void updateConfigVersionControl(int oldVersion) {
        File file = new File(plugin.getDataFolder() + "/config.yml");
        Utils.removeLineFromFile(file, "# Don't modify.");
        Utils.removeLineFromFile(file, "Version: " + oldVersion);
        Utils.removeLineFromFile(file, "# No way! You've reached the end! Let us join!?");
        Utils.addNewLines(file, "# Don't modify.\r\nVersion: " + CONFIG_FILE_VERSION + "\r\n\r\n# No way! You've reached the end! Let us join!?");
    }

}
