package net.nighthawkempires.core;

import de.slikey.effectlib.EffectLib;
import de.slikey.effectlib.EffectManager;
import net.nighthawkempires.core.announcer.AnnouncementManager;
import net.nighthawkempires.core.ban.BanManager;
import net.nighthawkempires.core.chat.format.ChatFormat;
import net.nighthawkempires.core.chat.tag.NameTag;
import net.nighthawkempires.core.enchantment.EnchantmentManager;
import net.nighthawkempires.core.file.FileManager;
import net.nighthawkempires.core.kit.KitManager;
import net.nighthawkempires.core.listener.PlayerListener;
import net.nighthawkempires.core.listener.PluginListener;
import net.nighthawkempires.core.logger.Logger;
import net.nighthawkempires.core.mute.MuteManager;
import net.nighthawkempires.core.recipe.RecipeManager;
import net.nighthawkempires.core.scoreboard.ScoreboardManager;
import net.nighthawkempires.core.scoreboard.def.NameScoreboards;
import net.nighthawkempires.core.settings.Settings;
import net.nighthawkempires.core.sql.MySQL;
import net.nighthawkempires.core.users.UserManager;
import net.nighthawkempires.core.volatilecode.VolatileCodeHandler;
import net.nighthawkempires.core.volatilecode.code.VolatileCodeDisabled;
import net.nighthawkempires.core.volatilecode.code.VolatileCode_v1_12_R1;
import net.nighthawkempires.core.volatilecode.glow.GlowManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.inventivetalent.apihelper.APIManager;

public class NECore extends JavaPlugin {

    private static NECore instance;
    private static Plugin plugin;
    private static FileManager fileManager;
    private static Settings settings;
    private static ChatFormat chatFormat;
    private static UserManager userManager;
    private static AnnouncementManager announcementManager;
    private static PluginManager pluginManager;
    private static ScoreboardManager scoreboardManager;
    private static EnchantmentManager enchantmentManager;
    private static BanManager banManager;
    private static MuteManager muteManager;
    private static VolatileCodeHandler codeHandler;
    private static RecipeManager recipeManager;
    private static KitManager kitManager;
    private static EffectLib effectLib;
    private static EffectManager effectManager;
    private static GlowManager glowManager = new GlowManager();
    private static MySQL sql;
    private static Logger logger;

    public void onLoad() {
        APIManager.registerAPI(glowManager, this);
    }

    public void onEnable() {
        instance = this;
        plugin = this;
        fileManager = new FileManager();
        settings = new Settings();
        chatFormat = new ChatFormat();
        userManager = new UserManager();
        announcementManager = new AnnouncementManager();
        pluginManager = Bukkit.getPluginManager();
        scoreboardManager = new ScoreboardManager();
        scoreboardManager.addScoreboard(new NameScoreboards());
        enchantmentManager = new EnchantmentManager();
        recipeManager = new RecipeManager();
        banManager = new BanManager();
        muteManager = new MuteManager();
        effectLib = EffectLib.instance();
        effectManager = new EffectManager(effectLib);
        glowManager = new GlowManager();
        kitManager = new KitManager();
        logger = new Logger();

        try {
            if (getSettings().useSQL) {
                sql = new MySQL(getSettings().sqlHostname, getSettings().sqlPort, getSettings().sqlDatabase, getSettings().sqlUsername, getSettings().sqlPassword);
                sql.openConnection();
            }
        } catch (Exception e) {
            getLoggers().warn("Could not connect to Database.");
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
    }

    public void onDisable() {
        getKitManager().saveKits();
        announcementManager.saveAnnouncements();
        enchantmentManager.unregisterEnchants();
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

    public static ChatFormat getChatFormat() {
        return chatFormat;
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
}