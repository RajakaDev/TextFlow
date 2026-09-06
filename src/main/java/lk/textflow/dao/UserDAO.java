package lk.textflow.dao;

import lk.textflow.config.DatabaseConnection;
import lk.textflow.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class UserDAO {

    public boolean addUser(User user) {

        String sql = """
                INSERT INTO users
                (name, username, password_hash, role, position, contact_number, status)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;
        try(
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPasswordHash());
            statement.setString(4, user.getRole());
            statement.setString(5, user.getPosition());
            statement.setString(6, user.getContactNumber());
            statement.setString(7, user.getStatus());

            int rowsAffected = statement.executeUpdate();

            return rowsAffected > 0;

          } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<User> getAllUsers() {

        List<User> users = new ArrayList<>();

        String sql = "SELECT * FROM users";

        try(
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {

            while(resultSet.next()) {

                User user = new User();

                user.setUserId(resultSet.getInt("user_id"));
                user.setName(resultSet.getString("name"));
                user.setUsername(resultSet.getString("username"));
                user.setPasswordHash(resultSet.getString("password_hash"));
                user.setRole(resultSet.getString("role"));
                user.setPosition(resultSet.getString("position"));
                user.setContactNumber(resultSet.getString("contact_number"));
                user.setStatus(resultSet.getString("status"));

                user.setCreatedAt(
                        resultSet.getTimestamp("created_at").toLocalDateTime()
                );
                users.add(user);
            }
        }  catch (SQLException e) {
            e.printStackTrace();
    }
        return users;
}
}
