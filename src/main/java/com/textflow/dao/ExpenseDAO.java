package com.textflow.dao;

import lk.textflow.config.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExpenseDAO {

    // =========================================
    // ADD EXPENSE
    // =========================================

    public boolean addExpense(
            int expenseCategoryId,
            int userId,
            Integer relatedUserId,
            String expenseDate,
            String description,
            double amount,
            String paymentMethod
    ) {

        if (amount <= 0) {
            return false;
        }

        String sql = """
                INSERT INTO expenses
                (
                    expense_category_id,
                    user_id,
                    related_user_id,
                    expense_date,
                    description,
                    amount,
                    payment_method,
                    status
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, 'ACTIVE')
                """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    expenseCategoryId
            );

            statement.setInt(
                    2,
                    userId
            );

            if (relatedUserId == null) {

                statement.setNull(
                        3,
                        Types.INTEGER
                );

            } else {

                statement.setInt(
                        3,
                        relatedUserId
                );
            }

            statement.setDate(
                    4,
                    Date.valueOf(expenseDate)
            );

            statement.setString(
                    5,
                    description
            );

            statement.setDouble(
                    6,
                    amount
            );

            statement.setString(
                    7,
                    paymentMethod
            );

            return statement.executeUpdate() > 0;

        } catch (
                SQLException |
                IllegalArgumentException e
        ) {

            e.printStackTrace();
            return false;
        }
    }

    // =========================================
    // DELETE EXPENSE
    // =========================================

    public boolean deleteExpense(
            int expenseId
    ) {

        String sql =
                "DELETE FROM expenses WHERE expense_id = ?";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    expenseId
            );

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        }
    }

    // =========================================
    // GET EXPENSES
    // =========================================

    public List<String[]> getExpenses() {

        List<String[]> expenses =
                new ArrayList<>();

        String sql = """
                SELECT
                    expense_id,
                    expense_category_id,
                    expense_date,
                    description,
                    amount,
                    payment_method,
                    status
                FROM expenses
                ORDER BY expense_id DESC
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

                String[] expense = {

                        String.valueOf(
                                resultSet.getInt(
                                        "expense_id"
                                )
                        ),

                        String.valueOf(
                                resultSet.getInt(
                                        "expense_category_id"
                                )
                        ),

                        String.valueOf(
                                resultSet.getDate(
                                        "expense_date"
                                )
                        ),

                        resultSet.getString(
                                "description"
                        ),

                        String.valueOf(
                                resultSet.getDouble(
                                        "amount"
                                )
                        ),

                        resultSet.getString(
                                "payment_method"
                        ),

                        resultSet.getString(
                                "status"
                        )
                };

                expenses.add(expense);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return expenses;
    }
}