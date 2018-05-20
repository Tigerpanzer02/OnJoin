package at.tigerpanzer.util;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

public class tabtitle {
    @Deprecated
    public static void sendTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String message) {
        tabtitle.sendTitle(player, fadeIn, stay, fadeOut, message, null);
    }

    @Deprecated
    public static void sendSubtitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String message) {
        tabtitle.sendTitle(player, fadeIn, stay, fadeOut, null, message);
    }

    @Deprecated
    public static void sendFullTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String title, String subtitle) {
        tabtitle.sendTitle(player, fadeIn, stay, fadeOut, title, subtitle);
    }

    public static void sendTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String title, String subtitle) {
        PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
        PacketPlayOutTitle packetPlayOutTimes = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, null, fadeIn.intValue(), stay.intValue(), fadeOut.intValue());
        connection.sendPacket((Packet) packetPlayOutTimes);
        if(subtitle != null) {
            subtitle = subtitle.replaceAll("%player%", player.getDisplayName());
            subtitle = ChatColor.translateAlternateColorCodes((char) '&', (String) subtitle);
            IChatBaseComponent titleSub = IChatBaseComponent.ChatSerializer.a((String) ("{\"text\": \"" + subtitle + "\"}"));
            PacketPlayOutTitle packetPlayOutSubTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, titleSub);
            connection.sendPacket((Packet) packetPlayOutSubTitle);
        }
        if(title != null) {
            title = title.replaceAll("%player%", player.getDisplayName());
            title = ChatColor.translateAlternateColorCodes((char) '&', (String) title);
            IChatBaseComponent titleMain = IChatBaseComponent.ChatSerializer.a((String) ("{\"text\": \"" + title + "\"}"));
            PacketPlayOutTitle packetPlayOutTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, titleMain);
            connection.sendPacket((Packet) packetPlayOutTitle);
        }
    }

    public static void sendTabTitle(Player player, String header, String footer) {
        if(header == null) {
            header = "";
        }
        header = ChatColor.translateAlternateColorCodes((char) '&', (String) header);
        if(footer == null) {
            footer = "";
        }
        footer = ChatColor.translateAlternateColorCodes((char) '&', (String) footer);
        header = header.replaceAll("%player%", player.getDisplayName());
        footer = footer.replaceAll("%player%", player.getDisplayName());
        PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
        IChatBaseComponent tabTitle = IChatBaseComponent.ChatSerializer.a((String) ("{\"text\": \"" + header + "\"}"));
        IChatBaseComponent tabFoot = IChatBaseComponent.ChatSerializer.a((String) ("{\"text\": \"" + footer + "\"}"));
        PacketPlayOutPlayerListHeaderFooter headerPacket = new PacketPlayOutPlayerListHeaderFooter(tabTitle);
        try {
            try {
                Field field = headerPacket.getClass().getDeclaredField("b");
                field.setAccessible(true);
                field.set((Object) headerPacket, (Object) tabFoot);
            } catch(Exception e) {
                e.printStackTrace();
                connection.sendPacket((Packet) headerPacket);
            }
        } finally {
            connection.sendPacket((Packet) headerPacket);
        }
    }

    boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        }
        return true;
    }
}