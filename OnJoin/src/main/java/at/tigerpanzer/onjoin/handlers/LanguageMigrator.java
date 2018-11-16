

package at.tigerpanzer.onjoin.handlers;

import at.tigerpanzer.onjoin.Main;
import at.tigerpanzer.onjoin.util.MessageUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import pl.plajerlair.core.utils.ConfigUtils;
import pl.plajerlair.core.utils.MigratorUtils;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/*
  NOTE FOR CONTRIBUTORS - Please do not touch this class if you don't know how it works! You can break migrator modyfing these values!
 */
public class LanguageMigrator {


    private static final int LANGUAGE_FILE_VERSION = 1;
    private static final int CONFIG_FILE_VERSION = 1;
  private static Main plugin = JavaPlugin.getPlugin(Main.class);
  private static List<String> migratable = Arrays.asList("config", "language");

  public static void configUpdate() {
    if (plugin.getConfig().getInt("Version") == CONFIG_FILE_VERSION) {
      return;
    }
    Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[OnJoin] System notify >> Your config file is outdated! Updating...");
    File file = new File(plugin.getDataFolder() + "/config.yml");

    int version = plugin.getConfig().getInt("Version", 0);
    updateConfigVersionControl(version);

    for (int i = version; i < LANGUAGE_FILE_VERSION; i++) {
      switch (version) {
        case 1:
          break;
      }
      version++;
    }
    Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[OnJoin] [System notify] Config updated, no comments were removed :)");
    Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[OnJoin] [System notify] You're using latest config file version! Nice!");
  }

  public static void languageFileUpdate() {
    if (ConfigUtils.getConfig(plugin, "language").getString("File-Version-Do-Not-Edit", "").equals(String.valueOf(LANGUAGE_FILE_VERSION))) {
      return;
    }
    Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[OnJoin] [System notify] Your language file is outdated! Updating...");

    int version = 0;
    if (NumberUtils.isNumber(ConfigUtils.getConfig(plugin, "language").getString("File-Version-Do-Not-Edit"))) {
      version = Integer.valueOf(ConfigUtils.getConfig(plugin, "language").getString("File-Version-Do-Not-Edit"));
    }
    updateLanguageVersionControl(version);

    File file = new File(plugin.getDataFolder() + "/language.yml");

    for (int i = version; i < LANGUAGE_FILE_VERSION; i++) {
      switch (version) {
        case 1:
          break;
      }
      version++;
    }
    Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[OnJoin] [System notify] Language file updated! Nice!");
    Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[OnJoin] [System notify] You're using latest language file version! Nice!");
  }

  public static void migrateToNewFormat() {
    MessageUtils.gonnaMigrate();
    Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "OnJoin is migrating all files to the new file format...");
    Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Don't worry! Old files will be renamed not overridden!");
    for (String file : migratable) {
      if (ConfigUtils.getFile(plugin, file).exists()) {
        ConfigUtils.getFile(plugin, file).renameTo(new File(plugin.getDataFolder(), "ONJOIN_" + file + ".yml"));
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Renamed file " + file + ".yml");
      }
    }
    Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Done! Enabling OnJoin...");
  }

  private static void updateLanguageVersionControl(int oldVersion) {
    File file = new File(plugin.getDataFolder() + "/language.yml");
    MigratorUtils.removeLineFromFile(file, "# Don't edit it. But who's stopping you? It's your server!");
    MigratorUtils.removeLineFromFile(file, "# Really, don't edit ;p");
    MigratorUtils.removeLineFromFile(file, "File-Version-Do-Not-Edit: " + oldVersion);
    MigratorUtils.addNewLines(file, "# Don't edit it. But who's stopping you? It's your server!\r\n# Really, don't edit ;p\r\nFile-Version-Do-Not-Edit: " + LANGUAGE_FILE_VERSION + "\r\n");
  }

  private static void updateConfigVersionControl(int oldVersion) {
    File file = new File(plugin.getDataFolder() + "/config.yml");
    MigratorUtils.removeLineFromFile(file, "# Don't modify.");
    MigratorUtils.removeLineFromFile(file, "Version: " + oldVersion);
    MigratorUtils.removeLineFromFile(file, "# No way! You've reached the end! Let us join!?");
    MigratorUtils.addNewLines(file, "# Don't modify\r\nVersion: " + CONFIG_FILE_VERSION + "\r\n\r\n# No way! You've reached the end! Let us join!?");
  }

}
