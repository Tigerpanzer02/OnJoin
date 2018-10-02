package at.tigerpanzer.onjoin.events;

import at.tigerpanzer.onjoin.Main;
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
    public void onJoin(final PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if(!p.hasPermission("OnJoin.ExecuteCommand") || !p.hasPermission("OnJoin.*")) {
            return;
        }
        if(!plugin.getConfig().getBoolean("ExecuteCommand.CommandOn")) {
            return;
        }
        final List<String> commands = plugin.getConfig().getStringList("ExecuteCommand.Commands");
        for(String command : commands) {
            String[] parts = command.split(";");
            String sender = parts[0];
            String cmd = Utils.setPlaceholders(p, parts[1]);
            if(sender.equalsIgnoreCase("console")) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
            } else {
                if(!sender.equalsIgnoreCase("player")) {
                    continue;
                }
                p.performCommand(cmd);
            }
        }
    }
}