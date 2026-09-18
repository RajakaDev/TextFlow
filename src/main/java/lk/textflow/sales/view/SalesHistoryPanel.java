package lk.textflow.sales.view;

import lk.textflow.sales.dao.SaleDAO;
import lk.textflow.sales.model.Sale;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class SalesHistoryPanel extends JPanel {

    private JTable salesTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JButton searchButton;
    private JButton clearButton;

    public SalesHistoryPanel() {

        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Sales History");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        add(titleLabel, BorderLayout.NORTH);
        JPanel searchPanel = new JPanel();

        searchField = new JTextField(10);
        searchButton = new JButton("Search");
        clearButton = new JButton("Clear");

        searchPanel.add(new JLabel("Sale ID:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(clearButton);

        add(searchPanel, BorderLayout.SOUTH);
        searchButton.addActionListener(e -> searchSale());

        clearButton.addActionListener(e -> {
            searchField.setText("");
            tableModel.setRowCount(0);
            loadSales();
        });

        tableModel = new DefaultTableModel(
                new String[]{
                        "Sale ID",
                        "Customer ID",
                        "Date",
                        "Total",
                        "Amount Given",
                        "Balance",
                        "Payment Method",
                        "Payment Status",
                        "Status"
                }, 0);

        salesTable = new JTable(tableModel);

        add(new JScrollPane(salesTable), BorderLayout.CENTER);

        loadSales();
    }

    private void loadSales() {

        SaleDAO saleDAO = new SaleDAO();

        List<Sale> sales = saleDAO.getAllSales();

        for (Sale sale : sales) {

            tableModel.addRow(new Object[]{
                    sale.getSaleId(),
                    sale.getCustomerId(),
                    sale.getSaleDate(),
                    sale.getTotalAmount(),
                    sale.getAmountGiven(),
                    sale.getBalance(),
                    sale.getPaymentMethod(),
                    sale.getPaymentStatus(),
                    sale.getStatus()
            });
        }
    }

    private void searchSale() {

        try {
            int saleId = Integer.parseInt(searchField.getText());

            SaleDAO saleDAO = new SaleDAO();
            Sale sale = saleDAO.getSaleById(saleId);

            tableModel.setRowCount(0);

            if (sale != null) {

                tableModel.addRow(new Object[]{
                        sale.getSaleId(),
                        sale.getCustomerId(),
                        sale.getSaleDate(),
                        sale.getTotalAmount(),
                        sale.getAmountGiven(),
                        sale.getBalance(),
                        sale.getPaymentMethod(),
                        sale.getPaymentStatus(),
                        sale.getStatus()
                });

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Sale not found."
                );
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid Sale ID."
            );
        }
    }

}
