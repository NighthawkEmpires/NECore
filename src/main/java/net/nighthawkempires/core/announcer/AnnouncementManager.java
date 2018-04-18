package net.nighthawkempires.core.announcer;

import com.google.common.collect.Lists;
import net.nighthawkempires.core.NECore;
import net.nighthawkempires.core.file.FileType;
import net.nighthawkempires.core.language.Messages;
import net.nighthawkempires.core.utils.ListArraySetUtil;
import net.nighthawkempires.core.utils.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

import static net.nighthawkempires.core.NECore.getFileManager;

public class AnnouncementManager {

    private List<Announcement> announcements;
    private int delay;
    private int taskId;
    private int id;

    public AnnouncementManager() {
        announcements = Lists.newArrayList();
        delay = getConfig().getInt("MESSAGE_DELAY", 60);
        taskId = -1;
        id = -1;

        loadAnnouncements();
        startSchedule();
    }

    public List<Announcement> getAnnouncements() {
        return announcements;
    }

    public void loadAnnouncements() {
        announcements.clear();
        if (getConfig().getBoolean("ENABLED", true)) {
            if (getConfig().isSet("ANNOUNCEMENTS")) {
                for (String announcement : getConfig().getConfigurationSection("ANNOUNCEMENTS").getKeys(false)) {
                    ConfigurationSection section = getConfig().getConfigurationSection("ANNOUNCEMENT." + announcement);
                    getAnnouncements().add(new Announcement(announcement, ListArraySetUtil.getStringArray(section.getStringList("LINES"))));
                }
            }
        }
    }

    public void saveAnnouncements() {
        if (getConfig().getBoolean("ENABLED", true)) {
            if (getAnnouncements().size() != 0) {
                ConfigurationSection section = getConfig().getConfigurationSection("ANNOUNCEMENTS");
                for (Announcement announcement : getAnnouncements()) {
                    section.set(announcement.getName() + ".LINES", ListArraySetUtil.getStringList(announcement.getLines()));
                    getFileManager().save(FileType.ANNOUNCEMENT, true);
                }
            }
        }
    }

    private void startSchedule() {
        if (getConfig().getBoolean("ENABLED", true)) {
            if (this.taskId != -1) {
                Bukkit.getScheduler().cancelTask(taskId);
            }

            this.taskId = Bukkit.getScheduler()
                    .scheduleSyncRepeatingTask(NECore.getPlugin(), this::broadcastAnnouncement, delay * 20L,
                            delay * 20L);
        }
    }

    private void broadcastAnnouncement() {
        Announcement announcement = pickAnnouncement();
        if (announcement == null) return;

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(StringUtil.centeredMessage(Messages.HEADER.getMessage()));
            player.sendMessage(StringUtil.centeredMessage(Messages.BLANK.getFormattedMessages(announcement.getLines())));
            player.sendMessage(StringUtil.centeredMessage(Messages.FOOTER.getMessage()));
        }
    }

    public Announcement pickAnnouncement() {
        if (getAnnouncements().isEmpty()) return null;

        id++;
        if (id >= getAnnouncements().size()) {
            id = 0;
        }
        return getAnnouncements().get(id);
    }

    private Announcement getAnnouncement(String name) {
        for (Announcement announcement : getAnnouncements()) {
            if (announcement.getName() == null) {
                return announcement;
            }
        }
        return null;
    }

    public Announcement addAnnouncement(String name, String[] lines) {
        Announcement announcement = new Announcement(name, lines);
        getAnnouncements().add(announcement);
        return announcement;
    }

    public void removeAnnouncement(String name) {
        Announcement announcement = getAnnouncement(name);
        if (announcement != null) {
            getAnnouncements().remove(announcement);
        }
    }

    public FileConfiguration getConfig() {
        return NECore.getFileManager().get(FileType.ANNOUNCEMENT);
    }
}