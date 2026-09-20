package lk.textflow.sales.view;

import lk.textflow.config.DatabaseConnection;
import lk.textflow.dao.InventoryAdjustmentDAO;
import lk.textflow.dao.ProductDAO;
import lk.textflow.model.InventoryAdjustment;
import lk.textflow.model.Product;
import lk.textflow.sales.dao.SaleDAO;
import lk.textflow.sales.dao.SaleItemDAO;
import lk.textflow.sales.model.Sale;
import lk.textflow.sales.model.SaleItem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class SalesPanel extends JPanel {

    // ==================================================
    // FORM COMPONENTS
    // ==================================================

    private JTextField customerIdField;
    private JTextField barcodeField;
    private JTextField productNameField;
    private JTextField availableStockField;
    private JTextField quantityField;
    private JTextField unitPriceField;
    private JTextField amountGivenField;

    private JLabel totalLabel;
    private JLabel balanceLabel;

    private JComboBox<String> paymentMethodBox;

    // ==================================================
    // DAO
    // ==================================================

    private final SaleDAO saleDAO;
    private final ProductDAO productDAO;

    // ==================================================
    // CURRENT SALE DATA
    // ==================================================

    private Product selectedProduct;

    private int lastSaleId = -1;

    private final int loggedInUserId;

    // ==================================================
    // COLORS
    // ==================================================

    private final Color DARK_BLUE =
            new Color(31, 60, 136);

    private final Color LIGHT_BACKGROUND =
            new Color(245, 247, 250);

    private final Color READ_ONLY_BACKGROUND =
            new Color(248, 250, 252);

    // ==================================================
    // TEST CONSTRUCTOR
    // ==================================================

    public SalesPanel() {

        /*
         * Only for running SalesPanel separately.
         *
         * When opened from DashboardFrame,
         * use SalesPanel(loggedInUserId).
         */
        this(1);
    }

    // ==================================================
    // NORMAL CONSTRUCTOR
    // ==================================================

    public SalesPanel(
            int loggedInUserId
    ) {

        this.loggedInUserId =
                loggedInUserId;

        saleDAO =
                new SaleDAO();

        productDAO =
                new ProductDAO();

        createUI();
    }

    // ==================================================
    // CREATE UI
    // ==================================================

    private void createUI() {

        setLayout(
                new BorderLayout(
                        15,
                        15
                )
        );

        setBackground(
                LIGHT_BACKGROUND
        );

        setBorder(
                new EmptyBorder(
                        20,
                        20,
                        20,
                        20
                )
        );

        // ==================================================
        // HEADER
        // ==================================================

        JPanel headerPanel =
                new JPanel(
                        new BorderLayout()
                );

        headerPanel.setBackground(
                DARK_BLUE
        );

        headerPanel.setBorder(
                new EmptyBorder(
                        15,
                        20,
                        15,
                        20
                )
        );

        JLabel titleLabel =
                new JLabel(
                        "TEXTFLOW  |  Sales & Billing"
                );

        titleLabel.setForeground(
                Color.WHITE
        );

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24
                )
        );

        headerPanel.add(
                titleLabel,
                BorderLayout.WEST
        );

        add(
                headerPanel,
                BorderLayout.NORTH
        );

        // ==================================================
        // MAIN CARD
        // ==================================================

        JPanel formCard =
                new JPanel(
                        new BorderLayout(
                                15,
                                15
                        )
                );

        formCard.setBackground(
                Color.WHITE
        );

        formCard.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        220,
                                        225,
                                        230
                                )
                        ),
                        new EmptyBorder(
                                20,
                                25,
                                20,
                                25
                        )
                )
        );

        JLabel formTitle =
                new JLabel(
                        "Sale Details"
                );

        formTitle.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        18
                )
        );

        formTitle.setForeground(
                DARK_BLUE
        );

        formCard.add(
                formTitle,
                BorderLayout.NORTH
        );

        // ==================================================
        // COMPONENTS
        // ==================================================

        customerIdField =
                new JTextField();

        barcodeField =
                new JTextField();

        productNameField =
                createReadOnlyField();

        availableStockField =
                createReadOnlyField();

        quantityField =
                new JTextField();

        unitPriceField =
                createReadOnlyField();

        amountGivenField =
                new JTextField();

        totalLabel =
                new JLabel(
                        "0.00"
                );

        totalLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        16
                )
        );

        totalLabel.setForeground(
                new Color(
                        34,
                        100,
                        58
                )
        );

        balanceLabel =
                new JLabel(
                        "0.00"
                );

        balanceLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        16
                )
        );

        paymentMethodBox =
                new JComboBox<>(
                        new String[]{
                                "CASH",
                                "CARD",
                                "BANK",
                                "OTHER"
                        }
                );

        JButton findProductButton =
                createButton(
                        "Find",
                        new Color(
                                219,
                                234,
                                254
                        ),
                        new Color(
                                30,
                                64,
                                175
                        )
                );

        // ==================================================
        // BARCODE PANEL
        // ==================================================

        JPanel barcodePanel =
                new JPanel(
                        new BorderLayout(
                                8,
                                0
                        )
                );

        barcodePanel.setOpaque(
                false
        );

        barcodePanel.add(
                barcodeField,
                BorderLayout.CENTER
        );

        barcodePanel.add(
                findProductButton,
                BorderLayout.EAST
        );

        // ==================================================
        // ALIGNED FORM
        // ==================================================

        JPanel formPanel =
                new JPanel(
                        new GridBagLayout()
                );

        formPanel.setOpaque(
                false
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(
                        10,
                        10,
                        10,
                        10
                );

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        gbc.anchor =
                GridBagConstraints.WEST;

        // ------------------------------
        // ROW 0
        // ------------------------------

        addFormRow(
                formPanel,
                gbc,
                0,
                0,
                "Customer ID:",
                customerIdField
        );

        addFormRow(
                formPanel,
                gbc,
                0,
                2,
                "Unit Price:",
                unitPriceField
        );

        // ------------------------------
        // ROW 1
        // ------------------------------

        addFormRow(
                formPanel,
                gbc,
                1,
                0,
                "Barcode:",
                barcodePanel
        );

        addFormRow(
                formPanel,
                gbc,
                1,
                2,
                "Total:",
                totalLabel
        );

        // ------------------------------
        // ROW 2
        // ------------------------------

        addFormRow(
                formPanel,
                gbc,
                2,
                0,
                "Product:",
                productNameField
        );

        addFormRow(
                formPanel,
                gbc,
                2,
                2,
                "Amount Given:",
                amountGivenField
        );

        // ------------------------------
        // ROW 3
        // ------------------------------

        addFormRow(
                formPanel,
                gbc,
                3,
                0,
                "Available Stock:",
                availableStockField
        );

        addFormRow(
                formPanel,
                gbc,
                3,
                2,
                "Balance:",
                balanceLabel
        );

        // ------------------------------
        // ROW 4
        // ------------------------------

        addFormRow(
                formPanel,
                gbc,
                4,
                0,
                "Quantity:",
                quantityField
        );

        addFormRow(
                formPanel,
                gbc,
                4,
                2,
                "Payment Method:",
                paymentMethodBox
        );

        formCard.add(
                formPanel,
                BorderLayout.CENTER
        );

        // ==================================================
        // BUTTONS
        // ==================================================

        JButton calculateButton =
                createButton(
                        "Calculate",
                        new Color(
                                254,
                                243,
                                199
                        ),
                        new Color(
                                146,
                                64,
                                14
                        )
                );

        JButton confirmButton =
                createButton(
                        "Confirm Sale",
                        new Color(
                                220,
                                245,
                                228
                        ),
                        new Color(
                                34,
                                100,
                                58
                        )
                );

        JButton cancelButton =
                createButton(
                        "Cancel Sale",
                        new Color(
                                254,
                                226,
                                226
                        ),
                        new Color(
                                153,
                                27,
                                27
                        )
                );

        JButton receiptButton =
                createButton(
                        "Receipt",
                        new Color(
                                237,
                                233,
                                254
                        ),
                        new Color(
                                91,
                                33,
                                182
                        )
                );

        JButton clearButton =
                createButton(
                        "Clear",
                        new Color(
                                243,
                                244,
                                246
                        ),
                        new Color(
                                55,
                                65,
                                81
                        )
                );

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                12,
                                5
                        )
                );

        buttonPanel.setOpaque(
                false
        );

        buttonPanel.add(
                calculateButton
        );

        buttonPanel.add(
                confirmButton
        );

        buttonPanel.add(
                cancelButton
        );

        buttonPanel.add(
                receiptButton
        );

        buttonPanel.add(
                clearButton
        );

        formCard.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        add(
                formCard,
                BorderLayout.CENTER
        );

        // ==================================================
        // ACTIONS
        // ==================================================

        findProductButton.addActionListener(
                e -> findProductByBarcode()
        );

        /*
         * Barcode scanners normally send ENTER
         * after scanning the barcode.
         */
        barcodeField.addActionListener(
                e -> findProductByBarcode()
        );

        calculateButton.addActionListener(
                e -> calculateTotal()
        );

        confirmButton.addActionListener(
                e -> confirmSale()
        );

        cancelButton.addActionListener(
                e -> cancelSale()
        );

        receiptButton.addActionListener(
                e -> showReceipt()
        );

        clearButton.addActionListener(
                e -> clearSaleForm()
        );

        quantityField.addActionListener(
                e -> calculateTotal()
        );

        amountGivenField.addActionListener(
                e -> calculateTotal()
        );
    }

    // ==================================================
    // ADD FORM ROW
    // ==================================================

    private void addFormRow(
            JPanel panel,
            GridBagConstraints gbc,
            int row,
            int startingColumn,
            String labelText,
            Component component
    ) {

        // Label
        gbc.gridx =
                startingColumn;

        gbc.gridy =
                row;

        gbc.weightx =
                0;

        gbc.fill =
                GridBagConstraints.NONE;

        JLabel label =
                new JLabel(
                        labelText
                );

        label.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        12
                )
        );

        label.setPreferredSize(
                new Dimension(
                        120,
                        30
                )
        );

        panel.add(
                label,
                gbc
        );

        // Field / Component
        gbc.gridx =
                startingColumn + 1;

        gbc.weightx =
                1;

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        if (
                component
                        instanceof JComponent
        ) {

            ((JComponent) component)
                    .setPreferredSize(
                            new Dimension(
                                    270,
                                    34
                            )
                    );
        }

        panel.add(
                component,
                gbc
        );
    }

    // ==================================================
    // READ ONLY FIELD
    // ==================================================

    private JTextField createReadOnlyField() {

        JTextField field =
                new JTextField();

        field.setEditable(
                false
        );

        field.setBackground(
                READ_ONLY_BACKGROUND
        );

        return field;
    }

    // ==================================================
    // FIND PRODUCT BY BARCODE
    // ==================================================

    private void findProductByBarcode() {

        String barcode =
                barcodeField
                        .getText()
                        .trim();

        if (
                barcode.isEmpty()
        ) {

            message(
                    "Please enter or scan a barcode."
            );

            barcodeField.requestFocus();

            return;
        }

        Product product =
                productDAO.findByBarcode(
                        barcode
                );

        if (
                product == null
        ) {

            selectedProduct =
                    null;

            productNameField
                    .setText("");

            availableStockField
                    .setText("");

            unitPriceField
                    .setText("");

            quantityField
                    .setText("");

            totalLabel
                    .setText(
                            "0.00"
                    );

            message(
                    "No product found for barcode: "
                            + barcode
            );

            barcodeField.selectAll();
            barcodeField.requestFocus();

            return;
        }

        if (
                !"ACTIVE"
                        .equalsIgnoreCase(
                                product.getStatus()
                        )
        ) {

            selectedProduct =
                    null;

            message(
                    "This product is currently inactive."
            );

            return;
        }

        selectedProduct =
                product;

        productNameField.setText(
                product.getProductName()
        );

        availableStockField.setText(
                String.valueOf(
                        product.getStockQuantity()
                )
        );

        if (
                product.getUnitPrice()
                        != null
        ) {

            unitPriceField.setText(
                    product.getUnitPrice()
                            .toPlainString()
            );

        } else {

            unitPriceField.setText(
                    "0.00"
            );
        }

        quantityField.setText(
                "1"
        );

        calculateTotal();

        quantityField.requestFocus();
        quantityField.selectAll();
    }

    // ==================================================
    // CALCULATE TOTAL
    // ==================================================

    private void calculateTotal() {

        if (
                selectedProduct
                        == null
        ) {

            totalLabel.setText(
                    "0.00"
            );

            balanceLabel.setText(
                    "0.00"
            );

            return;
        }

        try {

            int quantity =
                    Integer.parseInt(
                            quantityField
                                    .getText()
                                    .trim()
                    );

            if (
                    quantity <= 0
            ) {

                message(
                        "Quantity must be greater than zero."
                );

                return;
            }

            if (
                    quantity
                            > selectedProduct
                            .getStockQuantity()
            ) {

                message(
                        "Insufficient stock.\n"
                                + "Available stock: "
                                + selectedProduct
                                .getStockQuantity()
                );

                return;
            }

            BigDecimal unitPrice =
                    selectedProduct
                            .getUnitPrice();

            if (
                    unitPrice == null
            ) {

                message(
                        "Product does not have a selling price."
                );

                return;
            }

            BigDecimal total =
                    unitPrice.multiply(
                            BigDecimal.valueOf(
                                    quantity
                            )
                    );

            totalLabel.setText(
                    total.toPlainString()
            );

            String amountText =
                    amountGivenField
                            .getText()
                            .trim();

            if (
                    !amountText.isEmpty()
            ) {

                BigDecimal amountGiven =
                        new BigDecimal(
                                amountText
                        );

                BigDecimal balance =
                        amountGiven.subtract(
                                total
                        );

                balanceLabel.setText(
                        balance.toPlainString()
                );

            } else {

                balanceLabel.setText(
                        "0.00"
                );
            }

        } catch (
                NumberFormatException e
        ) {

            totalLabel.setText(
                    "0.00"
            );

            balanceLabel.setText(
                    "0.00"
            );

            message(
                    "Please enter a valid quantity and amount."
            );
        }
    }

    // ==================================================
    // CONFIRM SALE
    // ==================================================

    private void confirmSale() {

        if (
                selectedProduct
                        == null
        ) {

            message(
                    "Please scan or find a product first."
            );

            barcodeField.requestFocus();

            return;
        }

        try {

            // ==================================================
            // QUANTITY
            // ==================================================

            int quantity =
                    Integer.parseInt(
                            quantityField
                                    .getText()
                                    .trim()
                    );

            if (
                    quantity <= 0
            ) {

                message(
                        "Quantity must be greater than zero."
                );

                return;
            }

            if (
                    quantity
                            > selectedProduct
                            .getStockQuantity()
            ) {

                message(
                        "Insufficient stock.\n"
                                + "Available stock: "
                                + selectedProduct
                                .getStockQuantity()
                );

                return;
            }

            // ==================================================
            // PRICE
            // ==================================================

            BigDecimal unitPrice =
                    selectedProduct
                            .getUnitPrice();

            if (
                    unitPrice == null
            ) {

                message(
                        "Product does not have a selling price."
                );

                return;
            }

            BigDecimal total =
                    unitPrice.multiply(
                            BigDecimal.valueOf(
                                    quantity
                            )
                    );

            // ==================================================
            // AMOUNT GIVEN
            // ==================================================

            String amountText =
                    amountGivenField
                            .getText()
                            .trim();

            if (
                    amountText.isEmpty()
            ) {

                message(
                        "Please enter the amount given."
                );

                return;
            }

            BigDecimal amountGiven =
                    new BigDecimal(
                            amountText
                    );

            if (
                    amountGiven.compareTo(
                            BigDecimal.ZERO
                    ) < 0
            ) {

                message(
                        "Amount given cannot be negative."
                );

                return;
            }

            // ==================================================
            // CUSTOMER
            // ==================================================

            String customerText =
                    customerIdField
                            .getText()
                            .trim();

            Integer customerId =
                    null;

            if (
                    !customerText.isEmpty()
            ) {

                customerId =
                        Integer.parseInt(
                                customerText
                        );
            }

            // ==================================================
            // PAYMENT STATUS
            // ==================================================

            String paymentStatus;

            if (
                    amountGiven.compareTo(
                            BigDecimal.ZERO
                    ) <= 0
            ) {

                paymentStatus =
                        "PENDING";

            } else if (
                    amountGiven.compareTo(
                            total
                    ) >= 0
            ) {

                paymentStatus =
                        "PAID";

            } else {

                paymentStatus =
                        "PARTIAL";
            }

            BigDecimal balance =
                    amountGiven.subtract(
                            total
                    );

            String paymentMethod =
                    String.valueOf(
                            paymentMethodBox
                                    .getSelectedItem()
                    );

            int productId =
                    selectedProduct
                            .getProductId();

            // ==================================================
            // SALE OBJECT
            // ==================================================

            Sale sale =
                    new Sale(
                            0,
                            customerId,
                            loggedInUserId,
                            LocalDateTime.now(),
                            total,
                            amountGiven,
                            balance,
                            paymentMethod,
                            paymentStatus,
                            "CONFIRMED"
                    );

            // ==================================================
            // SALE ITEM
            // ==================================================

            SaleItem saleItem =
                    new SaleItem(
                            0,
                            0,
                            productId,
                            quantity,
                            unitPrice,
                            total
                    );

            // ==================================================
            // STOCK ADJUSTMENT
            // ==================================================

            InventoryAdjustment adjustment =
                    new InventoryAdjustment(
                            productId,
                            loggedInUserId,
                            -quantity,
                            "SALE"
                    );

            // ==================================================
            // DATABASE TRANSACTION
            // ==================================================

            try (
                    Connection connection =
                            DatabaseConnection
                                    .getConnection()
            ) {

                connection.setAutoCommit(
                        false
                );

                try {

                    // ------------------------------------------
                    // 1. SAVE SALE
                    // ------------------------------------------

                    int saleId =
                            saleDAO.createSale(
                                    sale,
                                    connection
                            );

                    if (
                            saleId <= 0
                    ) {

                        throw new SQLException(
                                "Sale could not be saved."
                        );
                    }

                    // ------------------------------------------
                    // 2. SAVE SALE ITEM
                    // ------------------------------------------

                    saleItem.setSaleId(
                            saleId
                    );

                    SaleItemDAO saleItemDAO =
                            new SaleItemDAO();

                    if (
                            !saleItemDAO
                                    .addSaleItem(
                                            saleItem,
                                            connection
                                    )
                    ) {

                        throw new SQLException(
                                "Sale item could not be saved."
                        );
                    }

                    // ------------------------------------------
                    // 3. REDUCE STOCK
                    // ------------------------------------------

                    InventoryAdjustmentDAO inventoryDAO =
                            new InventoryAdjustmentDAO();

                    if (
                            !inventoryDAO
                                    .adjustStock(
                                            adjustment,
                                            connection
                                    )
                    ) {

                        throw new SQLException(
                                "Insufficient stock or stock update failed."
                        );
                    }

                    // ------------------------------------------
                    // SUCCESS
                    // ------------------------------------------

                    connection.commit();

                    lastSaleId =
                            saleId;

                    JOptionPane.showMessageDialog(
                            this,
                            "Sale saved successfully!\n\n"
                                    + "Sale ID: "
                                    + saleId
                                    + "\nProduct: "
                                    + selectedProduct
                                    .getProductName()
                                    + "\nQuantity: "
                                    + quantity
                                    + "\nTotal: "
                                    + total
                                    + "\nPayment Status: "
                                    + paymentStatus,
                            "Sale Successful",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                    clearSaleForm();

                } catch (
                        SQLException e
                ) {

                    connection.rollback();

                    JOptionPane.showMessageDialog(
                            this,
                            "Sale failed. No changes were saved.\n\n"
                                    + e.getMessage(),
                            "Transaction Failed",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }

        } catch (
                NumberFormatException e
        ) {

            message(
                    "Please enter valid numeric values."
            );

        } catch (
                SQLException e
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Database error: "
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ==================================================
    // SHOW RECEIPT
    // ==================================================

    private void showReceipt() {

        if (
                lastSaleId <= 0
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please confirm a sale first.",
                    "Receipt",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        Sale sale;

        try {

            sale =
                    saleDAO.getSaleById(
                            lastSaleId
                    );

        } catch (
                Exception e
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load sale: "
                            + e.getMessage(),
                    "Receipt",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        if (
                sale == null
        ) {

            message(
                    "Sale not found."
            );

            return;
        }

        SaleItemDAO saleItemDAO =
                new SaleItemDAO();

        List<SaleItem> items =
                saleItemDAO.getSaleItems(
                        lastSaleId
                );

        StringBuilder receipt =
                new StringBuilder();

        receipt.append(
                "========== TEXTFLOW RECEIPT ==========\n"
        );

        receipt.append(
                "Sale ID: "
        ).append(
                sale.getSaleId()
        ).append(
                "\n"
        );

        receipt.append(
                "Customer ID: "
        ).append(
                sale.getCustomerId() == null
                        ? "Walk-in Customer"
                        : sale.getCustomerId()
        ).append(
                "\n"
        );

        receipt.append(
                "Date: "
        ).append(
                sale.getSaleDate()
        ).append(
                "\n"
        );

        receipt.append(
                "--------------------------------------\n"
        );

        receipt.append(
                "Product | Qty | Price | Subtotal\n"
        );

        receipt.append(
                "--------------------------------------\n"
        );

        for (
                SaleItem item
                : items
        ) {

            String productDisplay =
                    "Product "
                            + item.getProductId();

            for (
                    Product product
                    : productDAO.getAllProducts()
            ) {

                if (
                        product.getProductId()
                                == item.getProductId()
                ) {

                    productDisplay =
                            product.getProductName();

                    break;
                }
            }

            receipt.append(
                    productDisplay
            ).append(
                    " | "
            ).append(
                    item.getQuantity()
            ).append(
                    " | "
            ).append(
                    item.getUnitPrice()
            ).append(
                    " | "
            ).append(
                    item.getTotalPrice()
            ).append(
                    "\n"
            );
        }

        receipt.append(
                "--------------------------------------\n"
        );

        receipt.append(
                "Total: "
        ).append(
                sale.getTotalAmount()
        ).append(
                "\n"
        );

        receipt.append(
                "Amount Given: "
        ).append(
                sale.getAmountGiven()
        ).append(
                "\n"
        );

        receipt.append(
                "Balance: "
        ).append(
                sale.getBalance()
        ).append(
                "\n"
        );

        receipt.append(
                "Payment Method: "
        ).append(
                sale.getPaymentMethod()
        ).append(
                "\n"
        );

        receipt.append(
                "Payment Status: "
        ).append(
                sale.getPaymentStatus()
        ).append(
                "\n"
        );

        receipt.append(
                "Status: "
        ).append(
                sale.getStatus()
        ).append(
                "\n"
        );

        receipt.append(
                "======================================"
        );

        JTextArea receiptArea =
                new JTextArea(
                        receipt.toString()
                );

        receiptArea.setEditable(
                false
        );

        receiptArea.setFont(
                new Font(
                        Font.MONOSPACED,
                        Font.PLAIN,
                        13
                )
        );

        receiptArea.setRows(
                18
        );

        receiptArea.setColumns(
                45
        );

        JOptionPane.showMessageDialog(
                this,
                new JScrollPane(
                        receiptArea
                ),
                "TextFlow Receipt",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // ==================================================
    // CANCEL SALE
    // ==================================================

    private void cancelSale() {

        String saleIdText =
                JOptionPane.showInputDialog(
                        this,
                        "Enter Sale ID to cancel:"
                );

        if (
                saleIdText == null
        ) {

            return;
        }

        saleIdText =
                saleIdText.trim();

        if (
                saleIdText.isEmpty()
        ) {

            return;
        }

        try {

            int saleId =
                    Integer.parseInt(
                            saleIdText
                    );

            boolean cancelled =
                    saleDAO.cancelSale(
                            saleId
                    );

            if (
                    cancelled
            ) {

                JOptionPane.showMessageDialog(
                        this,
                        "Sale "
                                + saleId
                                + " cancelled successfully.",
                        "Sale Cancelled",
                        JOptionPane.INFORMATION_MESSAGE
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Sale could not be cancelled.\n"
                                + "Only PENDING sales can be cancelled.",
                        "Cancel Sale",
                        JOptionPane.WARNING_MESSAGE
                );
            }

        } catch (
                NumberFormatException e
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid Sale ID.",
                    "Invalid Sale ID",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ==================================================
    // CLEAR FORM
    // ==================================================

    private void clearSaleForm() {

        customerIdField
                .setText("");

        barcodeField
                .setText("");

        productNameField
                .setText("");

        availableStockField
                .setText("");

        quantityField
                .setText("");

        unitPriceField
                .setText("");

        amountGivenField
                .setText("");

        totalLabel
                .setText(
                        "0.00"
                );

        balanceLabel
                .setText(
                        "0.00"
                );

        paymentMethodBox
                .setSelectedItem(
                        "CASH"
                );

        selectedProduct =
                null;

        barcodeField
                .requestFocus();
    }

    // ==================================================
    // BUTTON STYLE
    // ==================================================

    private JButton createButton(
            String text,
            Color backgroundColor,
            Color textColor
    ) {

        JButton button =
                new JButton(
                        text
                );

        button.setBackground(
                backgroundColor
        );

        button.setForeground(
                textColor
        );

        button.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        12
                )
        );

        button.setFocusPainted(
                false
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        button.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                backgroundColor.darker()
                        ),
                        BorderFactory.createEmptyBorder(
                                8,
                                15,
                                8,
                                15
                        )
                )
        );

        return button;
    }

    // ==================================================
    // MESSAGE
    // ==================================================

    private void message(
            String text
    ) {

        JOptionPane.showMessageDialog(
                this,
                text
        );
    }
}