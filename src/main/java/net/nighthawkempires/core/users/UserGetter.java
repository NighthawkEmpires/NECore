package net.nighthawkempires.core.users;

import net.nighthawkempires.core.NECore;
import org.bukkit.Bukkit;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserGetter {

    private User user;

    private final String SELECT = "SELECT * FROM global_data WHERE uuid=?";
    private final String INSERT = "INSERT INTO global_data(uuid,name,display_name,join_date," +
            "address,hub,survival,tokens,prison,freebuild,minigames,test) VALUE (?,?,?,?,?,?,?,?,?,?,?,?)";

    public UserGetter(User user) {
        this.user = user;
    }

    public void run() {
        Connection connection;
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        try {
            connection = NECore.getConnector().getConnection();

            preparedStatement = connection.prepareStatement(SELECT);
            preparedStatement.setString(1, user.getUUID().toString());

            resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                preparedStatement = connection.prepareStatement(INSERT);
                preparedStatement.setString(1, user.getUUID().toString());
                preparedStatement.setString(2, Bukkit.getOfflinePlayer(user.getUUID()).getName());
                preparedStatement.setString(3, "");
                preparedStatement.setString(4, new SimpleDateFormat("MM/dd/yyyy").format(new Date()));
                preparedStatement.setString(5, (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(user.getUUID()))
                        ? Bukkit.getPlayer(user.getUUID()).getAddress().getHostName() : ""));
                preparedStatement.setString(6, "true");
                preparedStatement.setString(7, "false");
                preparedStatement.setInt(8, 15);
                preparedStatement.setString(9, "false");
                preparedStatement.setString(10, "false");
                preparedStatement.setString(11, "false");
                preparedStatement.setString(12, "false");
                preparedStatement.executeQuery();
            }

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user.setName(Bukkit.getOfflinePlayer(user.getUUID()).getName());
                user.setDisplayName(resultSet.getString("display_name"));
                if (user.getDisplayName().equals("") || user.getDisplayName().equals(null)) {
                    user.setDisplayName(user.getName());
                }
                user.setJoinDate(resultSet.getString("join_date"));
                user.setAddress((Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(user.getUUID()))
                        ? Bukkit.getPlayer(user.getUUID()).getAddress().getHostName() : ""));
                user.setHub(Boolean.valueOf(resultSet.getString("hub")));
                user.setSur(Boolean.valueOf(resultSet.getString("survival")));
                user.setTokens(resultSet.getInt("tokens"));
                user.setPrs(Boolean.valueOf(resultSet.getString("prison")));
                user.setFrb(Boolean.valueOf(resultSet.getString("freebuild")));
                user.setMin(Boolean.valueOf(resultSet.getString("minigames")));
                user.setTest(Boolean.valueOf(resultSet.getString("test")));
            }

            preparedStatement.close();
            resultSet.close();
        } catch (SQLException exception) {
            NECore.getLoggers().warn(NECore.getPlugin(), "Could not getRaw user data from database.");
        }
    }
}
