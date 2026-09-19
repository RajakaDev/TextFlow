package lk.textflow.dao;

import lk.textflow.config.DatabaseConnection;
import lk.textflow.model.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {

    public boolean addCategory(Category category) {

        String sql = """
                INSERT INTO categories
                (category_name, description, status)
                VALUES (?, ?, ?)
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(
                    1,
                    category.getCategoryName()
            );

            statement.setString(
                    2,
                    category.getDescription()
            );

            statement.setString(
                    3,
                    category.getStatus()
            );

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Category> getAllCategories() {

        List<Category> categories =
                new ArrayList<>();

        String sql = """
                SELECT category_id,
                       category_name,
                       description,
                       status
                FROM categories
                ORDER BY category_name
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(sql);

             ResultSet resultSet =
                     statement.executeQuery()) {

            while (resultSet.next()) {

                Category category =
                        new Category();

                category.setCategoryId(
                        resultSet.getInt("category_id")
                );

                category.setCategoryName(
                        resultSet.getString("category_name")
                );

                category.setDescription(
                        resultSet.getString("description")
                );

                category.setStatus(
                        resultSet.getString("status")
                );

                categories.add(category);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories;
    }

    public boolean updateCategory(Category category) {

        String sql = """
                UPDATE categories
                SET category_name = ?,
                    description = ?,
                    status = ?
                WHERE category_id = ?
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(
                    1,
                    category.getCategoryName()
            );

            statement.setString(
                    2,
                    category.getDescription()
            );

            statement.setString(
                    3,
                    category.getStatus()
            );

            statement.setInt(
                    4,
                    category.getCategoryId()
            );

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteCategory(int categoryId) {

        String sql = """
                DELETE FROM categories
                WHERE category_id = ?
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, categoryId);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}