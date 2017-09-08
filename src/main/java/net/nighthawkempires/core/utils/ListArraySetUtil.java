package net.nighthawkempires.core.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.bukkit.Material;

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
}
