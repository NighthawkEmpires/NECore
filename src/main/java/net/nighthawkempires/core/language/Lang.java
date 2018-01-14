package net.nighthawkempires.core.language;

import com.google.common.collect.Lists;
import net.nighthawkempires.core.NECore;
import net.nighthawkempires.core.server.Server;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

public enum Lang {

    NO_PERM("&cSorry, you do not have enough permissions to do this."),
    SYNTAX_ERROR("&cIt appears there has been an error in syntax."),
    PLAYER_NULL("&cThat player is not online or does not exist."),
    INSUF_FUND("&cYou do not have enough money to do this."),
    CMD_NAME(""),
    CMD_HELP(""),
    LIST(""),
    INFO(""),
    HELP(""),
    MESS_ERROR("Sorry, there was an error in sending your message"),
    MESSAGE(""),
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
    HUB_SCOREBOARD("&8&m-<<-<&c&l&oHUB&8&m>->>-"),
    PRS_CHAT_TAG("&6&l&oPRISON&8&l&o>&r "),
    PRS_HEADER("&8&l&m--------------------<&6&l&oPRISON&8&l&m>---------------------"),
    PRS_TAG1(" &8[&6&l&oPRISON&8] "),
    PRS_TAG2(" &8&m&l-<<-<&6&l&oPRISON&8&m&l>->>- "),
    PRS_SCOREBOARD("&8&m-<<-<&6&l&oPRISON&8&m>->>-"),
    FRB_CHAT_TAG("&a&l&oFREEBUILD&8&l&o>&r "),
    FRB_HEADER("&8&l&m--------------------<&a&l&oFREEBUILD&8&l&m>---------------------"),
    FRB_TAG1(" &8[&a&l&oFREEBUILD&8] "),
    FRB_TAG2(" &8&m&l-<<-<&a&l&oFRB&8&m&l>->>- "),
    FRB_SCOREBOARD("&8&m-<<-<&a&l&oFRB&8&m>->>-"),
    MIN_CHAT_TAG("&b&l&oMINIGAMES&8&l&o>&r "),
    MIN_HEADER("&8&l&m--------------------<&b&l&oMINIGAMES&8&l&m>---------------------"),
    MIN_TAG1(" &8[&b&l&oMINIGAME&8] "),
    MIN_TAG2(" &8&m&l-<<-<&b&l&oMINIGAMES&8&m&l>->>- "),
    MIN_SCOREBOARD("&8&m-<<-<&b&l&oMINIGAMES&8&m>->>-"),
    TEST_CHAT_TAG("&4&l&oTEST&8&l&o>&r "),
    TEST_HEADER("&a&l&m--------------------<&4&l&oTEST&8&l&m>---------------------"),
    TEST_TAG1(" &8[&4&l&oTEST&8] "),
    TEST_TAG2(" &8&m&l-<<-<&4&l&oTEST&8&m&l>->>- "),
    TEST_SCOREBOARD("&8&m-<<-<&4&l&oTEST&8&m>->>-");

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
        return getServerChatTag() + format(message);
    }

    public String getServerMessage() {
        return getServerChatTag() + format(message);
    }

    public String getServerBoard() {
        if (NECore.getSettings().server.equals(Server.HUB)) {
            return HUB_SCOREBOARD.getMessage();
        } else if (NECore.getSettings().server.equals(Server.SUR)) {
            return NS_SCOREBOARD.getMessage();
        } else if (NECore.getSettings().server.equals(Server.FRB)) {
            return FRB_SCOREBOARD.getMessage();
        } else if (NECore.getSettings().server.equals(Server.PRS)) {
            return PRS_SCOREBOARD.getMessage();
        } else if (NECore.getSettings().server.equals(Server.MIN)) {
            return MIN_SCOREBOARD.getMessage();
        } else if (NECore.getSettings().server.equals(Server.TEST)) {
            return TEST_SCOREBOARD.getMessage();
        }
        return HUB_SCOREBOARD.getMessage();
    }

    public String getServerHeader() {
        if (NECore.getSettings().server.equals(Server.HUB)) {
            return HUB_HEADER.getMessage();
        } else if (NECore.getSettings().server.equals(Server.SUR)) {
            return NS_HEADER.getMessage();
        } else if (NECore.getSettings().server.equals(Server.FRB)) {
            return FRB_HEADER.getMessage();
        } else if (NECore.getSettings().server.equals(Server.PRS)) {
            return PRS_HEADER.getMessage();
        } else if (NECore.getSettings().server.equals(Server.MIN)) {
            return MIN_HEADER.getMessage();
        } else if (NECore.getSettings().server.equals(Server.TEST)) {
            return TEST_HEADER.getMessage();
        }
        return HUB_HEADER.getMessage();
    }

    public String getServerChatTag() {
        if (NECore.getSettings().server.equals(Server.HUB)) {
            return HUB_CHAT_TAG.getMessage();
        } else if (NECore.getSettings().server.equals(Server.SUR)) {
            return NS_CHAT_TAG.getMessage();
        } else if (NECore.getSettings().server.equals(Server.FRB)) {
            return FRB_CHAT_TAG.getMessage();
        } else if (NECore.getSettings().server.equals(Server.PRS)) {
            return PRS_CHAT_TAG.getMessage();
        } else if (NECore.getSettings().server.equals(Server.MIN)) {
            return MIN_CHAT_TAG.getMessage();
        } else if (NECore.getSettings().server.equals(Server.TEST)) {
            return TEST_CHAT_TAG.getMessage();
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

    public class Words {

        private List<String> bannedWords;
        private List<Integer> numbers;
        private List<String> nameFriendly;

        public Words() {
            bannedWords =
                    Lists.newArrayList("Ass", "Bitch", "Hoe", "Nigger", "Nigga", "Damn", "Fuck", "Dammit", "Pussy",
                            "Cock", "Cunt", "Vagina", "Dick", "Shit", "Penis", "Staff", "Server", "Console");
            numbers = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 0);
            nameFriendly =
                    Lists.newArrayList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P",
                            "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
                            "1", "2", "3", "4", "5", "6", "7", "8", "9", "0");
        }

        public List<String> getBannedWords() {
            return bannedWords;
        }

        public List<String> getNameFriendly() {
            return nameFriendly;
        }

        public List<Integer> getNumbers() {
            return numbers;
        }
    }
}
