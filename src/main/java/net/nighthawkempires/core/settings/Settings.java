package net.nighthawkempires.core.settings;

import net.nighthawkempires.core.NECore;
import net.nighthawkempires.core.server.Server;
import org.bukkit.configuration.file.FileConfiguration;

public class Settings {

    public Server server;

    public boolean useRedis;
    public String redisServerId;
    public String redisChannel;
    public String redisAddress;

    public boolean useSQL;
    public String sqlHostname;
    public String sqlPort;
    public String sqlDatabase;
    public String sqlUsername;
    public String sqlPassword;

    public Settings() {
        server = Server.valueOf(getConfig().getString("server"));

        useRedis = getConfig().getBoolean("redis.use", false);
        redisServerId = getConfig().getString("redis.server_id", "minecraft");
        redisChannel = getConfig().getString("redis.channel", "default");
        redisAddress = getConfig().getString("redis.address", "127.0.0.1:6379");

        useSQL = getConfig().getBoolean("sql.use", false);
        sqlHostname = getConfig().getString("sql.hostname", "localhost");
        sqlPort = getConfig().getString("sql.port", "3306");
        sqlDatabase = getConfig().getString("sql.database", "database");
        sqlUsername = getConfig().getString("sql.username", "root");
        sqlPassword = getConfig().getString("sql.password", "pa$$w0rd");
    }

    private FileConfiguration getConfig() {
        return NECore.getFileManager().getConfig();
    }
}
