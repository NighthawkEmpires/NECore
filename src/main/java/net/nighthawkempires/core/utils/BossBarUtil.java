package net.nighthawkempires.core.utils;

import com.google.common.collect.Maps;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

public class BossBarUtil {

    private static ConcurrentMap<UUID, BossBar> playerBar;

    static {
        playerBar = Maps.newConcurrentMap();
    }

    public static void setPlayerBar(Player player, BossBar bossBar) {
        playerBar.put(player.getUniqueId(), bossBar);
        bossBar.addPlayer(player);
    }

    public static void setPlayerBar(Player player, String title, double percent, BarColor color, BarStyle style) {
        BossBar bossBar = playerBar.get(player.getUniqueId());
        if (bossBar == null) {
            bossBar = Bukkit.createBossBar(ChatColor.translateAlternateColorCodes('&', title), color, style);
            playerBar.put(player.getUniqueId(), bossBar);
        }
        bossBar.setTitle(ChatColor.translateAlternateColorCodes('&', title));
        bossBar.setProgress(percent);
        bossBar.addPlayer(player);
    }

    public static void removePlayerBar(Player player) {
        BossBar bar = playerBar.remove(player.getUniqueId());
        if (bar != null) {
            bar.removeAll();
        }
    }

    public static void turnOff() {
        for (BossBar bar : playerBar.values()) {
            bar.removeAll();
        }
        playerBar.clear();
    }

    public static void setDefaultBar(Player player) {
        BossBar bossBar = playerBar.get(player.getUniqueId());
        if (bossBar == null) {
            bossBar = Bukkit.createBossBar(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "* " + ChatColor.GRAY  + "" + ChatColor.ITALIC + "Donate"
                            + ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + " * " + ChatColor.BLUE + "" + ChatColor.ITALIC + "" + ChatColor.UNDERLINE + "store.nighthawkempires.net"
                            + ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + " * " + ChatColor.GRAY  + "" + ChatColor.ITALIC + "50% off" + ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + " * ",
                    BarColor.BLUE, BarStyle.SOLID);
            playerBar.put(player.getUniqueId(), bossBar);
        }
        bossBar.setTitle(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "* " + ChatColor.GRAY  + "" + ChatColor.ITALIC + "Donate"
                + ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + " * " + ChatColor.BLUE + "" + ChatColor.ITALIC + "" + ChatColor.UNDERLINE + "store.nighthawkempires.net"
                + ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + " * " + ChatColor.GRAY  + "" + ChatColor.ITALIC + "50% off" + ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + " * ");
        bossBar.setColor(BarColor.BLUE);
        bossBar.setStyle(BarStyle.SOLID);
        bossBar.setProgress(1.0);
        bossBar.addPlayer(player);
    }

    public static void setCombatBar(Player player, int seconds, double percent) {
        BossBar bossBar = playerBar.get(player.getUniqueId());
        String title = ChatColor.RED + "" + ChatColor.ITALIC + "In Combat - " + seconds + " seconds remaining.";
        if (bossBar == null) {
            bossBar = Bukkit.createBossBar(title, BarColor.RED, BarStyle.SOLID, BarFlag.CREATE_FOG);
            playerBar.put(player.getUniqueId(), bossBar);
        }
        bossBar.setTitle(title);
        bossBar.setColor(BarColor.RED);
        bossBar.setStyle(BarStyle.SOLID);
        bossBar.addFlag(BarFlag.CREATE_FOG);
        bossBar.setProgress(percent);
        bossBar.addPlayer(player);
    }
}
