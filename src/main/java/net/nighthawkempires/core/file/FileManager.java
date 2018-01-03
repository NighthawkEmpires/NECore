package net.nighthawkempires.core.file;

import com.google.common.collect.Maps;
import net.nighthawkempires.core.NECore;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.ConcurrentMap;

public class FileManager {

    private ConcurrentMap<String, FileConfiguration> configMap;
    private ConcurrentMap<FileConfiguration, File> fileMap;

    private File announcement_file;
    private FileConfiguration announcement;
    private File ban_file;
    private FileConfiguration ban;
    private File config_file;
    private FileConfiguration config;
    private File group_file;
    private FileConfiguration group;
    private File homes_file;
    private FileConfiguration homes;
    private File location_file;
    private FileConfiguration location;
    private File kits_file;
    private FileConfiguration kits;
    private File portals_file;
    private FileConfiguration portals;
    private File regions_file;
    private FileConfiguration regions;
    private File shops_file;
    private FileConfiguration shops;
    private File dir;
    private File guild_dir;

    public FileManager() {
        configMap = Maps.newConcurrentMap();
        fileMap = Maps.newConcurrentMap();

        guild_dir = new File(FileType.GUILD_FILE.getPath());
        loadFiles();
    }

    public void loadFiles() {
        loadAnnouncementFile();
        loadBanFile();
        loadConfig();
        loadGroupFile();
        loadHomesFile();
        loadLocationFile();
        loadKitsFile();
        loadPortalsFile();
        loadRegionsFile();
        loadShopsFile();
    }

    public void saveFiles() {
        try {
            saveAnnouncementFile();
            saveBanFile();
            saveConfig();
            saveGroupFile();
            saveHomesFile();
            saveLocationFile();
            saveKitsFile();
            savePortalsFile();
            saveRegionsFile();
            saveShopsFile();
        } catch (Exception ignored) {}
    }

    public File getGuildDirectory() {
        return guild_dir;
    }

    public void loadAnnouncementFile() {
        try {
            announcement_file = new File(FileType.CONFIG_FILE.getPath() + "announcements.yml");

            dir = new File(announcement_file.getParentFile().getAbsolutePath());
            dir.mkdirs();

            if (!announcement_file.exists()) {
                announcement_file.createNewFile();
            }

            announcement = YamlConfiguration.loadConfiguration(announcement_file);

            getAnnouncementFile().options().copyDefaults(true);
            getAnnouncementFile().set("settings.enable", true);
            getAnnouncementFile().set("settings.delay", 60);
            saveAnnouncementFile();

        } catch (Exception ignored) {}
    }

    public FileConfiguration getAnnouncementFile() {
        return announcement;
    }

    public void saveAnnouncementFile() {
        try {
            getAnnouncementFile().save(announcement_file);
        } catch (Exception ignored) {}
    }

    public void loadBanFile() {
        try {
            ban_file = new File(FileType.CONFIG_FILE.getPath() + "bans.yml");

            dir = new File(ban_file.getParentFile().getAbsolutePath());
            dir.mkdirs();

            if (!ban_file.exists()) {
                ban_file.createNewFile();
            }

            ban = YamlConfiguration.loadConfiguration(ban_file);


        } catch (Exception ignored) {}
    }

    public FileConfiguration getBanFile() {
        return ban;
    }

    public void saveBanFile() {
        try {
            getBanFile().save(ban_file);
        } catch (Exception ignored) {}
    }

