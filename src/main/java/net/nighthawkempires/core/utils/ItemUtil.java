package net.nighthawkempires.core.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.*;
import org.bukkit.Color;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.awt.*;
import java.util.*;
import java.util.List;
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

    public static ItemStack customItem(Material material, int amount, String displayName) {
        ItemStack itemStack = new ItemStack(material);
        itemStack.setAmount(amount);

        ItemMeta meta = itemStack.getItemMeta();

        meta.setDisplayName(displayName);

        itemStack.setItemMeta(meta);

        return itemStack;
    }

    public static ItemStack customItem(Material material, int amount, String displayName, List<String> lore) {
        ItemStack stack = customItem(material, amount, displayName);
        stack.setAmount(amount);

        ItemMeta meta = stack.getItemMeta();
        ArrayList<String> lor = (ArrayList<String>) lore;

        meta.setLore(lor);

        stack.setItemMeta(meta);

        return stack;
    }

    public static ItemStack coloredItem(Colorable colorable, Color color, String name, ArrayList<String> lore, int amount) {
        ItemStack itemStack = new ItemStack(colorable.getMaterial(), amount, (short) 0, (byte) color.getCode());
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public static void removeAmount(Player p, Material m, int amount) {
        int remaining = amount;
        int slot = 0;
        for (ItemStack i : p.getInventory().getContents()) {
            if (i != null && i.getType() == m) {
                if (i.getAmount() > remaining) {
                    i.setAmount(i.getAmount() - remaining);
                    break;
                } else {
                    remaining = remaining - i.getAmount();
                    p.getInventory().setItem(slot, new ItemStack(Material.AIR));
                }
                if (remaining < 1) break;
            }
            slot++;
        }
        p.updateInventory();
    }

    public static void removeAmount(Player p, Material m, int amount, int durability) {
        int remaining = amount;
        int slot = 0;
        for (ItemStack i : p.getInventory().getContents()) {
            if (i != null && i.getType() == m && i.getDurability() == (short) durability) {
                if (i.getAmount() > remaining) {
                    i.setAmount(i.getAmount() - remaining);
                    break;
                } else {
                    remaining = remaining - i.getAmount();
                    p.getInventory().setItem(slot, new ItemStack(Material.AIR));
                }
                if (remaining < 1) break;
            }
            slot++;
        }
        p.updateInventory();
    }

    public static ItemStack getEnchantedItem(Material material, int amount, String name, Map<Enchantment, Integer> map) {
        ItemStack stack = new ItemStack(material);
        stack.setAmount(amount);

        ItemMeta meta = stack.getItemMeta();

        meta.setDisplayName(name);
        stack.setItemMeta(meta);
        stack.addUnsafeEnchantments(map);

        return stack;
    }

    public static ItemStack getPotion(Potion potion, int amount, String name) {
        ItemStack stack = new ItemStack(Material.POTION, 1, (short) 0, (byte) potion.getPotionCode());
        stack.setAmount(amount);

        ItemMeta meta = stack.getItemMeta();

        meta.setDisplayName(name);

        stack.setItemMeta(meta);
        return stack;
    }

    public static ItemStack getMonsterEgg(Monster monster, int amount, String name) {
        ItemStack stack = new ItemStack(Material.MONSTER_EGG, 1, (short) 0, (byte) monster.getMonsterCode());
        stack.setAmount(amount);

        ItemMeta meta = stack.getItemMeta();

        meta.setDisplayName(name);

        stack.setItemMeta(meta);
        return stack;
    }

    public static ItemStack getColoredArmor(Material armor, int amount, String name, org.bukkit.Color color) {
        ItemStack stack = new ItemStack(armor);
        stack.setAmount(amount);

        LeatherArmorMeta meta = (LeatherArmorMeta) stack.getItemMeta();
        meta.setColor(color);
        meta.setDisplayName(name);

        stack.setItemMeta(meta);

        return stack;
    }

    public static ItemStack getSkull(String skullOwner, String displayName, int quantity) {

        ItemStack skull = new ItemStack(Material.SKULL_ITEM, quantity, (short) SkullType.PLAYER.ordinal());

        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();

        skullMeta.setOwner(skullOwner);
        skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(skullOwner));
        skullMeta.setDisplayName(ChatColor.RESET + displayName);
        skull.setItemMeta(skullMeta);

        return skull;
    }

    public static ItemStack getBackArrow(String name, int amount) {
        return getSkull(Bukkit.getOfflinePlayer(UUID.fromString("6ac68253-f6d4-4caa-90f4-c5fc9db0ad03")).getName(), name, amount);
    }

    public static ItemStack getNextArrow(String name, int amount) {
        return getSkull(Bukkit.getOfflinePlayer(UUID.fromString("e6ae8e4c-09a8-461d-91f1-2bedf8789b1b")).getName(), name, amount);
    }

    public static ItemStack getItem(String string) {
        // TYPE-12-&4&lBOB-2
        String[] args = string.split("-");
        String type = args[0].toUpperCase();
        int amount = (NumberUtils.isDigits(args[1]) ? Integer.parseInt(args[1]) : 0);
        String name = ChatColor.translateAlternateColorCodes('&', args[2]);
        int durability = (NumberUtils.isDigits(args[3]) ? Integer.parseInt(args[3]) : 0);
        return new ItemStack(Material.valueOf(type.toUpperCase()), amount, (byte) durability);
    }

    public static ItemStack[] getItems(List<String> strings) {
        List<ItemStack> stacks = Lists.newArrayList();
        for (String string : strings) {
            stacks.add(getItem(string));
        }

        if (stacks == null) {
            return null;
        } else {
            ItemStack[] l = new ItemStack[stacks.size()];
            l = stacks.toArray(l);
            return l;
        }
    }

    public static String itemToString(ItemStack itemStack) {
        String string = itemStack.getType().name() + "-" + itemStack.getAmount() + "-" + itemStack.getItemMeta().getDisplayName() + "-" + itemStack.getDurability();
        return string;
    }

    public static List<String> itemsToList(ItemStack[] itemStacks) {
        List<String> strings = Lists.newArrayList();
        for (ItemStack itemStack : itemStacks) {
            strings.add(itemToString(itemStack));
        }
        return strings;
    }

    public enum Colorable {
        STAINED_GLASS(Material.STAINED_GLASS),
        STAINED_GLASS_PANE(Material.STAINED_GLASS_PANE),
        STAINED_CLAY(Material.STAINED_CLAY),
        WOOL(Material.WOOL),
        CARPET(Material.CARPET),
        BANNER(Material.BANNER),
        CONCRETE(Material.CONCRETE),
        CONCRETE_POWDER(Material.CONCRETE_POWDER);

        private Material material;

        Colorable(Material material) {
            this.material = material;
        }

        public Material getMaterial() {
            return material;
        }
    }

    public enum Color {
        WHITE(0),
        ORANGE(1),
        MAGENTA(2),
        LIGHT_BLUE(3),
        YELLOW(4),
        LIME(5),
        PINK(6),
        GRAY(7),
        LIGHT_GRAY(8),
        CYAN(9),
        PURPLE(10),
        BLUE(11),
        BROWN(12),
        GREEN(13),
        RED(14),
        BLACK(15);

        private int code;

        Color(int code){
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

    public enum Potion {
        WATER(0),
        REGENI(8193),
        REGENIE(8257),
        REGENII(8225),
        REGENIS(16385),
        REGENIES(16449),
        REGENIIS(16417),
        SPEEDI(8194),
        SPEEDIE(8258),
        SPEEDII(8226),
        SPEEDIS(18386),
        SPEEDIES(16450),
        SPEEDIIS(16418),
        FIREI(8227),
        FIREIE(8259),
        FIREIS(16419),
        FIREIES(16451),
        POISONI(8196),
        POISONIE(8260),
        POISONII(8228),
        POISONIS(16388),
        POISONIES(16452),
        POISONIIS(16420),
        HEALTHI(8261),
        HEALTHII(8229),
        HEALTHIS(16453),
        HEALTHIIS(16421),
        NIGHTI(8230),
        NIGHTIE(8262),
        NIGHTIS(16422),
        NIGHTIES(16454),
        WEAKI(8232),
        WEAKIE(8264),
        WEAKIS(16424),
        WEAKIES(16456),
        STRENGTHI(8201),
        STRENGTHIE(8265),
        STRENGTHII(8233),
        STRENGTHIS(164),
        STRENGTHIES(164),
        STRENGTHIIS(164),
        SLOWI(8234),
        SLOWIE(8266),
        SLOWIS(16426),
        SLOWIES(16458),
        JUMPI(8203),
        JUMPIE(8267),
        JUMPII(8235),
        JUMPIS(16495),
        JUMPIES(16459),
        JUMPIIS(16427),
        HARMI(8268),
        HARMII(8236),
        HARMIS(16460),
        HARMIIS(16428),
        BREATHI(8237),
        BREATHIE(8269),
        BREATHIS(16429),
        BREATHIES(16461),
        INVISI(8238),
        INVISIE(8270),
        INVISIS(16430),
        INVISIES(16462);

        private int code;

        Potion(int code) {
            this.code = code;
        }

        public int getPotionCode() {
            return this.code;
        }
    }

    public enum Monster {
        CREEPER(50),
        SKELETON(51),
        SPIDER(52),
        ZOMBIE(54),
        SLIME(55),
        GHAST(56),
        PIGZOMBIE(57),
        ENDERMAN(58),
        CAVESPIDER(59),
        SILVERFISH(60),
        BLAZE(61),
        MAGMACUBE(62),
        BAT(65),
        WITCH(66),
        ENDERMITE(67),
        GUARDIAN(68),
        PIG(90),
        SHEEP(91),
        COW(92),
        CHICKEN(93),
        SQUID(94),
        WOLF(95),
        MOOSHROOM(96),
        OCELOT(98),
        HORSE(100),
        RABBIT(101),
        VILLAGER(120);

        private int code;

        Monster(int code) {
            this.code = code;
        }

        public int getMonsterCode() {
            return this.code;
        }
    }
}
