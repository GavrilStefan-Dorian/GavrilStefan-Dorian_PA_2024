package org.example;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class Database {
    private static final String URL = "jdbc:postgresql://localhost:5432/Books_Lab8Java";
    // create the db
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    private static final HikariConfig config = new HikariConfig();
    private static HikariDataSource dataSource;

    private Database() {}

    public static Connection getConnection() throws SQLException {
        if (dataSource == null) {
            createDataSource();
        }
        return dataSource.getConnection();
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
        if (dataSource != null)
            dataSource.close();
    }
}
