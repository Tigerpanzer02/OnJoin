package at.tigerpanzer.util;

import org.bukkit.Bukkit;

/**
 * @author Plajer
 * <p>
 * Created at 20.05.2018
 */
public class ReflectionUtils {

    public static Class<?> getNMSClass(String name) {
        try {
            return Class.forName("net.minecraft.server." + getVersion() + "." + name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    }

}
