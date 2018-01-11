package net.nighthawkempires.core.volatilecode.glow;

import com.google.common.collect.Lists;
import net.nighthawkempires.core.NECore;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.inventivetalent.apihelper.API;
import org.inventivetalent.apihelper.APIManager;
import org.inventivetalent.packetlistener.PacketListenerAPI;
import org.inventivetalent.packetlistener.handler.*;
import org.inventivetalent.packetlistener.reflection.minecraft.Minecraft;
import org.inventivetalent.packetlistener.reflection.resolver.*;
import org.inventivetalent.packetlistener.reflection.resolver.minecraft.NMSClassResolver;
import org.inventivetalent.packetlistener.reflection.resolver.minecraft.OBCClassResolver;

import java.util.*;

public class GlowManager implements Listener, API{

    private static Map<UUID, GlowData> dataMap = new HashMap<>();

    private static final NMSClassResolver NMS_CLASS_RESOLVER = new NMSClassResolver();

    private static Class<?> PacketPlayOutEntityMetadata;
    private static Class<?> DataWatcher;
    private static Class<?> DataWatcherItem;
    private static Class<?> Entity;

    private static FieldResolver PacketPlayOutMetadataFieldResolver;
    private static FieldResolver EntityFieldResolver;
    private static FieldResolver DataWatcherFieldResolver;
    private static FieldResolver DataWatcherItemFieldResolver;

    private static ConstructorResolver DataWatcherItemConstructorResolver;

    private static MethodResolver DataWatcherMethodResolver;
    private static MethodResolver DataWatcherItemMethodResolver;
    private static MethodResolver EntityMethodResolver;

    private static Class<?> PacketPlayOutScoreboardTeam;

    private static FieldResolver PacketScoreboardTeamFieldResolver;

    private static FieldResolver EntityPlayerFieldResolver;
    private static MethodResolver PlayerConnectionMethodResolver;

    public static String TEAM_TAG_VISIBILITY = "always";
    public static String TEAM_PUSH = "always";

    public static void setGlowing(Entity entity, Color color, String tagVisibility, String push, Player receiver) {
        if (receiver == null) { return; }

        boolean glowing = color != null;
        if (entity == null) { glowing = false; }
        if (entity instanceof OfflinePlayer) { if (!((OfflinePlayer) entity).isOnline()) { glowing = false; } }

        boolean wasGlowing = dataMap.containsKey(entity != null ? entity.getUniqueId() : null);
        GlowData glowData;
        if (wasGlowing && entity != null) { glowData = dataMap.get(entity.getUniqueId()); } else { glowData = new GlowData(); }

        Color oldColor = wasGlowing ? glowData.colorMap.get(receiver.getUniqueId()) : null;

        if (glowing) {
            glowData.colorMap.put(receiver.getUniqueId(), color);
        } else {
            glowData.colorMap.remove(receiver.getUniqueId());
        }
        if (glowData.colorMap.isEmpty()) {
            dataMap.remove(entity != null ? entity.getUniqueId() : null);
        } else {
            if (entity != null) {
                dataMap.put(entity.getUniqueId(), glowData);
            }
        }

        if (color != null && oldColor == color) { return; }
        if (entity == null) { return; }
        if (entity instanceof OfflinePlayer) { if (!((OfflinePlayer) entity).isOnline()) { return; } }
        if (!receiver.isOnline()) { return; }

        sendGlowPacket(entity, wasGlowing, glowing, receiver);
        if (oldColor != null && oldColor != Color.NONE) {
            sendTeamPacket(entity, oldColor, false, false, tagVisibility, push, receiver);
        }
        if (glowing) {
            sendTeamPacket(entity, color, false, color != Color.NONE, tagVisibility, push, receiver);
        }
    }

    public static void setGlowing(Entity entity, Color color, Player receiver) {
        setGlowing(entity, color, "always", "always", receiver);
    }

    public static void setGlowing(Entity entity, boolean glowing, Player receiver) {
        setGlowing(entity, glowing ? Color.NONE : null, receiver);
    }

    public static void setGlowing(Entity entity, boolean glowing, Collection<? extends Player> receivers) {
        for (Player receiver : receivers) {
            setGlowing(entity, glowing, receiver);
        }
    }

