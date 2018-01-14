package net.nighthawkempires.core.chat.tag;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public class SpecificPlayerTag extends PlayerTag {

    private final String name;
    private final String playerName;
    private final TextComponent tag;
    private final int priority;

    public SpecificPlayerTag(String name, String playerName, TextComponent tag, int priority) {
        this.name = name;
        this.playerName = playerName;
        this.tag = tag;
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public TextComponent getComponentFor(Player player) {
        if (player.getName().equals(playerName)) {
            return tag;
        }
        return null;
    }

    public int getPriority() {
        return priority;
    }
}
