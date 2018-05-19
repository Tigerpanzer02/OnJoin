package at.tigerpanzer.util;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;

public class actionbar {
	
	 public static void sendActionBar(Player player, String message){
	        CraftPlayer p = (CraftPlayer)player;
	        IChatBaseComponent cbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + message + "\"}");
	        PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, (byte)2);
	        p.getHandle().playerConnection.sendPacket(ppoc);
	    }

}
