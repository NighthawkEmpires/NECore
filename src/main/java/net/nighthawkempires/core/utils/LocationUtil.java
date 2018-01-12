package net.nighthawkempires.core.utils;

import com.google.common.collect.Lists;
import net.md_5.bungee.api.ChatColor;
import net.nighthawkempires.core.NECore;
import net.nighthawkempires.core.file.FileType;
import net.nighthawkempires.core.light.LightLevel;
import org.apache.commons.lang.Validate;
import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

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

    public static Player getTargetPlayer(final Player player, final double maxRange, final double precision) {
        Validate.notNull(player, "player may not be null");
        Validate.isTrue(maxRange > 0D, "the maximum range has to be greater than 0");
        Validate.isTrue(precision > 0D && precision < 1D, "the precision has to be greater than 0 and smaller than 1");
        final double maxRange2 = maxRange * maxRange;

        final String playerName = player.getName();
        final Location fromLocation = player.getEyeLocation();
        final String playersWorldName = fromLocation.getWorld().getName();
        final Vector playerDirection = fromLocation.getDirection().normalize();
        final Vector playerVectorPos = fromLocation.toVector();

        Player target = null;
        double targetDistance2 = Double.MAX_VALUE;
        for (final Player somePlayer : Bukkit.getServer().getOnlinePlayers()) {
            if (somePlayer.getName().equals(playerName)) {
                continue;
            }
            final Location somePlayerLocation_middle = getMiddleLocationOfPlayer(somePlayer);
            final Location somePlayerLocation_eye = somePlayer.getEyeLocation();
            final Location somePlayerLocation_feed = somePlayer.getLocation();
            if (!somePlayerLocation_eye.getWorld().getName().equals(playersWorldName)) {
                continue;
            }
            final double newTargetDistance2_middle = somePlayerLocation_middle.distanceSquared(fromLocation);
            final double newTargetDistance2_eye = somePlayerLocation_eye.distanceSquared(fromLocation);
            final double newTargetDistance2_feed = somePlayerLocation_feed.distanceSquared(fromLocation);

            if (newTargetDistance2_middle <= maxRange2) {
                final Vector toTarget_middle = somePlayerLocation_middle.toVector().subtract(playerVectorPos).normalize();
                final double dotProduct_middle = toTarget_middle.dot(playerDirection);
                if (dotProduct_middle > precision && player.hasLineOfSight(somePlayer) && (target == null || newTargetDistance2_middle < targetDistance2)) {
                    target = somePlayer;
                    targetDistance2 = newTargetDistance2_middle;
                    continue;
                }
            }
            if (newTargetDistance2_eye <= maxRange2) {
                final Vector toTarget_eye = somePlayerLocation_eye.toVector().subtract(playerVectorPos).normalize();
                final double dotProduct_eye = toTarget_eye.dot(playerDirection);
                if (dotProduct_eye > precision && player.hasLineOfSight(somePlayer) && (target == null || newTargetDistance2_eye < targetDistance2)) {
                    target = somePlayer;
                    targetDistance2 = newTargetDistance2_eye;
                    continue;
                }
            }
            if (newTargetDistance2_feed <= maxRange2) {
                final Vector toTarget_feed = somePlayerLocation_feed.toVector().subtract(playerVectorPos).normalize();
                final double dotProduct_feed = toTarget_feed.dot(playerDirection);
                if (dotProduct_feed > precision && player.hasLineOfSight(somePlayer) && (target == null || newTargetDistance2_feed < targetDistance2)) {
                    target = somePlayer;
                    targetDistance2 = newTargetDistance2_feed;
                }
            }
        }
        return target;
    }
    public static Location getMiddleLocationOfPlayer(final Player player) {
        return player.getLocation().add(0, player.getEyeHeight() / 2, 0);
    }

    private static FileConfiguration getLocationFile() {
        return NECore.getFileManager().get(FileType.LOCATION);
    }

    private static void saveLocationFile() {
        NECore.getFileManager().save(FileType.LOCATION, true);
    }

    private static FileConfiguration getHomesFile() {
        return NECore.getFileManager().get(FileType.HOMES);
    }

    private static void saveHomesFile() {
        NECore.getFileManager().save(FileType.HOMES, true);
    }
}
