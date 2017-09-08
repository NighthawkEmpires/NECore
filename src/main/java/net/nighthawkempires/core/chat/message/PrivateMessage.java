package net.nighthawkempires.core.chat.message;

import com.google.gson.GsonBuilder;
import net.md_5.bungee.api.ChatColor;
import net.nighthawkempires.core.NECore;
import net.nighthawkempires.core.RedisCore;
import net.nighthawkempires.core.language.Lang;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.HashMap;
import java.util.Map;

public class PrivateMessage {

    public static final long serialVersionUID = 1L;
    private final transient NECore instance;

    private String target;
    private String sender;
    private String message;

    public PrivateMessage(NECore instance, String json) {
        this.instance = instance;
        Map<String, Object> map = new GsonBuilder().create().fromJson(json, Map.class);
        target = map.get("target").toString();
        sender = map.get("sender").toString();
        message = map.get("message").toString();
    }

    public PrivateMessage(NECore instance, String target, String sender, String message) {
        this.instance = instance;
        this.target = target;
        this.sender = sender;
        this.message = message;
    }

    public String getTarget() {
        return target;
    }

    public String getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

    public String getFormattedMessage(boolean out) {
        return ChatColor.DARK_GRAY + (out ? "<--[" + ChatColor.GREEN + target + ChatColor.DARK_GRAY + "]<" : ">[" + ChatColor.GREEN + sender + ChatColor.DARK_GRAY + "]-->")
                + ChatColor.GRAY + ": " + message;
    }

    public String toJson() {
        Map<String, Object> map = new HashMap<>();
        map.put("target", target);
        map.put("sender", sender);
        map.put("message", message);
        return new GsonBuilder().create().toJson(map, Map.class);
    }

    public void send() {
        OfflinePlayer sender = Bukkit.getOfflinePlayer(this.sender);

        if (Bukkit.getPlayer(target) != null) {
            Bukkit.getPlayer(target).sendMessage(getFormattedMessage(false));
        } else if (NECore.getSettings().useRedis) {
            RedisCore.redis_msg.publishAsync(toJson());
        } else if (sender.isOnline()) {
            sender.getPlayer().sendMessage(Lang.MESS_ERROR.getServerMessage());
            return;
        }

        if (sender.isOnline()) {
            sender.getPlayer().sendMessage(getFormattedMessage(true));
        }

        NECore.getChatManager().setReplyTo(this.sender, this.target);
        NECore.getChatManager().setReplyTo(this.target, this.sender);
    }
}
