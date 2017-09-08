package net.nighthawkempires.core.ban;

import com.google.common.collect.Lists;
import net.nighthawkempires.core.NECore;
import net.nighthawkempires.core.utils.BroadcastUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static net.nighthawkempires.core.NECore.getFileManager;
import static net.nighthawkempires.core.NECore.getUserManager;

public class BanManager {

    private List<Ban> bans;
    private List<Ban> ipbans;
    private int taskId;
    private int min_delay;

    public BanManager() {
        bans = Lists.newArrayList();
        ipbans = Lists.newArrayList();
        taskId = -1;
        min_delay = 2;

        loadBans();
        startSaveSchedule();
    }

    public List<Ban> getBans() {
        return bans;
    }

    public List<Ban> getIpBans() {
        return ipbans;
    }

    public void loadBans() {
        bans.clear();
        ipbans.clear();

        if (getFileManager().getBanFile().isSet("bans")) {
            for (String string : getFileManager().getBanFile().getConfigurationSection("bans").getKeys(false)) {
                ConfigurationSection section = getFileManager().getBanFile().getConfigurationSection("bans." + string);
                getBans().add(new Ban(UUID.fromString(string), section.getString("address"), section.getString("reason"), section.getString("date"), UUID.fromString(section.getString("by"))));
            }
        }
    }

    public void saveBans() {
        getFileManager().getBanFile().set("bans", null);
        getFileManager().saveBanFile();
        for (Ban ban : getBans()) {
            getFileManager().getBanFile().set("bans." + ban.getUUID() + ".address", ban.getAddress());
            getFileManager().getBanFile().set("bans." + ban.getUUID() + ".reason", ban.getReason());
            getFileManager().getBanFile().set("bans." + ban.getUUID() + ".date", ban.getDate());
            getFileManager().getBanFile().set("bans." + ban.getUUID() + ".by", ban.getBy().toString());
            getFileManager().saveBanFile();
        }
    }

    public void startSaveSchedule() {
        if (taskId != -1) Bukkit.getScheduler().cancelTask(taskId);

        taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(NECore.getPlugin(), () -> {
            saveBans();
            loadBans();
        }, 20*20*60*min_delay, 20*20*60*min_delay);
    }

    public void broadcastBan(UUID banned, String reason, UUID by) {
        BroadcastUtil.broadcast(ChatColor.BLUE + Bukkit.getOfflinePlayer(banned).getName() + ChatColor.GRAY + " has been banned by " + ChatColor.BLUE + Bukkit.getOfflinePlayer(by).getName()
                + ChatColor.GRAY + " for: " + ChatColor.RED + reason + ChatColor.GRAY + ".");
    }

    public Ban addBan(UUID banned, String reason, UUID by) {
        Ban ban = new Ban(banned, getUserManager().getTempUser(banned).getAddress(), reason, new SimpleDateFormat("MM/dd/yyyy").format(new Date()), by);
        return ban;
    }

    public void removeBan(UUID uuid) {
        for (Ban ban : getBans()) {
            if (ban.getUUID().equals(uuid)) {
                getBans().remove(ban);
            }
        }
    }
}
