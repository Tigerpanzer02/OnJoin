/*
 *  OnJoin - Your Server Join Plugin
 *          With this plugin, joins are
 *          unique on your server​
 *
 *  Maintained by Tigerpanzer_02
 */

package at.tigerpanzer.onjoin.events;

import at.tigerpanzer.onjoin.Main;
import at.tigerpanzer.onjoin.util.Storage;
import at.tigerpanzer.onjoin.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;

public class JoinExecuteCommand implements Listener {

    private Main plugin;

    public JoinExecuteCommand(Main plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onJoinExecuteCommand(final PlayerJoinEvent e) {
        Player p = e.getPlayer();
        List<String> commands;
        if(plugin.firstJoin() && Storage.getFirstJoin(p)) {
            if(!p.hasPermission("OnJoin.FirstJoin.ExecuteCommand")) {
                return;
            }
            if(!plugin.getConfig().getBoolean("FirstJoin.ExecuteCommand.CommandOn")) {
                return;
            }
            commands = plugin.getConfig().getStringList("FirstJoin.ExecuteCommand.Commands");
        } else {
            if(!p.hasPermission("OnJoin.ExecuteCommand")) {
                return;
            }
            if(!plugin.getConfig().getBoolean("ExecuteCommand.CommandOn")) {
                return;
            }
            commands = plugin.getConfig().getStringList("ExecuteCommand.Commands");
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
}