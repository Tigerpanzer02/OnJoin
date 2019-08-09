/*
 *  OnJoin - Your Server Join Plugin
 *          With this plugin, joins are
 *          unique on your server​
 *
 *  Maintained by Tigerpanzer_02
 */

package at.tigerpanzer.onjoin.events;

import at.tigerpanzer.onjoin.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class JoinQuitWorldListener implements Listener {

    private Main plugin;

    public JoinQuitWorldListener(Main plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onJoin(PlayerChangedWorldEvent e) {
    }
}