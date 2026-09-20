package com.textflow.rahman.dao;

import com.textflow.rahman.model.Customer;
import lk.textflow.config.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    // ==================================================
    // ADD CUSTOMER
    // ==================================================

    public boolean addCustomer(Customer customer) {

        String sql = """
                INSERT INTO customers
                (
                    customer_name,
                    contact_number,
                    address,
                    loyalty_points,
                    status
                )
                VALUES (?, ?, ?, ?, ?)
                """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(
                                sql,
                                Statement.RETURN_GENERATED_KEYS
                        )
        ) {

            statement.setString(
                    1,
                    customer.getCustomerName()
            );

            statement.setString(
                    2,
                    customer.getContactNumber()
            );

            statement.setString(
                    3,
                    customer.getAddress()
            );

            statement.setInt(
                    4,
                    customer.getLoyaltyPoints()
            );

            statement.setString(
                    5,
                    customer.getStatus()
            );

            int rows =
                    statement.executeUpdate();

            if (rows > 0) {

                try (
                        ResultSet generatedKeys =
                                statement.getGeneratedKeys()
                ) {

                    if (generatedKeys.next()) {

                        customer.setCustomerId(
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

    // ==================================================
    // GET ALL CUSTOMERS
    // ==================================================

    public List<Customer> getAllCustomers() {

        List<Customer> customers =
                new ArrayList<>();

        String sql = """
                SELECT
                    customer_id,
                    customer_name,
                    contact_number,
                    address,
                    loyalty_points,
                    status
                FROM customers
                ORDER BY customer_id DESC
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

                customers.add(
                        mapCustomer(
                                resultSet
                        )
                );
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return customers;
    }

    // ==================================================
    // UPDATE CUSTOMER
    // ==================================================

    public boolean updateCustomer(
            Customer customer
    ) {

        String sql = """
                UPDATE customers
                SET customer_name = ?,
                    contact_number = ?,
                    address = ?,
                    loyalty_points = ?,
                    status = ?
                WHERE customer_id = ?
                """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    customer.getCustomerName()
            );

            statement.setString(
                    2,
                    customer.getContactNumber()
            );

            statement.setString(
                    3,
                    customer.getAddress()
            );

            statement.setInt(
                    4,
                    customer.getLoyaltyPoints()
            );

            statement.setString(
                    5,
                    customer.getStatus()
            );

            statement.setInt(
                    6,
                    customer.getCustomerId()
            );

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return false;
    }

    // ==================================================
    // DEACTIVATE CUSTOMER
    // ==================================================

    public boolean deactivateCustomer(
            int customerId
    ) {

        String sql = """
                UPDATE customers
                SET status = 'INACTIVE'
                WHERE customer_id = ?
                """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    customerId
            );

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return false;
    }

    // ==================================================
    // DELETE CUSTOMER
    // ==================================================

    public boolean deleteCustomer(
            int customerId
    ) {

        String sql = """
                DELETE FROM customers
                WHERE customer_id = ?
                """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    customerId
            );

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            /*
             * For example:
             * a customer already used in a sale cannot
             * normally be deleted because of the FK.
             */
            e.printStackTrace();
        }

        return false;
    }

    // ==================================================
    // SEARCH
    // ==================================================

    public List<Customer> searchCustomers(
            String keyword
    ) {

        List<Customer> customers =
                new ArrayList<>();

        String sql = """
                SELECT
                    customer_id,
                    customer_name,
                    contact_number,
                    address,
                    loyalty_points,
                    status
                FROM customers
                WHERE CAST(customer_id AS CHAR) LIKE ?
                   OR customer_name LIKE ?
                   OR contact_number LIKE ?
                   OR address LIKE ?
                   OR status LIKE ?
                ORDER BY customer_id DESC
                """;

        String search =
                "%"
                        + keyword
                        + "%";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(1, search);
            statement.setString(2, search);
            statement.setString(3, search);
            statement.setString(4, search);
            statement.setString(5, search);

            try (
                    ResultSet resultSet =
                            statement.executeQuery()
            ) {

                while (resultSet.next()) {

                    customers.add(
                            mapCustomer(
                                    resultSet
                            )
                    );
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return customers;
    }

    // ==================================================
    // FIND BY ID
    // ==================================================

    public Customer getCustomerById(
            int customerId
    ) {

        String sql = """
                SELECT
                    customer_id,
                    customer_name,
                    contact_number,
                    address,
                    loyalty_points,
                    status
                FROM customers
                WHERE customer_id = ?
                """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    customerId
            );

            try (
                    ResultSet resultSet =
                            statement.executeQuery()
            ) {

                if (resultSet.next()) {

                    return mapCustomer(
                            resultSet
                    );
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return null;
    }

    // ==================================================
    // MAP DATABASE ROW
    // ==================================================

    private Customer mapCustomer(
            ResultSet resultSet
    ) throws SQLException {

        Customer customer =
                new Customer();

        customer.setCustomerId(
                resultSet.getInt(
                        "customer_id"
                )
        );

        customer.setCustomerName(
                resultSet.getString(
                        "customer_name"
                )
        );

        customer.setContactNumber(
                resultSet.getString(
                        "contact_number"
                )
        );

        customer.setAddress(
                resultSet.getString(
                        "address"
                )
        );

        customer.setLoyaltyPoints(
                resultSet.getInt(
                        "loyalty_points"
                )
        );

        customer.setStatus(
                resultSet.getString(
                        "status"
                )
        );

        return customer;
    }
}