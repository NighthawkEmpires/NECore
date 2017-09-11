package net.nighthawkempires.core;

import net.nighthawkempires.core.announcer.AnnouncementManager;
import net.nighthawkempires.core.ban.BanManager;
import net.nighthawkempires.core.chat.ChatManager;
import net.nighthawkempires.core.chat.format.ChatFormat;
import net.nighthawkempires.core.chat.tag.NameTag;
import net.nighthawkempires.core.chat.tag.ServerTag;
import net.nighthawkempires.core.file.FileManager;
import net.nighthawkempires.core.listener.PlayerListener;
import net.nighthawkempires.core.listener.PluginListener;
import net.nighthawkempires.core.logger.Logger;
import net.nighthawkempires.core.scoreboard.ScoreboardManager;
import net.nighthawkempires.core.settings.Settings;
import net.nighthawkempires.core.sql.MySQL;
import net.nighthawkempires.core.users.UserManager;
import net.nighthawkempires.core.volatilecode.VolatileCodeHandler;
import net.nighthawkempires.core.volatilecode.code.VolatileCodeDisabled;
import net.nighthawkempires.core.volatilecode.code.VolatileCode_v1_12_R1;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class NECore extends JavaPlugin {

    private static NECore instance;
    private static Plugin plugin;
    private static FileManager fileManager;
    private static Settings settings;
    private static ChatManager chatManager;
    private static ChatFormat chatFormat;
    private static RedisCore redisCore;
    private static UserManager userManager;
    private static AnnouncementManager announcementManager;
    private static PluginManager pluginManager;
    private static ScoreboardManager scoreboardManager;
    private static BanManager banManager;
    private static VolatileCodeHandler codeHandler;
    private static MySQL sql;
    private static Logger logger;

    public void onEnable() {
        instance = this;
        plugin = this;
        fileManager = new FileManager();
        settings = new Settings();
        chatManager = new ChatManager();
        chatFormat = new ChatFormat();
        redisCore = (getSettings().useRedis ? new RedisCore(this) : null);
        userManager = new UserManager();
        announcementManager = new AnnouncementManager();
        pluginManager = Bukkit.getPluginManager();
        scoreboardManager = new ScoreboardManager();
        banManager = new BanManager();
        logger = new Logger();

        try {
            Class.forName("net.minecraft.server.v1_12_R1.MinecraftServer");
            codeHandler = new VolatileCode_v1_12_R1();
            getLoggers().info("Volatile Code hooked into v1_12_R1.");
        } catch (Exception e) {
            codeHandler = new VolatileCodeDisabled();
            getLoggers().warn("Volatile Code disabled, compatibility lost.");
        }

        getChatFormat().add(new ServerTag()).add(new NameTag());

        registerListeners();

        try {
            if (getSettings().useSQL) {
                sql = new MySQL(getSettings().sqlHostname, getSettings().sqlPort, getSettings().sqlDatabase, getSettings().sqlUsername, getSettings().sqlPassword);
                sql.openConnection();
            }
        } catch (Exception e) {
            getLoggers().warn("Could not connect to MySQL.");
        }
    }

    public void onDisable() {
        announcementManager.saveAnnouncements();
        getFileManager().saveFiles();
        getMySQL().closeConnection();
    }

    public void registerListeners() {
        getPluginManager().registerEvents(new PlayerListener(), this);
        getPluginManager().registerEvents(new PluginListener(), this);
    }

    public static NECore getInstance() {
        return instance;
    }

    public static Plugin getPlugin() {
        return plugin;
    }

    public static FileManager getFileManager() {
        return fileManager;
    }

    public static Settings getSettings() {
        return settings;
    }

    public static ChatManager getChatManager() {
        return chatManager;
    }

    public static ChatFormat getChatFormat() {
        return chatFormat;
    }

    public static RedisCore getRedisCore() {
        return redisCore;
    }

    public static UserManager getUserManager() {
        return userManager;
    }

    public static AnnouncementManager getAnnouncementManager() {
        return announcementManager;
    }

    public static PluginManager getPluginManager() {
        return pluginManager;
    }

    public static ScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }

    public static BanManager getBanManager() {
        return banManager;
    }

    public static VolatileCodeHandler getCodeHandler() {
        return codeHandler;
    }

    public static MySQL getMySQL() {
        return sql;
    }

    public static Logger getLoggers() {
        return logger;
    }
}
