package net.nighthawkempires.core.utils;

import net.md_5.bungee.api.ChatColor;
import net.nighthawkempires.core.NECore;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

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
        return ChatColor.DARK_GRAY + "[" + ChatColor.YELLOW + location.getX() + ChatColor.DARK_GRAY + ","
                + ChatColor.YELLOW + location.getY() + ChatColor.DARK_GRAY + ","
                + ChatColor.YELLOW + location.getZ() + ChatColor.DARK_GRAY + "]";
    }

    public static double getBlockCenter(int block) {
        double d = block;
        d = d < 0 ? d - .5 : d + .5;
        return d;
    }

    public static Location getSpawn(World world) {
        ConfigurationSection section = getLocationFile().getConfigurationSection("spawn." + world.getName());
        int x = section.getInt("cord-x"), y = section.getInt("cord-y"), z = section.getInt("cord-z");
        float yaw = Float.parseFloat("yaw"), pitch = Float.parseFloat("pitch");
        return new Location(world, getBlockCenter(x), getBlockCenter(y), getBlockCenter(z), yaw, pitch);
    }

    public static void setSpawn(World world, Location location) {
        getLocationFile().set("spawn." + world.getName() + ".cord-x", location.getBlockX());
        getLocationFile().set("spawn." + world.getName() + ".cord-y", location.getBlockY());
        getLocationFile().set("spawn." + world.getName() + ".cord-z", location.getBlockZ());
        getLocationFile().set("spawn." + world.getName() + ".yaw", location.getYaw());
        getLocationFile().set("spawn." + world.getName() + ".pitch", location.getPitch());
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
        float yaw = Float.parseFloat("yaw"), pitch = Float.parseFloat("pitch");
        return new Location(world, getBlockCenter(x), getBlockCenter(y), getBlockCenter(z), yaw, pitch);
    }

    public static void setWarp(String name, Location location) {
        getLocationFile().set("warp." + name.toLowerCase() + ".world", location.getWorld().getName());
        getLocationFile().set("warp." + name.toLowerCase() + ".cord-x", location.getBlockX());
        getLocationFile().set("warp." + name.toLowerCase() + ".cord-y", location.getBlockY());
        getLocationFile().set("warp." + name.toLowerCase() + ".cord-z", location.getBlockZ());
        getLocationFile().set("warp." + name.toLowerCase() + ".yaw", location.getYaw());
        getLocationFile().set("warp." + name.toLowerCase() + ".pitch", location.getPitch());
        saveLocationFile();
    }

    public static void deleteWarp(String name) {
        if (!warpExists(name))return;
        getLocationFile().set("warp." + name.toLowerCase(), null);
        saveLocationFile();
    }

    public static boolean warpExists(String name) {
        return getLocationFile().isSet("warp." + name.toLowerCase() + ".cord-x");
    }

    private static FileConfiguration getLocationFile() {
        return NECore.getFileManager().getLocationFile();
    }

    private static void saveLocationFile() {
        NECore.getFileManager().saveLocationFile();
    }
}
