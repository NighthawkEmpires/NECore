package net.nighthawkempires.core.inventory;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public abstract class Inventories {

    public abstract String getName();

    public abstract String getTitle();

    public abstract int getId();

    public abstract int getSize();

    public abstract InventoryHolder getInventoryHolder();

    public abstract InventoryType getInventoryType();

    public abstract Inventory getFor(Player player);

    public Inventory getInventory() {
        return (getInventoryType() == null ? Bukkit.createInventory(getInventoryHolder(), getSize(), getTitle())
                : Bukkit.createInventory(getInventoryHolder(), getInventoryType(), getTitle()));
    }
}
