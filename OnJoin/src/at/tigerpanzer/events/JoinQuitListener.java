package at.tigerpanzer.events;

import at.tigerpanzer.Main;
import at.tigerpanzer.util.ActionbarUtils;
import at.tigerpanzer.util.TitleUtils;
import at.tigerpanzer.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Sound;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.meta.FireworkMeta;

public class JoinQuitListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (Main.getInstance().getConfig().getString("Join.ChatClearOn").contains("true")) {
            for (int i = 0; i < 200; i++) {
                p.sendMessage(" ");
            }
        }
        if (Main.getInstance().getConfig().getString("Join.JoinSoundOn").contains("true")) {
            p.playSound(p.getLocation(), Sound.valueOf(Main.getInstance().getConfig().getString("Join.JoinSound")), 3, 1);
        }
        if (Main.getInstance().getConfig().getString("Join.JoinMessageOn").contains("true")) {
            e.setJoinMessage(Utils.color(Main.getInstance().getConfig().getString("Join.JoinMessage").replaceAll("%player%", p.getDisplayName())));
        } else if (Main.getInstance().getConfig().getString("Join.JoinMessageOn").contains("false")) {
            e.setJoinMessage("");
        }
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            if (Main.getInstance().getConfig().getString("Title.TitleOnJoin").contains("true")) {
                TitleUtils.sendTitle(p, Utils.color(Main.getInstance().getConfig().getString("Title.Title1").replaceAll("%player%", p.getDisplayName())), 25, 90, 0);
                TitleUtils.sendSubTitle(p, Utils.color(Main.getInstance().getConfig().getString("Title.SubTitle1").replaceAll("%player%", p.getDisplayName())), 25, 90, 0);

                if (Main.getInstance().getConfig().getString("actionbar.actionbaronjoin").contains("true")) {
                    ActionbarUtils.sendActionBar(p, Utils.color(Main.getInstance().getConfig().getString("actionbar.actionbar1").replaceAll("%player%", p.getDisplayName())));
                }
                if (Main.getInstance().getConfig().getString("WelcomeMessage.WelcomeMessageOn").contains("true")) {
                    Player p1 = e.getPlayer();
                    p1.sendMessage(Utils.color(Main.getInstance().getConfig().getString("Prefix") + Main.getInstance().getConfig().getString("WelcomeMessage.WelcomeMessageHeader").replaceAll("%player%", p1.getDisplayName())));
                    p1.sendMessage(Utils.color(Main.getInstance().getConfig().getString("Prefix") + Main.getInstance().getConfig().getString("WelcomeMessage.WelcomeMessageLine1").replaceAll("%player%", p1.getDisplayName())));
                    p1.sendMessage(Utils.color(Main.getInstance().getConfig().getString("Prefix") + Main.getInstance().getConfig().getString("WelcomeMessage.WelcomeMessageLine2").replaceAll("%player%", p1.getDisplayName())));
                    p1.sendMessage(Utils.color(Main.getInstance().getConfig().getString("Prefix") + Main.getInstance().getConfig().getString("WelcomeMessage.WelcomeMessageLine3").replaceAll("%player%", p1.getDisplayName())));
                    p1.sendMessage(Utils.color(Main.getInstance().getConfig().getString("Prefix") + Main.getInstance().getConfig().getString("WelcomeMessage.WelcomeMessageLine4").replaceAll("%player%", p1.getDisplayName())));
                    p1.sendMessage(Utils.color(Main.getInstance().getConfig().getString("Prefix") + Main.getInstance().getConfig().getString("WelcomeMessage.WelcomeMessageFooder").replaceAll("%player%", p1.getDisplayName())));
                }
            }
        }, 2L);

        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            if (Main.getInstance().getConfig().getString("Title.TitleOnJoin").contains("true")) {
                TitleUtils.sendSubTitle(p, Utils.color(Main.getInstance().getConfig().getString("Title.SubTitle2").replaceAll("%player%", p.getDisplayName())), 0, 90, 0);
            }
            if (Main.getInstance().getConfig().getString("actionbar.actionbaronjoin").contains("true")) {
                ActionbarUtils.sendActionBar(p, Utils.color(Main.getInstance().getConfig().getString("actionbar.actionbar2").replaceAll("%player%", p.getDisplayName())));

            }
        }, 65L);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if (Main.getInstance().getConfig().getString("Quit.QuitSoundOn").contains("true")) {
            p.playSound(p.getLocation(), Sound.valueOf(Main.getInstance().getConfig().getString("Quit.QuitSound")), 3, 1);
        }
        if (Main.getInstance().getConfig().getString("Quit.QuitMessageOn").contains("true")) {
            e.setQuitMessage(Utils.color(Main.getInstance().getConfig().getString("Quit.QuitMessage").replaceAll("%player%", p.getDisplayName())));
        } else if (Main.getInstance().getConfig().getString("Quit.QuitMessageOn").contains("false")) {
            e.setQuitMessage("");
        }
    }
}
