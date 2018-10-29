package at.tigerpanzer.onjoin.command;

import at.tigerpanzer.onjoin.Main;
import at.tigerpanzer.onjoin.util.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;


public class JoinCommand implements CommandExecutor {

    private Main plugin;

    public JoinCommand(Main plugin) {
        this.plugin = plugin;
        plugin.getCommand("onjoin").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(Utils.color(plugin.getConfig().getString("NoPlayer")));
            return true;
        }
        Player player = (Player) sender;
        if(!player.hasPermission("OnJoin.config") || !player.hasPermission("OnJoin.*")) {
            player.sendMessage(Utils.setPlaceholders(player, plugin.getConfig().getString("Permissionfail")));
            return true;
        }
        if(args.length == 0) {
            List<String> HelpText = plugin.getConfig().getStringList("Help.HelpText");
            for(String msg : HelpText) {
                player.sendMessage(Utils.setPlaceholders(player, msg));
            }
            return true;
        } else if(args.length == 2) {
            if(args[0].equalsIgnoreCase("translate")) {
                if(args[1].equalsIgnoreCase("de")) {
                    //player.sendMessage(Utils.color(plugin.getConfig().getString("Prefix") + plugin.getConfig().getString("Help.OutConfigCreate")));
                    player.sendMessage(Utils.color(plugin.getConfig().getString("Prefix") + "-- MAYBE NEXT UPDATE --"));
                } else if(args[1].equalsIgnoreCase("en")) {
                    //player.sendMessage(Utils.color(plugin.getConfig().getString("Prefix") + plugin.getConfig().getString("Help.OutConfigCreate")));
                    player.sendMessage(Utils.color(plugin.getConfig().getString("Prefix") + "-- MAYBE NEXT UPDATE --"));
                }
            }
            return true;
        } else if(args.length == 1) {
            if(args[0].equalsIgnoreCase("reloadconfig")) {
                plugin.reloadConfig();
                player.sendMessage(Utils.setPlaceholders(player, plugin.getConfig().getString("Prefix") + plugin.getConfig().getString("Help.OutConfigLoad")));
            }
            if(args[0].equalsIgnoreCase("setspawn")) {
                plugin.getConfig().set("SpawnLocation.World", player.getLocation().getWorld().getName());
                plugin.getConfig().set("SpawnLocation.XCoord", player.getLocation().getX());
                plugin.getConfig().set("SpawnLocation.YCoord", player.getLocation().getY());
                plugin.getConfig().set("SpawnLocation.ZCoord", player.getLocation().getZ());
                plugin.getConfig().set("SpawnLocation.Yaw", player.getLocation().getYaw());
                plugin.getConfig().set("SpawnLocation.Pitch", player.getLocation().getPitch());
                plugin.getConfig().set("SpawnLocation.SpawnLocationEnable", true);
                player.sendMessage(plugin.getConfig().getString("SpawnLocation.SetSpawnMessageSetTo") + player.getLocation().getWorld().getName() + ", " + player.getLocation().getBlockX() + ", " + player.getLocation().getBlockY() + ", " + player.getLocation().getBlockZ());
                player.sendMessage(plugin.getConfig().getString("SpawnLocation.SetSpawnMessageYaw") + player.getLocation().getYaw());
                player.sendMessage(plugin.getConfig().getString("SpawnLocation.SetSpawnMessagePitch") + player.getLocation().getPitch());
                plugin.saveConfig();
            }
            return true;
        } else {
            player.sendMessage(Utils.setPlaceholders(player, plugin.getConfig().getString("Prefix") + "Use /onjoin"));
            return true;
        }
    }
}