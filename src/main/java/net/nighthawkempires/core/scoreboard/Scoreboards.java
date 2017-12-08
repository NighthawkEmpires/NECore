package net.nighthawkempires.core.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

public abstract class Scoreboards {

    public abstract String getName();

    public abstract int getTaskID();

    public abstract Scoreboard getFor(Player player);

    public Scoreboard getScoreboard() {
        return Bukkit.getScoreboardManager().getNewScoreboard();
    }
}
