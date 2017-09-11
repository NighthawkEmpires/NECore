package net.nighthawkempires.core.listener;

import net.nighthawkempires.core.NECore;
import net.nighthawkempires.core.chat.ChatScope;
import net.nighthawkempires.core.language.Lang;
import net.nighthawkempires.core.server.Server;
import net.nighthawkempires.core.users.User;
import net.nighthawkempires.core.utils.BossBarUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import static net.nighthawkempires.core.NECore.*;

public class PlayerListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        User user = new User(player.getUniqueId());

        if (getUserManager().userLoaded(player.getUniqueId())) {
            getUserManager().saveUser(getUserManager().getUser(player.getUniqueId()));
        }

        if (!getUserManager().userExists(player.getUniqueId())) {
            getUserManager().createUser(user);
        } else {
            getUserManager().loadUser(user);
        }

        if (NECore.getSettings().server.equals(Server.HUB)) {
            if (!user.hub()) {
                for (Player players : Bukkit.getOnlinePlayers()) {
                    players.sendMessage(ChatColor.GRAY + "" + ChatColor.BOLD + "Welcome " + ChatColor.BLUE + "" + ChatColor.BOLD + player.getName() + ChatColor.GRAY + ""
                            + ChatColor.BOLD + " to our " + ChatColor.RED + "" + ChatColor.BOLD + "" + ChatColor.ITALIC + "HUB" + ChatColor.GRAY + "" + ChatColor.BOLD + ".");
                }
                player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "Welcome to our Server! Type " + ChatColor.RED + "/help " + ChatColor.GRAY + "to get started!");
                user.setHub(true);
            }
        } else if (NECore.getSettings().server.equals(Server.NS)) {
            if (!user.survival()) {
                for (Player players : Bukkit.getOnlinePlayers()) {
                    players.sendMessage(ChatColor.GRAY + "" + ChatColor.BOLD + "Welcome " + ChatColor.BLUE + "" + ChatColor.BOLD + player.getName() + ChatColor.GRAY + ""
                            + ChatColor.BOLD + " to" + ChatColor.DARK_BLUE + "" + ChatColor.BOLD + " Nighthawk" + ChatColor.DARK_RED + "" + ChatColor.BOLD + " Survival"
                            + ChatColor.GRAY + "" + ChatColor.BOLD + ".");
                }
                player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "Welcome to our Server! Type " + ChatColor.RED + "/help " + ChatColor.GRAY + "to get started!");
                user.setSurvival(true);
            }
        }

        String joinMessage;
        String joinTag = ChatColor.DARK_GRAY + " [" + ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "+" + ChatColor.DARK_GRAY + "] ";
        if (player.hasPermission("ne.staff")) {
            String staffTag = ChatColor.AQUA + "" + ChatColor.ITALIC + "" + ChatColor.BOLD + "Staff ";
            joinMessage = joinTag + staffTag + user.getDisplayName() + ChatColor.GRAY + " joined the game.";
        } else {
            joinMessage = joinTag + user.getDisplayName() + ChatColor.GRAY + " joined the game.";
        }
        getScoreboardManager().setupDefaultBoard(player);
        getScoreboardManager().startBoards(player);
        getCodeHandler().setTabMenuHeaderFooter(player, Lang.HEADER.getServerHeader(), Lang.FOOTER.getMessage());
        BossBarUtil.setPlayerBar(player, "&7Welcome, " + ChatColor.BLUE + player.getName(), 0.1);
        event.setJoinMessage(joinMessage);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        User user = (getUserManager().userLoaded(player.getUniqueId()) ? getUserManager().getUser(player.getUniqueId()) : getUserManager().getTempUser(player.getUniqueId()));

        String quitMessage;
        String quitTag = ChatColor.DARK_GRAY + " [" + ChatColor.DARK_RED + "" + ChatColor.BOLD + "-" + ChatColor.DARK_GRAY + "] ";
        if (player.hasPermission("ne.staff")) {
            String staffTag = ChatColor.AQUA + "" + ChatColor.ITALIC + "" + ChatColor.BOLD + "Staff ";
                quitMessage = quitTag + staffTag + user.getDisplayName() + ChatColor.GRAY + " left the game.";
        } else {
                quitMessage = quitTag + user.getDisplayName() + ChatColor.GRAY + " left the game.";
        }
        getScoreboardManager().stopBoards(player);
        event.setQuitMessage(quitMessage);

        getUserManager().saveUser(user);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        getChatManager().sendMessage(getChatFormat().getFormattedMessage(event.getPlayer(), ChatScope.LOCAL, event.getMessage()));
        event.getRecipients().clear();
    }
}
