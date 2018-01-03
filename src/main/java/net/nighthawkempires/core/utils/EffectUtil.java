package net.nighthawkempires.core.utils;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class EffectUtil {

    public static void playEffect(Location location, Effect effect) {
        location.getWorld().playEffect(location, effect, 1, 1);
    }

    public static void playEffect(Player player, Effect effect) {
        playEffect(player.getLocation(), effect);
    }

    public static void playSpigotEffect(Location location, Effect effect) {
    }

    public static void playSpigtEffect(Player player, Effect effect) {
        playSpigotEffect(player.getLocation(), effect);
    }
}
