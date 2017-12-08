package net.nighthawkempires.core.users;

import com.google.common.collect.Lists;
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

public class UserCreator {

    private User user;
    private FileManager fileManager;

    public UserCreator(User user) {
        this.user = user;
        this.fileManager = NECore.getFileManager();
    }

    public User getUser() {
        return user;
    }

    public void create() {
        if (NECore.getSettings().useSQL) {
            try {
                PreparedStatement statement = NECore.getMySQL().getConnection().prepareStatement("SELECT * FROM global_data WHERE uuid=?");
                statement.setString(1, getUser().getUUID().toString());
                ResultSet results = statement.executeQuery();
                results.next();
                if (!NECore.getUserManager().userExists(getUser().getUUID())) {
                    PreparedStatement insert = NECore.getMySQL().getConnection().prepareStatement("INSERT INTO global_data(" +
                            "uuid,name,display_name,join_date,address,hub,survival,tokens,prison,freebuild,minigames,test) VALUE (?,?,?,?,?,?,?,?,?,?,?,?)");
                    insert.setString(1, getUser().getUUID().toString());
                    insert.setString(2, Bukkit.getOfflinePlayer(getUser().getUUID()).getName());
                    insert.setString(3, Bukkit.getOfflinePlayer(getUser().getUUID()).getName());
                    insert.setString(4, new SimpleDateFormat("MM/dd/yyyy").format(new Date()));
                    insert.setString(5, (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(getUser().getUUID())) ? Bukkit.getPlayer(getUser().getUUID()).getAddress().toString() : ""));
                    insert.setString(6, "false");
                    insert.setString(7, "false");
                    insert.setInt(8, 10);
                    insert.setString(9, "false");
                    insert.setString(10, "false");
                    insert.setString(11,"false");
                    insert.setString(12, "fasle");
                    insert.executeUpdate();
                    NECore.getLoggers().info("Created User " + getUser().getUUID().toString() + ": " + Bukkit.getOfflinePlayer(getUser().getUUID()).getName() + ".");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            getPlayerFile().set("name", Bukkit.getOfflinePlayer(getUser().getUUID()).getName());
            getPlayerFile().set("address", (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(user.getUUID()))
                    ? Bukkit.getPlayer(user.getUUID()).getAddress().toString() : ""));
            getPlayerFile().set("tokens", 10);
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
