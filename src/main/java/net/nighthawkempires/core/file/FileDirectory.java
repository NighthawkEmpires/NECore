package net.nighthawkempires.core.file;

import java.io.File;

public enum FileDirectory {

    CONFIG_DIRECTORY(new File(FileFolder.CONFIG_PATH.getPath())),
    GUILD_DIRECTORY(new File(FileFolder.GUILD_PATH.getPath())),
    NULL(null);

    private File file;

    FileDirectory(File file) {
        this.file = file;
    }

    public File getDirectory() {
        return file;
    }

    public File getDirectory(FileFolder folder) {
        return new File(folder.getPath());
    }
}
