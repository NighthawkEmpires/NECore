package net.nighthawkempires.core.chat.tag;

import com.google.common.collect.Maps;
import net.nighthawkempires.core.NECore;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Map;

public class WorldTag extends PlayerTag {

    private final Map<String, String> textMap = Maps.newHashMap();

    public String getName() {
        return "world";
    }

    public String getFor(Player player) {
        String world = player.getWorld().getName();

        if(textMap.containsKey(world)) {
            return textMap.get(world);
        }

        String tag = ChatColor.translateAlternateColorCodes('&', NECore.getInstance().getConfig().getString("worlds."
                + world + ".text", "[" + world.toUpperCase() + "]"));
        textMap.put(world, tag);
        return tag;
    }

    public int getPriority() {
        return 0;
    }
}
