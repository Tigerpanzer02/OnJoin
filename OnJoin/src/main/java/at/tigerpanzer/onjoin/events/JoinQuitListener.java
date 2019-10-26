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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private Map<Player, Object> titletask = new HashMap<>();
    private Map<Player, Object> actionbartask = new HashMap<>();

    public JoinQuitListener(Main plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        Storage.DataOnJoin(p);
        titletask.put(p, 0);
        actionbartask.put(p, 0);
        if(plugin.firstJoin() && Storage.getFirstJoin(p)) {
            Utils.debugmessage("Loading First join for " + p.getName());
        } else {
            Utils.debugmessage("Loading normal join for " + p.getName());
        }
        //load values
        List<String> WelcomeMessageText;
        WelcomeMessageText = welcomeMessage(p);
        healthvalues(p);
        spawnvalues(p);
        joinsoundvalues(p);
        messagesvalues(p, true);
        actionbarvalues(p);
        titlevalues(p);

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
        if(joinmessageon) {
            e.setJoinMessage(Utils.setPlaceholders(p, joinmessage));
            Utils.debugmessage("Join messaged send to all for " + p.getName());
        } else {
            e.setJoinMessage("");
        }
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            if(welcomemessageon) {
                Utils.debugmessage("Welcome message for " + p.getName());
                for(String msg : WelcomeMessageText) {
                    p.sendMessage(Utils.setPlaceholders(e.getPlayer(), msg));
                }
            }
        }, 2L);

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
            p.sendMessage(Utils.color("&e--------- &7[&4UPGRADE &7| &eOnJoin&7]&e --------- \n" + "&eThis update offers new customisations. Please check your configs!!!\n" + "&cWe detected that you have used a version before 2.3.0! Please have a look on the &dUpdateChanges (View it on SpigotMC)" + "\n&e--------- &7[&4UPGRADE &7| &eOnJoin&7]&e ---------"));
        }
        try {
            if(joinsoundon) {
                p.playSound(p.getLocation(), Sound.valueOf(joinsound), joinvolume, joinpitch);
                Utils.debugmessage("Send sound to " + p.getName());
            }
        } catch(Exception ex) {
            MessageUtils.errorOccurred();
            Bukkit.getConsoleSender().sendMessage(Utils.color(plugin.consolePrefix + " &7=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-="));
            Bukkit.getConsoleSender().sendMessage(Utils.color(plugin.consolePrefix + " &7Error in the sounds | Check your sounds for the server version that you using!"));
            Bukkit.getConsoleSender().sendMessage(Utils.color(plugin.consolePrefix + " &7=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-="));
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
        if(quitmessageon) {
            Utils.debugmessage("Send quitmessage to " + p.getName());
            e.setQuitMessage(Utils.setPlaceholders(p, quitmessage));
        } else {
            e.setQuitMessage("");
        }
        if(quitsoundon) {
            p.playSound(p.getLocation(), Sound.valueOf(quitsound), quitvolume, quitpitch);
            Utils.debugmessage("Send Sound to " + p.getName());
        }
    }

    private void getwelcomeMessagevalues(String key) {
        chatclearon = LanguageManager.getLanguageBoolean("WelcomeMessage" + key + "ChatClear");
        welcomemessageon = LanguageManager.getLanguageBoolean("WelcomeMessage" + key + "Enabled", false);
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
                            getwelcomeMessagevalues(key);
                            return LanguageManager.getLanguageList("WelcomeMessage.firstjoin.Text");
                        }
                        Utils.debugmessage("FirstJoin is disabled or the player joined has already joined");
                    } else if(player.hasPermission(LanguageManager.getLanguageMessage("WelcomeMessage." + key + ".Permission"))) {
                        Utils.debugmessage("Player permission " + LanguageManager.getLanguageMessage("WelcomeMessage." + key + ".Permission") + " = " + player.hasPermission(LanguageManager.getLanguageMessage("WelcomeMessage." + key + ".Permission")));
                        getwelcomeMessagevalues(key);
                        return LanguageManager.getLanguageList("WelcomeMessage." + key + ".Text");
                    }
                }
            }
            Utils.debugmessage("Send default welcome");
            getwelcomeMessagevalues("default");
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

    private void gethealthvalues(Player player, String key) {
        if(plugin.getConfig().getBoolean("Heal." + key + ".Enabled", false)) {
            player.setHealth(plugin.getConfig().getInt("Heal." + key + ".Health"));
            player.setFoodLevel(plugin.getConfig().getInt("Heal." + key + ".FoodLevel"));
            if(plugin.getConfig().getBoolean("Heal." + key + ".ClearPotionEffects")) {
                player.getActivePotionEffects().clear();
            }
        }
    }

    private void healthvalues(Player player) {
        try {
            ConfigurationSection section = plugin.getConfig().getConfigurationSection("Heal");
            for(String key : section.getKeys(false)) {
                if(!key.equals("default")) {
                    if(key.equals("firstjoin")) {
                        if(plugin.firstJoin() && Storage.getFirstJoin(player)) {
                            gethealthvalues(player, key);
                            return;
                        }
                    } else if(player.hasPermission(plugin.getConfig().getString("Heal." + key + ".Permission"))) {
                        gethealthvalues(player, key);
                        return;
                    }
                }
            }
            gethealthvalues(player, "default");
        } catch(Exception ex) {
            MessageUtils.errorOccurred();
            Bukkit.getConsoleSender().sendMessage(Utils.color(plugin.consolePrefix + " &7=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-="));
            Bukkit.getConsoleSender().sendMessage(Utils.color(plugin.consolePrefix + " &7Error in the healvalues"));
            Bukkit.getConsoleSender().sendMessage(Utils.color(plugin.consolePrefix + " &7=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-="));
            ex.printStackTrace();
        }

    }

    private void getspawnvalues(String key) {
        spawnlocationenable = plugin.getConfig().getBoolean("SpawnLocation." + key + ".Enabled", false);
        spawnlocationworld = plugin.getConfig().getString("SpawnLocation." + key + ".World");
        spawnlocationx = plugin.getConfig().getDouble("SpawnLocation." + key + ".XCoord");
        spawnlocationy = plugin.getConfig().getDouble("SpawnLocation." + key + ".YCoord");
        spawnlocationz = plugin.getConfig().getDouble("SpawnLocation." + key + ".ZCoord");
        spawnlocationyaw = plugin.getConfig().getInt("SpawnLocation." + key + ".Yaw");
        spawnlocationpitch = plugin.getConfig().getInt("SpawnLocation." + key + ".Pitch");
    }

    private void spawnvalues(Player player) {
        try {
            ConfigurationSection section = plugin.getConfig().getConfigurationSection("SpawnLocation");
            for(String key : section.getKeys(false)) {
                /*if(key.startsWith("w:") || key.startsWith("world:") || key.startsWith("firstjoin-w:") || key.startsWith("firstjoin-world:") || key.startsWith("fj-world:")) {
                    if(player.getWorld().getName().equals(key.split(":")[1])) {
                        if(key.startsWith("f") && plugin.firstJoin() && Storage.getFirstJoin(player)) {
                            getspawnvalues(key);
                            return;
                        } else {
                            getspawnvalues(key);
                            return;
                        }
                    }
                }*/
                if(!key.equals("default")) {
                    if(key.equals("firstjoin")) {
                        if(plugin.firstJoin() && Storage.getFirstJoin(player)) {
                            getspawnvalues(key);
                            return;
                        }
                    } else if(player.hasPermission(plugin.getConfig().getString("SpawnLocation." + key + ".Permission"))) {
                        getspawnvalues(key);
                        return;
                    }
                }
            }
            getspawnvalues("default");
        } catch(Exception ex) {
            MessageUtils.errorOccurred();
            Bukkit.getConsoleSender().sendMessage(Utils.color(plugin.consolePrefix + " &7=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-="));
            Bukkit.getConsoleSender().sendMessage(Utils.color(plugin.consolePrefix + " &7Error in the spawnvalues"));
            Bukkit.getConsoleSender().sendMessage(Utils.color(plugin.consolePrefix + " &7=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-="));
            ex.printStackTrace();
        }
    }

    private void getjoinsoundvalues(String key) {
        joinsoundon = plugin.getConfig().getBoolean("Sounds.Join." + key + ".Enabled", false);
        joinsound = plugin.getConfig().getString("Sounds.Join." + key + ".Sound");
        joinvolume = plugin.getConfig().getInt("Sounds.Join." + key + ".Volume");
        joinpitch = plugin.getConfig().getInt("Sounds.Join." + key + ".Pitch");
    }

    private void joinsoundvalues(Player player) {
        try {
            ConfigurationSection section = plugin.getConfig().getConfigurationSection("Sounds.Join");
            for(String key : section.getKeys(false)) {
                if(!key.equals("default")) {
                    if(key.equals("firstjoin")) {
                        if(plugin.firstJoin() && Storage.getFirstJoin(player)) {
                            getjoinsoundvalues(key);
                            return;
                        }
                    } else if(player.hasPermission(plugin.getConfig().getString("Sounds.Join." + key + ".Permission"))) {
                        getjoinsoundvalues(key);
                        return;
                    }
                }
            }
            getjoinsoundvalues("default");
        } catch(Exception ex) {
            MessageUtils.errorOccurred();
            Bukkit.getConsoleSender().sendMessage(Utils.color(plugin.consolePrefix + " &7=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-="));
            Bukkit.getConsoleSender().sendMessage(Utils.color(plugin.consolePrefix + " &7Error in the soundvalues"));
            Bukkit.getConsoleSender().sendMessage(Utils.color(plugin.consolePrefix + " &7=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-="));
            ex.printStackTrace();
        }
    }

    private void getquitsoundvalues(String key) {
        quitsoundon = plugin.getConfig().getBoolean("Sounds.Quit." + key + ".Enabled", false);
        quitsound = plugin.getConfig().getString("Sounds.Quit." + key + ".Sound");
        quitvolume = plugin.getConfig().getInt("Sounds.Quit." + key + ".Volume");
        quitpitch = plugin.getConfig().getInt("Sounds.Quit." + key + ".Pitch");
    }

    private void quitsoundvalues(Player player) {
        try {
            ConfigurationSection section = plugin.getConfig().getConfigurationSection("Sounds.Quit");
            for(String key : section.getKeys(false)) {
                if(!key.equals("default")) {
                    if(key.equals("firstjoin")) {
                        if(plugin.firstJoin() && Storage.getFirstJoin(player)) {
                            getquitsoundvalues(key);
                            return;
                        }
                    } else if(player.hasPermission(plugin.getConfig().getString("Sounds.Quit." + key + ".Permission"))) {
                        getquitsoundvalues(key);
                        return;
                    }
                }
            }
            getquitsoundvalues("default");

        } catch(Exception ex) {
            MessageUtils.errorOccurred();
            Bukkit.getConsoleSender().sendMessage(Utils.color(plugin.consolePrefix + " &7=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-="));
            Bukkit.getConsoleSender().sendMessage(Utils.color(plugin.consolePrefix + " &7Error in the soundvalues"));
            Bukkit.getConsoleSender().sendMessage(Utils.color(plugin.consolePrefix + " &7=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-="));
            ex.printStackTrace();
        }
    }

    private void getjoinmessagesvalues(String key) {
        String firstpath = "Messages.Join.";
        joinmessageon = LanguageManager.getLanguageBoolean(firstpath + key + ".Enabled", false);
        joinmessage = Utils.colorMessage(firstpath + key + ".Message");
    }

    private void getquitmessagesvalues(String key) {
        String firstpath = "Messages.Quit.";
        quitmessageon = LanguageManager.getLanguageBoolean(firstpath + key + ".Enabled", false);
        quitmessage = Utils.colorMessage(firstpath + key + ".Message");
    }

    private void messagesvalues(Player player, boolean join) {
        try {
            if(join) {
                ConfigurationSection section = LanguageManager.getLanguageSection("Messages.Join");

                for(String key : section.getKeys(false)) {
                    if(!key.equals("default")) {
                        if(key.equals("firstjoin")) {
                            if(plugin.firstJoin() && Storage.getFirstJoin(player)) {
                                getjoinmessagesvalues(key);
                                return;
                            }
                        } else if(player.hasPermission(LanguageManager.getLanguageMessage("Messages.Join." + key + ".Permission"))) {
                            getjoinmessagesvalues(key);
                            return;
                        }
                    }
                }
                getjoinmessagesvalues("default");
            } else {
                ConfigurationSection section = LanguageManager.getLanguageSection("Messages.Quit");
                for(String key : section.getKeys(false)) {
                    if(!key.equals("default")) {
                        if(key.equals("firstjoin")) {
                            if(plugin.firstJoin() && Storage.getFirstJoin(player)) {
                                getquitmessagesvalues(key);
                                return;
                            }
                        } else if(player.hasPermission(LanguageManager.getLanguageMessage("Messages.Quit." + key + ".Permission"))) {
                            getquitmessagesvalues(key);
                            return;
                        }
                    }
                }
                getquitmessagesvalues("default");
            }
        } catch(Exception ex) {
            MessageUtils.errorOccurred();
            Bukkit.getConsoleSender().sendMessage(Utils.color(plugin.consolePrefix + " &7=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-="));
            Bukkit.getConsoleSender().sendMessage(Utils.color(plugin.consolePrefix + " &7Error in the messages"));
            Bukkit.getConsoleSender().sendMessage(Utils.color(plugin.consolePrefix + " &7=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-="));
            ex.printStackTrace();
        }
    }

    private void getactionbarvalues(Player player, String key) {
        String firstpath = "Actionbar.";
        if(!LanguageManager.getLanguageBoolean(firstpath + key + ".Enabled", false)) {
            return;
        }
        ConfigurationSection titlesection = LanguageManager.getLanguageSection(firstpath + key + ".Actionbars");
        String secondpath = firstpath + key + ".Actionbars.";
        for(String titleskey : titlesection.getKeys(false)) {
            if(Integer.parseInt(actionbartask.get(player).toString()) < LanguageManager.getLanguageInt(secondpath + titleskey + ".RunTaskLater", 0)) {
                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    Utils.sendActionBar(player, Utils.setPlaceholders(player, Utils.colorMessage(secondpath + titleskey + ".Actionbar")));
                    Utils.debugmessage("Actionbar on join for " + player.getName() + " with runtasklater value " + LanguageManager.getLanguageInt(secondpath + titleskey + "RunTaskLater", 0));
                    actionbarvalues(player);
                }, LanguageManager.getLanguageInt(secondpath + titleskey + ".RunTaskLater", 0) - Integer.parseInt(actionbartask.get(player).toString()));
                actionbartask.replace(player, LanguageManager.getLanguageInt(secondpath + titleskey + ".RunTaskLater", 0));
                return;
            }
        }
    }

    private void actionbarvalues(Player player) {
        try {
            ConfigurationSection section = LanguageManager.getLanguageSection("Actionbar");

            for(String key : section.getKeys(false)) {
                if(!key.equals("default")) {
                    if(key.equals("firstjoin")) {
                        if(plugin.firstJoin() && Storage.getFirstJoin(player)) {
                            getactionbarvalues(player, key);
                            return;
                        }
                    } else if(player.hasPermission(LanguageManager.getLanguageMessage("Actionbar." + key + ".Permission"))) {
                        getactionbarvalues(player, key);
                        return;
                    }
                }
            }
            getactionbarvalues(player, "default");
        } catch(Exception ex) {
            MessageUtils.errorOccurred();
            Bukkit.getConsoleSender().sendMessage(Utils.color(plugin.consolePrefix + " &7=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-="));
            Bukkit.getConsoleSender().sendMessage(Utils.color(plugin.consolePrefix + " &7Error in the actionbarvalues"));
            Bukkit.getConsoleSender().sendMessage(Utils.color(plugin.consolePrefix + " &7=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-="));
            ex.printStackTrace();
        }
    }

    private void gettitlevalues(Player player, String key) {
        String firstpath = "Title.";
        if(!LanguageManager.getLanguageBoolean(firstpath + key + ".Enabled", false)) {
            return;
        }
        ConfigurationSection titlesection = LanguageManager.getLanguageSection(firstpath + key + ".Titles");
        String secondpath = firstpath + key + ".Titles.";
        for(String titleskey : titlesection.getKeys(false)) {
            if(Integer.parseInt(titletask.get(player).toString()) < LanguageManager.getLanguageInt(secondpath + titleskey + ".Timings.RunTaskLater", 0)) {
                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    Utils.sendTitle(player, Utils.setPlaceholders(player, Utils.colorMessage(secondpath + titleskey + ".Title")), LanguageManager.getLanguageInt(secondpath + titleskey + "Timings.TitleFadeInTime", 0), LanguageManager.getLanguageInt(secondpath + titleskey + "Timings.TitleShowTime", 0), LanguageManager.getLanguageInt(secondpath + titleskey + "Timings.TitleFadeOutTime", 0));
                    Utils.sendSubTitle(player, Utils.setPlaceholders(player, Utils.colorMessage(secondpath + titleskey + ".SubTitle")), LanguageManager.getLanguageInt(secondpath + titleskey + "Timings.SubTitleFadeInTime", 0), LanguageManager.getLanguageInt(secondpath + titleskey + "Timings.SubTitleShowTime", 0), LanguageManager.getLanguageInt(secondpath + titleskey + "Timings.SubTitleFadeOutTime", 0));
                    Utils.debugmessage("Title join for " + player.getName() + " with runtasklater value " + LanguageManager.getLanguageInt(secondpath + titleskey + "Timings.RunTaskLater", 0));
                    titlevalues(player);
                }, LanguageManager.getLanguageInt(secondpath + titleskey + ".Timings.RunTaskLater", 0) - Integer.parseInt(titletask.get(player).toString()));
                titletask.replace(player, LanguageManager.getLanguageInt(secondpath + titleskey + ".Timings.RunTaskLater", 0));
                return;
            }
        }
    }

    private void titlevalues(Player player) {
        try {
            ConfigurationSection section = LanguageManager.getLanguageSection("Title");
            for(String key : section.getKeys(false)) {
                if(!key.equals("default")) {
                    if(key.equals("firstjoin")) {
                        if(plugin.firstJoin() && Storage.getFirstJoin(player)) {
                            gettitlevalues(player, key);
                            return;
                        }
                    } else if(player.hasPermission(LanguageManager.getLanguageMessage("Title." + key + ".Permission"))) {
                        gettitlevalues(player, key);
                        return;
                    }
                }
            }
            gettitlevalues(player, "default");
        } catch(Exception ex) {
            MessageUtils.errorOccurred();
            Bukkit.getConsoleSender().sendMessage(Utils.color(plugin.consolePrefix + " &7=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-="));
            Bukkit.getConsoleSender().sendMessage(Utils.color(plugin.consolePrefix + " &7Error in the titlevalues"));
            Bukkit.getConsoleSender().sendMessage(Utils.color(plugin.consolePrefix + " &7=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-="));
            ex.printStackTrace();
        }
    }
}
