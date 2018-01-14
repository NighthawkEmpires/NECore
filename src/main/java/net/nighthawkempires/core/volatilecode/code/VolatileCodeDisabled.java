package net.nighthawkempires.core.volatilecode.code;

import net.nighthawkempires.core.volatilecode.VolatileCodeHandler;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.material.Button;
import org.bukkit.material.Lever;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class VolatileCodeDisabled implements VolatileCodeHandler {


    public void addPotionGraphicalEffect(LivingEntity entity, int color, int duration) {
    }


    public void entityPathTo(LivingEntity entity, LivingEntity target) {
    }


    public void sendFakeSlotUpdate(Player player, int slot, ItemStack item) {
    }


    public void toggleLeverOrButton(Block block) {
        if (block.getType() == Material.STONE_BUTTON || block.getType() == Material.WOOD_BUTTON) {
            BlockState state = block.getState();
            Button button = (Button) state.getData();
            button.setPowered(true);
            state.update();
        } else if (block.getType() == Material.LEVER) {
            BlockState state = block.getState();
            Lever lever = (Lever) state.getData();
            lever.setPowered(!lever.isPowered());
            state.update();
        }
    }


    public void pressPressurePlate(Block block) {
        block.setData((byte) (block.getData() ^ 0x1));
    }


    public boolean simulateTnt(Location target, LivingEntity source, float explosionSize, boolean fire) {
        return false;
    }


    public boolean createExplosionByPlayer(Player player, Location location, float size, boolean fire,
                                           boolean breakBlocks) {
        return location.getWorld().createExplosion(location, size, fire);
    }


    public void playExplosionEffect(Location location, float size) {
        location.getWorld().createExplosion(location, 0F);
    }


    public void setExperienceBar(Player player, int level, float percent) {
    }


    public Fireball shootSmallFireball(Player player) {
        return player.launchProjectile(SmallFireball.class);
    }


    public void setTarget(LivingEntity entity, LivingEntity target) {
        if (entity instanceof Creature) {
            ((Creature) entity).setTarget(target);
        }
    }


    public void playSound(Location location, String sound, float volume, float pitch) {
    }

    @SuppressWarnings("deprecation")

    public void playSound(Player player, String sound, float volume, float pitch) {
        player.playSound(player.getLocation(), sound, volume, pitch);
    }


    public ItemStack addFakeEnchantment(ItemStack item) {
        return item;
    }


    public void setFallingBlockHurtEntities(FallingBlock block, float damage, int max) {
    }

    @Override
    public void addPotionEffect(LivingEntity entity, PotionEffect effect, boolean ambient) {

    }


    public void playEntityAnimation(Location location, EntityType entityType, int animationId, boolean instant) {
        if (entityType == EntityType.OCELOT && animationId == 7) {
            Ocelot entity = (Ocelot) location.getWorld().spawnEntity(location, entityType);
            entity.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 10, 0));
            entity.playEffect(EntityEffect.WOLF_HEARTS);
            entity.remove();
        }
    }


    public void createFireworksExplosion(Location location, boolean flicker, boolean trail, int type, int[] colors,
                                         int[] fadeColors, int flightDuration) {
        FireworkEffect.Type t = FireworkEffect.Type.BALL;
        if (type == 1) {
            t = FireworkEffect.Type.BALL_LARGE;
        } else if (type == 2) {
            t = FireworkEffect.Type.STAR;
        } else if (type == 3) {
            t = FireworkEffect.Type.CREEPER;
        } else if (type == 4) {
            t = FireworkEffect.Type.BURST;
        }
        Color[] c1 = new Color[colors.length];
        for (int i = 0; i < colors.length; i++) {
            c1[i] = Color.fromRGB(colors[i]);
        }
        Color[] c2 = new Color[fadeColors.length];
        for (int i = 0; i < fadeColors.length; i++) {
            c2[i] = Color.fromRGB(fadeColors[i]);
        }
        FireworkEffect effect = FireworkEffect.builder()
                .flicker(flicker)
                .trail(trail)
                .with(t)
                .withColor(c1)
                .withFade(c2)
                .build();
        Firework firework = location.getWorld().spawn(location, Firework.class);
        FireworkMeta meta = firework.getFireworkMeta();
        meta.addEffect(effect);
        meta.setPower(flightDuration < 1 ? 1 : flightDuration);
        firework.setFireworkMeta(meta);
    }

    @Override
    public void setHeldItemSlot(Player player, int slot) {

    }


    public void playParticleEffect(Location location, String name, float spreadHoriz, float spreadVert, float speed,
                                   int count, int radius, float yOffset) {
    }


    public void playParticleEffect(Location location, String name, float spreadX, float spreadY, float spreadZ,
                                   float speed, int count, int radius, float yOffset) {
    }


    public void playDragonDeathEffect(Location location) {

    }


    public void setKiller(LivingEntity entity, Player killer) {

    }

    public ItemStack addAttributes(ItemStack item, String[] names, String[] types, double[] amounts, int[] operations) {
        return item;
    }


    public void removeAI(LivingEntity entity) {
    }


    public void setNoAIFlag(LivingEntity entity) {
    }


    public void addEntityAttribute(LivingEntity entity, String attribute, double amount, int operation) {
    }


    public void resetEntityAttributes(LivingEntity entity) {
    }


    public void addAILookAtPlayer(LivingEntity entity, int range) {
    }


    public void saveSkinData(Player player, String name) {
    }


    public ItemStack setUnbreakable(ItemStack item) {
        return item;
    }


    public void setArrowsStuck(LivingEntity entity, int count) {
    }


    public void sendTitleToPlayer(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
    }


    public void sendActionBarMessage(Player player, String message) {
    }


    public void setTabMenuHeaderFooter(Player player, String header, String footer) {
    }


    public ItemStack hideTooltipCrap(ItemStack item) {
        return item;
    }


    public void setClientVelocity(Player player, Vector velocity) {
        player.setVelocity(velocity);
    }


    public double getAbsorptionHearts(LivingEntity entity) {
        return 0;
    }


    public void setOffhand(Player player, ItemStack item) {
    }


    public ItemStack getOffhand(Player player) {
        // TODO Auto-generated method stub
        return null;
    }


    public void showItemCooldown(Player player, ItemStack item, int duration) {
        // TODO Auto-generated method stub

    }

    @Override
    public String getItemStackInfo(ItemStack itemStack) {
        return null;
    }
}
