package lk.textflow.view;

import lk.textflow.dao.CategoryDAO;
import lk.textflow.model.Category;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CategoryPanel extends JPanel {

    private JTextField txtCategoryId;
    private JTextField txtCategoryName;
    private JTextField txtDescription;

    private JComboBox<String> cmbStatus;

    private JButton btnAdd;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnClear;

    private JTable categoryTable;
    private DefaultTableModel tableModel;

    private final CategoryDAO categoryDAO;

    public CategoryPanel() {

        categoryDAO = new CategoryDAO();

        setLayout(new BorderLayout(10, 10));

        initializeUI();

        loadCategories();
    }

    private void initializeUI() {

        JLabel titleLabel =
                new JLabel("Category Management");

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        titleLabel.setBorder(
                BorderFactory.createEmptyBorder(
                        10, 10, 10, 10
                )
        );

        add(titleLabel, BorderLayout.NORTH);

        JPanel mainPanel =
                new JPanel(new BorderLayout(10, 10));

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        10, 10, 10, 10
                )
        );

        mainPanel.add(
                createFormPanel(),
                BorderLayout.NORTH
        );

        mainPanel.add(
                createTablePanel(),
                BorderLayout.CENTER
        );

        add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel createFormPanel() {

        JPanel formPanel =
                new JPanel(new GridBagLayout());

        formPanel.setBorder(
                BorderFactory.createTitledBorder(
                        "Category Details"
                )
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets = new Insets(
                5, 5, 5, 5
        );

        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtCategoryId =
                new JTextField(15);

        txtCategoryId.setEditable(false);

        txtCategoryName =
                new JTextField(20);

        txtDescription =
                new JTextField(20);

        cmbStatus =
                new JComboBox<>(
                        new String[]{
                                "ACTIVE",
                                "INACTIVE"
                        }
                );

        gbc.gridx = 0;
        gbc.gridy = 0;

        formPanel.add(
                new JLabel("Category ID:"),
                gbc
        );

        gbc.gridx = 1;

        formPanel.add(
                txtCategoryId,
                gbc
        );

        gbc.gridx = 0;
        gbc.gridy = 1;

        formPanel.add(
                new JLabel("Category Name:"),
                gbc
        );

        gbc.gridx = 1;

        formPanel.add(
                txtCategoryName,
                gbc
        );

        gbc.gridx = 0;
        gbc.gridy = 2;

        formPanel.add(
                new JLabel("Description:"),
                gbc
        );

        gbc.gridx = 1;

        formPanel.add(
                txtDescription,
                gbc
        );

        gbc.gridx = 0;
        gbc.gridy = 3;

        formPanel.add(
                new JLabel("Status:"),
                gbc
        );

        gbc.gridx = 1;

        formPanel.add(
                cmbStatus,
                gbc
        );

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT
                        )
                );

        btnAdd =
                new JButton("Add");

        btnUpdate =
                new JButton("Update");

        btnDelete =
                new JButton("Delete");

        btnClear =
                new JButton("Clear");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;

        formPanel.add(
                buttonPanel,
                gbc
        );

        btnAdd.addActionListener(
                e -> addCategory()
        );

        btnUpdate.addActionListener(
                e -> updateCategory()
        );

        btnDelete.addActionListener(
                e -> deleteCategory()
        );

        btnClear.addActionListener(
                e -> clearForm()
        );

        return formPanel;
    }

    private JPanel createTablePanel() {

        JPanel tablePanel =
                new JPanel(
                        new BorderLayout()
                );

        tablePanel.setBorder(
                BorderFactory.createTitledBorder(
                        "Category List"
                )
        );

        String[] columns = {
                "ID",
                "Category Name",
                "Description",
                "Status"
        };

        tableModel =
                new DefaultTableModel(
                        columns,
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column) {

                        return false;
                    }
                };

        categoryTable =
                new JTable(tableModel);

        categoryTable.setSelectionMode(
                ListSelectionModel
                        .SINGLE_SELECTION
        );

        categoryTable.getSelectionModel()
                .addListSelectionListener(
                        e -> {

                            if (!e.getValueIsAdjusting()) {

                                loadSelectedCategory();
                            }
                        }
                );

        JScrollPane scrollPane =
                new JScrollPane(categoryTable);

        tablePanel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        return tablePanel;
    }

    private void loadCategories() {

        tableModel.setRowCount(0);

        List<Category> categories =
                categoryDAO.getAllCategories();

        for (Category category : categories) {

            tableModel.addRow(
                    new Object[]{
                            category.getCategoryId(),
                            category.getCategoryName(),
                            category.getDescription(),
                            category.getStatus()
                    }
            );
        }
    }

    private void addCategory() {

        String name =
                txtCategoryName
                        .getText()
                        .trim();

        String description =
                txtDescription
                        .getText()
                        .trim();

        String status =
                cmbStatus
                        .getSelectedItem()
                        .toString();

        if (name.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Category name is required.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        Category category =
                new Category(
                        name,
                        description,
                        status
                );

        boolean success =
                categoryDAO.addCategory(
                        category
                );

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Category added successfully!"
            );

            clearForm();
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

    private void updateCategory() {

        if (txtCategoryId
                .getText()
                .isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a category first."
            );

            return;
        }

        String name =
                txtCategoryName
                        .getText()
                        .trim();

        if (name.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Category name is required."
            );

            return;
        }

        int categoryId =
                Integer.parseInt(
                        txtCategoryId.getText()
                );

        String description =
                txtDescription
                        .getText()
                        .trim();

        String status =
                cmbStatus
                        .getSelectedItem()
                        .toString();

        Category category =
                new Category(
                        categoryId,
                        name,
                        description,
                        status
                );

        boolean success =
                categoryDAO.updateCategory(
                        category
                );

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Category updated successfully!"
            );

            clearForm();
            loadCategories();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to update category.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void deleteCategory() {

        if (txtCategoryId
                .getText()
                .isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a category first."
            );

            return;
        }

        int confirmation =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete this category?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION
                );

        if (confirmation !=
                JOptionPane.YES_OPTION) {

            return;
        }

        int categoryId =
                Integer.parseInt(
                        txtCategoryId.getText()
                );

        boolean success =
                categoryDAO.deleteCategory(
                        categoryId
                );

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Category deleted successfully!"
            );

            clearForm();
            loadCategories();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Category cannot be deleted. "
                            + "It may be used by a product.",
                    "Delete Failed",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void loadSelectedCategory() {

        int selectedRow =
                categoryTable
                        .getSelectedRow();

        if (selectedRow == -1) {

            return;
        }

        txtCategoryId.setText(
                tableModel
                        .getValueAt(
                                selectedRow,
                                0
                        )
                        .toString()
        );

        txtCategoryName.setText(
                tableModel
                        .getValueAt(
                                selectedRow,
                                1
                        )
                        .toString()
        );

        Object description =
                tableModel.getValueAt(
                        selectedRow,
                        2
                );

        txtDescription.setText(
                description == null
                        ? ""
                        : description.toString()
        );

        cmbStatus.setSelectedItem(
                tableModel
                        .getValueAt(
                                selectedRow,
                                3
                        )
                        .toString()
        );
    }

    private void clearForm() {

        txtCategoryId.setText("");
        txtCategoryName.setText("");
        txtDescription.setText("");

        cmbStatus.setSelectedItem(
                "ACTIVE"
        );

        categoryTable.clearSelection();
    }
}