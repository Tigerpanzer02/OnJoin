/*
 *  OnJoin - Your Server Join Plugin
 *          With this plugin, joins are
 *          unique on your server​
 *
 *  Maintained by Tigerpanzer_02
 */

package at.tigerpanzer.onjoin.command;

import at.tigerpanzer.onjoin.Main;
import at.tigerpanzer.onjoin.handlers.LanguageManager;
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
            sender.sendMessage(Utils.colorMessage("NoPlayer"));
            return true;
        }
        Player player = (Player) sender;
        if(!player.hasPermission("OnJoin.config")) {
            player.sendMessage(Utils.setPlaceholders(player, Utils.colorMessage("Permissionfail")));
            return true;
        }
        if(args.length == 0) {
            List<String> HelpText = LanguageManager.getLanguageList("Help.HelpText");
            for(String msg : HelpText) {
                player.sendMessage(Utils.setPlaceholders(player, msg));
            }
            return true;
        } else if(args.length == 2) {
            if(args[0].equalsIgnoreCase("setspawn")) {
                player.sendMessage(Utils.setPlaceholders(player, Utils.colorMessage("SpawnLocation.Help")));
                plugin.getConfig().set("SpawnLocation." + args[1] + ".World", player.getLocation().getWorld().getName());
                plugin.getConfig().set("SpawnLocation." + args[1] + ".XCoord", player.getLocation().getX());
                plugin.getConfig().set("SpawnLocation." + args[1] + ".YCoord", player.getLocation().getY());
                plugin.getConfig().set("SpawnLocation." + args[1] + ".ZCoord", player.getLocation().getZ());
                plugin.getConfig().set("SpawnLocation." + args[1] + ".Yaw", player.getLocation().getYaw());
                plugin.getConfig().set("SpawnLocation." + args[1] + ".Pitch", player.getLocation().getPitch());
                plugin.getConfig().set("SpawnLocation." + args[1] + ".Enabled", true);
                player.sendMessage(Utils.colorMessage("SpawnLocation.SetSpawnMessageSetTo") + player.getLocation().getWorld().getName() + ", " + player.getLocation().getBlockX() + ", " + player.getLocation().getBlockY() + ", " + player.getLocation().getBlockZ());
                player.sendMessage(Utils.colorMessage("SpawnLocation.SetSpawnMessageYaw") + player.getLocation().getYaw());
                player.sendMessage(Utils.colorMessage("SpawnLocation.SetSpawnMessagePitch") + player.getLocation().getPitch());
                plugin.saveConfig();
            }
            if(args[0].equalsIgnoreCase("locale")) {
                if(args[1].equalsIgnoreCase("de")) {
                    plugin.getConfig().set("locale", "de");
                    plugin.saveConfig();
                    player.sendMessage(Utils.color(Utils.colorMessage("Prefix") + LanguageManager.getLanguageMessage("Help.LanguageSwitch")));
                } else if(args[1].equalsIgnoreCase("default")) {
                    plugin.getConfig().set("locale", "default");
                    plugin.saveConfig();
                    player.sendMessage(Utils.color(Utils.colorMessage("Prefix") + LanguageManager.getLanguageMessage("Help.LanguageSwitch")));
                }
            }
            return true;
        } else if(args.length == 1) {
            if(args[0].equalsIgnoreCase("reloadconfig")) {
                plugin.reloadConfig();
                player.sendMessage(Utils.setPlaceholders(player, Utils.colorMessage("Prefix") + Utils.colorMessage("Help.OutConfigLoad")));
            }
            return true;
        } else {
            player.sendMessage(Utils.setPlaceholders(player, Utils.colorMessage("Prefix") + "Use /onjoin"));
            return true;
        }
    }
}