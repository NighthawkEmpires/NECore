package net.nighthawkempires.core.file;

public enum FileFolder {

    CONFIG_PATH("data/empires/"),
    BAN_PATH("data/empires/bans/"),
    GUILD_PATH("data/empires/guild/"),
    MUTE_PATH("data/empires/mutes/"),
    LIBRARY_PATH("data/empires/library/"),
    PLAYER_PATH("../data/empires/player/"),
    SKIN_PATH("../data/empires/skins/");

    private String path;

    FileFolder(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
