package net.nighthawkempires.core.chat.format;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import net.nighthawkempires.core.chat.ChatScope;
import net.nighthawkempires.core.chat.tag.PlayerTag;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.concurrent.ConcurrentMap;

public class ChatFormat {

    private List<PlayerTag> playerTags = new LinkedList<>();
    private ConcurrentMap<PlayerTag, Integer> priorityMap = Maps.newConcurrentMap();

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

    public TextComponent getTags(TextComponent parent, Player player, ChatScope scope) {
        sort();
        playerTags.stream().filter(tag -> tag.getScope().equals(scope) || ChatScope.ALL.equals(tag.getScope())).
                forEach(tag -> {
                    TextComponent component = tag.getComponentFor(player);
                    if (component != null) {
                        parent.addExtra(component.duplicate());
                    }
                });
        return parent;
    }

    public BaseComponent getFormattedMessage(Player player, ChatScope scope, String message) {
        TextComponent ret = new TextComponent("");
        ret = getTags(ret, player, scope);
        ret.setColor(net.md_5.bungee.api.ChatColor.GRAY);
        TextComponent next = new TextComponent(": ");
        next.setColor(net.md_5.bungee.api.ChatColor.DARK_GRAY);
        ret.addExtra(next);
        String finalMessage = ChatColor.WHITE + message;
        if (player.hasPermission("ns.")) {
            finalMessage = ChatColor.translateAlternateColorCodes('&', finalMessage);
        }
        for (BaseComponent component : TextComponent.fromLegacyText(finalMessage)) {
            ret.addExtra(component);
        }
        return ret;
    }

    public String getSerializedMessage(Player player, ChatScope scope, String message) {
        return ComponentSerializer.toString(getFormattedMessage(player, scope, message));
    }

    public boolean shouldCancelRedis(Player player) {
        for (PlayerTag tag : getPlayerTags()) {
            if (tag.cancelRedis(player)) {
                return true;
            }
        }
        return false;
    }
}
