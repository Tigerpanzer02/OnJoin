/*
 *  OnJoin - Your Server Join Plugin
 *          With this plugin, joins are
 *          unique on your server​
 *
 *  Maintained by Tigerpanzer_02
 */

package at.tigerpanzer.onjoin;

import at.tigerpanzer.onjoin.command.JoinCommand;
import at.tigerpanzer.onjoin.events.JoinExecuteCommand;
import at.tigerpanzer.onjoin.events.JoinFirework;
import at.tigerpanzer.onjoin.events.JoinQuitListener;
import at.tigerpanzer.onjoin.handlers.LanguageManager;
import at.tigerpanzer.onjoin.handlers.LanguageMigrator;
import at.tigerpanzer.onjoin.util.MessageUtils;

import at.tigerpanzer.onjoin.util.MySQL;
import at.tigerpanzer.onjoin.util.UpdateChecker;
import at.tigerpanzer.onjoin.util.Utils;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin {

    private boolean needUpdateJoin;
    private boolean placeholderAPI;
    private boolean mySQLEnabled;
    private boolean firstJoinEnabled;
    public boolean oldversion;
    public String consolePrefix;
    public static MySQL mysql;

    @Override
    public void onEnable() {
        //check if using releases before 2.1.0
        if(Utils.getConfig(this, "config").getInt("Version") <= 5) {
            LanguageMigrator.migrateToNewFormat();
            oldversion = true;
        }
        if((Utils.getConfig(this, "config").isSet("PlaceholderAPI") && Utils.getConfig(this, "config").isSet("Help.HelpText"))) {
            LanguageMigrator.migrateToNewFormat();
            oldversion = true;
        }
        saveDefaultConfig();
        LanguageManager.init(this);
        LanguageMigrator.configUpdate();
        LanguageMigrator.languageFileUpdate();
        consolePrefix = Utils.color(LanguageManager.getLanguageMessage("Console.PrefixConsole"));
        needUpdateJoin = false;
        mySQLEnabled = false;
        firstJoinEnabled = false;
        Bukkit.getConsoleSender().sendMessage(Utils.color(consolePrefix + " &7=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-="));
        Bukkit.getConsoleSender().sendMessage(Utils.color(consolePrefix + " &cWird &aGESTARTET &7| &cis &aSTARTING"));
        register();
        if(getConfig().getBoolean("MySQL.Enabled", false)) {
            connectMySQL();
            mySQLEnabled = true;
        }
        if(getConfig().getBoolean("Join.UpdateMessageOn", true)) {
            update();
        }
        if(getConfig().getBoolean("FirstJoin.Enabled", false)) {
            firstJoinEnabled = true;
        }
        if(oldversion) {
            MessageUtils.info();
            Bukkit.getConsoleSender().sendMessage(Utils.color(consolePrefix + " &cWe detected that you have used a version before 2.1.0! Please have a look on the &dUpdateChanges"));
        }
        Bukkit.getConsoleSender().sendMessage(Utils.color(consolePrefix + " &cPlugin version: &e" + getDescription().getVersion()));
        Bukkit.getConsoleSender().sendMessage(Utils.color(consolePrefix + " &cPlugin author: &e" + getDescription().getAuthors()));
        Bukkit.getConsoleSender().sendMessage(Utils.color(consolePrefix + " &cPlugin status: &aaktiviert &c| &aenabled"));
        if(Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            Bukkit.getConsoleSender().sendMessage(Utils.color(consolePrefix + " §a✔ §ePlaceholderAPI §7| §aVersion§7:§e " + PlaceholderAPIPlugin.getInstance().getDescription().getVersion()));
            placeholderAPI = true;
        } else {
            Bukkit.getConsoleSender().sendMessage(Utils.color(consolePrefix + " §c✖ §4PlaceholderAPI"));
            placeholderAPI = false;
        }
        if(mySQLEnabled) {
            Bukkit.getConsoleSender().sendMessage(Utils.color(consolePrefix + " §a✔ §eMySQL"));
        } else {
            Bukkit.getConsoleSender().sendMessage(Utils.color(consolePrefix + " §c✖ §4MySQL"));
        }
        Bukkit.getConsoleSender().sendMessage(Utils.color(consolePrefix + " &7=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-="));
    }

    @Override
    public void onDisable() {
        if(mySQLEnabled) {
            mysql.Disconnect();
        }
        Bukkit.getConsoleSender().sendMessage(Utils.color(consolePrefix + " &7=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-="));
        Bukkit.getConsoleSender().sendMessage(Utils.color(consolePrefix + " &cPlugin version: &e" + getDescription().getVersion()));
        Bukkit.getConsoleSender().sendMessage(Utils.color(consolePrefix + " &cPlugin author: &e" + getDescription().getAuthors()));
        Bukkit.getConsoleSender().sendMessage(Utils.color(consolePrefix + " &cPlugin status: &4deaktiviert &c| &4disabled"));
        Bukkit.getConsoleSender().sendMessage(Utils.color(consolePrefix + " &7=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-="));
    }

    private void update() {
        UpdateChecker.init(this, 56907).requestUpdateCheck().whenComplete((result, exception) -> {
            if(result.requiresUpdate()) {
                if(result.getNewestVersion().contains("b")) {
                    if(getConfig().getBoolean("Update-Notifier.Notify-Beta-Versions", true)) {
                        Bukkit.getConsoleSender().sendMessage(Utils.color(consolePrefix + " &7=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-="));
                        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[OnJoin] Your software is ready for update! However it's a BETA VERSION. Proceed with caution.");
                        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[OnJoin] Current version %old%, latest version %new%".replace("%old%", getDescription().getVersion()).replace("%new%",
                                result.getNewestVersion()));
                        Bukkit.getConsoleSender().sendMessage(Utils.color(consolePrefix + " &7=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-="));
                    }
                    return;
                }
                needUpdateJoin = true;
                Bukkit.getConsoleSender().sendMessage(Utils.color(consolePrefix + " &7=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-="));
                MessageUtils.updateIsHere();
                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Your OnJoin plugin is outdated! Download it to keep with latest changes and fixes.");
                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Disable this option in config.yml if you wish.");
                Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "Current version: " + ChatColor.RED + getDescription().getVersion() + ChatColor.YELLOW + " Latest version: " + ChatColor.GREEN + result.getNewestVersion());
                Bukkit.getConsoleSender().sendMessage(Utils.color(consolePrefix + " &7=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-="));
            }
        });
    }

    private void register() {
        new JoinCommand(this);
        new JoinQuitListener(this);
        new JoinFirework(this);
        new JoinExecuteCommand(this);
    }

    private void connectMySQL() {
        Main.mysql = new MySQL(getConfig().getString("MySQL.Host"), getConfig().getString("MySQL.Database"), getConfig().getString("MySQL.Username"), getConfig().getString("MySQL.Password"), getConfig().getInt("MySQL.Port", 3306));
        if(getConfig().getBoolean("MySQL.AutoReconnect.Enabled")) {
            Bukkit.getScheduler().runTaskTimer(this, () -> {
                if(mySQLEnabled)
                    mysql.Reconnect();
            }, ((20L * 60) * getConfig().getInt("MySQL.AutoReconnect.ReconnectCoolDown")), ((20L * 60) * getConfig().getInt("MySQL.AutoReconnect.ReconnectCoolDown")));
        }
    }

    public String getConsolePrefix() {
        return consolePrefix;
    }

    public boolean needUpdateJoin() {
        return needUpdateJoin;
    }

    public boolean firstJoin() {
        return firstJoinEnabled;
    }

    public boolean mySQLEnabled() {
        return mySQLEnabled;
    }

    public void setNeedUpdateJoin(boolean needUpdateJoin) {
        this.needUpdateJoin = needUpdateJoin;
    }

    public boolean isPlaceholderAPIEnabled() {
        return placeholderAPI;
    }

    public static MySQL getMysql() {
        return mysql;
    }
}
