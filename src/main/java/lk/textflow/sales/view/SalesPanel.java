package lk.textflow.sales.view;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lk.textflow.sales.dao.SaleDAO;
import lk.textflow.sales.model.Sale;
import java.math.BigDecimal;
import lk.textflow.sales.model.SaleItem;
import lk.textflow.sales.dao.SaleItemDAO;
import lk.textflow.dao.ProductDAO;
import lk.textflow.dao.InventoryAdjustmentDAO;
import lk.textflow.model.InventoryAdjustment;
import lk.textflow.model.Product;
import lk.textflow.dao.InventoryAdjustmentDAO;
import lk.textflow.model.InventoryAdjustment;



public class SalesPanel extends JPanel {

    private JTextField customerIdField;
    private JTextField productIdField;
    private JTextField quantityField;
    private JTextField unitPriceField;
    private JTextField amountGivenField;
    private JLabel totalLabel;
    private JLabel balanceLabel;
    private JComboBox<String> paymentMethodBox;
    private SaleDAO saleDAO = new SaleDAO();
    private int lastSaleId = -1;





    public SalesPanel() {

        setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("Sales & Billing");
        title.setFont(new Font("Arial", Font.BOLD, 24));

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));

        formPanel.add(new JLabel("Customer ID:"));
        customerIdField = new JTextField();
        formPanel.add(customerIdField);

        formPanel.add(new JLabel("Product ID:"));
        productIdField = new JTextField();
        formPanel.add(productIdField);

        formPanel.add(new JLabel("Quantity:"));
        quantityField = new JTextField();
        formPanel.add(quantityField);

        formPanel.add(new JLabel("Unit Price:"));
        unitPriceField = new JTextField();
        formPanel.add(unitPriceField);

        formPanel.add(new JLabel("Total:"));
        totalLabel = new JLabel("0.00");
        formPanel.add(totalLabel);

        formPanel.add(new JLabel("Amount Given:"));
        amountGivenField = new JTextField();
        formPanel.add(amountGivenField);

        formPanel.add(new JLabel("Balance:"));
        balanceLabel = new JLabel("0.00");
        formPanel.add(balanceLabel);

        formPanel.add(new JLabel("Payment Method:"));

        paymentMethodBox = new JComboBox<>(
                new String[]{"CASH", "CARD", "BANK", "OTHER"}
        );

        formPanel.add(paymentMethodBox);

        JButton calculateButton = new JButton("Calculate Total");
        calculateButton.addActionListener(e -> calculateTotal());

        JButton confirmButton = new JButton("Confirm Sale");
        confirmButton.addActionListener(e -> confirmSale());

        JButton receiptButton = new JButton("Receipt");
        receiptButton.addActionListener(e -> showReceipt());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(calculateButton);
        buttonPanel.add(confirmButton);
        buttonPanel.add(receiptButton);

        add(title, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void confirmSale() {


        int productId = Integer.parseInt(productIdField.getText());
        ProductDAO productDAO = new ProductDAO();
        Product product = null;

        for (Product p : productDAO.getAllProducts()) {
            if (p.getProductId() == productId) {
                product = p;
                break;
            }
        }
        int quantity = Integer.parseInt(quantityField.getText());
        double unitPrice = Double.parseDouble(unitPriceField.getText());

        try {
            double total = Double.parseDouble(totalLabel.getText());
            double amountGiven = Double.parseDouble(amountGivenField.getText());

            String paymentStatus;

            if (amountGiven <= 0) {
                paymentStatus = "PENDING";
            } else if (amountGiven >= total) {
                paymentStatus = "PAID";
            } else {
                paymentStatus = "PARTIAL";
            }

            int customerId = Integer.parseInt(customerIdField.getText());
            int userId = 1;

            BigDecimal totalAmount = BigDecimal.valueOf(total);
            BigDecimal amountGivenBD = BigDecimal.valueOf(amountGiven);
            BigDecimal balanceBD = amountGivenBD.subtract(totalAmount);

            String paymentMethod =
                    (String) paymentMethodBox.getSelectedItem();

            Sale sale = new Sale(
                    0,
                    customerId,
                    userId,
                    LocalDateTime.now(),
                    totalAmount,
                    amountGivenBD,
                    balanceBD,
                    paymentMethod,
                    paymentStatus,
                    "CONFIRMED"
            );

            int saleId = saleDAO.createSale(sale);
            lastSaleId = saleId;

            if (saleId > 0) {

                InventoryAdjustment adjustment =
                        new InventoryAdjustment(
                                productId,
                                userId,
                                -quantity,
                                "SALE"
                        );

                InventoryAdjustmentDAO inventoryDAO =
                        new InventoryAdjustmentDAO();

                boolean stockUpdated =
                        inventoryDAO.adjustStock(adjustment);

                if (!stockUpdated) {
                    JOptionPane.showMessageDialog(this,
                            "Sale saved, but stock could not be updated.");
                    return;
                }

                SaleItem item = new SaleItem(
                        0,
                        saleId,
                        productId,
                        quantity,
                        BigDecimal.valueOf(unitPrice),
                        BigDecimal.valueOf(quantity * unitPrice)
                );

                SaleItemDAO saleItemDAO = new SaleItemDAO();
                saleItemDAO.addSaleItem(item);

            }

            if (saleId > 0) {
                JOptionPane.showMessageDialog(this,
                        "Sale saved!\nSale ID: " + saleId +
                                "\nPayment Status: " + paymentStatus);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Sale was NOT saved.\nPlease check the database error.");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Please enter valid numbers.");
        }
    }

    private void calculateTotal() {

        try {
            int quantity = Integer.parseInt(quantityField.getText());
            double unitPrice = Double.parseDouble(unitPriceField.getText());

            double total = quantity * unitPrice;

            totalLabel.setText(String.format("%.2f", total));

            if (!amountGivenField.getText().isEmpty()) {
                double amountGiven = Double.parseDouble(amountGivenField.getText());
                double balance = amountGiven - total;
                balanceLabel.setText(String.format("%.2f", balance));
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter valid quantity and unit price.");
        };
    }

    private void showReceipt() {

        if (lastSaleId <= 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please confirm a sale first.",
                    "Receipt",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        SaleDAO saleDAO = new SaleDAO();
        Sale sale = saleDAO.getSaleById(lastSaleId);

        if (sale == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Sale not found.",
                    "Receipt",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        String receipt =
                "====== TEXTFLOW RECEIPT ======\n" +
                        "Sale ID: " + sale.getSaleId() + "\n" +
                        "Customer ID: " + sale.getCustomerId() + "\n" +
                        "Date: " + sale.getSaleDate() + "\n" +
                        "Total: " + sale.getTotalAmount() + "\n" +
                        "Amount Given: " + sale.getAmountGiven() + "\n" +
                        "Balance: " + sale.getBalance() + "\n" +
                        "Payment Method: " + sale.getPaymentMethod() + "\n" +
                        "Payment Status: " + sale.getPaymentStatus() + "\n" +
                        "Status: " + sale.getStatus() + "\n" +
                        "==============================";

        JOptionPane.showMessageDialog(
                this,
                receipt,
                "Receipt",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

}
