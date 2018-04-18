package net.nighthawkempires.core.settings;

import com.google.common.collect.ImmutableList;
import com.mongodb.DB;
import net.nighthawkempires.core.NECore;
import net.nighthawkempires.core.database.DatabaseType;
import net.nighthawkempires.core.file.FileType;
import net.nighthawkempires.core.server.Server;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.UUID;

public class Settings {

    public Server SERVER;
    public UUID CON_UUID;
    public String CON_DISPLAY;
    public ImmutableList<String> BANNED_WORDS;

    public boolean DB_USE;
    public DatabaseType DB_TYPE;
    public String DB_HOSTNAME;
    public int DB_PORT;
    public String DB_DATABASE;
    public String DB_USERNAME;
    public String DB_PASSWORD;

    public Settings() {
        SERVER = Server.valueOf(getConfig().getString("SERVER_TYPE"));

        CON_UUID = UUID.fromString(getConfig().getString("CON_UUID"));
        CON_DISPLAY = ChatColor.translateAlternateColorCodes('&', getConfig().getString("CON_DISPLAY"));

        BANNED_WORDS = ImmutableList.copyOf(getConfig().getStringList("BANNED_WORDS"));

        DB_USE = getConfig().getBoolean("USE_DATABASE");
        if (DB_USE) {
            DB_TYPE = DatabaseType.valueOf(getConfig().getString("DB_TYPE"));
            DB_HOSTNAME = getConfig().getString("DB_HOSTNAME");
            DB_PORT = getConfig().getInt("DB_PORT");
            DB_DATABASE = getConfig().getString("DB_DATABASE");
            DB_USERNAME = getConfig().getString("DB_USERNAME");
            DB_PASSWORD = getConfig().getString("DB_PASSWORD");
        } else {
            DB_TYPE = DatabaseType.FILE;
            DB_HOSTNAME = "null";
            DB_PORT = 0;
            DB_DATABASE = "null";
            DB_USERNAME = "null";
            DB_PASSWORD = "null";
        }
    }

    private FileConfiguration getConfig() {
        return NECore.getFileManager().get(FileType.CONFIG);
    }
}
