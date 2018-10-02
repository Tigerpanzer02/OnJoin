package at.tigerpanzer.util;


import at.tigerpanzer.Main;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;


public class SpigotPluginUpdater {

    public static final String VERSION = "Update 1";
    private final Main plugin;
    private final String pluginurl;
    private URL url;
    private boolean canceled = false;
    private String version = "";
    private String downloadURL = "";
    private String changeLog = "";
    private boolean out = true;

    public SpigotPluginUpdater(Main plugin, String pluginurl) {
        try {
            url = new URL(pluginurl);
        } catch(MalformedURLException e) {
            canceled = true;
            plugin.getLogger().log(Level.WARNING, "Error: Bad URL while checking {0} !", plugin.getName());
        }
        this.plugin = plugin;
        this.pluginurl = pluginurl;
    }

    public void enableOut() {
        out = true;
    }


    public void disableOut() {
        out = true;
    }

    public boolean needsUpdate() {
        if(canceled) {
            return false;
        }
        try {
            URLConnection con = url.openConnection();
            InputStream _in = con.getInputStream();
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(_in);

            Node nod = doc.getElementsByTagName("item").item(0);
            NodeList children = nod.getChildNodes();

            version = children.item(1).getTextContent();
            downloadURL = children.item(3).getTextContent();
            changeLog = children.item(5).getTextContent();
            if(newVersionAvailiable(plugin.getDescription().getVersion(), version.replaceAll("[a-zA-z ]", ""))) {
                if(out) {
                    plugin.getLogger().log(Level.INFO, " New Version: {0}", version.replaceAll("[a-zA-z ]", ""));
                    //plugin.getLogger().log(Level.INFO, Main.getInstance().getConfig().getString("Prefix") + " Download it here: {0}", downloadURL);
                    plugin.getLogger().log(Level.INFO, " Changelog: {0}", changeLog);
                    Bukkit.getConsoleSender().sendMessage(Utils.color(plugin.getConfig().getString("Console.PrefixConsole") + plugin.getConfig().getString("Console.newupdatebconrl")));
                    Bukkit.broadcastMessage(Utils.color(plugin.getConfig().getString("Prefix") + plugin.getConfig().getString("Console.newupdatebconrl")));
                    plugin.setNeedUpdateJoin(false);
                }
                return true;
            }

        } catch(IOException | SAXException | ParserConfigurationException e) {
            plugin.getLogger().log(Level.WARNING, "Error in checking update for ''{0}''!", plugin.getName());
            plugin.getLogger().log(Level.WARNING, "Error: ", e);
        }
        return false;
    }


    private boolean newVersionAvailiable(String oldv, String newv) {
        if(oldv != null && newv != null) {
            oldv = oldv.replace('.', '_');
            newv = newv.replace('.', '_');
            if(oldv.split("_").length != 0 && oldv.split("_").length != 1 && newv.split("_").length != 0 && newv.split("_").length != 1) {

                int vnum = Integer.valueOf(oldv.split("_")[0]);
                int vsec = Integer.valueOf(oldv.split("_")[1]);

                int newvnum = Integer.valueOf(newv.split("_")[0]);
                int newvsec = Integer.valueOf(newv.split("_")[1]);
                if(newvnum > vnum) {
                    return true;
                }

                if(newvnum == vnum) {
                    if(newvsec > vsec) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}

