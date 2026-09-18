package lk.textflow.sales.dao;

import lk.textflow.config.DatabaseConnection;
import lk.textflow.sales.model.SaleItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SaleItemDAO {

    public boolean addSaleItem(SaleItem saleItem) {

        String sql = "INSERT INTO sale_items " +
                "(sale_id, product_id, quantity, unit_price) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, saleItem.getSaleId());
            statement.setInt(2, saleItem.getProductId());
            statement.setInt(3, saleItem.getQuantity());
            statement.setBigDecimal(4, saleItem.getUnitPrice());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<SaleItem> getSaleItems(int saleId) {

        List<SaleItem> items = new ArrayList<>();

        String sql = "SELECT * FROM sale_items WHERE sale_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, saleId);

            try (ResultSet rs = statement.executeQuery()) {

                while (rs.next()) {

                    SaleItem item = new SaleItem();

                    item.setSaleItemId(rs.getInt("sale_item_id"));
                    item.setSaleId(rs.getInt("sale_id"));
                    item.setProductId(rs.getInt("product_id"));
                    item.setQuantity(rs.getInt("quantity"));
                    item.setUnitPrice(rs.getBigDecimal("unit_price"));
                    item.setTotalPrice(rs.getBigDecimal("total_price"));

                    items.add(item);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }
}
