package net.nighthawkempires.core.listener;

import net.nighthawkempires.core.NECore;
import net.nighthawkempires.core.language.Lang;
import net.nighthawkempires.core.users.UserModel;
import net.nighthawkempires.core.utils.BossBarUtil;
import net.nighthawkempires.core.utils.LocationUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

import java.util.UUID;

import static net.nighthawkempires.core.NECore.*;

public class PlayerListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        if (!NECore.getUserRegistry().getRegisteredData().containsKey(player.getUniqueId().toString())) {
            NECore.getUserRegistry().loadFromDb(player.getUniqueId().toString());
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UserModel user = NECore.getUserRegistry().getUser(player.getUniqueId());

        switch (NECore.getSettings().server.getFrom(NECore.getSettings().server)) {
            case HUB:
                if (!user.playedHub()) {
                    user.setPlayedHub(true);
                    broadcastNewJoin(Lang.CHAT_TAG.getServerMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "Welcome " +
                            ChatColor.BLUE + "" + player.getName() + ChatColor.GRAY + "" + ChatColor.ITALIC + " to the " +
                            ChatColor.RED + "" + ChatColor.BOLD + "" + ChatColor.ITALIC + "HUB" + ChatColor.GRAY + "."));
                }
                break;
            case SURVIVAL:
                if (!user.playedSurvival()) {
                    user.setPlayedSurvival(true);
                    broadcastNewJoin(Lang.CHAT_TAG.getServerMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "Welcome " +
                            ChatColor.BLUE + "" + player.getName() + ChatColor.GRAY + "" + ChatColor.ITALIC + " to " +
                            ChatColor.DARK_BLUE + "" + ChatColor.BOLD + "" + ChatColor.ITALIC + "Nighthawk" +
                            ChatColor.DARK_RED + "" + ChatColor.BOLD + "" + ChatColor.ITALIC + " Survival" + ChatColor.GRAY + "."));
                    if (LocationUtil.hasSpawn(player.getWorld())) {
                        player.teleport(LocationUtil.getSpawn(player.getWorld()));
                    }
                }
                String[] motd = new String[]{
                        Lang.HEADER.getServerHeader(),
                        ChatColor.GRAY + "Welcome to " + ChatColor.DARK_BLUE + "" + ChatColor.ITALIC + "Nighthawk " +
                                ChatColor.DARK_RED + "" + ChatColor.ITALIC + "Survival" + ChatColor.GRAY + ", " + ChatColor.BLUE +
                                player.getName() + ChatColor.GRAY + ".",
                        ChatColor.GRAY + "Use " + ChatColor.RED + "/help " + ChatColor.GRAY + "or visit the help section at " +
                                ChatColor.RED + "/warp help" + ChatColor.GRAY + " for help.",
                        ChatColor.GRAY + "There are currently " + ChatColor.GOLD + Bukkit.getOnlinePlayers().size() + ChatColor.DARK_GRAY +
                                "/" + ChatColor.GOLD + Bukkit.getMaxPlayers() + ChatColor.GRAY + " player(s) online.",
                        "",
                        ChatColor.DARK_GRAY + "**" + ChatColor.GRAY + ChatColor.ITALIC + " The server is currently in testing, if you find" +
                                " any bugs/glitches please report them to a Staff Member" + ChatColor.DARK_GRAY + " **",
                        Lang.FOOTER.getMessage(),
                };
                player.sendMessage(motd);
                break;
            case TEST:
                if (!user.playedTest()) {
                    user.setPlayedTest(true);
                    broadcastNewJoin(Lang.CHAT_TAG.getServerMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "Welcome " +
                            ChatColor.BLUE + "" + player.getName() + ChatColor.GRAY + "" + ChatColor.ITALIC + " to " +
                            ChatColor.DARK_RED + "" + ChatColor.BOLD + "" + ChatColor.ITALIC + "Test" + ChatColor.GRAY + "."));
                }
                break;
            case PRISON:
                if (!user.playedPrison()) {
                    user.setPlayedPrison(true);
                    broadcastNewJoin(Lang.CHAT_TAG.getServerMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "Welcome " +
                            ChatColor.BLUE + "" + player.getName() + ChatColor.GRAY + "" + ChatColor.ITALIC + " to " +
                            ChatColor.GRAY + "" + ChatColor.BOLD + "" + ChatColor.ITALIC + "Hawkeye " +
                            ChatColor.GOLD + "" + ChatColor.BOLD + "" + ChatColor.ITALIC + "Prison" + ChatColor.GRAY + "."));
                    if (LocationUtil.hasSpawn(player.getWorld())) {
                        player.teleport(LocationUtil.getSpawn(player.getWorld()));
                    }
                }
                break;
            case FREEBUILD:
                if (!user.playedFreebuild()) {
                    user.setPlayedFreebuild(true);
                    broadcastNewJoin(Lang.CHAT_TAG.getServerMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "Welcome " +
                            ChatColor.BLUE + "" + player.getName() + ChatColor.GRAY + "" + ChatColor.ITALIC + " to " +
                            ChatColor.GREEN + "" + ChatColor.BOLD + "" + ChatColor.ITALIC + "Freebuild" + ChatColor.GRAY + "."));
                    if (LocationUtil.hasSpawn(player.getWorld())) {
                        player.teleport(LocationUtil.getSpawn(player.getWorld()));
                    }
                }
                break;
            case MINIGAMES:
                if (!user.playedMinigames()) {
                    user.setPlayedMinigames(true);
                    broadcastNewJoin(Lang.CHAT_TAG.getServerMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "Welcome " +
                            ChatColor.BLUE + "" + player.getName() + ChatColor.GRAY + "" + ChatColor.ITALIC + " to " +
                            ChatColor.AQUA + "" + ChatColor.BOLD + "" + ChatColor.ITALIC + "Minigames" + ChatColor.GRAY + "."));
                    if (LocationUtil.hasSpawn(player.getWorld())) {
                        player.teleport(LocationUtil.getSpawn(player.getWorld()));
                    }
                }
                break;
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
        getScoreboardManager().startBoards(player);
        getCodeHandler().setTabMenuHeaderFooter(player, Lang.HEADER.getServerHeader() + ChatColor.RESET + "\n  ","    \n" + ChatColor.DARK_GRAY + "»» " + ChatColor.GRAY + "play.nighthawkempires.net" + ChatColor.DARK_GRAY + " ««" + "\n" + Lang.FOOTER.getMessage());
        BossBarUtil.setDefaultBar(player);
        event.setJoinMessage(joinMessage);
    }

    @EventHandler (priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        UserModel user = NECore.getUserRegistry().getUser(player.getUniqueId());

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
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
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
        getChatFormat().sendMessage(getChatFormat().getFormattedMessage(event.getPlayer(), event.getMessage()));
        event.setCancelled(true);
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

    private void broadcastNewJoin(String message) {
        for (Player players : Bukkit.getOnlinePlayers()) {
            players.sendMessage(message);
        }
    }
}
