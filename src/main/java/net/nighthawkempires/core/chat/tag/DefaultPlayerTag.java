package net.nighthawkempires.core.chat.tag;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public class DefaultPlayerTag extends PlayerTag {

    private final String name;
    private final String permissionNode;
    private final TextComponent tagText;
    private final int priority;

    public DefaultPlayerTag(String name, String permissionNode, TextComponent tagText, int priority) {
        this.name = name;
        this.permissionNode = permissionNode;
        this.tagText = tagText;
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public TextComponent getComponentFor(Player tagSource) {
        if (tagSource.hasPermission(permissionNode)) {
            return tagText;
        }
        return null;
    }

    public int getPriority() {
        return priority;
    }
}
