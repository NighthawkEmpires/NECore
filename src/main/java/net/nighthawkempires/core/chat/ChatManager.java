package net.nighthawkempires.core.chat;

import com.google.common.collect.Maps;
import net.md_5.bungee.api.chat.BaseComponent;
import net.nighthawkempires.core.NECore;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

public class ChatManager {

    private ConcurrentMap<String, String> replyMap;
    private ConcurrentMap<UUID, Long> muteMap;

    public ChatManager() {
        if (NECore.getSettings().useRedis) {
            try {
                replyMap = NECore.getRedisCore().getRedis().getMap("reply.map");
                muteMap = NECore.getRedisCore().getRedis().getMap("mute.map");
            } catch (Exception ignored) {
                replyMap = Maps.newConcurrentMap();
                muteMap = Maps.newConcurrentMap();
            }
        } else {
            replyMap = Maps.newConcurrentMap();
            muteMap = Maps.newConcurrentMap();
        }
    }

    public void sendMessage(BaseComponent message) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.spigot().sendMessage(message);
        }
    }

    public void setReplyTo(String uuid1, String uuid2) {
        if (replyMap.containsKey(uuid1)) {
            replyMap.remove(uuid1);
        }

        replyMap.put(uuid1, uuid2);
    }

    public String getReplyTo(String uuid) {
        if (replyMap.containsKey(uuid)) {
            return replyMap.get(uuid);
        }
        return null;
    }

    public void addMute(UUID uuid, String time) {
        long ticks;
        if (time.contains("d")) {
            ticks = Long.parseLong(time.replaceAll("d", "")) * 24 * 60 * 60 * 20;
            muteMap.put(uuid, ticks);
            Bukkit.getScheduler().runTaskLater(NECore.getPlugin(), () -> {
                muteMap.remove(uuid);
            }, ticks);
        } else if (time.contains("h")) {
            ticks = Long.parseLong(time.replaceAll("h", "")) * 60 * 60 * 20;
            muteMap.put(uuid, ticks);
            Bukkit.getScheduler().runTaskLater(NECore.getPlugin(), () -> {
                muteMap.remove(uuid);
            }, ticks);
        } else if (time.contains("m")) {
            ticks = Long.parseLong(time.replaceAll("m", "")) * 60 * 20;
            muteMap.put(uuid, ticks);
            Bukkit.getScheduler().runTaskLater(NECore.getPlugin(), () -> {
                muteMap.remove(uuid);
            }, ticks);
        } else if (time.contains("s")) {
            ticks = Long.parseLong(time.replaceAll("s", "")) * 20;
            muteMap.put(uuid, ticks);
            Bukkit.getScheduler().runTaskLater(NECore.getPlugin(), () -> {
                muteMap.remove(uuid);
            }, ticks);
        } else {
        }
    }

    public void addMute(UUID uuid) {
        muteMap.put(uuid, -1L);
    }
}
