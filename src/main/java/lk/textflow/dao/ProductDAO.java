package lk.textflow.dao;

import lk.textflow.config.DatabaseConnection;
import lk.textflow.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    // =========================================================
    // ADD PRODUCT
    // Initial stock is allowed when creating a new product.
    // =========================================================
    public boolean addProduct(Product product) {

        String sql = """
                INSERT INTO products
                (
                    category_id,
                    product_name,
                    barcode,
                    unit_price,
                    cost_price,
                    stock_quantity,
                    reorder_level,
                    status
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(
                             sql,
                             Statement.RETURN_GENERATED_KEYS
                     )) {

            statement.setInt(
                    1,
                    product.getCategoryId()
            );

            statement.setString(
                    2,
                    product.getProductName()
            );

            statement.setString(
                    3,
                    product.getBarcode()
            );

            statement.setBigDecimal(
                    4,
                    product.getUnitPrice()
            );

            statement.setBigDecimal(
                    5,
                    product.getCostPrice()
            );

            // Initial stock is allowed here
            statement.setInt(
                    6,
                    product.getStockQuantity()
            );

            statement.setInt(
                    7,
                    product.getReorderLevel()
            );

            statement.setString(
                    8,
                    product.getStatus()
            );

            int rowsAffected =
                    statement.executeUpdate();

            if (rowsAffected > 0) {

                try (ResultSet generatedKeys =
                             statement.getGeneratedKeys()) {

                    if (generatedKeys.next()) {

                        product.setProductId(
                                generatedKeys.getInt(1)
                        );
                    }
                }

                return true;
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return false;
    }


    // =========================================================
    // GET ALL PRODUCTS
    // =========================================================
    public List<Product> getAllProducts() {

        List<Product> products =
                new ArrayList<>();

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

                Product product =
                        mapProduct(resultSet);

                products.add(product);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return products;
    }


    // =========================================================
    // UPDATE PRODUCT
    //
    // IMPORTANT:
    // stock_quantity is NOT updated here.
    //
    // Existing stock must only be changed through
    // InventoryAdjustmentDAO.
    // =========================================================
    public boolean updateProduct(Product product) {

        String sql = """
                UPDATE products
                SET category_id = ?,
                    product_name = ?,
                    barcode = ?,
                    unit_price = ?,
                    cost_price = ?,
                    reorder_level = ?,
                    status = ?
                WHERE product_id = ?
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(
                    1,
                    product.getCategoryId()
            );

            statement.setString(
                    2,
                    product.getProductName()
            );

            statement.setString(
                    3,
                    product.getBarcode()
            );

            statement.setBigDecimal(
                    4,
                    product.getUnitPrice()
            );

            statement.setBigDecimal(
                    5,
                    product.getCostPrice()
            );

            statement.setInt(
                    6,
                    product.getReorderLevel()
            );

            statement.setString(
                    7,
                    product.getStatus()
            );

            statement.setInt(
                    8,
                    product.getProductId()
            );

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
    }


    // =========================================================
    // FIND PRODUCT BY BARCODE
    // =========================================================
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

            statement.setString(
                    1,
                    barcode
            );

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                if (resultSet.next()) {

                    return mapProduct(
                            resultSet
                    );
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return null;
    }


    // =========================================================
    // GET LOW STOCK PRODUCTS
    // =========================================================
    public List<Product> getLowStockProducts() {

        List<Product> products =
                new ArrayList<>();

        String sql = """
                SELECT *
                FROM products
                WHERE stock_quantity <= reorder_level
                AND status = 'ACTIVE'
                ORDER BY stock_quantity ASC
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(sql);

             ResultSet resultSet =
                     statement.executeQuery()) {

            while (resultSet.next()) {

                Product product =
                        mapProduct(resultSet);

                products.add(product);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return products;
    }


    // =========================================================
    // HELPER METHOD
    //
    // Converts one database row into a Product object.
    // This avoids repeating the same code everywhere.
    // =========================================================
    private Product mapProduct(
            ResultSet resultSet)
            throws SQLException {

        Product product =
                new Product();

        product.setProductId(
                resultSet.getInt(
                        "product_id"
                )
        );

        product.setCategoryId(
                resultSet.getInt(
                        "category_id"
                )
        );

        product.setProductName(
                resultSet.getString(
                        "product_name"
                )
        );

        product.setBarcode(
                resultSet.getString(
                        "barcode"
                )
        );

        product.setUnitPrice(
                resultSet.getBigDecimal(
                        "unit_price"
                )
        );

        product.setCostPrice(
                resultSet.getBigDecimal(
                        "cost_price"
                )
        );

        product.setStockQuantity(
                resultSet.getInt(
                        "stock_quantity"
                )
        );

        product.setReorderLevel(
                resultSet.getInt(
                        "reorder_level"
                )
        );

        product.setStatus(
                resultSet.getString(
                        "status"
                )
        );

        return product;
    }
}