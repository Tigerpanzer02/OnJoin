package at.tigerpanzer.onjoin.util;

import at.tigerpanzer.onjoin.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Storage {

    private Main plugin;

    public Storage(Main plugin) {
        this.plugin = plugin;
    }

    private static String path = "plugins//OnJoin//Data//%s.yml";

    private void SetDataToFile(Player playerName, boolean FirstJoin, String Date) {
        String UUID = playerName.getUniqueId().toString();
        File file = new File(String.format(path, UUID));
        YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        //PlayerData
        cfg.set("PlayerName", playerName);
        cfg.set("UUID", UUID);
        cfg.set("FirstJoin", FirstJoin);
        cfg.set("Date", Date);
        try {
            cfg.save(file);
        } catch(IOException e) {
            e.printStackTrace();
        }

    }

    public String getDataFile(String name, String data) {
        File file = new File(String.format(path, name));
        YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        //PlayerData
        if(data.equalsIgnoreCase("PlayerName")) {
            return cfg.getString("PlayerName");
        }
        if(data.equalsIgnoreCase("UUID")) {
            return cfg.getString("UUID");
        }
        if(data.equalsIgnoreCase("Date")) {
            return cfg.getString("Date");
        }
        return "null";
    }

    public static boolean getFirstJoin(Player p) {
        File file = new File(String.format(path, p.getUniqueId().toString()));
        YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        return cfg.getBoolean("FirstJoin");
    }

    public static void setFirstJoin(Player p, boolean data) {
        File file = new File(String.format(path, p.getUniqueId().toString()));
        YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        cfg.set("FirstJoin", data);
        try {
            cfg.save(file);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }


    public static void SetDataFile(String uuid, String data, String DataName) {
        File file = new File(String.format(path, uuid));
        YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        //PlayerData
        if(DataName.equalsIgnoreCase("PlayerName")) {
            cfg.set("PlayerName", data);
        }
        if(DataName.equalsIgnoreCase("UUID")) {
            cfg.set("UUID", data);
        }
        if(DataName.equalsIgnoreCase("FirstJoin")) {
            cfg.set("FirstJoin", data);
        }
        if(DataName.equalsIgnoreCase("Date")) {
            cfg.set("Date", data);
        }
        try {
            cfg.save(file);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void DataOnJoin(Player p) {
        String name = p.getUniqueId().toString();
        File file = new File(String.format(path, name));
        Date date = new Date(System.currentTimeMillis());
        //GetPlayerData
        if(plugin.mySQLEnabled()) {
            try {
                ResultSet rs = MySQL.getResultSet("SELECT * FROM FirstJoin WHERE UUID='" + p.getUniqueId() + "'");
                if(!rs.next()) {
                    MySQL.update("INSERT INTO FirstJoin (PlayerName, UUID, FirstJoin, Date) VALUES ('" + p.getName() + "','" + p.getUniqueId() + "','true','" + getDate() + "')");
                } else {
                    MySQL.update("UPDATE FirstJoin SET Spielername='" + p.getName() + "' WHERE UUID='" + p.getUniqueId() + "'");
                    boolean FirstJoin = rs.getBoolean("FirstJoin");
                    String Date = rs.getString("Date");
                    SetDataToFile(p, FirstJoin, Date);
                    return;
                }
            } catch(Exception ex2) {
                ex2.printStackTrace();
            }
        } else {
            if(file.exists()) {
                return;
            } else {
                boolean FirstJoin = true;
                String Date = getDate();
                SetDataToFile(p, FirstJoin, Date);
            }
        }
    }

    public void DataOnQuit(Player p) {
        if(plugin.mySQLEnabled()) {
            String name = p.getUniqueId().toString();
            File file = new File(String.format(path, name));
            YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
            //SetPlayerData
            try {
                boolean FirstJoin = cfg.getBoolean("FirstJoin");
                String Date = cfg.getString("Date");

                MySQL.update("UPDATE FirstJoin SET FirstJoin='" + FirstJoin + "', Date='" + Date + "' WHERE UUID='" + name + "'");
            } catch(Exception ex2) {
                ex2.printStackTrace();
            }
        }
    }

    private static String getDate() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return simpleDateFormat.format(cal.getTime());
    }
}
