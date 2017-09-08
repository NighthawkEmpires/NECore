package net.nighthawkempires.core.scoreboard.def;

import net.nighthawkempires.core.NECore;
import net.nighthawkempires.core.language.Lang;
import net.nighthawkempires.core.scoreboard.Scoreboards;
import net.nighthawkempires.core.users.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class KillScoreboards extends Scoreboards {

    private Scoreboard scoreboard;

    public KillScoreboards() {
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("test", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(Lang.SCOREBOARD.getServerBoard());

        objective.getScore(ChatColor.DARK_GRAY + "" + ChatColor.STRIKETHROUGH + "" + ChatColor.BOLD + "------------").setScore(10);
        objective.getScore(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Kills" + ChatColor.GRAY + ": ").setScore(9);
        objective.getScore("    {kills}" ).setScore(8);
        objective.getScore(ChatColor.DARK_PURPLE + " ").setScore(7);
        objective.getScore(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Deaths" + ChatColor.GRAY + ": ").setScore(6);
        objective.getScore("    {deaths}").setScore(5);
        objective.getScore(ChatColor.YELLOW + "  ").setScore(4);
        objective.getScore(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "K/D Ratio" + ChatColor.GRAY + ": ").setScore(3);
        objective.getScore("    {ratio}").setScore(2);
        objective.getScore(ChatColor.DARK_GRAY + "" + ChatColor.STRIKETHROUGH + "" + ChatColor.BOLD + "=----------=").setScore(1);
    }

    public String getName() {
        return "kill";
    }

    public int getNumber() {
        return 2;
    }

    public void update(Player player) {
        Objective objective = getScoreboard().getObjective("test");
        objective.unregister();
        objective = getScoreboard().registerNewObjective("test", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(Lang.SCOREBOARD.getServerBoard());
        User user = NECore.getUserManager().getUser(player.getUniqueId());

        double ratio = (double)user.getKills() / (double) user.getDeaths();

        objective.getScore(ChatColor.DARK_GRAY + "" + ChatColor.STRIKETHROUGH + "" + ChatColor.BOLD + "------------").setScore(10);
        objective.getScore(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Kills" + ChatColor.GRAY + ": ").setScore(9);
        objective.getScore("    " + ChatColor.GREEN + "" + ChatColor.BOLD + user.getKills()).setScore(8);
        objective.getScore(ChatColor.DARK_PURPLE + " ").setScore(7);
        objective.getScore(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Deaths" + ChatColor.GRAY + ": ").setScore(6);
        objective.getScore("    " + ChatColor.RED + "" + ChatColor.BOLD + user.getDeaths()).setScore(5);
        objective.getScore(ChatColor.YELLOW + "  ").setScore(4);
        objective.getScore(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "K/D Ratio" + ChatColor.GRAY + ": ").setScore(3);
        objective.getScore("    " + ChatColor.YELLOW + "" + ChatColor.BOLD + "" + String.valueOf(ratio).trim()).setScore(2);
        objective.getScore(ChatColor.DARK_GRAY + "" + ChatColor.STRIKETHROUGH + "" + ChatColor.BOLD + "=----------=").setScore(1);
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }
}
