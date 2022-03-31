package com.maxkhomoshchak.MovieApi.provider;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SimpleConnectionProvider implements ConnectionProvider{

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://localhost/max", "postgres", "root");
    }
}
