package net.nighthawkempires.core.mute;

import com.demigodsrpg.util.datasection.DataSection;
import com.demigodsrpg.util.datasection.Model;
import net.nighthawkempires.core.NECore;
import net.nighthawkempires.core.language.Tags;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MuteModel implements Model {

    private UUID uuid;
    private String name;
    private boolean muted;
    private UUID by;
    private String reason;
    private int mutes;

    public MuteModel(UUID uuid) {
        this.uuid = uuid;
        this.name = Bukkit.getOfflinePlayer(uuid).getName();
        this.muted = false;
        this.by = null;
        this.reason = null;
        this.mutes = 0;
    }

    public MuteModel(String key, DataSection data) {
        this.uuid = UUID.fromString(key);
        this.name = Bukkit.getOfflinePlayer(uuid).getName();
        if (data.isBoolean("muted")) {
            this.muted = data.getBoolean("muted");
        }
        if (this.muted) {
            if (data.isString("muted-by")) {
                this.by = UUID.fromString(data.getString("muted-by"));
            } else {
                this.by = UUID.fromString("12ea1659-5da0-4ec0-9c8d-15350261e2d5");
            }
            if (data.isString("muted-reason")) {
                this.reason = data.getString("muted-reason");
            } else {
                this.reason = "Unspecified";
            }
        }
        if (data.isInt("mutes")) {
            this.mutes = data.getInt("mutes");
        }
    }

    public UUID getUniqueId() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public boolean isMuted() {
        return muted;
    }

    public void setMuted(boolean muted) {
        this.muted = muted;
        NECore.getMuteRegistry().register(this);
    }

    public UUID getBy() {
        return by;
    }

    public void setBy(UUID by) {
        this.by = by;
        NECore.getMuteRegistry().register(this);
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
        NECore.getMuteRegistry().register(this);
    }

    public int getMutes() {
        return mutes;
    }

    public void setMutes(int mutes) {
        this.mutes = mutes;
        NECore.getMuteRegistry().register(this);
    }

    public String getMuteInfo() {
        if (isMuted()) {
            String tag = Tags.valueOf(NECore.getSettings().SERVER.getName().toUpperCase()).getTag();
            return tag + ChatColor.GRAY + "You were muted by " +
                    ChatColor.BLUE + Bukkit.getOfflinePlayer(this.by).getName() +
                    ChatColor.GRAY + " for: " + ChatColor.RED + this.reason + ChatColor.GRAY + ". \n" +
                    tag + ChatColor.GRAY +
                    "To request to be unmuted join our Discord and fill out your request accordingly https://discord.gg/YevukF5";
        }
        return null;
    }

    public String getKey() {
        return uuid.toString();
    }

    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", this.name);
        map.put("muted", this.muted);
        if (this.muted) {
            map.put("muted-by", this.by.toString());
            map.put("muted-reason", this.reason);
        }
        map.put("mutes", this.mutes);
        return map;
    }
}