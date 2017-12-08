package net.nighthawkempires.core.events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageEvent;

public class UserDeathEvent extends Event{

    private static HandlerList handlers = new HandlerList();
    private Player player;
    private Entity killer;
    private EntityDamageEvent.DamageCause damageCause;

    public UserDeathEvent(Player player, Entity killer, EntityDamageEvent.DamageCause damageCause) {
        this.player = player;
        this.killer = killer;
        this.damageCause = damageCause;
    }

    public Player getPlayer() {
        return player;
    }

    public Entity getKiller() {
        return killer;
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
}
