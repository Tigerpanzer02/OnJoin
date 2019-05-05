package at.tigerpanzer.onjoin.events;

import at.tigerpanzer.onjoin.Main;
import at.tigerpanzer.onjoin.util.Storage;
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

    private Main plugin;

    public JoinFirework(Main plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onJoinFirework(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        int fireworkamount;
        List<String> lore;
        List<String> lore2;
        int fireworkhight;
        boolean fireworkflicker;
        boolean fireworktrail;
        String fireworkftype;
        boolean fireworkinstantexplode;
        int fireworkpower;
        if(plugin.firstJoin() && Storage.getFirstJoin(p)) {
            if(!p.hasPermission("OnJoin.FirstJoin.Firework")) {
                return;
            }
            if(!plugin.getConfig().getBoolean("FirstJoin.Join.FireworkOn")) {
                return;
            }
            fireworkamount = plugin.getConfig().getInt("FirstJoin.Join.Firework.Amount");
            lore = plugin.getConfig().getStringList("FirstJoin.Join.Firework.Colors");
            lore2 = plugin.getConfig().getStringList("FirstJoin.Join.Firework.Fade");
            fireworkhight = plugin.getConfig().getInt("FirstJoin.Join.Firework.Firework-Height");
            fireworkflicker = plugin.getConfig().getBoolean("FirstJoin.Join.Firework.Flicker");
            fireworktrail = plugin.getConfig().getBoolean("FirstJoin.Join.Firework.Trail");
            fireworkftype = plugin.getConfig().getString("FirstJoin.Join.Firework.Type");
            fireworkinstantexplode = plugin.getConfig().getBoolean("FirstJoin.Join.Firework.InstantExplode");
            fireworkpower = plugin.getConfig().getInt("FirstJoin.Join.Firework.Power");
        } else {
            if(!p.hasPermission("OnJoin.Firework")) {
               return;
            }
            if(!plugin.getConfig().getBoolean("Join.FireworkOn")) {
                return;
            }
            fireworkamount = plugin.getConfig().getInt("Join.Firework.Amount");
            lore = plugin.getConfig().getStringList("Join.Firework.Colors");
            lore2 = plugin.getConfig().getStringList("Join.Firework.Fade");
            fireworkhight = plugin.getConfig().getInt("Join.Firework.Firework-Height");
            fireworkflicker = plugin.getConfig().getBoolean("Join.Firework.Flicker");
            fireworktrail = plugin.getConfig().getBoolean("Join.Firework.Trail");
            fireworkftype = plugin.getConfig().getString("Join.Firework.Type");
            fireworkinstantexplode = plugin.getConfig().getBoolean("Join.Firework.InstantExplode");
            fireworkpower = plugin.getConfig().getInt("Join.Firework.Power");
        }
        for(int i = 1; i < fireworkamount; i++) {
            List<Color> colors = new ArrayList<>();
            List<Color> fade = new ArrayList<>();
            for(String l : lore) {
                colors.add(getColor(l));
            }
            for(String l : lore2) {
                fade.add(getColor(l));
            }
            final Firework f = e.getPlayer().getWorld().spawn(
                    p.getLocation().add(0.5D, fireworkhight, 0.5D),
                    Firework.class);
            FireworkMeta fm = f.getFireworkMeta();
            fm.addEffect(FireworkEffect.builder().flicker(fireworkflicker).trail(fireworktrail).with(FireworkEffect.Type.valueOf(fireworkftype)).withColor(colors).withFade(fade).build());
            if(!fireworkinstantexplode) {
                fm.setPower(fireworkpower);
            }
            f.setFireworkMeta(fm);
            if(fireworkinstantexplode) {
                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, f::detonate, 1L);
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