package net.nighthawkempires.core.users;

import net.nighthawkempires.core.NECore;
import net.nighthawkempires.core.file.FileManager;
import net.nighthawkempires.core.file.FileType;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import static net.nighthawkempires.core.NECore.getFileManager;

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
        getPlayerFile().set("name", Bukkit.getOfflinePlayer(getUser().getUUID()).getName());
        getPlayerFile().set("display-name", getUser().getDisplayName());
        getPlayerFile().set("join-date", getUser().getJoinDate());
        getPlayerFile().set("servers", getUser().getServers());
        getPlayerFile().set("address", (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(user.getUUID()))
                ? Bukkit.getPlayer(user.getUUID()).getAddress().toString() : ""));
        getPlayerFile().set("balance", getUser().getBalance());
        getPlayerFile().set("deaths", getUser().getDeaths());
        getPlayerFile().set("kills", getUser().getKills());
        getPlayerFile().set("tokens", getUser().getTokens());
        savePlayerFile();
    }

    public FileConfiguration getPlayerFile() {
        savePlayerFile();
        if (!fileManager.isFileLoaded(user.getUUID().toString())) {
            fileManager.loadFile(user.getUUID().toString(), FileType.PLAYER_FILE);
        }
        return fileManager.getFile(user.getUUID().toString());
    }

    public void savePlayerFile() {
        fileManager.saveFile(user.getUUID().toString(), false);
    }
}
