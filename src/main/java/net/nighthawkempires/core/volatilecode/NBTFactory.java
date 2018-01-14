package net.nighthawkempires.core.volatilecode;

import com.google.common.base.Splitter;
import com.google.common.collect.*;
import com.google.common.primitives.Primitives;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.ConcurrentMap;

public class NBTFactory {

    private static final BiMap<Integer, Class<?>> NBT_CLASS = HashBiMap.create();
    private static final BiMap<Integer, NBTType> NBT_ENUM = HashBiMap.create();

    public enum StreamOptions {
        NO_COMPRESSION,
        GZIP_COMPRESSION,
    }

    private enum NBTType {
        TAG_END(0, Void.class),
        TAG_BYTE(1, byte.class),
        TAG_SHORT(2, short.class),
        TAG_INT(3, int.class),
        TAG_LONG(4, long.class),
        TAG_FLOAT(5, float.class),
        TAG_DOUBLE(6, double.class),
        TAG_BYTE_ARRAY(7, byte[].class),
        TAG_INT_ARRAY(11, int[].class),
        TAG_STRING(8, String.class),
        TAG_LIST(9, List.class),
        TAG_COMPOUND(10, Map.class);

        // Unique NBT id
        public final int id;

        NBTType(int id, Class<?> type) {
            this.id = id;
            NBT_CLASS.put(id, type);
            NBT_ENUM.put(id, this);
        }

        private String getFieldName() {
            if (this == TAG_COMPOUND) { return "map"; } else if (this == TAG_LIST) { return "list"; } else {
                return "data";
            }
        }
    }

    private Class<?> BASE_CLASS;
    private Class<?> COMPOUND_CLASS;
    private Method NBT_CREATE_TAG;
    private Method NBT_GET_TYPE;
    private Field NBT_LIST_TYPE;
    private final Field[] DATA_FIELD = new Field[12];

    private Class<?> CRAFT_STACK;
    private Field CRAFT_HANDLE;
    private Field STACK_TAG;

    private static NBTFactory INSTANCE;

    public final class NBTCompound extends ConvertedMap {
        private NBTCompound(Object handle) {
            super(handle, getDataMap(handle));
        }

        public Byte getByte(String key, Byte defaultValue) {
            return containsKey(key) ? (Byte) get(key) : defaultValue;
        }

        public Short getShort(String key, Short defaultValue) {
            return containsKey(key) ? (Short) get(key) : defaultValue;
        }

        public Integer getInteger(String key, Integer defaultValue) {
            return containsKey(key) ? (Integer) get(key) : defaultValue;
        }

        public Long getLong(String key, Long defaultValue) {
            return containsKey(key) ? (Long) get(key) : defaultValue;
        }

        public Float getFloat(String key, Float defaultValue) {
            return containsKey(key) ? (Float) get(key) : defaultValue;
        }

        public Double getDouble(String key, Double defaultValue) {
            return containsKey(key) ? (Double) get(key) : defaultValue;
        }

        public String getString(String key, String defaultValue) {
            return containsKey(key) ? (String) get(key) : defaultValue;
        }

        public byte[] getByteArray(String key, byte[] defaultValue) {
            return containsKey(key) ? (byte[]) get(key) : defaultValue;
        }

        public int[] getIntegerArray(String key, int[] defaultValue) {
            return containsKey(key) ? (int[]) get(key) : defaultValue;
        }

        public NBTList getList(String key, boolean createNew) {
            NBTList list = (NBTList) get(key);

            if (list == null) { put(key, list = createList()); }
            return list;
        }

        public NBTCompound getMap(String key, boolean createNew) {
            return getMap(Arrays.asList(key), createNew);
        }

        public NBTCompound putPath(String path, Object value) {
            List<String> entries = getPathElements(path);
            Map<String, Object> map = getMap(entries.subList(0, entries.size() - 1), true);

            map.put(entries.get(entries.size() - 1), value);
            return this;
        }

        @SuppressWarnings("unchecked")
        public <T> T getPath(String path) {
            List<String> entries = getPathElements(path);
            NBTCompound map = getMap(entries.subList(0, entries.size() - 1), false);

            if (map != null) {
                return (T) map.get(entries.get(entries.size() - 1));
            }
            return null;
        }

