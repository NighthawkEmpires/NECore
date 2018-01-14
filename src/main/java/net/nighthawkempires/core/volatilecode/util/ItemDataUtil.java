package net.nighthawkempires.core.volatilecode.util;

import net.nighthawkempires.core.volatilecode.NBTFactory;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemDataUtil {

    public static void mark(ItemStack is, String marker) {
        if (!hasMark(is, marker)) {
            NBTFactory.fromItemTag(is).put(marker, (byte) 1);
        }
    }

    public static void unmark(ItemStack is, String marker) {
        if (hasMark(is, marker)) {
            NBTFactory.fromItemTag(is).remove(marker);
        }
    }

    public static boolean hasMark(ItemStack is, String marker) {
        if (is == null || is.getType() == Material.AIR) {
            return false;
        }
        return NBTFactory.fromItemTag(is).get(marker) != null;
    }
}
