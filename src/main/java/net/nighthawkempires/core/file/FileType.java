package net.nighthawkempires.core.file;

public enum  FileType {

    CONFIG_FILE("data/empires/"),
    GUILD_FILE("data/empires/guild/"),
    LIBRARY_FILE("data/empires/library/"),
    PLAYER_FILE("../data/empires/player/"),
    SKIN_FOLDER("../data/empires/skins/");

    private String path;

    FileType(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
