package net.nighthawkempires.core.users;

import com.google.common.collect.Lists;
import net.nighthawkempires.core.NECore;
import net.nighthawkempires.core.file.FileManager;
import net.nighthawkempires.core.file.FileType;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
        getUser().setName(Bukkit.getOfflinePlayer(getUser().getUUID()).getName());
        getUser().setDisplayName(getPlayerFile().getString("display-name", getUser().getName()));
        getUser().setJoinDate(getPlayerFile().getString("join-date", new SimpleDateFormat("MM/dd/yyyy").format(new Date())));
        getUser().setServers((getPlayerFile().isSet("servers") ? getPlayerFile().getStringList("servers") : Lists.newArrayList()));
        getUser().setAddress((Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(getUser().getUUID())) ? Bukkit.getPlayer(getUser().getUUID()).getAddress().toString()
                : getPlayerFile().getString("address", "")));
        getUser().setBalance(getPlayerFile().getDouble("balance"));
        getUser().setDeaths(getPlayerFile().getInt("deaths"));
        getUser().setKills(getPlayerFile().getInt("kills"));
        getUser().setTokens(getPlayerFile().getInt("tokens"));
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
