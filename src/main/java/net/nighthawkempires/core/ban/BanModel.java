package net.nighthawkempires.core.ban;

import com.demigodsrpg.util.datasection.DataSection;
import com.demigodsrpg.util.datasection.Model;
import net.nighthawkempires.core.NECore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BanModel implements Model {

    private UUID uuid;
    private String name;
    private boolean banned;
    private UUID by;
    private String date;
    private String reason;
    private int bans;

    public BanModel(UUID uuid) {
        this.uuid = uuid;
        this.name = Bukkit.getOfflinePlayer(uuid).getName();
        this.banned = false;
        this.by = null;
        this.date = null;
        this.reason = null;
        this.bans = 0;
    }

    public BanModel(String key, DataSection data) {
        this.uuid = UUID.fromString(key);
        this.name = Bukkit.getOfflinePlayer(uuid).getName();
        if (data.isBoolean("banned")) {
            this.banned = data.getBoolean("banned");
        }
        if (this.banned) {
            if (data.isString("banned-by")) {
                this.by = UUID.fromString(data.getString("banned-by"));
            } else {
                this.by = UUID.fromString("12ea1659-5da0-4ec0-9c8d-15350261e2d5");
            }
            if (data.isString("banned-date")) {
                this.date = data.getString("banned-date");
            } else {
                this.date = "0/0/0";
            }
            if (data.isString("banned-reason")) {
                this.reason = data.getString("banned-reason");
            } else {
                this.reason = "Unspecified";
            }
        }
        if (data.isInt("bans")) {
            this.bans = data.getInt("bans");
        }
    }

    public UUID getUniqueId() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public boolean isBanned() {
        return banned;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
        NECore.getBanRegistry().register(this);
    }

    public UUID getBy() {
        return by;
    }

    public void setBy(UUID by) {
        this.by = by;
        NECore.getBanRegistry().register(this);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
        NECore.getBanRegistry().register(this);
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
        NECore.getBanRegistry().register(this);
    }

    public int getBans() {
        return bans;
    }

    public void setBans(int bans) {
        this.bans = bans;
        NECore.getBanRegistry().register(this);
    }

    public String getBanInfo() {
        if (isBanned()) {
            return ChatColor
                    .translateAlternateColorCodes('&',
                            "\n&4&l&oBANNED&7&l&o!\n   \n" +
                                    "&8&l&oBy&7&l&o: &9&l&o" + Bukkit.getOfflinePlayer(this.by).getName() +
                                    "&r\n&8&l&oReason&7&l&o: " + this.reason +
                                    "&r\n&8&l&oOn&7&l&o: " + this.date +
                                    "&r\n&8&l&oRequest Unban&7&l&o: https://discord.gg/YevukF5");
        }
        return null;
    }

    public String getKey() {
        return uuid.toString();
    }

    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", this.name);
        map.put("banned", this.banned);
        if (this.banned) {
            map.put("banned-by", this.by.toString());
            map.put("banned-date", this.date);
            map.put("banned-reason", this.reason);
        }
        map.put("bans", this.bans);
        return map;
    }
}