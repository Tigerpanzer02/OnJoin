package at.tigerpanzer.events;

import at.tigerpanzer.Main;
import at.tigerpanzer.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;

public class JoinExecuteCommand implements Listener {

    @EventHandler
    public void onJoin(final PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if((p.hasPermission("OnJoin.ExecuteCommand")) || (p.hasPermission("OnJoin.*"))) {
            if(Main.getInstance().getConfig().getString("ExecuteCommand.CommandOn").contains("true")) {
                final List<String> commands = Main.getInstance().getConfig().getStringList("ExecuteCommand.Commands");
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
    }
}