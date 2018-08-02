package at.tigerpanzer.util;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;


public class Utils {

    public static String color(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public static String setPlaceholders(final Player p, String s) {
        s = PlaceholderAPI.setPlaceholders(p, s);
        return s;
    }
}
