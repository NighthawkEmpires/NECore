package net.nighthawkempires.core.utils;

import com.google.common.collect.Lists;
import net.md_5.bungee.api.ChatColor;
import net.nighthawkempires.core.NECore;
import net.nighthawkempires.core.light.LightLevel;
import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.BlockIterator;

import java.util.List;
import java.util.UUID;

public class LocationUtil {

    public static Location getLocation(Block block) {
        return block.getLocation();
    }

    public static Location getLocation(Location location) {
        return location;
    }

    public static String getLocationName(Location location) {
        return "[" + location.getX() + "," + location.getY() + "," + location.getZ() + "]";
    }

    public static String getLocationNameColored(Location location) {
        return ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + location.getX() + ChatColor.DARK_GRAY + ","
                + ChatColor.GOLD + location.getY() + ChatColor.DARK_GRAY + ","
                + ChatColor.GOLD + location.getZ() + ChatColor.DARK_GRAY + "]";
    }

    public static Location getTargetBlock(Player player, int range) {
        BlockIterator iter = new BlockIterator(player, range);
        Block lastBlock = iter.next();
        while (iter.hasNext()) {
            lastBlock = iter.next();
            if (lastBlock.getType() == Material.AIR) {
                continue;
            }
            break;
        }
        return lastBlock.getLocation();
    }

    public static double getBlockCenter(int block) {
        double d = block;
        d = d < 0 ? d - .5 : d + .5;
        return d;
    }

    public static Location getSpawn(World world) {
        ConfigurationSection section = getLocationFile().getConfigurationSection("spawn." + world.getName());
        int x = section.getInt("cord-x"), y = section.getInt("cord-y"), z = section.getInt("cord-z");
        float yaw = Float.parseFloat(section.getString("yaw")), pitch = Float.parseFloat(section.getString("pitch"));
        return new Location(world, getBlockCenter(x), getBlockCenter(y), getBlockCenter(z), yaw, pitch);
    }

    public static void setSpawn(World world, Location location) {
        getLocationFile().set("spawn." + world.getName() + ".cord-x", location.getBlockX());
        getLocationFile().set("spawn." + world.getName() + ".cord-y", location.getBlockY());
        getLocationFile().set("spawn." + world.getName() + ".cord-z", location.getBlockZ());
        getLocationFile().set("spawn." + world.getName() + ".yaw", String.valueOf(location.getYaw()));
        getLocationFile().set("spawn." + world.getName() + ".pitch", String.valueOf(location.getPitch()));
        saveLocationFile();
    }

    public static void deleteSpawn(World world) {
        getLocationFile().set("spawn." + world.getName(), null);
        saveLocationFile();
    }

    public static boolean hasSpawn(World world) {
        return getLocationFile().isSet("spawn." + world.getName() + ".cord-x");
    }

    public static Location getWarp(String name) {
        if (!warpExists(name))return null;
        ConfigurationSection section = getLocationFile().getConfigurationSection("warp." + name.toLowerCase());
        World world = Bukkit.getWorld(section.getString("world"));
        int x = section.getInt("cord-x"), y = section.getInt("cord-y"), z = section.getInt("cord-z");
        float yaw = Float.parseFloat(section.getString("yaw")), pitch = Float.parseFloat(section.getString("pitch"));
        return new Location(world, getBlockCenter(x), getBlockCenter(y), getBlockCenter(z), yaw, pitch);
    }

    public static void setWarp(String name, Location location) {
        getLocationFile().set("warp." + name.toLowerCase() + ".world", location.getWorld().getName());
        getLocationFile().set("warp." + name.toLowerCase() + ".cord-x", location.getBlockX());
        getLocationFile().set("warp." + name.toLowerCase() + ".cord-y", location.getBlockY());
        getLocationFile().set("warp." + name.toLowerCase() + ".cord-z", location.getBlockZ());
        getLocationFile().set("warp." + name.toLowerCase() + ".yaw", String.valueOf(location.getYaw()));
        getLocationFile().set("warp." + name.toLowerCase() + ".pitch", String.valueOf(location.getPitch()));
        saveLocationFile();
    }

    public static List<String> getWarps() {
        List<String> warps = Lists.newArrayList();
        warps.addAll(getLocationFile().getConfigurationSection("warp").getKeys(false));
        return warps;
    }

    public static void deleteWarp(String name) {
        if (!warpExists(name))return;
        getLocationFile().set("warp." + name.toLowerCase(), null);
        saveLocationFile();
    }

