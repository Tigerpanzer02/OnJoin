package at.tigerpanzer.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Utils {
    public static void broadcast(String msg) {
        for(Player all : Bukkit.getOnlinePlayers()) {
            all.sendMessage(msg);
        }
    }
}
