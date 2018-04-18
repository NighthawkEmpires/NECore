package net.nighthawkempires.core.sql;

import net.nighthawkempires.core.NECore;
import net.nighthawkempires.core.logger.LogManager;

import java.sql.*;

public class SQLConnector {

    private final int DEFAULT_PORT = 3306;

    private String address;
    private String name;
    private String username;
    private String password;

    private int port;

    private Connection connection;

    public SQLConnector(String address, String name, String username, String password) {
        this.address = address;
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public SQLConnector(String address, String name, String username, String password, int port) {
        this(address, name, username, password);
        this.port = port;
    }

    public PreparedStatement prepareStatement(String statement) {
        final Connection conn = getConnection();


        try {
            return conn.prepareStatement(statement);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to execute statement: " + statement, e);
        }
    }

    public Connection getConnection() {
        if (!this.alive()) {
            this.connect();
        }

        return connection;
    }

    private void connect() {
        String connectionAddress = String.format("jdbc:mysql://%s:%s/%s/?username=%s&password=%s",
                address, port != 0 ? port : DEFAULT_PORT, name, username, password);

        try {
            int usedPort = port != 0 ? port : DEFAULT_PORT;
            connection = DriverManager
                    .getConnection("jdbc:mysql://" + address + ":" + usedPort + "/" + name, username, password);
            LogManager.info(NECore.getPlugin(), "Connected to SQL");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to database at " + connectionAddress + "!", e);
        }
    }

    private boolean alive() {
        if (connection == null) {
            return false;
        }

        try {
            PreparedStatement ps = connection.prepareStatement("/* ping */ SELECT 1;");
            ResultSet rs = ps.executeQuery();

            return rs != null;
        } catch (SQLException e) {
            return false;
        }
    }

    public void ping() {
        Connection connection;
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement("/* ping */ SELECT 1;");
            resultSet = preparedStatement.executeQuery();

            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}