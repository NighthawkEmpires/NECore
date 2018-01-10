package net.nighthawkempires.core.users;

import net.nighthawkempires.core.NECore;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserSetter {

    private User user;

    private final String UPDATE = "UPDATE global_data SET name=?,display_name=?,address=?,hub=?," +
            "survival=?,tokens=?,prison=?,freebuild=?,minigames=?,test=? WHERE uuid=?";

    public UserSetter(User user) {
        this.user = user;
    }

    public void run() {
        Connection connection;
        PreparedStatement preparedStatement;

        try {
            connection = NECore.getConnector().getConnection();

            preparedStatement = connection.prepareStatement(UPDATE);
            preparedStatement.setString(1, Bukkit.getOfflinePlayer(user.getUUID()).getName());
            preparedStatement.setString(2, (!user.getDisplayName().equals(user.getName()) ? user.getDisplayName() : ""));
            preparedStatement.setString(3, (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(user.getUUID()))
                    ? Bukkit.getPlayer(user.getUUID()).getAddress().getHostName() : ""));
            preparedStatement.setString(4, String.valueOf(user.hub()));
            preparedStatement.setString(5, String.valueOf(user.sur()));
            preparedStatement.setInt(6, user.getTokens());
            preparedStatement.setString(7, String.valueOf(user.prs()));
            preparedStatement.setString(8, String.valueOf(user.frb()));
            preparedStatement.setString(9, String.valueOf(user.min()));
            preparedStatement.setString(10, String.valueOf(user.test()));
            preparedStatement.setString(11, user.getUUID().toString());
            preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (SQLException exception) {
            NECore.getLoggers().warn(NECore.getPlugin(), "Could not set user data into database.");
        }
    }
}
