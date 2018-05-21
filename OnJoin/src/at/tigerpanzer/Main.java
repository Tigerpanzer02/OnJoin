package at.tigerpanzer;

import at.tigerpanzer.command.JoinCommand;
import at.tigerpanzer.events.JoinQuitListener;
import at.tigerpanzer.util.SpigotPluginUpdater;
import at.tigerpanzer.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin implements Listener {

    private static Main instance;

    public static Main getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        Bukkit.getConsoleSender().sendMessage(Utils.color(getConfig().getString("Console.PrefixConsole") + " &cWird &aGESTARTET &7| &cis &aSTARTED"));
        register();
        update();
        Bukkit.getConsoleSender().sendMessage(Utils.color(getConfig().getString("Console.PrefixConsole") + " &7=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-="));
        Bukkit.getConsoleSender().sendMessage(Utils.color(getConfig().getString("Console.PrefixConsole") + " &cPlugin version: &e" + getDescription().getVersion()));
        Bukkit.getConsoleSender().sendMessage(Utils.color(getConfig().getString("Console.PrefixConsole") + " &cPlugin author: &e" + getDescription().getAuthors()));
        Bukkit.getConsoleSender().sendMessage(Utils.color(getConfig().getString("Console.PrefixConsole") + " &cPlugin status: &aaktiviert &c| &aenabled"));
        Bukkit.getConsoleSender().sendMessage(Utils.color(getConfig().getString("Console.PrefixConsole") + " &7=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-="));
    }

    @Override
    public void onDisable() {
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
    }
}
