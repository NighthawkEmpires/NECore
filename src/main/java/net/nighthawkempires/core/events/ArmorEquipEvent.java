package net.nighthawkempires.core.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;

public class ArmorEquipEvent extends PlayerEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private boolean cancel = false;
    private final EquipMethod equipType;
    private final ArmorType type;
    private ItemStack oldArmourPiece, newArmourPiece;

    public ArmorEquipEvent(final Player player, final EquipMethod equipType, final ArmorType type,
                           final ItemStack oldArmourPiece, final ItemStack newArmourPiece) {
        super(player);
        this.equipType = equipType;
        this.type = type;
        this.oldArmourPiece = oldArmourPiece;
        this.newArmourPiece = newArmourPiece;
    }

    public final static HandlerList getHandlerList() {
        return handlers;
    }

    public final HandlerList getHandlers() {
        return handlers;
    }

    public final void setCancelled(final boolean cancel) {
        this.cancel = cancel;
    }

    public final boolean isCancelled() {
        return cancel;
    }

    public final ArmorType getType() {
        return type;
    }

    public final ItemStack getOldArmourPiece() {
        return oldArmourPiece;
    }

    public final void setOldArmourPiece(final ItemStack oldArmourPiece) {
        this.oldArmourPiece = oldArmourPiece;
    }

    public final ItemStack getNewArmourPiece() {
        return newArmourPiece;
    }

    public final void setNewArmourPiece(final ItemStack newArmourPiece) {
        this.newArmourPiece = newArmourPiece;
    }

    public EquipMethod getMethod() {
        return equipType;
    }

    public enum EquipMethod {
        SHIFT_CLICK,
        DRAG,
        HOTBAR,
        HOTBAR_SWAP,
        DISPENSER,
        BROKE,
        DEATH
    }

    public enum ArmorType {
        HELMET(5), CHESTPLATE(6), LEGGINGS(7), BOOTS(8);

        private final int slot;

        ArmorType(int slot) {
            this.slot = slot;
        }

        public final static ArmorType matchType(final ItemStack itemStack) {
            if (itemStack == null) { return null; }
            switch (itemStack.getType()) {
                case DIAMOND_HELMET:
                case GOLD_HELMET:
                case IRON_HELMET:
                case CHAINMAIL_HELMET:
                case LEATHER_HELMET:
                    return HELMET;
                case DIAMOND_CHESTPLATE:
                case GOLD_CHESTPLATE:
                case IRON_CHESTPLATE:
                case CHAINMAIL_CHESTPLATE:
                case LEATHER_CHESTPLATE:
                    return CHESTPLATE;
                case DIAMOND_LEGGINGS:
                case GOLD_LEGGINGS:
                case IRON_LEGGINGS:
                case CHAINMAIL_LEGGINGS:
                case LEATHER_LEGGINGS:
                    return LEGGINGS;
                case DIAMOND_BOOTS:
                case GOLD_BOOTS:
                case IRON_BOOTS:
                case CHAINMAIL_BOOTS:
                case LEATHER_BOOTS:
                    return BOOTS;
                default:
                    return null;
            }
        }

        public int getSlot() {
            return slot;
        }
    }
}
