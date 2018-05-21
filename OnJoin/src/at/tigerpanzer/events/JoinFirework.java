package at.tigerpanzer.events;

import at.tigerpanzer.Main;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.ArrayList;
import java.util.List;

public class JoinFirework implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (p.hasPermission("OnJoin.firework") || (p.hasPermission("OnJoin.*"))) {
            for (int i = 1; i < Main.getInstance().getConfig().getInt("Firework.amount"); i++) {
                List<Color> colors = new ArrayList<>();
                List<Color> fade = new ArrayList<>();
                List<String> lore = Main.getInstance().getConfig().getStringList("Firework.colors");
                List<String> lore2 = Main.getInstance().getConfig().getStringList("Firework.fade");
                for (String l : lore) {
                    colors.add(getColor(l));
                }
                for (String l : lore2) {
                    fade.add(getColor(l));
                }
                final Firework f = e.getPlayer().getWorld().spawn(
                        p.getLocation().add(0.5D, Main.getInstance().getConfig().getInt("Firework.height"), 0.5D),
                        Firework.class);

                FireworkMeta fm = f.getFireworkMeta();
                fm.addEffect(FireworkEffect.builder().flicker(Main.getInstance().getConfig().getBoolean("Firework.flicker"))
                        .trail(Main.getInstance().getConfig().getBoolean("Firework.trail"))
                        .with(FireworkEffect.Type.valueOf(Main.getInstance().getConfig().getString("Firework.type"))).withColor(colors).withFade(fade)
                        .build());
                if (!Main.getInstance().getConfig().getBoolean("Firework.instant-explode")) {
                    fm.setPower(Main.getInstance().getConfig().getInt("Firework.power"));
                }
                f.setFireworkMeta(fm);
                if (Main.getInstance().getConfig().getBoolean("Firework.instant-explode")) {
                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), f::detonate, 1L);
                }
            }
        }
    }

    public Color getColor(String paramString) {
        String temp = paramString;
        if (temp.equalsIgnoreCase("AQUA")) {
            return Color.AQUA;
        }
        if (temp.equalsIgnoreCase("BLACK")) {
            return Color.BLACK;
        }
        if (temp.equalsIgnoreCase("BLUE")) {
            return Color.BLUE;
        }
        if (temp.equalsIgnoreCase("FUCHSIA")) {
            return Color.FUCHSIA;
        }
        if (temp.equalsIgnoreCase("GRAY")) {
            return Color.GRAY;
        }
        if (temp.equalsIgnoreCase("GREEN")) {
            return Color.GREEN;
        }
        if (temp.equalsIgnoreCase("LIME")) {
            return Color.LIME;
        }
        if (temp.equalsIgnoreCase("MAROON")) {
            return Color.MAROON;
        }
        if (temp.equalsIgnoreCase("NAVY")) {
            return Color.NAVY;
        }
        if (temp.equalsIgnoreCase("OLIVE")) {
            return Color.OLIVE;
        }
        if (temp.equalsIgnoreCase("ORANGE")) {
            return Color.ORANGE;
        }
        if (temp.equalsIgnoreCase("PURPLE")) {
            return Color.PURPLE;
        }
        if (temp.equalsIgnoreCase("RED")) {
            return Color.RED;
        }
        if (temp.equalsIgnoreCase("SILVER")) {
            return Color.SILVER;
        }
        if (temp.equalsIgnoreCase("TEAL")) {
            return Color.TEAL;
        }
        if (temp.equalsIgnoreCase("WHITE")) {
            return Color.WHITE;
        }
        if (temp.equalsIgnoreCase("YELLOW")) {
            return Color.YELLOW;
        }
        return null;
    }
}

