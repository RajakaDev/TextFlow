package lk.textflow.sales.dao;

import lk.textflow.config.DatabaseConnection;
import lk.textflow.sales.model.SalesReturn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.math.BigDecimal;

public class SalesReturnDAO {

    public boolean processReturn(SalesReturn salesReturn) {

        String saleSql =
                "SELECT status FROM sales WHERE sale_id = ?";

        String itemSql =
                "SELECT quantity, unit_price " +
                        "FROM sale_items " +
                        "WHERE sale_id = ? AND product_id = ?";

        String returnedSql =
                "SELECT COALESCE(SUM(quantity), 0) " +
                        "FROM sales_returns " +
                        "WHERE sale_id = ? AND product_id = ?";

        String insertSql =
                "INSERT INTO sales_returns " +
                        "(sale_id, product_id, quantity, return_amount) " +
                        "VALUES (?, ?, ?, ?)";

        String stockSql =
                "UPDATE products " +
                        "SET stock_quantity = stock_quantity + ? " +
                        "WHERE product_id = ?";

        try (Connection connection = DatabaseConnection.getConnection()) {

            connection.setAutoCommit(false);

            try {
                // Check sale status
                try (PreparedStatement statement =
                             connection.prepareStatement(saleSql)) {

                    statement.setInt(1, salesReturn.getSaleId());

                    try (ResultSet rs = statement.executeQuery()) {

                        if (!rs.next()) {
                            throw new SQLException("Sale not found.");
                        }

                        if (!"CONFIRMED".equalsIgnoreCase(
                                rs.getString("status"))) {
                            throw new SQLException(
                                    "Only confirmed sales can be returned.");
                        }
                    }
                }

                int soldQuantity;
                BigDecimal unitPrice;

                // Get original sold quantity and price
                try (PreparedStatement statement =
                             connection.prepareStatement(itemSql)) {

                    statement.setInt(1, salesReturn.getSaleId());
                    statement.setInt(2, salesReturn.getProductId());

                    try (ResultSet rs = statement.executeQuery()) {

                        if (!rs.next()) {
                            throw new SQLException(
                                    "Product was not found in this sale.");
                        }

                        soldQuantity = rs.getInt("quantity");
                        unitPrice = rs.getBigDecimal("unit_price");
                    }
                }

                int alreadyReturned = 0;

                // Check previous returns
                try (PreparedStatement statement =
                             connection.prepareStatement(returnedSql)) {

                    statement.setInt(1, salesReturn.getSaleId());
                    statement.setInt(2, salesReturn.getProductId());

                    try (ResultSet rs = statement.executeQuery()) {
                        if (rs.next()) {
                            alreadyReturned = rs.getInt(1);
                        }
                    }
                }

                if (salesReturn.getQuantity() <= 0) {
                    throw new SQLException(
                            "Return quantity must be greater than 0.");
                }

                if (alreadyReturned + salesReturn.getQuantity()
                        > soldQuantity) {

                    throw new SQLException(
                            "Return quantity exceeds the sold quantity.");
                }

                BigDecimal returnAmount =
                        unitPrice.multiply(
                                BigDecimal.valueOf(
                                        salesReturn.getQuantity()));

                salesReturn.setReturnAmount(returnAmount);

                // Save return
                try (PreparedStatement statement =
                             connection.prepareStatement(insertSql)) {

                    statement.setInt(1, salesReturn.getSaleId());
                    statement.setInt(2, salesReturn.getProductId());
                    statement.setInt(3, salesReturn.getQuantity());
                    statement.setBigDecimal(4, returnAmount);

                    statement.executeUpdate();
                }

                // Add returned quantity back to stock
                try (PreparedStatement statement =
                             connection.prepareStatement(stockSql)) {

                    statement.setInt(1, salesReturn.getQuantity());
                    statement.setInt(2, salesReturn.getProductId());

                    if (statement.executeUpdate() == 0) {
                        throw new SQLException(
                                "Product stock could not be updated.");
                    }
                }

                connection.commit();
                return true;

            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}