package net.nighthawkempires.core;

import com.mongodb.*;
import com.mongodb.client.MongoDatabase;
import de.slikey.effectlib.EffectLib;
import de.slikey.effectlib.EffectManager;
import net.nighthawkempires.core.announcer.AnnouncementManager;
import net.nighthawkempires.core.ban.registry.BanRegistry;
import net.nighthawkempires.core.ban.registry.FBanRegistry;
import net.nighthawkempires.core.ban.registry.MBanRegistry;
import net.nighthawkempires.core.bungee.BungeeManager;
import net.nighthawkempires.core.chat.format.ChatFormat;
import net.nighthawkempires.core.chat.tag.NameTag;
import net.nighthawkempires.core.database.DatabaseType;
import net.nighthawkempires.core.enchantment.EnchantmentManager;
import net.nighthawkempires.core.file.FileFolder;
import net.nighthawkempires.core.file.FileManager;
import net.nighthawkempires.core.inventory.InventoryManager;
import net.nighthawkempires.core.kit.KitManager;
import net.nighthawkempires.core.listener.PlayerListener;
import net.nighthawkempires.core.logger.LogManager;
import net.nighthawkempires.core.mute.registry.FMuteRegistry;
import net.nighthawkempires.core.mute.registry.MMuteRegistry;
import net.nighthawkempires.core.mute.registry.MuteRegistry;
import net.nighthawkempires.core.recipe.RecipeManager;
import net.nighthawkempires.core.scoreboard.ScoreboardManager;
import net.nighthawkempires.core.scoreboard.def.NameScoreboards;
import net.nighthawkempires.core.settings.Settings;
import net.nighthawkempires.core.sql.SQLConnector;
import net.nighthawkempires.core.task.HeartbeatTask;
import net.nighthawkempires.core.users.registry.*;
import net.nighthawkempires.core.volatilecode.VolatileCodeHandler;
import net.nighthawkempires.core.volatilecode.code.VolatileCodeDisabled;
import net.nighthawkempires.core.volatilecode.code.VolatileCode_v1_12_R1;
import net.nighthawkempires.core.volatilecode.glow.GlowManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.inventivetalent.apihelper.APIManager;

public class NECore extends JavaPlugin {

    private static NECore instance;
    private static Plugin plugin;

    private static AnnouncementManager announcementManager;
    private static BungeeManager bungeeManager;
    private static EffectManager effectManager;
    private static EnchantmentManager enchantmentManager;
    private static FileManager fileManager;
    private static GlowManager glowManager = new GlowManager();
    private static InventoryManager inventoryManager;
    private static KitManager kitManager;
    private static PluginManager pluginManager;
    private static RecipeManager recipeManager;
    private static ScoreboardManager scoreboardManager;

    private static ChatFormat chatFormat;

    private static EffectLib effectLib;

    private static Settings settings;

    private static VolatileCodeHandler codeHandler;

    private static SQLConnector connector;
    private static MongoDatabase mongoDatabase;

    private static UserRegistry userRegistry;
    private static BanRegistry banRegistry;
    private static MuteRegistry muteRegistry;

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
        inventoryManager = new InventoryManager();
        recipeManager = new RecipeManager();
        bungeeManager = new BungeeManager();
        effectLib = EffectLib.instance();
        effectManager = new EffectManager(effectLib);
        glowManager = new GlowManager();
        kitManager = new KitManager();

        /**try {
            if (getSettings().mysqlEnabled) {
                connector = new SQLConnector(getSettings().mysqlHostname, getSettings().mysqlDatabase,
                        getSettings().mysqlUsername, getSettings().mysqlPassword);
                connector.getConnection();
            }
        } catch (Exception e) {
            getLoggers().warn("Could not connect to Database.");
        }
        sqlTask = getHeartbeatTask();*/

        if (NECore.getSettings().DB_USE) {
            String pluginName = getPlugin().getName().toLowerCase().replaceFirst("NE", "");
            if (NECore.getSettings().DB_TYPE == DatabaseType.MONGO) {
                try {
                    String hostname = NECore.getSettings().DB_HOSTNAME;
                    int port = NECore.getSettings().DB_PORT;
                    String database = NECore.getSettings().DB_DATABASE.replaceAll("%PLUGIN%", pluginName.toLowerCase());
                    String username = NECore.getSettings().DB_USERNAME.replaceAll("%PLUGIN%", pluginName.toLowerCase());
                    String password = NECore.getSettings().DB_PASSWORD;

                    ServerAddress address = new ServerAddress(hostname, 27017);
                    MongoCredential credential =
                            MongoCredential.createCredential(username, database, password.toCharArray());
                    mongoDatabase =
                            new MongoClient(address, credential, new MongoClientOptions.Builder().build()).getDatabase(database);
                    userRegistry = new MUserRegistry(mongoDatabase);
                    banRegistry = new MBanRegistry(mongoDatabase);
                    muteRegistry = new MMuteRegistry(mongoDatabase);
                    LogManager.info(this, "MongoDB enabled.");
                } catch (Exception e) {
                    e.printStackTrace();
                    LogManager.warn("MongoDB connection failed. Disabling plugin.");
                    Bukkit.getPluginManager().disablePlugin(this);
                    return;
                }
            } else if (NECore.getSettings().DB_TYPE == DatabaseType.MYSQL) {
                //TODO: Integrate MySQL
            } else {
                userRegistry = new FUserRegistry(FileFolder.PLAYER_PATH.getPath());
                banRegistry = new FBanRegistry(FileFolder.BAN_PATH.getPath());
                muteRegistry = new FMuteRegistry(FileFolder.MUTE_PATH.getPath());
            }
        } else {
            userRegistry = new FUserRegistry(FileFolder.PLAYER_PATH.getPath());
            banRegistry = new FBanRegistry(FileFolder.BAN_PATH.getPath());
            muteRegistry = new FMuteRegistry(FileFolder.MUTE_PATH.getPath());
        }

        try {
            Class.forName("net.minecraft.server.v1_12_R1.MinecraftServer");
            codeHandler = new VolatileCode_v1_12_R1();
            LogManager.info("Volatile Code hooked into v1_12_R1.");
        } catch (Exception e) {
            codeHandler = new VolatileCodeDisabled();
            LogManager.warn("Volatile Code disabled, compatibility lost.");
        }

        kitManager.loadKits();
        APIManager.initAPI(GlowManager.class);
        getChatFormat().add(new NameTag());
        registerListeners();

        userRegistry.loadAllFromDb();
        banRegistry.loadAllFromDb();
        muteRegistry.loadAllFromDb();
    }

    public void onDisable() {
        getKitManager().saveKits();
        announcementManager.saveAnnouncements();
        enchantmentManager.unregisterEnchants();
    }

    private void registerListeners() {
        getPluginManager().registerEvents(new PlayerListener(), this);
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", bungeeManager);
    }

    private BukkitRunnable getHeartbeatTask() {
        BukkitRunnable runnable = new HeartbeatTask();
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

    public static VolatileCodeHandler getCodeHandler() {
        return codeHandler;
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

    public static BanRegistry getBanRegistry() {
        return banRegistry;
    }

    public static MuteRegistry getMuteRegistry() {
        return muteRegistry;
    }
}