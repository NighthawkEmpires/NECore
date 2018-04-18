package net.nighthawkempires.core.chat.format;

import com.google.common.collect.*;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.nighthawkempires.core.chat.tag.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.concurrent.ConcurrentMap;

public class ChatFormat {

    private List<PlayerTag> playerTags;

    private ConcurrentMap<PlayerTag, Integer> priorityMap;
    private ConcurrentMap<String, Boolean> cancelMessage;

    public ChatFormat() {
        playerTags = Lists.newArrayList();
        priorityMap = Maps.newConcurrentMap();
        cancelMessage = Maps.newConcurrentMap();
    }

    public ChatFormat add(PlayerTag playerTag) {
        priorityMap.put(playerTag, playerTag.getPriority());
        return this;
    }

    public void sort() {
        playerTags.clear();

        List<Integer> order = Lists.newArrayList();
        order.addAll(priorityMap.values());
        Collections.sort(order);

        for (Integer integer : order) {
            for (PlayerTag playerTag : priorityMap.keySet()) {
                if (playerTag.getPriority() == integer) {
                    playerTags.add(playerTag);
                }
            }
        }
    }

    public ChatFormat addAll(Collection<PlayerTag> playerTags) {
        playerTags.forEach(this::add);
        return this;
    }

    public ChatFormat addAll(PlayerTag[] playerTags) {
        Arrays.asList(playerTags).forEach(this::add);
        return this;
    }

    public ImmutableList<PlayerTag> getPlayerTags() {
        return ImmutableList.copyOf(playerTags);
    }

    public TextComponent getTags(TextComponent parent, Player player) {
        sort();
        for (PlayerTag playerTag : playerTags) {
            TextComponent component = playerTag.getComponentFor(player);
            if (component != null) {
                parent.addExtra(component.duplicate());
            }
        }
        return parent;
    }

    public BaseComponent getFormattedMessage(Player player, String message) {
        TextComponent component = new TextComponent("");
        component = getTags(component, player);
        component.setColor(net.md_5.bungee.api.ChatColor.GRAY);
        TextComponent next = new TextComponent("» ");
        next.setColor(net.md_5.bungee.api.ChatColor.DARK_GRAY);
        component.addExtra(next);
        String finalMessage = ChatColor.WHITE + message;
        if (player.hasPermission("ne.colorchat")) {
            finalMessage = ChatColor.translateAlternateColorCodes('&', finalMessage);
        }
        for (BaseComponent components : TextComponent.fromLegacyText(finalMessage)) {
            component.addExtra(components);
        }
        return component;
    }

    public BaseComponent getFormattedMessage(ConsoleCommandSender console, String message) {
        TextComponent component = new TextComponent("");
        component.setColor(net.md_5.bungee.api.ChatColor.GRAY);
        TextComponent next = new TextComponent("» ");
        next.setColor(net.md_5.bungee.api.ChatColor.DARK_GRAY);
        component.addExtra(new ConsoleTag().getComponentFor(null));
        component.addExtra(next);
        String finalMessage = ChatColor.WHITE + message;
        if (console.hasPermission("ne.colorchat")) {
            finalMessage = ChatColor.translateAlternateColorCodes('&', finalMessage);
        }
        for (BaseComponent components : TextComponent.fromLegacyText(finalMessage)) {
            component.addExtra(components);
        }
        return component;
    }

    public void sendMessage(BaseComponent baseComponent) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.spigot().sendMessage(baseComponent);
        }
        Bukkit.getConsoleSender().spigot().sendMessage(baseComponent);
    }

    public void setCancelled(String message, boolean cancelled) {
        this.cancelMessage.put(message, cancelled);
    }

    public boolean isCancelled(String message) {
        return this.cancelMessage.containsKey(message) && this.cancelMessage.get(message);
    }

    public void clear(String message) {
        this.cancelMessage.remove(message);
    }
}
