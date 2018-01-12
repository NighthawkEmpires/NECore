package net.nighthawkempires.core.users.registry;

import com.demigodsrpg.util.datasection.AbstractMongoRegistry;
import com.mongodb.client.MongoDatabase;
import net.nighthawkempires.core.users.UserModel;

import java.util.Map;

public class MUserRegistry extends AbstractMongoRegistry<UserModel> implements UserRegistry {

    public MUserRegistry(MongoDatabase database) {
        super(database.getCollection(NAME), 5);
    }

    @Override
    public Map<String, UserModel> getRegisteredData() {
        return REGISTERED_DATA.asMap();
    }
}
