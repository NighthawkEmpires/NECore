package net.nighthawkempires.core.events;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.entity.EntityDamageEvent;

public class UserPreDeathEvent extends Event implements Cancellable {

    private static HandlerList handlers = new HandlerList();
    private Player player;
    private Entity entityKiller;
    private Block blockKiller;
    private EntityDamageEvent.DamageCause damageCause;
    private UserDeathEvent.DeathType deathType;
    private String deathMessage = "";
    private TextComponent deathComponent;
    private boolean cancelled = false;

    public UserPreDeathEvent(Player player, Entity entity, EntityDamageEvent.DamageCause damageCause) {
        this.player = player;
        this.entityKiller = entity;
        this.damageCause = damageCause;
        this.deathType = UserDeathEvent.DeathType.DEATH_BY_ENTITY;
    }

    public UserPreDeathEvent(Player player, Block block, EntityDamageEvent.DamageCause damageCause) {
        this.player = player;
        this.blockKiller = block;
        this.damageCause = damageCause;
        this.deathType = UserDeathEvent.DeathType.DEATH_BY_BLOCK;
    }

    public UserPreDeathEvent(Player player, EntityDamageEvent.DamageCause damageCause) {
        this.player = player;
        this.damageCause = damageCause;
        this.deathType = UserDeathEvent.DeathType.DEATH;
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

    public UserDeathEvent.DeathType getDeathType() {
        return deathType;
    }

    public EntityDamageEvent.DamageCause getDamageCause() {
        return damageCause;
    }

    public String getDeathMessage() {
        return deathMessage;
    }

    public void setDeathMessage(String deathMessage) {
        this.deathMessage = deathMessage;
    }

    public TextComponent getDeathComponent() {
        return deathComponent;
    }

    public void setDeathComponent(TextComponent deathComponent) {
        this.deathComponent = deathComponent;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public enum DeathType {
        DEATH, DEATH_BY_ENTITY, DEATH_BY_BLOCK
    }
}
