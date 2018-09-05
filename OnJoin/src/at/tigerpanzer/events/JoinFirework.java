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

    public JoinFirework(Main plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if(p.hasPermission("OnJoin.Firework") || p.hasPermission("OnJoin.*")) {
            if(Main.getInstance().getConfig().getBoolean("Join.Firework-On")) {
                for(int i = 1; i < Main.getInstance().getConfig().getInt("Join.Firework.Amount"); i++) {
                    List<Color> colors = new ArrayList<>();
                    List<Color> fade = new ArrayList<>();
                    List<String> lore = Main.getInstance().getConfig().getStringList("Join.Firework.Colors");
                    List<String> lore2 = Main.getInstance().getConfig().getStringList("Join.Firework.Fade");
                    for(String l : lore) {
                        colors.add(getColor(l));
                    }
                    for(String l : lore2) {
                        fade.add(getColor(l));
                    }
                    final Firework f = e.getPlayer().getWorld().spawn(
                            p.getLocation().add(0.5D, Main.getInstance().getConfig().getInt("Join.Firework.Firework-Height"), 0.5D),
                            Firework.class);

                    FireworkMeta fm = f.getFireworkMeta();
                    fm.addEffect(FireworkEffect.builder().flicker(Main.getInstance().getConfig().getBoolean("Join.Firework.Flicker")).trail(Main.getInstance().getConfig().getBoolean("Join.Firework.Trail")).with(FireworkEffect.Type.valueOf(Main.getInstance().getConfig().getString("Join.Firework.Type"))).withColor(colors).withFade(fade).build());
                    if(!Main.getInstance().getConfig().getBoolean("Join.Firework.Instant-Explode")) {
                        fm.setPower(Main.getInstance().getConfig().getInt("Join.Firework.Power"));
                    }
                    f.setFireworkMeta(fm);
                    if(Main.getInstance().getConfig().getBoolean("Join.Firework.Instant-Explode")) {
                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), f::detonate, 1L);
                    }
                }
            }
        }
    }

    private Color getColor(String color) {
        switch(color.toUpperCase()) {
            case "AQUA":
                return Color.AQUA;
            case "BLACK":
                return Color.BLACK;
            case "BLUE":
                return Color.BLUE;
            case "FUCHSIA":
                return Color.FUCHSIA;
            case "GRAY":
                return Color.GRAY;
            case "GREEN":
                return Color.GREEN;
            case "LIME":
                return Color.LIME;
            case "MAROON":
                return Color.MAROON;
            case "NAVY":
                return Color.NAVY;
            case "OLIVE":
                return Color.OLIVE;
            case "ORANGE":
                return Color.ORANGE;
            case "PURPLE":
                return Color.PURPLE;
            case "RED":
                return Color.RED;
            case "SILVER":
                return Color.SILVER;
            case "TEAL":
                return Color.TEAL;
            case "WHITE":
                return Color.WHITE;
            case "YELLOW":
                return Color.YELLOW;
            default:
                return Color.BLACK;
        }
    }
}