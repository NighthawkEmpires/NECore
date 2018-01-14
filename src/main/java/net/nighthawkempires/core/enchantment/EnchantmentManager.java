package net.nighthawkempires.core.enchantment;

import com.google.common.collect.Lists;
import org.bukkit.enchantments.Enchantment;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

public class EnchantmentManager {

    private List<Enchantment> enchants;

    public EnchantmentManager() {
        enchants = Lists.newArrayList();
    }

    public void registerEnchant(Enchantment enchantment) {
        try {
            try {
                Field field = Enchantment.class.getDeclaredField("acceptingNew");
                field.setAccessible(true);
                field.set(null, true);
            } catch (IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
            }

            try {
                Enchantment.registerEnchantment(enchantment);
                enchants.add(enchantment);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void unregisterEnchants() {
        try {
            for (Enchantment enchantment : enchants) {
                Field byIdField = Enchantment.class.getDeclaredField("byId");
                Field byNameField = Enchantment.class.getDeclaredField("byName");

                byIdField.setAccessible(true);
                byNameField.setAccessible(true);

                HashMap<Integer, Enchantment> byId = (HashMap<Integer, Enchantment>) byIdField.get(null);
                HashMap<String, Enchantment> byName = (HashMap<String, Enchantment>) byIdField.get(null);

                if (byId.containsKey(enchantment.getId())) {
                    byId.remove(enchantment);
                } else if (byName.containsKey(enchantment.getName())) {
                    byName.remove(enchantment);
                }
            }
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public Enchantment getEnchantment(int id) {
        for (Enchantment enchantment : enchants) {
            if (enchantment.getId() == id) {
                return enchantment;
            }
        }
        return null;
    }

    public Enchantment getEnchantment(String name) {
        for (Enchantment enchantment : enchants) {
            if (enchantment.getName().equals(name)) {
                return enchantment;
            }
        }
        return null;
    }
}