    public static void setGlowing(Entity entity, Color color, Collection<? extends Player> receivers) {
        for (Player receiver : receivers) {
            setGlowing(entity, color, receiver);
        }
    }

    public static void setGlowing(Collection<? extends Entity> entities, Color color, Player receiver) {
        for (Entity entity : entities) {
            setGlowing(entity, color, receiver);
        }
    }

    public static void setGlowing(Collection<? extends Entity> entities, Color color, Collection<? extends Player> receivers) {
        for (Entity entity : entities) {
            setGlowing(entity, color, receivers);
        }
    }

    public static boolean isGlowing(Entity entity, Player receiver) {
        return getGlowColor(entity, receiver) != null;
    }

    public static boolean isGlowing(Entity entity, Collection<? extends Player> receivers, boolean checkAll) {
        if (checkAll) {
            boolean glowing = true;
            for (Player receiver : receivers) {
                if (!isGlowing(entity, receiver)) {
                    glowing = false;
                }
            }
            return glowing;
        } else {
            for (Player receiver : receivers) {
                if (isGlowing(entity, receiver)) { return true; }
            }
        }
        return false;
    }

    public static Color getGlowColor(Entity entity, Player receiver) {
        if (!dataMap.containsKey(entity.getUniqueId())) { return null; }
        GlowData data = dataMap.get(entity.getUniqueId());
        return data.colorMap.get(receiver.getUniqueId());
    }

