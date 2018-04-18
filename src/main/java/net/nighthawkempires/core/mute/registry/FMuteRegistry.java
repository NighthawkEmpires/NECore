package net.nighthawkempires.core.mute.registry;

import com.demigodsrpg.util.datasection.AbstractFileRegistry;
import net.nighthawkempires.core.mute.MuteModel;

import java.util.Map;

public class FMuteRegistry extends AbstractFileRegistry<MuteModel> implements MuteRegistry {
    private static final boolean SAVE_PRETTY = true;

    public FMuteRegistry(String path) {
        super(path, NAME, SAVE_PRETTY, 5);
    }

    @Override
    public Map<String, MuteModel> getRegisteredData() {
        return REGISTERED_DATA.asMap();
    }
}
