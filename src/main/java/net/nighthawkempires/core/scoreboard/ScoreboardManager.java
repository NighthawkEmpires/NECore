package net.nighthawkempires.core.scoreboard;

import com.google.common.collect.Lists;

import net.nighthawkempires.core.NECore;
import net.nighthawkempires.core.scoreboard.def.KillScoreboards;
import net.nighthawkempires.core.scoreboard.def.NameScoreboards;
import net.nighthawkempires.core.users.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class ScoreboardManager {

    private List<Scoreboards> scoreboards;

    public ScoreboardManager() {
        scoreboards = Lists.newArrayList();

        scoreboards.add(new NameScoreboards());
        scoreboards.add(new KillScoreboards());
    }

    public void addScoreboard(Scoreboards scoreboards) {
        this.scoreboards.add(scoreboards);
    }

    public void setupDefaultBoard(Player player) {
        User user = NECore.getUserManager().getUser(player.getUniqueId());
    }

    public void startBoards(Player player) {
        final int[] board = {0};
        Bukkit.getScheduler().scheduleSyncRepeatingTask(NECore.getPlugin(), () -> {
            if (!player.isOnline())return;

            if (board[0] == scoreboards.size()) {
                board[0] = 0;
            }

            if (scoreboards.get(board[0]) == null) {
                board[0] = 0;
            }

            scoreboards.get(board[0]).update(player);
            player.setScoreboard(scoreboards.get(board[0]).getScoreboard());
            board[0]++;
        }, 0 , 20*30);
    }

    public void stopBoards(Player player) {
        player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
    }
}
