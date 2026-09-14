package lk.textflow.view;

import lk.textflow.dao.CategoryDAO;
import lk.textflow.dao.ProductDAO;
import lk.textflow.model.Category;
import lk.textflow.model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductPanel extends JPanel {

    private JTextField txtProductId;
    private JComboBox<String> cmbCategory;
    private JTextField txtProductName;
    private JTextField txtBarcode;
    private JTextField txtUnitPrice;
    private JTextField txtCostPrice;
    private JTextField txtStockQuantity;
    private JTextField txtReorderLevel;
    private JComboBox<String> cmbStatus;

    private JTextField txtSearch;

    private JTable productTable;
    private DefaultTableModel tableModel;

    private JButton btnAdd;
    private JButton btnUpdate;
    private JButton btnClear;
    private JButton btnSearch;
    private JButton btnShowAll;
    private JButton btnLowStock;

    private final ProductDAO productDAO;
    private final CategoryDAO categoryDAO;

    private final Map<String, Integer> categoryMap;

    public ProductPanel() {

        productDAO = new ProductDAO();
        categoryDAO = new CategoryDAO();
        categoryMap = new HashMap<>();

        setLayout(new BorderLayout(10, 10));

        initializeUI();
        loadCategories();
        loadProducts();
    }

    private void initializeUI() {

        JLabel titleLabel =
                new JLabel("Product Management");

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        titleLabel.setBorder(
                BorderFactory.createEmptyBorder(
                        10, 10, 10, 10
                )
        );

        add(titleLabel, BorderLayout.NORTH);

        JPanel mainPanel =
                new JPanel(new BorderLayout(10, 10));

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        10, 10, 10, 10
                )
        );

        mainPanel.add(
                createFormPanel(),
                BorderLayout.NORTH
        );

        mainPanel.add(
                createTablePanel(),
                BorderLayout.CENTER
        );

        add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel createFormPanel() {

        JPanel formPanel =
                new JPanel(new GridBagLayout());

        formPanel.setBorder(
                BorderFactory.createTitledBorder(
                        "Product Details"
                )
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(5, 5, 5, 5);

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        txtProductId = new JTextField(15);
        txtProductId.setEditable(false);

        cmbCategory = new JComboBox<>();

        txtProductName = new JTextField(20);
        txtBarcode = new JTextField(20);
        txtUnitPrice = new JTextField(15);
        txtCostPrice = new JTextField(15);
        txtStockQuantity = new JTextField(15);
        txtReorderLevel = new JTextField(15);

        cmbStatus =
                new JComboBox<>(
                        new String[]{
                                "ACTIVE",
                                "INACTIVE"
                        }
                );

        int row = 0;

        addFormField(
                formPanel,
                gbc,
                row++,
                "Product ID:",
                txtProductId
        );

        addFormField(
                formPanel,
                gbc,
                row++,
                "Category:",
                cmbCategory
        );

        addFormField(
                formPanel,
                gbc,
                row++,
                "Product Name:",
                txtProductName
        );

        addFormField(
                formPanel,
                gbc,
                row++,
                "Barcode:",
                txtBarcode
        );

        addFormField(
                formPanel,
                gbc,
                row++,
                "Selling Price:",
                txtUnitPrice
        );

        addFormField(
                formPanel,
                gbc,
                row++,
                "Cost Price:",
                txtCostPrice
        );

        addFormField(
                formPanel,
                gbc,
                row++,
                "Stock Quantity:",
                txtStockQuantity
        );

        addFormField(
                formPanel,
                gbc,
                row++,
                "Reorder Level:",
                txtReorderLevel
        );

        addFormField(
                formPanel,
                gbc,
                row++,
                "Status:",
                cmbStatus
        );

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT
                        )
                );

        btnAdd = new JButton("Add");
        btnUpdate = new JButton("Update");
        btnClear = new JButton("Clear");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnClear);

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;

        formPanel.add(
                buttonPanel,
                gbc
        );

        btnAdd.addActionListener(
                e -> addProduct()
        );

        btnUpdate.addActionListener(
                e -> updateProduct()
        );

        btnClear.addActionListener(
                e -> clearForm()
        );

        return formPanel;
    }

    private void addFormField(
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

        panel.add(
                component,
                gbc
        );
    }

    private JPanel createTablePanel() {

        JPanel tablePanel =
                new JPanel(
                        new BorderLayout(5, 5)
                );

        tablePanel.setBorder(
                BorderFactory.createTitledBorder(
                        "Product List"
                )
        );

        JPanel searchPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT
                        )
                );

        searchPanel.add(
                new JLabel(
                        "Barcode Search:"
                )
        );

        txtSearch =
                new JTextField(20);

        btnSearch =
                new JButton("Search");

        btnShowAll =
                new JButton("Show All");

        btnLowStock =
                new JButton("Low Stock");

        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);
        searchPanel.add(btnShowAll);
        searchPanel.add(btnLowStock);

        tablePanel.add(
                searchPanel,
                BorderLayout.NORTH
        );

        String[] columns = {
                "ID",
                "Category ID",
                "Product Name",
                "Barcode",
                "Selling Price",
                "Cost Price",
                "Stock",
                "Reorder Level",
                "Status"
        };

        tableModel =
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

        productTable =
                new JTable(tableModel);

        productTable.setSelectionMode(
                ListSelectionModel
                        .SINGLE_SELECTION
        );

        productTable
                .getSelectionModel()
                .addListSelectionListener(
                        e -> {

                            if (!e.getValueIsAdjusting()) {
                                loadSelectedProduct();
                            }
                        }
                );

        JScrollPane scrollPane =
                new JScrollPane(
                        productTable
                );

        tablePanel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        btnSearch.addActionListener(
                e -> searchProduct()
        );

        btnShowAll.addActionListener(
                e -> loadProducts()
        );

        btnLowStock.addActionListener(
                e -> loadLowStockProducts()
        );

        return tablePanel;
    }

    private void loadCategories() {

        cmbCategory.removeAllItems();
        categoryMap.clear();

        List<Category> categories =
                categoryDAO.getAllCategories();

        for (Category category : categories) {

            if ("ACTIVE".equals(
                    category.getStatus())) {

                String name =
                        category.getCategoryName();

                cmbCategory.addItem(name);

                categoryMap.put(
                        name,
                        category.getCategoryId()
                );
            }
        }
    }

    private void loadProducts() {

        tableModel.setRowCount(0);

        List<Product> products =
                productDAO.getAllProducts();

        displayProducts(products);
    }

    private void displayProducts(
            List<Product> products) {

        tableModel.setRowCount(0);

        for (Product product : products) {

            tableModel.addRow(
                    new Object[]{
                            product.getProductId(),
                            product.getCategoryId(),
                            product.getProductName(),
                            product.getBarcode(),
                            product.getUnitPrice(),
                            product.getCostPrice(),
                            product.getStockQuantity(),
                            product.getReorderLevel(),
                            product.getStatus()
                    }
            );
        }
    }

    private void addProduct() {

        try {

            if (!validateForm()) {
                return;
            }

            String categoryName =
                    cmbCategory
                            .getSelectedItem()
                            .toString();

            int categoryId =
                    categoryMap.get(
                            categoryName
                    );

            Product product =
                    new Product(
                            categoryId,
                            txtProductName
                                    .getText()
                                    .trim(),

                            txtBarcode
                                    .getText()
                                    .trim(),

                            new BigDecimal(
                                    txtUnitPrice
                                            .getText()
                                            .trim()
                            ),

                            new BigDecimal(
                                    txtCostPrice
                                            .getText()
                                            .trim()
                            ),

                            Integer.parseInt(
                                    txtStockQuantity
                                            .getText()
                                            .trim()
                            ),

                            Integer.parseInt(
                                    txtReorderLevel
                                            .getText()
                                            .trim()
                            ),

                            cmbStatus
                                    .getSelectedItem()
                                    .toString()
                    );

            boolean success =
                    productDAO.addProduct(
                            product
                    );

            if (success) {

                JOptionPane.showMessageDialog(
                        this,
                        "Product added successfully!"
                );

                clearForm();
                loadProducts();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Product could not be added.\n"
                                + "Check whether the barcode already exists.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Price, stock and reorder level must contain valid numbers.",
                    "Invalid Input",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    private void updateProduct() {

        if (txtProductId
                .getText()
                .isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a product first."
            );

            return;
        }

        try {

            if (!validateForm()) {
                return;
            }

            int productId =
                    Integer.parseInt(
                            txtProductId
                                    .getText()
                    );

            String categoryName =
                    cmbCategory
                            .getSelectedItem()
                            .toString();

            int categoryId =
                    categoryMap.get(
                            categoryName
                    );

            Product product =
                    new Product(
                            productId,
                            categoryId,
                            txtProductName
                                    .getText()
                                    .trim(),

                            txtBarcode
                                    .getText()
                                    .trim(),

                            new BigDecimal(
                                    txtUnitPrice
                                            .getText()
                                            .trim()
                            ),

                            new BigDecimal(
                                    txtCostPrice
                                            .getText()
                                            .trim()
                            ),

                            Integer.parseInt(
                                    txtStockQuantity
                                            .getText()
                                            .trim()
                            ),

                            Integer.parseInt(
                                    txtReorderLevel
                                            .getText()
                                            .trim()
                            ),

                            cmbStatus
                                    .getSelectedItem()
                                    .toString()
                    );

            boolean success =
                    productDAO.updateProduct(
                            product
                    );

            if (success) {

                JOptionPane.showMessageDialog(
                        this,
                        "Product updated successfully!"
                );

                clearForm();
                loadProducts();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Product update failed.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter valid numeric values.",
                    "Invalid Input",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    private void searchProduct() {

        String barcode =
                txtSearch
                        .getText()
                        .trim();

        if (barcode.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Enter a barcode to search."
            );

            return;
        }

        Product product =
                productDAO.findByBarcode(
                        barcode
                );

        tableModel.setRowCount(0);

        if (product == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Product not found."
            );

            return;
        }

        tableModel.addRow(
                new Object[]{
                        product.getProductId(),
                        product.getCategoryId(),
                        product.getProductName(),
                        product.getBarcode(),
                        product.getUnitPrice(),
                        product.getCostPrice(),
                        product.getStockQuantity(),
                        product.getReorderLevel(),
                        product.getStatus()
                }
        );
    }

    private void loadLowStockProducts() {

        List<Product> lowStock =
                productDAO
                        .getLowStockProducts();

        displayProducts(
                lowStock
        );

        if (lowStock.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "No low-stock products."
            );
        }
    }

    private boolean validateForm() {

        if (cmbCategory.getSelectedItem()
                == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please create/select a category."
            );

            return false;
        }

        if (txtProductName
                .getText()
                .trim()
                .isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Product name is required."
            );

            return false;
        }

        if (txtBarcode
                .getText()
                .trim()
                .isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Barcode is required."
            );

            return false;
        }

        if (txtUnitPrice
                .getText()
                .trim()
                .isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Selling price is required."
            );

            return false;
        }

        if (txtCostPrice
                .getText()
                .trim()
                .isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Cost price is required."
            );

            return false;
        }

        if (txtStockQuantity
                .getText()
                .trim()
                .isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Stock quantity is required."
            );

            return false;
        }

        if (txtReorderLevel
                .getText()
                .trim()
                .isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Reorder level is required."
            );

            return false;
        }

        return true;
    }

    private void loadSelectedProduct() {

        int selectedRow =
                productTable.getSelectedRow();

        if (selectedRow == -1) {
            return;
        }

        txtProductId.setText(
                tableModel
                        .getValueAt(
                                selectedRow,
                                0
                        )
                        .toString()
        );

        int categoryId =
                Integer.parseInt(
                        tableModel
                                .getValueAt(
                                        selectedRow,
                                        1
                                )
                                .toString()
                );

        selectCategoryById(
                categoryId
        );

        txtProductName.setText(
                tableModel
                        .getValueAt(
                                selectedRow,
                                2
                        )
                        .toString()
        );

        txtBarcode.setText(
                tableModel
                        .getValueAt(
                                selectedRow,
                                3
                        )
                        .toString()
        );

        txtUnitPrice.setText(
                tableModel
                        .getValueAt(
                                selectedRow,
                                4
                        )
                        .toString()
        );

        txtCostPrice.setText(
                tableModel
                        .getValueAt(
                                selectedRow,
                                5
                        )
                        .toString()
        );

        txtStockQuantity.setText(
                tableModel
                        .getValueAt(
                                selectedRow,
                                6
                        )
                        .toString()
        );

        txtReorderLevel.setText(
                tableModel
                        .getValueAt(
                                selectedRow,
                                7
                        )
                        .toString()
        );

        cmbStatus.setSelectedItem(
                tableModel
                        .getValueAt(
                                selectedRow,
                                8
                        )
                        .toString()
        );
    }

    private void selectCategoryById(
            int categoryId) {

        for (Map.Entry<String, Integer> entry :
                categoryMap.entrySet()) {

            if (entry.getValue()
                    == categoryId) {

                cmbCategory.setSelectedItem(
                        entry.getKey()
                );

                break;
            }
        }
    }

    private void clearForm() {

        txtProductId.setText("");
        txtProductName.setText("");
        txtBarcode.setText("");
        txtUnitPrice.setText("");
        txtCostPrice.setText("");
        txtStockQuantity.setText("");
        txtReorderLevel.setText("");

        cmbStatus.setSelectedItem(
                "ACTIVE"
        );

        if (cmbCategory.getItemCount() > 0) {
            cmbCategory.setSelectedIndex(0);
        }

        productTable.clearSelection();
    }
}