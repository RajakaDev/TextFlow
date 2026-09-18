package lk.textflow.sales.dao;

import lk.textflow.config.DatabaseConnection;
import lk.textflow.sales.model.SaleItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SaleItemDAO {

    public void addSaleItem(SaleItem saleItem) throws SQLException {

        String sql = """
                INSERT INTO sale_items
                (sale_id, product_id, quantity, unit_price)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, saleItem.getSaleId());
            statement.setInt(2, saleItem.getProductId());
            statement.setInt(3, saleItem.getQuantity());
            statement.setBigDecimal(4, saleItem.getUnitPrice());

            statement.executeUpdate();
        }
    }

    public java.util.List<SaleItem> getSaleItems(int saleId) throws SQLException {

        String sql = "SELECT * FROM sale_items WHERE sale_id = ?";

        java.util.List<SaleItem> items = new java.util.ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, saleId);

            try (java.sql.ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {

                    SaleItem item = new SaleItem();

                    item.setSaleItemId(resultSet.getInt("sale_item_id"));
                    item.setSaleId(resultSet.getInt("sale_id"));
                    item.setProductId(resultSet.getInt("product_id"));
                    item.setQuantity(resultSet.getInt("quantity"));
                    item.setUnitPrice(resultSet.getBigDecimal("unit_price"));
                    item.setTotalPrice(resultSet.getBigDecimal("total_price"));

                    items.add(item);
                }
            }
        }

        return items;
    }


}

