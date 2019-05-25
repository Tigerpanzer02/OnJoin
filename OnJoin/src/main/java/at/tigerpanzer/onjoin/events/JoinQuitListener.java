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
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;

public class JoinQuitListener implements Listener {

    private Main plugin;
    private boolean spawnlocationenable;
    private String spawnlocationworld;
    private double spawnlocationx;
    private double spawnlocationy;
    private double spawnlocationz;
    private int spawnlocationyaw;
    private int spawnlocationpitch;
    private String joinsound;
    private Integer joinvolume;
    private Integer joinpitch;
    private boolean joinsoundon;
    private boolean joinmessageon;
    private String joinmessage;
    private boolean chatclearon;
    private boolean welcomemessageon;

    public JoinQuitListener(Main plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        Storage.DataOnJoin(p);
        boolean titleonjoin;
        String title1;
        String subtitle1;
        String subtitle2;
        boolean actionbaronjoin;
        String actionbar1;
        String actionbar2;
        List<String> WelcomeMessageText;
        WelcomeMessageText = welcomeMessage(p);
        healthvalues(p);
        spawnvalues(p);
        joinsoundvalues(p);
        messagesvalues(p, true);
        if(plugin.firstJoin() && Storage.getFirstJoin(p)) {
            Utils.debugmessage("Loading First join for " + p.getName());
            titleonjoin = plugin.getConfig().getBoolean("FirstJoin.Title.TitleOnJoin");
            title1 = Utils.colorMessage("FirstJoin.Title.Title1");
            subtitle1 = Utils.colorMessage("FirstJoin.Title.SubTitle1");
            subtitle2 = Utils.colorMessage("FirstJoin.Title.SubTitle2");
            actionbaronjoin = plugin.getConfig().getBoolean("FirstJoin.Actionbar.ActionbarOnJoin");
            actionbar1 = Utils.colorMessage("FirstJoin.Actionbar.Actionbar1");
            actionbar2 = Utils.colorMessage("FirstJoin.Actionbar.Actionbar2");
        } else {
            Utils.debugmessage("Loading normal join for " + p.getName());
            titleonjoin = plugin.getConfig().getBoolean("Title.TitleOnJoin");
            title1 = Utils.colorMessage("Title.Title1");
            subtitle1 = Utils.colorMessage("Title.SubTitle1");
            subtitle2 = Utils.colorMessage("Title.SubTitle2");
            actionbaronjoin = plugin.getConfig().getBoolean("Actionbar.ActionbarOnJoin");
            actionbar1 = Utils.colorMessage("Actionbar.Actionbar1");
            actionbar2 = Utils.colorMessage("Actionbar.Actionbar2");
        }
        if(chatclearon) {
            for(int i = 0; i < 200; i++) {
                p.sendMessage(" ");
            }
        }
        if(spawnlocationenable) {
            final Location SpawnLocation = new Location(Bukkit.getServer().getWorld(spawnlocationworld), spawnlocationx, spawnlocationy, spawnlocationz, (float) spawnlocationyaw, (float) spawnlocationpitch);
            Utils.debugmessage("Teleporting " + p.getName() + " to " + SpawnLocation);
            p.teleport(SpawnLocation);
        }
        if(joinsoundon) {
            p.playSound(p.getLocation(), Sound.valueOf(joinsound), joinvolume, joinpitch);
            Utils.debugmessage("Send sound to " + p.getName());
        }
        if(joinmessageon) {
            e.setJoinMessage(Utils.setPlaceholders(p, joinmessage));
            Utils.debugmessage("Join messaged send to all for " + p.getName());
        } else {
            e.setJoinMessage("");
        }
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            if(titleonjoin) {
                Utils.sendTitle(p, Utils.setPlaceholders(p, title1), 25, 90, 0);
                Utils.sendSubTitle(p, Utils.setPlaceholders(p, subtitle1), 25, 90, 0);
                Utils.debugmessage("Title join for " + p.getName());
                if(actionbaronjoin) {
                    Utils.sendActionBar(p, Utils.setPlaceholders(p, actionbar1));
                    Utils.debugmessage("Actionbar for " + p.getName());
                }
                if(welcomemessageon) {
                    Utils.debugmessage("Welcome message for " + p.getName());
                    for(String msg : WelcomeMessageText) {
                        p.sendMessage(Utils.setPlaceholders(e.getPlayer(), msg));
                    }
                }
            }
        }, 2L);

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            if(titleonjoin) {
                Utils.debugmessage("Subtitle 2 for " + p.getName());
                Utils.sendSubTitle(p, Utils.setPlaceholders(p, subtitle2), 0, 90, 0);
            }
            if(actionbaronjoin) {
                Utils.debugmessage("Actionbar 2 for " + p.getName());
                Utils.sendActionBar(p, Utils.setPlaceholders(p, actionbar2));

            }
        }, 65L);
        if(plugin.needUpdateJoin()) {
            Utils.debugmessage("Plugin needs update ");
            if(plugin.getConfig().getBoolean("Join.UpdateMessageOn")) {
                if((p.hasPermission("OnJoin.UpdateMessage"))) {
                    List<String> UpdateMessageText = LanguageManager.getLanguageList("Messages.UpdateMessageText");
                    for(String msg : UpdateMessageText) {
                        p.sendMessage(Utils.setPlaceholders(p, msg));
                    }
                }
            }
        }
        if(plugin.oldversion) {
            p.sendMessage(Utils.color("&e--------- &7[&4UPGRADE &7| &eOnJoin&7]&e --------- \n" + "&eThis update offers new customisations. Please check your configs!!!\n" + "&cWe detected that you have used a version before 2.1.0! Please have a look on the &dUpdateChanges" + "\n&e--------- &7[&4UPGRADE &7| &eOnJoin&7]&e ---------"));
        }
    }

    private boolean quitsoundon;
    private String quitsound;
    private Integer quitvolume;
    private Integer quitpitch;
    private boolean quitmessageon;
    private String quitmessage;

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        quitsoundvalues(p);
        messagesvalues(p, false);
        if(plugin.firstJoin() && Storage.getFirstJoin(p)) {
            Utils.debugmessage("Loading FirstQuit for " + p.getName());
            Storage.setFirstJoin(p, false);
        } else {
            Utils.debugmessage("Loading NormalQuit for " + p.getName());
        }
        if(plugin.mySQLEnabled()) {
            Utils.debugmessage("Saved data to mysql for " + p.getName());
            Storage.DataOnQuit(p);
        }
        if(quitsoundon) {
            p.playSound(p.getLocation(), Sound.valueOf(quitsound), quitvolume, quitpitch);
            Utils.debugmessage("Send Sound to " + p.getName());
        }
        if(quitmessageon) {
            Utils.debugmessage("Send quitmessage to " + p.getName());
            e.setQuitMessage(Utils.setPlaceholders(p, quitmessage));
        } else {
            e.setQuitMessage("");
        }
    }

    private List<String> welcomeMessage(Player player) {
        try {
            ConfigurationSection section = LanguageManager.getLanguageSection("WelcomeMessage");
            for(String key : section.getKeys(false)) {
                if(!key.equals("default")) {
                    Utils.debugmessage("Key: " + key);
                    if(key.equals("firstjoin")) {
                        if(plugin.firstJoin() && Storage.getFirstJoin(player)) {
                            Utils.debugmessage("Send Firstjoin welcome");
                            chatclearon = LanguageManager.getLanguageBoolean("WelcomeMessage" + key + "ChatClear");
                            chatclearon = LanguageManager.getLanguageBoolean("WelcomeMessage" + key + "Enabled");
                            return LanguageManager.getLanguageList("WelcomeMessage.firstjoin.Text");
                        }
                        Utils.debugmessage("FirstJoin is disabled or the player joined has already joined");
                    } else if(player.hasPermission(LanguageManager.getLanguageMessage("WelcomeMessage." + key + ".Permission"))) {
                        Utils.debugmessage("Player permission " + LanguageManager.getLanguageMessage("WelcomeMessage." + key + ".Permission") + " = " + player.hasPermission(LanguageManager.getLanguageMessage("WelcomeMessage." + key + ".Permission")));
                        chatclearon = LanguageManager.getLanguageBoolean("WelcomeMessage" + key + "ChatClear");
                        chatclearon = true;
                        return LanguageManager.getLanguageList("WelcomeMessage." + key + ".Text");
                    }
                }
            }
            Utils.debugmessage("Send default welcome");
            chatclearon = LanguageManager.getLanguageBoolean("WelcomeMessage" + "default" + "ChatClear");
            chatclearon = LanguageManager.getLanguageBoolean("WelcomeMessage" + "default" + "Enabled");
            return LanguageManager.getLanguageList("WelcomeMessage.default.Text");
        } catch(Exception ex) {
            MessageUtils.errorOccurred();
            Bukkit.getConsoleSender().sendMessage(Utils.color(plugin.consolePrefix + " &7=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-="));
            Bukkit.getConsoleSender().sendMessage(Utils.color(plugin.consolePrefix + " &7Error in the welcomemessagevalues"));
            Bukkit.getConsoleSender().sendMessage(Utils.color(plugin.consolePrefix + " &7=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-="));
            ex.printStackTrace();
        }
        Utils.debugmessage("Send default welcome");
        return LanguageManager.getLanguageList("WelcomeMessage.default.Text");
    }

    private void healthvalues(Player player) {
        try {
            ConfigurationSection section = plugin.getConfig().getConfigurationSection("Heal");
            for(String key : section.getKeys(false)) {
                if(!key.equals("default")) {
                    if(key.equals("firstjoin")) {
                        if(plugin.firstJoin() && Storage.getFirstJoin(player)) {
                            player.setHealth(plugin.getConfig().getInt("Heal." + key + ".Health"));
                            player.setFoodLevel(plugin.getConfig().getInt("Heal." + key + ".FoodLevel"));
                            if(plugin.getConfig().getBoolean("Heal." + key + ".ClearPotionEffects")) {
                                player.getActivePotionEffects().clear();
                            }
                            break;
                        }
                    } else if(player.hasPermission(plugin.getConfig().getString("Heal." + key + ".Permission"))) {
                        player.setHealth(plugin.getConfig().getInt("Heal." + key + ".Health"));
                        player.setFoodLevel(plugin.getConfig().getInt("Heal." + key + ".FoodLevel"));
                        if(plugin.getConfig().getBoolean("Heal." + key + ".ClearPotionEffects")) {
                            player.getActivePotionEffects().clear();
                        }
                        break;
                    }
                }
            }
            if(plugin.getConfig().getBoolean("Heal.default.Enabled")) {
                player.setHealth(plugin.getConfig().getInt("Heal." + "default" + ".Health"));
                player.setFoodLevel(plugin.getConfig().getInt("Heal." + "default" + ".FoodLevel"));
                if(plugin.getConfig().getBoolean("Heal." + "default" + ".ClearPotionEffects")) {
                    player.getActivePotionEffects().clear();
                }
            }
        } catch(Exception ex) {
            MessageUtils.errorOccurred();
            Bukkit.getConsoleSender().sendMessage(Utils.color(plugin.consolePrefix + " &7=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-="));
            Bukkit.getConsoleSender().sendMessage(Utils.color(plugin.consolePrefix + " &7Error in the healvalues"));
            Bukkit.getConsoleSender().sendMessage(Utils.color(plugin.consolePrefix + " &7=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-="));
            ex.printStackTrace();
        }

    }


    private void spawnvalues(Player player) {
        try {
            ConfigurationSection section = plugin.getConfig().getConfigurationSection("SpawnLocation");
            for(String key : section.getKeys(false)) {
                if(!key.equals("default")) {
                    if(key.equals("firstjoin")) {
                        if(plugin.firstJoin() && Storage.getFirstJoin(player)) {
                            spawnlocationenable = plugin.getConfig().getBoolean("SpawnLocation." + key + ".Enabled");
                            spawnlocationworld = plugin.getConfig().getString("SpawnLocation." + key + ".World");
                            spawnlocationx = plugin.getConfig().getDouble("SpawnLocation." + key + ".XCoord");
                            spawnlocationy = plugin.getConfig().getDouble("SpawnLocation." + key + ".YCoord");
                            spawnlocationz = plugin.getConfig().getDouble("SpawnLocation." + key + ".ZCoord");
                            spawnlocationyaw = plugin.getConfig().getInt("SpawnLocation." + key + ".Yaw");
                            spawnlocationpitch = plugin.getConfig().getInt("SpawnLocation." + key + ".Pitch");
                            break;
                        }
                    } else if(player.hasPermission(plugin.getConfig().getString("SpawnLocation." + key + ".Permission"))) {
                        spawnlocationworld = plugin.getConfig().getString("SpawnLocation." + key + ".World");
                        spawnlocationx = plugin.getConfig().getDouble("SpawnLocation." + key + ".XCoord");
                        spawnlocationy = plugin.getConfig().getDouble("SpawnLocation." + key + ".YCoord");
                        spawnlocationz = plugin.getConfig().getDouble("SpawnLocation." + key + ".ZCoord");
                        spawnlocationyaw = plugin.getConfig().getInt("SpawnLocation." + key + ".Yaw");
                        spawnlocationpitch = plugin.getConfig().getInt("SpawnLocation." + key + ".Pitch");
                        spawnlocationenable = true;
                        break;
                    }
                }
            }
            spawnlocationenable = plugin.getConfig().getBoolean("SpawnLocation." + "default" + ".Enabled");
            spawnlocationworld = plugin.getConfig().getString("SpawnLocation." + "default" + ".World");
            spawnlocationx = plugin.getConfig().getDouble("SpawnLocation." + "default" + ".XCoord");
            spawnlocationy = plugin.getConfig().getDouble("SpawnLocation." + "default" + ".YCoord");
            spawnlocationz = plugin.getConfig().getDouble("SpawnLocation." + "default" + ".ZCoord");
            spawnlocationyaw = plugin.getConfig().getInt("SpawnLocation." + "default" + ".Yaw");
            spawnlocationpitch = plugin.getConfig().getInt("SpawnLocation." + "default" + ".Pitch");

        } catch(Exception ex) {
            MessageUtils.errorOccurred();
            Bukkit.getConsoleSender().sendMessage(Utils.color(plugin.consolePrefix + " &7=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-="));
            Bukkit.getConsoleSender().sendMessage(Utils.color(plugin.consolePrefix + " &7Error in the spawnvalues"));
            Bukkit.getConsoleSender().sendMessage(Utils.color(plugin.consolePrefix + " &7=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-="));
            ex.printStackTrace();
        }
    }

    private void joinsoundvalues(Player player) {
        try {
            ConfigurationSection section = plugin.getConfig().getConfigurationSection("Sounds.Join");
            for(String key : section.getKeys(false)) {
                if(!key.equals("default")) {
                    if(key.equals("firstjoin")) {
                        if(plugin.firstJoin() && Storage.getFirstJoin(player)) {
                            joinsoundon = plugin.getConfig().getBoolean("Sounds.Join." + key + ".Enabled");
                            joinsound = plugin.getConfig().getString("Sounds.Join." + key + ".Sound");
                            joinvolume = plugin.getConfig().getInt("Sounds.Join." + key + ".Volume");
                            joinpitch = plugin.getConfig().getInt("Sounds.Join." + key + ".Pitch");
                            break;
                        }
                    } else if(player.hasPermission(plugin.getConfig().getString("Sounds.Join." + key + ".Permission"))) {
                        joinsound = plugin.getConfig().getString("Sounds.Join." + key + ".Sound");
                        joinvolume = plugin.getConfig().getInt("Sounds.Join." + key + ".Volume");
                        joinpitch = plugin.getConfig().getInt("Sounds.Join." + key + ".Pitch");
                        joinsoundon = true;
                        break;
                    }
                }
            }
            joinsoundon = plugin.getConfig().getBoolean("Sounds.Join." + "default" + ".Enabled");
            joinsound = plugin.getConfig().getString("Sounds.Join." + "default" + ".Sound");
            joinvolume = plugin.getConfig().getInt("Sounds.Join." + "default" + ".Volume");
            joinpitch = plugin.getConfig().getInt("Sounds.Join." + "default" + ".Pitch");

        } catch(Exception ex) {
            MessageUtils.errorOccurred();
            Bukkit.getConsoleSender().sendMessage(Utils.color(plugin.consolePrefix + " &7=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-="));
            Bukkit.getConsoleSender().sendMessage(Utils.color(plugin.consolePrefix + " &7Error in the soundvalues"));
            Bukkit.getConsoleSender().sendMessage(Utils.color(plugin.consolePrefix + " &7=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-="));
            ex.printStackTrace();
        }
    }

    private void quitsoundvalues(Player player) {
        try {
            ConfigurationSection section = plugin.getConfig().getConfigurationSection("Sounds.Quit");
            for(String key : section.getKeys(false)) {
                if(!key.equals("default")) {
                    if(key.equals("firstjoin")) {
                        if(plugin.firstJoin() && Storage.getFirstJoin(player)) {
                            quitsoundon = plugin.getConfig().getBoolean("Sounds.Quit." + key + ".Enabled");
                            quitsound = plugin.getConfig().getString("Sounds.Quit." + key + ".Sound");
                            quitvolume = plugin.getConfig().getInt("Sounds.Quit." + key + ".Volume");
                            quitpitch = plugin.getConfig().getInt("Sounds.Quit." + key + ".Pitch");
                            break;
                        }
                    } else if(player.hasPermission(plugin.getConfig().getString("Sounds.Quit." + key + ".Permission"))) {
                        quitsound = plugin.getConfig().getString("Sounds.Quit." + key + ".Sound");
                        quitvolume = plugin.getConfig().getInt("Sounds.Quit." + key + ".Volume");
                        quitpitch = plugin.getConfig().getInt("Sounds.Quit." + key + ".Pitch");
                        quitsoundon = true;
                        break;
                    }
                }
            }
            quitsoundon = plugin.getConfig().getBoolean("Sounds.Quit." + "default" + ".Enabled");
            quitsound = plugin.getConfig().getString("Sounds.Quit." + "default" + ".Sound");
            quitvolume = plugin.getConfig().getInt("Sounds.Quit." + "default" + ".Volume");
            quitpitch = plugin.getConfig().getInt("Sounds.Quit." + "default" + ".Pitch");

        } catch(Exception ex) {
            MessageUtils.errorOccurred();
            Bukkit.getConsoleSender().sendMessage(Utils.color(plugin.consolePrefix + " &7=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-="));
            Bukkit.getConsoleSender().sendMessage(Utils.color(plugin.consolePrefix + " &7Error in the soundvalues"));
            Bukkit.getConsoleSender().sendMessage(Utils.color(plugin.consolePrefix + " &7=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-="));
            ex.printStackTrace();
        }
    }

    private void messagesvalues(Player player, boolean join) {
        try {
            if(join) {
                ConfigurationSection section = LanguageManager.getLanguageSection("Messages.Join");
                String firstpath = "Messages.Join.";
                for(String key : section.getKeys(false)) {
                    if(!key.equals("default")) {
                        if(key.equals("firstjoin")) {
                            if(plugin.firstJoin() && Storage.getFirstJoin(player)) {
                                joinmessageon = LanguageManager.getLanguageBoolean(firstpath + key + ".Enabled");
                                joinmessage = Utils.colorMessage(firstpath + key + ".Message");
                                break;
                            }
                        } else if(player.hasPermission(LanguageManager.getLanguageMessage(firstpath + key + ".Permission"))) {
                            joinmessage = Utils.colorMessage(firstpath + key + ".Message");
                            joinmessageon = true;
                            break;
                        }
                    }
                }
                joinmessageon = LanguageManager.getLanguageBoolean(firstpath + "default" + ".Enabled");
                joinmessage = Utils.colorMessage(firstpath + "default" + ".Message");
            } else {
                ConfigurationSection section = LanguageManager.getLanguageSection("Messages.Quit");
                String firstpath = "Messages.Quit.";
                for(String key : section.getKeys(false)) {
                    if(!key.equals("default")) {
                        if(key.equals("firstjoin")) {
                            if(plugin.firstJoin() && Storage.getFirstJoin(player)) {
                                quitmessageon = LanguageManager.getLanguageBoolean(firstpath + key + ".Enabled");
                                quitmessage = Utils.colorMessage(firstpath + key + ".Message");
                                break;
                            }
                        } else if(player.hasPermission(LanguageManager.getLanguageMessage(firstpath + key + ".Permission"))) {
                            quitmessage = Utils.colorMessage(firstpath + key + ".Message");
                            quitmessageon = true;
                            break;
                        }
                    }
                }
                quitmessageon = LanguageManager.getLanguageBoolean(firstpath + "default" + ".Enabled");
                quitmessage = Utils.colorMessage(firstpath + "default" + ".Message");
            }
        } catch(Exception ex) {
            MessageUtils.errorOccurred();
            Bukkit.getConsoleSender().sendMessage(Utils.color(plugin.consolePrefix + " &7=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-="));
            Bukkit.getConsoleSender().sendMessage(Utils.color(plugin.consolePrefix + " &7Error in the messages"));
            Bukkit.getConsoleSender().sendMessage(Utils.color(plugin.consolePrefix + " &7=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-="));
            ex.printStackTrace();
        }
    }
}
