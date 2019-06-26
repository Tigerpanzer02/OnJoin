/*
 *  OnJoin - Your Server Join Plugin
 *          With this plugin, joins are
 *          unique on your server​
 *
 *  Maintained by Tigerpanzer_02
 */

package at.tigerpanzer.onjoin.events;

import at.tigerpanzer.onjoin.Main;
import at.tigerpanzer.onjoin.handlers.LanguageManager;
import at.tigerpanzer.onjoin.util.MessageUtils;
import at.tigerpanzer.onjoin.util.Storage;
import at.tigerpanzer.onjoin.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.configuration.ConfigurationSection;
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

    private int fireworkamount;
    private List<String> lore;
    private List<String> lore2;
    private int fireworkhight;
    private boolean fireworkflicker;
    private boolean fireworktrail;
    private String fireworkftype;
    private boolean fireworkinstantexplode;
    private int fireworkpower;
    private boolean fireworkenable;

    @EventHandler
    public void onJoinFirework(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        getfireworkvalues(p);
        if(!fireworkenable) {
            return;
        }
        for(int i = 1; i < fireworkamount; i++) {
            Utils.debugmessage("Execute Firework " + p.getName());
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
            Utils.debugmessage("Firework launched on " + p.getName());
        }
    }

    private void getfireworkvalues(Player player) {
        try {
            ConfigurationSection section = plugin.getConfig().getConfigurationSection("Firework");
            for(String key : section.getKeys(false)) {
                if(!key.equals("default")) {
                    if(key.equals("firstjoin")) {
                        if(plugin.firstJoin() && Storage.getFirstJoin(player)) {
                            fireworkenable = plugin.getConfig().getBoolean("Firework." + key + ".Enabled");
                            fireworkamount = plugin.getConfig().getInt("Firework." + key + ".Amount");
                            lore = plugin.getConfig().getStringList("Firework." + key + ".Colors");
                            lore2 = plugin.getConfig().getStringList("Firework." + key + ".Fade");
                            fireworkhight = plugin.getConfig().getInt("Firework." + key + ".Firework-Height");
                            fireworkflicker = plugin.getConfig().getBoolean("Firework." + key + ".Flicker");
                            fireworktrail = plugin.getConfig().getBoolean("Firework." + key + ".Trail");
                            fireworkftype = plugin.getConfig().getString("Firework." + key + ".Type");
                            fireworkinstantexplode = plugin.getConfig().getBoolean("Firework." + key + ".InstantExplode");
                            fireworkpower = plugin.getConfig().getInt("Firework." + key + ".Power");
                            return;
                        }
                    } else if(player.hasPermission(LanguageManager.getLanguageMessage("Firework." + key + ".Permission"))) {
                        fireworkamount = plugin.getConfig().getInt("Firework." + key + ".Amount");
                        lore = plugin.getConfig().getStringList("Firework." + key + ".Colors");
                        lore2 = plugin.getConfig().getStringList("Firework." + key + ".Fade");
                        fireworkhight = plugin.getConfig().getInt("Firework." + key + ".Firework-Height");
                        fireworkflicker = plugin.getConfig().getBoolean("Firework." + key + ".Flicker");
                        fireworktrail = plugin.getConfig().getBoolean("Firework." + key + ".Trail");
                        fireworkftype = plugin.getConfig().getString("Firework." + key + ".Type");
                        fireworkinstantexplode = plugin.getConfig().getBoolean("Firework." + key + ".InstantExplode");
                        fireworkpower = plugin.getConfig().getInt("Firework." + key + ".Power");
                        fireworkenable = plugin.getConfig().getBoolean("Firework." + key + ".Enabled", true);
                        return;
                    }
                }
            }
            fireworkenable = plugin.getConfig().getBoolean("Firework." + "default" + ".Enabled");
            fireworkamount = plugin.getConfig().getInt("Firework." + "default" + ".Amount");
            lore = plugin.getConfig().getStringList("Firework." + "default" + ".Colors");
            lore2 = plugin.getConfig().getStringList("Firework." + "default" + ".Fade");
            fireworkhight = plugin.getConfig().getInt("Firework." + "default" + ".Firework-Height");
            fireworkflicker = plugin.getConfig().getBoolean("Firework." + "default" + ".Flicker");
            fireworktrail = plugin.getConfig().getBoolean("Firework." + "default" + ".Trail");
            fireworkftype = plugin.getConfig().getString("Firework." + "default" + ".Type");
            fireworkinstantexplode = plugin.getConfig().getBoolean("Firework." + "default" + ".InstantExplode");
            fireworkpower = plugin.getConfig().getInt("Firework." + "default" + ".Power");
        } catch(Exception ex) {
            MessageUtils.errorOccurred();
            Bukkit.getConsoleSender().sendMessage(Utils.color(plugin.consolePrefix + " &7=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-="));
            Bukkit.getConsoleSender().sendMessage(Utils.color(plugin.consolePrefix + " &7Error in the fireworkvalues"));
            Bukkit.getConsoleSender().sendMessage(Utils.color(plugin.consolePrefix + " &7=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-="));
            ex.printStackTrace();
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