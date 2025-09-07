package com.dg.mtms.server.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
    // TODO: handle db url changes
    private static final String DB_URL = "jdbc:sqlite:mtms.db";

    private DB() {}

    public static Connection createConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }
}
