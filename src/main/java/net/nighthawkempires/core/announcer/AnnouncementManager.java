package net.nighthawkempires.core.announcer;

import com.google.common.collect.Lists;
import net.nighthawkempires.core.NECore;
import net.nighthawkempires.core.file.FileType;
import net.nighthawkempires.core.language.Lang;
import net.nighthawkempires.core.utils.ListArraySetUtil;
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
        delay = getConfig().getInt("settings.delay", 60);
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
        if (getConfig().getBoolean("settings.enable", true)) {
            if (getConfig().isSet("announcements")) {
                for (String announcement : getConfig().getConfigurationSection("announcements").getKeys(false)) {
                    ConfigurationSection section = getConfig().getConfigurationSection("announcements." + announcement);
                    String id = announcement.substring(announcement.length() - 1);
                    getAnnouncements().add(new Announcement(Integer.valueOf(id),
                            ListArraySetUtil.getStringArray(section.getStringList("lines"))));
                }
            }
        }
    }

    public void saveAnnouncements() {

        if (getConfig().getBoolean("settings.enable", true)) {
            ConfigurationSection section = getConfig().getConfigurationSection("announcements");
            for (Announcement announcement : getAnnouncements()) {
                section.set("announcement" + announcement.getId() + ".lines",
                        ListArraySetUtil.getStringList(announcement.getLines()));
            }
            getFileManager().save(FileType.ANNOUNCEMENT, true);
        }
    }

    public void startSchedule() {
        if (getConfig().getBoolean("settings.enable")) {
            if (this.taskId != -1) {
                Bukkit.getScheduler().cancelTask(taskId);
            }

            this.taskId = Bukkit.getScheduler()
                    .scheduleSyncRepeatingTask(NECore.getPlugin(), this::broadcastAnnouncement, delay * 20L,
                            delay * 20L);
        }
    }

    public void broadcastAnnouncement() {
        Announcement announcement = pickAnnouncement();
        if (announcement == null) return;

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(Lang.HEADER.getServerHeader());
            player.sendMessage(Lang.HEADER.format(announcement.getLines()));
            player.sendMessage(Lang.FOOTER.getMessage());
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

    public int getUnusedId() {
        int id;
        for (id = 0; getAnnouncement(id) != null; id++) {}
        return id;
    }

    public Announcement getAnnouncement(int id) {
        for (Announcement announcement : getAnnouncements()) {
            if (announcement.getId() == id) {
                return announcement;
            }
        }
        return null;
    }

    public Announcement addAnnouncement(String[] lines) {
        Announcement announcement = new Announcement(getUnusedId(), lines);
        getAnnouncements().add(announcement);
        return announcement;
    }

    public void removeAnnouncement(int id) {
        Announcement announcement = getAnnouncement(id);
        if (announcement != null) {
            getAnnouncements().remove(announcement);
        }
    }

    public FileConfiguration getConfig() {
        return NECore.getFileManager().get(FileType.ANNOUNCEMENT);
    }
}