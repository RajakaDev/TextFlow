package lk.textflow.sales.view;

import lk.textflow.sales.dao.SaleDAO;
import lk.textflow.sales.model.Sale;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.util.List;

public class SalesReportPanel extends JPanel {

    private DefaultTableModel tableModel;
    private JLabel totalSalesLabel;
    private JLabel salesCountLabel;

    public SalesReportPanel() {

        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Sales Report");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        tableModel = new DefaultTableModel(
                new String[]{
                        "Sale ID",
                        "Customer ID",
                        "Date",
                        "Total",
                        "Payment Method",
                        "Payment Status"
                }, 0
        );

        JTable salesTable = new JTable(tableModel);

        JPanel summaryPanel = new JPanel();

        salesCountLabel = new JLabel("Sales Count: 0");
        totalSalesLabel = new JLabel("Total Sales: 0.00");

        summaryPanel.add(salesCountLabel);
        summaryPanel.add(totalSalesLabel);

        add(titleLabel, BorderLayout.NORTH);
        add(new JScrollPane(salesTable), BorderLayout.CENTER);
        add(summaryPanel, BorderLayout.SOUTH);

        loadReport();
    }

    private void loadReport() {

        SaleDAO saleDAO = new SaleDAO();
        List<Sale> sales = saleDAO.getAllSales();

        BigDecimal totalSales = BigDecimal.ZERO;
        int count = 0;

        tableModel.setRowCount(0);

        for (Sale sale : sales) {

            if ("CONFIRMED".equalsIgnoreCase(sale.getStatus())) {

                tableModel.addRow(new Object[]{
                        sale.getSaleId(),
                        sale.getCustomerId(),
                        sale.getSaleDate(),
                        sale.getTotalAmount(),
                        sale.getPaymentMethod(),
                        sale.getPaymentStatus()
                });

                totalSales = totalSales.add(sale.getTotalAmount());
                count++;
            }
        }

        salesCountLabel.setText("Sales Count: " + count);
        totalSalesLabel.setText("Total Sales: " + totalSales);
    }
}
