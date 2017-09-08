package net.nighthawkempires.core.language;

import net.nighthawkempires.core.NECore;
import net.nighthawkempires.core.server.Server;
import org.bukkit.ChatColor;

public enum Lang {

    NO_PERM("&cSorry, you do not have enough permissions to do this."),
    SYNTAX_ERROR("&cIt appears there has been an error in syntax."),
    PLAYER_NULL("&cThat player is not online or does not exist."),
    INSUF_FUND("&cYou do not have enough money to do this."),
    CMD_NAME(""),
    CMD_HELP(""),
    LIST(""),
    INFO(""),
    MESS_ERROR("Sorry, there was an error in sending your message"),
    BAL_FORMAT(""),
    CHAT_TAG(""),
    FOOTER("&8&l&m---------------------------------------------"),
    HEADER(""),
    SCOREBOARD(""),
    NS_CHAT_TAG("&1&l&oN&4&l&oS&8&l&o>&r "),
    NS_HEADER("&8&l&m---------------------<&1&l&oN&4&l&oS&8&l&m>--------------------"),
    NS_TAG1(" &8[&1&l&oN&4&l&oS&8] "),
    NS_TAG2(" &8&m&l-<<-<&1&l&oN&4&l&oS&8&m&l>->>- "),
    NS_SCOREBOARD("&8&m-<<-<&1&l&oN&4&l&oS&8&m>->>-"),
    HUB_CHAT_TAG("&c&l&oHUB&8&l&o>&r "),
    HUB_HEADER("&8&l&m--------------------<&c&l&oHUB&8&l&m>---------------------"),
    HUB_TAG1(" &8[&c&l&oHUB&8] "),
    HUB_TAG2(" &8&m&l-<<-<&c&l&oHUB&8&m&l>->>- "),
    HUB_SCOREBOARD("&8&m-<<-<&c&l&oHUB&8&m>->>-");

    private String message;

    Lang(String message) {
        this.message = message;
    }

    public String getMessage() {
        return ChatColor.translateAlternateColorCodes('&', this.message);
    }

    public String format(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public String getServerMessage(String message) {
        String smessage = "";
        if (NECore.getSettings().server.equals(Server.HUB)) {
            smessage = format(HUB_TAG1 + message);
        } else if (NECore.getSettings().server.equals(Server.NS)) {
            smessage = format(NS_TAG1 + message);
        }
        return smessage;
    }

    public String getServerMessage() {
        String smessage = "";
        if (NECore.getSettings().server.equals(Server.HUB)) {
            smessage = format(HUB_TAG1 + message);
        } else if (NECore.getSettings().server.equals(Server.NS)) {
            smessage = format(NS_TAG1 + message);
        }
        return smessage;
    }

    public String getServerBoard() {
        if (NECore.getSettings().server.equals(Server.HUB)) {
            return HUB_SCOREBOARD.getMessage();
        } else if (NECore.getSettings().server.equals(Server.NS)) {
            return NS_SCOREBOARD.getMessage();
        }
        return HUB_SCOREBOARD.getMessage();
    }

    public String getServerHeader() {
        if (NECore.getSettings().server.equals(Server.HUB)) {
            return HUB_HEADER.getMessage();
        } else if (NECore.getSettings().server.equals(Server.NS)) {
            return NS_HEADER.getMessage();
        }
        return HUB_HEADER.getMessage();
    }

    public String getServerChatTag() {
        if (NECore.getSettings().server.equals(Server.HUB)) {
            return HUB_CHAT_TAG.getMessage();
        } else if (NECore.getSettings().server.equals(Server.NS)) {
            return NS_CHAT_TAG.getMessage();
        }
        return HUB_CHAT_TAG.getMessage();
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
        return ChatColor.AQUA + "/" + name + " " + ChatColor.DARK_AQUA + args + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + desc + ".";
    }

    public String[] format(String[] messages) {
        if (messages == null) {
            return null;
        } else {
            for (int message = 0; message < messages.length; message++) {
                messages[message] = format(messages[message]);
            }
            return messages;
        }
    }

    public String balance(double balance) {
        String bal = String.valueOf(balance);
        return bal;
    }
}
