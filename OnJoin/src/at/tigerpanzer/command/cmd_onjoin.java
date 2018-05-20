package at.tigerpanzer.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import at.tigerpanzer.util.FileManager;

public class cmd_onjoin implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){

			    if ((sender instanceof Player))
			    {
			      Player player = (Player)sender;
			      if (cmd.getName().equalsIgnoreCase("onjoin")) {
			        if ((player.hasPermission("OnJoin.config")) || (player.hasPermission("OnJoin.*")))
			        {
			          if (args.length == 0)
			          {
						player.sendMessage(FileManager.getString("Prefix").replaceAll("&", "�") + "!!Derzeit bewirkt dieser Command noch nichts!!");
			          }
			          if (args[1].equalsIgnoreCase("cupdate"))
			          {
						player.sendMessage(FileManager.getString("Prefix").replaceAll("&", "�") + "!!Du hast deine Config geupdatet!!!");
			          }
			          else
			          {
			            player.sendMessage(FileManager.getString("Prefix").replaceAll("&", "�") + "�7Bitte benutze �e/onjoin!");
			          }
			        }
			        else {
			          player.sendMessage(FileManager.getString("Prefix").replaceAll("&", "�") + (FileManager.getString("Permissionfail").replaceAll("&", "�")));
			        }
			      }
			    }
			    return false;
			  }
			}
