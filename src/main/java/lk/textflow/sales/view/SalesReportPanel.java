package lk.textflow.sales.view;

import lk.textflow.sales.dao.SaleDAO;
import lk.textflow.sales.model.Sale;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.util.List;

public class SalesReportPanel extends JPanel {

    private JTable salesTable;
    private DefaultTableModel tableModel;

    private JLabel totalSalesValueLabel;
    private JLabel salesCountValueLabel;

    private final SaleDAO saleDAO;

    // ==================================================
    // COLORS
    // ==================================================

    private final Color DARK_BLUE =
            new Color(31, 60, 136);

    private final Color LIGHT_BACKGROUND =
            new Color(245, 247, 250);

    private final Color CARD_BORDER =
            new Color(220, 225, 230);

    // ==================================================
    // CONSTRUCTOR
    // ==================================================

    public SalesReportPanel() {

        saleDAO =
                new SaleDAO();

        setLayout(
                new BorderLayout()
        );

        setBackground(
                LIGHT_BACKGROUND
        );

        createUI();

        loadReport();
    }

    // ==================================================
    // CREATE UI
    // ==================================================

    private void createUI() {

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
                        17,
                        25,
                        17,
                        25
                )
        );

        JLabel titleLabel =
                new JLabel(
                        "TEXTFLOW  |  Sales Report"
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

        JButton refreshButton =
                createButton(
                        "Refresh",
                        new Color(
                                204,
                                251,
                                241
                        ),
                        new Color(
                                17,
                                94,
                                89
                        )
                );

        headerPanel.add(
                titleLabel,
                BorderLayout.WEST
        );

        headerPanel.add(
                refreshButton,
                BorderLayout.EAST
        );

        add(
                headerPanel,
                BorderLayout.NORTH
        );

        // ==================================================
        // MAIN CONTENT
        // ==================================================

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
                        20,
                        20,
                        20,
                        20
                )
        );

        // ==================================================
        // SUMMARY CARDS
        // ==================================================

        JPanel summaryPanel =
                new JPanel(
                        new GridLayout(
                                1,
                                2,
                                15,
                                0
                        )
                );

        summaryPanel.setOpaque(
                false
        );

        JPanel countCard =
                createSummaryCard(
                        "Confirmed Sales",
                        "0"
                );

        salesCountValueLabel =
                getSummaryValueLabel(
                        countCard
                );

        JPanel totalCard =
                createSummaryCard(
                        "Total Sales Amount",
                        "0.00"
                );

        totalSalesValueLabel =
                getSummaryValueLabel(
                        totalCard
                );

        summaryPanel.add(
                countCard
        );

        summaryPanel.add(
                totalCard
        );

        contentPanel.add(
                summaryPanel,
                BorderLayout.NORTH
        );

        // ==================================================
        // TABLE CARD
        // ==================================================

        JPanel tableCard =
                new JPanel(
                        new BorderLayout(
                                10,
                                10
                        )
                );

        tableCard.setBackground(
                Color.WHITE
        );

        tableCard.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                CARD_BORDER
                        ),
                        new EmptyBorder(
                                15,
                                15,
                                15,
                                15
                        )
                )
        );

        JLabel tableTitle =
                new JLabel(
                        "Confirmed Sales"
                );

        tableTitle.setForeground(
                DARK_BLUE
        );

        tableTitle.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        17
                )
        );

        tableCard.add(
                tableTitle,
                BorderLayout.NORTH
        );

        // ==================================================
        // TABLE MODEL
        // ==================================================

        tableModel =
                new DefaultTableModel(
                        new String[]{
                                "Sale ID",
                                "Customer ID",
                                "Date",
                                "Total",
                                "Payment Method",
                                "Payment Status"
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

        salesTable =
                new JTable(
                        tableModel
                );

        salesTable.setRowHeight(
                28
        );

        salesTable.setSelectionMode(
                ListSelectionModel
                        .SINGLE_SELECTION
        );

        salesTable.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        12
                )
        );

        salesTable.setGridColor(
                new Color(
                        230,
                        233,
                        238
                )
        );

        salesTable
                .getTableHeader()
                .setBackground(
                        DARK_BLUE
                );

        salesTable
                .getTableHeader()
                .setForeground(
                        Color.WHITE
                );

        salesTable
                .getTableHeader()
                .setFont(
                        new Font(
                                "Arial",
                                Font.BOLD,
                                12
                        )
                );

        salesTable
                .getTableHeader()
                .setPreferredSize(
                        new Dimension(
                                0,
                                32
                        )
                );

        salesTable
                .getTableHeader()
                .setReorderingAllowed(
                        false
                );

        // ==================================================
        // COLUMN WIDTHS
        // ==================================================

        salesTable
                .getColumnModel()
                .getColumn(0)
                .setPreferredWidth(
                        80
                );

        salesTable
                .getColumnModel()
                .getColumn(1)
                .setPreferredWidth(
                        100
                );

        salesTable
                .getColumnModel()
                .getColumn(2)
                .setPreferredWidth(
                        180
                );

        salesTable
                .getColumnModel()
                .getColumn(3)
                .setPreferredWidth(
                        120
                );

        salesTable
                .getColumnModel()
                .getColumn(4)
                .setPreferredWidth(
                        130
                );

        salesTable
                .getColumnModel()
                .getColumn(5)
                .setPreferredWidth(
                        130
                );

        JScrollPane scrollPane =
                new JScrollPane(
                        salesTable
                );

        scrollPane.setBorder(
                BorderFactory.createLineBorder(
                        CARD_BORDER
                )
        );

        tableCard.add(
                scrollPane,
                BorderLayout.CENTER
        );

        contentPanel.add(
                tableCard,
                BorderLayout.CENTER
        );

        // ==================================================
        // INFORMATION BAR
        // ==================================================

        JLabel infoLabel =
                new JLabel(
                        "Report includes confirmed sales only."
                );

        infoLabel.setForeground(
                new Color(
                        107,
                        114,
                        128
                )
        );

        infoLabel.setFont(
                new Font(
                        "Arial",
                        Font.ITALIC,
                        12
                )
        );

        contentPanel.add(
                infoLabel,
                BorderLayout.SOUTH
        );

        add(
                contentPanel,
                BorderLayout.CENTER
        );

        // ==================================================
        // ACTIONS
        // ==================================================

        refreshButton.addActionListener(
                e -> loadReport()
        );
    }

    // ==================================================
    // LOAD REPORT
    // ==================================================

    private void loadReport() {

        List<Sale> sales =
                saleDAO.getAllSales();

        BigDecimal totalSales =
                BigDecimal.ZERO;

        int count =
                0;

        tableModel.setRowCount(
                0
        );

        for (
                Sale sale
                : sales
        ) {

            if (
                    "CONFIRMED"
                            .equalsIgnoreCase(
                                    sale.getStatus()
                            )
            ) {

                Object customerDisplay =
                        sale.getCustomerId()
                                == null
                                ? "Walk-in"
                                : sale.getCustomerId();

                tableModel.addRow(
                        new Object[]{
                                sale.getSaleId(),
                                customerDisplay,
                                sale.getSaleDate(),
                                sale.getTotalAmount(),
                                sale.getPaymentMethod(),
                                sale.getPaymentStatus()
                        }
                );

                if (
                        sale.getTotalAmount()
                                != null
                ) {

                    totalSales =
                            totalSales.add(
                                    sale.getTotalAmount()
                            );
                }

                count++;
            }
        }

        salesCountValueLabel.setText(
                String.valueOf(
                        count
                )
        );

        totalSalesValueLabel.setText(
                totalSales
                        .toPlainString()
        );
    }

    // ==================================================
    // SUMMARY CARD
    // ==================================================

    private JPanel createSummaryCard(
            String title,
            String initialValue
    ) {

        JPanel card =
                new JPanel();

        card.setLayout(
                new BoxLayout(
                        card,
                        BoxLayout.Y_AXIS
                )
        );

        card.setBackground(
                Color.WHITE
        );

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                CARD_BORDER
                        ),
                        new EmptyBorder(
                                18,
                                20,
                                18,
                                20
                        )
                )
        );

        JLabel titleLabel =
                new JLabel(
                        title
                );

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        13
                )
        );

        titleLabel.setForeground(
                new Color(
                        75,
                        85,
                        99
                )
        );

        JLabel valueLabel =
                new JLabel(
                        initialValue
                );

        valueLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        25
                )
        );

        valueLabel.setForeground(
                DARK_BLUE
        );

        titleLabel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        valueLabel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        card.add(
                titleLabel
        );

        card.add(
                Box.createVerticalStrut(
                        8
                )
        );

        card.add(
                valueLabel
        );

        /*
         * Save this reference so we can
         * retrieve the value label later.
         */
        card.putClientProperty(
                "valueLabel",
                valueLabel
        );

        return card;
    }

    // ==================================================
    // GET SUMMARY VALUE LABEL
    // ==================================================

    private JLabel getSummaryValueLabel(
            JPanel card
    ) {

        return (JLabel)
                card.getClientProperty(
                        "valueLabel"
                );
    }

    // ==================================================
    // BUTTON STYLE
    // ==================================================

    private JButton createButton(
            String text,
            Color background,
            Color foreground
    ) {

        JButton button =
                new JButton(
                        text
                );

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

        button.setFocusPainted(
                false
        );

        button.setOpaque(
                true
        );

        button.setContentAreaFilled(
                true
        );

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
                                8,
                                14,
                                8,
                                14
                        )
                )
        );

        return button;
    }
}