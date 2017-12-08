package net.nighthawkempires.core.kit;

import org.bukkit.inventory.ItemStack;

public class Kit {

    private String name;
    private ItemStack display;
    private long cooldown;
    private ItemStack[] items;

    public Kit(String name, ItemStack display, long cooldown, ItemStack[] items) {
        this.name = name;
        this.display = display;
        this.cooldown = cooldown;
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public ItemStack getDisplay() {
        return display;
    }

    public long getCooldown() {
        return cooldown;
    }

    public ItemStack[] getItems() {
        return items;
    }
}
