package com.textflow.ui;

import com.textflow.dao.ExpenseDAO;
import com.textflow.dao.ExpenseCategoryDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
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

    private final int userId;

    // ==================================================
    // TEST CONSTRUCTOR
    // ==================================================

    public ExpenseUI() {

        // Temporary user ID for standalone testing
        // Later we will pass the logged-in user's actual ID
        this(1);
    }

    // ==================================================
    // NORMAL CONSTRUCTOR
    // ==================================================

    public ExpenseUI(int userId) {

        this.userId = userId;

        expenseDAO =
                new ExpenseDAO();

        categoryDAO =
                new ExpenseCategoryDAO();

        setTitle(
                "TextFlow - Expense Management"
        );

        setSize(
                950,
                650
        );

        // Important for integrated application
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

        JPanel mainPanel =
                new JPanel(
                        new BorderLayout(
                                10,
                                10
                        )
                );

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        20,
                        20,
                        20,
                        20
                )
        );

        // ==================================================
        // TITLE
        // ==================================================

        JLabel titleLabel =
                new JLabel(
                        "Expense Management"
                );

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24
                )
        );

        // ==================================================
        // INPUT PANEL
        // ==================================================

        JPanel inputPanel =
                new JPanel(
                        new GridLayout(
                                5,
                                2,
                                10,
                                10
                        )
                );

        // Category
        inputPanel.add(
                new JLabel(
                        "Category:"
                )
        );

        categoryComboBox =
                new JComboBox<>();

        inputPanel.add(
                categoryComboBox
        );

        // Date
        inputPanel.add(
                new JLabel(
                        "Date (YYYY-MM-DD):"
                )
        );

        dateField =
                new JTextField();

        dateField.setText(
                LocalDate.now()
                        .toString()
        );

        inputPanel.add(
                dateField
        );

        // Description
        inputPanel.add(
                new JLabel(
                        "Description:"
                )
        );

        descriptionField =
                new JTextField();

        inputPanel.add(
                descriptionField
        );

        // Amount
        inputPanel.add(
                new JLabel(
                        "Amount:"
                )
        );

        amountField =
                new JTextField();

        inputPanel.add(
                amountField
        );

        // Payment Method
        inputPanel.add(
                new JLabel(
                        "Payment Method:"
                )
        );

        paymentMethodComboBox =
                new JComboBox<>(
                        new String[]{
                                "Cash",
                                "Card",
                                "Bank Transfer",
                                "Online Payment"
                        }
                );

        inputPanel.add(
                paymentMethodComboBox
        );

        // ==================================================
        // BUTTONS
        // ==================================================

        JButton addButton =
                new JButton(
                        "Add Expense"
                );

        JButton deleteButton =
                new JButton(
                        "Delete Selected"
                );

        JButton categoryButton =
                new JButton(
                        "Manage Categories"
                );

        JButton refreshButton =
                new JButton(
                        "Refresh"
                );

        JButton closeButton =
                new JButton(
                        "Close"
                );

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                10,
                                5
                        )
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
                closeButton
        );

        // ==================================================
        // FORM AREA
        // ==================================================

        JPanel formPanel =
                new JPanel(
                        new BorderLayout(
                                10,
                                10
                        )
                );

        formPanel.add(
                inputPanel,
                BorderLayout.CENTER
        );

        formPanel.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        JPanel topPanel =
                new JPanel(
                        new BorderLayout(
                                10,
                                10
                        )
                );

        topPanel.add(
                titleLabel,
                BorderLayout.NORTH
        );

        topPanel.add(
                formPanel,
                BorderLayout.CENTER
        );

        mainPanel.add(
                topPanel,
                BorderLayout.NORTH
        );

        // ==================================================
        // TABLE
        // ==================================================

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
                25
        );

        expenseTable.setSelectionMode(
                ListSelectionModel
                        .SINGLE_SELECTION
        );

        JScrollPane scrollPane =
                new JScrollPane(
                        expenseTable
                );

        mainPanel.add(
                scrollPane,
                BorderLayout.CENTER
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
                e -> {

                    ExpenseCategoryUI frame =
                            new ExpenseCategoryUI();

                    frame.setVisible(true);
                }
        );

        refreshButton.addActionListener(
                e -> {

                    loadCategories();
                    loadExpenses();
                }
        );

        closeButton.addActionListener(
                e -> dispose()
        );

        add(
                mainPanel
        );
    }

    // ==================================================
    // LOAD CATEGORIES
    // ==================================================

    private void loadCategories() {

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

            categoryComboBox.addItem(
                    categoryId
                            + " - "
                            + categoryName
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
        // DATE VALIDATION
        // ==================================================

        if (date.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter the expense date.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
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

            JOptionPane.showMessageDialog(
                    this,
                    "Date must be in YYYY-MM-DD format.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // ==================================================
        // DESCRIPTION VALIDATION
        // ==================================================

        if (
                description.isEmpty()
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a description.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // ==================================================
        // AMOUNT VALIDATION
        // ==================================================

        if (
                amountText.isEmpty()
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter the amount.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        double amount;

        try {

            amount =
                    Double.parseDouble(
                            amountText
                    );

            if (amount <= 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Amount must be greater than zero.",
                        "Validation Error",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

        } catch (
                NumberFormatException e
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid amount.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
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
        // RELATED USER
        // ==================================================

        // Expense is not related to another user by default.
        Integer relatedUserId =
                null;

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
                break;
        }

        // ==================================================
        // SAVE EXPENSE
        // ==================================================

        boolean success =
                expenseDAO
                        .addExpense(
                                categoryId,
                                userId,
                                relatedUserId,
                                date,
                                description,
                                amount,
                                paymentMethod
                        );

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Expense added successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            descriptionField
                    .setText("");

            amountField
                    .setText("");

            dateField.setText(
                    LocalDate.now()
                            .toString()
            );

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

            JOptionPane.showMessageDialog(
                    this,
                    "Please select an expense first.",
                    "Selection Required",
                    JOptionPane.WARNING_MESSAGE
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
                JOptionPane
                        .showConfirmDialog(
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

        if (success) {

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
                .setRowCount(0);

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
    // TEST MAIN
    // ==================================================

    public static void main(
            String[] args
    ) {

        SwingUtilities.invokeLater(
                () -> {

                    ExpenseUI ui =
                            new ExpenseUI();

                    ui.setVisible(
                            true
                    );
                }
        );
    }
}