package at.tigerpanzer.util;

import at.tigerpanzer.Main;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;


public class Utils {

    public static String color(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public static String setPlaceholders(final Player p, String s) {
        if(Main.getInstance().placeholderAPI) {
            s = PlaceholderAPI.setPlaceholders(p, s);
        }
        s = s.replace("%prefix%", Main.getInstance().getConfig().getString("Prefix"));
        s = s.replace("%player%", p.getName());
        s = s.replace("%onjoin-player-displayname%", p.getDisplayName());
        s = s.replace("%onjoin-player-uuid%", p.getUniqueId().toString());
        s = s.replace("%onjoin-player-gamemode%", p.getGameMode().name());
        s = s.replace("%onjoin-world%", p.getWorld().getName());
        s = s.replace("%onjoin-player-health%", String.valueOf(p.getHealth()));
        s = s.replace("%onjoin-player-max-health%", String.valueOf(p.getMaxHealth()));
        s = s.replace("%onjoin-max-players%", String.valueOf(Bukkit.getServer().getMaxPlayers()));
        s = s.replace("%onjoin-online-players%", String.valueOf(Bukkit.getServer().getOnlinePlayers().size()));
        s = ChatColor.translateAlternateColorCodes('&', s);
        return s;
    }
}
