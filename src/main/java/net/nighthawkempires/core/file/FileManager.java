package net.nighthawkempires.core.file;

import com.google.common.collect.Maps;
import net.nighthawkempires.core.NECore;
import net.nighthawkempires.core.logger.LogManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
                    LogManager.info("Created File Directory for File: " + fileType.getFileName() + ".");
                }

                boolean setup = false;
                if (!fileType.getFile().exists()) {
                    setup = fileType.getFile().createNewFile();
                }

                FileConfiguration config = YamlConfiguration.loadConfiguration(fileType.getFile());
                fileTypeMap.put(fileType, config);
                config.options().copyDefaults(true);
                if (setup) {
                    switch (fileType) {
                        case CONFIG:
                            copyDefaults(config, "/config.yml");
                            break;
                        case MESSAGES:
                            copyDefaults(config, "/messages.yml");
                            break;
                        case ANNOUNCEMENT:
                            copyDefaults(config, "/announcements.yml");
                            break;
                        default:
                            break;
                    }
                }
                save(fileType, true);
                fileTypeMap.put(fileType, config);
            }
        } catch (IOException ignored) {}

        return fileTypeMap.get(fileType);
    }

    public void copyDefaults(FileConfiguration file, String resource){
        InputStream inputStream;
        file.options().copyDefaults(true);
        try {
            inputStream = NECore.getPlugin().getClass().getResourceAsStream(resource);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            FileConfiguration defaults = YamlConfiguration.loadConfiguration(inputStreamReader);
            file.setDefaults(defaults);
            inputStream.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
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
}