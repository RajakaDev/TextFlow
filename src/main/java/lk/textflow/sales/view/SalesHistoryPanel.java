package lk.textflow.sales.view;

import lk.textflow.sales.dao.SaleDAO;
import lk.textflow.sales.model.Sale;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class SalesHistoryPanel extends JPanel {

    private JTable salesTable;
    private DefaultTableModel tableModel;

    private JTextField searchField;

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

    public SalesHistoryPanel() {

        saleDAO =
                new SaleDAO();

        setLayout(
                new BorderLayout()
        );

        setBackground(
                LIGHT_BACKGROUND
        );

        createUI();

        loadSales();
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
                        "TEXTFLOW  |  Sales History"
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

        headerPanel.add(
                titleLabel,
                BorderLayout.WEST
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
        // SEARCH CARD
        // ==================================================

        JPanel searchCard =
                new JPanel(
                        new BorderLayout(
                                10,
                                10
                        )
                );

        searchCard.setBackground(
                Color.WHITE
        );

        searchCard.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                CARD_BORDER
                        ),
                        new EmptyBorder(
                                15,
                                18,
                                15,
                                18
                        )
                )
        );

        JLabel searchTitle =
                new JLabel(
                        "Search Sales"
                );

        searchTitle.setForeground(
                DARK_BLUE
        );

        searchTitle.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        17
                )
        );

        searchCard.add(
                searchTitle,
                BorderLayout.NORTH
        );

        JPanel searchControls =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                10,
                                3
                        )
                );

        searchControls.setOpaque(
                false
        );

        JLabel searchLabel =
                new JLabel(
                        "Sale ID:"
                );

        searchLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        12
                )
        );

        searchField =
                new JTextField(
                        18
                );

        searchField.setPreferredSize(
                new Dimension(
                        220,
                        34
                )
        );

        JButton searchButton =
                createButton(
                        "Search",
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

        searchControls.add(
                searchLabel
        );

        searchControls.add(
                searchField
        );

        searchControls.add(
                searchButton
        );

        searchControls.add(
                refreshButton
        );

        searchControls.add(
                clearButton
        );

        searchCard.add(
                searchControls,
                BorderLayout.CENTER
        );

        contentPanel.add(
                searchCard,
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
                        "Sales Records"
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
                                "Amount Given",
                                "Balance",
                                "Payment Method",
                                "Payment Status",
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

        salesTable.setAutoResizeMode(
                JTable.AUTO_RESIZE_OFF
        );

        // ==================================================
        // TABLE HEADER STYLE
        // ==================================================

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
                .setPreferredWidth(75);

        salesTable
                .getColumnModel()
                .getColumn(1)
                .setPreferredWidth(95);

        salesTable
                .getColumnModel()
                .getColumn(2)
                .setPreferredWidth(165);

        salesTable
                .getColumnModel()
                .getColumn(3)
                .setPreferredWidth(100);

        salesTable
                .getColumnModel()
                .getColumn(4)
                .setPreferredWidth(110);

        salesTable
                .getColumnModel()
                .getColumn(5)
                .setPreferredWidth(100);

        salesTable
                .getColumnModel()
                .getColumn(6)
                .setPreferredWidth(110);

        salesTable
                .getColumnModel()
                .getColumn(7)
                .setPreferredWidth(110);

        salesTable
                .getColumnModel()
                .getColumn(8)
                .setPreferredWidth(100);

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

        add(
                contentPanel,
                BorderLayout.CENTER
        );

        // ==================================================
        // ACTIONS
        // ==================================================

        searchButton.addActionListener(
                e -> searchSale()
        );

        searchField.addActionListener(
                e -> searchSale()
        );

        refreshButton.addActionListener(
                e -> {
                    searchField.setText("");
                    loadSales();
                }
        );

        clearButton.addActionListener(
                e -> {
                    searchField.setText("");
                    loadSales();
                    searchField.requestFocus();
                }
        );
    }

    // ==================================================
    // LOAD ALL SALES
    // ==================================================

    private void loadSales() {

        tableModel.setRowCount(
                0
        );

        List<Sale> sales =
                saleDAO.getAllSales();

        for (
                Sale sale
                : sales
        ) {

            addSaleToTable(
                    sale
            );
        }
    }

    // ==================================================
    // SEARCH SALE
    // ==================================================

    private void searchSale() {

        String searchText =
                searchField
                        .getText()
                        .trim();

        if (
                searchText.isEmpty()
        ) {

            loadSales();

            return;
        }

        int saleId;

        try {

            saleId =
                    Integer.parseInt(
                            searchText
                    );

        } catch (
                NumberFormatException e
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid Sale ID.",
                    "Invalid Sale ID",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        Sale sale =
                saleDAO.getSaleById(
                        saleId
                );

        tableModel.setRowCount(
                0
        );

        if (
                sale != null
        ) {

            addSaleToTable(
                    sale
            );

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Sale not found.",
                    "Sales History",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    // ==================================================
    // ADD SALE TO TABLE
    // ==================================================

    private void addSaleToTable(
            Sale sale
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
                        sale.getAmountGiven(),
                        sale.getBalance(),
                        sale.getPaymentMethod(),
                        sale.getPaymentStatus(),
                        sale.getStatus()
                }
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