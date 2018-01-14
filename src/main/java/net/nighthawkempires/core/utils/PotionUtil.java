package net.nighthawkempires.core.utils;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PotionUtil {

    public static void applyPotion(Player player, PotionEffectType type, int amplifier, int length) {
        player.getActivePotionEffects().stream()
                .filter(effect -> effect.getType().equals(type) && effect.getAmplifier() <= amplifier)
                .forEach(effect -> {
                    player.removePotionEffect(type);
                });
        player.addPotionEffect(new PotionEffect(type, 15 * length, amplifier, false, false));
    }

    public static void applyPotion(LivingEntity entity, PotionEffectType type, int amplifier, int length) {
        entity.getActivePotionEffects().stream()
                .filter(effect -> effect.getType().equals(type) && effect.getAmplifier() <= amplifier)
                .forEach(effect -> {
                    entity.removePotionEffect(type);
                });
        entity.addPotionEffect(new PotionEffect(type, 15 * length, amplifier, false, false));
    }

    public static void removePotion(Player player, PotionEffectType type) {
        player.getActivePotionEffects().stream().filter(effect -> effect.getType().equals(type)).forEach(effect -> {
            player.removePotionEffect(type);
        });
    }

    public static void removePotion(LivingEntity entity, PotionEffectType type) {
        entity.getActivePotionEffects().stream().filter(effect -> effect.getType().equals(type)).forEach(effect -> {
            entity.removePotionEffect(type);
        });
    }

    public static void removePotions(Player player) {
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
    }

    public static void removePotions(LivingEntity entity) {
        for (PotionEffect effect : entity.getActivePotionEffects()) {
            entity.removePotionEffect(effect.getType());
        }
    }
}
