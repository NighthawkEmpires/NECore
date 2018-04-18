package net.nighthawkempires.core.mute.registry;

import com.demigodsrpg.util.datasection.DataSection;
import com.demigodsrpg.util.datasection.Registry;
import net.nighthawkempires.core.mute.MuteModel;

import java.util.Map;
import java.util.UUID;

public interface MuteRegistry extends Registry<MuteModel>{
    String NAME = "mutes";

    default MuteModel fromDataSection(String stringKey, DataSection data) {
        return new MuteModel(stringKey, data);
    }

    default MuteModel getMute(UUID uuid) {
        if (uuid == null) {
            return null;
        }
        return fromKey(uuid.toString()).orElseGet(() -> register(new MuteModel(uuid)));
    }

    @Deprecated
    Map<String, MuteModel> getRegisteredData();

    default boolean banExists(UUID uuid) {
        return fromKey(uuid.toString()).isPresent();
    }
}
