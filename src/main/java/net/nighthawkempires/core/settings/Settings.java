package net.nighthawkempires.core.settings;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import net.nighthawkempires.core.NECore;
import net.nighthawkempires.core.file.FileType;
import net.nighthawkempires.core.server.Server;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.UUID;

public class Settings {

    public Server server;
    public UUID consoleUUID;
    public String consoleDisplay;
    public ImmutableList<String> bannedWords;

    public boolean mongoEnabled;
    public String mongoHostname;
    public String mongoDatabase;
    public String mongoUsername;
    public String mongoPassword;

    public boolean mysqlEnabled;
    public String mysqlHostname;
    public String mysqlDatabase;
    public String mysqlUsername;
    public String mysqlPassword;

    public boolean redisEnabled;
    public String redisHostname;
    public String redisServerId;
    public String redisChannel;

    public boolean mongoEnabledGuilds;
    public String mongoHostnameGuilds;
    public String mongoDatabaseGuilds;
    public String mongoUsernameGuilds;
    public String mongoPasswordGuilds;

    public Settings() {
        server = Server.valueOf(getConfig().getString("settings.server", "HUB"));
        consoleUUID = UUID.fromString(getConfig().getString("settings.console.uuid"));
        consoleDisplay = ChatColor.translateAlternateColorCodes('&', getConfig().getString("settings.console.display-name"));
        bannedWords = ImmutableList.copyOf(getConfig().getStringList("settings.banned-words"));

        mongoEnabled = getConfig().getBoolean("databases.mongodb.enabled", false);
        if (mongoEnabled) {
            mongoHostname = getConfig().getString("databases.mongodb.hostname");
            mongoDatabase = getConfig().getString("databases.mongodb.database");
            mongoUsername = getConfig().getString("databases.mongodb.username");
            mongoPassword = getConfig().getString("databases.mongodb.password");
        }

        mysqlEnabled = getConfig().getBoolean("databases.mysql.enabled", false);
        if (mysqlEnabled) {
            mysqlHostname = getConfig().getString("databases.mysql.hostname");
            mysqlDatabase = getConfig().getString("databases.mysql.database");
            mysqlUsername = getConfig().getString("databases.mysql.username");
            mysqlPassword = getConfig().getString("databases.mysql.password");
        }

        redisEnabled = getConfig().getBoolean("databases.redis.enabled", false);
        if (redisEnabled) {
            redisHostname = getConfig().getString("databases.redis.hostname");
            redisServerId = getConfig().getString("databases.redis.server-id");
            redisChannel = getConfig().getString("databases.redis.channel");
        }

        mongoEnabledGuilds = getConfig().getBoolean("databases.mongodb.enabled", false);
        if (mongoEnabled) {
            mongoHostnameGuilds = getConfig().getString("guilds.databases.mongodb.hostname");
            mongoDatabaseGuilds = getConfig().getString("guilds.databases.mongodb.database");
            mongoUsernameGuilds = getConfig().getString("guilds.databases.mongodb.username");
            mongoPasswordGuilds = getConfig().getString("guilds.databases.mongodb.password");
        }
    }

    private FileConfiguration getConfig() {
        return NECore.getFileManager().get(FileType.CONFIG);
    }
}
