package lk.textflow.supplier.view;

import lk.textflow.supplier.dao.PurchaseDAO;
import lk.textflow.supplier.dao.SupplierDAO;
import lk.textflow.supplier.model.Purchase;
import lk.textflow.supplier.model.PurchaseItem;
import lk.textflow.supplier.model.Supplier;

import javax.swing.*;
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

    private final int userId;

    public PurchaseManagementFrame() {

        // Temporary standalone test user
        this(1);
    }

    public PurchaseManagementFrame(
            int userId
    ) {

        this.userId =
                userId;

        purchaseDAO =
                new PurchaseDAO();

        supplierDAO =
                new SupplierDAO();

        setTitle(
                "TextFlow - Purchase Management"
        );

        setSize(
                1150,
                750
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

    private void createUI() {

        JPanel mainPanel =
                new JPanel(
                        new BorderLayout(
                                10,
                                10
                        )
                );

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        15,
                        15,
                        15,
                        15
                )
        );

        JLabel titleLabel =
                new JLabel(
                        "Purchase Management"
                );

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24
                )
        );

        mainPanel.add(
                titleLabel,
                BorderLayout.NORTH
        );

        // ==================================================
        // CREATE PURCHASE PANEL
        // ==================================================

        JPanel createPanel =
                new JPanel(
                        new BorderLayout(
                                10,
                                10
                        )
                );

        createPanel.setBorder(
                BorderFactory.createTitledBorder(
                        "Create Purchase"
                )
        );

        JPanel fields =
                new JPanel(
                        new GridLayout(
                                4,
                                2,
                                10,
                                10
                        )
                );

        supplierComboBox =
                new JComboBox<>();

        productComboBox =
                new JComboBox<>();

        quantityField =
                new JTextField();

        unitCostField =
                new JTextField();

        fields.add(
                new JLabel(
                        "Supplier:"
                )
        );

        fields.add(
                supplierComboBox
        );

        fields.add(
                new JLabel(
                        "Product:"
                )
        );

        fields.add(
                productComboBox
        );

        fields.add(
                new JLabel(
                        "Quantity:"
                )
        );

        fields.add(
                quantityField
        );

        fields.add(
                new JLabel(
                        "Unit Cost:"
                )
        );

        fields.add(
                unitCostField
        );

        createPanel.add(
                fields,
                BorderLayout.NORTH
        );

        // ==================================================
        // ITEM TABLE
        // ==================================================

        itemTableModel =
                new DefaultTableModel(
                        new String[]{
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
                new JTable(
                        itemTableModel
                );

        itemTable.setRowHeight(
                25
        );

        createPanel.add(
                new JScrollPane(
                        itemTable
                ),
                BorderLayout.CENTER
        );

        JButton addItemButton =
                new JButton(
                        "Add Item"
                );

        JButton removeItemButton =
                new JButton(
                        "Remove Item"
                );

        JButton savePurchaseButton =
                new JButton(
                        "Save Purchase"
                );

        JButton clearButton =
                new JButton(
                        "Clear"
                );

        totalField =
                new JTextField(
                        "0.00",
                        10
                );

        totalField.setEditable(
                false
        );

        JPanel itemButtonPanel =
                new JPanel();

        itemButtonPanel.add(
                addItemButton
        );

        itemButtonPanel.add(
                removeItemButton
        );

        itemButtonPanel.add(
                new JLabel(
                        "Total:"
                )
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

        createPanel.add(
                itemButtonPanel,
                BorderLayout.SOUTH
        );

        // ==================================================
        // PURCHASE HISTORY
        // ==================================================

        JPanel historyPanel =
                new JPanel(
                        new BorderLayout(
                                10,
                                10
                        )
                );

        historyPanel.setBorder(
                BorderFactory.createTitledBorder(
                        "Purchase History"
                )
        );

        purchaseTableModel =
                new DefaultTableModel(
                        new String[]{
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
                new JTable(
                        purchaseTableModel
                );

        purchaseTable.setRowHeight(
                25
        );

        purchaseTable.setSelectionMode(
                ListSelectionModel
                        .SINGLE_SELECTION
        );

        historyPanel.add(
                new JScrollPane(
                        purchaseTable
                ),
                BorderLayout.CENTER
        );

        JButton confirmButton =
                new JButton(
                        "Confirm Purchase"
                );

        JButton cancelButton =
                new JButton(
                        "Cancel Purchase"
                );

        JButton refreshButton =
                new JButton(
                        "Refresh"
                );

        JButton supplierButton =
                new JButton(
                        "Suppliers"
                );

        JButton closeButton =
                new JButton(
                        "Close"
                );

        JPanel historyButtons =
                new JPanel();

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

        historyPanel.add(
                historyButtons,
                BorderLayout.SOUTH
        );

        JSplitPane splitPane =
                new JSplitPane(
                        JSplitPane.VERTICAL_SPLIT,
                        createPanel,
                        historyPanel
                );

        splitPane.setResizeWeight(
                0.50
        );

        mainPanel.add(
                splitPane,
                BorderLayout.CENTER
        );

        add(
                mainPanel
        );

        // ==================================================
        // ACTIONS
        // ==================================================

        productComboBox
                .addActionListener(
                        e -> loadSelectedProductCost()
                );

        addItemButton
                .addActionListener(
                        e -> addItem()
                );

        removeItemButton
                .addActionListener(
                        e -> removeItem()
                );

        savePurchaseButton
                .addActionListener(
                        e -> savePurchase()
                );

        clearButton
                .addActionListener(
                        e -> clearPurchase()
                );

        confirmButton
                .addActionListener(
                        e -> confirmPurchase()
                );

        cancelButton
                .addActionListener(
                        e -> cancelPurchase()
                );

        refreshButton
                .addActionListener(
                        e -> {

                            loadSuppliers();
                            loadProducts();
                            loadPurchases();
                        }
                );

        supplierButton
                .addActionListener(
                        e -> new SupplierManagementFrame()
                                .setVisible(true)
                );

        closeButton
                .addActionListener(
                        e -> dispose()
                );
    }

    // ==================================================
    // LOAD SUPPLIERS
    // ==================================================

    private void loadSuppliers() {

        supplierComboBox
                .removeAllItems();

        List<Supplier> suppliers =
                supplierDAO
                        .getActiveSuppliers();

        for (
                Supplier supplier
                : suppliers
        ) {

            supplierComboBox.addItem(
                    supplier.getSupplierId()
                            + " - "
                            + supplier.getSupplierName()
            );
        }
    }

    // ==================================================
    // LOAD PRODUCTS
    // ==================================================

    private void loadProducts() {

        productComboBox
                .removeAllItems();

        List<String[]> products =
                purchaseDAO
                        .getActiveProducts();

        for (
                String[] product
                : products
        ) {

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

    // ==================================================
    // ADD ITEM
    // ==================================================

    private void addItem() {

        if (
                productComboBox
                        .getSelectedItem()
                        == null
        ) {

            message(
                    "Please select a product."
            );

            return;
        }

        String quantityText =
                quantityField
                        .getText()
                        .trim();

        String costText =
                unitCostField
                        .getText()
                        .trim();

        int quantity;

        BigDecimal unitCost;

        try {

            quantity =
                    Integer.parseInt(
                            quantityText
                    );

            unitCost =
                    new BigDecimal(
                            costText
                    );

        } catch (Exception e) {

            message(
                    "Enter a valid quantity and unit cost."
            );

            return;
        }

        if (quantity <= 0) {

            message(
                    "Quantity must be greater than zero."
            );

            return;
        }

        if (
                unitCost.compareTo(
                        BigDecimal.ZERO
                ) < 0
        ) {

            message(
                    "Unit cost cannot be negative."
            );

            return;
        }

        String selectedProduct =
                productComboBox
                        .getSelectedItem()
                        .toString();

        String idPart =
                selectedProduct
                        .split(
                                " - ",
                                2
                        )[0];

        int productId =
                Integer.parseInt(
                        idPart
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

        // Prevent duplicate product rows
        for (
                int i = 0;
                i < itemTableModel
                        .getRowCount();
                i++
        ) {

            int existingProductId =
                    Integer.parseInt(
                            itemTableModel
                                    .getValueAt(
                                            i,
                                            0
                                    )
                                    .toString()
                    );

            if (
                    existingProductId
                            == productId
            ) {

                message(
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

    // ==================================================
    // REMOVE ITEM
    // ==================================================

    private void removeItem() {

        int row =
                itemTable
                        .getSelectedRow();

        if (row == -1) {

            message(
                    "Please select an item."
            );

            return;
        }

        itemTableModel.removeRow(
                row
        );

        calculateTotal();
    }

    private void calculateTotal() {

        BigDecimal total =
                BigDecimal.ZERO;

        for (
                int i = 0;
                i < itemTableModel
                        .getRowCount();
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

    // ==================================================
    // SAVE PURCHASE
    // ==================================================

    private void savePurchase() {

        if (
                supplierComboBox
                        .getSelectedItem()
                        == null
        ) {

            message(
                    "Please select a supplier."
            );

            return;
        }

        if (
                itemTableModel
                        .getRowCount()
                        == 0
        ) {

            message(
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

        purchase.setUserId(
                userId
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
                i < itemTableModel
                        .getRowCount();
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

            message(
                    "Purchase saved as PENDING."
            );

            clearPurchase();
            loadPurchases();

        } else {

            message(
                    "Purchase could not be saved."
            );
        }
    }

    // ==================================================
    // PURCHASE HISTORY
    // ==================================================

    private void loadPurchases() {

        purchaseTableModel
                .setRowCount(0);

        List<String[]> purchases =
                purchaseDAO
                        .getAllPurchases();

        for (
                String[] purchase
                : purchases
        ) {

            purchaseTableModel.addRow(
                    purchase
            );
        }
    }

    // ==================================================
    // CONFIRM
    // ==================================================

    private void confirmPurchase() {

        int row =
                purchaseTable
                        .getSelectedRow();

        if (row == -1) {

            message(
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

            message(
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
                JOptionPane
                        .showConfirmDialog(
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

            message(
                    "Purchase confirmed and inventory updated."
            );

            loadPurchases();
            loadProducts();

        } else {

            message(
                    "Purchase could not be confirmed."
            );
        }
    }

    // ==================================================
    // CANCEL
    // ==================================================

    private void cancelPurchase() {

        int row =
                purchaseTable
                        .getSelectedRow();

        if (row == -1) {

            message(
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

            message(
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
                JOptionPane
                        .showConfirmDialog(
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

            message(
                    "Purchase cancelled."
            );

            loadPurchases();

        } else {

            message(
                    "Purchase could not be cancelled."
            );
        }
    }

    private void clearPurchase() {

        itemTableModel
                .setRowCount(0);

        quantityField
                .setText("");

        totalField
                .setText(
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
    }

    private void message(
            String text
    ) {

        JOptionPane.showMessageDialog(
                this,
                text
        );
    }

    public static void main(
            String[] args
    ) {

        SwingUtilities.invokeLater(
                () -> new PurchaseManagementFrame()
                        .setVisible(true)
        );
    }
}