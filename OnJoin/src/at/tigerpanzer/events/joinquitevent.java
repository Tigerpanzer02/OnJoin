package at.tigerpanzer.events;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import at.tigerpanzer.main.OnJoin;
import at.tigerpanzer.util.FileManager;
import at.tigerpanzer.util.actionbar;
import at.tigerpanzer.util.tabtitle;

public class joinquitevent implements Listener {
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onJoin(PlayerJoinEvent e) {
		
		Player p = e.getPlayer();
		if (FileManager.getString("Join.JoinSoundOn").contains("true")) {
		p.playSound(p.getLocation(), Sound.valueOf(FileManager.getString("Join.JoinSound")), 3, 1);  
		}
	    if (FileManager.getString("Join.JoinMessageOn").contains("true")) {
	    	e.setJoinMessage(FileManager.getString("Join.JoinMessage").replaceAll("%player%", p.getDisplayName()).replaceAll("&", "§"));
	      } else if (FileManager.getString("Join.JoinMessageOn").contains("false")) {
	        e.setJoinMessage("");
	      }
	    Bukkit.getScheduler().runTaskLater(OnJoin.getInstance(), new Runnable()
	    {
	    	@Override
	      public void run()
	      {

	        if (FileManager.getString("Title.TitleOnJoin").contains("true")) {
	        	tabtitle.sendFullTitle(p, Integer.valueOf(25), Integer.valueOf(90), Integer.valueOf(0), FileManager.getString("Title.Title1").replaceAll("%player%", p.getDisplayName()), FileManager.getString("Title.SubTitle1").replaceAll("%player%", p.getDisplayName()));
		    	if (FileManager.getString("actionbar.actionbaronjoin").contains("true")) {
			    	actionbar.sendActionBar(p, FileManager.getString("actionbar.actionbar1").replaceAll("%player%", p.getDisplayName()).replaceAll("&", "§"));}
		        if (FileManager.getString("WelcomeMessage.WelcomeMessageOn").contains("true"))
		        {
		        	Player p = e.getPlayer();
		          p.sendMessage(FileManager.getString("Prefix").replaceAll("&", "§") + FileManager.getString("WelcomeMessage.WelcomeMessageHeader").replaceAll("%player%", p.getDisplayName()).replaceAll("&", "§"));
		          p.sendMessage(FileManager.getString("Prefix").replaceAll("&", "§") + FileManager.getString("WelcomeMessage.WelcomeMessageLine1").replaceAll("%player%", p.getDisplayName()).replaceAll("&", "§"));
		          p.sendMessage(FileManager.getString("Prefix").replaceAll("&", "§") + FileManager.getString("WelcomeMessage.WelcomeMessageLine2").replaceAll("%player%", p.getDisplayName()).replaceAll("&", "§"));
		          p.sendMessage(FileManager.getString("Prefix").replaceAll("&", "§") + FileManager.getString("WelcomeMessage.WelcomeMessageLine3").replaceAll("%player%", p.getDisplayName()).replaceAll("&", "§"));
		          p.sendMessage(FileManager.getString("Prefix").replaceAll("&", "§") + FileManager.getString("WelcomeMessage.WelcomeMessageLine4").replaceAll("%player%", p.getDisplayName()).replaceAll("&", "§"));
		          p.sendMessage(FileManager.getString("Prefix").replaceAll("&", "§") + FileManager.getString("WelcomeMessage.WelcomeMessageFooder").replaceAll("%player%", p.getDisplayName()).replaceAll("&", "§"));
		        }
	        }
	      }
	    }, 2L);
	    
	    Bukkit.getScheduler().runTaskLater(OnJoin.getInstance(), new Runnable()
	    {
	    	@Override
	      public void run()
	      {
	        if (FileManager.getString("Title.TitleOnJoin").contains("true")) {
	        	tabtitle.sendSubtitle(p, Integer.valueOf(0), Integer.valueOf(90), Integer.valueOf(0), FileManager.getString("Title.SubTitle2").replaceAll("%player%", p.getDisplayName()));
	        }
	        if (FileManager.getString("actionbar.actionbaronjoin").contains("true")) {
	        actionbar.sendActionBar(p, FileManager.getString("actionbar.actionbar2").replaceAll("%player%", p.getDisplayName()).replaceAll("&", "§"));

	      }}
	    }, 65L);
	    

	}
	@EventHandler(priority = EventPriority.NORMAL)
	public void onQuit(PlayerQuitEvent e) {
		
		Player p = e.getPlayer();
		if (FileManager.getString("Quit.QuitSoundOn").contains("true")) {
		p.playSound(p.getLocation(), Sound.valueOf(FileManager.getString("Quit.QuitSound")), 3, 1);  
		}
	    if (FileManager.getString("Quit.QuitMessageOn").contains("true")) {
	    	e.setQuitMessage(FileManager.getString("Quit.QuitMessage").replaceAll("%player%", p.getDisplayName()).replaceAll("&", "§"));
	      } else if (FileManager.getString("Quit.QuitMessageOn").contains("false")) {
	        e.setQuitMessage("");
	      }
	    }
}
