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
    private File location_file;
    private FileConfiguration location;
    private File dir;

    public FileManager() {
        configMap = Maps.newConcurrentMap();
        fileMap = Maps.newConcurrentMap();

        loadFiles();
    }

    public void loadFiles() {
        loadAnnouncementFile();
        loadBanFile();
        loadConfig();
        loadGroupFile();
        loadLocationFile();
    }

    public void saveFiles() {
        try {
            saveAnnouncementFile();
            saveBanFile();
            saveConfig();
            saveGroupFile();
            saveLocationFile();
            for (FileConfiguration configuration : fileMap.keySet()) {
                configuration.save(fileMap.get(configuration));
            }
        } catch (Exception ignored) {}
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
            config.addDefault("#servers", "hub, nighthawksurvival, creative");
            config.addDefault("server", "hub");
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
        if (fileMap.containsKey(name))return true;
        return false;
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
}
