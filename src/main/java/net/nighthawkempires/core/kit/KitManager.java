package net.nighthawkempires.core.kit;

import com.google.common.collect.Lists;
import net.nighthawkempires.core.NECore;
import net.nighthawkempires.core.utils.ItemUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.List;

import static net.nighthawkempires.core.file.FileType.KITS;

public class KitManager {

    public List<Kit> kits;

    public KitManager() {
        kits = Lists.newArrayList();
    }

    public void loadKits() {
        try {
            for (String string : getKitsFile().getConfigurationSection("kits").getKeys(false)) {
                ConfigurationSection section = getKitsFile().getConfigurationSection("kits." + string);
                kits.add(new Kit(section.getString("name"), ItemUtil.getItem(section.getString("display")),
                        section.getLong("cooldown", 60L * 60L * 24L),
                        ItemUtil.getItems(section.getStringList("items"))));
            }
        } catch (Exception e) {
            NECore.getLoggers().warn("There are no kits to load in!");
        }
    }

    public void saveKits() {
        getKitsFile().set("kits", null);
        for (Kit kit : kits) {
            if (kit != null) {
                getKitsFile().set("kits." + kit.getName().toLowerCase() + ".name", kit.getName());
                getKitsFile().set("kits." + kit.getName().toLowerCase() + ".display",
                        ItemUtil.itemToString(kit.getDisplay()));
                getKitsFile().set("kits." + kit.getName().toLowerCase() + ".cooldown", kit.getCooldown());
                getKitsFile()
                        .set("kits." + kit.getName().toLowerCase() + ".items", ItemUtil.itemsToList(kit.getItems()));
                saveKitsFile();
            }
        }
    }

    public void addKit(String name, ItemStack display, long cooldown, ItemStack[] itemStacks) {
        kits.add(new Kit(name, display, cooldown, itemStacks));
    }

    public List<Kit> getKits() {
        return kits;
    }

    private FileConfiguration getKitsFile() {
        return NECore.getFileManager().get(KITS);
    }

    private void saveKitsFile() {
        NECore.getFileManager().save(KITS, true);
    }
}