    public static boolean warpExists(String name) {
        return getLocationFile().isSet("warp." + name.toLowerCase() + ".cord-x");
    }

    public static Location getHome(UUID uuid, World world) {
        if (!homeExists(uuid, world))return null;
        ConfigurationSection section = getHomesFile().getConfigurationSection("homes." + uuid.toString() + "." + world.getName());
        int x = section.getInt("cord-x"), y = section.getInt("cord-y"), z = section.getInt("cord-z");
        float yaw = Float.parseFloat(section.getString("yaw")), pitch = Float.parseFloat(section.getString("pitch"));
        return new Location(world, getBlockCenter(x), getBlockCenter(y), getBlockCenter(z), yaw, pitch);
    }

    public static void setHome(UUID uuid, World world, Location location) {
        getHomesFile().set("homes." + uuid.toString() + "." + world.getName() + ".cord-x", location.getBlockX());
        getHomesFile().set("homes." + uuid.toString() + "." + world.getName() + ".cord-y", location.getBlockY());
        getHomesFile().set("homes." + uuid.toString() + "." + world.getName() + ".cord-z", location.getBlockZ());
        getHomesFile().set("homes." + uuid.toString() + "." + world.getName() + ".yaw", String.valueOf(location.getYaw()));
        getHomesFile().set("homes." + uuid.toString() + "." + world.getName() + ".pitch", String.valueOf(location.getPitch()));
        saveHomesFile();
    }

    public static void deleteHome(UUID uuid, World world) {
        getHomesFile().set("homes." + uuid.toString() + "." + world.getName(), null);
    }

    public static boolean homeExists(UUID uuid, World world) {
        return getHomesFile().isSet("homes." + uuid.toString() + "." + world.getName() + ".cord-x");
    }

    public static boolean radiusCheck(Block center, int radius, Material searched, int neededAmount) {
        int count = 0;

        for (int x = -(radius); x <= radius; x++) {
            for (int y = -(radius); y <= radius; y++) {
                for (int z = -(radius); z <= radius; z++) {
                    if (center.getRelative(x, y, z).getType() == searched) {
                        count++;
                    }
                }
            }
        }
        return count >= neededAmount;
    }

    public static Block getHighestBlock(Location l) {
        return l.getWorld().getHighestBlockAt(l);
    }

    public static LightLevel getLightLevel(Location l) {
        if (l.getBlock().getLightLevel() <= 6) return LightLevel.DARK;
        return LightLevel.LIGHT;
    }

    public static int getHighestBlockYAt(Location l) {
        return l.getWorld().getHighestBlockYAt(l);
    }

    public static Material getBlockAtLocation(Location l) {
        return l.getBlock().getType();
    }

    public static Material getBlockAtPlayer(Player p) {
        return getBlockAtLocation(p.getLocation());
    }


    public static void poofBlocks(Block center, int radius, List<Material> searched, Material replacement, Effect effect) {
        for (int x = -(radius); x <= radius; x++) {
            for (int y = -(radius); y <= radius; y++) {
                for (int z = -(radius); z <= radius; z++) {
                    Block block = center.getRelative(x, y, z);
                    if (searched.contains(block.getType())) {
                        EffectUtil.playEffect(block.getLocation(), effect);
                        block.setType(replacement);
                    }
                }
            }
        }
    }

    public static boolean isInOpenAir(Player p) {
        return getHighestBlockYAt(p.getLocation()) <= p.getEyeLocation().getY();
    }

    public static boolean isInRain(Player p) {
        return WorldUtil.isRaining(p.getWorld()) && isInOpenAir(p);
    }

    public static boolean isInWater(Player p) {
        Material mat = p.getLocation().getBlock().getType();
        return mat == Material.WATER || mat == Material.STATIONARY_WATER;
    }

    public static boolean isInLava(Player p) {
        Material mat = p.getLocation().getBlock().getType();
        return mat == Material.LAVA || mat == Material.STATIONARY_LAVA;
    }

    public static Biome getBiome(Player player) {
        World world = player.getWorld();
        Location location = player.getLocation();
        Biome biome = world.getBiome(location.getBlockX(), location.getBlockZ());
        return biome;
    }

    private static FileConfiguration getLocationFile() {
        return NECore.getFileManager().getLocationFile();
    }

    private static void saveLocationFile() {
        NECore.getFileManager().saveLocationFile();
    }

    private static FileConfiguration getHomesFile() {
        return NECore.getFileManager().getHomesFile();
    }

    private static void saveHomesFile() {
        NECore.getFileManager().saveHomesFile();
    }
}
