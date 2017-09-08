package net.nighthawkempires.core.utils;

import com.google.common.collect.Maps;
import net.nighthawkempires.core.NECore;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

import java.util.HashMap;

public class ExpBarUtil {

    private static HashMap<Player, Object> locks;

    static {
        locks = Maps.newHashMap();
    }

    public static void update(Player player, int level, float percent) {
        update(player, level, percent, null);
    }

    public static void update(Player player, int level, float percent, Object object) {
        Object lock = locks.get(player);
        if (lock == null || (object != null && object.equals(lock))) {
            if (player.getOpenInventory().getType() != InventoryType.ENCHANTING) {
                NECore.getCodeHandler().setExperienceBar(player, level, percent);
            }
        }
    }

    public static void lock(Player player, Object object) {
        Object lock = locks.get(player);
        if (lock == null || lock.equals(object)) {
            locks.put(player, object);
        }
    }

    public static void unlock(Player player, Object object) {
        Object lock = locks.get(player);
        if (lock != null && lock.equals(object)) {
            locks.remove(player);
        }
    }
}
