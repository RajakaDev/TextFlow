package lk.textflow;

import lk.textflow.config.DatabaseConnection;
import java.sql.Connection;

public class Main {

    public static void main(String[] args) {

        try (Connection connection =
                     DatabaseConnection.getConnection()) {

            System.out.println(
                    "TextFlow database connected successfully!"
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}