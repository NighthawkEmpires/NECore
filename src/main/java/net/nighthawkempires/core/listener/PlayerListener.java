package net.nighthawkempires.core.listener;

import net.nighthawkempires.core.NECore;
import net.nighthawkempires.core.chat.ChatScope;
import net.nighthawkempires.core.chat.ChatType;
import net.nighthawkempires.core.language.Lang;
import net.nighthawkempires.core.server.Server;
import net.nighthawkempires.core.users.User;
import net.nighthawkempires.core.utils.BossBarUtil;
import net.nighthawkempires.core.utils.LocationUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

import static net.nighthawkempires.core.NECore.*;

public class PlayerListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (getUserManager().getUserMap().containsKey(player.getUniqueId())) {
            getUserManager().getUserMap().remove(player.getUniqueId());
        }

        User user = new User(player.getUniqueId());

        if (!getUserManager().userExists(player.getUniqueId())) {
            getUserManager().createUser(user);
            if (LocationUtil.hasSpawn(player.getWorld())) {
                player.teleport(LocationUtil.getSpawn(player.getWorld()));
            }
        } else {
            getUserManager().loadUser(user);
        }

        if (NECore.getSettings().server.equals(Server.HUB)) {
            if (!user.hub()) {
                for (Player players : Bukkit.getOnlinePlayers()) {
                    players.sendMessage(ChatColor.GRAY + "" + ChatColor.BOLD + "Welcome " + ChatColor.BLUE + "" + ChatColor.BOLD + player.getName() + ChatColor.GRAY + ""
                            + ChatColor.BOLD + " to our " + ChatColor.RED + "" + ChatColor.BOLD + "" + ChatColor.ITALIC + "HUB" + ChatColor.GRAY + "" + ChatColor.BOLD + " server.");
                }
                player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "Welcome to our Server! Type " + ChatColor.RED + "/help " + ChatColor.GRAY + "to get started!");
                user.setHub(true);
            }
        } else if (NECore.getSettings().server.equals(Server.SUR)) {
            if (!user.sur()) {
                for (Player players : Bukkit.getOnlinePlayers()) {
                    players.sendMessage(ChatColor.GRAY + "" + ChatColor.BOLD + "Welcome " + ChatColor.BLUE + "" + ChatColor.BOLD + player.getName() + ChatColor.GRAY + ""
                            + ChatColor.BOLD + " to" + ChatColor.DARK_BLUE + "" + ChatColor.BOLD + " Nighthawk" + ChatColor.DARK_RED + "" + ChatColor.BOLD + " Survival"
                            + ChatColor.GRAY + "" + ChatColor.BOLD + ".");
                }
                player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "Welcome to our Server! Type " + ChatColor.RED + "/help " + ChatColor.GRAY + "to get started!");
                user.setSur(true);
            }
            String[] motd = new String[] {
                    Lang.HEADER.getServerHeader(),
                    ChatColor.GRAY + "Welcome to " + ChatColor.DARK_BLUE + "" + ChatColor.BOLD + "" + ChatColor.ITALIC + "Nighthawk " + ChatColor.DARK_RED + "" + ChatColor.ITALIC + "" + ChatColor.BOLD + "Survival" + ChatColor.GRAY + ".",
                    ChatColor.GRAY + "Type " + ChatColor.RED + "/help" + ChatColor.GRAY + " or visit the help section at " + ChatColor.RED + "/warp help" + ChatColor.GRAY + ".",
                    ChatColor.GRAY + "There are currently " + ChatColor.GOLD + Bukkit.getOnlinePlayers().size() + ChatColor.DARK_GRAY + "/" + ChatColor.GOLD + Bukkit.getServer().getMaxPlayers() + ChatColor.GRAY + " player(s) online.",
                    Lang.FOOTER.getMessage(),
            };
            player.sendMessage(motd);
        } else if (NECore.getSettings().server.equals(Server.PRS)) {
            if (!user.prs()) {
                for (Player players : Bukkit.getOnlinePlayers()) {
                    players.sendMessage(ChatColor.GRAY + "" + ChatColor.BOLD + "Welcome " + ChatColor.BLUE + "" + ChatColor.BOLD + player.getName() + ChatColor.GRAY + ""
                            + ChatColor.BOLD + " to our " + ChatColor.GOLD + "" + ChatColor.BOLD + "" + ChatColor.ITALIC + "Prison" + ChatColor.GRAY + "" + ChatColor.BOLD + " server.");
                }
                player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "Welcome to our Server! Type " + ChatColor.RED + "/help " + ChatColor.GRAY + "to get started!");
                user.setPrs(true);
            }
        } else if (NECore.getSettings().server.equals(Server.FRB)) {
            if (!user.frb()) {
                for (Player players : Bukkit.getOnlinePlayers()) {
                    players.sendMessage(ChatColor.GRAY + "" + ChatColor.BOLD + "Welcome " + ChatColor.BLUE + "" + ChatColor.BOLD + player.getName() + ChatColor.GRAY + ""
                            + ChatColor.BOLD + " to our " + ChatColor.GREEN + "" + ChatColor.BOLD + "" + ChatColor.ITALIC + "Freebuild" + ChatColor.GRAY + "" + ChatColor.BOLD + " server.");
                }
                player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "Welcome to our Server! Type " + ChatColor.RED + "/help " + ChatColor.GRAY + "to get started!");
                user.setFrb(true);
            }
        } else if (NECore.getSettings().server.equals(Server.MIN)) {
            if (!user.min()) {
                for (Player players : Bukkit.getOnlinePlayers()) {
                    players.sendMessage(ChatColor.GRAY + "" + ChatColor.BOLD + "Welcome " + ChatColor.BLUE + "" + ChatColor.BOLD + player.getName() + ChatColor.GRAY + ""
                            + ChatColor.BOLD + " to our " + ChatColor.RED + "" + ChatColor.BOLD + "" + ChatColor.ITALIC + "Minigames" + ChatColor.GRAY + "" + ChatColor.BOLD + " server.");
                }
                player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "Welcome to our Server! Type " + ChatColor.RED + "/help " + ChatColor.GRAY + "to get started!");
                user.setMin(true);
            }
        } else if (NECore.getSettings().server.equals(Server.TEST)) {
            if (!user.test()) {
                for (Player players : Bukkit.getOnlinePlayers()) {
                    players.sendMessage(ChatColor.GRAY + "" + ChatColor.BOLD + "Welcome " + ChatColor.BLUE + "" + ChatColor.BOLD + player.getName() + ChatColor.GRAY + ""
                            + ChatColor.BOLD + " to our " + ChatColor.DARK_RED + "" + ChatColor.BOLD + "" + ChatColor.ITALIC + "TEST" + ChatColor.GRAY + "" + ChatColor.BOLD + " server.");
                }
                player.sendMessage(Lang.CHAT_TAG.getServerChatTag() + ChatColor.GRAY + "Welcome to our Server! Type " + ChatColor.RED + "/help " + ChatColor.GRAY + "to get started!");
                user.setTest(true);
            }
        }

        String joinMessage;
        String joinTag = ChatColor.DARK_GRAY + " [" + ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "+" + ChatColor.DARK_GRAY + "] ";
        if (player.hasPermission("ne.staff")) {
            String staffTag = ChatColor.AQUA + "" + ChatColor.ITALIC + "" + ChatColor.BOLD + "Staff ";
            joinMessage = joinTag + staffTag + ChatColor.translateAlternateColorCodes('&', user.getDisplayName()) + ChatColor.GRAY + " joined the game.";
        } else {
            joinMessage = joinTag + ChatColor.translateAlternateColorCodes('&', user.getDisplayName()) + ChatColor.GRAY + " joined the game.";
        }
        player.setDisplayName(ChatColor.translateAlternateColorCodes('&', user.getDisplayName()));
        getScoreboardManager().setupDefaultBoard(player);
        getScoreboardManager().startBoards(player);
        getCodeHandler().setTabMenuHeaderFooter(player, Lang.HEADER.getServerHeader() + ChatColor.RESET + "\n  ","    \n" + ChatColor.DARK_GRAY + "»» " + ChatColor.GRAY + "play.nighthawkempires.net" + ChatColor.DARK_GRAY + " ««" + "\n" + Lang.FOOTER.getMessage());
        BossBarUtil.setPlayerBar(player, ChatColor.RED + "" + ChatColor.UNDERLINE + "**opening sale** " + ChatColor.GRAY + "store.nighthawkempires.com " + ChatColor.RED + "" + ChatColor.UNDERLINE + "**50% off**", 1.0, BarColor.RED, BarStyle.SOLID);
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
                quitMessage = quitTag + staffTag + ChatColor.translateAlternateColorCodes('&', user.getDisplayName()) + ChatColor.GRAY + " left the game.";
        } else {
                quitMessage = quitTag + ChatColor.translateAlternateColorCodes('&', user.getDisplayName()) + ChatColor.GRAY + " left the game.";
        }
        getScoreboardManager().stopBoards(player);
        event.setQuitMessage(quitMessage);

        getUserManager().saveUser(user);
        if (getUserManager().getUserMap().containsKey(player.getUniqueId())) {
            getUserManager().getUserMap().remove(player.getUniqueId());
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (NECore.getChatManager().getChatType(player) == ChatType.CUSTOM) {
            return;
        }
        if (getMuteManager().isMuted(player.getUniqueId())){
            player.sendMessage(getMuteManager().getMuteInfo(player.getUniqueId()));
            event.setCancelled(true);
            return;
        }
        if (getChatFormat().isCancelled(event.getMessage())) {
            event.setCancelled(true);
            getChatFormat().clear(event.getMessage());
            return;
        }
        getChatManager().sendMessage(getChatFormat().getFormattedMessage(event.getPlayer(), ChatScope.LOCAL, event.getMessage()));
        event.getRecipients().clear();
    }

    @EventHandler
    public void onJoin(AsyncPlayerPreLoginEvent event) {
        UUID uuid = event.getUniqueId();
        if (getBanManager().isBanned(uuid)) {
            event.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_BANNED);
            event.setKickMessage(getBanManager().getBanInfo(uuid));
        }
    }
}
