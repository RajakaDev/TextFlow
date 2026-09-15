package lk.textflow.dao;

import lk.textflow.config.DatabaseConnection;
import lk.textflow.model.InventoryAdjustment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class InventoryAdjustmentDAO {
    public java.util.List<InventoryAdjustment> getAllAdjustments() {

        java.util.List<InventoryAdjustment> adjustments =
                new java.util.ArrayList<>();

        String sql = """
            SELECT adjustment_id,
                   product_id,
                   user_id,
                   quantity_change,
                   reason,
                   adjustment_date
            FROM inventory_adjustments
            ORDER BY adjustment_date DESC
            """;

        try (Connection connection =
                     DatabaseConnection.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(sql);

             java.sql.ResultSet resultSet =
                     statement.executeQuery()) {

            while (resultSet.next()) {

                InventoryAdjustment adjustment =
                        new InventoryAdjustment();

                adjustment.setAdjustmentId(
                        resultSet.getInt("adjustment_id"));

                adjustment.setProductId(
                        resultSet.getInt("product_id"));

                adjustment.setUserId(
                        resultSet.getInt("user_id"));

                adjustment.setQuantityChange(
                        resultSet.getInt("quantity_change"));

                adjustment.setReason(
                        resultSet.getString("reason"));

                java.sql.Timestamp timestamp =
                        resultSet.getTimestamp("adjustment_date");

                if (timestamp != null) {
                    adjustment.setAdjustmentDate(
                            timestamp.toLocalDateTime());
                }

                adjustments.add(adjustment);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return adjustments;
    }

    public boolean adjustStock(
            InventoryAdjustment adjustment) {

        Connection connection = null;

        try {

            connection =
                    DatabaseConnection.getConnection();

            connection.setAutoCommit(false);

            String stockSql = """
                    UPDATE products
                    SET stock_quantity =
                        stock_quantity + ?
                    WHERE product_id = ?
                    AND stock_quantity + ? >= 0
                    """;

            try (PreparedStatement stockStatement =
                         connection.prepareStatement(stockSql)) {

                stockStatement.setInt(
                        1,
                        adjustment.getQuantityChange());

                stockStatement.setInt(
                        2,
                        adjustment.getProductId());

                stockStatement.setInt(
                        3,
                        adjustment.getQuantityChange());

                int updated =
                        stockStatement.executeUpdate();

                if (updated == 0) {

                    connection.rollback();
                    return false;
                }
            }

            String adjustmentSql = """
                    INSERT INTO inventory_adjustments
                    (product_id,
                     user_id,
                     quantity_change,
                     reason)
                    VALUES (?, ?, ?, ?)
                    """;

            try (PreparedStatement statement =
                         connection.prepareStatement(
                                 adjustmentSql)) {

                statement.setInt(
                        1,
                        adjustment.getProductId());

                statement.setInt(
                        2,
                        adjustment.getUserId());

                statement.setInt(
                        3,
                        adjustment.getQuantityChange());

                statement.setString(
                        4,
                        adjustment.getReason());

                statement.executeUpdate();
            }

            connection.commit();

            return true;

        } catch (SQLException e) {

            if (connection != null) {

                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

            e.printStackTrace();
            return false;

        } finally {

            if (connection != null) {

                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}