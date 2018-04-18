package net.nighthawkempires.core.mute.registry;

import com.demigodsrpg.util.datasection.AbstractMongoRegistry;
import com.mongodb.client.MongoDatabase;
import net.nighthawkempires.core.mute.MuteModel;

import java.util.Map;

public class MMuteRegistry extends AbstractMongoRegistry<MuteModel> implements MuteRegistry {

    public MMuteRegistry(MongoDatabase database) {
        super(database.getCollection(NAME), 5);
    }

    @Override
    public Map<String, MuteModel> getRegisteredData() {
        return REGISTERED_DATA.asMap();
    }
}
