package net.nighthawkempires.core.chat.tag;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public abstract class PlayerTag {

    public abstract String getName();

    public TextComponent getComponentFor(Player player) {
        return new TextComponent(TextComponent.fromLegacyText(getFor(player)));
    }

    public String getFor(Player player) {
        throw new UnsupportedOperationException("This method is not supported.");
    }

    public abstract int getPriority();
}
