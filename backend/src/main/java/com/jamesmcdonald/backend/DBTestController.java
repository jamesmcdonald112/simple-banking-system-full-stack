package com.jamesmcdonald.backend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@RestController
public class DBTestController {

    @GetMapping("/ping-db")
    public String testConnection() {
        try (Connection conn = DriverManager.getConnection(
                System.getenv("SPRING_DATASOURCE_URL"),
                System.getenv("SPRING_DATASOURCE_USERNAME"),
                System.getenv("SPRING_DATASOURCE_PASSWORD"))
        ) {
            return conn.isValid(2) ? "✅ DB connection successful" : "❌ DB connection failed";
        } catch (SQLException e) {
            return "❌ Connection failed: " + e.getMessage();
        }
    }
}