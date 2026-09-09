package lk.textflow.dao;

import lk.textflow.config.DatabaseConnection;
import lk.textflow.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    public boolean addProduct(Product product) {

        String sql = """
                INSERT INTO products
                (category_id, product_name, barcode,
                 unit_price, cost_price, stock_quantity,
                 reorder_level, status)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, product.getCategoryId());
            statement.setString(2, product.getProductName());
            statement.setString(3, product.getBarcode());
            statement.setBigDecimal(4, product.getUnitPrice());
            statement.setBigDecimal(5, product.getCostPrice());
            statement.setInt(6, product.getStockQuantity());
            statement.setInt(7, product.getReorderLevel());
            statement.setString(8, product.getStatus());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Product> getAllProducts() {

        List<Product> products = new ArrayList<>();

        String sql = """
                SELECT *
                FROM products
                ORDER BY product_name
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(sql);

             ResultSet resultSet =
                     statement.executeQuery()) {

            while (resultSet.next()) {

                Product product = new Product();

                product.setProductId(
                        resultSet.getInt("product_id"));

                product.setCategoryId(
                        resultSet.getInt("category_id"));

                product.setProductName(
                        resultSet.getString("product_name"));

                product.setBarcode(
                        resultSet.getString("barcode"));

                product.setUnitPrice(
                        resultSet.getBigDecimal("unit_price"));

                product.setCostPrice(
                        resultSet.getBigDecimal("cost_price"));

                product.setStockQuantity(
                        resultSet.getInt("stock_quantity"));

                product.setReorderLevel(
                        resultSet.getInt("reorder_level"));

                product.setStatus(
                        resultSet.getString("status"));

                products.add(product);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    public boolean updateProduct(Product product) {

        String sql = """
                UPDATE products
                SET category_id = ?,
                    product_name = ?,
                    barcode = ?,
                    unit_price = ?,
                    cost_price = ?,
                    stock_quantity = ?,
                    reorder_level = ?,
                    status = ?
                WHERE product_id = ?
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, product.getCategoryId());
            statement.setString(2, product.getProductName());
            statement.setString(3, product.getBarcode());
            statement.setBigDecimal(4, product.getUnitPrice());
            statement.setBigDecimal(5, product.getCostPrice());
            statement.setInt(6, product.getStockQuantity());
            statement.setInt(7, product.getReorderLevel());
            statement.setString(8, product.getStatus());
            statement.setInt(9, product.getProductId());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Product findByBarcode(String barcode) {

        String sql = """
                SELECT *
                FROM products
                WHERE barcode = ?
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, barcode);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                if (resultSet.next()) {

                    Product product = new Product();

                    product.setProductId(
                            resultSet.getInt("product_id"));

                    product.setCategoryId(
                            resultSet.getInt("category_id"));

                    product.setProductName(
                            resultSet.getString("product_name"));

                    product.setBarcode(
                            resultSet.getString("barcode"));

                    product.setUnitPrice(
                            resultSet.getBigDecimal("unit_price"));

                    product.setCostPrice(
                            resultSet.getBigDecimal("cost_price"));

                    product.setStockQuantity(
                            resultSet.getInt("stock_quantity"));

                    product.setReorderLevel(
                            resultSet.getInt("reorder_level"));

                    product.setStatus(
                            resultSet.getString("status"));

                    return product;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Product> getLowStockProducts() {

        List<Product> products = new ArrayList<>();

        String sql = """
                SELECT *
                FROM products
                WHERE stock_quantity <= reorder_level
                ORDER BY stock_quantity ASC
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(sql);

             ResultSet resultSet =
                     statement.executeQuery()) {

            while (resultSet.next()) {

                Product product = new Product();

                product.setProductId(
                        resultSet.getInt("product_id"));

                product.setCategoryId(
                        resultSet.getInt("category_id"));

                product.setProductName(
                        resultSet.getString("product_name"));

                product.setBarcode(
                        resultSet.getString("barcode"));

                product.setUnitPrice(
                        resultSet.getBigDecimal("unit_price"));

                product.setCostPrice(
                        resultSet.getBigDecimal("cost_price"));

                product.setStockQuantity(
                        resultSet.getInt("stock_quantity"));

                product.setReorderLevel(
                        resultSet.getInt("reorder_level"));

                product.setStatus(
                        resultSet.getString("status"));

                products.add(product);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }
}