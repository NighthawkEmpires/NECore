package net.nighthawkempires.core.utils;

import com.google.common.collect.Lists;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class BlockUtil {

    private static List<Material> interactiveBlocks;

    static {
        interactiveBlocks = Lists.newArrayList(Material.CHEST, Material.TRAPPED_CHEST, Material.ACACIA_DOOR, Material.BIRCH_DOOR, Material.DARK_OAK_DOOR, Material.IRON_DOOR, Material.JUNGLE_DOOR,
                Material.TRAP_DOOR, Material.IRON_TRAPDOOR, Material.WOODEN_DOOR, Material.WOOD_DOOR, Material.ENDER_CHEST, Material.STONE_BUTTON, Material.WOOD_BUTTON, Material.LEVER,
                Material.DIODE, Material.DIODE_BLOCK_OFF, Material.DIODE_BLOCK_ON, Material.REDSTONE_COMPARATOR, Material.REDSTONE_COMPARATOR_OFF, Material.REDSTONE_COMPARATOR_ON,
                Material.BOAT_ACACIA, Material.BOAT_BIRCH, Material.BOAT_DARK_OAK, Material.BOAT_JUNGLE, Material.BOAT_SPRUCE, Material.BOAT, Material.MINECART, Material.COMMAND_MINECART,
                Material.EXPLOSIVE_MINECART, Material.HOPPER_MINECART, Material.POWERED_MINECART, Material.STORAGE_MINECART, Material.ANVIL, Material.WORKBENCH, Material.FURNACE, Material.BREWING_STAND,
                Material.JUKEBOX, Material.NOTE_BLOCK, Material.BED, Material.BED_BLOCK, Material.ITEM_FRAME, Material.ARMOR_STAND, Material.ACACIA_FENCE_GATE, Material.BIRCH_FENCE_GATE,
                Material.DARK_OAK_FENCE_GATE, Material.FENCE_GATE, Material.JUNGLE_FENCE_GATE, Material.SPRUCE_FENCE_GATE, Material.BLACK_SHULKER_BOX, Material.RED_SHULKER_BOX, Material.BLUE_SHULKER_BOX,
                Material.BROWN_SHULKER_BOX, Material.CYAN_SHULKER_BOX, Material.GRAY_SHULKER_BOX, Material.GREEN_SHULKER_BOX, Material.LIGHT_BLUE_SHULKER_BOX, Material.LIME_SHULKER_BOX,
                Material.MAGENTA_SHULKER_BOX, Material.ORANGE_SHULKER_BOX, Material.PINK_SHULKER_BOX, Material.PURPLE_SHULKER_BOX, Material.SILVER_SHULKER_BOX, Material.WHITE_SHULKER_BOX,
                Material.YELLOW_SHULKER_BOX, Material.HOPPER, Material.DISPENSER, Material.DROPPER, Material.CAULDRON);
    }

    public static boolean isInteractiveBlock(Material material) {
        return interactiveBlocks.contains(material);
    }

    public static boolean isInteractiveBlock(ItemStack itemStack) {
        return isInteractiveBlock(itemStack.getType());
    }
}
