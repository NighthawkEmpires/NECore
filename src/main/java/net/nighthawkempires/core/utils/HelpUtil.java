package net.nighthawkempires.core.utils;

import com.google.common.collect.Lists;
import net.nighthawkempires.core.language.Lang;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class HelpUtil {



    public static List<String> getPlugins(Player player) {
        List<String> plugins = Lists.newArrayList();
        for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
            if (plugin.getDescription().getCommands() != null) {
                plugins.add(plugin.getName());
            }
        }
        plugins.sort(Collator.getInstance());
        return plugins;
    }

    public static List<String> getCommands(Player player) {
        List<String> commands = Lists.newArrayList();
        for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
            if (plugin.getDescription().getCommands() != null) {
                commands.addAll(plugin.getDescription().getCommands().keySet());
            }
        }

        commands.sort(Collator.getInstance());
        return commands;
    }

    public static List<String> getHelpTopics(Player player) {
        List<String> plugins = Lists.newArrayList();
        List<String> commands = Lists.newArrayList();
        for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
            if (plugin.getDescription().getCommands() != null) {
                plugins.add(ChatColor.BLUE + plugin.getName() + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + (plugin.getDescription().getDescription() != null ? plugin.getDescription().getDescription() : ""));
                for (String s : plugin.getDescription().getCommands().keySet()) {
                    commands.add(ChatColor.AQUA + "/" + s + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + (Bukkit.getPluginCommand(s).getDescription() != null ? Bukkit.getPluginCommand(s).getDescription() : ""));
                }
            }
        }

        plugins.sort(Collator.getInstance());
        commands.sort(Collator.getInstance());

        List<String> together = Lists.newArrayList();
        together.addAll(new ArrayList<>(plugins));
        together.addAll(new ArrayList<>(commands));

        return together;
    }

    public static int getTotalPages(Player player) {
        return (int) Math.ceil((double) getHelpTopics(player).size() / 10);
    }

    public static void sendPage(Player player, int page) {
        int displayPage = page;
        page = page -1;

        int start = 10 * page;
        int finish;

        if (start + 10 > getHelpTopics(player).size()) {
            finish = getHelpTopics(player).size();
        } else {
            finish = start + 10;
        }

        List<String> cmds = Lists.newArrayList();
        for (int i = start; i < finish; i++) {
            cmds.add(getHelpTopics(player).get(i));
        }

        String[] help = new String[] {
                Lang.HEADER.getServerHeader(),
                Lang.HELP.getHelpLine(displayPage, getTotalPages(player)),
                Lang.FOOTER.getMessage(),
        };
        player.sendMessage(help);
        for (String string : cmds) {
            player.sendMessage(string);
        }
        player.sendMessage(Lang.FOOTER.getMessage());
    }

    public static void sendPage(ConsoleCommandSender console, int page) {
        int displayPage = page;
        page = page -1;

        int start = 10 * page;
        int finish;

        if (start + 10 > getHelpTopics(null).size()) {
            finish = getHelpTopics(null).size();
        } else {
            finish = start + 10;
        }

        List<String> cmds = Lists.newArrayList();
        for (int i = start; i < finish; i++) {
            cmds.add(getHelpTopics(null).get(i));
        }

        String[] help = new String[] {
                Lang.HEADER.getServerHeader(),
                Lang.HELP.getHelpLine(displayPage, getTotalPages(null)),
                Lang.FOOTER.getMessage(),
        };
        console.sendMessage(help);
        for (String string : cmds) {
            console.sendMessage(string);
        }
        console.sendMessage(Lang.FOOTER.getMessage());
    }
}
