package com.textflow.dao;

import com.textflow.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExpenseCategoryDAO {

    // ADD CATEGORY
    public boolean addCategory(String categoryName, String description) {

        String sql = """
                INSERT INTO expense_categories
                (category_name, description, status)
                VALUES (?, ?, 'ACTIVE')
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, categoryName);
            statement.setString(2, description);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // GET ACTIVE CATEGORIES
    public List<String[]> getCategories() {

        List<String[]> categories = new ArrayList<>();

        String sql = """
                SELECT expense_category_id,
                       category_name,
                       description,
                       status
                FROM expense_categories
                WHERE status = 'ACTIVE'
                ORDER BY expense_category_id
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                String[] category = {
                        String.valueOf(
                                resultSet.getInt("expense_category_id")
                        ),
                        resultSet.getString("category_name"),
                        resultSet.getString("description"),
                        resultSet.getString("status")
                };

                categories.add(category);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories;
    }

    // DELETE / DEACTIVATE CATEGORY
    public boolean deleteCategory(int categoryId) {

        String sql = """
                UPDATE expense_categories
                SET status = 'INACTIVE'
                WHERE expense_category_id = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, categoryId);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}