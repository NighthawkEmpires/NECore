package net.nighthawkempires.core.ban;

import net.nighthawkempires.core.NECore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class   BanManager {

    public void ban(UUID uuid, String reason, UUID bannedBy) {
        if (isBanned(uuid))return;
        try {
            PreparedStatement statement = NECore.getMySQL().getConnection().prepareStatement("SELECT * FROM bans WHERE uuid=?");
            statement.setString(1, uuid.toString());
            ResultSet results = statement.executeQuery();
            results.next();
            if (!isBanned(uuid)) {
                PreparedStatement insert = NECore.getMySQL().getConnection().prepareStatement("INSERT INTO bans(" +
                        "uuid,name,reason,date,banned_by) VALUE (?,?,?,?,?)");
                insert.setString(1, uuid.toString());
                insert.setString(2, Bukkit.getOfflinePlayer(uuid).getName());
                insert.setString(3, reason);
                insert.setString(4, new SimpleDateFormat("MM/dd/yyyy").format(new Date()));
                insert.setString(5, bannedBy.toString());
                insert.executeUpdate();
                NECore.getLoggers().info("Banned Player " + uuid.toString() + ": " + Bukkit.getOfflinePlayer(uuid).getName() + ".");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void unban(UUID uuid) {
        if (!isBanned(uuid))return;

        try {
            PreparedStatement statement = NECore.getMySQL().getConnection().prepareStatement("DELETE FROM bans WHERE uuid=? limit 1");
            statement.setString(1, uuid.toString());
            statement.execute();
            NECore.getLoggers().info("Unbanned Player " + uuid.toString() + ": " + Bukkit.getOfflinePlayer(uuid).getName() + ".");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isBanned(UUID uuid) {
        try {
            PreparedStatement statement = NECore.getMySQL().getConnection().prepareStatement("SELECT * FROM bans WHERE UUID=?");
            statement.setString(1, uuid.toString());
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getBanInfo(UUID uuid) {
        if (!isBanned(uuid))return "";
        String by_uuid;
        String reason = "";
        String date = "";
        String banned_by = "";
        try {
            PreparedStatement statement = NECore.getMySQL().getConnection().prepareStatement("SELECT * FROM bans WHERE uuid='" + uuid.toString() + "'");
            ResultSet results = statement.executeQuery();
            results.next();
            by_uuid = results.getString("banned_by");
            reason = results.getString("reason");
            date = results.getString("date");
            UUID by = UUID.fromString(by_uuid);
            if (by == NECore.getSettings().consoleUUID) {
                banned_by = NECore.getSettings().consoleDisplay;
            } else {
                banned_by = Bukkit.getOfflinePlayer(UUID.fromString(by_uuid)).getName();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ChatColor.translateAlternateColorCodes('&', "\n&4&l&oBANNED&7&l&o!\n   \n&8&l&oBy&7&l&o: &9&l&o" + banned_by
                + "&r\n&8&l&oReason&7&l&o: " + reason + "&r\n&8&l&oOn&7&l&o: " + date + "&r\n&8&l&oRequest Unban&7&l&o: https://discord.gg/YevukF5");
    }
}