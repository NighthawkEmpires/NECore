package net.nighthawkempires.core.scoreboard.def;

import net.nighthawkempires.core.NECore;
import net.nighthawkempires.core.language.Lang;
import net.nighthawkempires.core.scoreboard.Scoreboards;
import net.nighthawkempires.core.users.UserModel;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class NameScoreboards extends Scoreboards {

    private int task = 0;

    public String getName() {
        return "name";
    }

    public int getTaskID() {
        return task;
    }

    public int getNumber() {
        return 1;
    }

    public Scoreboard getFor(Player player) {
        UserModel user = NECore.getUserRegistry().getUser(player.getUniqueId());
        Scoreboard[] scoreboard = {Bukkit.getScoreboardManager().getNewScoreboard()};
        Objective[] objective = {scoreboard[0].registerNewObjective("test", "dummy")};
        objective[0].setDisplaySlot(DisplaySlot.SIDEBAR);
        objective[0].setDisplayName(Lang.SCOREBOARD.getServerBoard());
        Team name = scoreboard[0].registerNewTeam("name");
        name.addEntry(ChatColor.GRAY + " ➛   " + ChatColor.BLUE + "" + ChatColor.BOLD);
        name.setPrefix("");
        name.setSuffix("");
        Team display = scoreboard[0].registerNewTeam("display");
        display.addEntry(ChatColor.GRAY + " ➛   " + ChatColor.GRAY + "" + ChatColor.BOLD);
        display.setPrefix("");
        display.setSuffix("");
        Team tokens = scoreboard[0].registerNewTeam("tokens");
        tokens.addEntry(ChatColor.GRAY + " ➛   " + ChatColor.GOLD + "" + ChatColor.BOLD);
        tokens.setPrefix("");
        tokens.setSuffix("");

        objective[0].getScore(ChatColor.DARK_GRAY + "" + ChatColor.STRIKETHROUGH + "" + ChatColor.BOLD + "------------")
                .setScore(10);
        objective[0].getScore(ChatColor.GRAY + "" + ChatColor.BOLD + " Name" + ChatColor.GRAY + ": ").setScore(9);
        objective[0].getScore(ChatColor.GRAY + " ➛   " + ChatColor.BLUE + "" + ChatColor.BOLD).setScore(8);
        name.setSuffix(player.getName());
        objective[0].getScore(ChatColor.DARK_PURPLE + " ").setScore(7);
        objective[0].getScore(ChatColor.GRAY + "" + ChatColor.BOLD + " Display Name" + ChatColor.GRAY + ": ")
                .setScore(6);
        objective[0].getScore(ChatColor.GRAY + " ➛   " + ChatColor.GRAY + "" + ChatColor.BOLD).setScore(5);
        try {
            display.setSuffix(ChatColor.translateAlternateColorCodes('&', user.getDisplayName()));
        } catch (IllegalArgumentException e) {
            display.setSuffix(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', user.getDisplayName())));
        }
        objective[0].getScore(ChatColor.YELLOW + "  ").setScore(4);
        objective[0].getScore(ChatColor.GRAY + "" + ChatColor.BOLD + " Tokens" + ChatColor.GRAY + ": ").setScore(3);
        objective[0].getScore(ChatColor.GRAY + " ➛   " + ChatColor.GOLD + "" + ChatColor.BOLD).setScore(2);
        tokens.setSuffix(user.getTokens() + "");
        objective[0].getScore(ChatColor.DARK_GRAY + "" + ChatColor.STRIKETHROUGH + "" + ChatColor.BOLD + "-----------")
                .setScore(1);

        this.task = Bukkit.getScheduler().scheduleSyncRepeatingTask(NECore.getPlugin(), () -> {
            name.setSuffix(player.getName());
            try {
                display.setSuffix(ChatColor.translateAlternateColorCodes('&', user.getDisplayName()));
            } catch (IllegalArgumentException e) {
                display.setSuffix(
                        ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', user.getDisplayName())));
            }
            tokens.setSuffix(user.getTokens() + "");
        }, 0, 5);
        Bukkit.getScheduler()
                .scheduleSyncDelayedTask(NECore.getPlugin(), () -> Bukkit.getScheduler().cancelTask(this.task),
                        14 * 20);
        return scoreboard[0];
    }
}
