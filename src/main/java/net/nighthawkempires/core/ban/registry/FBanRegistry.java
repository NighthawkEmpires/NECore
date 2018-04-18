package net.nighthawkempires.core.ban.registry;

import com.demigodsrpg.util.datasection.AbstractFileRegistry;
import net.nighthawkempires.core.ban.BanModel;

import java.util.Map;

public class FBanRegistry extends AbstractFileRegistry<BanModel> implements BanRegistry {
    private static final boolean SAVE_PRETTY = true;

    public FBanRegistry(String path) {
        super(path, NAME, SAVE_PRETTY, 5);
    }

    @Override
    public Map<String, BanModel> getRegisteredData() {
        return REGISTERED_DATA.asMap();
    }
}
