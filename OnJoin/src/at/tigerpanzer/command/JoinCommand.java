package at.tigerpanzer.command;

import at.tigerpanzer.Main;
import at.tigerpanzer.util.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class JoinCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(cmd.getName().equalsIgnoreCase("onjoin")) {
                if((player.hasPermission("OnJoin.config")) || (player.hasPermission("OnJoin.*"))) {
                    if(args.length == 0) {
                        player.sendMessage(Utils.color(Main.getInstance().getConfig().getString("Prefix") + "- Maybe in next Update -"));

                        return true;
                    } else if(args.length == 2) {
                        if(args[0].equalsIgnoreCase("translate")) {
                            if(args[1].equalsIgnoreCase("de")) {
                                player.sendMessage(Utils.color(Main.getInstance().getConfig().getString("Prefix") + Main.getInstance().getConfig().getString("Console.outconfigcreate")));
                            } else if(args[1].equalsIgnoreCase("en")) {
                                player.sendMessage(Utils.color(Main.getInstance().getConfig().getString("Prefix") + Main.getInstance().getConfig().getString("Console.outconfigcreate")));

                            }
                        }
                    } else {
                        player.sendMessage(Utils.color(Main.getInstance().getConfig().getString("Prefix") + "Bitte benutze /onjoin"));
                        return true;
                    }
                } else {
                    player.sendMessage(Utils.color(Main.getInstance().getConfig().getString("Permissionfail")));
                    return true;
                }
            } else {
                sender.sendMessage(Utils.color("Nur Spieler können diesen Befehl nutzen!"));
                return true;
            }
        }
        return false;
    }
}