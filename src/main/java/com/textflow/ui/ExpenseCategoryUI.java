package com.textflow.ui;

import com.textflow.dao.ExpenseCategoryDAO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ExpenseCategoryUI extends JFrame {

    private JTextField categoryNameField;
    private JTextField descriptionField;

    private JTable categoryTable;
    private DefaultTableModel tableModel;

    private final ExpenseCategoryDAO categoryDAO;

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

    public ExpenseCategoryUI() {

        categoryDAO =
                new ExpenseCategoryDAO();

        setTitle(
                "TextFlow - Expense Category Management"
        );

        setSize(
                850,
                600
        );

        setMinimumSize(
                new Dimension(
                        750,
                        520
                )
        );

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setLocationRelativeTo(null);

        createUI();

        loadCategories();
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
                        "TEXTFLOW  |  Expense Categories"
                );

        titleLabel.setForeground(
                Color.WHITE
        );

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        22
                )
        );

        headerPanel.add(
                titleLabel,
                BorderLayout.WEST
        );

        rootPanel.add(
                headerPanel,
                BorderLayout.NORTH
        );

        // ==================================================
        // CONTENT
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
                                12
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
                        "Category Details"
                );

        formTitle.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        17
                )
        );

        formTitle.setForeground(
                DARK_BLUE
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
                        8,
                        8,
                        8,
                        8
                );

        gbc.anchor =
                GridBagConstraints.WEST;

        categoryNameField =
                new JTextField();

        descriptionField =
                new JTextField();

        addFormRow(
                fieldsPanel,
                gbc,
                0,
                "Category Name:",
                categoryNameField
        );

        addFormRow(
                fieldsPanel,
                gbc,
                1,
                "Description:",
                descriptionField
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
                        "Add Category",
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
                        "Deactivate Selected",
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
                        "Expense Categories"
                );

        tableTitle.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        17
                )
        );

        tableTitle.setForeground(
                DARK_BLUE
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
                new JTable(
                        tableModel
                );

        categoryTable.setRowHeight(
                28
        );

        categoryTable.setSelectionMode(
                ListSelectionModel
                        .SINGLE_SELECTION
        );

        categoryTable.setGridColor(
                new Color(
                        230,
                        233,
                        238
                )
        );

        categoryTable
                .getTableHeader()
                .setBackground(
                        DARK_BLUE
                );

        categoryTable
                .getTableHeader()
                .setForeground(
                        Color.WHITE
                );

        categoryTable
                .getTableHeader()
                .setFont(
                        new Font(
                                "Arial",
                                Font.BOLD,
                                12
                        )
                );

        categoryTable
                .getTableHeader()
                .setPreferredSize(
                        new Dimension(
                                0,
                                32
                        )
                );

        JScrollPane scrollPane =
                new JScrollPane(
                        categoryTable
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
        // ACTIONS
        // ==================================================

        addButton.addActionListener(
                e -> addCategory()
        );

        deleteButton.addActionListener(
                e -> deleteCategory()
        );

        refreshButton.addActionListener(
                e -> loadCategories()
        );

        clearButton.addActionListener(
                e -> clearFields()
        );

        closeButton.addActionListener(
                e -> dispose()
        );
    }

    // ==================================================
    // FORM ROW
    // ==================================================

    private void addFormRow(
            JPanel panel,
            GridBagConstraints gbc,
            int row,
            String labelText,
            Component component
    ) {

        gbc.gridx =
                0;

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
                        130,
                        30
                )
        );

        panel.add(
                label,
                gbc
        );

        gbc.gridx =
                1;

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
                                    450,
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
    // ADD CATEGORY
    // ==================================================

    private void addCategory() {

        String categoryName =
                categoryNameField
                        .getText()
                        .trim();

        String description =
                descriptionField
                        .getText()
                        .trim();

        if (
                categoryName.isEmpty()
        ) {

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

        if (
                success
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Category added successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            clearFields();

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

    // ==================================================
    // DEACTIVATE CATEGORY
    // ==================================================

    private void deleteCategory() {

        int selectedRow =
                categoryTable
                        .getSelectedRow();

        if (
                selectedRow == -1
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a category first.",
                    "Selection Required",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

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

        String status =
                tableModel
                        .getValueAt(
                                selectedRow,
                                4
                        )
                        .toString();

        if (
                "INACTIVE"
                        .equalsIgnoreCase(
                                status
                        )
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "This category is already inactive.",
                    "Category",
                    JOptionPane.INFORMATION_MESSAGE
            );

            return;
        }

        int confirmation =
                JOptionPane.showConfirmDialog(
                        this,
                        "Deactivate category: "
                                + categoryName
                                + "?",
                        "Confirm Deactivation",
                        JOptionPane.YES_NO_OPTION
                );

        if (
                confirmation
                        != JOptionPane.YES_OPTION
        ) {

            return;
        }

        boolean success =
                categoryDAO.deleteCategory(
                        categoryId
                );

        if (
                success
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Category deactivated successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            clearFields();

            loadCategories();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to deactivate category.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ==================================================
    // LOAD CATEGORIES
    // ==================================================

    private void loadCategories() {

        tableModel.setRowCount(
                0
        );

        List<String[]> categories =
                categoryDAO
                        .getCategories();

        int displayNumber =
                1;

        for (
                String[] category
                : categories
        ) {

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

    // ==================================================
    // CLEAR
    // ==================================================

    private void clearFields() {

        categoryNameField
                .setText("");

        descriptionField
                .setText("");

        categoryTable
                .clearSelection();

        categoryNameField
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
}