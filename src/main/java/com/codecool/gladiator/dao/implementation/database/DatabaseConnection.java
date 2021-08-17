package com.codecool.gladiator.dao.implementation.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class DatabaseConnection {
    protected Connection getConnection() {
        Connection connection = null;
        String databaseType = "postgresql";
        String host = "localhost";
        int port = 5432;
        String databaseName = "gladiator";
        String userName = "hamargyuri";
        String password = "data";


        try {
            connection = DriverManager.getConnection(
                    String.format("jdbc:%s://%s:%d/%s", databaseType, host, port, databaseName),
                    userName,
                    password
            );
        } catch (SQLException e) {
            System.err.println("Database not reachable.");
        }
        return connection;
    }

    protected void createTableIfNotExists(String tableName, String schema) {
        String query = String.format("CREATE TABLE IF NOT EXISTS %s %s", tableName, schema);
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            System.err.println(tableName + " table could not be created.");
        }
    }
}
