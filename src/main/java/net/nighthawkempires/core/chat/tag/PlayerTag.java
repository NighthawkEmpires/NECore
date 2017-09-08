package net.nighthawkempires.core.chat.tag;

import net.md_5.bungee.api.chat.TextComponent;
import net.nighthawkempires.core.chat.ChatScope;
import org.bukkit.entity.Player;

public abstract class PlayerTag {

    public abstract String getName();

    public TextComponent getComponentFor(Player player) {
        return new TextComponent(TextComponent.fromLegacyText(getFor(player)));
    }

    public String getFor(Player player) {
        throw new UnsupportedOperationException("This method is not supported.");
    }

    public ChatScope getScope() {
        return ChatScope.ALL;
    }

    public boolean cancelRedis(Player player) {
        return false;
    }

    public abstract int getPriority();
}
