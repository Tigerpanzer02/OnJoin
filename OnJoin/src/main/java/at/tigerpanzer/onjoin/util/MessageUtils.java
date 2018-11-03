
package at.tigerpanzer.onjoin.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

/**
 * @author Plajer
 * <p>
 * Created at 11.02.2018
 */
public class MessageUtils {

  public static void errorOccurred() {
    Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "  _____                                                                                  _   _ ");
    Bukkit.getConsoleSender().sendMessage(ChatColor.RED + " | ____|  _ __   _ __    ___    _ __      ___     ___    ___   _   _   _ __    ___    __| | | |");
    Bukkit.getConsoleSender().sendMessage(ChatColor.RED + " |  _|   | '__| | '__|  / _ \\  | '__|    / _ \\   / __|  / __| | | | | | '__|  / _ \\  / _` | | |");
    Bukkit.getConsoleSender().sendMessage(ChatColor.RED + " | |___  | |    | |    | (_) | | |      | (_) | | (__  | (__  | |_| | | |    |  __/ | (_| | |_|");
    Bukkit.getConsoleSender().sendMessage(ChatColor.RED + " |_____| |_|    |_|     \\___/  |_|       \\___/   \\___|  \\___|  \\__,_| |_|     \\___|  \\__,_| (_)");
    Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "                                                                                               ");
  }

  public static void updateIsHere() {
    Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "  _   _               _           _          ");
    Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + " | | | |  _ __     __| |   __ _  | |_    ___ ");
    Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + " | | | | | '_ \\   / _` |  / _` | | __|  / _ \\");
    Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + " | |_| | | |_) | | (_| | | (_| | | |_  |  __/");
    Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "  \\___/  | .__/   \\__,_|  \\__,_|  \\__|  \\___|");
    Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "         |_|                                 ");
  }

  public static void gonnaMigrate() {
    Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "  __  __   _                          _     _                    ");
    Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + " |  \\/  | (_)   __ _   _ __    __ _  | |_  (_)  _ __     __ _             ");
    Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + " | |\\/| | | |  / _` | | '__|  / _` | | __| | | | '_ \\   / _` |            ");
    Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + " | |  | | | | | (_| | | |    | (_| | | |_  | | | | | | | (_| |  _   _   _ ");
    Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + " |_|  |_| |_|  \\__, | |_|     \\__,_|  \\__| |_| |_| |_|  \\__, | (_) (_) (_)");
    Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "               |___/                                    |___/             ");
  }

  public static void info() {
    Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "  _____        __         _ ");
    Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + " |_   _|      / _|       | |");
    Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "   | |  _ __ | |_ ___    | |");
    Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "   | | | '_ \\|  _/ _ \\ | |");
    Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "  _| |_| | | | || (_) |  |_|");
    Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + " |_____|_| |_|_| \\___/  (_)");
  }

}
