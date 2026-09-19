package lk.textflow.sales.dao;


import lk.textflow.config.DatabaseConnection;
import lk.textflow.sales.model.Sale;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class SaleDAO {

    public int createSale(Sale sale) throws SQLException {

        String sql = """
                INSERT INTO sales
                (customer_id, user_id, total_amount, amount_given, balance,
                 payment_method, payment_status, status)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            if (sale.getCustomerId() == null) {
                statement.setNull(1, java.sql.Types.INTEGER);
            } else {
                statement.setInt(1, sale.getCustomerId());
            }

            statement.setInt(2, sale.getUserId());
            statement.setBigDecimal(3, sale.getTotalAmount());
            statement.setBigDecimal(4, sale.getAmountGiven());
            statement.setBigDecimal(5, sale.getBalance());
            statement.setString(6, sale.getPaymentMethod());
            statement.setString(7, sale.getPaymentStatus());
            statement.setString(8, sale.getStatus());

            statement.executeUpdate();

            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
        }

        return -1;
    }

    public Sale getSaleById(int saleId) throws SQLException {

        String sql = "SELECT * FROM sales WHERE sale_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, saleId);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {

                    Sale sale = new Sale();

                    sale.setSaleId(resultSet.getInt("sale_id"));

                    int customerId = resultSet.getInt("customer_id");
                    if (resultSet.wasNull()) {
                        sale.setCustomerId(null);
                    } else {
                        sale.setCustomerId(customerId);
                    }

                    sale.setUserId(resultSet.getInt("user_id"));

                    if (resultSet.getTimestamp("sale_date") != null) {
                        sale.setSaleDate(
                                resultSet.getTimestamp("sale_date").toLocalDateTime()
                        );
                    }

                    sale.setTotalAmount(resultSet.getBigDecimal("total_amount"));
                    sale.setAmountGiven(resultSet.getBigDecimal("amount_given"));
                    sale.setBalance(resultSet.getBigDecimal("balance"));
                    sale.setPaymentMethod(resultSet.getString("payment_method"));
                    sale.setPaymentStatus(resultSet.getString("payment_status"));
                    sale.setStatus(resultSet.getString("status"));

                    return sale;
                }
            }
        }

        return null;
    }

    public boolean updateSale(Sale sale) throws SQLException {

        String sql = """
            UPDATE sales
            SET customer_id = ?,
                total_amount = ?,
                amount_given = ?,
                balance = ?,
                payment_method = ?,
                payment_status = ?
            WHERE sale_id = ?
              AND status = 'PENDING'
            """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            if (sale.getCustomerId() == null) {
                statement.setNull(1, java.sql.Types.INTEGER);
            } else {
                statement.setInt(1, sale.getCustomerId());
            }

            statement.setBigDecimal(2, sale.getTotalAmount());
            statement.setBigDecimal(3, sale.getAmountGiven());
            statement.setBigDecimal(4, sale.getBalance());
            statement.setString(5, sale.getPaymentMethod());
            statement.setString(6, sale.getPaymentStatus());
            statement.setInt(7, sale.getSaleId());

            return statement.executeUpdate() > 0;
        }
    }
    public java.util.List<Sale> getAllSales() {

        java.util.List<Sale> sales = new java.util.ArrayList<>();

        String sql = "SELECT * FROM sales ORDER BY sale_date DESC";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Sale sale = new Sale();

                sale.setSaleId(resultSet.getInt("sale_id"));

                int customerId = resultSet.getInt("customer_id");
                if (resultSet.wasNull()) {
                    sale.setCustomerId(null);
                } else {
                    sale.setCustomerId(customerId);
                }

                sale.setUserId(resultSet.getInt("user_id"));

                if (resultSet.getTimestamp("sale_date") != null) {
                    sale.setSaleDate(
                            resultSet.getTimestamp("sale_date").toLocalDateTime()
                    );
                }

                sale.setTotalAmount(resultSet.getBigDecimal("total_amount"));
                sale.setAmountGiven(resultSet.getBigDecimal("amount_given"));
                sale.setBalance(resultSet.getBigDecimal("balance"));
                sale.setPaymentMethod(resultSet.getString("payment_method"));
                sale.setPaymentStatus(resultSet.getString("payment_status"));
                sale.setStatus(resultSet.getString("status"));

                sales.add(sale);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sales;
    }
}

