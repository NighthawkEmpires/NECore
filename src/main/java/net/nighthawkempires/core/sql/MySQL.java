package net.nighthawkempires.core.sql;

import net.nighthawkempires.core.NECore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.*;
import java.util.logging.Level;

public class MySQL {

    private final String user;
    private final String database;
    private final String password;
    private final String port;
    private final String hostname;
    private Connection connection;

    public MySQL(String hostname, String port, String database, String username, String password) {
        this.hostname = hostname;
        this.port = port;
        this.database = database;
        this.user = username;
        this.password = password;
    }

    public void openConnection() {
        try {
            synchronized (this) {
                if (connection != null && !connection.isClosed()) {
                    return;
                }
            }

            Class.forName("com.mysql.jdbc.Driver");
            this.connection = DriverManager.getConnection("jdbc:mysql://" + this.hostname + ":" + this.port + "/" + this.database, this.user, this.password);
            NECore.getLoggers().info("Connected to MySQL.");

            try {
                PreparedStatement statement = getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS 'global_data' (" +
                        "'uuid' VARCHAR(45)," +
                        "'name' VARCHAR(45)," +
                        "'display-name' VARCHAR(45)," +
                        "'join-date' VARCHAR(45)," +
                        "'address' VARCHAR(45)," +
                        "'hub' VARCHAR(45)," +
                        "'survival' VARCHAR(45)," +
                        "'balance' INTEGER," +
                        "'kills' INTEGER," +
                        "'deaths' INTEGER," +
                        "'tokens' INTEGER," +
                        "'group' VARCHAR(45)," +
                        "'permissions' TEXT," +
                        "'status' VARCHAR(45)," +
                        "'donor' VARCHAR(45)");
                statement.executeQuery();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
            NECore.getLoggers().warn("Could not connect to MySQL due to a SQLException.");
        } catch (ClassNotFoundException exc) {
            NECore.getLoggers().warn("Could not connect to MySQL due to a ClassNotFoundException.");
            exc.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    private void checkConnection() {
        try {
            if (this.connection == null || this.connection.isClosed()) {
                this.openConnection();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

    }

    public void closeConnection() {
        if (this.connection != null) {
            try {
                this.connection.close();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }

    }

    public ResultSet query(String sql) {
        this.checkConnection();

        try {
            return this.connection.createStatement().executeQuery(sql);
        } catch (SQLException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public boolean update(String sql) {
        this.checkConnection();

        try {
            this.connection.createStatement().executeUpdate(sql);
            return true;
        } catch (SQLException exception) {
            exception.printStackTrace();
            return false;
        }
    }

    public void updateAsyncLater(final String sql) {
        (new BukkitRunnable() {
            public void run() {
                NECore.getMySQL().update(sql);
            }
        }).runTaskAsynchronously(NECore.getPlugin());
    }

    public PreparedStatement prepareStatement(String sql) {
        this.checkConnection();

        try {
            return this.connection.prepareStatement(sql);
        } catch (SQLException exception) {
            exception.printStackTrace();
            return null;
        }
    }
}
