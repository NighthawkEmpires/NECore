package net.nighthawkempires.core;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import de.slikey.effectlib.EffectLib;
import de.slikey.effectlib.EffectManager;
import net.nighthawkempires.core.announcer.AnnouncementManager;
import net.nighthawkempires.core.ban.BanManager;
import net.nighthawkempires.core.bungee.BungeeManager;
import net.nighthawkempires.core.chat.format.ChatFormat;
import net.nighthawkempires.core.chat.tag.NameTag;
import net.nighthawkempires.core.enchantment.EnchantmentManager;
import net.nighthawkempires.core.file.FileFolder;
import net.nighthawkempires.core.file.FileManager;
import net.nighthawkempires.core.kit.KitManager;
import net.nighthawkempires.core.listener.PlayerListener;
import net.nighthawkempires.core.logger.Logger;
import net.nighthawkempires.core.mute.MuteManager;
import net.nighthawkempires.core.recipe.RecipeManager;
import net.nighthawkempires.core.scoreboard.ScoreboardManager;
import net.nighthawkempires.core.scoreboard.def.NameScoreboards;
import net.nighthawkempires.core.settings.Settings;
import net.nighthawkempires.core.sql.MySQL;
import net.nighthawkempires.core.sql.SQLConnector;
import net.nighthawkempires.core.task.SQLTask;
import net.nighthawkempires.core.users.registry.FUserRegistry;
import net.nighthawkempires.core.users.registry.MUserRegistry;
import net.nighthawkempires.core.users.registry.UserRegistry;
import net.nighthawkempires.core.volatilecode.VolatileCodeHandler;
import net.nighthawkempires.core.volatilecode.code.VolatileCodeDisabled;
import net.nighthawkempires.core.volatilecode.code.VolatileCode_v1_12_R1;
import net.nighthawkempires.core.volatilecode.glow.GlowManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.inventivetalent.apihelper.APIManager;

public class NECore extends JavaPlugin {

    private static NECore instance;
    private static Plugin plugin;

    private static AnnouncementManager announcementManager;
    private static BanManager banManager;
    private static BungeeManager bungeeManager;
    private static EffectManager effectManager;
    private static EnchantmentManager enchantmentManager;
    private static FileManager fileManager;
    private static GlowManager glowManager = new GlowManager();
    private static KitManager kitManager;
    private static MuteManager muteManager;
    private static PluginManager pluginManager;
    private static RecipeManager recipeManager;
    private static ScoreboardManager scoreboardManager;

    private static ChatFormat chatFormat;

    private static EffectLib effectLib;

    private static Logger logger;

    private static Settings settings;

    private static VolatileCodeHandler codeHandler;

    private static MySQL sql;
    private static SQLConnector connector;
    private static MongoDatabase mongoDatabase;

    private static UserRegistry userRegistry;

    private BukkitRunnable sqlTask;

    public void onLoad() {
        APIManager.registerAPI(glowManager, this);
    }

    public void onEnable() {
        instance = this;
        plugin = this;

        fileManager = new FileManager();
        settings = new Settings();
        chatFormat = new ChatFormat();
        announcementManager = new AnnouncementManager();
        pluginManager = Bukkit.getPluginManager();
        scoreboardManager = new ScoreboardManager();
        scoreboardManager.addScoreboard(new NameScoreboards());
        enchantmentManager = new EnchantmentManager();
        recipeManager = new RecipeManager();
        banManager = new BanManager();
        bungeeManager = new BungeeManager();
        muteManager = new MuteManager();
        effectLib = EffectLib.instance();
        effectManager = new EffectManager(effectLib);
        glowManager = new GlowManager();
        kitManager = new KitManager();
        logger = new Logger();

        try {
            if (getSettings().mysqlEnabled) {
                sql = new MySQL(getSettings().mysqlHostname, "3306", getSettings().mysqlDatabase, getSettings().mysqlUsername, getSettings().mysqlPassword);
                sql.openConnection();
                connector = new SQLConnector(getSettings().mysqlHostname, getSettings().mysqlDatabase, getSettings().mysqlUsername, getSettings().mysqlPassword);
                connector.getConnection();
            }
        } catch (Exception e) {
            getLoggers().warn("Could not connect to Database.");
        }
        sqlTask = getSQLTask();

        if (NECore.getSettings().mongoEnabledGuilds) {
            try {
                String hostname = NECore.getSettings().mongoHostnameGuilds;
                String username = NECore.getSettings().mongoUsernameGuilds;
                String password = NECore.getSettings().mongoPasswordGuilds;
                ServerAddress address = new ServerAddress(hostname, 27017);
                MongoCredential credential =
                        MongoCredential.createCredential("core", "ne_core", password.toCharArray());
                mongoDatabase =
                        new MongoClient(address, credential, new MongoClientOptions.Builder().build())
                                .getDatabase("ne_core");
                userRegistry = new MUserRegistry(mongoDatabase);
                NECore.getLoggers().info("MongoDB enabled.");
            } catch (Exception oops) {
                oops.printStackTrace();
                NECore.getLoggers().warn("MongoDB connection failed. Disabling plugin.");
                Bukkit.getPluginManager().disablePlugin(this);
                return;
            }
        } else {
            userRegistry = new FUserRegistry(FileFolder.PLAYER_PATH.getPath());
        }

        try {
            Class.forName("net.minecraft.server.v1_12_R1.MinecraftServer");
            codeHandler = new VolatileCode_v1_12_R1();
            getLoggers().info("Volatile Code hooked into v1_12_R1.");
        } catch (Exception e) {
            codeHandler = new VolatileCodeDisabled();
            getLoggers().warn("Volatile Code disabled, compatibility lost.");
        }

        kitManager.loadKits();
        APIManager.initAPI(GlowManager.class);
        getChatFormat().add(new NameTag());
        registerListeners();

        userRegistry.loadAllFromDb();
    }

    public void onDisable() {
        getKitManager().saveKits();
        announcementManager.saveAnnouncements();
        enchantmentManager.unregisterEnchants();
        getMySQL().closeConnection();
    }

    private void registerListeners() {
        getPluginManager().registerEvents(new PlayerListener(), this);
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", bungeeManager);
    }

    private BukkitRunnable getSQLTask() {
        BukkitRunnable runnable = new SQLTask();
        runnable.runTaskTimer(this, 1200, 1200);
        return runnable;
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

    public static ChatFormat getChatFormat() {
        return chatFormat;
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

    public static MuteManager getMuteManager() {
        return muteManager;
    }

    public static KitManager getKitManager() {
        return kitManager;
    }

    public static EnchantmentManager getEnchantmentManager() {
        return enchantmentManager;
    }

    public static RecipeManager getRecipeManager() {
        return recipeManager;
    }

    public static EffectLib getEffectLib() {
        return effectLib;
    }

    public static EffectManager getEffectManager() {
        return effectManager;
    }

    public static GlowManager getGlowManager() {
        return glowManager;
    }

    public static SQLConnector getConnector() {
        return connector;
    }

    public static BungeeManager getBungeeManager() {
        return bungeeManager;
    }

    public static MongoDatabase getMongoDatabase() {
        return mongoDatabase;
    }

    public static UserRegistry getUserRegistry() {
        return userRegistry;
    }
}