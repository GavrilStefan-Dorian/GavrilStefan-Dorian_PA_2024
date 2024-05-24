package com.example.lab9;

import com.example.lab9.factories.AbstractFactory;
import com.example.lab9.factories.JDBCFactory;
//import com.example.lab9.factories.JPAFactory;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class Database {

    // JPA
    static final String PERSISTENCE_UNIT_NAME = "Lab9PU";
    private static Database instance;
    private static EntityManagerFactory entityManagerFactory;
    private static final String URL = "jdbc:postgresql://localhost:5432/Books_Lab8Java";
    // JDBC
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    private static final HikariConfig config = new HikariConfig();
    private static HikariDataSource dataSource;
    public static String dataAccessType;

    static {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("src//main//resources//app.properties"));
            dataAccessType = properties.getProperty("data.access.type");
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration file", e);
        }
    }
    private Database() {
    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public EntityManager getEntityManager() {
        if (entityManagerFactory == null && "jpa".equals(dataAccessType)) {
            entityManagerFactory = Persistence.createEntityManagerFactory("Lab9PU");
        }
        return "jpa".equals(dataAccessType) ? entityManagerFactory.createEntityManager() : null;
    }

    public static Connection getConnection() throws SQLException {
        switch(dataAccessType) {
            case "jpa":
                return null;
            case "jdbc":
            default:
                if (dataSource == null) {
                    createDataSource();
                }
                return dataSource.getConnection();
        }
    }

    private static void createDataSource() {
        config.setJdbcUrl(URL);
        config.setUsername(USER);
        config.setPassword(PASSWORD);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        dataSource = new HikariDataSource(config);
    }

    public static void closeDataSource() {
        switch(dataAccessType) {
            case "jpa":
                return;
            case "jdbc":
            default:
                if (dataSource != null)
                    dataSource.close();
        }
    }
}

