package lk.textflow.config;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {

    private static final Properties properties = new Properties();

    static {
        try (InputStream input =
                     DatabaseConnection.class
                             .getClassLoader()
                             .getResourceAsStream("database.properties")) {

            if (input == null) {
                throw new RuntimeException(
                        "database.properties not found"
                );
            }

            properties.load(input);

        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to load database configuration",
                    e
            );
        }
    }

    public static Connection getConnection()
            throws SQLException {

        return DriverManager.getConnection(
                properties.getProperty("db.url"),
                properties.getProperty("db.user"),
                properties.getProperty("db.password")
        );
    }
}