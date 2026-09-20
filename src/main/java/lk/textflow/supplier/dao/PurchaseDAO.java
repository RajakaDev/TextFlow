package lk.textflow.supplier.dao;

import lk.textflow.config.DatabaseConnection;
import lk.textflow.supplier.model.Purchase;
import lk.textflow.supplier.model.PurchaseItem;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PurchaseDAO {

    // ==================================================
    // CREATE PURCHASE
    // ==================================================

    public boolean createPurchase(
            Purchase purchase,
            List<PurchaseItem> items
    ) {

        if (items == null || items.isEmpty()) {
            return false;
        }

        Connection connection = null;

        try {

            connection =
                    DatabaseConnection.getConnection();

            connection.setAutoCommit(false);

            String purchaseSql = """
                    INSERT INTO purchases
                    (
                        supplier_id,
                        user_id,
                        total_amount,
                        status
                    )
                    VALUES (?, ?, ?, 'PENDING')
                    """;

            int purchaseId;

            try (
                    PreparedStatement statement =
                            connection.prepareStatement(
                                    purchaseSql,
                                    Statement.RETURN_GENERATED_KEYS
                            )
            ) {

                statement.setInt(
                        1,
                        purchase.getSupplierId()
                );

                statement.setInt(
                        2,
                        purchase.getUserId()
                );

                statement.setBigDecimal(
                        3,
                        purchase.getTotalAmount()
                );

                statement.executeUpdate();

                try (
                        ResultSet keys =
                                statement.getGeneratedKeys()
                ) {

                    if (!keys.next()) {

                        connection.rollback();
                        return false;
                    }

                    purchaseId =
                            keys.getInt(1);
                }
            }

            String itemSql = """
                    INSERT INTO purchase_items
                    (
                        purchase_id,
                        product_id,
                        quantity,
                        unit_cost
                    )
                    VALUES (?, ?, ?, ?)
                    """;

            try (
                    PreparedStatement statement =
                            connection.prepareStatement(
                                    itemSql
                            )
            ) {

                for (
                        PurchaseItem item
                        : items
                ) {

                    statement.setInt(
                            1,
                            purchaseId
                    );

                    statement.setInt(
                            2,
                            item.getProductId()
                    );

                    statement.setInt(
                            3,
                            item.getQuantity()
                    );

                    statement.setBigDecimal(
                            4,
                            item.getUnitCost()
                    );

                    statement.addBatch();
                }

                statement.executeBatch();
            }

            connection.commit();

            return true;

        } catch (SQLException e) {

            e.printStackTrace();

            if (connection != null) {

                try {
                    connection.rollback();
                } catch (SQLException ignored) {
                }
            }

            return false;

        } finally {

            if (connection != null) {

                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException ignored) {
                }
            }
        }
    }

    // ==================================================
    // PURCHASE HISTORY
    // ==================================================

    public List<String[]> getAllPurchases() {

        List<String[]> purchases =
                new ArrayList<>();

        String sql = """
                SELECT
                    p.purchase_id,
                    s.supplier_name,
                    p.user_id,
                    p.purchase_date,
                    p.total_amount,
                    p.status
                FROM purchases p
                INNER JOIN suppliers s
                    ON p.supplier_id = s.supplier_id
                ORDER BY p.purchase_id DESC
                """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql);

                ResultSet resultSet =
                        statement.executeQuery()
        ) {

            while (resultSet.next()) {

                String[] purchase = {

                        String.valueOf(
                                resultSet.getInt(
                                        "purchase_id"
                                )
                        ),

                        resultSet.getString(
                                "supplier_name"
                        ),

                        String.valueOf(
                                resultSet.getInt(
                                        "user_id"
                                )
                        ),

                        String.valueOf(
                                resultSet.getTimestamp(
                                        "purchase_date"
                                )
                        ),

                        resultSet
                                .getBigDecimal(
                                        "total_amount"
                                )
                                .toPlainString(),

                        resultSet.getString(
                                "status"
                        )
                };

                purchases.add(purchase);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return purchases;
    }

    // ==================================================
    // GET PRODUCTS
    // ==================================================

    public List<String[]> getActiveProducts() {

        List<String[]> products =
                new ArrayList<>();

        String sql = """
                SELECT
                    product_id,
                    product_name,
                    stock_quantity,
                    cost_price
                FROM products
                WHERE status = 'ACTIVE'
                ORDER BY product_name
                """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql);

                ResultSet resultSet =
                        statement.executeQuery()
        ) {

            while (resultSet.next()) {

                String[] product = {

                        String.valueOf(
                                resultSet.getInt(
                                        "product_id"
                                )
                        ),

                        resultSet.getString(
                                "product_name"
                        ),

                        String.valueOf(
                                resultSet.getInt(
                                        "stock_quantity"
                                )
                        ),

                        resultSet
                                .getBigDecimal(
                                        "cost_price"
                                )
                                .toPlainString()
                };

                products.add(product);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    // ==================================================
    // CONFIRM PURCHASE
    // ==================================================

    public boolean confirmPurchase(
            int purchaseId
    ) {

        Connection connection = null;

        try {

            connection =
                    DatabaseConnection.getConnection();

            connection.setAutoCommit(false);

            String statusSql = """
                    SELECT status
                    FROM purchases
                    WHERE purchase_id = ?
                    FOR UPDATE
                    """;

            String currentStatus;

            try (
                    PreparedStatement statement =
                            connection.prepareStatement(
                                    statusSql
                            )
            ) {

                statement.setInt(
                        1,
                        purchaseId
                );

                try (
                        ResultSet resultSet =
                                statement.executeQuery()
                ) {

                    if (!resultSet.next()) {

                        connection.rollback();
                        return false;
                    }

                    currentStatus =
                            resultSet.getString(
                                    "status"
                            );
                }
            }

            if (
                    !"PENDING".equalsIgnoreCase(
                            currentStatus
                    )
            ) {

                connection.rollback();
                return false;
            }

            String itemSql = """
                    SELECT
                        product_id,
                        quantity
                    FROM purchase_items
                    WHERE purchase_id = ?
                    """;

            String stockSql = """
                    UPDATE products
                    SET stock_quantity =
                        stock_quantity + ?
                    WHERE product_id = ?
                    """;

            try (
                    PreparedStatement itemStatement =
                            connection.prepareStatement(
                                    itemSql
                            );

                    PreparedStatement stockStatement =
                            connection.prepareStatement(
                                    stockSql
                            )
            ) {

                itemStatement.setInt(
                        1,
                        purchaseId
                );

                try (
                        ResultSet resultSet =
                                itemStatement.executeQuery()
                ) {

                    while (resultSet.next()) {

                        int productId =
                                resultSet.getInt(
                                        "product_id"
                                );

                        int quantity =
                                resultSet.getInt(
                                        "quantity"
                                );

                        stockStatement.setInt(
                                1,
                                quantity
                        );

                        stockStatement.setInt(
                                2,
                                productId
                        );

                        stockStatement.addBatch();
                    }
                }

                stockStatement.executeBatch();
            }

            String updatePurchaseSql = """
                    UPDATE purchases
                    SET status = 'CONFIRMED'
                    WHERE purchase_id = ?
                      AND status = 'PENDING'
                    """;

            try (
                    PreparedStatement statement =
                            connection.prepareStatement(
                                    updatePurchaseSql
                            )
            ) {

                statement.setInt(
                        1,
                        purchaseId
                );

                if (
                        statement.executeUpdate()
                                == 0
                ) {

                    connection.rollback();
                    return false;
                }
            }

            connection.commit();

            return true;

        } catch (SQLException e) {

            e.printStackTrace();

            if (connection != null) {

                try {
                    connection.rollback();
                } catch (SQLException ignored) {
                }
            }

            return false;

        } finally {

            if (connection != null) {

                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException ignored) {
                }
            }
        }
    }

    // ==================================================
    // CANCEL PURCHASE
    // ==================================================

    public boolean cancelPurchase(
            int purchaseId
    ) {

        String sql = """
                UPDATE purchases
                SET status = 'CANCELLED'
                WHERE purchase_id = ?
                  AND status = 'PENDING'
                """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    purchaseId
            );

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        }
    }
}