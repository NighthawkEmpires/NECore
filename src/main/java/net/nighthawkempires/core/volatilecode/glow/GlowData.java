package net.nighthawkempires.core.volatilecode.glow;

import com.google.common.collect.Maps;

import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

public class GlowData {

    public ConcurrentMap<UUID, GlowManager.Color> colorMap;

    public GlowData() {
        colorMap = Maps.newConcurrentMap();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        GlowData glowData = (GlowData) o;

        return colorMap != null ? colorMap.equals(glowData.colorMap) : glowData.colorMap == null;

    }

    @Override
    public int hashCode() {
        return colorMap != null ? colorMap.hashCode() : 0;
    }
}
