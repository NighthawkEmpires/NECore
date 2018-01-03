package net.nighthawkempires.core.utils;

import net.nighthawkempires.core.volatilecode.reflection.PacketType;
import net.nighthawkempires.core.volatilecode.util.ReflectionUtil;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class TeamUtil {

    private static ArrayList<Player> securePlayers = new ArrayList<>();
    private static Class<?> packetTeamClass = null;
    private static Field nameField = null;
    private static Field modeField = null;
    private static Field collisionRuleField = null;
    private static Field playersField = null;

    static {
        try {
            packetTeamClass = ReflectionUtil.Packets.getPacket(PacketType.PlayOut, "ScoreboardTeam");

            nameField = ReflectionUtil.getInaccessibleField(packetTeamClass, "a");
            modeField = ReflectionUtil.getInaccessibleField(packetTeamClass, "i");
            collisionRuleField = ReflectionUtil.getInaccessibleField(packetTeamClass, "f");
            playersField = ReflectionUtil.getInaccessibleField(packetTeamClass, "h");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static synchronized void sendTeamPacket(Player player) {
        try {
            Object packetTeamObject = packetTeamClass.newInstance();

            nameField.set(packetTeamObject, UUID.randomUUID().toString().substring(0, 15));
            modeField.set(packetTeamObject, 0);
            playersField.set(packetTeamObject, Arrays.asList(player.getName()));

            changePacketCollisionType(packetTeamObject);

            if (!getSecurePlayers().contains(player))
                ReflectionUtil.Packets.sendPacket(player, packetTeamObject);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void changePacketCollisionType(Object packetTeamObject) throws Exception {
        collisionRuleField.set(packetTeamObject, "never");
    }

    public static Class<?> getPacketTeamClass() {
        return packetTeamClass;
    }

    public static ArrayList<Player> getSecurePlayers() {
        return securePlayers;
    }
}
