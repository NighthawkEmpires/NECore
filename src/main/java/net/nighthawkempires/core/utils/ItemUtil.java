package net.nighthawkempires.core.utils;

import com.google.common.collect.Sets;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

public class ItemUtil {

    private static Set<Material> axes;
    private static Set<Material> hoes;
    private static Set<Material> pickaxes;
    private static Set<Material> spades;
    private static Set<Material> swords;
    private static Set<Material> tools;

    private static Set<Material> helmets;
    private static Set<Material> chestplates;
    private static Set<Material> leggings;
    private static Set<Material> boots;
    private static Set<Material> armor;

    private static Set<Material> fruits;
    private static Set<Material> vegetables;
    private static Set<Material> raw_foods;
    private static Set<Material> grown_foods;
    private static Set<Material> cooked_foods;
    private static Set<Material> baked_foods;
    private static Set<Material> stew_foods;
    private static Set<Material> poisonus_foods;
    private static Set<Material> foods;
    private static Set<Material> meats;

    static {
        axes = Sets.newHashSet(Material.WOOD_AXE, Material.STONE_AXE, Material.IRON_AXE, Material.GOLD_AXE, Material.DIAMOND_AXE);
        hoes = Sets.newHashSet(Material.WOOD_HOE, Material.STONE_HOE, Material.IRON_HOE, Material.GOLD_HOE, Material.DIAMOND_HOE);
        pickaxes = Sets.newHashSet(Material.WOOD_PICKAXE, Material.STONE_PICKAXE, Material.IRON_PICKAXE, Material.GOLD_PICKAXE, Material.DIAMOND_PICKAXE);
        spades = Sets.newHashSet(Material.WOOD_SPADE, Material.STONE_SPADE, Material.IRON_SPADE, Material.GOLD_SPADE, Material.DIAMOND_SPADE);
        swords = Sets.newHashSet(Material.WOOD_SWORD, Material.STONE_SWORD, Material.IRON_SWORD, Material.GOLD_SWORD, Material.DIAMOND_SWORD);

        helmets = Sets.newHashSet(Material.LEATHER_HELMET, Material.CHAINMAIL_HELMET, Material.IRON_HELMET, Material.GOLD_HELMET, Material.DIAMOND_HELMET);
        chestplates = Sets.newHashSet(Material.LEATHER_CHESTPLATE, Material.CHAINMAIL_CHESTPLATE, Material.IRON_CHESTPLATE, Material.GOLD_CHESTPLATE,
                Material.DIAMOND_CHESTPLATE, Material.ELYTRA);
        leggings = Sets.newHashSet(Material.LEATHER_LEGGINGS, Material.CHAINMAIL_LEGGINGS, Material.IRON_LEGGINGS, Material.GOLD_LEGGINGS, Material.DIAMOND_LEGGINGS);
        boots = Sets.newHashSet(Material.LEATHER_BOOTS, Material.CHAINMAIL_BOOTS, Material.IRON_BOOTS, Material.GOLD_BOOTS, Material.DIAMOND_BOOTS);

        fruits = Sets.newHashSet(Material.APPLE, Material.GOLDEN_APPLE, Material.MELON);
        vegetables = Sets.newHashSet(Material.CARROT, Material.GOLDEN_CARROT, Material.BEETROOT, Material.BAKED_POTATO, Material.POTATO, Material.POISONOUS_POTATO);
        raw_foods = Sets.newHashSet(Material.RAW_BEEF, Material.RAW_CHICKEN, Material.RAW_FISH, Material.PORK, Material.MUTTON, Material.RABBIT);
        grown_foods = Sets.newHashSet(Material.MELON);
        cooked_foods = Sets.newHashSet(Material.GRILLED_PORK, Material.COOKED_BEEF, Material.COOKED_CHICKEN, Material.COOKED_FISH, Material.COOKED_MUTTON, Material.COOKED_MUTTON);
        baked_foods = Sets.newHashSet(Material.BAKED_POTATO, Material.PUMPKIN_PIE, Material.CAKE, Material.COOKIE);
        stew_foods = Sets.newHashSet(Material.RABBIT_STEW, Material.BEETROOT_SOUP, Material.MUSHROOM_SOUP);
        poisonus_foods = Sets.newHashSet(Material.POISONOUS_POTATO, Material.ROTTEN_FLESH, Material.SPIDER_EYE);

        tools = ListArraySetUtil.addMaterials(axes, hoes, pickaxes, spades);
        armor = ListArraySetUtil.addMaterials(helmets, chestplates, leggings, boots);
        foods = ListArraySetUtil.addMaterials(fruits, vegetables, raw_foods, grown_foods, cooked_foods, baked_foods, stew_foods, poisonus_foods);
        meats = ListArraySetUtil.addMaterials(raw_foods, cooked_foods);
    }

    public static boolean isAxe(Material material) {
        return axes.contains(material);
    }

    public static boolean isAxe(ItemStack itemStack) {
        return isAxe(itemStack.getType());
    }

    public static boolean isHoe(Material material) {
        return hoes.contains(material);
    }

    public static boolean isHoe(ItemStack itemStack) {
        return isHoe(itemStack.getType());
    }

    public static boolean isPickaxe(Material material) {
        return pickaxes.contains(material);
    }

    public static boolean isPickaxe(ItemStack itemStack) {
        return isPickaxe(itemStack.getType());
    }

    public static boolean isSpade(Material material) {
        return spades.contains(material);
    }

    public static boolean isSpade(ItemStack itemStack) {
        return isSpade(itemStack.getType());
    }

    public static boolean isSword(Material material) {
        return swords.contains(material);
    }

    public static boolean isSword(ItemStack itemStack) {
        return isSword(itemStack.getType());
    }

    public static boolean isBow(Material material) {
        return material.equals(Material.BOW);
    }

    public static boolean isBow(ItemStack itemStack) {
        return isBow(itemStack.getType());
    }

    public static boolean isFishingRod(Material material) {
        return material.equals(Material.FISHING_ROD);
    }

    public static boolean isFishingRod(ItemStack itemStack) {
        return isFishingRod(itemStack.getType());
    }

    public static boolean isTool(Material material) {
        return tools.contains(material);
    }

    public static boolean isTool(ItemStack itemStack) {
        return isTool(itemStack.getType());
    }

    public static boolean isHelmet(Material material) {
        return helmets.contains(material);
    }

    public static boolean isHelmet(ItemStack itemStack) {
        return isHoe(itemStack.getType());
    }

    public static boolean isChestplate(Material material) {
        return chestplates.contains(material);
    }

    public static boolean isChestplate(ItemStack itemStack) {
        return isChestplate(itemStack.getType());
    }

    public static boolean isLeggings(Material material) {
        return leggings.contains(material);
    }

    public static boolean isLeggings(ItemStack itemStack) {
        return isLeggings(itemStack.getType());
    }

    public static boolean isBoots(Material material) {
        return boots.contains(material);
    }

    public static boolean isBoots(ItemStack itemStack) {
        return isBow(itemStack.getType());
    }

    public static boolean isArmor(Material material) {
        return armor.contains(material);
    }

    public static boolean isArmor(ItemStack itemStack) {
        return isArmor(itemStack.getType());
    }
}
