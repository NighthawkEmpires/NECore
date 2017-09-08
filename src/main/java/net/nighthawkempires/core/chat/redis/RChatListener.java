package net.nighthawkempires.core.chat.redis;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import net.nighthawkempires.core.RedisCore;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.redisson.core.MessageListener;

public class RChatListener implements MessageListener<String> {

    private RedisCore redisCore;

    public RChatListener(RedisCore redisCore) {
        this.redisCore = redisCore;
    }

    @Override
    public void onMessage(String ignored, String message) {
        if (message != null) {
            if (!message.startsWith(redisCore.getServerId() + "$")) {
                TextComponent component = new TextComponent(ComponentSerializer.parse(message));
                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.spigot().sendMessage(component);
                }
            }
        }
    }
}