        private NBTCompound getMap(Iterable<String> path, boolean createNew) {
            NBTCompound current = this;

            for (String entry : path) {
                NBTCompound child = (NBTCompound) current.get(entry);

                if (child == null) {
                    if (!createNew) { throw new IllegalArgumentException("Cannot find " + entry + " in " + path); }
                    current.put(entry, child = createCompound());
                }
                current = child;
            }
            return current;
        }

        private List<String> getPathElements(String path) {
            return Lists.newArrayList(Splitter.on(".").omitEmptyStrings().split(path));
        }
    }

    public final class NBTList extends ConvertedList {
        private NBTList(Object handle) {
            super(handle, getDataList(handle));
        }
    }

    public interface Wrapper {
        Object getHandle();
    }


    private static NBTFactory get() {
        if (INSTANCE == null) { INSTANCE = new NBTFactory(); }
        return INSTANCE;
    }

    private NBTFactory() {
        if (BASE_CLASS == null) {
            try {
                ClassLoader loader = NBTFactory.class.getClassLoader();
                String packageName = Bukkit.getServer().getClass().getPackage().getName();
                Class<?> offlinePlayer = loader.loadClass(packageName + ".CraftOfflinePlayer");

                COMPOUND_CLASS = getMethod(0, Modifier.STATIC, offlinePlayer, "getData").getReturnType();
                BASE_CLASS = COMPOUND_CLASS.getSuperclass();
                NBT_GET_TYPE = getMethod(0, Modifier.STATIC, BASE_CLASS, "getTypeId");
                NBT_CREATE_TAG = getMethod(Modifier.STATIC, 0, BASE_CLASS, "createTag", byte.class);

                CRAFT_STACK = loader.loadClass(packageName + ".inventory.CraftItemStack");
                CRAFT_HANDLE = getField(null, CRAFT_STACK, "handle");
                STACK_TAG = getField(null, CRAFT_HANDLE.getType(), "tag");
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException("Unable to find offline player.", e);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> getDataMap(Object handle) {
        return (Map<String, Object>) getFieldValue(
                getDataField(NBTType.TAG_COMPOUND, handle), handle);
    }

    @SuppressWarnings("unchecked")
    private List<Object> getDataList(Object handle) {
        return (List<Object>) getFieldValue(
                getDataField(NBTType.TAG_LIST, handle), handle);
    }

    public static NBTList createList(Object... content) {
        return createList(Arrays.asList(content));
    }

    public static NBTList createList(Iterable<? extends Object> iterable) {
        NBTList list = get().new NBTList(
                INSTANCE.createNbtTag(NBTType.TAG_LIST, "", null)
        );

        // Add the content as well
        for (Object obj : iterable) { list.add(obj); }
        return list;
    }

    public static NBTCompound createCompound() {
        return get().new NBTCompound(
                INSTANCE.createNbtTag(NBTType.TAG_COMPOUND, "", null)
        );
    }

    public static NBTCompound createRootCompound(String name) {
        return get().new NBTCompound(
                INSTANCE.createNbtTag(NBTType.TAG_COMPOUND, name, null)
        );
    }

    public static NBTList fromList(Object nmsList) {
        return get().new NBTList(nmsList);
    }

    public static NBTCompound fromCompound(Object nmsCompound) {
        return get().new NBTCompound(nmsCompound);
    }


    public static void setItemTag(ItemStack stack, NBTCompound compound) {
        checkItemStack(stack);
        Object nms = getFieldValue(get().CRAFT_HANDLE, stack);

        setFieldValue(get().STACK_TAG, nms, compound.getHandle());
    }

    public static NBTCompound fromItemTag(ItemStack stack) {
        checkItemStack(stack);
        Object nms = getFieldValue(get().CRAFT_HANDLE, stack);
        Object tag = getFieldValue(get().STACK_TAG, nms);

        // Create the tag if it doesn't exist
        if (tag == null) {
            NBTCompound compound = createRootCompound("tag");
            setItemTag(stack, compound);
            return compound;
        }
        return fromCompound(tag);
    }

    public static ItemStack getCraftItemStack(ItemStack stack) {
        if (stack == null || get().CRAFT_STACK.isAssignableFrom(stack.getClass())) { return stack; }
        try {
            Constructor<?> caller = INSTANCE.CRAFT_STACK.getDeclaredConstructor(ItemStack.class);
            caller.setAccessible(true);
            return (ItemStack) caller.newInstance(stack);
        } catch (Exception e) {
            throw new IllegalStateException("Unable to convert " + stack + " + to a CraftItemStack.");
        }
    }

    private static void checkItemStack(ItemStack stack) {
        if (stack == null) { throw new IllegalArgumentException("Stack cannot be NULL."); }
        if (!get().CRAFT_STACK.isAssignableFrom(stack.getClass())) {
            throw new IllegalArgumentException(
                    "Stack must be a CraftItemStack, found " + stack.getClass().getSimpleName());
        }
        if (stack.getType() == Material.AIR) {
            throw new IllegalArgumentException("ItemStacks representing air cannot store NMS information.");
        }
    }

    private Object unwrapValue(String name, Object value) {
        if (value == null) { return null; }

        if (value instanceof Wrapper) {
            return ((Wrapper) value).getHandle();

        } else if (value instanceof List) {
            throw new IllegalArgumentException("Can only insert a WrappedList.");
        } else if (value instanceof Map) {
            throw new IllegalArgumentException("Can only insert a WrappedCompound.");

        } else {
            return createNbtTag(getPrimitiveType(value), name, value);
        }
    }

    private Object wrapNative(Object nms) {
        if (nms == null) { return null; }

        if (BASE_CLASS.isAssignableFrom(nms.getClass())) {
            final NBTType type = getNbtType(nms);

            // Handle the different types
            switch (type) {
                case TAG_COMPOUND:
                    return new NBTCompound(nms);
                case TAG_LIST:
                    return new NBTList(nms);
                default:
                    return getFieldValue(getDataField(type, nms), nms);
            }
        }
        throw new IllegalArgumentException("Unexpected type: " + nms);
    }

    private Object createNbtTag(NBTType type, String name, Object value) {
        Object tag = invokeMethod(NBT_CREATE_TAG, null, (byte) type.id);

        if (value != null) {
            setFieldValue(getDataField(type, tag), tag, value);
        }
        return tag;
    }

    private Field getDataField(NBTType type, Object nms) {
        if (DATA_FIELD[type.id] == null) { DATA_FIELD[type.id] = getField(nms, null, type.getFieldName()); }
        return DATA_FIELD[type.id];
    }

    private NBTType getNbtType(Object nms) {
        int type = (Byte) invokeMethod(NBT_GET_TYPE, nms);
        return NBT_ENUM.get(type);
    }

    private NBTType getPrimitiveType(Object primitive) {
        NBTType type = NBT_ENUM.get(NBT_CLASS.inverse().get(
                Primitives.unwrap(primitive.getClass())
        ));

        if (type == null) {
            throw new IllegalArgumentException(String.format(
                    "Illegal type: %s (%s)", primitive.getClass(), primitive));
        }
        return type;
    }

    private static Object invokeMethod(Method method, Object target, Object... params) {
        try {
            return method.invoke(target, params);
        } catch (Exception e) {
            throw new RuntimeException("Unable to invoke method " + method + " for " + target, e);
        }
    }

    private static void setFieldValue(Field field, Object target, Object value) {
        try {
            field.set(target, value);
        } catch (Exception e) {
            throw new RuntimeException("Unable to set " + field + " for " + target, e);
        }
    }

    private static Object getFieldValue(Field field, Object target) {
        try {
            return field.get(target);
        } catch (Exception e) {
            throw new RuntimeException("Unable to retrieve " + field + " for " + target, e);
        }
    }

    private static Method getMethod(int requireMod, int bannedMod, Class<?> clazz, String methodName,
                                    Class<?>... params) {
        for (Method method : clazz.getDeclaredMethods()) {
            if ((method.getModifiers() & requireMod) == requireMod &&
                    (method.getModifiers() & bannedMod) == 0 &&
                    (methodName == null || method.getName().equals(methodName)) &&
                    Arrays.equals(method.getParameterTypes(), params)) {

                method.setAccessible(true);
                return method;
            }
        }
        if (clazz.getSuperclass() != null) {
            return getMethod(requireMod, bannedMod, clazz.getSuperclass(), methodName, params);
        }
        throw new IllegalStateException(String.format(
                "Unable to find method %s (%s).", methodName, Arrays.asList(params)));
    }

    private static Field getField(Object instance, Class<?> clazz, String fieldName) {
        if (clazz == null) { clazz = instance.getClass(); }
        for (Field field : clazz.getDeclaredFields()) {
            if (field.getName().equals(fieldName)) {
                field.setAccessible(true);
                return field;
            }
        }
        if (clazz.getSuperclass() != null) { return getField(instance, clazz.getSuperclass(), fieldName); }
        throw new IllegalStateException("Unable to find field " + fieldName + " in " + instance);
    }

    private final class CachedNativeWrapper {
        private final ConcurrentMap<Object, Object> cache = new MapMaker().weakKeys().makeMap();

        public Object wrap(Object value) {
            Object current = cache.get(value);

            if (current == null) {
                current = wrapNative(value);

                // Only cache composite objects
                if (current instanceof ConvertedMap ||
                        current instanceof ConvertedList) {
                    cache.put(value, current);
                }
            }
            return current;
        }
    }

    private class ConvertedMap extends AbstractMap<String, Object> implements Wrapper {
        private final Object handle;
        private final Map<String, Object> original;

        private final CachedNativeWrapper cache = new CachedNativeWrapper();

        public ConvertedMap(Object handle, Map<String, Object> original) {
            this.handle = handle;
            this.original = original;
        }

        protected Object wrapOutgoing(Object value) {
            return cache.wrap(value);
        }

        protected Object unwrapIncoming(String key, Object wrapped) {
            return unwrapValue(key, wrapped);
        }

        @Override
        public Object put(String key, Object value) {
            return wrapOutgoing(original.put(
                    key,
                    unwrapIncoming(key, value)
            ));
        }

        @Override
        public Object get(Object key) {
            return wrapOutgoing(original.get(key));
        }

        @Override
        public Object remove(Object key) {
            return wrapOutgoing(original.remove(key));
        }

        @Override
        public boolean containsKey(Object key) {
            return original.containsKey(key);
        }

        @Override
        public Set<Entry<String, Object>> entrySet() {
            return new AbstractSet<Entry<String, Object>>() {
                @Override
                public boolean add(Entry<String, Object> e) {
                    String key = e.getKey();
                    Object value = e.getValue();

                    original.put(key, unwrapIncoming(key, value));
                    return true;
                }

                @Override
                public int size() {
                    return original.size();
                }

                @Override
                public Iterator<Map.Entry<String, Object>> iterator() {
                    return ConvertedMap.this.iterator();
                }
            };
        }

        private Iterator<Map.Entry<String, Object>> iterator() {
            final Iterator<Entry<String, Object>> proxy = original.entrySet().iterator();

            return new Iterator<Entry<String, Object>>() {

                public boolean hasNext() {
                    return proxy.hasNext();
                }


                public Entry<String, Object> next() {
                    Entry<String, Object> entry = proxy.next();

                    return new SimpleEntry<String, Object>(
                            entry.getKey(), wrapOutgoing(entry.getValue())
                    );
                }


                public void remove() {
                    proxy.remove();
                }
            };
        }

        public Object getHandle() {
            return handle;
        }
    }

    private class ConvertedList extends AbstractList<Object> implements Wrapper {
        private final Object handle;

        private final List<Object> original;
        private final CachedNativeWrapper cache = new CachedNativeWrapper();

        public ConvertedList(Object handle, List<Object> original) {
            if (NBT_LIST_TYPE == null) { NBT_LIST_TYPE = getField(handle, null, "type"); }
            this.handle = handle;
            this.original = original;
        }

        protected Object wrapOutgoing(Object value) {
            return cache.wrap(value);
        }

        protected Object unwrapIncoming(Object wrapped) {
            return unwrapValue("", wrapped);
        }

        @Override
        public Object get(int index) {
            return wrapOutgoing(original.get(index));
        }

        @Override
        public int size() {
            return original.size();
        }

        @Override
        public Object set(int index, Object element) {
            return wrapOutgoing(
                    original.set(index, unwrapIncoming(element))
            );
        }

        @Override
        public void add(int index, Object element) {
            Object nbt = unwrapIncoming(element);

            if (size() == 0) { setFieldValue(NBT_LIST_TYPE, handle, (byte) getNbtType(nbt).id); }
            original.add(index, nbt);
        }

        @Override
        public Object remove(int index) {
            return wrapOutgoing(original.remove(index));
        }

        @Override
        public boolean remove(Object o) {
            return original.remove(unwrapIncoming(o));
        }

        public Object getHandle() {
            return handle;
        }
    }
}
