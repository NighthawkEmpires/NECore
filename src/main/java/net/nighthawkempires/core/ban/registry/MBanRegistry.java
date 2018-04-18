package net.nighthawkempires.core.ban.registry;

import com.demigodsrpg.util.datasection.AbstractMongoRegistry;
import com.mongodb.client.MongoDatabase;
import net.nighthawkempires.core.ban.BanModel;

import java.util.Map;

public class MBanRegistry extends AbstractMongoRegistry<BanModel> implements BanRegistry {

    public MBanRegistry(MongoDatabase database) {
        super(database.getCollection(NAME), 5);
    }

    @Override
    public Map<String, BanModel> getRegisteredData() {
        return REGISTERED_DATA.asMap();
    }
}
