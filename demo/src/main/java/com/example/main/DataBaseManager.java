package com.example.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * @author Kamil Krzemiński 
 */
public class DataBaseManager {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/test";
    private static final String DB_USR = "root";
    private static final String DB_PASS = "Fc(UQVJZa4";

    private static DataBaseManager instance;
    private Connection connection;

    DataBaseManager() {
        
    }

    public static DataBaseManager getInstance() {
        if (instance == null) {
            instance = new DataBaseManager();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(DB_URL, DB_USR, DB_PASS);
        }
        return connection;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}