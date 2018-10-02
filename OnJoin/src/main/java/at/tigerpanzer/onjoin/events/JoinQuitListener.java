package at.tigerpanzer.onjoin.events;

import at.tigerpanzer.onjoin.Main;
import at.tigerpanzer.onjoin.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;

public class JoinQuitListener implements Listener {
    
    private Main plugin;

    public JoinQuitListener(Main plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if(plugin.getConfig().getBoolean("Join.ChatClearOn")) {
            for(int i = 0; i < 200; i++) {
                p.sendMessage(" ");
            }
        }
        if(plugin.needUpdateJoin()) {
            if((p.hasPermission("OnJoin.UpdateMessage")) || (p.hasPermission("OnJoin.*"))) {
                if(plugin.getConfig().getBoolean("Join.UpdateMessageOn")) {
                    List<String> UpdateMessageText = plugin.getConfig().getStringList("Join.UpdateMessageText");
                    for(String msg : UpdateMessageText) {
                        p.sendMessage(Utils.setPlaceholders(p, msg));
                    }
                }
            }
        }
        if(plugin.getConfig().getBoolean("SpawnLocation.SpawnLocationEnable")) {
            final Location SpawnLocation = new Location(Bukkit.getServer().getWorld(plugin.getConfig().getString("SpawnLocation.World")), plugin.getConfig().getDouble("SpawnLocation.XCoord"), plugin.getConfig().getDouble("SpawnLocation.YCoord"), plugin.getConfig().getDouble("SpawnLocation.ZCoord"), (float) plugin.getConfig().getInt("SpawnLocation.Yaw"), (float) plugin.getConfig().getInt("SpawnLocation.Pitch"));
            p.teleport(SpawnLocation);
        }
        if((p.hasPermission("OnJoin.Heal")) || (p.hasPermission("OnJoin.*"))) {
            if(plugin.getConfig().getBoolean("Heal.HealOnWithPermission")) {
                p.setHealth(plugin.getConfig().getInt("Heal.HealthWithPermission"));
                p.setFoodLevel(plugin.getConfig().getInt("Heal.FoodLevelWithPermission"));
                if(plugin.getConfig().getBoolean("Heal.ClearPotionEffectsWithPermission")) {
                    p.getActivePotionEffects().clear();
                }
            }
        } else if(plugin.getConfig().getString("Heal.HealOn").contains("true")) {
            p.setHealth(plugin.getConfig().getInt("Heal.Health"));
            p.setFoodLevel(plugin.getConfig().getInt("Heal.FoodLevel"));
            if(plugin.getConfig().getBoolean("Heal.ClearPotionEffects")) {
                p.getActivePotionEffects().clear();
            }
        }
        if(plugin.getConfig().getBoolean("Join.JoinSoundOn")) {
            p.playSound(p.getLocation(), Sound.valueOf(plugin.getConfig().getString("Join.JoinSound")), 3, 1);
        }
        if(plugin.getConfig().getBoolean("Join.JoinMessageOn")) {
            e.setJoinMessage(Utils.setPlaceholders(p, plugin.getConfig().getString("Join.JoinMessage")));
        } else if(!plugin.getConfig().getBoolean("Join.JoinMessageOn")) {
            e.setJoinMessage("");
        }
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            if(plugin.getConfig().getBoolean("Title.TitleOnJoin")) {
                Utils.sendTitle(p, Utils.setPlaceholders(p, plugin.getConfig().getString("Title.Title1")), 25, 90, 0);
                Utils.sendSubTitle(p, Utils.setPlaceholders(p, plugin.getConfig().getString("Title.SubTitle1")), 25, 90, 0);

                if(plugin.getConfig().getBoolean("actionbar.actionbaronjoin")) {
                    Utils.sendActionBar(p, Utils.setPlaceholders(p, plugin.getConfig().getString("actionbar.actionbar1")));
                }
                if(plugin.getConfig().getBoolean("WelcomeMessage.WelcomeMessageOn")) {
                    List<String> WelcomeMessageText = plugin.getConfig().getStringList("WelcomeMessage.WelcomeMessageText");
                    for(String msg : WelcomeMessageText) {
                        p.sendMessage(Utils.setPlaceholders(e.getPlayer(), msg));
                    }
                }
            }
        }, 2L);

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            if(plugin.getConfig().getBoolean("Title.TitleOnJoin")) {
                Utils.sendSubTitle(p, Utils.setPlaceholders(p, plugin.getConfig().getString("Title.SubTitle2")), 0, 90, 0);
            }
            if(plugin.getConfig().getBoolean("actionbar.actionbaronjoin")) {
                Utils.sendActionBar(p, Utils.setPlaceholders(p, plugin.getConfig().getString("actionbar.actionbar2")));

            }
        }, 65L);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if(plugin.getConfig().getBoolean("Quit.QuitSoundOn")) {
            p.playSound(p.getLocation(), Sound.valueOf(plugin.getConfig().getString("Quit.QuitSound")), 3, 1);
        }
        if(plugin.getConfig().getBoolean("Quit.QuitMessageOn")) {
            e.setQuitMessage(Utils.setPlaceholders(p, plugin.getConfig().getString("Quit.QuitMessage")));
        } else if(!plugin.getConfig().getBoolean("Quit.QuitMessageOn")) {
            e.setQuitMessage("");
        }
    }
}
