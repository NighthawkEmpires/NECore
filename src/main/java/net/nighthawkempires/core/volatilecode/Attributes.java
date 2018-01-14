package net.nighthawkempires.core.volatilecode;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import org.bukkit.inventory.ItemStack;

import java.util.Iterator;
import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

public class Attributes {

    public enum Operation {
        ADD_NUMBER(0),
        MULTIPLY_PERCENTAGE(1),
        ADD_PERCENTAGE(2);
        private int id;

        Operation(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public static Operation fromId(int id) {
            for (Operation op : values()) {
                if (op.getId() == id) {
                    return op;
                }
            }
            throw new IllegalArgumentException("Corrupt operation ID " + id + " detected.");
        }
    }

    public static class AttributeType {
        private static ConcurrentMap<String, AttributeType> LOOKUP = Maps.newConcurrentMap();
        public static final AttributeType GENERIC_MAX_HEALTH = new AttributeType("generic.maxHealth").register();
        public static final AttributeType GENERIC_FOLLOW_RANGE = new AttributeType("generic.followRange").register();
        public static final AttributeType GENERIC_ATTACK_DAMAGE = new AttributeType("generic.attackDamage").register();
        public static final AttributeType GENERIC_MOVEMENT_SPEED =
                new AttributeType("generic.movementSpeed").register();
        public static final AttributeType GENERIC_KNOCKBACK_RESISTANCE =
                new AttributeType("generic.knockbackResistance").register();
        public static final AttributeType GENERIC_ARMOR = new AttributeType("generic.armor").register();
        public static final AttributeType GENERIC_ARMOR_TOUGHNESS =
                new AttributeType("generic.armorToughness").register();

        private final String minecraftId;

        public AttributeType(String minecraftId) {
            this.minecraftId = minecraftId;
        }

        public String getMinecraftId() {
            return minecraftId;
        }

        public AttributeType register() {
            AttributeType old = LOOKUP.putIfAbsent(minecraftId, this);
            return old != null ? old : this;
        }

        public static AttributeType fromId(String minecraftId) {
            return LOOKUP.get(minecraftId);
        }

        public static Iterable<AttributeType> values() {
            return LOOKUP.values();
        }
    }

    public static class Attribute {
        private NBTFactory.NBTCompound data;

        private Attribute(Builder builder) {
            data = NBTFactory.createCompound();
            setAmount(builder.amount);
            setOperation(builder.operation);
            setAttributeType(builder.type);
            setName(builder.name);
            setUUID(builder.uuid);
            setSlot(builder.slot);
        }

        private Attribute(NBTFactory.NBTCompound data) {
            this.data = data;
        }

        public double getAmount() {
            return data.getDouble("Amount", 0.0);
        }

        public void setAmount(double amount) {
            data.put("Amount", amount);
        }

        public Operation getOperation() {
            return Operation.fromId(data.getInteger("Operation", 0));
        }

        public void setOperation(Operation operation) {
            Preconditions.checkNotNull(operation, "operation cannot be NULL.");
            data.put("Operation", operation.getId());
        }

        public AttributeType getAttributeType() {
            return AttributeType.fromId(data.getString("AttributeName", null));
        }

        public void setAttributeType(AttributeType type) {
            Preconditions.checkNotNull(type, "type cannot be NULL.");
            data.put("AttributeName", type.getMinecraftId());
        }

        public String getName() {
            return data.getString("Name", null);
        }

        public void setName(String name) {
            Preconditions.checkNotNull(name, "name cannot be NULL.");
            data.put("Name", name);
        }

        public UUID getUUID() {
            return new UUID(data.getLong("UUIDMost", null), data.getLong("UUIDLeast", null));
        }

        public void setUUID(UUID id) {
            Preconditions.checkNotNull("id", "id cannot be NULL.");
            data.put("UUIDLeast", id.getLeastSignificantBits());
            data.put("UUIDMost", id.getMostSignificantBits());
        }

        public String getSlot() {
            return data.getString("Slot", null);
        }

        public void setSlot(String slot) {
            Preconditions.checkNotNull(slot, "slot cannot be NULL.");
            data.put("Slot", slot);
        }

        public NBTFactory.NBTCompound getData() {
            return data;
        }

        public void setData(NBTFactory.NBTCompound data) {
            this.data = data;
        }

        public static Builder newBuilder() {
            return new Builder().uuid(UUID.randomUUID()).operation(Operation.ADD_NUMBER);
        }

        public static class Builder {
            private double amount;
            private Operation operation = Operation.ADD_NUMBER;
            private AttributeType type;
            private String name;
            private UUID uuid;
            private String slot;

            private Builder() {

            }

            public Builder amount(double amount) {
                this.amount = amount;
                return this;
            }

            public Builder operation(Operation operation) {
                this.operation = operation;
                return this;
            }

            public Builder type(AttributeType type) {
                this.type = type;
                return this;
            }

            public Builder name(String name) {
                this.name = name;
                return this;
            }

            public Builder uuid(UUID uuid) {
                this.uuid = uuid;
                return this;
            }

            public Builder slot(String slot) {
                this.slot = slot;
                return this;
            }

            public Attribute build() {
                return new Attribute(this);
            }
        }
    }

    public ItemStack stack;
    private NBTFactory.NBTList attributes;

    public Attributes(ItemStack stack) {
        this.stack = NBTFactory.getCraftItemStack(stack);

        NBTFactory.NBTCompound nbt = NBTFactory.fromItemTag(this.stack);
        this.attributes = nbt.getList("AttributeModifiers", true);
    }

    public ItemStack getStack() {
        return stack;
    }

    public int size() {
        return attributes.size();
    }

    public void add(Attribute attribute) {
        Preconditions.checkNotNull(attribute.getName(), "must specify an attribute name.");
        attributes.add(attribute.data);
    }

    public boolean remove(Attribute attribute) {
        UUID uuid = attribute.getUUID();

        for (Iterator<Attribute> it = values().iterator(); it.hasNext(); ) {
            if (Objects.equal(it.next().getUUID(), uuid)) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    public void clear() {
        attributes.clear();
    }

    public Attribute get(int index) {
        return new Attribute((NBTFactory.NBTCompound) attributes.get(index));
    }

    public Iterable<Attribute> values() {
        return () -> Iterators.transform(attributes.iterator(),
                element -> new Attribute((NBTFactory.NBTCompound) element));
    }
}
