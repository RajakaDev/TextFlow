package lk.textflow.sales.dao;

import lk.textflow.config.DatabaseConnection;
import lk.textflow.sales.model.Sale;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SaleDAO {

    public int createSale(Sale sale) {

        String sql = "INSERT INTO sales " +
                "(customer_id, user_id, total_amount, amount_given, balance, " +
                "payment_method, payment_status, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            if (sale.getCustomerId() == null) {
                statement.setNull(1, Types.INTEGER);
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

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public Sale getSaleById(int saleId) {

        String sql = "SELECT * FROM sales WHERE sale_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, saleId);

            try (ResultSet rs = statement.executeQuery()) {

                if (rs.next()) {

                    Sale sale = new Sale();

                    sale.setSaleId(rs.getInt("sale_id"));

                    int customerId = rs.getInt("customer_id");

                    if (rs.wasNull()) {
                        sale.setCustomerId(null);
                    } else {
                        sale.setCustomerId(customerId);
                    }

                    sale.setUserId(rs.getInt("user_id"));

                    Timestamp saleDate =
                            rs.getTimestamp("sale_date");

                    if (saleDate != null) {
                        sale.setSaleDate(
                                saleDate.toLocalDateTime()
                        );
                    }

                    sale.setTotalAmount(
                            rs.getBigDecimal("total_amount")
                    );

                    sale.setAmountGiven(
                            rs.getBigDecimal("amount_given")
                    );

                    sale.setBalance(
                            rs.getBigDecimal("balance")
                    );

                    sale.setPaymentMethod(
                            rs.getString("payment_method")
                    );

                    sale.setPaymentStatus(
                            rs.getString("payment_status")
                    );

                    sale.setStatus(
                            rs.getString("status")
                    );

                    return sale;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean updateSale(Sale sale) {

        String sql = "UPDATE sales SET " +
                "customer_id = ?, " +
                "total_amount = ?, " +
                "amount_given = ?, " +
                "balance = ?, " +
                "payment_method = ?, " +
                "payment_status = ? " +
                "WHERE sale_id = ? AND status = 'PENDING'";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            if (sale.getCustomerId() == null) {
                statement.setNull(1, Types.INTEGER);
            } else {
                statement.setInt(1, sale.getCustomerId());
            }

            statement.setBigDecimal(
                    2,
                    sale.getTotalAmount()
            );

            statement.setBigDecimal(
                    3,
                    sale.getAmountGiven()
            );

            statement.setBigDecimal(
                    4,
                    sale.getBalance()
            );

            statement.setString(
                    5,
                    sale.getPaymentMethod()
            );

            statement.setString(
                    6,
                    sale.getPaymentStatus()
            );

            statement.setInt(
                    7,
                    sale.getSaleId()
            );

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public int createSale(
            Sale sale,
            Connection connection
    ) throws SQLException {

        String sql = """
                INSERT INTO sales
                (customer_id, user_id, total_amount, amount_given, balance,
                 payment_method, payment_status, status)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (PreparedStatement statement =
                     connection.prepareStatement(
                             sql,
                             Statement.RETURN_GENERATED_KEYS
                     )) {

            if (sale.getCustomerId() == null) {
                statement.setNull(
                        1,
                        Types.INTEGER
                );
            } else {
                statement.setInt(
                        1,
                        sale.getCustomerId()
                );
            }

            statement.setInt(
                    2,
                    sale.getUserId()
            );

            statement.setBigDecimal(
                    3,
                    sale.getTotalAmount()
            );

            statement.setBigDecimal(
                    4,
                    sale.getAmountGiven()
            );

            statement.setBigDecimal(
                    5,
                    sale.getBalance()
            );

            statement.setString(
                    6,
                    sale.getPaymentMethod()
            );

            statement.setString(
                    7,
                    sale.getPaymentStatus()
            );

            statement.setString(
                    8,
                    sale.getStatus()
            );

            statement.executeUpdate();

            try (ResultSet keys =
                         statement.getGeneratedKeys()) {

                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
        }

        return -1;
    }

    public boolean cancelSale(int saleId) {

        String sql =
                "UPDATE sales SET status = 'CANCELLED' " +
                        "WHERE sale_id = ? AND status = 'PENDING'";

        try (Connection connection =
                     DatabaseConnection.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(
                    1,
                    saleId
            );

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Sale> getAllSales() {

        List<Sale> sales =
                new ArrayList<>();

        String sql =
                "SELECT * FROM sales ORDER BY sale_date DESC";

        try (Connection connection =
                     DatabaseConnection.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(sql);

             ResultSet resultSet =
                     statement.executeQuery()) {

            while (resultSet.next()) {

                Sale sale =
                        new Sale();

                sale.setSaleId(
                        resultSet.getInt("sale_id")
                );

                int customerId =
                        resultSet.getInt("customer_id");

                if (resultSet.wasNull()) {
                    sale.setCustomerId(null);
                } else {
                    sale.setCustomerId(customerId);
                }

                sale.setUserId(
                        resultSet.getInt("user_id")
                );

                Timestamp saleDate =
                        resultSet.getTimestamp("sale_date");

                if (saleDate != null) {
                    sale.setSaleDate(
                            saleDate.toLocalDateTime()
                    );
                }

                sale.setTotalAmount(
                        resultSet.getBigDecimal("total_amount")
                );

                sale.setAmountGiven(
                        resultSet.getBigDecimal("amount_given")
                );

                sale.setBalance(
                        resultSet.getBigDecimal("balance")
                );

                sale.setPaymentMethod(
                        resultSet.getString("payment_method")
                );

                sale.setPaymentStatus(
                        resultSet.getString("payment_status")
                );

                sale.setStatus(
                        resultSet.getString("status")
                );

                sales.add(sale);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sales;
    }
}