package lk.textflow.view;

import lk.textflow.dao.InventoryAdjustmentDAO;
import lk.textflow.dao.ProductDAO;
import lk.textflow.model.InventoryAdjustment;
import lk.textflow.model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryPanel extends JPanel {

    private JComboBox<String> cmbProduct;

    private JTextField txtCurrentStock;
    private JTextField txtQuantityChange;
    private JTextField txtReason;
    private JTextField txtUserId;

    private JTable historyTable;
    private JTable lowStockTable;

    private DefaultTableModel historyModel;
    private DefaultTableModel lowStockModel;

    private final ProductDAO productDAO;
    private final InventoryAdjustmentDAO adjustmentDAO;

    private final Map<String, Product> productMap;

    public InventoryPanel() {

        productDAO = new ProductDAO();
        adjustmentDAO = new InventoryAdjustmentDAO();

        productMap = new HashMap<>();

        setLayout(new BorderLayout(10, 10));

        createUI();

        refreshAll();
    }

    private void createUI() {

        JLabel title =
                new JLabel("Inventory Management");

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24
                )
        );

        title.setBorder(
                BorderFactory.createEmptyBorder(
                        10, 10, 10, 10
                )
        );

        add(title, BorderLayout.NORTH);

        JPanel content =
                new JPanel(
                        new BorderLayout(10, 10)
                );

        content.setBorder(
                BorderFactory.createEmptyBorder(
                        10, 10, 10, 10
                )
        );

        content.add(
                createAdjustmentPanel(),
                BorderLayout.NORTH
        );

        content.add(
                createTables(),
                BorderLayout.CENTER
        );

        add(content, BorderLayout.CENTER);
    }

    private JPanel createAdjustmentPanel() {

        JPanel panel =
                new JPanel(
                        new GridBagLayout()
                );

        panel.setBorder(
                BorderFactory.createTitledBorder(
                        "Stock Adjustment"
                )
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(5, 5, 5, 5);

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        cmbProduct =
                new JComboBox<>();

        txtCurrentStock =
                new JTextField(15);

        txtCurrentStock.setEditable(false);

        txtQuantityChange =
                new JTextField(15);

        txtReason =
                new JTextField(20);

        txtUserId =
                new JTextField("1", 15);

        addField(
                panel,
                gbc,
                0,
                "Product:",
                cmbProduct
        );

        addField(
                panel,
                gbc,
                1,
                "Current Stock:",
                txtCurrentStock
        );

        addField(
                panel,
                gbc,
                2,
                "Quantity Change:",
                txtQuantityChange
        );

        addField(
                panel,
                gbc,
                3,
                "Reason:",
                txtReason
        );

        addField(
                panel,
                gbc,
                4,
                "User ID:",
                txtUserId
        );

        JLabel information =
                new JLabel(
                        "Example: +10 to add stock, -5 to remove stock"
                );

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;

        panel.add(information, gbc);

        JButton btnAdjust =
                new JButton("Adjust Stock");

        JButton btnRefresh =
                new JButton("Refresh");

        JPanel buttons =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT
                        )
                );

        buttons.add(btnAdjust);
        buttons.add(btnRefresh);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;

        panel.add(buttons, gbc);

        cmbProduct.addActionListener(
                e -> updateCurrentStock()
        );

        btnAdjust.addActionListener(
                e -> adjustStock()
        );

        btnRefresh.addActionListener(
                e -> refreshAll()
        );

        return panel;
    }

    private void addField(
            JPanel panel,
            GridBagConstraints gbc,
            int row,
            String label,
            Component component) {

        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = row;

        panel.add(
                new JLabel(label),
                gbc
        );

        gbc.gridx = 1;

        panel.add(component, gbc);
    }

    private JTabbedPane createTables() {

        JTabbedPane tabs =
                new JTabbedPane();

        tabs.addTab(
                "Adjustment History",
                createHistoryPanel()
        );

        tabs.addTab(
                "Low Stock Products",
                createLowStockPanel()
        );

        return tabs;
    }

    private JPanel createHistoryPanel() {

        JPanel panel =
                new JPanel(
                        new BorderLayout()
                );

        String[] columns = {
                "Adjustment ID",
                "Product ID",
                "User ID",
                "Quantity Change",
                "Reason",
                "Date"
        };

        historyModel =
                new DefaultTableModel(
                        columns,
                        0
                ) {
                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column) {

                        return false;
                    }
                };

        historyTable =
                new JTable(historyModel);

        panel.add(
                new JScrollPane(historyTable),
                BorderLayout.CENTER
        );

        return panel;
    }

    private JPanel createLowStockPanel() {

        JPanel panel =
                new JPanel(
                        new BorderLayout()
                );

        String[] columns = {
                "Product ID",
                "Product Name",
                "Barcode",
                "Current Stock",
                "Reorder Level"
        };

        lowStockModel =
                new DefaultTableModel(
                        columns,
                        0
                ) {
                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column) {

                        return false;
                    }
                };

        lowStockTable =
                new JTable(lowStockModel);

        panel.add(
                new JScrollPane(lowStockTable),
                BorderLayout.CENTER
        );

        return panel;
    }

    private void loadProducts() {

        cmbProduct.removeAllItems();
        productMap.clear();

        List<Product> products =
                productDAO.getAllProducts();

        for (Product product : products) {

            if (!"ACTIVE".equals(
                    product.getStatus())) {

                continue;
            }

            String display =
                    product.getProductId()
                            + " - "
                            + product.getProductName();

            cmbProduct.addItem(display);

            productMap.put(
                    display,
                    product
            );
        }

        updateCurrentStock();
    }

    private void updateCurrentStock() {

        String selected =
                (String)
                        cmbProduct.getSelectedItem();

        if (selected == null) {

            txtCurrentStock.setText("");
            return;
        }

        Product product =
                productMap.get(selected);

        if (product != null) {

            txtCurrentStock.setText(
                    String.valueOf(
                            product.getStockQuantity()
                    )
            );
        }
    }

    private void adjustStock() {

        String selected =
                (String)
                        cmbProduct.getSelectedItem();

        if (selected == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a product."
            );

            return;
        }

        String quantityText =
                txtQuantityChange
                        .getText()
                        .trim();

        String reason =
                txtReason
                        .getText()
                        .trim();

        String userText =
                txtUserId
                        .getText()
                        .trim();

        if (quantityText.isEmpty()
                || reason.isEmpty()
                || userText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Quantity, reason and user ID are required."
            );

            return;
        }

        try {

            int quantityChange =
                    Integer.parseInt(
                            quantityText
                    );

            int userId =
                    Integer.parseInt(
                            userText
                    );

            if (quantityChange == 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Quantity change cannot be 0."
                );

                return;
            }

            Product product =
                    productMap.get(selected);

            if (product.getStockQuantity()
                    + quantityChange < 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Not enough stock. Stock cannot become negative.",
                        "Invalid Adjustment",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            InventoryAdjustment adjustment =
                    new InventoryAdjustment(
                            product.getProductId(),
                            userId,
                            quantityChange,
                            reason
                    );

            boolean success =
                    adjustmentDAO.adjustStock(
                            adjustment
                    );

            if (success) {

                JOptionPane.showMessageDialog(
                        this,
                        "Stock adjusted successfully!"
                );

                txtQuantityChange.setText("");
                txtReason.setText("");

                refreshAll();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Stock adjustment failed.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Quantity and User ID must be whole numbers.",
                    "Invalid Input",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    private void loadHistory() {

        historyModel.setRowCount(0);

        List<InventoryAdjustment> adjustments =
                adjustmentDAO
                        .getAllAdjustments();

        for (InventoryAdjustment adjustment :
                adjustments) {

            historyModel.addRow(
                    new Object[]{
                            adjustment.getAdjustmentId(),
                            adjustment.getProductId(),
                            adjustment.getUserId(),
                            adjustment.getQuantityChange(),
                            adjustment.getReason(),
                            adjustment.getAdjustmentDate()
                    }
            );
        }
    }

    private void loadLowStock() {

        lowStockModel.setRowCount(0);

        List<Product> products =
                productDAO
                        .getLowStockProducts();

        for (Product product : products) {

            lowStockModel.addRow(
                    new Object[]{
                            product.getProductId(),
                            product.getProductName(),
                            product.getBarcode(),
                            product.getStockQuantity(),
                            product.getReorderLevel()
                    }
            );
        }
    }

    private void refreshAll() {

        loadProducts();
        loadHistory();
        loadLowStock();
    }


    // ======================================
// REFRESH INVENTORY PANEL
// ======================================
    public void refreshData() {

        refreshAll();
    }

}