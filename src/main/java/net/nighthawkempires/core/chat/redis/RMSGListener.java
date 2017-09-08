package net.nighthawkempires.core.chat.redis;

import net.nighthawkempires.core.NECore;
import net.nighthawkempires.core.chat.message.PrivateMessage;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.redisson.core.MessageListener;

public class RMSGListener implements MessageListener<String> {

    @Override
    public void onMessage(String ignored, String json) {
        PrivateMessage message = new PrivateMessage(NECore.getInstance(), json);
        OfflinePlayer offline = Bukkit.getOfflinePlayer(message.getTarget());
        if (offline.isOnline()) {
            offline.getPlayer().sendMessage(message.getFormattedMessage(false));
        }
    }
}
