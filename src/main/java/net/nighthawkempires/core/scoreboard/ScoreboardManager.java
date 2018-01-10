package net.nighthawkempires.core.scoreboard;

import com.google.common.collect.Lists;

import com.google.common.collect.Maps;
import net.nighthawkempires.core.NECore;
import net.nighthawkempires.core.scoreboard.def.NameScoreboards;
import net.nighthawkempires.core.users.User;
import net.nighthawkempires.core.utils.MathUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

public class ScoreboardManager {

    private List<Scoreboards> scoreboards;
    private ConcurrentMap<UUID, List<Scoreboards>> scoreboardMap;
    private ConcurrentMap<UUID, Integer> repeatFor;

    public ScoreboardManager() {
        scoreboards = Lists.newArrayList();
        scoreboardMap = Maps.newConcurrentMap();
        repeatFor = Maps.newConcurrentMap();

        scoreboards.add(new NameScoreboards());
    }

    public void addScoreboard(Scoreboards scoreboards) {
        this.scoreboards.add(scoreboards);
    }

    public void startBoards(Player player) {
        scoreboardMap.put(player.getUniqueId(), scoreboards);
        final int[] board = {0};
        final int[] stopBoard = {0};
        int taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(NECore.getPlugin(), () -> {
            if (!player.isOnline())return;

            if (board[0] == scoreboardMap.get(player.getUniqueId()).size()) {
                board[0] = 0;
            }

            if (scoreboardMap.get(player.getUniqueId()).get(board[0]) == null) {
                board[0] = 0;
            }

            stopBoard[0] = board[0] -1;

            if (stopBoard[0] == -1) {
                stopBoard[0] = scoreboardMap.get(player.getUniqueId()).size() -1;
            }

            try {
                Bukkit.getScheduler().cancelTask(scoreboardMap.get(player.getUniqueId()).get(stopBoard[0]).getTaskID());
            } catch (Exception ignored) {}

            player.setScoreboard(scoreboardMap.get(player.getUniqueId()).get(board[0]).getFor(player));
            board[0]++;
        }, 0 , 20*15);
        repeatFor.put(player.getUniqueId(), taskId);
    }

    public void stopBoards(Player player) {
        player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
        Bukkit.getScheduler().cancelTask(repeatFor.get(player.getUniqueId()));
    }
}
