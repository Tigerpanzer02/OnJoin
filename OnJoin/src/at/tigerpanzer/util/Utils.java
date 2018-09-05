package at.tigerpanzer.util;

import at.tigerpanzer.Main;
import me.clip.placeholderapi.PlaceholderAPI;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;


public class Utils {

    public static String color(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public static String setPlaceholders(final Player p, String str) {
        String formatted = str;
        if(Main.getInstance().isPlaceholderAPIEnabled()) {
            formatted = PlaceholderAPI.setPlaceholders(p, str);
        }
        formatted = StringUtils.replace(formatted, "%prefix%", Main.getInstance().getConfig().getString("Prefix"));
        formatted = StringUtils.replace(formatted, "%player%", p.getName());
        formatted = StringUtils.replace(formatted, "%onjoin-player-displayname%", p.getDisplayName());
        formatted = StringUtils.replace(formatted, "%onjoin-player-uuid%", p.getUniqueId().toString());
        formatted = StringUtils.replace(formatted, "%onjoin-player-gamemode%", p.getGameMode().name());
        formatted = StringUtils.replace(formatted, "%onjoin-world%", p.getWorld().getName());
        formatted = StringUtils.replace(formatted, "%onjoin-player-health%", String.valueOf(p.getHealth()));
        formatted = StringUtils.replace(formatted, "%onjoin-player-max-health%", String.valueOf(p.getMaxHealth()));
        formatted = StringUtils.replace(formatted, "%onjoin-max-players%", String.valueOf(Bukkit.getServer().getMaxPlayers()));
        formatted = StringUtils.replace(formatted, "%onjoin-online-players%", String.valueOf(Bukkit.getServer().getOnlinePlayers().size()));
        formatted = ChatColor.translateAlternateColorCodes('&', formatted);
        return formatted;
    }
}
