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

    // =========================================================
    // FORM COMPONENTS
    // =========================================================

    private JTextField txtProductId;
    private JComboBox<String> cmbCategory;
    private JTextField txtProductName;
    private JTextField txtBarcode;
    private JTextField txtUnitPrice;
    private JTextField txtCostPrice;
    private JTextField txtStockQuantity;
    private JTextField txtReorderLevel;
    private JComboBox<String> cmbStatus;

    // Search
    private JTextField txtSearch;

    // Table
    private JTable productTable;
    private DefaultTableModel tableModel;

    // Buttons
    private JButton btnAdd;
    private JButton btnUpdate;
    private JButton btnClear;
    private JButton btnSearch;
    private JButton btnShowAll;
    private JButton btnLowStock;

    // DAO
    private final ProductDAO productDAO;
    private final CategoryDAO categoryDAO;

    // Category Name -> Category ID
    private final Map<String, Integer> categoryMap;


    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public ProductPanel() {

        productDAO = new ProductDAO();
        categoryDAO = new CategoryDAO();

        categoryMap = new HashMap<>();

        setLayout(
                new BorderLayout(10, 10)
        );

        initializeUI();

        loadCategories();
        loadProducts();

        clearForm();
    }


    // =========================================================
    // INITIALIZE UI
    // =========================================================

    private void initializeUI() {

        JLabel titleLabel =
                new JLabel(
                        "Product Management"
                );

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24
                )
        );

        titleLabel.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        10,
                        10,
                        10
                )
        );

        add(
                titleLabel,
                BorderLayout.NORTH
        );


        JPanel mainPanel =
                new JPanel(
                        new BorderLayout(
                                10,
                                10
                        )
                );

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        10,
                        10,
                        10
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

        add(
                mainPanel,
                BorderLayout.CENTER
        );
    }


    // =========================================================
    // CREATE FORM PANEL
    // =========================================================

    private JPanel createFormPanel() {

        JPanel formPanel =
                new JPanel(
                        new GridBagLayout()
                );

        formPanel.setBorder(
                BorderFactory.createTitledBorder(
                        "Product Details"
                )
        );


        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(
                        5,
                        5,
                        5,
                        5
                );

        gbc.fill =
                GridBagConstraints.HORIZONTAL;


        // -------------------------
        // Fields
        // -------------------------

        txtProductId =
                new JTextField(15);

        txtProductId.setEditable(false);


        cmbCategory =
                new JComboBox<>();


        txtProductName =
                new JTextField(20);

        txtBarcode =
                new JTextField(20);

        txtUnitPrice =
                new JTextField(15);

        txtCostPrice =
                new JTextField(15);

        txtStockQuantity =
                new JTextField(15);

        txtReorderLevel =
                new JTextField(15);


        cmbStatus =
                new JComboBox<>(
                        new String[]{
                                "ACTIVE",
                                "INACTIVE"
                        }
                );


        // -------------------------
        // Add fields
        // -------------------------

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


        // -------------------------
        // Buttons
        // -------------------------

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT
                        )
                );


        btnAdd =
                new JButton("Add");

        btnUpdate =
                new JButton("Update");

        btnClear =
                new JButton("Clear");


        buttonPanel.add(
                btnAdd
        );

        buttonPanel.add(
                btnUpdate
        );

        buttonPanel.add(
                btnClear
        );


        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;


        formPanel.add(
                buttonPanel,
                gbc
        );


        // -------------------------
        // Button events
        // -------------------------

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


    // =========================================================
    // ADD FORM FIELD
    // =========================================================

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


    // =========================================================
    // CREATE TABLE PANEL
    // =========================================================

    private JPanel createTablePanel() {

        JPanel tablePanel =
                new JPanel(
                        new BorderLayout(
                                5,
                                5
                        )
                );


        tablePanel.setBorder(
                BorderFactory.createTitledBorder(
                        "Product List"
                )
        );


        // -------------------------
        // Search Panel
        // -------------------------

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


        searchPanel.add(
                txtSearch
        );

        searchPanel.add(
                btnSearch
        );

        searchPanel.add(
                btnShowAll
        );

        searchPanel.add(
                btnLowStock
        );


        tablePanel.add(
                searchPanel,
                BorderLayout.NORTH
        );


        // -------------------------
        // Table
        // -------------------------

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
                new JTable(
                        tableModel
                );


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


        // -------------------------
        // Search button events
        // -------------------------

        btnSearch.addActionListener(
                e -> searchProduct()
        );


        btnShowAll.addActionListener(
                e -> {

                    txtSearch.setText("");

                    loadProducts();

                    clearForm();
                }
        );


        btnLowStock.addActionListener(
                e -> loadLowStockProducts()
        );


        return tablePanel;
    }


    // =========================================================
    // LOAD CATEGORIES
    // =========================================================

    private void loadCategories() {

        cmbCategory.removeAllItems();

        categoryMap.clear();


        List<Category> categories =
                categoryDAO
                        .getAllCategories();


        for (Category category :
                categories) {


            // Only active categories
            if (!"ACTIVE".equals(
                    category.getStatus())) {

                continue;
            }


            String categoryName =
                    category.getCategoryName();


            cmbCategory.addItem(
                    categoryName
            );


            categoryMap.put(
                    categoryName,
                    category.getCategoryId()
            );
        }
    }


    // =========================================================
    // LOAD ALL PRODUCTS
    // =========================================================

    private void loadProducts() {

        List<Product> products =
                productDAO
                        .getAllProducts();


        displayProducts(
                products
        );
    }


    // =========================================================
    // DISPLAY PRODUCTS
    // =========================================================

    private void displayProducts(
            List<Product> products) {

        tableModel.setRowCount(0);


        for (Product product :
                products) {


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


    // =========================================================
    // ADD PRODUCT
    // =========================================================

    private void addProduct() {

        try {

            // -------------------------
            // Required field validation
            // -------------------------

            if (!validateForm()) {

                return;
            }


            // -------------------------
            // Read barcode
            // -------------------------

            String barcode =
                    txtBarcode
                            .getText()
                            .trim();


            // -------------------------
            // Duplicate barcode check
            // -------------------------

            Product existingProduct =
                    productDAO
                            .findByBarcode(
                                    barcode
                            );


            if (existingProduct != null) {

                JOptionPane.showMessageDialog(
                        this,

                        "This barcode already exists.\n"
                                + "Existing Product: "
                                + existingProduct
                                .getProductName(),

                        "Duplicate Barcode",

                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }


            // -------------------------
            // Get Category
            // -------------------------

            String categoryName =
                    (String)
                            cmbCategory
                                    .getSelectedItem();


            Integer categoryId =
                    categoryMap
                            .get(
                                    categoryName
                            );


            if (categoryId == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Invalid category selected.",
                        "Category Error",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }


            // -------------------------
            // Convert numeric values
            // -------------------------

            BigDecimal unitPrice =
                    new BigDecimal(
                            txtUnitPrice
                                    .getText()
                                    .trim()
                    );


            BigDecimal costPrice =
                    new BigDecimal(
                            txtCostPrice
                                    .getText()
                                    .trim()
                    );


            int stockQuantity =
                    Integer.parseInt(
                            txtStockQuantity
                                    .getText()
                                    .trim()
                    );


            int reorderLevel =
                    Integer.parseInt(
                            txtReorderLevel
                                    .getText()
                                    .trim()
                    );


            // -------------------------
            // Negative value validation
            // -------------------------

            if (unitPrice.compareTo(
                    BigDecimal.ZERO) < 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Selling price cannot be negative.",
                        "Invalid Selling Price",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }


            if (costPrice.compareTo(
                    BigDecimal.ZERO) < 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Cost price cannot be negative.",
                        "Invalid Cost Price",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }


            if (stockQuantity < 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Initial stock cannot be negative.",
                        "Invalid Stock",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }


            if (reorderLevel < 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Reorder level cannot be negative.",
                        "Invalid Reorder Level",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }


            // -------------------------
            // Create Product
            // -------------------------

            Product product =
                    new Product(

                            categoryId,

                            txtProductName
                                    .getText()
                                    .trim(),

                            barcode,

                            unitPrice,

                            costPrice,

                            stockQuantity,

                            reorderLevel,

                            cmbStatus
                                    .getSelectedItem()
                                    .toString()
                    );


            // -------------------------
            // Save Product
            // -------------------------

            boolean success =
                    productDAO
                            .addProduct(
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

                        "Product could not be added.",

                        "Error",

                        JOptionPane.ERROR_MESSAGE
                );
            }


        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,

                    "Selling price, cost price, stock and "
                            + "reorder level must contain valid numbers.",

                    "Invalid Input",

                    JOptionPane.WARNING_MESSAGE
            );
        }
    }


    // =========================================================
    // UPDATE PRODUCT
    // =========================================================

    private void updateProduct() {

        // Product must be selected
        if (txtProductId
                .getText()
                .trim()
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
                                    .trim()
                    );


            String categoryName =
                    (String)
                            cmbCategory
                                    .getSelectedItem();


            Integer categoryId =
                    categoryMap
                            .get(
                                    categoryName
                            );


            if (categoryId == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Invalid category selected.",
                        "Category Error",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }


            String barcode =
                    txtBarcode
                            .getText()
                            .trim();


            // -------------------------
            // Duplicate barcode check
            // -------------------------

            Product barcodeProduct =
                    productDAO
                            .findByBarcode(
                                    barcode
                            );


            /*
             * Barcode is allowed if it belongs
             * to the currently selected product.
             *
             * It is NOT allowed if another
             * product already uses it.
             */
            if (barcodeProduct != null
                    && barcodeProduct.getProductId()
                    != productId) {


                JOptionPane.showMessageDialog(
                        this,

                        "This barcode is already used by:\n"
                                + barcodeProduct
                                .getProductName(),

                        "Duplicate Barcode",

                        JOptionPane.WARNING_MESSAGE
                );


                return;
            }


            // -------------------------
            // Numeric values
            // -------------------------

            BigDecimal unitPrice =
                    new BigDecimal(
                            txtUnitPrice
                                    .getText()
                                    .trim()
                    );


            BigDecimal costPrice =
                    new BigDecimal(
                            txtCostPrice
                                    .getText()
                                    .trim()
                    );


            /*
             * We still read stock so the Product
             * object contains the displayed value.
             *
             * ProductDAO.updateProduct() does NOT
             * update stock_quantity.
             */
            int stockQuantity =
                    Integer.parseInt(
                            txtStockQuantity
                                    .getText()
                                    .trim()
                    );


            int reorderLevel =
                    Integer.parseInt(
                            txtReorderLevel
                                    .getText()
                                    .trim()
                    );


            // -------------------------
            // Validate prices
            // -------------------------

            if (unitPrice.compareTo(
                    BigDecimal.ZERO) < 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Selling price cannot be negative.",
                        "Invalid Selling Price",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }


            if (costPrice.compareTo(
                    BigDecimal.ZERO) < 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Cost price cannot be negative.",
                        "Invalid Cost Price",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }


            if (reorderLevel < 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Reorder level cannot be negative.",
                        "Invalid Reorder Level",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }


            // -------------------------
            // Create Product object
            // -------------------------

            Product product =
                    new Product(

                            productId,

                            categoryId,

                            txtProductName
                                    .getText()
                                    .trim(),

                            barcode,

                            unitPrice,

                            costPrice,

                            stockQuantity,

                            reorderLevel,

                            cmbStatus
                                    .getSelectedItem()
                                    .toString()
                    );


            // -------------------------
            // Update
            // -------------------------

            boolean success =
                    productDAO
                            .updateProduct(
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


    // =========================================================
    // SEARCH PRODUCT BY BARCODE
    // =========================================================

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
                productDAO
                        .findByBarcode(
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


    // =========================================================
    // LOW STOCK PRODUCTS
    // =========================================================

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


    // =========================================================
    // REQUIRED FIELD VALIDATION
    // =========================================================

    private boolean validateForm() {

        // Category
        if (cmbCategory
                .getSelectedItem()
                == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please create/select a category."
            );

            return false;
        }


        // Product Name
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


        // Barcode
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


        // Selling Price
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


        // Cost Price
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


        // Stock Quantity
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


        // Reorder Level
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


    // =========================================================
    // LOAD SELECTED PRODUCT
    // =========================================================

    private void loadSelectedProduct() {

        int selectedRow =
                productTable
                        .getSelectedRow();


        if (selectedRow == -1) {

            return;
        }


        // Product ID
        txtProductId.setText(

                tableModel
                        .getValueAt(
                                selectedRow,
                                0
                        )
                        .toString()
        );


        // Category ID
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


        // Product Name
        txtProductName.setText(

                tableModel
                        .getValueAt(
                                selectedRow,
                                2
                        )
                        .toString()
        );


        // Barcode
        txtBarcode.setText(

                tableModel
                        .getValueAt(
                                selectedRow,
                                3
                        )
                        .toString()
        );


        // Selling Price
        txtUnitPrice.setText(

                tableModel
                        .getValueAt(
                                selectedRow,
                                4
                        )
                        .toString()
        );


        // Cost Price
        txtCostPrice.setText(

                tableModel
                        .getValueAt(
                                selectedRow,
                                5
                        )
                        .toString()
        );


        // Stock
        txtStockQuantity.setText(

                tableModel
                        .getValueAt(
                                selectedRow,
                                6
                        )
                        .toString()
        );


        // Reorder Level
        txtReorderLevel.setText(

                tableModel
                        .getValueAt(
                                selectedRow,
                                7
                        )
                        .toString()
        );


        // Status
        cmbStatus.setSelectedItem(

                tableModel
                        .getValueAt(
                                selectedRow,
                                8
                        )
                        .toString()
        );


        // =====================================================
        // LOCK STOCK
        // =====================================================

        /*
         * Existing product stock cannot
         * be directly changed here.
         *
         * User must use Inventory Management.
         */
        txtStockQuantity.setEditable(
                false
        );


        txtStockQuantity.setToolTipText(
                "Use Inventory Management to change stock."
        );
    }


    // =========================================================
    // SELECT CATEGORY USING CATEGORY ID
    // =========================================================

    private void selectCategoryById(
            int categoryId) {


        for (Map.Entry<String, Integer> entry :
                categoryMap.entrySet()) {


            if (entry
                    .getValue()
                    == categoryId) {


                cmbCategory.setSelectedItem(
                        entry.getKey()
                );


                break;
            }
        }
    }


    // =========================================================
    // CLEAR FORM
    // =========================================================

    private void clearForm() {

        txtProductId.setText("");

        txtProductName.setText("");

        txtBarcode.setText("");

        txtUnitPrice.setText("");

        txtCostPrice.setText("");

        txtStockQuantity.setText("");

        txtReorderLevel.setText("");

        txtSearch.setText("");


        // =====================================================
        // ENABLE STOCK FOR NEW PRODUCT
        // =====================================================

        txtStockQuantity.setEditable(
                true
        );


        txtStockQuantity.setToolTipText(
                "Enter the initial stock quantity for the new product."
        );


        // Default Status
        cmbStatus.setSelectedItem(
                "ACTIVE"
        );


        // Default Category
        if (cmbCategory
                .getItemCount() > 0) {

            cmbCategory
                    .setSelectedIndex(0);
        }


        // Clear table selection
        productTable
                .clearSelection();
    }


    // =========================================================
    // REFRESH PRODUCT PANEL
    // =========================================================

    public void refreshData() {

        loadCategories();

        loadProducts();

        clearForm();
    }
}