package at.tigerpanzer.util;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class FileManager {
    public static void createFile() {
        File file = new File("plugins/OnJoin", "onjoin.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        if(!file.exists()) {
            cfg.set("Console.PrefixConsole", "&7[&eOnJoin&7]");
            cfg.set("Console.outconfigcreate", " Eine Config wurde im Plugin-Ordner erstellt!");
            cfg.set("Console.outconfigload", " Die Einstellungen aus der Config wurden erfolgreich geladen!");
            cfg.set("Console.newupdatebconrl", " Neue Version verf�gbar! Downloade sie dir auf SpigotMC!");

            cfg.set("Prefix", "&7[&eOnJoin&7] &7�&e ");

            cfg.set("Permissionfail", "&cDaf�r hast du keine Rechte");

            cfg.set("targetfalse", "&cDer Spieler ist momentan &enicht online &coder wurde &efalsch geschrieben");

            cfg.set("noplayer", "&cDu bist kein Spieler");

            cfg.set("Title.TitleOnJoin", true);
            cfg.set("Title.Title1", "&bCC&3SW");
            cfg.set("Title.SubTitle1", "&eOnJoin");
            cfg.set("Title.SubTitle2", "&aTitle&8 - &7Kann in der &8onjoin.yml &7editiert werden");

            cfg.set("WelcomeMessage.WelcomeMessageOn", true);
            cfg.set("WelcomeMessage.WelcomeMessageHeader", "&e------------------------------------");
            cfg.set("WelcomeMessage.WelcomeMessageLine1", "&eWillkommen auf unseren Server!");
            cfg.set("WelcomeMessage.WelcomeMessageLine2", "&eOnJoin &8- &7Dein Join Plugin");
            cfg.set("WelcomeMessage.WelcomeMessageLine3", "&7Plugin by &bTigerpanzer_02/Tigerkatze");
            cfg.set("WelcomeMessage.WelcomeMessageLine4", "&7Dein Netzwerk @CC-SW");
            cfg.set("WelcomeMessage.WelcomeMessageFooder", "&e------------------------------------");

            cfg.set("Join.JoinMessageOn", true);
            cfg.set("Join.JoinMessage", "&7%player% hat den Server betreten");
            cfg.set("Join.JoinSoundOn", true);
            cfg.set("Join.JoinSound", "FIREWORK_LAUNCH");

            cfg.set("actionbar.actionbaronjoin", true);
            cfg.set("actionbar.actionbar1", "&7%player%! Willkommen");
            cfg.set("actionbar.actionbar2", "&7 CC-SW Network");

            cfg.set("Quit.QuitMessageOn", true);
            cfg.set("Quit.QuitMessage", "&7%player% hat den Server verlassen");
            cfg.set("Quit.QuitSoundOn", true);
            cfg.set("Quit.QuitSound", "BAT_DEATH");


            try {
                cfg.save(file);
                Bukkit.getConsoleSender().sendMessage(Utils.color(FileManager.getString("Console.PrefixConsole") + FileManager.getString("Console.outconfigcreate")));
            } catch(IOException e) {
                e.printStackTrace();
            }
        } else {
            Bukkit.getConsoleSender().sendMessage(Utils.color(FileManager.getString("Console.PrefixConsole") + FileManager.getString("Console.outconfigload")));
            return;
        }
    }

    public static void update(String path, String newstring) {
        File file = new File("plugins/OnJoin", "onjoin.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        cfg.set(path, newstring);
    }

    public static boolean getBoolean(String path) {
        File file = new File("plugins/OnJoin", "onjoin.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        return cfg.getBoolean(path);
    }

    public static String getString(String path) {
        File file = new File("plugins/OnJoin", "onjoin.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        return cfg.getString(path);
    }
}