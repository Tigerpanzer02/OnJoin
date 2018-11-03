package at.tigerpanzer.onjoin;

import at.tigerpanzer.onjoin.command.JoinCommand;
import at.tigerpanzer.onjoin.events.JoinExecuteCommand;
import at.tigerpanzer.onjoin.events.JoinFirework;
import at.tigerpanzer.onjoin.events.JoinQuitListener;
import at.tigerpanzer.onjoin.handlers.LanguageManager;
import at.tigerpanzer.onjoin.handlers.LanguageMigrator;
import at.tigerpanzer.onjoin.util.MessageUtils;

import at.tigerpanzer.onjoin.util.MySQL;
import at.tigerpanzer.onjoin.util.Utils;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import pl.plajerlair.core.services.ServiceRegistry;
import pl.plajerlair.core.utils.UpdateChecker;


public class Main extends JavaPlugin {

    private boolean needUpdateJoin;
    private boolean placeholderAPI;
    private boolean mySQLEnabled;
    private boolean firstJoinEnabled;
    private String consolePrefix;

    @Override
    public void onEnable() {
        ServiceRegistry.registerService(this);
        LanguageManager.init(this);
        LanguageMigrator.configUpdate();
        LanguageMigrator.languageFileUpdate();
        saveDefaultConfig();
        consolePrefix = Utils.color(getConfig().getString("Console.PrefixConsole"));
        needUpdateJoin = false;
        mySQLEnabled = false;
        firstJoinEnabled = false;
        Bukkit.getConsoleSender().sendMessage(Utils.color(consolePrefix + " &cWird &aGESTARTET &7| &cis &aSTARTED"));
        register();
        if(getConfig().getBoolean("MySQL.Enabled", false)) {
            connectMySQL();
        }
        if(getConfig().getBoolean("Join.UpdateMessageOn", true)) {
            update();
        }
        if(getConfig().getBoolean("FirstJoin.Enabled", false)) {
            firstJoinEnabled = true;
        }
        Bukkit.getConsoleSender().sendMessage(Utils.color(consolePrefix + " &7=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-="));
        MessageUtils.info();
        Bukkit.getConsoleSender().sendMessage(Utils.color(consolePrefix + " &cIf you have used a version before 2.0.0 please have a look on the &dUpdateChanges"));
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
            Bukkit.getConsoleSender().sendMessage(Utils.color(consolePrefix + " §c✖ §4eMySQL"));
        }
        Bukkit.getConsoleSender().sendMessage(Utils.color(consolePrefix + " &7=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-="));
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(Utils.color(consolePrefix + " &7=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-="));
        Bukkit.getConsoleSender().sendMessage(Utils.color(consolePrefix + " &cPlugin version: &e" + getDescription().getVersion()));
        Bukkit.getConsoleSender().sendMessage(Utils.color(consolePrefix + " &cPlugin author: &e" + getDescription().getAuthors()));
        Bukkit.getConsoleSender().sendMessage(Utils.color(consolePrefix + " &cPlugin status: &4deaktiviert &c| &4disabled"));
        Bukkit.getConsoleSender().sendMessage(Utils.color(consolePrefix + " &7=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-="));
    }

    private void update() {
        String currentVersion = "v" + Bukkit.getPluginManager().getPlugin("OnJoin").getDescription().getVersion();
        try {
            boolean check = UpdateChecker.checkUpdate(this, currentVersion, 56907);
            if(check) {
                String latestVersion = "v" + UpdateChecker.getLatestVersion();
                if(latestVersion.contains("b")) {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[OnJoin] Your software is ready for update! However it's a BETA VERSION. Proceed with caution.");
                    Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[OnJoin] Current version %old%, latest version %new%".replace("%old%", currentVersion)
                            .replace("%new%", latestVersion));
                } else {
                    needUpdateJoin = true;
                    MessageUtils.updateIsHere();
                    Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Your OnJoin plugin is outdated! Download it to keep with latest changes and fixes.");
                    Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Disable this option in config.yml if you wish.");
                    Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "Current version: " + ChatColor.RED + currentVersion + ChatColor.YELLOW + " Latest version: " + ChatColor.GREEN + latestVersion);
                }
            }
        } catch(Exception ignored) {
        }
    }

    private void register() {
        new JoinCommand(this);
        new JoinQuitListener(this);
        new JoinFirework(this);
        new JoinExecuteCommand(this);
    }

    private void connectMySQL() {
        MySQL.username = getConfig().getString("MySQL.Username");
        MySQL.password = getConfig().getString("MySQL.Password");
        MySQL.host = getConfig().getString("MySQL.Host");
        MySQL.port = getConfig().getString("MySQL.Port");
        MySQL.database = getConfig().getString("MySQL.Database");
        MySQL.connect();
        MySQL.createTable();
        mySQLEnabled = true;
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
}
