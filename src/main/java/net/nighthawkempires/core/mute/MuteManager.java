package net.nighthawkempires.core.mute;

import net.nighthawkempires.core.NECore;
import net.nighthawkempires.core.language.Lang;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class MuteManager {

    public void mute(UUID uuid, String reason, UUID mutedBy) {
        if (isMuted(uuid))return;
        try {
            PreparedStatement statement = NECore.getMySQL().getConnection().prepareStatement("SELECT * FROM mutes WHERE uuid=?");
            statement.setString(1, uuid.toString());
            ResultSet results = statement.executeQuery();
            results.next();
            if (!isMuted(uuid)) {
                PreparedStatement insert = NECore.getMySQL().getConnection().prepareStatement("INSERT INTO mutes(" +
                        "uuid,name,reason,date,muted_by) VALUE (?,?,?,?,?)");
                insert.setString(1, uuid.toString());
                insert.setString(2, Bukkit.getOfflinePlayer(uuid).getName());
                insert.setString(3, reason);
                insert.setString(4, new SimpleDateFormat("MM/dd/yyyy").format(new Date()));
                insert.setString(5, mutedBy.toString());
                insert.executeUpdate();
                NECore.getLoggers().info("Muted Player " + uuid.toString() + ": " + Bukkit.getOfflinePlayer(uuid).getName() + ".");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void unmute(UUID uuid) {
        if (!isMuted(uuid))return;

        try {
            PreparedStatement statement = NECore.getMySQL().getConnection().prepareStatement("DELETE FROM mutes WHERE uuid=? limit 1");
            statement.setString(1, uuid.toString());
            statement.execute();
            NECore.getLoggers().info("Unmuted Player " + uuid.toString() + ": " + Bukkit.getOfflinePlayer(uuid).getName() + ".");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isMuted(UUID uuid) {
        try {
            PreparedStatement statement = NECore.getMySQL().getConnection().prepareStatement("SELECT * FROM mutes WHERE UUID=?");
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

    public String getMuteInfo(UUID uuid) {
        if (!isMuted(uuid))return "";
        String by_uuid = "";
        String reason = "";
        String date = "";
        String muted_by = "";
        try {
            PreparedStatement statement = NECore.getMySQL().getConnection().prepareStatement("SELECT * FROM mutes WHERE uuid='" + uuid.toString() + "'");
            ResultSet results = statement.executeQuery();
            results.next();
            by_uuid = results.getString("muted_by");
            reason = results.getString("reason");
            date = results.getString("date");
            UUID by = UUID.fromString(by_uuid);
            if (by == NECore.getSettings().consoleUUID) {
                muted_by = NECore.getSettings().consoleDisplay;
            } else {
                muted_by = Bukkit.getOfflinePlayer(UUID.fromString(by_uuid)).getName();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "You were muted by " + ChatColor.BLUE + muted_by + ChatColor.GRAY + " for: "
                + ChatColor.RED + reason + ChatColor.GRAY + ". \n" + Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "To request to be unmuted join our Discord and fill out your request accordingly https://discord.gg/YevukF5";
    }
}
