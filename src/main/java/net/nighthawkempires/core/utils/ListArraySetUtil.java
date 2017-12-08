package net.nighthawkempires.core.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import net.minecraft.server.v1_12_R1.Item;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class ListArraySetUtil {

    public static List<String> getStringList(String[] array) {
        List<String> list = Lists.newArrayList();
        list.addAll(Arrays.asList(array));
        return list;
    }

    public static String[] getStringArray(List<String> list) {
        if (list == null) {
            return null;
        } else {
            String[] l = new String[list.size()];
            l = list.toArray(l);
            return l;
        }
    }

    public static Set<Material> addMaterials(Set<Material>... sets) {
        Set<Material> materials = Sets.newHashSet();
        for (Set<Material> set : sets) {
            materials.addAll(new ArrayList<>(set));
        }
        return materials;
    }

    public static String stringListToString(List<String> list) {
        StringBuilder builder = new StringBuilder();
        if (list.isEmpty()) {
            return "";
        } else {
            for (String string : list) {
                builder.append(string).append(", ");
            }
            return builder.substring(0, builder.length() - 2);
        }
    }

    public static List<String> stringToStringList(String string) {
        if (string.length() == 0) {
            return Lists.newArrayList();
        } else {
            List<String> list = Lists.newArrayList();
            String[] strings = string.split(", ");
            list.addAll(Arrays.asList(strings));
            return list;
        }
    }
}
