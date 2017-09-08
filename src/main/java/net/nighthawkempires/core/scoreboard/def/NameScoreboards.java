package net.nighthawkempires.core.scoreboard.def;

import net.nighthawkempires.core.NECore;
import net.nighthawkempires.core.language.Lang;
import net.nighthawkempires.core.scoreboard.Scoreboards;
import net.nighthawkempires.core.server.Server;
import net.nighthawkempires.core.users.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class NameScoreboards extends Scoreboards {

    private Scoreboard scoreboard;

    public NameScoreboards() {
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("test", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(Lang.SCOREBOARD.getServerBoard());

        if (NECore.getSettings().server.equals(Server.NS)) {
            objective.getScore(ChatColor.BLUE + "").setScore(10);
            objective.getScore(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Name" + ChatColor.GRAY + ": ").setScore(9);
            objective.getScore("    {name}" ).setScore(8);
            objective.getScore(ChatColor.DARK_PURPLE + " ").setScore(7);
            objective.getScore(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Balance" + ChatColor.GRAY + ": ").setScore(6);
            objective.getScore("    {balance}").setScore(5);
            objective.getScore(ChatColor.YELLOW + "  ").setScore(4);
            objective.getScore(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Tokens" + ChatColor.GRAY + ": ").setScore(3);
            objective.getScore("    {tokens}").setScore(2);
            objective.getScore(ChatColor.RED + "   ").setScore(1);
        } else if (NECore.getSettings().server.equals(Server.HUB)) {
            objective.getScore(ChatColor.BLUE + "").setScore(10);
            objective.getScore(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Name" + ChatColor.GRAY + ": ").setScore(9);
            objective.getScore("    {name}" ).setScore(8);
            objective.getScore(ChatColor.DARK_PURPLE + " ").setScore(7);
            objective.getScore(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Date Joined" + ChatColor.GRAY + ": ").setScore(6);
            objective.getScore("    {balance}").setScore(5);
            objective.getScore(ChatColor.YELLOW + "  ").setScore(4);
            objective.getScore(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Tokens" + ChatColor.GRAY + ": ").setScore(3);
            objective.getScore("    {tokens}").setScore(2);
            objective.getScore(ChatColor.RED + "   ").setScore(1);
        }
    }

    public String getName() {
        return "name";
    }

    public int getNumber() {
        return 1;
    }

    public void update(Player player) {
        Objective objective = getScoreboard().getObjective("test");
        objective.unregister();
        objective = getScoreboard().registerNewObjective("test", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(Lang.SCOREBOARD.getServerBoard());
        User user = NECore.getUserManager().getUser(player.getUniqueId());

        if (NECore.getSettings().server.equals(Server.NS)) {
            objective.getScore(ChatColor.DARK_GRAY + "" + ChatColor.STRIKETHROUGH + "" + ChatColor.BOLD + "------------").setScore(10);
            objective.getScore(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + " Name" + ChatColor.GRAY + ": ").setScore(9);
            objective.getScore("     " + ChatColor.BLUE + "" + ChatColor.BOLD + player.getName()).setScore(8);
            objective.getScore(ChatColor.DARK_PURPLE + " ").setScore(7);
            objective.getScore(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + " Balance" + ChatColor.GRAY + ": ").setScore(6);
            objective.getScore("     " + ChatColor.GREEN + "" + ChatColor.BOLD + "$" + ChatColor.YELLOW + "" + ChatColor.BOLD + Lang.BAL_FORMAT.balance(user.getBalance())).setScore(5);
            objective.getScore(ChatColor.YELLOW + "  ").setScore(4);
            objective.getScore(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + " Tokens" + ChatColor.GRAY + ": ").setScore(3);
            objective.getScore("     " + ChatColor.GOLD + "" + ChatColor.BOLD + user.getTokens()).setScore(2);
            objective.getScore(ChatColor.DARK_GRAY + "" + ChatColor.STRIKETHROUGH + "" + ChatColor.BOLD + "=----------=").setScore(1);
        } else if (NECore.getSettings().server.equals(Server.HUB)) {
            objective.getScore(ChatColor.DARK_GRAY + "" + ChatColor.STRIKETHROUGH + "" + ChatColor.BOLD + "------------").setScore(10);
            objective.getScore(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + " Name" + ChatColor.GRAY + ": ").setScore(9);
            objective.getScore("     " + ChatColor.BLUE + "" + ChatColor.BOLD + player.getName()).setScore(8);
            objective.getScore(ChatColor.DARK_PURPLE + " ").setScore(7);
            objective.getScore(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + " Date Joined" + ChatColor.GRAY + ": ").setScore(6);
            objective.getScore("     " + ChatColor.GRAY + "" + ChatColor.BOLD + user.getJoinDate()).setScore(5);
            objective.getScore(ChatColor.YELLOW + "  ").setScore(4);
            objective.getScore(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + " Tokens" + ChatColor.GRAY + ": ").setScore(3);
            objective.getScore("     " + ChatColor.GOLD + "" + ChatColor.BOLD + user.getTokens()).setScore(2);
            objective.getScore(ChatColor.DARK_GRAY + "" + ChatColor.STRIKETHROUGH + "" + ChatColor.BOLD + "=----------=").setScore(1);
        }
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }
}
