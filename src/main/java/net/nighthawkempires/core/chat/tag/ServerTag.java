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
            tag.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&',
                    "&r&8&lServer&7&l: &c&l&oHUB"))));
        } else if (NECore.getSettings().server.equals(Server.SUR)) {
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
                    "&r&8&lServer&7&l: &1&l&oNighthawk&r &4&l&oSurvival"))));
        } else if (NECore.getSettings().server.equals(Server.FRB)) {
            TextComponent mid = new TextComponent("FRB");
            mid.setColor(ChatColor.GREEN);
            mid.setBold(true);
            mid.setItalic(true);
            tag.addExtra(mid);
            tag.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&',
                    "&r&8&lServer&7&l: &a&l&oFreebuild"))));
        } else if (NECore.getSettings().server.equals(Server.PRS)) {
            TextComponent mid = new TextComponent("PRS");
            mid.setColor(ChatColor.GOLD);
            mid.setBold(true);
            mid.setItalic(true);
            tag.addExtra(mid);
            tag.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&',
                    "&r&8&lServer&7&l: &6&l&oPrison"))));
        } else if (NECore.getSettings().server.equals(Server.MIN)) {
            TextComponent mid = new TextComponent("MIN");
            mid.setColor(ChatColor.AQUA);
            mid.setBold(true);
            mid.setItalic(true);
            tag.addExtra(mid);
            tag.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&',
                    "&r&8&lServer&7&l: &b&l&oMinigames"))));
        } else if (NECore.getSettings().server.equals(Server.TEST)) {
            TextComponent mid = new TextComponent("TEST");
            mid.setColor(ChatColor.DARK_RED);
            mid.setBold(true);
            mid.setItalic(true);
            tag.addExtra(mid);
            tag.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&',
                    "&r&8&lServer&7&l: &4&l&oTest"))));
        }
        tag.addExtra("]");
        return tag;
    }

    public int getPriority() {
        return 1;
    }
}
