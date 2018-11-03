package at.tigerpanzer.onjoin.events;

import at.tigerpanzer.onjoin.Main;
import at.tigerpanzer.onjoin.handlers.LanguageManager;
import at.tigerpanzer.onjoin.util.Storage;
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
        boolean chatclearon;
        boolean spawnlocationenable;
        String spawnlocationworld;
        double spawnlocationx;
        double spawnlocationy;
        double spawnlocationz;
        int spawnlocationyaw;
        int spawnlocationpitch;
        boolean joinsoundon;
        String joinsound;
        boolean joinmessageon;
        String joinmessage;
        boolean titleonjoin;
        String title1;
        String subtitle1;
        String subtitle2;
        boolean actionbaronjoin;
        String actionbar1;
        String actionbar2;
        boolean welcomemessageon;
        List<String> WelcomeMessageText;
        if(plugin.firstJoin() && Storage.getFirstJoin(p)) {
            chatclearon = plugin.getConfig().getBoolean("FirstJoin.Join.ChatClearOn");
            spawnlocationenable = plugin.getConfig().getBoolean("FirstJoin.SpawnLocation.SpawnLocationEnable");
            spawnlocationworld = plugin.getConfig().getString("FirstJoin.SpawnLocation.World");
            spawnlocationx = plugin.getConfig().getDouble("FirstJoin.SpawnLocation.XCoord");
            spawnlocationy = plugin.getConfig().getDouble("FirstJoin.SpawnLocation.YCoord");
            spawnlocationz = plugin.getConfig().getDouble("FirstJoin.SpawnLocation.ZCoord");
            spawnlocationyaw = plugin.getConfig().getInt("FirstJoin.SpawnLocation.Yaw");
            spawnlocationpitch = plugin.getConfig().getInt("FirstJoin.SpawnLocation.Pitch");
            if((p.hasPermission("FirstJoin.OnJoin.Heal")) || (p.hasPermission("OnJoin.*"))) {
                if(plugin.getConfig().getBoolean("FirstJoin.Heal.HealOnWithPermission")) {
                    p.setHealth(plugin.getConfig().getInt("FirstJoin.Heal.HealthWithPermission"));
                    p.setFoodLevel(plugin.getConfig().getInt("FirstJoin.Heal.FoodLevelWithPermission"));
                    if(plugin.getConfig().getBoolean("FirstJoin.Heal.ClearPotionEffectsWithPermission")) {
                        p.getActivePotionEffects().clear();
                    }
                }
            } else if(plugin.getConfig().getString("FirstJoin.Heal.HealOn").contains("true")) {
                p.setHealth(plugin.getConfig().getInt("FirstJoin.Heal.Health"));
                p.setFoodLevel(plugin.getConfig().getInt("FirstJoin.Heal.FoodLevel"));
                if(plugin.getConfig().getBoolean("FirstJoin.Heal.ClearPotionEffects")) {
                    p.getActivePotionEffects().clear();
                }
            }
            joinsoundon = plugin.getConfig().getBoolean("FirstJoin.Join.JoinSoundOn");
            joinsound = plugin.getConfig().getString("FirstJoin.Join.JoinSound");
            joinmessageon = plugin.getConfig().getBoolean("FirstJoin.Join.JoinMessageOn");
            joinmessage = Utils.colorMessage("FirstJoin.Join.JoinMessage");
            titleonjoin = plugin.getConfig().getBoolean("FirstJoin.Title.TitleOnJoin");
            title1 = Utils.colorMessage("FirstJoin.Title.Title1");
            subtitle1 = Utils.colorMessage("FirstJoin.Title.SubTitle1");
            subtitle2 = Utils.colorMessage("FirstJoin.Title.SubTitle2");
            actionbaronjoin = plugin.getConfig().getBoolean("FirstJoin.actionbar.actionbaronjoin");
            actionbar1 = Utils.colorMessage("FirstJoin.actionbar.actionbar1");
            actionbar2 = Utils.colorMessage("FirstJoin.actionbar.actionbar2");
            welcomemessageon = plugin.getConfig().getBoolean("FirstJoin.WelcomeMessage.WelcomeMessageOn");
            WelcomeMessageText = LanguageManager.getLanguageList("FirstJoin.WelcomeMessage.WelcomeMessageText");
        } else {
            chatclearon = plugin.getConfig().getBoolean("Join.ChatClearOn");
            spawnlocationenable = plugin.getConfig().getBoolean("SpawnLocation.SpawnLocationEnable");
            spawnlocationworld = plugin.getConfig().getString("SpawnLocation.World");
            spawnlocationx = plugin.getConfig().getDouble("SpawnLocation.XCoord");
            spawnlocationy = plugin.getConfig().getDouble("SpawnLocation.YCoord");
            spawnlocationz = plugin.getConfig().getDouble("SpawnLocation.ZCoord");
            spawnlocationyaw = plugin.getConfig().getInt("SpawnLocation.Yaw");
            spawnlocationpitch = plugin.getConfig().getInt("SpawnLocation.Pitch");
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
            joinsoundon = plugin.getConfig().getBoolean("Join.JoinSoundOn");
            joinsound = plugin.getConfig().getString("Join.JoinSound");
            joinmessageon = plugin.getConfig().getBoolean("Join.JoinMessageOn");
            joinmessage = Utils.colorMessage("Join.JoinMessage");
            titleonjoin = plugin.getConfig().getBoolean("Title.TitleOnJoin");
            title1 = Utils.colorMessage("Title.Title1");
            subtitle1 = Utils.colorMessage("Title.SubTitle1");
            subtitle2 = Utils.colorMessage("Title.SubTitle2");
            actionbaronjoin = plugin.getConfig().getBoolean("actionbar.actionbaronjoin");
            actionbar1 = Utils.colorMessage("actionbar.actionbar1");
            actionbar2 = Utils.colorMessage("actionbar.actionbar2");
            welcomemessageon = plugin.getConfig().getBoolean("WelcomeMessage.WelcomeMessageOn");
            WelcomeMessageText = LanguageManager.getLanguageList("WelcomeMessage.WelcomeMessageText");
        }
        if(chatclearon) {
            for(int i = 0; i < 200; i++) {
                p.sendMessage(" ");
            }
        }
        if(spawnlocationenable) {
            final Location SpawnLocation = new Location(Bukkit.getServer().getWorld(spawnlocationworld), spawnlocationx, spawnlocationy, spawnlocationz, (float) spawnlocationyaw, (float) spawnlocationpitch);
            p.teleport(SpawnLocation);
        }
        if(joinsoundon) {
            p.playSound(p.getLocation(), Sound.valueOf(joinsound), 3, 1);
        }
        if(joinmessageon) {
            e.setJoinMessage(Utils.setPlaceholders(p, joinmessage));
        } else {
            e.setJoinMessage("");
        }
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            if(titleonjoin) {
                Utils.sendTitle(p, Utils.setPlaceholders(p, title1), 25, 90, 0);
                Utils.sendSubTitle(p, Utils.setPlaceholders(p, subtitle1), 25, 90, 0);

                if(actionbaronjoin) {
                    Utils.sendActionBar(p, Utils.setPlaceholders(p, actionbar1));
                }
                if(welcomemessageon) {
                    for(String msg : WelcomeMessageText) {
                        p.sendMessage(Utils.setPlaceholders(e.getPlayer(), msg));
                    }
                }
            }
        }, 2L);

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            if(titleonjoin) {
                Utils.sendSubTitle(p, Utils.setPlaceholders(p, subtitle2), 0, 90, 0);
            }
            if(actionbaronjoin) {
                Utils.sendActionBar(p, Utils.setPlaceholders(p, actionbar2));

            }
        }, 65L);
        if(plugin.needUpdateJoin()) {
            if((p.hasPermission("OnJoin.UpdateMessage")) || (p.hasPermission("OnJoin.*"))) {
                if(plugin.getConfig().getBoolean("Join.UpdateMessageOn")) {
                    List<String> UpdateMessageText = LanguageManager.getLanguageList("Join.UpdateMessageText");
                    for(String msg : UpdateMessageText) {
                        p.sendMessage(Utils.setPlaceholders(p, msg));
                    }
                }
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        boolean quitsoundon;
        String quitsound;
        boolean quitmessageon;
        String quitmessage;
        if(plugin.firstJoin() && Storage.getFirstJoin(p)) {
            quitsoundon = plugin.getConfig().getBoolean("FirstJoin.Quit.QuitSoundOn");
            quitsound = plugin.getConfig().getString("FirstJoin.Quit.QuitSound");
            quitmessageon = plugin.getConfig().getBoolean("FirstJoin.Quit.QuitMessageOn");
            quitmessage = Utils.colorMessage("FirstJoin.Quit.QuitMessage");
            Storage.setFirstJoin(p, false);
        } else {
            quitsoundon = plugin.getConfig().getBoolean("Quit.QuitSoundOn");
            quitsound = plugin.getConfig().getString("Quit.QuitSound");
            quitmessageon = plugin.getConfig().getBoolean("Quit.QuitMessageOn");
            quitmessage = Utils.colorMessage("Quit.QuitMessage");
        }
        if(quitsoundon) {
            p.playSound(p.getLocation(), Sound.valueOf(quitsound), 3, 1);
        }
        if(quitmessageon) {
            e.setQuitMessage(Utils.setPlaceholders(p, quitmessage));
        } else {
            e.setQuitMessage("");
        }
    }
}
