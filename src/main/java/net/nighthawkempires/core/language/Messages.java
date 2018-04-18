package net.nighthawkempires.core.language;

import net.nighthawkempires.core.NECore;
import net.nighthawkempires.core.file.FileType;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

public enum Messages {

    HEADER, FOOTER, BLANK, CHAT, NO_PERMISSIONS, INVALID_PLAYER, SYNTAX_ERROR, INSUFFICIENT_FUNDS, SCOREBOARD_HEADER

    ;public String getMessage() {
        return ChatColor.translateAlternateColorCodes('&', getConfig().getString("MESSAGES."
                + this.name())).replaceAll("%SERVER_TAG%", Tags.valueOf(NECore.getSettings().SERVER.getName()).getTag());
    }

    public String getMessage(String message) {
        return ChatColor.translateAlternateColorCodes('&', getMessage() + message);
    }

    public String getServerMessage(String message) {
        return Tags.valueOf(NECore.getSettings().SERVER.getName()).getTag() + getMessage(message);
    }

    public String[] getFormattedMessages(String[] messages) {
        if (messages == null)return null;

        for (int i = 0; i < messages.length; i++) {
            messages[i] = ChatColor.translateAlternateColorCodes('&', messages[i]);
        }
        return messages;
    }

    public List<String> getMessageList() {
        return getConfig().getStringList("LISTS." + this.name());
    }

    public String formatMSGIn(Player player, String message) {
        return ChatColor.DARK_GRAY + ">[" + ChatColor.BLUE + player.getName() + ChatColor.DARK_GRAY + "]--> " +
                ChatColor.GRAY + message;
    }

    public String formatMSGIn(String string, String message) {
        return ChatColor.DARK_GRAY + ">[" + ChatColor.BLUE + string + ChatColor.DARK_GRAY + "]--> " + ChatColor.GRAY +
                message;
    }

    public String formatMSGOut(Player player, String message) {
        return ChatColor.DARK_GRAY + "<--[" + ChatColor.BLUE + player.getName() + ChatColor.DARK_GRAY + "]< " +
                ChatColor.GRAY + message;
    }


    public String getInfo(String name) {
        return ChatColor.DARK_GRAY + "Info" + ChatColor.GRAY + ": " + name;
    }

    public String getListName(String name) {
        return ChatColor.DARK_GRAY + "List" + ChatColor.GRAY + ": " + name;
    }

    public String getCommandName(String name) {
        return ChatColor.DARK_GRAY + "Command" + ChatColor.GRAY + ": " + name;
    }

    public String getCommand(String name, String desc) {
        return ChatColor.AQUA + "/" + name + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + desc + ".";
    }

    public String getCommand(String name, String args, String desc) {
        return ChatColor.AQUA + "/" + name + " " + ChatColor.DARK_AQUA + args + ChatColor.DARK_GRAY + " - " +
                ChatColor.GRAY + desc + ".";
    }

    public String getHelpTopic(String topic) {
        return ChatColor.DARK_GRAY + "Help" + ChatColor.GRAY + ": " + topic;
    }

    public String getHelpLine(int page, int total) {
        return ChatColor.DARK_GRAY + "Help Topic Page" + ChatColor.GRAY + ": " + ChatColor.DARK_GRAY + "[" +
                ChatColor.GOLD + "" + ChatColor.UNDERLINE + page + ChatColor.DARK_GRAY + "/" + ChatColor.GOLD
                + "" + ChatColor.UNDERLINE + total + ChatColor.DARK_GRAY + "]";
    }

    private FileConfiguration getConfig() {
        return NECore.getFileManager().get(FileType.MESSAGES);
    }
}
