package com.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
    private Connection connection;

    public DBConnector() {
        System.setProperty("org.sqlite.lib.verbose", "true");
        try {
            Class.forName("org.sqlite.JDBC");

            String url = "jdbc:sqlite:/Users/suinlee/Desktop/apache-tomcat-10.1.39/webapps/ROOT/IoTBayDB.db";
            connection = DriverManager.getConnection(url);
            connection.setAutoCommit(true);
            System.out.println("✅ Connected to database");

        } catch (ClassNotFoundException e) {
            System.out.println("XX. SQLite JDBC Driver not found");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("XX. Failed to connect to SQLite DB");
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Connection closed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
