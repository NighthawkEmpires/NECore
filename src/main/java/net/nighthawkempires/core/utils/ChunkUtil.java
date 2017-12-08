package net.nighthawkempires.core.utils;

import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;

public class ChunkUtil {

    public static Chunk getChunk(String world, int x, int y) {
        return Bukkit.getWorld(world).getChunkAt(x, y);
    }

    public static Chunk getChunk(String string) {
        String[] parts = string.split("-");
        String world = parts[0];
        if (!NumberUtils.isDigits(parts[1]) || !NumberUtils.isDigits(parts[2])) {
            return null;
        }
        int x = Integer.parseInt(parts[1]);
        int y = Integer.parseInt(parts[2]);
        return getChunk(world, x, y);
    }

    public static String getChunkString(Chunk chunk) {
        return chunk.getWorld().getName() + "-" + chunk.getX() + "-" + chunk.getZ();
    }
}
