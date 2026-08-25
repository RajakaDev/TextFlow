package lk.textflow;

import lk.textflow.config.DatabaseConnection;

import java.sql.Connection;

public class Main {

    public static void main(String[] args) {

        try (Connection connection =
                     DatabaseConnection.getConnection()) {

            if (connection != null &&
                    !connection.isClosed()) {

                System.out.println(
                        "TextFlow database connected successfully!"
                );
            }

        } catch (Exception e) {

            System.out.println(
                    "Database connection failed!"
            );

            e.printStackTrace();
        }
    }
}