package lk.textflow.view;

import lk.textflow.dao.CategoryDAO;
import lk.textflow.model.Category;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class CategoryPanel extends JPanel {

    // =========================================================
    // TEXTFLOW COLORS
    // =========================================================

    private static final Color PAGE_BG =
            new Color(245, 247, 250);

    private static final Color CARD_BG =
            Color.WHITE;

    private static final Color PRIMARY =
            new Color(37, 99, 235);

    private static final Color PRIMARY_DARK =
            new Color(30, 64, 175);

    private static final Color SUCCESS =
            new Color(22, 163, 74);

    private static final Color DANGER =
            new Color(220, 38, 38);

    private static final Color SECONDARY =
            new Color(100, 116, 139);

    private static final Color TEXT_DARK =
            new Color(30, 41, 59);

    private static final Color TEXT_MUTED =
            new Color(100, 116, 139);

    private static final Color BORDER =
            new Color(226, 232, 240);

    private static final Color TABLE_HEADER =
            new Color(30, 64, 175);

    private static final Color ALTERNATE_ROW =
            new Color(248, 250, 252);


    // =========================================================
    // COMPONENTS
    // =========================================================

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


    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public CategoryPanel() {

        categoryDAO = new CategoryDAO();

        setLayout(
                new BorderLayout()
        );

        setBackground(
                PAGE_BG
        );

        initializeUI();

        loadCategories();
    }


    // =========================================================
    // MAIN UI
    // =========================================================

    private void initializeUI() {

        JPanel container =
                new JPanel(
                        new BorderLayout(
                                0,
                                20
                        )
                );

        container.setBackground(
                PAGE_BG
        );

        container.setBorder(
                new EmptyBorder(
                        25,
                        30,
                        30,
                        30
                )
        );


        // =====================================================
        // PAGE HEADER
        // =====================================================

        JPanel header =
                new JPanel();

        header.setLayout(
                new BoxLayout(
                        header,
                        BoxLayout.Y_AXIS
                )
        );

        header.setBackground(
                PAGE_BG
        );


        JLabel title =
                new JLabel(
                        "Category Management"
                );

        title.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        28
                )
        );

        title.setForeground(
                TEXT_DARK
        );

        title.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );


        JLabel subtitle =
                new JLabel(
                        "Create and manage product categories"
                );

        subtitle.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        14
                )
        );

        subtitle.setForeground(
                TEXT_MUTED
        );

        subtitle.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );


        header.add(title);

        header.add(
                Box.createVerticalStrut(5)
        );

        header.add(subtitle);


        container.add(
                header,
                BorderLayout.NORTH
        );


        // =====================================================
        // CONTENT
        // =====================================================

        JPanel content =
                new JPanel(
                        new BorderLayout(
                                20,
                                20
                        )
                );

        content.setBackground(
                PAGE_BG
        );


        content.add(
                createFormPanel(),
                BorderLayout.NORTH
        );


        content.add(
                createTablePanel(),
                BorderLayout.CENTER
        );


        container.add(
                content,
                BorderLayout.CENTER
        );


        add(
                container,
                BorderLayout.CENTER
        );
    }


    // =========================================================
    // FORM CARD
    // =========================================================

    private JPanel createFormPanel() {

        JPanel card =
                new JPanel(
                        new BorderLayout()
                );

        card.setBackground(
                CARD_BG
        );

        card.setBorder(
                BorderFactory.createCompoundBorder(

                        BorderFactory.createLineBorder(
                                BORDER
                        ),

                        new EmptyBorder(
                                20,
                                25,
                                20,
                                25
                        )
                )
        );


        // =====================================================
        // CARD TITLE
        // =====================================================

        JLabel sectionTitle =
                new JLabel(
                        "Category Details"
                );

        sectionTitle.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        18
                )
        );

        sectionTitle.setForeground(
                TEXT_DARK
        );

        sectionTitle.setBorder(
                new EmptyBorder(
                        0,
                        0,
                        15,
                        0
                )
        );


        card.add(
                sectionTitle,
                BorderLayout.NORTH
        );


        // =====================================================
        // FORM
        // =====================================================

        JPanel form =
                new JPanel(
                        new GridBagLayout()
                );

        form.setBackground(
                CARD_BG
        );


        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(
                        7,
                        7,
                        7,
                        7
                );

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        gbc.weightx = 1;


        // =====================================================
        // COMPONENTS
        // =====================================================

        txtCategoryId =
                createTextField();

        txtCategoryId.setEditable(
                false
        );

        txtCategoryId.setBackground(
                new Color(
                        241,
                        245,
                        249
                )
        );


        txtCategoryName =
                createTextField();


        txtDescription =
                createTextField();


        cmbStatus =
                new JComboBox<>(
                        new String[]{
                                "ACTIVE",
                                "INACTIVE"
                        }
                );

        cmbStatus.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        14
                )
        );

        cmbStatus.setPreferredSize(
                new Dimension(
                        300,
                        38
                )
        );


        // =====================================================
        // ROW 1
        // =====================================================

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;

        form.add(
                createLabel(
                        "Category ID"
                ),
                gbc
        );


        gbc.gridx = 1;
        gbc.weightx = 1;

        form.add(
                txtCategoryId,
                gbc
        );


        // =====================================================
        // ROW 2
        // =====================================================

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;

        form.add(
                createLabel(
                        "Category Name"
                ),
                gbc
        );


        gbc.gridx = 1;
        gbc.weightx = 1;

        form.add(
                txtCategoryName,
                gbc
        );


        // =====================================================
        // ROW 3
        // =====================================================

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;

        form.add(
                createLabel(
                        "Description"
                ),
                gbc
        );


        gbc.gridx = 1;
        gbc.weightx = 1;

        form.add(
                txtDescription,
                gbc
        );


        // =====================================================
        // ROW 4
        // =====================================================

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;

        form.add(
                createLabel(
                        "Status"
                ),
                gbc
        );


        gbc.gridx = 1;
        gbc.weightx = 1;

        form.add(
                cmbStatus,
                gbc
        );


        // =====================================================
        // BUTTONS
        // =====================================================

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                10,
                                10
                        )
                );

        buttonPanel.setBackground(
                CARD_BG
        );


        btnAdd =
                createButton(
                        "Add Category",
                        PRIMARY,
                        Color.WHITE
                );


        btnUpdate =
                createButton(
                        "Update",
                        SUCCESS,
                        Color.WHITE
                );


        btnDelete =
                createButton(
                        "Delete",
                        DANGER,
                        Color.WHITE
                );


        btnClear =
                createButton(
                        "Clear",
                        SECONDARY,
                        Color.WHITE
                );


        buttonPanel.add(
                btnAdd
        );

        buttonPanel.add(
                btnUpdate
        );

        buttonPanel.add(
                btnDelete
        );

        buttonPanel.add(
                btnClear
        );


        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.weightx = 1;


        form.add(
                buttonPanel,
                gbc
        );


        // =====================================================
        // EVENTS
        // =====================================================

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


        card.add(
                form,
                BorderLayout.CENTER
        );


        return card;
    }


    // =========================================================
    // TABLE CARD
    // =========================================================

    private JPanel createTablePanel() {

        JPanel card =
                new JPanel(
                        new BorderLayout(
                                0,
                                15
                        )
                );

        card.setBackground(
                CARD_BG
        );

        card.setBorder(
                BorderFactory.createCompoundBorder(

                        BorderFactory.createLineBorder(
                                BORDER
                        ),

                        new EmptyBorder(
                                20,
                                20,
                                20,
                                20
                        )
                )
        );


        // =====================================================
        // TABLE TITLE
        // =====================================================

        JPanel tableHeaderPanel =
                new JPanel(
                        new BorderLayout()
                );

        tableHeaderPanel.setBackground(
                CARD_BG
        );


        JLabel title =
                new JLabel(
                        "Category List"
                );

        title.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        18
                )
        );

        title.setForeground(
                TEXT_DARK
        );


        JLabel hint =
                new JLabel(
                        "Select a category to update or delete"
                );

        hint.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        13
                )
        );

        hint.setForeground(
                TEXT_MUTED
        );


        tableHeaderPanel.add(
                title,
                BorderLayout.WEST
        );

        tableHeaderPanel.add(
                hint,
                BorderLayout.EAST
        );


        card.add(
                tableHeaderPanel,
                BorderLayout.NORTH
        );


        // =====================================================
        // TABLE MODEL
        // =====================================================

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


        // =====================================================
        // TABLE
        // =====================================================

        categoryTable =
                new JTable(
                        tableModel
                );


        categoryTable.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        14
                )
        );


        categoryTable.setRowHeight(
                38
        );


        categoryTable.setSelectionMode(
                ListSelectionModel
                        .SINGLE_SELECTION
        );


        categoryTable.setShowVerticalLines(
                false
        );

        categoryTable.setShowHorizontalLines(
                true
        );

        categoryTable.setGridColor(
                BORDER
        );


        categoryTable.setSelectionBackground(
                new Color(
                        219,
                        234,
                        254
                )
        );

        categoryTable.setSelectionForeground(
                TEXT_DARK
        );


        // =====================================================
        // TABLE HEADER
        // =====================================================

        JTableHeader header =
                categoryTable.getTableHeader();


        header.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        14
                )
        );


        header.setBackground(
                TABLE_HEADER
        );


        header.setForeground(
                Color.WHITE
        );


        header.setPreferredSize(
                new Dimension(
                        header.getPreferredSize().width,
                        42
                )
        );


        header.setReorderingAllowed(
                false
        );


        // =====================================================
        // CUSTOM ROW COLORS
        // =====================================================

        categoryTable.setDefaultRenderer(
                Object.class,
                new CategoryTableRenderer()
        );


        // =====================================================
        // COLUMN WIDTHS
        // =====================================================

        categoryTable
                .getColumnModel()
                .getColumn(0)
                .setPreferredWidth(60);


        categoryTable
                .getColumnModel()
                .getColumn(1)
                .setPreferredWidth(220);


        categoryTable
                .getColumnModel()
                .getColumn(2)
                .setPreferredWidth(400);


        categoryTable
                .getColumnModel()
                .getColumn(3)
                .setPreferredWidth(120);


        // =====================================================
        // SELECT ROW
        // =====================================================

        categoryTable
                .getSelectionModel()
                .addListSelectionListener(

                        e -> {

                            if (!e.getValueIsAdjusting()) {

                                loadSelectedCategory();
                            }
                        }
                );


        JScrollPane scrollPane =
                new JScrollPane(
                        categoryTable
                );


        scrollPane.setBorder(
                BorderFactory.createLineBorder(
                        BORDER
                )
        );


        scrollPane.getViewport()
                .setBackground(
                        Color.WHITE
                );


        card.add(
                scrollPane,
                BorderLayout.CENTER
        );


        return card;
    }


    // =========================================================
    // CREATE TEXT FIELD
    // =========================================================

    private JTextField createTextField() {

        JTextField field =
                new JTextField();

        field.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        14
                )
        );


        field.setPreferredSize(
                new Dimension(
                        300,
                        38
                )
        );


        field.setBorder(
                BorderFactory.createCompoundBorder(

                        BorderFactory.createLineBorder(
                                BORDER
                        ),

                        new EmptyBorder(
                                7,
                                10,
                                7,
                                10
                        )
                )
        );


        return field;
    }


    // =========================================================
    // CREATE LABEL
    // =========================================================

    private JLabel createLabel(
            String text) {

        JLabel label =
                new JLabel(text);


        label.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        14
                )
        );


        label.setForeground(
                TEXT_DARK
        );


        return label;
    }


    // =========================================================
    // CREATE BUTTON
    // =========================================================

    private JButton createButton(
            String text,
            Color background,
            Color foreground) {

        JButton button =
                new JButton(text);


        button.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        13
                )
        );


        button.setForeground(
                foreground
        );


        button.setBackground(
                background
        );


        button.setOpaque(
                true
        );


        button.setBorderPainted(
                false
        );


        button.setFocusPainted(
                false
        );


        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );


        button.setPreferredSize(
                new Dimension(
                        130,
                        38
                )
        );


        return button;
    }


    // =========================================================
    // CUSTOM TABLE RENDERER
    // =========================================================

    private class CategoryTableRenderer
            extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(
                JTable table,
                Object value,
                boolean isSelected,
                boolean hasFocus,
                int row,
                int column) {

            Component component =
                    super.getTableCellRendererComponent(
                            table,
                            value,
                            isSelected,
                            hasFocus,
                            row,
                            column
                    );


            setBorder(
                    new EmptyBorder(
                            0,
                            10,
                            0,
                            10
                    )
            );


            if (isSelected) {

                component.setBackground(
                        new Color(
                                219,
                                234,
                                254
                        )
                );

                component.setForeground(
                        TEXT_DARK
                );

            } else {

                if (row % 2 == 0) {

                    component.setBackground(
                            Color.WHITE
                    );

                } else {

                    component.setBackground(
                            ALTERNATE_ROW
                    );
                }


                component.setForeground(
                        TEXT_DARK
                );
            }


            // Status column
            if (column == 3
                    && value != null) {

                String status =
                        value.toString();


                if ("ACTIVE".equals(status)) {

                    component.setForeground(
                            SUCCESS
                    );

                    setFont(
                            new Font(
                                    "SansSerif",
                                    Font.BOLD,
                                    13
                            )
                    );

                } else {

                    component.setForeground(
                            DANGER
                    );

                    setFont(
                            new Font(
                                    "SansSerif",
                                    Font.BOLD,
                                    13
                            )
                    );
                }

            } else {

                setFont(
                        new Font(
                                "SansSerif",
                                Font.PLAIN,
                                14
                        )
                );
            }


            return component;
        }
    }


    // =========================================================
    // LOAD CATEGORIES
    // =========================================================

    private void loadCategories() {

        tableModel.setRowCount(0);


        List<Category> categories =
                categoryDAO
                        .getAllCategories();


        for (Category category :
                categories) {

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


    // =========================================================
    // ADD CATEGORY
    // =========================================================

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

            txtCategoryName.requestFocus();

            return;
        }


        Category category =
                new Category(
                        name,
                        description,
                        status
                );


        boolean success =
                categoryDAO
                        .addCategory(
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

                    "Failed to add category.\n"
                            + "The category name may already exist.",

                    "Add Failed",

                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // =========================================================
    // UPDATE CATEGORY
    // =========================================================

    private void updateCategory() {

        if (txtCategoryId
                .getText()
                .trim()
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
                        txtCategoryId
                                .getText()
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
                categoryDAO
                        .updateCategory(
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
                    "Update Failed",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // =========================================================
    // DELETE CATEGORY
    // =========================================================

    private void deleteCategory() {

        if (txtCategoryId
                .getText()
                .trim()
                .isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a category first."
            );

            return;
        }


        String categoryName =
                txtCategoryName
                        .getText()
                        .trim();


        int confirmation =
                JOptionPane.showConfirmDialog(
                        this,

                        "Delete category \""
                                + categoryName
                                + "\"?\n\n"
                                + "Categories used by products "
                                + "cannot be deleted.",

                        "Confirm Delete",

                        JOptionPane.YES_NO_OPTION,

                        JOptionPane.WARNING_MESSAGE
                );


        if (confirmation
                != JOptionPane.YES_OPTION) {

            return;
        }


        int categoryId =
                Integer.parseInt(
                        txtCategoryId
                                .getText()
                );


        boolean success =
                categoryDAO
                        .deleteCategory(
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

                    "This category cannot be deleted.\n"
                            + "It may already be assigned to a product.\n\n"
                            + "You can change its status to INACTIVE instead.",

                    "Delete Failed",

                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // =========================================================
    // LOAD SELECTED CATEGORY
    // =========================================================

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
                tableModel
                        .getValueAt(
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


    // =========================================================
    // CLEAR FORM
    // =========================================================

    private void clearForm() {

        txtCategoryId.setText("");

        txtCategoryName.setText("");

        txtDescription.setText("");


        cmbStatus.setSelectedItem(
                "ACTIVE"
        );


        categoryTable.clearSelection();


        txtCategoryName.requestFocus();
    }


    // =========================================================
    // PUBLIC REFRESH
    // =========================================================

    public void refreshData() {

        loadCategories();

        clearForm();
    }
}