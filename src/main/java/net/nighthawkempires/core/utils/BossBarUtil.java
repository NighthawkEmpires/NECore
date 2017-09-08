package net.nighthawkempires.core.utils;

import com.google.common.collect.Maps;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class BossBarUtil {

    private static HashMap<String, BossBar> bars;

    static {
        bars = Maps.newHashMap();
    }

    public static void setPlayerBar(Player player, String title, double percent) {
        BossBar bar = bars.get(player.getName());
        if (bar == null) {
            bar = Bukkit.createBossBar(ChatColor.translateAlternateColorCodes('&', title), BarColor.RED, BarStyle.SEGMENTED_20);
            bars.put(player.getName(), bar);
        }
        bar.setTitle(ChatColor.translateAlternateColorCodes('&', title));
        bar.setProgress(percent);
        bar.addPlayer(player);
    }

    public static void removePlayerBar(Player player) {
        BossBar bar = bars.remove(player.getName());
        if (bar != null) {
            bar.removeAll();
        }
    }

    public static void turnOff() {
        for (BossBar bar : bars.values()) {
            bar.removeAll();
        }
        bars.clear();
    }
}
