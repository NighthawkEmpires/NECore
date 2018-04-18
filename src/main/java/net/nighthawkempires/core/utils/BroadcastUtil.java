package net.nighthawkempires.core.utils;

import net.nighthawkempires.core.NECore;
import net.nighthawkempires.core.language.Tags;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class BroadcastUtil {

    public static void broadcast(String message) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(Tags.valueOf(NECore.getSettings().SERVER.getName().toUpperCase()).getTag() + ChatColor.GRAY + message);
        }
    }
}
