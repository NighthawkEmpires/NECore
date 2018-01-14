package net.nighthawkempires.core.file;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.nighthawkempires.core.NECore;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ConcurrentMap;

public class FileManager {

    private ConcurrentMap<String, FileConfiguration> configMap;
    private ConcurrentMap<FileConfiguration, File> fileMap;

    private ConcurrentMap<FileType, FileConfiguration> fileTypeMap;

    public FileManager() {
        fileTypeMap = Maps.newConcurrentMap();
        configMap = Maps.newConcurrentMap();
        fileMap = Maps.newConcurrentMap();
    }

    public FileConfiguration get(FileType fileType) {
        try {
            if (!loaded(fileType)) {
                if (FileDirectory.NULL.getDirectory(fileType.getFilePath()).mkdirs()) {
                    NECore.getLoggers().info("Created File Directory for File: " + fileType.getFileName() + ".");
                }

                if (!fileType.getFile().exists()) {
                    fileType.getFile().createNewFile();
                }

                FileConfiguration config = YamlConfiguration.loadConfiguration(fileType.getFile());
                fileTypeMap.put(fileType, config);
                config.options().copyDefaults(true);
                switch (fileType) {
                    case ANNOUNCEMENT:
                        config.addDefault("settings.enabled", false);
                        config.addDefault("settings.message-delay", 120);
                        break;
                    case CONFIG:
                        config.options().header("Server Types: HUB, SURVIVAL, FREEBUILD, PRISON, MINIGAME, TEST");
                        config.options().copyHeader(true);
                        config.addDefault("settings.server", "HUB");
                        config.addDefault("settings.console.uuid", "12ea1659-5da0-4ec0-9c8d-15350261e2d5");
                        config.addDefault("settings.console.display-name", "&8&l&o*&7&l&oHawkeye&8&l&o*");
                        config.addDefault("settings.banned-words", Lists.newArrayList("Pussy", "Cock", "Fuck", "Shit",
                                "Ass", "Dick", "Penis", "Vagina", "Cunt", "Bitch", "Nigger", "Phil", "Staff", "Server",
                                "Console", "Disowned"));
                        config.options().header("MongoDB Connection Info");
                        config.options().copyHeader(true);
                        config.addDefault("databases.mongodb.enabled", false);
                        config.addDefault("databases.mongodb.hostname", "localhost");
                        config.addDefault("databases.mongodb.database", "default");
                        config.addDefault("databases.mongodb.username", "u$er");
                        config.addDefault("databases.mongodb.password", "p@ss");
                        config.options().header("MySQL Database Connection Info");
                        config.options().copyHeader(true);
                        config.addDefault("databases.mysql.enabled", false);
                        config.addDefault("databases.mysql.hostname", "localhost");
                        config.addDefault("databases.mysql.database", "default");
                        config.addDefault("databases.mysql.username", "u$er");
                        config.addDefault("databases.mysql.password", "p@ss");
                        config.options().header("Redis Database Connection Info");
                        config.options().copyHeader(true);
                        config.addDefault("databases.redis.enabled", false);
                        config.addDefault("databases.redis.hostname", "localhost");
                        config.addDefault("databases.redis.server-id", "minecraft");
                        config.addDefault("databases.redis.channel", "default");
                        config.options().header("Guild Settings");
                        config.options().copyHeader(true);
                        config.addDefault("guilds.databases.mongodb.enabled", false);
                        config.addDefault("guilds.databases.mongodb.hostname", "localhost");
                        config.addDefault("guilds.databases.mongodb.database", "default");
                        config.addDefault("guilds.databases.mongodb.username", "u$er");
                        config.addDefault("guilds.databases.mongodb.password", "p@ss");
                        config.options().copyHeader(true);
                        break;
                    default:
                        break;
                }
                save(fileType, true);
                fileTypeMap.put(fileType, config);
            }
        } catch (IOException ignored) {}

        return fileTypeMap.get(fileType);
    }

    public void save(FileType fileType) {
        if (!loaded(fileType)) return;
        try {
            get(fileType).save(fileType.getFile());
        } catch (IOException ignored) {}
    }

    public void save(FileType fileType, boolean clear) {
        save(fileType);
        if (clear) {
            fileTypeMap.remove(fileType);
        }
    }

    public boolean loaded(FileType fileType) {
        return fileTypeMap.containsKey(fileType);
    }

    public void loadFile(String name, FileFolder type) {
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
        } catch (Exception ignored) {}
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

    public boolean fileExists(String name, FileFolder fileType) {
        File file = new File(fileType.getPath() + name + ".yml");
        return file.exists();
    }

    public boolean deleteFile(String name, FileFolder type) {
        if (fileExists(name, type)) {
            File file = new File(type.getPath() + name + ".yml");
            return file.delete();
        }
        return false;
    }
}