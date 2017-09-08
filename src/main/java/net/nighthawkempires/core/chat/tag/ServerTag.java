package net.nighthawkempires.core.chat.tag;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.nighthawkempires.core.NECore;
import net.nighthawkempires.core.server.Server;
import org.bukkit.entity.Player;

public class ServerTag extends PlayerTag {

    public String getName() {
        return "server";
    }

    public TextComponent getComponentFor(Player player) {
        TextComponent tag = new TextComponent("[");
        tag.setColor(ChatColor.DARK_GRAY);
        if (NECore.getSettings().server.equals(Server.HUB)) {
            TextComponent mid = new TextComponent("HUB");
            mid.setColor(ChatColor.RED);
            mid.setBold(true);
            mid.setItalic(true);
            tag.addExtra(mid);
            tag.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(ChatColor.RED + "" + ChatColor.BOLD + "HUB" + ChatColor.RESET + "\n" + ChatColor.GRAY + "Hub Server")));
        } else if (NECore.getSettings().server.equals(Server.NS)) {
            TextComponent n = new TextComponent("N");
            n.setColor(ChatColor.DARK_BLUE);
            n.setBold(true);
            n.setItalic(true);
            TextComponent s = new TextComponent("S");
            s.setColor(ChatColor.DARK_RED);
            s.setBold(true);
            s.setItalic(true);
            tag.addExtra(n);
            tag.addExtra(s);
            tag.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&',
                    "  &r&1&lNighthawk &4&lSurvival  \n&r&7Survival Server!"))));
        } else if (NECore.getSettings().server.equals(Server.CREATIVE)) {

        }
        tag.addExtra("]");
        return tag;
    }

    public int getPriority() {
        return 1;
    }
}
