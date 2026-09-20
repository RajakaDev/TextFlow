package com.textflow.ui;

import com.textflow.dao.ExpenseCategoryDAO;
import com.textflow.dao.ExpenseDAO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class ExpenseUI extends JFrame {

    private JTextField dateField;
    private JTextField descriptionField;
    private JTextField amountField;

    private JComboBox<String> categoryComboBox;
    private JComboBox<String> paymentMethodComboBox;

    private JTable expenseTable;
    private DefaultTableModel tableModel;

    private final ExpenseDAO expenseDAO;
    private final ExpenseCategoryDAO categoryDAO;

    private final int loggedInUserId;

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

    public ExpenseUI(
            int loggedInUserId
    ) {

        this.loggedInUserId =
                loggedInUserId;

        expenseDAO =
                new ExpenseDAO();

        categoryDAO =
                new ExpenseCategoryDAO();

        setTitle(
                "TextFlow - Expense Management"
        );

        setSize(
                1050,
                700
        );

        setMinimumSize(
                new Dimension(
                        900,
                        600
                )
        );

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setLocationRelativeTo(null);

        createUI();

        loadCategories();
        loadExpenses();
    }

    // ==================================================
    // CREATE UI
    // ==================================================

    private void createUI() {

        JPanel rootPanel =
                new JPanel(
                        new BorderLayout()
                );

        rootPanel.setBackground(
                LIGHT_BACKGROUND
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
                        17,
                        25,
                        17,
                        25
                )
        );

        JLabel titleLabel =
                new JLabel(
                        "TEXTFLOW  |  Expense Management"
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
                        220,
                        230,
                        245
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
        // FORM CARD
        // ==================================================

        JPanel formCard =
                new JPanel(
                        new BorderLayout(
                                10,
                                15
                        )
                );

        formCard.setBackground(
                Color.WHITE
        );

        formCard.setBorder(
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

        JLabel formTitle =
                new JLabel(
                        "Expense Details"
                );

        formTitle.setForeground(
                DARK_BLUE
        );

        formTitle.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        18
                )
        );

        formCard.add(
                formTitle,
                BorderLayout.NORTH
        );

        JPanel fieldsPanel =
                new JPanel(
                        new GridBagLayout()
                );

        fieldsPanel.setOpaque(
                false
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(
                        7,
                        8,
                        7,
                        8
                );

        gbc.anchor =
                GridBagConstraints.WEST;

        // ==================================================
        // FIELDS
        // ==================================================

        categoryComboBox =
                new JComboBox<>();

        dateField =
                new JTextField(
                        LocalDate.now()
                                .toString()
                );

        descriptionField =
                new JTextField();

        amountField =
                new JTextField();

        paymentMethodComboBox =
                new JComboBox<>(
                        new String[]{
                                "Cash",
                                "Card",
                                "Bank Transfer",
                                "Online Payment"
                        }
                );

        addFormRow(
                fieldsPanel,
                gbc,
                0,
                0,
                "Category:",
                categoryComboBox
        );

        addFormRow(
                fieldsPanel,
                gbc,
                0,
                2,
                "Date (YYYY-MM-DD):",
                dateField
        );

        addFormRow(
                fieldsPanel,
                gbc,
                1,
                0,
                "Description:",
                descriptionField
        );

        addFormRow(
                fieldsPanel,
                gbc,
                1,
                2,
                "Amount:",
                amountField
        );

        addFormRow(
                fieldsPanel,
                gbc,
                2,
                0,
                "Payment Method:",
                paymentMethodComboBox
        );

        formCard.add(
                fieldsPanel,
                BorderLayout.CENTER
        );

        // ==================================================
        // BUTTONS
        // ==================================================

        JButton addButton =
                createButton(
                        "Add Expense",
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

        JButton deleteButton =
                createButton(
                        "Delete Selected",
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

        JButton categoryButton =
                createButton(
                        "Manage Categories",
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

        JButton closeButton =
                createButton(
                        "Close",
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

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                10,
                                3
                        )
                );

        buttonPanel.setOpaque(
                false
        );

        buttonPanel.add(
                addButton
        );

        buttonPanel.add(
                deleteButton
        );

        buttonPanel.add(
                categoryButton
        );

        buttonPanel.add(
                refreshButton
        );

        buttonPanel.add(
                clearButton
        );

        buttonPanel.add(
                closeButton
        );

        formCard.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        contentPanel.add(
                formCard,
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
                        "Expense Records"
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

        tableModel =
                new DefaultTableModel(
                        new Object[]{
                                "No.",
                                "ID",
                                "Category ID",
                                "Date",
                                "Description",
                                "Amount",
                                "Payment Method",
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

        expenseTable =
                new JTable(
                        tableModel
                );

        expenseTable.setRowHeight(
                28
        );

        expenseTable.setSelectionMode(
                ListSelectionModel
                        .SINGLE_SELECTION
        );

        expenseTable.setShowGrid(
                true
        );

        expenseTable.setGridColor(
                new Color(
                        230,
                        233,
                        238
                )
        );

        expenseTable
                .getTableHeader()
                .setBackground(
                        DARK_BLUE
                );

        expenseTable
                .getTableHeader()
                .setForeground(
                        Color.WHITE
                );

        expenseTable
                .getTableHeader()
                .setFont(
                        new Font(
                                "Arial",
                                Font.BOLD,
                                12
                        )
                );

        expenseTable
                .getTableHeader()
                .setPreferredSize(
                        new Dimension(
                                0,
                                32
                        )
                );

        JScrollPane scrollPane =
                new JScrollPane(
                        expenseTable
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

        rootPanel.add(
                contentPanel,
                BorderLayout.CENTER
        );

        add(
                rootPanel
        );

        // ==================================================
        // BUTTON ACTIONS
        // ==================================================

        addButton.addActionListener(
                e -> addExpense()
        );

        deleteButton.addActionListener(
                e -> deleteExpense()
        );

        categoryButton.addActionListener(
                e -> openCategoryManagement()
        );

        refreshButton.addActionListener(
                e -> {
                    loadCategories();
                    loadExpenses();
                }
        );

        clearButton.addActionListener(
                e -> clearFields()
        );

        closeButton.addActionListener(
                e -> dispose()
        );
    }

    // ==================================================
    // ADD FORM ROW
    // ==================================================

    private void addFormRow(
            JPanel panel,
            GridBagConstraints gbc,
            int row,
            int startColumn,
            String labelText,
            Component component
    ) {

        gbc.gridx =
                startColumn;

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
                        135,
                        30
                )
        );

        panel.add(
                label,
                gbc
        );

        gbc.gridx =
                startColumn + 1;

        gbc.weightx =
                1;

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        if (
                component instanceof JComponent
        ) {

            ((JComponent) component)
                    .setPreferredSize(
                            new Dimension(
                                    280,
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
    // OPEN CATEGORY MANAGEMENT
    // ==================================================

    private void openCategoryManagement() {

        ExpenseCategoryUI frame =
                new ExpenseCategoryUI();

        frame.addWindowListener(
                new WindowAdapter() {

                    @Override
                    public void windowClosed(
                            WindowEvent e
                    ) {

                        loadCategories();
                    }
                }
        );

        frame.setVisible(
                true
        );
    }

    // ==================================================
    // LOAD CATEGORIES
    // ==================================================

    private void loadCategories() {

        Object currentSelection =
                categoryComboBox
                        .getSelectedItem();

        categoryComboBox
                .removeAllItems();

        List<String[]> categories =
                categoryDAO
                        .getCategories();

        for (
                String[] category
                : categories
        ) {

            String categoryId =
                    category[0];

            String categoryName =
                    category[1];

            String status =
                    category.length > 3
                            ? category[3]
                            : "ACTIVE";

            if (
                    "ACTIVE"
                            .equalsIgnoreCase(
                                    status
                            )
            ) {

                categoryComboBox.addItem(
                        categoryId
                                + " - "
                                + categoryName
                );
            }
        }

        if (
                currentSelection != null
        ) {

            categoryComboBox.setSelectedItem(
                    currentSelection
            );
        }
    }

    // ==================================================
    // ADD EXPENSE
    // ==================================================

    private void addExpense() {

        if (
                categoryComboBox
                        .getSelectedItem()
                        == null
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please create or select an expense category.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        String date =
                dateField
                        .getText()
                        .trim();

        String description =
                descriptionField
                        .getText()
                        .trim();

        String amountText =
                amountField
                        .getText()
                        .trim();

        // ==================================================
        // DATE
        // ==================================================

        if (
                date.isEmpty()
        ) {

            warning(
                    "Please enter the expense date."
            );

            return;
        }

        try {

            LocalDate.parse(
                    date
            );

        } catch (
                DateTimeParseException e
        ) {

            warning(
                    "Date must be in YYYY-MM-DD format."
            );

            return;
        }

        // ==================================================
        // DESCRIPTION
        // ==================================================

        if (
                description.isEmpty()
        ) {

            warning(
                    "Please enter a description."
            );

            return;
        }

        // ==================================================
        // AMOUNT
        // ==================================================

        if (
                amountText.isEmpty()
        ) {

            warning(
                    "Please enter the amount."
            );

            return;
        }

        double amount;

        try {

            amount =
                    Double.parseDouble(
                            amountText
                    );

            if (
                    amount <= 0
            ) {

                warning(
                        "Amount must be greater than zero."
                );

                return;
            }

        } catch (
                NumberFormatException e
        ) {

            warning(
                    "Please enter a valid amount."
            );

            return;
        }

        // ==================================================
        // CATEGORY ID
        // ==================================================

        String selectedCategory =
                categoryComboBox
                        .getSelectedItem()
                        .toString();

        int categoryId;

        try {

            categoryId =
                    Integer.parseInt(
                            selectedCategory
                                    .split(
                                            " - ",
                                            2
                                    )[0]
                    );

        } catch (
                NumberFormatException e
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid expense category.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        // ==================================================
        // PAYMENT METHOD
        // ==================================================

        String selectedPaymentMethod =
                paymentMethodComboBox
                        .getSelectedItem()
                        .toString();

        String paymentMethod;

        switch (
                selectedPaymentMethod
        ) {

            case "Cash":
                paymentMethod =
                        "CASH";
                break;

            case "Card":
                paymentMethod =
                        "CARD";
                break;

            case "Bank Transfer":
                paymentMethod =
                        "BANK";
                break;

            case "Online Payment":
                paymentMethod =
                        "OTHER";
                break;

            default:
                paymentMethod =
                        "OTHER";
        }

        Integer relatedUserId =
                null;

        // ==================================================
        // SAVE
        // ==================================================

        boolean success =
                expenseDAO.addExpense(
                        categoryId,
                        loggedInUserId,
                        relatedUserId,
                        date,
                        description,
                        amount,
                        paymentMethod
                );

        if (
                success
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Expense added successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            clearFields();

            loadExpenses();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to add expense.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ==================================================
    // DELETE EXPENSE
    // ==================================================

    private void deleteExpense() {

        int selectedRow =
                expenseTable
                        .getSelectedRow();

        if (
                selectedRow == -1
        ) {

            warning(
                    "Please select an expense first."
            );

            return;
        }

        int expenseId =
                Integer.parseInt(
                        tableModel
                                .getValueAt(
                                        selectedRow,
                                        1
                                )
                                .toString()
                );

        String description =
                String.valueOf(
                        tableModel
                                .getValueAt(
                                        selectedRow,
                                        4
                                )
                );

        int confirmation =
                JOptionPane.showConfirmDialog(
                        this,
                        "Delete expense: "
                                + description
                                + "?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION
                );

        if (
                confirmation
                        != JOptionPane.YES_OPTION
        ) {

            return;
        }

        boolean success =
                expenseDAO
                        .deleteExpense(
                                expenseId
                        );

        if (
                success
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Expense deleted successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            loadExpenses();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to delete expense.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ==================================================
    // LOAD EXPENSES
    // ==================================================

    private void loadExpenses() {

        tableModel
                .setRowCount(
                        0
                );

        List<String[]> expenses =
                expenseDAO
                        .getExpenses();

        int displayNumber =
                1;

        for (
                String[] expense
                : expenses
        ) {

            tableModel.addRow(
                    new Object[]{
                            displayNumber++,
                            expense[0],
                            expense[1],
                            expense[2],
                            expense[3],
                            expense[4],
                            expense[5],
                            expense[6]
                    }
            );
        }
    }

    // ==================================================
    // CLEAR
    // ==================================================

    private void clearFields() {

        descriptionField
                .setText("");

        amountField
                .setText("");

        dateField.setText(
                LocalDate.now()
                        .toString()
        );

        paymentMethodComboBox
                .setSelectedIndex(
                        0
                );

        if (
                categoryComboBox
                        .getItemCount()
                        > 0
        ) {

            categoryComboBox
                    .setSelectedIndex(
                            0
                    );
        }

        expenseTable
                .clearSelection();

        descriptionField
                .requestFocus();
    }

    // ==================================================
    // BUTTON
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

    // ==================================================
    // WARNING
    // ==================================================

    private void warning(
            String message
    ) {

        JOptionPane.showMessageDialog(
                this,
                message,
                "Validation Error",
                JOptionPane.WARNING_MESSAGE
        );
    }
}