package net.nighthawkempires.core.users;

import net.nighthawkempires.core.NECore;
import net.nighthawkempires.core.file.FileManager;
import net.nighthawkempires.core.file.FileType;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserLoader {

    private User user;
    private FileManager fileManager;

    public UserLoader(User user) {
        this.user = user;
        this.fileManager = NECore.getFileManager();
    }

    public User getUser() {
        return user;
    }

    public void load() {
        if (NECore.getSettings().useSQL) {
            try {
                NECore.getMySQL().checkConnection();
                PreparedStatement statement = NECore.getMySQL().getConnection().prepareStatement("SELECT * FROM global_data WHERE uuid='" + getUser().getUUID().toString() + "'");
                ResultSet results = statement.executeQuery();
                results.next();
                getUser().setName(Bukkit.getOfflinePlayer(getUser().getUUID()).getName());
                getUser().setDisplayName(results.getString("display_name"));
                if (getUser().getDisplayName().equals("") || getUser().getDisplayName().equals(null)) {
                    getUser().setDisplayName(getUser().getName());
                }
                getUser().setJoinDate(results.getString("join_date"));
                getUser().setAddress((Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(getUser().getUUID())) ? Bukkit.getPlayer(getUser().getUUID()).getAddress().toString() : results.getString("address")));
                getUser().setHub(Boolean.valueOf(results.getString("hub")));
                getUser().setSur(Boolean.valueOf(results.getString("survival")));
                getUser().setTokens(results.getInt("tokens"));
                try {
                    getUser().setPrs(Boolean.valueOf(results.getString("prison")));
                    getUser().setFrb(Boolean.valueOf(results.getString("freebuild")));
                    getUser().setMin(Boolean.valueOf(results.getString("minigames")));
                    getUser().setTest(Boolean.valueOf(results.getString("test")));
                } catch (Exception e) {
                    getUser().setPrs(false);
                    getUser().setFrb(false);
                    getUser().setMin(false);
                    getUser().setTest(false);
                }
                NECore.getLoggers().info("Loaded User " + getUser().getUUID().toString() + ": " + Bukkit.getOfflinePlayer(getUser().getUUID()).getName() + ".");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            getUser().setName(Bukkit.getOfflinePlayer(getUser().getUUID()).getName());
            getUser().setDisplayName(getPlayerFile().getString("display-name", getUser().getName()));
            getUser().setJoinDate(getPlayerFile().getString("join-date", new SimpleDateFormat("MM/dd/yyyy").format(new Date())));
            getUser().setAddress((Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(getUser().getUUID())) ? Bukkit.getPlayer(getUser().getUUID()).getAddress().toString()
                    : getPlayerFile().getString("address", "")));
            getUser().setTokens(getPlayerFile().getInt("tokens"));
        }
    }

    public FileConfiguration getPlayerFile() {
        if (!fileManager.isFileLoaded(user.getUUID().toString())) {
            fileManager.loadFile(user.getUUID().toString(), FileType.PLAYER_FILE);
        }
        return fileManager.getFile(user.getUUID().toString());
    }

    public void savePlayerFile() {
        fileManager.saveFile(user.getUUID().toString(), false);
    }
}
