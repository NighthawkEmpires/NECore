package net.nighthawkempires.core.users;

import com.google.common.collect.Maps;
import net.nighthawkempires.core.NECore;
import org.bukkit.Bukkit;

import java.sql.*;
import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

import static net.nighthawkempires.core.NECore.getPlugin;

public class UserManager {

    private final String SELECT = "SELECT * FROM global_data WHERE uuid=?";

    private ConcurrentMap<UUID, User> userMap;

    public UserManager() {
        userMap = Maps.newConcurrentMap();
    }

    public ConcurrentMap<UUID, User> getUserMap() {
        return userMap;
    }

    public User getUser(UUID uuid) {
        if (getUserMap().containsKey(uuid)) {
            return getUserMap().get(uuid);
        }
        return getTempUser(uuid);
    }

    public User getTempUser(UUID uuid) {
        if (userLoaded(uuid)) {
            return getUser(uuid);
        } else if (userExists(uuid)) {
            userGetter(new User(uuid));
            Bukkit.getScheduler().scheduleSyncDelayedTask(getPlugin(), () -> userSetter(getUser(uuid)), 100L);
            return getUser(uuid);
        }
        return null;
    }

    public void userGetter(User user) {
        new UserGetter(user).run();
        userMap.put(user.getUUID(), user);
    }

    public void userSetter(User user) {
        new UserSetter(user).run();
        userMap.remove(user.getUUID());
    }

    public boolean userExists(UUID uuid) {
        Connection connection;
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        try {
            connection = NECore.getConnector().getConnection();

            preparedStatement = connection.prepareStatement(SELECT);
            preparedStatement.setString(1, uuid.toString());

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                preparedStatement.close();
                resultSet.close();
                return true;
            } else {
                preparedStatement.close();
                resultSet.close();
                return false;
            }
        } catch (SQLException exception) {
            NECore.getLoggers().warn(NECore.getPlugin(), "Could not get user data from database.");
            return false;
        }
    }

    public boolean userLoaded(UUID uuid) {
        return getUserMap().containsKey(uuid);
    }
}
