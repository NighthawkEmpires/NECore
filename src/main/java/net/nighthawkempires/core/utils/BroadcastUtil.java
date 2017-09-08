package net.nighthawkempires.core.utils;

import net.nighthawkempires.core.language.Lang;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BroadcastUtil {

    public static void broadcast(String message) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + message);
        }
    }
}
