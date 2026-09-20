package com.textflow.ui;

import com.textflow.dao.ExpenseCategoryDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ExpenseCategoryUI extends JFrame {

    private JTextField categoryNameField;
    private JTextField descriptionField;

    private JTable categoryTable;
    private DefaultTableModel tableModel;

    private final ExpenseCategoryDAO categoryDAO;

    public ExpenseCategoryUI() {

        categoryDAO = new ExpenseCategoryDAO();

        setTitle("TextFlow - Expense Category Management");
        setSize(750, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        createUI();

        // Load existing categories from database
        loadCategories();
    }

    private void createUI() {

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 20, 20, 20
                )
        );

        // =========================
        // TITLE
        // =========================

        JLabel titleLabel =
                new JLabel("Expense Category Management");

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        mainPanel.add(
                titleLabel,
                BorderLayout.NORTH
        );

        // =========================
        // INPUT AREA
        // =========================

        JPanel inputPanel =
                new JPanel(new GridLayout(2, 2, 10, 10));

        inputPanel.add(
                new JLabel("Category Name:")
        );

        categoryNameField = new JTextField();

        inputPanel.add(
                categoryNameField
        );

        inputPanel.add(
                new JLabel("Description:")
        );

        descriptionField = new JTextField();

        inputPanel.add(
                descriptionField
        );

        // =========================
        // BUTTONS
        // =========================

        JButton addButton =
                new JButton("Add Category");

        JButton deleteButton =
                new JButton("Delete Selected");

        JButton refreshButton =
                new JButton("Refresh");

        JPanel buttonPanel =
                new JPanel();

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);

        JPanel topPanel =
                new JPanel(new BorderLayout(10, 10));

        topPanel.add(
                inputPanel,
                BorderLayout.CENTER
        );

        topPanel.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        mainPanel.add(
                topPanel,
                BorderLayout.CENTER
        );

        // =========================
        // TABLE
        // =========================

        tableModel =
                new DefaultTableModel(
                        new Object[]{
                                "No.",
                                "ID",
                                "Category Name",
                                "Description",
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

        categoryTable =
                new JTable(tableModel);

        categoryTable.setRowHeight(25);

        categoryTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        JScrollPane scrollPane =
                new JScrollPane(categoryTable);

        mainPanel.add(
                scrollPane,
                BorderLayout.SOUTH
        );

        // =========================
        // BUTTON ACTIONS
        // =========================

        addButton.addActionListener(
                e -> addCategory()
        );

        deleteButton.addActionListener(
                e -> deleteCategory()
        );

        refreshButton.addActionListener(
                e -> loadCategories()
        );

        add(mainPanel);
    }

    // =========================
    // ADD CATEGORY
    // =========================

    private void addCategory() {

        String categoryName =
                categoryNameField.getText().trim();

        String description =
                descriptionField.getText().trim();

        if (categoryName.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a category name.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        boolean success =
                categoryDAO.addCategory(
                        categoryName,
                        description
                );

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Category added successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            categoryNameField.setText("");
            descriptionField.setText("");

            loadCategories();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to add category.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================
    // DELETE CATEGORY
    // =========================

    private void deleteCategory() {

        int selectedRow =
                categoryTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a category first.",
                    "Selection Required",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // ID is now column 1 because
        // column 0 is the display number
        int categoryId =
                Integer.parseInt(
                        tableModel
                                .getValueAt(
                                        selectedRow,
                                        1
                                )
                                .toString()
                );

        String categoryName =
                tableModel
                        .getValueAt(
                                selectedRow,
                                2
                        )
                        .toString();

        int confirmation =
                JOptionPane.showConfirmDialog(
                        this,
                        "Deactivate category: "
                                + categoryName
                                + "?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION
                );

        if (confirmation ==
                JOptionPane.YES_OPTION) {

            boolean success =
                    categoryDAO.deleteCategory(
                            categoryId
                    );

            if (success) {

                JOptionPane.showMessageDialog(
                        this,
                        "Category deleted successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

                loadCategories();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Failed to delete category.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    // =========================
    // LOAD CATEGORIES
    // =========================

    private void loadCategories() {

        tableModel.setRowCount(0);

        List<String[]> categories =
                categoryDAO.getCategories();

        int displayNumber = 1;

        for (String[] category : categories) {

            tableModel.addRow(
                    new Object[]{
                            displayNumber++,
                            category[0],
                            category[1],
                            category[2],
                            category[3]
                    }
            );
        }
    }

    // =========================
    // MAIN
    // =========================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            ExpenseCategoryUI ui =
                    new ExpenseCategoryUI();

            ui.setVisible(true);
        });
    }
}