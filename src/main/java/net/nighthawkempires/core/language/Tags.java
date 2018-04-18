package net.nighthawkempires.core.language;

import net.nighthawkempires.core.NECore;
import net.nighthawkempires.core.file.FileType;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

public enum Tags {

    HUB, SURVIVAL, FREEBUILD, PRISON, MINIGAME, TEST

    ;public String getTag() {
        return ChatColor.translateAlternateColorCodes('&', getConfig().getString("TAGS." + this.name()));
    }

    private FileConfiguration getConfig() {
        return NECore.getFileManager().get(FileType.MESSAGES);
    }

}
