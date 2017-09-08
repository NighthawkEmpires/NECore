package net.nighthawkempires.core.listener;

import net.nighthawkempires.core.NECore;
import net.nighthawkempires.core.users.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;

import static net.nighthawkempires.core.NECore.getScoreboardManager;

public class PluginListener implements Listener {

    @EventHandler
    public void onEnable(PluginEnableEvent event) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            NECore.getUserManager().loadUser(new User(player.getUniqueId()));
            getScoreboardManager().setupDefaultBoard(player);
            getScoreboardManager().startBoards(player);
        }
    }

    @EventHandler
    public void onDisable(PluginDisableEvent event) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            getScoreboardManager().stopBoards(player);
        }
        for (User user : NECore.getUserManager().getUserMap().values()) {
            NECore.getUserManager().saveUser(user);
        }
    }
}
