package net.nighthawkempires.core.events;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageEvent;

public class UserDeathEvent extends Event{

    private static HandlerList handlers = new HandlerList();
    private Player player;
    private Entity entityKiller;
    private Block blockKiller;
    private EntityDamageEvent.DamageCause damageCause;
    private DeathType deathType;

    public UserDeathEvent(Player player, Entity entity, EntityDamageEvent.DamageCause damageCause) {
        this.player = player;
        this.entityKiller = entity;
        this.damageCause = damageCause;
        this.deathType = DeathType.DEATH_BY_ENTITY;
    }

    public UserDeathEvent(Player player, Block block, EntityDamageEvent.DamageCause damageCause) {
        this.player = player;
        this.blockKiller = block;
        this.damageCause = damageCause;
        this.deathType = DeathType.DEATH_BY_BLOCK;
    }

    public UserDeathEvent(Player player, EntityDamageEvent.DamageCause damageCause) {
        this.player = player;
        this.damageCause = damageCause;
        this.deathType = DeathType.DEATH;
    }

    public Player getPlayer() {
        return player;
    }

    public Entity getEntityKiller() {
        return entityKiller;
    }

    public Block getBlockKiller() {
        return blockKiller;
    }

    public DeathType getDeathType() {
        return deathType;
    }

    public EntityDamageEvent.DamageCause getDamageCause() {
        return damageCause;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public enum DeathType {
        DEATH, DEATH_BY_ENTITY, DEATH_BY_BLOCK
    }
}
