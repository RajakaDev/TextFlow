package lk.textflow.supplier.view;

import lk.textflow.supplier.dao.PurchaseDAO;
import lk.textflow.supplier.dao.SupplierDAO;
import lk.textflow.supplier.model.Purchase;
import lk.textflow.supplier.model.PurchaseItem;
import lk.textflow.supplier.model.Supplier;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PurchaseManagementFrame extends JFrame {

    private JComboBox<String> supplierComboBox;
    private JComboBox<String> productComboBox;

    private JTextField quantityField;
    private JTextField unitCostField;
    private JTextField totalField;

    private JTable itemTable;
    private JTable purchaseTable;

    private DefaultTableModel itemTableModel;
    private DefaultTableModel purchaseTableModel;

    private final PurchaseDAO purchaseDAO;
    private final SupplierDAO supplierDAO;

    private final int loggedInUserId;

    // ==============================
    // COLORS
    // ==============================

    private final Color DARK_BLUE = new Color(31, 60, 136);
    private final Color LIGHT_BACKGROUND = new Color(245, 247, 250);
    private final Color CARD_BORDER = new Color(220, 225, 230);

    // ==============================
    // CONSTRUCTOR
    // ==============================

    public PurchaseManagementFrame(
            int loggedInUserId
    ) {

        this.loggedInUserId =
                loggedInUserId;

        purchaseDAO =
                new PurchaseDAO();

        supplierDAO =
                new SupplierDAO();

        setTitle(
                "TextFlow - Purchase Management"
        );

        setSize(
                1200,
                780
        );

        setMinimumSize(
                new Dimension(
                        1000,
                        680
                )
        );

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setLocationRelativeTo(null);

        createUI();

        loadSuppliers();
        loadProducts();
        loadPurchases();
    }

    // ==============================
    // CREATE UI
    // ==============================

    private void createUI() {

        JPanel rootPanel =
                new JPanel(
                        new BorderLayout()
                );

        rootPanel.setBackground(
                LIGHT_BACKGROUND
        );

        // ==============================
        // HEADER
        // ==============================

        JPanel headerPanel =
                new JPanel(
                        new BorderLayout()
                );

        headerPanel.setBackground(
                DARK_BLUE
        );

        headerPanel.setBorder(
                new EmptyBorder(
                        17, 25, 17, 25
                )
        );

        JLabel titleLabel =
                new JLabel(
                        "TEXTFLOW  |  Purchase Management"
                );

        titleLabel.setForeground(
                Color.WHITE
        );

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        23
                )
        );

        JLabel userLabel =
                new JLabel(
                        "User ID: "
                                + loggedInUserId
                );

        userLabel.setForeground(
                new Color(
                        220, 230, 245
                )
        );

        userLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        12
                )
        );

        headerPanel.add(
                titleLabel,
                BorderLayout.WEST
        );

        headerPanel.add(
                userLabel,
                BorderLayout.EAST
        );

        rootPanel.add(
                headerPanel,
                BorderLayout.NORTH
        );

        // ==============================
        // CONTENT
        // ==============================

        JPanel contentPanel =
                new JPanel(
                        new BorderLayout(
                                15,
                                15
                        )
                );

        contentPanel.setBackground(
                LIGHT_BACKGROUND
        );

        contentPanel.setBorder(
                new EmptyBorder(
                        20, 20, 20, 20
                )
        );

        // ==============================
        // CREATE PURCHASE CARD
        // ==============================

        JPanel createCard =
                new JPanel(
                        new BorderLayout(
                                10,
                                12
                        )
                );

        createCard.setBackground(
                Color.WHITE
        );

        createCard.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                CARD_BORDER
                        ),
                        new EmptyBorder(
                                15, 15, 15, 15
                        )
                )
        );

        JLabel createTitle =
                new JLabel(
                        "Create Purchase"
                );

        createTitle.setForeground(
                DARK_BLUE
        );

        createTitle.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        17
                )
        );

        createCard.add(
                createTitle,
                BorderLayout.NORTH
        );

        // ==============================
        // FIELDS
        // ==============================

        supplierComboBox =
                new JComboBox<>();

        productComboBox =
                new JComboBox<>();

        quantityField =
                new JTextField();

        unitCostField =
                new JTextField();

        JPanel fieldsPanel =
                new JPanel(
                        new GridBagLayout()
                );

        fieldsPanel.setOpaque(false);

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(
                        6, 8, 6, 8
                );

        gbc.anchor =
                GridBagConstraints.WEST;

        addFormRow(
                fieldsPanel,
                gbc,
                0,
                0,
                "Supplier:",
                supplierComboBox
        );

        addFormRow(
                fieldsPanel,
                gbc,
                0,
                2,
                "Product:",
                productComboBox
        );

        addFormRow(
                fieldsPanel,
                gbc,
                1,
                0,
                "Quantity:",
                quantityField
        );

        addFormRow(
                fieldsPanel,
                gbc,
                1,
                2,
                "Unit Cost:",
                unitCostField
        );

        createCard.add(
                fieldsPanel,
                BorderLayout.CENTER
        );

        // ==============================
        // ITEM TABLE
        // ==============================

        itemTableModel =
                new DefaultTableModel(
                        new Object[]{
                                "Product ID",
                                "Product",
                                "Quantity",
                                "Unit Cost",
                                "Total"
                        },
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column
                    ) {
                        return false;
                    }
                };

        itemTable =
                createStyledTable(
                        itemTableModel
                );

        JScrollPane itemScrollPane =
                new JScrollPane(
                        itemTable
                );

        itemScrollPane.setPreferredSize(
                new Dimension(
                        0,
                        150
                )
        );

        itemScrollPane.setBorder(
                BorderFactory.createLineBorder(
                        CARD_BORDER
                )
        );

        JPanel itemArea =
                new JPanel(
                        new BorderLayout(
                                5,
                                5
                        )
                );

        itemArea.setOpaque(false);

        itemArea.add(
                fieldsPanel,
                BorderLayout.NORTH
        );

        itemArea.add(
                itemScrollPane,
                BorderLayout.CENTER
        );

        createCard.add(
                itemArea,
                BorderLayout.CENTER
        );

        // ==============================
        // ITEM BUTTONS
        // ==============================

        JButton addItemButton =
                createButton(
                        "Add Item",
                        new Color(220, 245, 228),
                        new Color(34, 100, 58)
                );

        JButton removeItemButton =
                createButton(
                        "Remove Item",
                        new Color(254, 226, 226),
                        new Color(153, 27, 27)
                );

        JButton savePurchaseButton =
                createButton(
                        "Save Purchase",
                        new Color(219, 234, 254),
                        new Color(30, 64, 175)
                );

        JButton clearButton =
                createButton(
                        "Clear",
                        new Color(243, 244, 246),
                        new Color(55, 65, 81)
                );

        totalField =
                new JTextField(
                        "0.00",
                        10
                );

        totalField.setEditable(false);

        totalField.setHorizontalAlignment(
                JTextField.RIGHT
        );

        totalField.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        JPanel itemButtonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                10,
                                4
                        )
                );

        itemButtonPanel.setOpaque(false);

        JLabel totalLabel =
                new JLabel("Total:");

        totalLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        13
                )
        );

        itemButtonPanel.add(
                addItemButton
        );

        itemButtonPanel.add(
                removeItemButton
        );

        itemButtonPanel.add(
                totalLabel
        );

        itemButtonPanel.add(
                totalField
        );

        itemButtonPanel.add(
                savePurchaseButton
        );

        itemButtonPanel.add(
                clearButton
        );

        createCard.add(
                itemButtonPanel,
                BorderLayout.SOUTH
        );

        // ==============================
        // HISTORY CARD
        // ==============================

        JPanel historyCard =
                new JPanel(
                        new BorderLayout(
                                10,
                                10
                        )
                );

        historyCard.setBackground(
                Color.WHITE
        );

        historyCard.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                CARD_BORDER
                        ),
                        new EmptyBorder(
                                15, 15, 15, 15
                        )
                )
        );

        JLabel historyTitle =
                new JLabel(
                        "Purchase History"
                );

        historyTitle.setForeground(
                DARK_BLUE
        );

        historyTitle.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        17
                )
        );

        historyCard.add(
                historyTitle,
                BorderLayout.NORTH
        );

        purchaseTableModel =
                new DefaultTableModel(
                        new Object[]{
                                "Purchase ID",
                                "Supplier",
                                "User ID",
                                "Date",
                                "Total",
                                "Status"
                        },
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column
                    ) {
                        return false;
                    }
                };

        purchaseTable =
                createStyledTable(
                        purchaseTableModel
                );

        purchaseTable.setSelectionMode(
                ListSelectionModel
                        .SINGLE_SELECTION
        );

        JScrollPane historyScrollPane =
                new JScrollPane(
                        purchaseTable
                );

        historyScrollPane.setBorder(
                BorderFactory.createLineBorder(
                        CARD_BORDER
                )
        );

        historyCard.add(
                historyScrollPane,
                BorderLayout.CENTER
        );

        // ==============================
        // HISTORY BUTTONS
        // ==============================

        JButton confirmButton =
                createButton(
                        "Confirm Purchase",
                        new Color(220, 245, 228),
                        new Color(34, 100, 58)
                );

        JButton cancelButton =
                createButton(
                        "Cancel Purchase",
                        new Color(254, 226, 226),
                        new Color(153, 27, 27)
                );

        JButton refreshButton =
                createButton(
                        "Refresh",
                        new Color(204, 251, 241),
                        new Color(17, 94, 89)
                );

        JButton supplierButton =
                createButton(
                        "Suppliers",
                        new Color(237, 233, 254),
                        new Color(91, 33, 182)
                );

        JButton closeButton =
                createButton(
                        "Close",
                        new Color(243, 244, 246),
                        new Color(55, 65, 81)
                );

        JPanel historyButtons =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                10,
                                4
                        )
                );

        historyButtons.setOpaque(false);

        historyButtons.add(
                confirmButton
        );

        historyButtons.add(
                cancelButton
        );

        historyButtons.add(
                refreshButton
        );

        historyButtons.add(
                supplierButton
        );

        historyButtons.add(
                closeButton
        );

        historyCard.add(
                historyButtons,
                BorderLayout.SOUTH
        );

        // ==============================
        // SPLIT
        // ==============================

        JSplitPane splitPane =
                new JSplitPane(
                        JSplitPane.VERTICAL_SPLIT,
                        createCard,
                        historyCard
                );

        splitPane.setResizeWeight(
                0.52
        );

        splitPane.setBorder(null);

        splitPane.setBackground(
                LIGHT_BACKGROUND
        );

        contentPanel.add(
                splitPane,
                BorderLayout.CENTER
        );

        rootPanel.add(
                contentPanel,
                BorderLayout.CENTER
        );

        setContentPane(
                rootPanel
        );

        // ==============================
        // ACTIONS
        // ==============================

        productComboBox.addActionListener(
                e -> loadSelectedProductCost()
        );

        addItemButton.addActionListener(
                e -> addItem()
        );

        removeItemButton.addActionListener(
                e -> removeItem()
        );

        savePurchaseButton.addActionListener(
                e -> savePurchase()
        );

        clearButton.addActionListener(
                e -> clearPurchase()
        );

        confirmButton.addActionListener(
                e -> confirmPurchase()
        );

        cancelButton.addActionListener(
                e -> cancelPurchase()
        );

        refreshButton.addActionListener(
                e -> {
                    loadSuppliers();
                    loadProducts();
                    loadPurchases();
                }
        );

        supplierButton.addActionListener(
                e -> new SupplierManagementFrame(
                        loggedInUserId
                ).setVisible(true)
        );

        closeButton.addActionListener(
                e -> dispose()
        );
    }

    // ==============================
    // FORM ROW
    // ==============================

    private void addFormRow(
            JPanel panel,
            GridBagConstraints gbc,
            int row,
            int startColumn,
            String labelText,
            Component component
    ) {

        gbc.gridx = startColumn;
        gbc.gridy = row;

        gbc.weightx = 0;

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
                        90,
                        30
                )
        );

        panel.add(
                label,
                gbc
        );

        gbc.gridx =
                startColumn + 1;

        gbc.weightx = 1;

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        if (component instanceof JComponent) {

            ((JComponent) component)
                    .setPreferredSize(
                            new Dimension(
                                    330,
                                    34
                            )
                    );
        }

        panel.add(
                component,
                gbc
        );
    }

    // ==============================
    // LOAD SUPPLIERS
    // ==============================

    private void loadSuppliers() {

        supplierComboBox.removeAllItems();

        List<Supplier> suppliers =
                supplierDAO
                        .getActiveSuppliers();

        for (Supplier supplier : suppliers) {

            supplierComboBox.addItem(
                    supplier.getSupplierId()
                            + " - "
                            + supplier.getSupplierName()
            );
        }
    }

    // ==============================
    // LOAD PRODUCTS
    // ==============================

    private void loadProducts() {

        productComboBox.removeAllItems();

        List<String[]> products =
                purchaseDAO
                        .getActiveProducts();

        for (String[] product : products) {

            productComboBox.addItem(
                    product[0]
                            + " - "
                            + product[1]
                            + " | Stock: "
                            + product[2]
                            + " | Cost: "
                            + product[3]
            );
        }
    }

    private void loadSelectedProductCost() {

        Object selected =
                productComboBox
                        .getSelectedItem();

        if (selected == null) {
            return;
        }

        String text =
                selected.toString();

        int costPosition =
                text.lastIndexOf(
                        "Cost: "
                );

        if (costPosition != -1) {

            unitCostField.setText(
                    text.substring(
                            costPosition + 6
                    ).trim()
            );
        }
    }

    // ==============================
    // ADD ITEM
    // ==============================

    private void addItem() {

        if (
                productComboBox
                        .getSelectedItem()
                        == null
        ) {

            warning(
                    "Please select a product."
            );

            return;
        }

        int quantity;
        BigDecimal unitCost;

        try {

            quantity =
                    Integer.parseInt(
                            quantityField
                                    .getText()
                                    .trim()
                    );

            unitCost =
                    new BigDecimal(
                            unitCostField
                                    .getText()
                                    .trim()
                    );

        } catch (Exception e) {

            warning(
                    "Enter a valid quantity and unit cost."
            );

            return;
        }

        if (quantity <= 0) {

            warning(
                    "Quantity must be greater than zero."
            );

            return;
        }

        if (
                unitCost.compareTo(
                        BigDecimal.ZERO
                ) < 0
        ) {

            warning(
                    "Unit cost cannot be negative."
            );

            return;
        }

        String selectedProduct =
                productComboBox
                        .getSelectedItem()
                        .toString();

        int productId =
                Integer.parseInt(
                        selectedProduct
                                .split(
                                        " - ",
                                        2
                                )[0]
                );

        String productName =
                selectedProduct
                        .split(
                                " - ",
                                2
                        )[1]
                        .split(
                                " \\| ",
                                2
                        )[0];

        for (
                int i = 0;
                i < itemTableModel.getRowCount();
                i++
        ) {

            int existingId =
                    Integer.parseInt(
                            itemTableModel
                                    .getValueAt(
                                            i,
                                            0
                                    )
                                    .toString()
                    );

            if (
                    existingId
                            == productId
            ) {

                warning(
                        "This product is already in the purchase."
                );

                return;
            }
        }

        BigDecimal total =
                unitCost.multiply(
                        BigDecimal.valueOf(
                                quantity
                        )
                );

        itemTableModel.addRow(
                new Object[]{
                        productId,
                        productName,
                        quantity,
                        unitCost,
                        total
                }
        );

        quantityField.setText("");

        calculateTotal();
    }

    // ==============================
    // REMOVE ITEM
    // ==============================

    private void removeItem() {

        int row =
                itemTable
                        .getSelectedRow();

        if (row == -1) {

            warning(
                    "Please select an item."
            );

            return;
        }

        itemTableModel.removeRow(
                row
        );

        calculateTotal();
    }

    // ==============================
    // TOTAL
    // ==============================

    private void calculateTotal() {

        BigDecimal total =
                BigDecimal.ZERO;

        for (
                int i = 0;
                i < itemTableModel.getRowCount();
                i++
        ) {

            total =
                    total.add(
                            new BigDecimal(
                                    itemTableModel
                                            .getValueAt(
                                                    i,
                                                    4
                                            )
                                            .toString()
                            )
                    );
        }

        totalField.setText(
                total.toPlainString()
        );
    }

    // ==============================
    // SAVE PURCHASE
    // ==============================

    private void savePurchase() {

        if (
                supplierComboBox
                        .getSelectedItem()
                        == null
        ) {

            warning(
                    "Please select a supplier."
            );

            return;
        }

        if (
                itemTableModel
                        .getRowCount()
                        == 0
        ) {

            warning(
                    "Add at least one product."
            );

            return;
        }

        String supplierText =
                supplierComboBox
                        .getSelectedItem()
                        .toString();

        int supplierId =
                Integer.parseInt(
                        supplierText
                                .split(
                                        " - ",
                                        2
                                )[0]
                );

        Purchase purchase =
                new Purchase();

        purchase.setSupplierId(
                supplierId
        );

        // REAL LOGGED-IN USER
        purchase.setUserId(
                loggedInUserId
        );

        purchase.setTotalAmount(
                new BigDecimal(
                        totalField
                                .getText()
                )
        );

        purchase.setStatus(
                "PENDING"
        );

        List<PurchaseItem> items =
                new ArrayList<>();

        for (
                int i = 0;
                i < itemTableModel.getRowCount();
                i++
        ) {

            int productId =
                    Integer.parseInt(
                            itemTableModel
                                    .getValueAt(
                                            i,
                                            0
                                    )
                                    .toString()
                    );

            int quantity =
                    Integer.parseInt(
                            itemTableModel
                                    .getValueAt(
                                            i,
                                            2
                                    )
                                    .toString()
                    );

            BigDecimal unitCost =
                    new BigDecimal(
                            itemTableModel
                                    .getValueAt(
                                            i,
                                            3
                                    )
                                    .toString()
                    );

            items.add(
                    new PurchaseItem(
                            productId,
                            quantity,
                            unitCost
                    )
            );
        }

        boolean success =
                purchaseDAO
                        .createPurchase(
                                purchase,
                                items
                        );

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Purchase saved as PENDING.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            clearPurchase();
            loadPurchases();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Purchase could not be saved.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ==============================
    // LOAD PURCHASES
    // ==============================

    private void loadPurchases() {

        purchaseTableModel.setRowCount(
                0
        );

        List<String[]> purchases =
                purchaseDAO
                        .getAllPurchases();

        for (String[] purchase : purchases) {

            purchaseTableModel.addRow(
                    purchase
            );
        }
    }

    // ==============================
    // CONFIRM
    // ==============================

    private void confirmPurchase() {

        int row =
                purchaseTable
                        .getSelectedRow();

        if (row == -1) {

            warning(
                    "Please select a purchase."
            );

            return;
        }

        String status =
                purchaseTableModel
                        .getValueAt(
                                row,
                                5
                        )
                        .toString();

        if (
                !"PENDING"
                        .equalsIgnoreCase(
                                status
                        )
        ) {

            warning(
                    "Only PENDING purchases can be confirmed."
            );

            return;
        }

        int purchaseId =
                Integer.parseInt(
                        purchaseTableModel
                                .getValueAt(
                                        row,
                                        0
                                )
                                .toString()
                );

        int confirm =
                JOptionPane.showConfirmDialog(
                        this,
                        "Confirm this purchase?\n"
                                + "Product stock will be increased.",
                        "Confirm Purchase",
                        JOptionPane.YES_NO_OPTION
                );

        if (
                confirm
                        != JOptionPane.YES_OPTION
        ) {
            return;
        }

        if (
                purchaseDAO
                        .confirmPurchase(
                                purchaseId
                        )
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Purchase confirmed and inventory updated.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            loadPurchases();
            loadProducts();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Purchase could not be confirmed.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ==============================
    // CANCEL
    // ==============================

    private void cancelPurchase() {

        int row =
                purchaseTable
                        .getSelectedRow();

        if (row == -1) {

            warning(
                    "Please select a purchase."
            );

            return;
        }

        String status =
                purchaseTableModel
                        .getValueAt(
                                row,
                                5
                        )
                        .toString();

        if (
                !"PENDING"
                        .equalsIgnoreCase(
                                status
                        )
        ) {

            warning(
                    "Only PENDING purchases can be cancelled."
            );

            return;
        }

        int purchaseId =
                Integer.parseInt(
                        purchaseTableModel
                                .getValueAt(
                                        row,
                                        0
                                )
                                .toString()
                );

        int confirm =
                JOptionPane.showConfirmDialog(
                        this,
                        "Cancel this purchase?",
                        "Cancel Purchase",
                        JOptionPane.YES_NO_OPTION
                );

        if (
                confirm
                        != JOptionPane.YES_OPTION
        ) {
            return;
        }

        if (
                purchaseDAO
                        .cancelPurchase(
                                purchaseId
                        )
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Purchase cancelled.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            loadPurchases();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Purchase could not be cancelled.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ==============================
    // CLEAR
    // ==============================

    private void clearPurchase() {

        itemTableModel.setRowCount(
                0
        );

        quantityField.setText("");

        totalField.setText(
                "0.00"
        );

        if (
                productComboBox
                        .getItemCount()
                        > 0
        ) {

            productComboBox
                    .setSelectedIndex(
                            0
                    );
        }

        itemTable.clearSelection();
    }

    // ==============================
    // TABLE STYLE
    // ==============================

    private JTable createStyledTable(
            DefaultTableModel model
    ) {

        JTable table =
                new JTable(model);

        table.setRowHeight(28);

        table.setGridColor(
                new Color(
                        230, 233, 238
                )
        );

        table.setSelectionMode(
                ListSelectionModel
                        .SINGLE_SELECTION
        );

        table
                .getTableHeader()
                .setBackground(
                        DARK_BLUE
                );

        table
                .getTableHeader()
                .setForeground(
                        Color.WHITE
                );

        table
                .getTableHeader()
                .setFont(
                        new Font(
                                "Arial",
                                Font.BOLD,
                                12
                        )
                );

        table
                .getTableHeader()
                .setPreferredSize(
                        new Dimension(
                                0,
                                32
                        )
                );

        table
                .getTableHeader()
                .setReorderingAllowed(
                        false
                );

        return table;
    }

    // ==============================
    // BUTTON STYLE
    // ==============================

    private JButton createButton(
            String text,
            Color background,
            Color foreground
    ) {

        JButton button =
                new JButton(text);

        button.setBackground(
                background
        );

        button.setForeground(
                foreground
        );

        button.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        12
                )
        );

        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setContentAreaFilled(true);

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        button.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                background.darker()
                        ),
                        BorderFactory.createEmptyBorder(
                                8, 14, 8, 14
                        )
                )
        );

        return button;
    }

    private void warning(
            String message
    ) {

        JOptionPane.showMessageDialog(
                this,
                message,
                "Validation",
                JOptionPane.WARNING_MESSAGE
        );
    }
}