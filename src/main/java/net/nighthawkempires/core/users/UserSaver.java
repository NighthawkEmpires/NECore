package net.nighthawkempires.core.users;

import net.nighthawkempires.core.NECore;
import net.nighthawkempires.core.file.FileManager;
import net.nighthawkempires.core.file.FileType;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import static net.nighthawkempires.core.NECore.getFileManager;
import static net.nighthawkempires.core.NECore.getUserManager;

public class UserSaver {

    private User user;
    private FileManager fileManager;

    public UserSaver(User user) {
        this.user = user;
        this.fileManager = getFileManager();
    }

    public User getUser() {
        return user;
    }

    public void save() {
        if (NECore.getSettings().useSQL) {
            try {
                PreparedStatement statement = NECore.getMySQL().getConnection().prepareStatement("UPDATE global_data SET name=?,display_name=?,join_date=?,address=?,hub=?,survival=?,tokens=?,prison=?,freebuild=?,minigames=?,test=? WHERE uuid=?");
                statement.setString(1, Bukkit.getOfflinePlayer(getUser().getUUID()).getName());
                if (!Bukkit.getOfflinePlayer(getUser().getUUID()).getName().equals(getUser().getDisplayName())) {
                    statement.setString(2, getUser().getDisplayName());
                } else {
                    statement.setString(2, "");
                }
                statement.setString(3, getUser().getJoinDate());
                statement.setString(4, getUser().getAddress());
                statement.setString(5, String.valueOf(getUser().hub()));
                statement.setString(6, String.valueOf(getUser().sur()));
                statement.setInt(7, getUser().getTokens());
                statement.setString(8, String.valueOf(getUser().prs()));
                statement.setString(9, String.valueOf(getUser().frb()));
                statement.setString(10, String.valueOf(getUser().min()));
                statement.setString(11, String.valueOf(getUser().test()));
                statement.setString(12, getUser().getUUID().toString());
                statement.executeUpdate();
                NECore.getLoggers().info("Saved User " + getUser().getUUID().toString() + ": " + Bukkit.getOfflinePlayer(getUser().getUUID()).getName() + ".");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            getPlayerFile().set("name", Bukkit.getOfflinePlayer(getUser().getUUID()).getName());
            getPlayerFile().set("display-name", getUser().getDisplayName());
            getPlayerFile().set("join-date", getUser().getJoinDate());
            getPlayerFile().set("address", (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(user.getUUID()))
                    ? Bukkit.getPlayer(user.getUUID()).getAddress().toString() : ""));
            getPlayerFile().set("tokens", getUser().getTokens());
            savePlayerFile(true);
        }
    }

    public FileConfiguration getPlayerFile() {
        savePlayerFile(true);
        if (!fileManager.isFileLoaded(user.getUUID().toString())) {
            fileManager.loadFile(user.getUUID().toString(), FileType.PLAYER_FILE);
        }
        return fileManager.getFile(user.getUUID().toString());
    }

    public void savePlayerFile(boolean clear) {
        fileManager.saveFile(user.getUUID().toString(), clear);
    }
}
