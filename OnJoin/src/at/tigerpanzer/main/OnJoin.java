package at.tigerpanzer.main;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import at.tigerpanzer.command.cmd_onjoin;
import at.tigerpanzer.events.joinquitevent;
import at.tigerpanzer.util.FileManager;


public class OnJoin
  extends JavaPlugin implements Listener
{
  public static OnJoin instance;
  
public OnJoin()
  {
    instance = this;
  } 

  
  public static OnJoin getInstance()
  {
    return instance;
  }
  
  public void onEnable()
  {
	  FileManager.createFile();
	  Bukkit.getConsoleSender().sendMessage(FileManager.getString("Console.PrefixConsole").replaceAll("&", "§") + " §cWird §aGESTARTET §7| §cis §aSTARTED");
	
    register();
	update();
    
    Bukkit.getConsoleSender().sendMessage(FileManager.getString("Console.PrefixConsole").replaceAll("&", "§") + " §7=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
    Bukkit.getConsoleSender().sendMessage(FileManager.getString("Console.PrefixConsole").replaceAll("&", "§") + " §cPlugin version: §e" + getDescription().getVersion());
    Bukkit.getConsoleSender().sendMessage(FileManager.getString("Console.PrefixConsole").replaceAll("&", "§") + " §cPlugin author: §e" + getDescription().getAuthors());
    Bukkit.getConsoleSender().sendMessage(FileManager.getString("Console.PrefixConsole").replaceAll("&", "§") + " §cPlugin status: §aaktiviert §c| §aenabled");
    Bukkit.getConsoleSender().sendMessage(FileManager.getString("Console.PrefixConsole").replaceAll("&", "§") + " §7=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");

  }
  
  private void update()
  {
	  SpigotPluginUpdater update = new SpigotPluginUpdater(this, "http://tigerpanzer02.bplaced.net/plugins/onjoin/");
	  update.enableOut();
      if(update.needsUpdate());
  }
  private void register()
  {
	    getCommand("onjoin").setExecutor(new cmd_onjoin());
	    PluginManager pm = Bukkit.getPluginManager();
	    pm.registerEvents(new joinquitevent(), this);
	     
  }

  public void onDisable()
  {
    Bukkit.getConsoleSender().sendMessage(FileManager.getString("Console.PrefixConsole").replaceAll("&", "§") + " §7=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
    Bukkit.getConsoleSender().sendMessage(FileManager.getString("Console.PrefixConsole").replaceAll("&", "§") + " §cPlugin version: §e" + getDescription().getVersion());
    Bukkit.getConsoleSender().sendMessage(FileManager.getString("Console.PrefixConsole").replaceAll("&", "§") + " §cPlugin author: §e" + getDescription().getAuthors());
    Bukkit.getConsoleSender().sendMessage(FileManager.getString("Console.PrefixConsole").replaceAll("&", "§") + " §cPlugin status: §4deaktiviert §c| §4disabled");
    Bukkit.getConsoleSender().sendMessage(FileManager.getString("Console.PrefixConsole").replaceAll("&", "§") + " §7=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
  }
}