    protected static void sendGlowPacket(Entity entity, boolean wasGlowing, boolean glowing, Player receiver) {
        try {
            if (PacketPlayOutEntityMetadata == null) {
                PacketPlayOutEntityMetadata = NMS_CLASS_RESOLVER.resolve("PacketPlayOutEntityMetadata");
            }
            if (DataWatcher == null) {
                DataWatcher = NMS_CLASS_RESOLVER.resolve("DataWatcher");
            }
            if (DataWatcherItem == null) {
                DataWatcherItem = NMS_CLASS_RESOLVER.resolve("DataWatcher$Item");
            }
            if (Entity == null) {
                Entity = NMS_CLASS_RESOLVER.resolve("Entity");
            }
            if (PacketPlayOutMetadataFieldResolver == null) {
                PacketPlayOutMetadataFieldResolver = new FieldResolver(PacketPlayOutEntityMetadata);
            }
            if (DataWatcherItemConstructorResolver == null) {
                DataWatcherItemConstructorResolver = new ConstructorResolver(DataWatcherItem);
            }
            if (EntityFieldResolver == null) {
                EntityFieldResolver = new FieldResolver(Entity);
            }
            if (DataWatcherMethodResolver == null) {
                DataWatcherMethodResolver = new MethodResolver(DataWatcher);
            }
            if (DataWatcherItemMethodResolver == null) {
                DataWatcherItemMethodResolver = new MethodResolver(DataWatcherItem);
            }
            if (EntityMethodResolver == null) {
                EntityMethodResolver = new MethodResolver(Entity);
            }
            if (DataWatcherFieldResolver == null) {
                DataWatcherFieldResolver = new FieldResolver(DataWatcher);
            }

            List list = Lists.newArrayList();

            Object dataWatcher = EntityMethodResolver.resolve("getDataWatcher").invoke(Minecraft.getHandle(entity));
            Map<Integer, Object> dataWatcherItems = (Map<Integer, Object>) DataWatcherFieldResolver.resolveByLastType(Map.class).get(dataWatcher);

            Object dataWatcherObject = org.inventivetalent.packetlistener.reflection.minecraft.DataWatcher.V1_9.ValueType.ENTITY_FLAG.getType();
            byte prev = (byte) (dataWatcherItems.isEmpty() ? 0 : DataWatcherItemMethodResolver.resolve("b").invoke(dataWatcherItems.get(0)));
            byte b = (byte) (glowing ? (prev | 1 << 6) : (prev & ~(1 << 6)));
            Object dataWatcherItem = DataWatcherItemConstructorResolver.resolveFirstConstructor().newInstance(dataWatcherObject, b);

            list.add(dataWatcherItem);

            Object packetMetadata = PacketPlayOutEntityMetadata.newInstance();
            PacketPlayOutMetadataFieldResolver.resolve("a").set(packetMetadata, -entity.getEntityId());
            PacketPlayOutMetadataFieldResolver.resolve("b").set(packetMetadata, list);

            sendPacket(packetMetadata, receiver);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    public static void initTeam(Player receiver, String tagVisibility, String push) {
        for (GlowManager.Color color : GlowManager.Color.values()) {
            GlowManager.sendTeamPacket(null, color, true, false, tagVisibility, push, receiver);
        }
    }

    public static void initTeam(Player receiver) {
        initTeam(receiver, TEAM_TAG_VISIBILITY, TEAM_PUSH);
    }

    protected static void sendTeamPacket(Entity entity, Color color, boolean createNewTeam, boolean addEntity, String tagVisibility, String push, Player receiver) {
        try {
            if (PacketPlayOutScoreboardTeam == null) {
                PacketPlayOutScoreboardTeam = NMS_CLASS_RESOLVER.resolve("PacketPlayOutScoreboardTeam");
            }
            if (PacketScoreboardTeamFieldResolver == null) {
                PacketScoreboardTeamFieldResolver = new FieldResolver(PacketPlayOutScoreboardTeam);
            }

            Object packetScoreboardTeam = PacketPlayOutScoreboardTeam.newInstance();
            PacketScoreboardTeamFieldResolver.resolve("i").set(packetScoreboardTeam, createNewTeam ? 0 : addEntity ? 3 : 4);
            PacketScoreboardTeamFieldResolver.resolve("a").set(packetScoreboardTeam, color.getTeamName());
            PacketScoreboardTeamFieldResolver.resolve("e").set(packetScoreboardTeam, tagVisibility);
            PacketScoreboardTeamFieldResolver.resolve("f").set(packetScoreboardTeam, push);

            if (createNewTeam) {
                PacketScoreboardTeamFieldResolver.resolve("g").set(packetScoreboardTeam, color.packetValue);
                PacketScoreboardTeamFieldResolver.resolve("c").set(packetScoreboardTeam, "§" + color.colorCode);

                PacketScoreboardTeamFieldResolver.resolve("b").set(packetScoreboardTeam, color.getTeamName());
                PacketScoreboardTeamFieldResolver.resolve("d").set(packetScoreboardTeam, "");
                PacketScoreboardTeamFieldResolver.resolve("j").set(packetScoreboardTeam, 0);
            }

            if (!createNewTeam) {
                Collection<String> collection = ((Collection<String>) PacketScoreboardTeamFieldResolver.resolve("h").get(packetScoreboardTeam));
                if (entity instanceof OfflinePlayer) {
                    collection.add(entity.getName());
                } else {
                    collection.add(entity.getUniqueId().toString());
                }
            }

            sendPacket(packetScoreboardTeam, receiver);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    protected static void sendPacket(Object packet, Player p) throws IllegalArgumentException, ClassNotFoundException {
        if (EntityPlayerFieldResolver == null) {
            EntityPlayerFieldResolver = new FieldResolver(NMS_CLASS_RESOLVER.resolve("EntityPlayer"));
        }
        if (PlayerConnectionMethodResolver == null) {
            PlayerConnectionMethodResolver = new MethodResolver(NMS_CLASS_RESOLVER.resolve("PlayerConnection"));
        }

        try {
            Object handle = Minecraft.getHandle(p);
            final Object connection = EntityPlayerFieldResolver.resolve("playerConnection").get(handle);
            PlayerConnectionMethodResolver.resolve("sendPacket").invoke(connection, packet);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    public enum Color {

        BLACK(0, "0"),
        DARK_BLUE(1, "1"),
        DARK_GREEN(2, "2"),
        DARK_AQUA(3, "3"),
        DARK_RED(4, "4"),
        DARK_PURPLE(5, "5"),
        GOLD(6, "6"),
        GRAY(7, "7"),
        DARK_GRAY(8, "8"),
        BLUE(9, "9"),
        GREEN(10, "a"),
        AQUA(11, "b"),
        RED(12, "c"),
        PURPLE(13, "d"),
        YELLOW(14, "e"),
        WHITE(15, "f"),
        NONE(-1, "");

        int    packetValue;
        String colorCode;

        Color(int packetValue, String colorCode) {
            this.packetValue = packetValue;
            this.colorCode = colorCode;
        }

        String getTeamName() {
            String name = String.format("GAPI#%s", name());
            if (name.length() > 16) {
                name = name.substring(0, 16);
            }
            return name;
        }
    }

    public void load() {
        APIManager.require(PacketListenerAPI.class, NECore.getPlugin());
    }

    public void init(Plugin plugin) {
        APIManager.initAPI(PacketListenerAPI.class);

        APIManager.registerEvents(this, this);

        PacketHandler.addHandler(new PacketHandler(NECore.getPlugin() != null ? NECore.getPlugin() : plugin) {
            @Override
            @PacketOptions(forcePlayer = true)
            public void onSend(SentPacket sentPacket) {
                if ("PacketPlayOutEntityMetadata".equals(sentPacket.getPacketName())) {
                    int a = (int) sentPacket.getPacketValue("a");
                    if (a < 0) {//Our packet
                        //Reset the ID and let it through
                        sentPacket.setPacketValue("a", -a);
                        return;
                    }

                    List b = (List) sentPacket.getPacketValue("b");
                    if (b == null || b.isEmpty()) {
                        return;//Nothing to modify
                    }

                    Entity entity = getEntityById(sentPacket.getPlayer().getWorld(), a);
                    if (entity != null) {
                        //Check if the entity is glowing
                        if (GlowManager.isGlowing(entity, sentPacket.getPlayer())) {
                            if (GlowManager.DataWatcherItemMethodResolver == null) {
                                GlowManager.DataWatcherItemMethodResolver = new MethodResolver(GlowManager.DataWatcherItem);
                            }
                            if (GlowManager.DataWatcherItemFieldResolver == null) {
                                GlowManager.DataWatcherItemFieldResolver = new FieldResolver(GlowManager.DataWatcherItem);
                            }

                            try {
                                //Update the DataWatcher Item
                                Object prevItem = b.get(0);
                                Object prevObj = GlowManager.DataWatcherItemMethodResolver.resolve("b").invoke(prevItem);
                                if (prevObj instanceof Byte) {
                                    byte prev = (byte) prevObj;
                                    byte bte = (byte) (true/*Maybe use the isGlowing result*/ ? (prev | 1 << 6) : (prev & ~(1 << 6)));//6 = glowing index
                                    GlowManager.DataWatcherItemFieldResolver.resolve("b").set(prevItem, bte);
                                }
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }
            }

            @Override
            public void onReceive(ReceivedPacket receivedPacket) {
            }
        });
    }

    @Override
    public void disable(Plugin plugin) {
    }

    @EventHandler
    public void onJoin(final PlayerJoinEvent event) {
        //Initialize the teams
        GlowManager.initTeam(event.getPlayer());
    }

    @EventHandler
    public void onQuit(final PlayerQuitEvent event) {
        for (Player receiver : Bukkit.getOnlinePlayers()) {
            if (GlowManager.isGlowing(event.getPlayer(), receiver)) {
                GlowManager.setGlowing(event.getPlayer(), null, receiver);
            }
        }
    }

    protected static NMSClassResolver nmsClassResolver = new NMSClassResolver();
    protected static OBCClassResolver obcClassResolver = new OBCClassResolver();

    private static FieldResolver  CraftWorldFieldResolver;
    private static FieldResolver  WorldFieldResolver;
    private static MethodResolver IntHashMapMethodResolver;

    public static Entity getEntityById(World world, int entityId) {
        try {
            if (CraftWorldFieldResolver == null) {
                CraftWorldFieldResolver = new FieldResolver(obcClassResolver.resolve("CraftWorld"));
            }
            if (WorldFieldResolver == null) {
                WorldFieldResolver = new FieldResolver(nmsClassResolver.resolve("World"));
            }
            if (IntHashMapMethodResolver == null) {
                IntHashMapMethodResolver = new MethodResolver(nmsClassResolver.resolve("IntHashMap"));
            }
            if (EntityMethodResolver == null) {
                EntityMethodResolver = new MethodResolver(nmsClassResolver.resolve("Entity"));
            }

            Object entitiesById = WorldFieldResolver.resolve("entitiesById").get(CraftWorldFieldResolver.resolve("world").get(world));
            Object entity = IntHashMapMethodResolver.resolve(new ResolverQuery("get", int.class))
                    .invoke(entitiesById, entityId);
            if (entity == null) { return null; }
            return (Entity) EntityMethodResolver.resolve("getBukkitEntity").invoke(entity);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
