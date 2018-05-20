package at.tigerpanzer.events;

import at.tigerpanzer.Main;
import at.tigerpanzer.util.FileManager;
import at.tigerpanzer.util.ActionbarUtils;
import at.tigerpanzer.util.TitleUtils;
import at.tigerpanzer.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinQuitListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if(FileManager.getString("Join.JoinSoundOn").contains("true")) {
            p.playSound(p.getLocation(), Sound.valueOf(FileManager.getString("Join.JoinSound")), 3, 1);
        }
        if(FileManager.getString("Join.JoinMessageOn").contains("true")) {
            e.setJoinMessage(Utils.color(FileManager.getString("Join.JoinMessage").replaceAll("%player%", p.getDisplayName())));
        } else if(FileManager.getString("Join.JoinMessageOn").contains("false")) {
            e.setJoinMessage("");
        }
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            if(FileManager.getString("Title.TitleOnJoin").contains("true")) {
                TitleUtils.sendTitle(p, FileManager.getString("Title.Title1").replaceAll("%player%", p.getDisplayName()), 25, 90, 0);
                TitleUtils.sendSubTitle(p, FileManager.getString("Title.SubTitle1").replaceAll("%player%", p.getDisplayName()), 25, 90, 0);

                if(FileManager.getString("actionbar.actionbaronjoin").contains("true")) {
                    ActionbarUtils.sendActionBar(p, Utils.color(FileManager.getString("actionbar.actionbar1").replaceAll("%player%", p.getDisplayName())));
                }
                if(FileManager.getString("WelcomeMessage.WelcomeMessageOn").contains("true")) {
                    Player p1 = e.getPlayer();
                    p1.sendMessage(Utils.color(FileManager.getString("Prefix") + FileManager.getString("WelcomeMessage.WelcomeMessageHeader").replaceAll("%player%", p1.getDisplayName())));
                    p1.sendMessage(Utils.color(FileManager.getString("Prefix") + FileManager.getString("WelcomeMessage.WelcomeMessageLine1").replaceAll("%player%", p1.getDisplayName())));
                    p1.sendMessage(Utils.color(FileManager.getString("Prefix") + FileManager.getString("WelcomeMessage.WelcomeMessageLine2").replaceAll("%player%", p1.getDisplayName())));
                    p1.sendMessage(Utils.color(FileManager.getString("Prefix") + FileManager.getString("WelcomeMessage.WelcomeMessageLine3").replaceAll("%player%", p1.getDisplayName())));
                    p1.sendMessage(Utils.color(FileManager.getString("Prefix") + FileManager.getString("WelcomeMessage.WelcomeMessageLine4").replaceAll("%player%", p1.getDisplayName())));
                    p1.sendMessage(Utils.color(FileManager.getString("Prefix") + FileManager.getString("WelcomeMessage.WelcomeMessageFooder").replaceAll("%player%", p1.getDisplayName())));
                }
            }
        }, 2L);

        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            if(FileManager.getString("Title.TitleOnJoin").contains("true")) {
                TitleUtils.sendSubTitle(p, FileManager.getString("Title.SubTitle2").replaceAll("%player%", p.getDisplayName()), 0, 90, 0);
            }
            if(FileManager.getString("actionbar.actionbaronjoin").contains("true")) {
                ActionbarUtils.sendActionBar(p, Utils.color(FileManager.getString("actionbar.actionbar2").replaceAll("%player%", p.getDisplayName())));

            }
        }, 65L);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if(FileManager.getString("Quit.QuitSoundOn").contains("true")) {
            p.playSound(p.getLocation(), Sound.valueOf(FileManager.getString("Quit.QuitSound")), 3, 1);
        }
        if(FileManager.getString("Quit.QuitMessageOn").contains("true")) {
            e.setQuitMessage(Utils.color(FileManager.getString("Quit.QuitMessage").replaceAll("%player%", p.getDisplayName())));
        } else if(FileManager.getString("Quit.QuitMessageOn").contains("false")) {
            e.setQuitMessage("");
        }
    }
}
