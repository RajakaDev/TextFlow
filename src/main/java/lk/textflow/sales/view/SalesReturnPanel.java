package lk.textflow.sales.view;

import lk.textflow.sales.dao.SalesReturnDAO;
import lk.textflow.sales.model.SalesReturn;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;

public class SalesReturnPanel extends JPanel {

    private JTextField saleIdField;
    private JTextField productIdField;
    private JTextField quantityField;

    private SalesReturnDAO salesReturnDAO = new SalesReturnDAO();

    public SalesReturnPanel() {

        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Sales Return / Refund");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));

        saleIdField = new JTextField();
        productIdField = new JTextField();
        quantityField = new JTextField();

        JButton returnButton = new JButton("Process Return");

        formPanel.add(new JLabel("Sale ID:"));
        formPanel.add(saleIdField);

        formPanel.add(new JLabel("Product ID:"));
        formPanel.add(productIdField);

        formPanel.add(new JLabel("Return Quantity:"));
        formPanel.add(quantityField);

        formPanel.add(new JLabel(""));
        formPanel.add(returnButton);

        add(titleLabel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);

        returnButton.addActionListener(e -> processReturn());
    }

    private void processReturn() {

        try {

            int saleId = Integer.parseInt(saleIdField.getText());
            int productId = Integer.parseInt(productIdField.getText());
            int quantity = Integer.parseInt(quantityField.getText());

            if (quantity <= 0) {
                JOptionPane.showMessageDialog(
                        this,
                        "Return quantity must be greater than 0."
                );
                return;
            }

            SalesReturn salesReturn = new SalesReturn();

            salesReturn.setSaleId(saleId);
            salesReturn.setProductId(productId);
            salesReturn.setQuantity(quantity);

            boolean success = salesReturnDAO.processReturn(salesReturn);

            if (success) {

                JOptionPane.showMessageDialog(
                        this,
                        "Sales return processed successfully.\n"
                                + "Refund Amount: "
                                + salesReturn.getReturnAmount()
                );

                saleIdField.setText("");
                productIdField.setText("");
                quantityField.setText("");

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Sales return failed.",
                        "Return Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter valid numbers.",
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}