package at.tigerpanzer.events;

import at.tigerpanzer.Main;
import at.tigerpanzer.util.ActionbarUtils;
import at.tigerpanzer.util.TitleUtils;
import at.tigerpanzer.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;

public class JoinQuitListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if(Main.getInstance().getConfig().getString("Join.ChatClearOn").contains("true")) {
            for(int i = 0; i < 200; i++) {
                p.sendMessage(" ");
            }
        }
        if(Main.getInstance().NeedUpdateJoin) {
            if((p.hasPermission("OnJoin.UpdateMessage")) || (p.hasPermission("OnJoin.*"))) {
                if(Main.getInstance().getConfig().getString("Join.UpdateMessageOn").contains("true")) {
                    List<String> UpdateMessageText = Main.getInstance().getConfig().getStringList("Join.UpdateMessageText");
                    for(String msg : UpdateMessageText) {
                        p.sendMessage(Utils.setPlaceholders(p, msg));
                    }
                }
            }
        }
        if(Main.getInstance().getConfig().getString("Heal.HealOn").contains("true")) {
            p.setHealth(Main.getInstance().getConfig().getInt("Heal.Health"));
            p.setFoodLevel(Main.getInstance().getConfig().getInt("Heal.FoodLevel"));
            if(Main.getInstance().getConfig().getString("Heal.ClearPotionEffects").contains("true")) {
                p.getActivePotionEffects().clear();
            }
        }
        if(Main.getInstance().getConfig().getString("Heal.HealOnWithPermission").contains("true")) {
            if((p.hasPermission("OnJoin.Heal")) || (p.hasPermission("OnJoin.*"))) {
                p.setHealth(Main.getInstance().getConfig().getInt("Heal.HealthWithPermission"));
                p.setFoodLevel(Main.getInstance().getConfig().getInt("Heal.FoodLevelWithPermission"));
                if(Main.getInstance().getConfig().getString("Heal.ClearPotionEffectsWithPermission").contains("true")) {
                    p.getActivePotionEffects().clear();
                }
            }
        }
        if(Main.getInstance().getConfig().getString("Join.JoinSoundOn").contains("true")) {
            p.playSound(p.getLocation(), Sound.valueOf(Main.getInstance().getConfig().getString("Join.JoinSound")), 3, 1);
        }
        if(Main.getInstance().getConfig().getString("Join.JoinMessageOn").contains("true")) {
            e.setJoinMessage(Utils.setPlaceholders(p, Main.getInstance().getConfig().getString("Join.JoinMessage")));
        } else if(Main.getInstance().getConfig().getString("Join.JoinMessageOn").contains("false")) {
            e.setJoinMessage("");
        }
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            if(Main.getInstance().getConfig().getString("Title.TitleOnJoin").contains("true")) {
                TitleUtils.sendTitle(p, Utils.setPlaceholders(p, Main.getInstance().getConfig().getString("Title.Title1")), 25, 90, 0);
                TitleUtils.sendSubTitle(p, Utils.setPlaceholders(p, Main.getInstance().getConfig().getString("Title.SubTitle1")), 25, 90, 0);

                if(Main.getInstance().getConfig().getString("actionbar.actionbaronjoin").contains("true")) {
                    ActionbarUtils.sendActionBar(p, Utils.setPlaceholders(p, Main.getInstance().getConfig().getString("actionbar.actionbar1")));
                }
                if(Main.getInstance().getConfig().getString("WelcomeMessage.WelcomeMessageOn").contains("true")) {
                    List<String> WelcomeMessageText = Main.getInstance().getConfig().getStringList("WelcomeMessage.WelcomeMessageText");
                    for(String msg : WelcomeMessageText) {
                        p.sendMessage(Utils.setPlaceholders(e.getPlayer(), msg));
                    }
                }
            }
        }, 2L);

        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            if(Main.getInstance().getConfig().getString("Title.TitleOnJoin").contains("true")) {
                TitleUtils.sendSubTitle(p, Utils.setPlaceholders(p, Main.getInstance().getConfig().getString("Title.SubTitle2")), 0, 90, 0);
            }
            if(Main.getInstance().getConfig().getString("actionbar.actionbaronjoin").contains("true")) {
                ActionbarUtils.sendActionBar(p, Utils.setPlaceholders(p, Main.getInstance().getConfig().getString("actionbar.actionbar2")));

            }
        }, 65L);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if(Main.getInstance().getConfig().getString("Quit.QuitSoundOn").contains("true")) {
            p.playSound(p.getLocation(), Sound.valueOf(Main.getInstance().getConfig().getString("Quit.QuitSound")), 3, 1);
        }
        if(Main.getInstance().getConfig().getString("Quit.QuitMessageOn").contains("true")) {
            e.setQuitMessage(Utils.setPlaceholders(p, Main.getInstance().getConfig().getString("Quit.QuitMessage")));
        } else if(Main.getInstance().getConfig().getString("Quit.QuitMessageOn").contains("false")) {
            e.setQuitMessage("");
        }
    }
}
