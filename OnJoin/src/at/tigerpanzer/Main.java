package at.tigerpanzer;

import at.tigerpanzer.command.JoinCommand;
import at.tigerpanzer.events.JoinExecuteCommand;
import at.tigerpanzer.events.JoinFirework;
import at.tigerpanzer.events.JoinQuitListener;
import at.tigerpanzer.util.SpigotPluginUpdater;
import at.tigerpanzer.util.Utils;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin implements Listener {

    private static Main instance;
    public boolean needUpdateJoin;
    public boolean placeholderAPI;

    public static Main getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        Main.getInstance().needUpdateJoin = false;
        saveDefaultConfig();
        Bukkit.getConsoleSender().sendMessage(Utils.color(getConfig().getString("Console.PrefixConsole") + " &cWird &aGESTARTET &7| &cis &aSTARTED"));
        register();
        update();
        Bukkit.getConsoleSender().sendMessage(Utils.color(getConfig().getString("Console.PrefixConsole") + " &7=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-="));
        Bukkit.getConsoleSender().sendMessage(Utils.color(getConfig().getString("Console.PrefixConsole") + " &cPlugin version: &e" + getDescription().getVersion()));
        Bukkit.getConsoleSender().sendMessage(Utils.color(getConfig().getString("Console.PrefixConsole") + " &cPlugin author: &e" + getDescription().getAuthors()));
        Bukkit.getConsoleSender().sendMessage(Utils.color(getConfig().getString("Console.PrefixConsole") + " &cPlugin status: &aaktiviert &c| &aenabled"));
        if(Main.getInstance().getConfig().getString("PlaceholderAPI").contains("true")) {
            if(Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
                Bukkit.getConsoleSender().sendMessage(Utils.color(getConfig().getString("Console.PrefixConsole") + " §a✔ §ePlaceholderAPI §7| §aVersion§7:§e " + PlaceholderAPIPlugin.getInstance().getDescription().getVersion()));
                Main.getInstance().placeholderAPI = true;
            } else {
                Bukkit.getConsoleSender().sendMessage(Utils.color(getConfig().getString("Console.PrefixConsole") + " §c✖ §4PlaceholderAPI"));
                Main.getInstance().placeholderAPI = false;
            }
        }
        Bukkit.getConsoleSender().sendMessage(Utils.color(getConfig().getString("Console.PrefixConsole") + " &7=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-="));
    }

    @Override
    public void onDisable() {
        instance = null;
        Bukkit.getConsoleSender().sendMessage(Utils.color(getConfig().getString("Console.PrefixConsole") + " &7=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-="));
        Bukkit.getConsoleSender().sendMessage(Utils.color(getConfig().getString("Console.PrefixConsole") + " &cPlugin version: &e" + getDescription().getVersion()));
        Bukkit.getConsoleSender().sendMessage(Utils.color(getConfig().getString("Console.PrefixConsole") + " &cPlugin author: &e" + getDescription().getAuthors()));
        Bukkit.getConsoleSender().sendMessage(Utils.color(getConfig().getString("Console.PrefixConsole") + " &cPlugin status: &4deaktiviert &c| &4disabled"));
        Bukkit.getConsoleSender().sendMessage(Utils.color(getConfig().getString("Console.PrefixConsole") + " &7=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-="));
    }

    private void update() {
        SpigotPluginUpdater update = new SpigotPluginUpdater(this, "http://tigerpanzer02.bplaced.net/plugins/onjoin/");
        update.enableOut();
        update.needsUpdate();
    }

    private void register() {
        getCommand("onjoin").setExecutor(new JoinCommand());
        Bukkit.getPluginManager().registerEvents(new JoinQuitListener(), this);
        Bukkit.getPluginManager().registerEvents(new JoinFirework(), this);
        Bukkit.getPluginManager().registerEvents(new JoinExecuteCommand(), this);
    }
}
