package net.nighthawkempires.core.effect;

import de.slikey.effectlib.Effect;
import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.util.ParticleEffect;
import org.bukkit.Location;

public class CustomCircleEffect extends Effect {

    public int radius;
    public ParticleEffect effect;

    public CustomCircleEffect(EffectManager effectManager) {
        super(effectManager);
    }

    public void onRun() {
        if (getLocation() != null) {
            for (int x = -radius; x < radius; x++) {
                for (int z = -radius; z < radius; z++) {
                    Location location =
                            getLocation().getBlock().getRelative(x, getLocation().getBlockY(), z).getLocation();
                    this.display(this.effect, location);
                }
            }
        }
    }
}
