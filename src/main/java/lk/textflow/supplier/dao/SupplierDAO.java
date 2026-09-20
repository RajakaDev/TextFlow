package lk.textflow.supplier.dao;

import lk.textflow.config.DatabaseConnection;
import lk.textflow.supplier.model.Supplier;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAO {

    // ==================================================
    // ADD SUPPLIER
    // ==================================================

    public boolean addSupplier(Supplier supplier) {

        String sql = """
                INSERT INTO suppliers
                (
                    supplier_name,
                    contact_number,
                    address,
                    email,
                    status
                )
                VALUES (?, ?, ?, ?, ?)
                """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    supplier.getSupplierName()
            );

            statement.setString(
                    2,
                    supplier.getContactNumber()
            );

            statement.setString(
                    3,
                    supplier.getAddress()
            );

            statement.setString(
                    4,
                    supplier.getEmail()
            );

            statement.setString(
                    5,
                    supplier.getStatus()
            );

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        }
    }

    // ==================================================
    // GET ALL SUPPLIERS
    // ==================================================

    public List<Supplier> getAllSuppliers() {

        List<Supplier> suppliers =
                new ArrayList<>();

        String sql = """
                SELECT
                    supplier_id,
                    supplier_name,
                    contact_number,
                    address,
                    email,
                    status
                FROM suppliers
                ORDER BY supplier_id DESC
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

                Supplier supplier =
                        new Supplier();

                supplier.setSupplierId(
                        resultSet.getInt(
                                "supplier_id"
                        )
                );

                supplier.setSupplierName(
                        resultSet.getString(
                                "supplier_name"
                        )
                );

                supplier.setContactNumber(
                        resultSet.getString(
                                "contact_number"
                        )
                );

                supplier.setAddress(
                        resultSet.getString(
                                "address"
                        )
                );

                supplier.setEmail(
                        resultSet.getString(
                                "email"
                        )
                );

                supplier.setStatus(
                        resultSet.getString(
                                "status"
                        )
                );

                suppliers.add(supplier);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return suppliers;
    }

    // ==================================================
    // ACTIVE SUPPLIERS
    // ==================================================

    public List<Supplier> getActiveSuppliers() {

        List<Supplier> suppliers =
                new ArrayList<>();

        String sql = """
                SELECT
                    supplier_id,
                    supplier_name,
                    contact_number,
                    address,
                    email,
                    status
                FROM suppliers
                WHERE status = 'ACTIVE'
                ORDER BY supplier_name
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

                Supplier supplier =
                        new Supplier();

                supplier.setSupplierId(
                        resultSet.getInt("supplier_id")
                );

                supplier.setSupplierName(
                        resultSet.getString("supplier_name")
                );

                supplier.setContactNumber(
                        resultSet.getString("contact_number")
                );

                supplier.setAddress(
                        resultSet.getString("address")
                );

                supplier.setEmail(
                        resultSet.getString("email")
                );

                supplier.setStatus(
                        resultSet.getString("status")
                );

                suppliers.add(supplier);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return suppliers;
    }

    // ==================================================
    // UPDATE SUPPLIER
    // ==================================================

    public boolean updateSupplier(
            Supplier supplier
    ) {

        String sql = """
                UPDATE suppliers
                SET
                    supplier_name = ?,
                    contact_number = ?,
                    address = ?,
                    email = ?,
                    status = ?
                WHERE supplier_id = ?
                """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    supplier.getSupplierName()
            );

            statement.setString(
                    2,
                    supplier.getContactNumber()
            );

            statement.setString(
                    3,
                    supplier.getAddress()
            );

            statement.setString(
                    4,
                    supplier.getEmail()
            );

            statement.setString(
                    5,
                    supplier.getStatus()
            );

            statement.setInt(
                    6,
                    supplier.getSupplierId()
            );

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        }
    }

    // ==================================================
    // FIND SUPPLIER
    // ==================================================

    public Supplier findSupplierById(
            int supplierId
    ) {

        String sql = """
                SELECT *
                FROM suppliers
                WHERE supplier_id = ?
                """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    supplierId
            );

            try (
                    ResultSet resultSet =
                            statement.executeQuery()
            ) {

                if (resultSet.next()) {

                    Supplier supplier =
                            new Supplier();

                    supplier.setSupplierId(
                            resultSet.getInt(
                                    "supplier_id"
                            )
                    );

                    supplier.setSupplierName(
                            resultSet.getString(
                                    "supplier_name"
                            )
                    );

                    supplier.setContactNumber(
                            resultSet.getString(
                                    "contact_number"
                            )
                    );

                    supplier.setAddress(
                            resultSet.getString(
                                    "address"
                            )
                    );

                    supplier.setEmail(
                            resultSet.getString(
                                    "email"
                            )
                    );

                    supplier.setStatus(
                            resultSet.getString(
                                    "status"
                            )
                    );

                    return supplier;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}