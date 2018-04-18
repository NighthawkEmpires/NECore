package net.nighthawkempires.core.file;

import java.io.File;

public enum FileType {

    ANNOUNCEMENT(FileFolder.CONFIG_PATH, "announcements.yml"),
    CONFIG(FileFolder.CONFIG_PATH, "config.yml"),
    GROUP(FileFolder.CONFIG_PATH, "groups.yml"),
    HOMES(FileFolder.CONFIG_PATH, "homes.yml"),
    LOCATION(FileFolder.CONFIG_PATH, "locations.yml"),
    KITS(FileFolder.CONFIG_PATH, "locations.yml"),
    MESSAGES(FileFolder.CONFIG_PATH, "messages.yml"),
    PORTAL(FileFolder.CONFIG_PATH, "portals.yml"),
    PRICES(FileFolder.CONFIG_PATH, "prices.yml"),
    REGION(FileFolder.CONFIG_PATH, "regions.yml"),
    SHOP(FileFolder.CONFIG_PATH, "shops.yml");

    private FileFolder filePath;
    private String fileName;

    FileType(FileFolder filePath, String fileName) {
        this.filePath = filePath;
        this.fileName = fileName;
    }

    public FileFolder getFilePath() {
        return filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public File getFile() {
        return new File(getFilePath().getPath() + getFileName());
    }
}
