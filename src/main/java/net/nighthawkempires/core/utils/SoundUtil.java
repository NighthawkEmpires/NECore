package net.nighthawkempires.core.utils;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class SoundUtil {

    public static void playSound(Player player, Sound sound) {
        player.getWorld().playSound(player.getLocation(), sound, 1F, 1F);
    }

    public static void playSound(Location location, Sound sound) {
        location.getWorld().playSound(location, sound, 1F, 1F);
    }
}
