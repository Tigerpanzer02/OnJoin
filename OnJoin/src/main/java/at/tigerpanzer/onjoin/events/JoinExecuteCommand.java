/*
 *  OnJoin - Your Server Join Plugin
 *          With this plugin, joins are
 *          unique on your server​
 *
 *  Maintained by Tigerpanzer_02
 */

package at.tigerpanzer.onjoin.events;

import at.tigerpanzer.onjoin.Main;
import at.tigerpanzer.onjoin.handlers.LanguageManager;
import at.tigerpanzer.onjoin.util.MessageUtils;
import at.tigerpanzer.onjoin.util.Storage;
import at.tigerpanzer.onjoin.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;

public class JoinExecuteCommand implements Listener {

    private Main plugin;
    private List<String> commands;
    private boolean commandsenable;

    public JoinExecuteCommand(Main plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onJoinExecuteCommand(final PlayerJoinEvent e) {
        Player p = e.getPlayer();
        commandvalues(p);
        if(!commandsenable) {
            return;
        }
        Utils.debugmessage("Trying to execute commands" + commands);
        for(String command : commands) {
            String[] parts = command.split(";");
            String sender = parts[0];
            String cmd = Utils.setPlaceholders(p, parts[1]);
            if(sender.equalsIgnoreCase("console")) {
                Utils.debugmessage("Executed console command" + cmd);
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
            } else {
                if(!sender.equalsIgnoreCase("player")) {
                    continue;
                }
                Utils.debugmessage("Executed player (" + p.getName() + ") command" + cmd);
                p.performCommand(cmd);
            }
        }
    }

    private void commandvalues(Player player) {
        try {
            ConfigurationSection section = plugin.getConfig().getConfigurationSection("ExecuteCommand");
            for(String key : section.getKeys(false)) {
                if(!key.equals("default")) {
                    if(key.equals("firstjoin")) {
                        if(plugin.firstJoin() && Storage.getFirstJoin(player)) {
                            commandsenable = plugin.getConfig().getBoolean("ExecuteCommand." + key + ".Enabled");
                            commands = plugin.getConfig().getStringList("ExecuteCommand." + key + ".Commands");
                            return;
                        }
                    } else if(player.hasPermission(LanguageManager.getLanguageMessage("SpawnLocation." + key + ".Permission"))) {
                        commands = plugin.getConfig().getStringList("ExecuteCommand." + key + ".Commands");
                        commandsenable = plugin.getConfig().getBoolean("ExecuteCommand." + key + ".Enabled", true);
                        return;
                    }
                }
            }
            commandsenable = plugin.getConfig().getBoolean("ExecuteCommand." + "default" + ".Enabled");
            commands = plugin.getConfig().getStringList("ExecuteCommand." + "default" + ".Commands");
        }catch(Exception ex) {
            MessageUtils.errorOccurred();
            Bukkit.getConsoleSender().sendMessage(Utils.color(plugin.consolePrefix + " &7=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-="));
            Bukkit.getConsoleSender().sendMessage(Utils.color(plugin.consolePrefix + " &7Error in the commandvalues"));
            Bukkit.getConsoleSender().sendMessage(Utils.color(plugin.consolePrefix + " &7=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-="));
            ex.printStackTrace();
        }

    }

}