    public void loadConfig() {
        try {
            config_file = new File(FileType.CONFIG_FILE.getPath() + "config.yml");

            dir = new File(config_file.getParentFile().getAbsolutePath());
            dir.mkdirs();

            if (!config_file.exists()) {
                config_file.createNewFile();
            }

            config = YamlConfiguration.loadConfiguration(config_file);

            config.options().copyDefaults(true);
            config.addDefault("redis.use", false);
            config.addDefault("redis.server_id", "minecraft");
            config.addDefault("redis.channel", "default");
            config.addDefault("redis.address", "127.0.0.1:6379");
            config.addDefault("sql.use", false);
            config.addDefault("sql.hostname", "minecraft");
            config.addDefault("sql.port", 3306);
            config.addDefault("sql.database", "database");
            config.addDefault("sql.username", "username");
            config.addDefault("sql.password", "password");
            config.addDefault("#servers", "HUB, SUR, FRB, PRS, MIN, TEST");
            config.addDefault("server", "HUB");
            saveConfig();

        } catch (Exception ignored) {}
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void saveConfig() {
        try {
            getConfig().save(config_file);
        } catch (Exception ignored) {}
    }

    public void loadGroupFile() {
        try {
            group_file = new File(FileType.CONFIG_FILE.getPath() + "groups.yml");

            dir = new File(group_file.getParentFile().getAbsolutePath());
            dir.mkdirs();

            if (!group_file.exists()) {
                group_file.createNewFile();
            }

            group = YamlConfiguration.loadConfiguration(group_file);

            group.options().copyDefaults(true);
        } catch (Exception ignored) {}
    }

    public FileConfiguration getGroupFile() {
        return group;
    }

    public void saveGroupFile() {
        try {
            getGroupFile().save(group_file);
        } catch (Exception ignored) {}
    }

    public void loadHomesFile() {
        try {
            homes_file = new File(FileType.CONFIG_FILE.getPath() + "homes.yml");

            dir = new File(homes_file.getParentFile().getAbsolutePath());
            dir.mkdirs();

            if (!homes_file.exists()) {
                homes_file.createNewFile();
            }

            homes = YamlConfiguration.loadConfiguration(homes_file);

            homes.options().copyDefaults(true);
        } catch (Exception ignored) {}
    }

    public FileConfiguration getHomesFile() {
        return homes;
    }

    public void saveHomesFile() {
        try {
            getHomesFile().save(homes_file);
        } catch (Exception ignored) {}
    }

    public void loadLocationFile() {
        try {
            location_file = new File(FileType.CONFIG_FILE.getPath() + "locations.yml");

            dir = new File(location_file.getParentFile().getAbsolutePath());
            dir.mkdirs();

            if (!location_file.exists()) {
                location_file.createNewFile();
            }

            location = YamlConfiguration.loadConfiguration(location_file);

            location.options().copyDefaults(true);
        } catch (Exception ignored) {}
    }

    public FileConfiguration getLocationFile() {
        return location;
    }

    public void saveLocationFile() {
        try {
            getLocationFile().save(location_file);
        } catch (Exception ignored) {}
    }

    public void loadKitsFile() {
        try {
            kits_file = new File(FileType.CONFIG_FILE.getPath() + "kits.yml");

            dir = new File(kits_file.getParentFile().getAbsolutePath());
            dir.mkdirs();

            if (!kits_file.exists()) {
                kits_file.createNewFile();
            }

            kits = YamlConfiguration.loadConfiguration(kits_file);

            kits.options().copyDefaults(true);
        } catch (Exception ignored) {}
    }

    public FileConfiguration getKitsFile() {
        return kits;
    }

    public void saveKitsFile() {
        try {
            getKitsFile().save(kits_file);
        } catch (Exception ignored) {}
    }

    public void loadPortalsFile() {
        try {
            portals_file = new File(FileType.CONFIG_FILE.getPath() + "portals.yml");

            dir = new File(portals_file.getParentFile().getAbsolutePath());
            dir.mkdirs();

            if (!portals_file.exists()) {
                portals_file.createNewFile();
            }

            portals = YamlConfiguration.loadConfiguration(portals_file);

            portals.options().copyDefaults(true);
        } catch (Exception ignored) {}
    }

    public FileConfiguration getPortalsFile() {
        return portals;
    }

    public void savePortalsFile() {
        try {
            getPortalsFile().save(portals_file);
        } catch (Exception ignored) {}
    }

    public void loadRegionsFile() {
        try {
            regions_file = new File(FileType.CONFIG_FILE.getPath() + "regions.yml");

            dir = new File(regions_file.getParentFile().getAbsolutePath());
            dir.mkdirs();

            if (!regions_file.exists()) {
                regions_file.createNewFile();
            }

            regions = YamlConfiguration.loadConfiguration(regions_file);

            regions.options().copyDefaults(true);
        } catch (Exception ignored) {}
    }

    public FileConfiguration getRegionsFile() {
        return regions;
    }

    public void saveRegionsFile() {
        try {
            getRegionsFile().save(regions_file);
        } catch (Exception ignored) {}
    }

    public void loadShopsFile() {
        try {
            shops_file = new File(FileType.CONFIG_FILE.getPath() + "shops.yml");

            dir = new File(shops_file.getParentFile().getAbsolutePath());
            dir.mkdirs();

            if (!shops_file.exists()) {
                shops_file.createNewFile();
            }

            shops = YamlConfiguration.loadConfiguration(shops_file);

            shops.options().copyDefaults(true);
        } catch (Exception ignored) {}
    }

    public FileConfiguration getShopsFile() {
        return shops;
    }

    public void saveShopsFile() {
        try {
            getShopsFile().save(shops_file);
        } catch (Exception ignored) {}
    }

    public void loadFile(String name, FileType type) {
        try {
            File file = new File(type.getPath() + name + ".yml");

            File dir = new File(file.getParentFile().getAbsolutePath());
            dir.mkdirs();

            if (!file.exists()) {
                file.createNewFile();
            }

            FileConfiguration config = YamlConfiguration.loadConfiguration(file);

            configMap.put(name, config);
            fileMap.put(config, file);
        } catch (Exception ignored){}
    }

    public FileConfiguration getFile(String name) {
        for (String strings : configMap.keySet()) {
            if (strings.toLowerCase().equalsIgnoreCase(name.toLowerCase())) {
                return configMap.get(strings);
            }
        }
        return null;
    }

    public void saveFile(String name, boolean clear) {
        try {
            if (configMap.containsKey(name)) {
                configMap.get(name).save(fileMap.get(configMap.get(name)));
            }

            if (clear) {
                fileMap.remove(configMap.get(name));
                configMap.remove(name);
                NECore.getLoggers().info("Cleared Player's Files");
            }
        } catch (Exception ignored) {}
    }

    public boolean isFileLoaded(String name) {
        return configMap.containsKey(name);
    }

    public void saveAllFiles() {
        try {
            for (FileConfiguration configuration : fileMap.keySet()) {
                configuration.save(fileMap.get(configuration));
            }
        } catch (Exception ignored) {}
    }

    public boolean fileExists(String name, FileType fileType) {
        File file = new File(fileType.getPath() + name + ".yml");
        return file.exists();
    }

    public boolean deleteFile(String name, FileType type) {
        if (fileExists(name, type)) {
            File file = new File(type.getPath() + name + ".yml");
            return file.delete();
        }
        return false;
    }
}