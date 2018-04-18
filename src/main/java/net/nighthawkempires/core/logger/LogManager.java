package net.nighthawkempires.core.logger;

import net.nighthawkempires.core.NECore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;

public class LogManager {


    public static void info(Plugin plugin, String message) {
        Bukkit.getConsoleSender().sendMessage(
                ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + plugin.getName() + ChatColor.DARK_GRAY + "] " +
                        ChatColor.GREEN + message);
    }

    public static void info(String message) {
        info(NECore.getPlugin(), message);
    }

    public static void warn(Plugin plugin, String message) {
        Bukkit.getConsoleSender().sendMessage(
                ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + plugin.getName() + ChatColor.DARK_GRAY + "] " +
                        ChatColor.RED + message);
    }

    public static void warn(String message) {
        warn(NECore.getPlugin(), message);
    }
}
