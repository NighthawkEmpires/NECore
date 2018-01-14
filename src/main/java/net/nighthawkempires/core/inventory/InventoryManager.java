package net.nighthawkempires.core.inventory;

import com.google.common.collect.Lists;

import java.util.List;

public class InventoryManager {

    private List<Inventories> inventories;

    public InventoryManager() {
        inventories = Lists.newArrayList();
    }

    public void registerInventory(Inventories inventory) {
        inventories.add(inventory);
    }

    public void unregisterInventories() {
        inventories.clear();
    }

    public Inventories getInventory(int id) {
        for (Inventories inventories : inventories) {
            if (inventories.getId() == id) {
                return inventories;
            }
        }
        return null;
    }

    public Inventories getInventory(String name) {
        for (Inventories inventories : inventories) {
            if (inventories.getName().equals(name)) {
                return inventories;
            }
        }
        return null;
    }
}
