package com.example.zoo.database;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;


@Component
public class MyDBConnection {

    @Value("${spring.datasource.url}")
    private String databaseUrl;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driver-class-name}")
    private String driver;

    private Connection conn = null;

    public Statement getMyConn() {
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(databaseUrl, username, password);
            return conn.createStatement();
        } catch (Exception e) {
            System.out.println("❌ Lỗi khi tạo Statement: " + e.getMessage());
        }
        return null;
    }

    public Connection getOnlyConn() {
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(databaseUrl, username, password);
            return conn;
        } catch (Exception e) {
            System.out.println("❌ Lỗi khi tạo Connection: " + e.getMessage());
        }
        return null;
    }
}
