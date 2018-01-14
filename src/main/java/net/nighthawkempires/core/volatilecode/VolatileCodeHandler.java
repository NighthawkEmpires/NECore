package net.nighthawkempires.core.volatilecode;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Vector;

import java.lang.reflect.InvocationTargetException;

public interface VolatileCodeHandler {

    void addPotionGraphicalEffect(LivingEntity entity, int color, int duration);

    void entityPathTo(LivingEntity entity, LivingEntity target);

    void sendFakeSlotUpdate(Player player, int slot, ItemStack item);

    void toggleLeverOrButton(Block block);

    void pressPressurePlate(Block block);

    boolean simulateTnt(Location target, LivingEntity source, float explosionSize, boolean fire);

    boolean createExplosionByPlayer(Player player, Location location, float size, boolean fire, boolean breakBlocks);

    void playExplosionEffect(Location location, float size);

    void setExperienceBar(Player player, int level, float percent);

    Fireball shootSmallFireball(Player player);

    void setTarget(LivingEntity entity, LivingEntity target);

    void playSound(Location location, String sound, float volume, float pitch);

    void playSound(Player player, String sound, float volume, float pitch);

    ItemStack addFakeEnchantment(ItemStack item);

    void setFallingBlockHurtEntities(FallingBlock block, float damage, int max);

    void addPotionEffect(LivingEntity entity, PotionEffect effect, boolean ambient);

    void playEntityAnimation(Location location, EntityType entityType, int animationId, boolean instant);

    void createFireworksExplosion(Location location, boolean flicker, boolean trail, int type, int[] colors,
                                  int[] fadeColors, int flightDuration);

    void setHeldItemSlot(Player player, int slot);

    void playParticleEffect(Location location, String name, float spreadHoriz, float spreadVert, float speed, int count,
                            int radius, float yOffset);

    void playParticleEffect(Location location, String name, float spreadX, float spreadY, float spreadZ, float speed,
                            int count, int radius, float yOffset);

    void setKiller(LivingEntity entity, Player killer);

    void playDragonDeathEffect(Location location);

    ItemStack addAttributes(ItemStack item, String[] names, String[] types, double[] amounts, int[] operations);

    ItemStack hideTooltipCrap(ItemStack item);

    void addEntityAttribute(LivingEntity entity, String attribute, double amount, int operation);

    void resetEntityAttributes(LivingEntity entity);

    void removeAI(LivingEntity entity);

    void setNoAIFlag(LivingEntity entity);

    void addAILookAtPlayer(LivingEntity entity, int range);

    void saveSkinData(Player player, String name);

    ItemStack setUnbreakable(ItemStack item);

    void setArrowsStuck(LivingEntity entity, int count);

    void sendTitleToPlayer(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut);

    void sendActionBarMessage(Player player, String message);

    void setTabMenuHeaderFooter(Player player, String header, String footer);

    void setClientVelocity(Player player, Vector velocity);

    double getAbsorptionHearts(LivingEntity entity);

    void setOffhand(Player player, ItemStack item);

    ItemStack getOffhand(Player player);

    void showItemCooldown(Player player, ItemStack item, int duration);

    String getItemStackInfo(ItemStack itemStack) throws InvocationTargetException, IllegalAccessException;
}
