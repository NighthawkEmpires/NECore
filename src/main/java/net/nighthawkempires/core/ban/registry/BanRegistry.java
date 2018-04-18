package net.nighthawkempires.core.ban.registry;

import com.demigodsrpg.util.datasection.DataSection;
import com.demigodsrpg.util.datasection.Registry;
import net.nighthawkempires.core.ban.BanModel;

import java.util.Map;
import java.util.UUID;

public interface BanRegistry extends Registry<BanModel>{
    String NAME = "bans";

    default BanModel fromDataSection(String stringKey, DataSection data) {
        return new BanModel(stringKey, data);
    }

    default BanModel getBan(UUID uuid) {
        if (uuid == null) {
            return null;
        }
        return fromKey(uuid.toString()).orElseGet(() -> register(new BanModel(uuid)));
    }

    @Deprecated
    Map<String, BanModel> getRegisteredData();

    default boolean banExists(UUID uuid) {
        return fromKey(uuid.toString()).isPresent();
    }
}
