package net.nighthawkempires.core.utils;

import com.google.common.collect.Maps;

import java.util.UUID;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

public class CooldownUtil {

    private static ConcurrentMap<String, Long> map = Maps.newConcurrentMap();

    public static void setCooldown(UUID uuid, String name, int seconds) {
        String stored = uuid.toString() + name;
        long expire = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(seconds);
        map.put(stored, expire);
    }

    public static int timeLeft(UUID uuid, String name) {
        if (!map.containsKey(uuid.toString() + name)) return 0;
        long remaining = map.get(uuid.toString() + name) - System.currentTimeMillis();
        return (int) TimeUnit.MILLISECONDS.toSeconds(remaining);
    }

    public static boolean cooledDown(UUID uuid, String name) {
        if (!map.containsKey(uuid.toString() + name)) return true;
        if (map.get(uuid.toString() + name) <= System.currentTimeMillis()) {
            remove(uuid, name);
            return true;
        }
        return false;
    }

    public static void remove(UUID uuid, String id) {
        if (!map.containsKey(uuid.toString() + id)) map.remove(uuid.toString() + id);
    }

    public static void run() {
        if (map.isEmpty()) {
            return;
        }

        for (String string : map.keySet()) {
            if (map.get(string) <= System.currentTimeMillis()) {
                map.remove(string);
            }
        }
    }
}
